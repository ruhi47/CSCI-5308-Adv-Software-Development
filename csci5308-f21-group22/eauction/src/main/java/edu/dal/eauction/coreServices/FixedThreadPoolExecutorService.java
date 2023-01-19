package edu.dal.eauction.coreServices;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/*
* Constructing this class as singleton
* Since there should be a single instance of thread pool executor
* For the application executing all asynchronous tasks
*/
public class FixedThreadPoolExecutorService implements IThreadPoolExecutor {

	private final static Integer NUMBER_OF_THREADS = 10;

	private static final FixedThreadPoolExecutorService FIXED_THREAD_POOL_EXECUTOR = new FixedThreadPoolExecutorService();

	private static ThreadPoolExecutor executor;

	private FixedThreadPoolExecutorService() {
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBER_OF_THREADS);
	}

	public static FixedThreadPoolExecutorService getInstance() {
		return FIXED_THREAD_POOL_EXECUTOR;
	}

	@Override
	public void runTask(Runnable runnable) {
		if (Objects.nonNull(runnable)) {
			executor.execute(runnable);
		}
	}

}
