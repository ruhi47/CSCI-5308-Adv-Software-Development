package edu.dal.eauction.userManagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.dal.eauction.Dao.DBConnect;
import edu.dal.eauction.userManagement.entities.Address;
import edu.dal.eauction.userManagement.entities.User;
import edu.dal.eauction.userManagement.entities.UserBuilder;
import edu.dal.eauction.userManagement.entities.UserRole;
import edu.dal.eauction.userManagement.entities.UserStatus;

public class UserDaoImpl implements UserDao {
	
	private static final String INSERT_USERS = "INSERT INTO USERS VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_USER = "SELECT USERS.FIRST_NAME, USERS.LAST_NAME, USERS.EMAIL, USERS.PASSWORD, USERS.DATE_OF_BIRTH, ADDRESS.STREET_1, ADDRESS.STREET_2, ADDRESS.CITY, ADDRESS.PROVINCE, ADDRESS.COUNTRY, ADDRESS.POSTAL_CODE, USERS.STATUS FROM USERS JOIN ADDRESS ON USERS.USER_ID = ADDRESS.USER_ID WHERE USERS.USER_ID = ?";
	private static final String SELECT_ALL_USERS = "SELECT FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, ADDRESS, STATUS FROM USERS";
	private static final String DELETE_USER = "DELETE FROM USERS WHERE USER_ID =?";
	private static final String UPDATE_USER = "UPDATE USERS SET FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, PASSWORD = ? , DATE_OF_BIRTH = ?, STATUS = ?, CREATED_DATE = ?, ROLE = ?, LAST_LOGGED_IN_DATE = ?, CREDITS = ?  WHERE USER_ID = ?";
	private static final String SELECT_USER_EMAIL = "SELECT USERS.USER_ID, USERS.FIRST_NAME, USERS.LAST_NAME, USERS.EMAIL, USERS.PASSWORD, USERS.DATE_OF_BIRTH, ADDRESS.STREET_1, ADDRESS.STREET_2, ADDRESS.CITY, ADDRESS.COUNTRY, ADDRESS.PROVINCE, ADDRESS.POSTAL_CODE, USERS.STATUS, USERS.ROLE, USERS.LAST_LOGGED_IN_DATE, USERS.CREDITS, USERS.CREATED_DATE FROM USERS INNER JOIN ADDRESS ON USERS.USER_ID = ADDRESS.USER_ID WHERE EMAIL = ?";
	private static final String SELECT_USER_BY_STATUS = "SELECT USER_ID, FIRST_NAME, LAST_NAME, CREATED_DATE, EMAIL, PASSWORD, DATE_OF_BIRTH, "
			+ "STATUS, ROLE, LAST_LOGGED_IN_DATE, CREDITS FROM USERS WHERE STATUS = ? AND ROLE = 'USER'";
	private static final Logger LOG = LogManager.getLogger();

	public UserDaoImpl() {
		super();
		this.addressDao = new AddressDaoImpl();
	}

	private AddressDaoImpl addressDao;

	@Override
	public Integer create(User newUser) {
		int id = autoIncrementId(newUser);
		newUser.setId(id);
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS)) {
			preparedStatement.setInt(1, newUser.getId());
			preparedStatement.setString(2, newUser.getFirstName());
			preparedStatement.setString(3, newUser.getLastName());
			preparedStatement.setObject(4, newUser.getCreatedDate());
			preparedStatement.setString(5, newUser.getEmail());
			preparedStatement.setString(6, newUser.getPassword());
			preparedStatement.setObject(7, newUser.getDateOfBirth());
			preparedStatement.setString(8, newUser.getStatus().toString());
			preparedStatement.setString(9, newUser.getRoleAsString());
			preparedStatement.setObject(10, newUser.getLastSigninDate());
			preparedStatement.setFloat(11, newUser.getCredits());
			id = preparedStatement.executeUpdate();
			LOG.info("User Inserted");
			insertUserAddress(newUser.getAddress());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return id;
	}

	private void insertUserAddress(Address address) throws Exception {
		int addressId = addressDao.create(address);
		if (addressId <= 0) {
			throw new Exception("Error inserting address");
		}
	}

	// update user
	@Override
	public void update(User transientObject) {
		boolean rowUpdated;
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);) {
			preparedStatement.setString(1, transientObject.getFirstName());
			preparedStatement.setString(2, transientObject.getLastName());
			preparedStatement.setString(3, transientObject.getEmail());
			preparedStatement.setString(4, transientObject.getPassword());
			preparedStatement.setObject(5, transientObject.getDateOfBirth());
			preparedStatement.setString(6, transientObject.getStatus().toString());
			preparedStatement.setObject(7, transientObject.getCreatedDate());
			preparedStatement.setString(8, transientObject.getRole().toString());
			preparedStatement.setObject(9, transientObject.getLastSigninDate());
			preparedStatement.setFloat(10, transientObject.getCredits());
			preparedStatement.setInt(11, transientObject.getId());
			rowUpdated = preparedStatement.executeUpdate() > 0;
			System.out.print(rowUpdated);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// select user using id
	@Override
	public User read(Integer id) {
		User user = null;
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER);) {
			preparedStatement.setInt(1, id);
			System.out.print(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String firstName = rs.getString("FIRST_NAME");
				String lastName = rs.getString("LAST_NAME");
				String email = rs.getString("EMAIL");
				String password = rs.getString("PASSWORD");
				LocalDate dateOfBirth = rs.getDate("DATE_OF_BIRTH").toLocalDate();
				String street_1 = rs.getString("STREET_1");
				String street_2 = rs.getString("STREET_2");
				String city = rs.getString("CITY");
				String province = rs.getString("PROVINCE");
				String country = rs.getString("COUNTRY");
				String postal_code = rs.getString("POSTAL_CODE");
				UserStatus status = (UserStatus.valueOf(rs.getString("status")));
				user = new User(firstName, lastName, email, password, dateOfBirth, status);
				Address address = new Address(user, street_1, street_2, city, province, country, postal_code);
				user.setAddress(address);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> read() {
		DBConnect con = DBConnect.getInstance();
		User user = null;
		List<User> users = new ArrayList<>();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
			System.out.print(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String firstName = rs.getString("FIRST_NAME");
				String lastName = rs.getString("LAST_NAME");
				String email = rs.getString("EMAIL");
				String password = rs.getString("PASSWORD");
				LocalDate dateOfBirth = rs.getDate("DATE_OF_BIRTH").toLocalDate();
				String street_1 = rs.getString("street_1");
				String street_2 = rs.getString("street_2");
				String city = rs.getString("city");
				String province = rs.getString("province");
				String country = rs.getString("country");
				String postal_code = rs.getString("postal_code");
				UserStatus status = (UserStatus.valueOf(rs.getString("status")));
				user = new User(firstName, lastName, email, password, dateOfBirth, status);
				Address address = new Address(user, street_1, street_2, city, province, country, postal_code);
				user.setAddress(address);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	// delete user
	@Override
	public void delete(Integer id) throws SQLException {
		boolean rowDeleted;
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);) {
			preparedStatement.setInt(1, id);
			rowDeleted = preparedStatement.executeUpdate() > 0;
		}
		System.out.print(rowDeleted);
	}

	// Select user by email id
	public User readByEmail(String Email) {
		DBConnect con = DBConnect.getInstance();
		User user = null;
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_EMAIL);) {
			preparedStatement.setString(1, Email);
			System.out.print(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("USER_ID");
				String firstName = rs.getString("FIRST_NAME");
				String lastName = rs.getString("LAST_NAME");
				String email = rs.getString("EMAIL");
				String password = rs.getString("PASSWORD");
				LocalDate dateOfBirth = rs.getDate("DATE_OF_BIRTH").toLocalDate();
				String street_1 = rs.getString("street_1");
				String street_2 = rs.getString("street_2");
				String city = rs.getString("city");
				String province = rs.getString("province");
				String country = rs.getString("country");
				String postal_code = rs.getString("postal_code");
				String role = rs.getString("ROLE");
				LocalDateTime lastSigninDate = (LocalDateTime) rs.getObject("LAST_LOGGED_IN_DATE");
				LocalDateTime createdDate = (LocalDateTime) rs.getObject("CREATED_DATE");
				Float credits = rs.getFloat("CREDITS");
				UserStatus status = (UserStatus.valueOf(rs.getString("status")));
				user = new User(firstName, lastName, email, password, dateOfBirth, status);
				Address address = new Address(user, street_1, street_2, city, province, country, postal_code);
				user.setId(id);
				user.setAddress(address);
				user.setCreatedDate(createdDate);
				user.setRole(UserRole.valueOf(role));
				user.setLastSigninDate(lastSigninDate);
				user.setCredits(credits);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;

	}

	// Auto incrementing user id to insert the data into database
	@Override
	public Integer autoIncrementId(User user) {
		DBConnect con = DBConnect.getInstance();
		Connection connection = con.getConnection();
		ResultSet rs;
		int user_id = 0;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select max(user_id)+1 from USERS");
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				user_id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user_id;
	}

	public List<User> searchByStatus(UserStatus inputStatus) {
		List<User> users = new ArrayList<>();
		DBConnect dbConnection = DBConnect.getInstance();
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_STATUS);) {				
			statement.setString(1, inputStatus.name());
			ResultSet result = statement.executeQuery();
			while (result.next()) {				
				int id = result.getInt(USER_ID);
				String firstName = result.getString(FIRST_NAME);
				String lastName = result.getString(LAST_NAME);
				String email = result.getString(EMAIL);
				String password = result.getString(PASSWORD);
				LocalDate dateOfBirth = result.getDate(DATE_OF_BIRTH).toLocalDate();
				String status = result.getString(STATUS);
				LocalDateTime createdDate = (LocalDateTime) result.getObject(CREATED_DATE);
				String role = result.getString(ROLE);
				LocalDateTime lastSigninDate = (LocalDateTime) result.getObject(LAST_LOGGED_IN_DATE);
				Float credits = result.getFloat(CREDITS);
				User user = new UserBuilder()
										.id(id)
										.firstName(firstName)
										.lastName(lastName)
										.email(email)
										.password(password)
										.dateOfBirth(dateOfBirth)
										.status(status)
										.createdDate(createdDate)
										.role(UserRole.valueOf(role))
										.lastSigninDate(lastSigninDate)
										.credits(credits)
										.build();
				users.add(user);
			}
		} catch (SQLException exception) {
			LOG.error("Error while searching user: {}", exception.getMessage());
		}
		return users;
	}
}
