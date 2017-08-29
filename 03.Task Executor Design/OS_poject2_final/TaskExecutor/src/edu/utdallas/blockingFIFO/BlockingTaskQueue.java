package edu.utdallas.blockingFIFO;

import java.util.concurrent.Semaphore;
import edu.utdallas.taskExecutor.Task;

public class BlockingTaskQueue {
	final private int SIZE = 100;
	private Task[] taskQueue = new Task[SIZE];
	private int start = 0;
	private int end = 0;
	private Semaphore semaForAdd = new Semaphore(SIZE, true);
	private Semaphore semaForRemove = new Semaphore(0, true);
	private Semaphore semaForMutex = new Semaphore(1, true);

	public void add(Task task) {
		try {
			//first, check whether there is space in the queue to add task
			semaForAdd.acquire();
			//second, check whether the queue is being used by other thread 
			semaForMutex.acquire();
			taskQueue[end] = task;
			end = (end + 1) % SIZE;
			semaForRemove.release();
			semaForMutex.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Task remove() {
		Task taskForExecute = null;
		try {
			//first, check whether there is task waiting in the queue
			semaForRemove.acquire();
			//second, check whether the queue is being used by other thread
			semaForMutex.acquire();
			taskForExecute = taskQueue[start];
			start = (start + 1) % SIZE;
			semaForAdd.release();
			semaForMutex.release();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return taskForExecute;
	}

}
