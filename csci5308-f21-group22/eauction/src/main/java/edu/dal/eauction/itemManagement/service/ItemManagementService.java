package edu.dal.eauction.itemManagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.dal.eauction.itemManagement.dao.*;
import edu.dal.eauction.itemManagement.entities.Item;
import edu.dal.eauction.userManagement.dao.UserDao;
import edu.dal.eauction.userManagement.dao.UserDaoImpl;
import edu.dal.eauction.userManagement.entities.User;
import edu.dal.eauction.userManagement.exception.UserAlreadyExistsException;

public class ItemManagementService implements IItemManagementService {
	
	private static final Logger log = LogManager.getLogger();
	
	public ItemManagementService() {
		super();
	}
	
	@Override
	//Adding an Item
	public boolean itemAddition(Item item) throws UserAlreadyExistsException {
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication.isAuthenticated()) {
			username = authentication.getPrincipal().toString();
		}
		
		UserDao userdao = new UserDaoImpl();
		User user = userdao.readByEmail(username);
		int userID = user.getId();
		item.setUserID(userID);
		log.info("Adding an Item : " + item.toString());
		ItemDao itemDaoImplement = new ItemDaoImplementation(); 
		itemDaoImplement.create(item);
		return true;
	}
	
	@Override
	//Updating an Item
	public boolean itemUpdation(Item item) throws UserAlreadyExistsException {
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication.isAuthenticated()) {
			username = authentication.getPrincipal().toString();
		}
		
		UserDao userdao = new UserDaoImpl();
		User user = userdao.readByEmail(username);
		int userID = user.getId();
		item.setUserID(userID);
		log.info("Updating an Item : " + item.toString());
		ItemDao itemDaoImplement = new ItemDaoImplementation(); 
		itemDaoImplement.update(item);
		return true;
	}

}
