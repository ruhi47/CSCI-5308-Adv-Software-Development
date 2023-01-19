package edu.dal.eauction.coreServices;

import edu.dal.eauction.userManagement.security.BCryptPasswordEncoderImpl;
import edu.dal.eauction.userManagement.security.IPasswordEncoder;

public class SystemServices {

	private static SystemServices systemServices;

	private static IPasswordEncoder passwordEncoder;

	private static IThreadPoolExecutor threadPoolExecutor;

	private SystemServices() {
		super();
	}

	public static SystemServices getInstance() {
		if (systemServices == null) {
			synchronized (SystemServices.class) {
				if (systemServices == null) {
					systemServices = new SystemServices();
					initializeServices();
				}
			}
		}
		return systemServices;
	}

	private static void initializeServices() {
		passwordEncoder = BCryptPasswordEncoderImpl.getInstance();
		threadPoolExecutor = FixedThreadPoolExecutorService.getInstance();
	}

	public IPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public IThreadPoolExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

}
