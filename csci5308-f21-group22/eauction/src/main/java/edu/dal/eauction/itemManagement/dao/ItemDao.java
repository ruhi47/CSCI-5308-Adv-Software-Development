package edu.dal.eauction.itemManagement.dao;

import java.sql.SQLException;
import java.util.List;

import edu.dal.eauction.Dao.GenericDao;
import edu.dal.eauction.itemManagement.entities.Item;

public interface ItemDao extends GenericDao<Item, Integer> {
	
	Integer create(Item item);
	
	Item read(Integer id);
	
	List<Item> read();
	
	List<Item> readOther(int userID);
	
	List<String> readCategories();
	
	List<Item> readByCategory(String category);
	
	List<Item> createdByUser(int userID);
	
	void update(Item item);
	
	void delete(Integer id) throws SQLException;

}
