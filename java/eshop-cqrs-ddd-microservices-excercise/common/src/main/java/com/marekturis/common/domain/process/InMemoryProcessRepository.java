package com.marekturis.common.domain.process;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Turis
 */
@Named
public class InMemoryProcessRepository implements ProcessRepository {

	private List<Process> processes = new ArrayList<>();

	@Override
	public void addProcess(Process process) {
		processes.add(process);
	}

	@Override
	public void removeProcess(Process process) {
		processes.remove(process);
	}

	@Override
	public List<Process> getAllProcesses() {
		return new ArrayList<>(processes);
	}

	@Override
	public Process getProcessById(String processId) {
		for (Process process : processes) {
			if (process.getId().equals(processId)) {
				return process;
			}
		}

		return null;
	}

}
