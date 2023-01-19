package edu.dal.eauction.emailSender;

import java.util.Properties;

public abstract class EmailConfiguration {

	public EmailConfiguration() {
		super();
	}
	
	protected abstract Properties getConfigProperties();

}
