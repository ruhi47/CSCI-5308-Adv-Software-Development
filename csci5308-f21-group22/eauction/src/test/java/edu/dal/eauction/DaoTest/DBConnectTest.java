package edu.dal.eauction.DaoTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import edu.dal.eauction.Dao.DBConnect;


public class DBConnectTest {
	@Test
	void testSingleton() {
		DBConnect instance1 = DBConnect.getInstance();
		DBConnect instance2 = DBConnect.getInstance();
		assertThat(instance1, equalTo(instance2));
	}
}
