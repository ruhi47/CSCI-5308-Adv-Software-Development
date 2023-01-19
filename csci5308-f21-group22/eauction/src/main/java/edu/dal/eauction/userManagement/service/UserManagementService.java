package edu.dal.eauction.userManagement.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import edu.dal.eauction.emailSender.IAsyncEmailSenderService;
import edu.dal.eauction.emailSender.SmtpEmailSenderImpl;
import edu.dal.eauction.emailSender.email.Email;
import edu.dal.eauction.emailSender.email.EmailType;
import edu.dal.eauction.emailSender.email.params.ForgotPasswordParams;
import edu.dal.eauction.emailSender.email.params.IEmailContentParams;
import edu.dal.eauction.userManagement.dao.UserDaoImpl;
import edu.dal.eauction.userManagement.entities.User;
import edu.dal.eauction.userManagement.entities.UserRole;
import edu.dal.eauction.userManagement.exception.UserAlreadyExistsException;
import edu.dal.eauction.userManagement.exception.UserCreationException;

@Service
public class UserManagementService implements IUserManagementService {

	private static final String KEY_DEFAULT_PASSWORD = "KEY_DEFAULT_PASSWORD";
	private static final Logger LOG = LogManager.getLogger();
	
	public UserManagementService() {
		super();			
		this.userDao = new UserDaoImpl();
		this.searchUserService = new SearchUserService();
		this.emailService = new SmtpEmailSenderImpl();	
	}

	private UserDaoImpl userDao;
	private SearchUserService searchUserService;
	private IAsyncEmailSenderService emailService;
	
	@Override
	public boolean registerUser(User user) throws Exception {
		User dbUser = searchUserService.searchUserByEmail(user.getEmail());
		if (Objects.nonNull(dbUser)) {
			String error = errorMessage(ServiceErrorResponse.ACCOUNT_ALREADY_EXISTS);
			LOG.error(error);
			throw new UserAlreadyExistsException(error);
		}
		user.setDefaultsForNewUser();
		Integer userId = userDao.create(user);
		if (Objects.isNull(userId)) {
			LOG.error("Exception while creating user {}", user.getEmail());
			throw new UserCreationException(errorMessage(ServiceErrorResponse.USER_CREATION_ERROR));
		}
		return true;
	}

	@Override
	public void processPostLoginRules(String email) {
		User user = searchUserService.searchUserByEmail(email);
		user.setLastSigninDate(LocalDateTime.now());
		userDao.update(user);
	}

	@Override
	public void recoverPassword(String emailAddress) throws Exception {
		User user = searchUserService.searchUserByEmail(emailAddress);
		if (Objects.isNull(user) || UserRole.ADMIN.equals(user.getRole())) {
			return;
		}
		if (user.isActiveUser()) {
			String defaultPassword = System.getenv(KEY_DEFAULT_PASSWORD);
			sendRecoveryEmail(user, defaultPassword);		
			user.setPassword(defaultPassword);
			user.hashPassword();
			userDao.update(user);
		} 
		else {
			String error = errorMessage(ServiceErrorResponse.ACCOUNT_DEACTIVATED);
			LOG.error(error);
			throw new Exception(error);
		}
	}
	
	private void sendRecoveryEmail(User user, String defaultPassword) {
		IEmailContentParams params = new ForgotPasswordParams(user.getFirstName(), defaultPassword);
		Email email = new Email(EmailType.FORGOT_PASSWORD_EMAIL, user.getEmail());
		email.constructEmailContent(params);
		emailService.sendEmail(email);
	}
	
	private String errorMessage(ServiceErrorResponse response) {
		return ServiceErrorResponse.getErrorMessage(response);
	}

}