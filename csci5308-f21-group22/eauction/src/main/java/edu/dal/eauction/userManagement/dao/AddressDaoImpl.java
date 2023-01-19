package edu.dal.eauction.userManagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import edu.dal.eauction.Dao.DBConnect;
import edu.dal.eauction.userManagement.entities.Address;

public class AddressDaoImpl  {
	
	private static final String INSERT_ADDRESS = "INSERT INTO ADDRESS (USER_ID, STREET_1, STREET_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

	public Integer create(Address address) {		
		Integer id = null;
		DBConnect con = DBConnect.getInstance();
		try (Connection connection = con.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADDRESS)) {
			preparedStatement.setInt(1, address.getUser().getId());
			preparedStatement.setString(2, address.getAddressLine1());
			preparedStatement.setString(3, address.getAddressLine2());
			preparedStatement.setString(4, address.getCity());
			preparedStatement.setString(5, address.getProvince());
			preparedStatement.setObject(6, address.getCountry());
			preparedStatement.setString(7, address.getZipCode());
			id = preparedStatement.executeUpdate();
			System.out.println("Address Inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

}
