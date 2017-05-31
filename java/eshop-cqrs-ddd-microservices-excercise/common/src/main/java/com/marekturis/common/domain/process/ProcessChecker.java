package com.marekturis.common.domain.process;

import com.marekturis.common.domain.event.EventPublisher;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Named;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Marek Turis
 */
@Named
public class ProcessChecker {

	private ProcessRepository processRepository;

	private EventPublisher eventPublisher;

	public ProcessChecker(ProcessRepository processRepository, EventPublisher eventPublisher) {
		this.processRepository = processRepository;
		this.eventPublisher = eventPublisher;
	}

	@Scheduled(fixedRate = 5000)
	public void checkProcesses() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
		for (Process process : processRepository.getAllProcesses()) {
			if (process.hasTimedOut()) {
				if (process.hasTotallyTimedOut()) {
					processTotallyTimedOut(process);
				} else {
					processTimedOut(process);
				}
			}
		}
	}

	private void processTimedOut(Process process) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		process.increaseRetries();
		process.refreshNextTimeOut();
		ProcessTimedOutEvent event = getTimedOutEventForProcess(process, false);
		eventPublisher.publish(event);
	}

	private void processTotallyTimedOut(Process process) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		ProcessTimedOutEvent event = getTimedOutEventForProcess(process, true);
		eventPublisher.publish(event);
		processRepository.removeProcess(process);
	}

	private ProcessTimedOutEvent getTimedOutEventForProcess(Process process, boolean totallyTimedOut) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Class<?> processTimedOutClass = Class.forName(process.processTimedOutEventType());

		Constructor<?> ctor = processTimedOutClass.getConstructor(process.getClass(), boolean.class);

		return (ProcessTimedOutEvent) ctor.newInstance(process, totallyTimedOut);
	}
}
