package edu.dal.eauction.itemManagement.controller;

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
public class ViewItemDetailsController {
	
	private static final Logger LOG = LogManager.getLogger();
	
	@RequestMapping(value = { "/selectItem/{id}" }, method=RequestMethod.GET)
	public String getItemDetails(@PathVariable Integer id, @ModelAttribute(name="getItem") Item getItem,Model model) {
		LOG.info("Viewing Details of Item with ID= "+id);
		ItemDao itemDaoImplement = new ItemDaoImplementation(); 
		Item item= itemDaoImplement.read(id);
		LOG.info("Details Fetched");
		model.addAttribute("item", item);
		return "viewItemDetails";
	}
	
}
