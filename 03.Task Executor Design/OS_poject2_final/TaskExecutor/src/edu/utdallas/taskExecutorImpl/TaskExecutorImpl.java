package edu.utdallas.taskExecutorImpl;

import java.util.ArrayList;
import java.util.List;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskExecutorImpl implements TaskExecutor {
	private List<TaskRunner> runnerPool = new ArrayList<>();

	public TaskExecutorImpl(int poolSize) {
		for (int i = 0; i < poolSize; i++) {
			TaskRunner newTaskRunner = new TaskRunner();
//			runnerPool.add(newTaskRunner);
			Thread thread = new Thread(newTaskRunner);
			thread.start();
			Thread.yield();
		}
	}

	@Override
	public void addTask(Task task) {
		TaskRunner.getTaskQueue().add(task);
	}

}
