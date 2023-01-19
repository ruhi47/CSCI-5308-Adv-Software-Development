package edu.dal.eauction.emailSender;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import edu.dal.eauction.coreServices.FixedThreadPoolExecutorService;

class SystemFixedThreadPoolExecutorTest {

	@Test
	void testSingleton() {
		FixedThreadPoolExecutorService executor1 = FixedThreadPoolExecutorService.getInstance();
		FixedThreadPoolExecutorService executor2 = FixedThreadPoolExecutorService.getInstance();
		assertThat(executor1, equalTo(executor2));
	}
	

}
