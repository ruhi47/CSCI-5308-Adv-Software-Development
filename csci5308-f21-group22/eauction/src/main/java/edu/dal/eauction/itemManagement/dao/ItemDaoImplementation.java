package edu.dal.eauction.itemManagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.dal.eauction.Dao.DBConnect;
import edu.dal.eauction.itemManagement.entities.*;

public class ItemDaoImplementation implements ItemDao{
	
	private static final String INSERT_ITEM = "INSERT INTO ITEMS (ITEM_NAME, BRAND_NAME, ITEM_DESCRIPTION, ITEM_PURCHASE_DATE, ITEM_EXPIRY_DATE, ITEM_MIN_PRICE, IMAGE_URL, USER_ID, ITEM_STATUS, ITEM_CATEGORY) VALUES (?,?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_ITEM = "SELECT ITEM_NAME, BRAND_NAME, ITEM_DESCRIPTION, ITEM_PURCHASE_DATE, ITEM_EXPIRY_DATE, ITEM_MIN_PRICE, IMAGE_URL, ITEM_STATUS, ITEM_CATEGORY FROM ITEMS WHERE ITEM_ID = ?";
	private static final String SELECT_ALL_ITEMS = "SELECT ITEM_ID, ITEM_NAME, BRAND_NAME, ITEM_DESCRIPTION, ITEM_PURCHASE_DATE, ITEM_EXPIRY_DATE, ITEM_MIN_PRICE, IMAGE_URL, ITEM_STATUS, ITEM_CATEGORY FROM ITEMS";
	private static final String SELECT_ALL_OTHER_ITEMS = "SELECT ITEM_ID, ITEM_NAME, BRAND_NAME, ITEM_DESCRIPTION, ITEM_PURCHASE_DATE, ITEM_EXPIRY_DATE, ITEM_MIN_PRICE, IMAGE_URL, ITEM_STATUS, ITEM_CATEGORY FROM ITEMS WHERE USER_ID <> ?";
	private static final String SELECT_ITEM_BY_CATEGORY = "SELECT ITEM_ID, ITEM_NAME, BRAND_NAME, ITEM_DESCRIPTION, ITEM_PURCHASE_DATE, ITEM_EXPIRY_DATE, ITEM_MIN_PRICE, IMAGE_URL, ITEM_STATUS, ITEM_CATEGORY FROM ITEMS WHERE ITEM_CATEGORY = ?";
	private static final String SELECT_ITEM_BY_USER = "SELECT ITEM_ID, ITEM_NAME, BRAND_NAME, ITEM_DESCRIPTION, ITEM_PURCHASE_DATE, ITEM_EXPIRY_DATE, ITEM_MIN_PRICE, IMAGE_URL, ITEM_STATUS, ITEM_CATEGORY FROM ITEMS WHERE USER_ID = ?";
	private static final String UPDATE_ITEM= "UPDATE ITEMS SET ITEM_NAME = ?, BRAND_NAME = ?, ITEM_DESCRIPTION = ?, ITEM_PURCHASE_DATE = ? , ITEM_EXPIRY_DATE = ?, ITEM_MIN_PRICE = ?, IMAGE_URL = ?, ITEM_STATUS = ?, ITEM_CATEGORY = ? WHERE ITEM_ID = ?";	
	private static final String DELETE_ITEM = "DELETE FROM ITEMS WHERE ITEM_ID =?";

	@Override
	public Integer create(Item item) {		
		DBConnect connect = DBConnect.getInstance();
		
		try(Connection connection = connect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ITEM);
				){
			
			preparedStatement.setString(1, item.getItemName());
			preparedStatement.setString(2, item.getBrandName());
			preparedStatement.setString(3, item.getDescription());
			preparedStatement.setObject(4, item.getPurchaseDate());
			preparedStatement.setObject(5, item.getExpiryDate());
			preparedStatement.setFloat(6, item.getMinPrice());
			preparedStatement.setString(7, item.getImageURL());
			preparedStatement.setInt(8, item.getUserID());
			preparedStatement.setString(9, item.getStatus().toString());
			preparedStatement.setString(10, item.getCategory().toString());
			
			preparedStatement.executeUpdate();
			System.out.println("Item Inserted");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Item read(Integer id) {
		Item item = null;		
		DBConnect connect = DBConnect.getInstance();
		
		try(Connection connection = connect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEM);
				){
			
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int itemID = id;
				String itemName=rs.getString("ITEM_NAME");
				String brandName=rs.getString("BRAND_NAME");
				String description=rs.getString("ITEM_DESCRIPTION");
				Date purchaseDate=rs.getDate("ITEM_PURCHASE_DATE");
				Date expiryDate=rs.getDate("ITEM_EXPIRY_DATE");
				float minPrice=rs.getFloat("ITEM_MIN_PRICE");
				String imageURL=rs.getString("IMAGE_URL");
				ItemStatus status=ItemStatus.valueOf(rs.getString("ITEM_STATUS"));
				ItemType category=ItemType.valueOf(rs.getString("ITEM_CATEGORY"));
				
				item = new Item(itemID, itemName, brandName, description, purchaseDate, expiryDate, minPrice, imageURL, status, category);				
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return item;
	}

	@Override
	public List<Item> read() {
		Item item = null;
		List<Item> items = new ArrayList<>();		
		DBConnect connect = DBConnect.getInstance();
		
		try(Connection connection = connect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ITEMS);
				){
						
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int itemID = rs.getInt("ITEM_ID");
				String itemName=rs.getString("ITEM_NAME");
				String brandName=rs.getString("BRAND_NAME");
				String description=rs.getString("ITEM_DESCRIPTION");
				Date purchaseDate=rs.getDate("ITEM_PURCHASE_DATE");
				Date expiryDate=rs.getDate("ITEM_EXPIRY_DATE");
				float minPrice=rs.getFloat("ITEM_MIN_PRICE");
				String imageURL=rs.getString("IMAGE_URL");
				ItemStatus status=ItemStatus.valueOf(rs.getString("ITEM_STATUS"));
				ItemType category=ItemType.valueOf(rs.getString("ITEM_CATEGORY"));
				
				item = new Item(itemID, itemName, brandName, description, purchaseDate, expiryDate, minPrice, imageURL, status, category);				
				items.add(item);				
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	@Override
	public List<Item> readOther(int userID) {
		Item item = null;
		List<Item> items = new ArrayList<>();		
		DBConnect connect = DBConnect.getInstance();
		
		try(Connection connection = connect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_OTHER_ITEMS);
				){
			
			preparedStatement.setInt(1, userID);			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int itemID = rs.getInt("ITEM_ID");
				String itemName=rs.getString("ITEM_NAME");
				String brandName=rs.getString("BRAND_NAME");
				String description=rs.getString("ITEM_DESCRIPTION");
				Date purchaseDate=rs.getDate("ITEM_PURCHASE_DATE");
				Date expiryDate=rs.getDate("ITEM_EXPIRY_DATE");
				float minPrice=rs.getFloat("ITEM_MIN_PRICE");
				String imageURL=rs.getString("IMAGE_URL");
				ItemStatus status=ItemStatus.valueOf(rs.getString("ITEM_STATUS"));
				ItemType category=ItemType.valueOf(rs.getString("ITEM_CATEGORY"));
				
				item = new Item(itemID, itemName, brandName, description, purchaseDate, expiryDate, minPrice, imageURL, status, category);				
				items.add(item);				
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;		
	}
	
	@Override
	public List<String> readCategories() {
		List<String> categories = new ArrayList<>();
	
		for (ItemType type : ItemType.values()) { 
		    categories.add(type.toString());
		}
		
		return categories;
	}
	
	@Override
	public List<Item> readByCategory(String search_category) {
		Item item = null;
		List<Item> items = new ArrayList<>();		
		DBConnect connect = DBConnect.getInstance();
		
		try(Connection connection = connect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEM_BY_CATEGORY);
				){
		
			preparedStatement.setString(1, search_category);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int itemID = rs.getInt("ITEM_ID");
				String itemName=rs.getString("ITEM_NAME");
				String brandName=rs.getString("BRAND_NAME");
				String description=rs.getString("ITEM_DESCRIPTION");
				Date purchaseDate=rs.getDate("ITEM_PURCHASE_DATE");
				Date expiryDate=rs.getDate("ITEM_EXPIRY_DATE");
				float minPrice=rs.getFloat("ITEM_MIN_PRICE");
				String imageURL=rs.getString("IMAGE_URL");
				ItemStatus status=ItemStatus.valueOf(rs.getString("ITEM_STATUS"));
				ItemType category=ItemType.valueOf(rs.getString("ITEM_CATEGORY"));
				
				item = new Item(itemID, itemName, brandName, description, purchaseDate, expiryDate, minPrice, imageURL, status, category);				
				items.add(item);				
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}

	@Override
	public List<Item> createdByUser(int userID) {
		Item item = null;
		List<Item> items = new ArrayList<>();
		DBConnect connect = DBConnect.getInstance();
		
		try(Connection connection = connect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEM_BY_USER);
				){
		
			preparedStatement.setInt(1, userID);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int itemID = rs.getInt("ITEM_ID");
				String itemName=rs.getString("ITEM_NAME");
				String brandName=rs.getString("BRAND_NAME");
				String description=rs.getString("ITEM_DESCRIPTION");
				Date purchaseDate=rs.getDate("ITEM_PURCHASE_DATE");
				Date expiryDate=rs.getDate("ITEM_EXPIRY_DATE");
				float minPrice=rs.getFloat("ITEM_MIN_PRICE");
				String imageURL=rs.getString("IMAGE_URL");
				ItemStatus status=ItemStatus.valueOf(rs.getString("ITEM_STATUS"));
				ItemType category=ItemType.valueOf(rs.getString("ITEM_CATEGORY"));
				
				item = new Item(itemID, itemName, brandName, description, purchaseDate, expiryDate, minPrice, imageURL, status, category);	
				items.add(item);				
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}

	@Override
	public void update(Item item) {		
		DBConnect connect = DBConnect.getInstance();
		
		try(Connection connection = connect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ITEM);
				){
			
			preparedStatement.setString(1, item.getItemName());
			preparedStatement.setString(2, item.getBrandName());
			preparedStatement.setString(3, item.getDescription());
			preparedStatement.setObject(4, item.getPurchaseDate());
			preparedStatement.setObject(5, item.getExpiryDate());
			preparedStatement.setFloat(6, item.getMinPrice());
			preparedStatement.setString(7, item.getImageURL());
			preparedStatement.setString(8, item.getStatus().toString());
			preparedStatement.setString(9, item.getCategory().toString());
			preparedStatement.setInt(10, item.getItemID());
			
			preparedStatement.executeUpdate();
			System.out.println("Item Updated");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(Integer id) throws SQLException {
		DBConnect connect = DBConnect.getInstance();

		try(Connection connection = connect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ITEM);
				){
			
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			System.out.println("Item Deleted");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
