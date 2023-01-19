package edu.dal.eauction.itemManagement.service;

import edu.dal.eauction.itemManagement.entities.Item;
import edu.dal.eauction.userManagement.exception.UserAlreadyExistsException;

public interface IItemManagementService {
	
	boolean itemAddition(Item item) throws UserAlreadyExistsException;
	
	boolean itemUpdation(Item item) throws UserAlreadyExistsException;

}
