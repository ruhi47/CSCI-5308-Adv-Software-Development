package edu.dal.eauction.itemManagement.controller;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.dal.eauction.itemManagement.dao.ItemDao;
import edu.dal.eauction.itemManagement.dao.ItemDaoImplementation;

@Controller
public class DeleteItemController {
	
	private static final Logger LOG = LogManager.getLogger();
	
	@RequestMapping(value = { "/deleteItem/{id}" }, method=RequestMethod.GET)
	public String deleteItem(@PathVariable Integer id) throws SQLException {
		LOG.info("Deleting Item with ID= "+id);
		ItemDao itemDaoImplement = new ItemDaoImplementation(); 
		itemDaoImplement.delete(id);
		LOG.info("Item Deleted");
		return "viewAllItemsCreatedByYou";
	}

}
