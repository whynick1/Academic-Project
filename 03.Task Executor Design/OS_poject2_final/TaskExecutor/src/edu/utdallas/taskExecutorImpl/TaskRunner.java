package edu.utdallas.taskExecutorImpl;

import javax.management.monitor.Monitor;

import edu.utdallas.blockingFIFO.BlockingTaskQueue;
import edu.utdallas.taskExecutor.Task;

public class TaskRunner implements Runnable {
	private static BlockingTaskQueue taskQueue = new BlockingTaskQueue();

	public static BlockingTaskQueue getTaskQueue() {
		return taskQueue;
	}
	
	@Override
	public void run() {
		while (true) {
			Task task = taskQueue.remove();
			task.execute();
		}
	}

}


/*public void run() {
    while(true) {
        // take() blocks if queue is empty
        Task newTask = blockingFifoQueue.take();
        try {
            newTask.execute();
        }
        catch(Throwable th) {
           // Log (e.g. print exception’s message to console) 
           // and drop any exceptions thrown by the task’s
           // execution.
        }
    }
}*/
