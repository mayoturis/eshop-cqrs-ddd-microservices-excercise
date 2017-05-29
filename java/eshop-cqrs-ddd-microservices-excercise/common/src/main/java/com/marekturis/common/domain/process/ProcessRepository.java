package com.marekturis.common.domain.process;

import java.util.List;

/**
 * @author Marek Turis
 */
public interface ProcessRepository {
	void addProcess(Process process);
	void removeProcess(Process process);
	List<Process> getAllProcesses();
	Process getProcessById(String processId);
}
