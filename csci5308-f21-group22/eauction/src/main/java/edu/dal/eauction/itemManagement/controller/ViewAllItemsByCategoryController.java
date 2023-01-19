package edu.dal.eauction.itemManagement.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.dal.eauction.itemManagement.dao.ItemDao;
import edu.dal.eauction.itemManagement.dao.ItemDaoImplementation;
import edu.dal.eauction.itemManagement.entities.Item;

@Controller
public class ViewAllItemsByCategoryController {
	
	private static final Logger LOG = LogManager.getLogger();
	
	@RequestMapping(value = { "/viewAllItemsByCategory/{category}" }, method=RequestMethod.GET)
	public String getAllItemsByCategory(@PathVariable String category, @ModelAttribute(name="getItems") Item getItems,Model model) {
		LOG.info("Viewing All Items By Category= "+ category);
		ItemDao itemDaoImplement = new ItemDaoImplementation(); 
		List<Item> itemList = itemDaoImplement.readByCategory(category);
		LOG.info("Total Items : "+itemList.size());
		model.addAttribute("itemList",itemList);
		return "viewAllItemsByCategory";
	}

}
