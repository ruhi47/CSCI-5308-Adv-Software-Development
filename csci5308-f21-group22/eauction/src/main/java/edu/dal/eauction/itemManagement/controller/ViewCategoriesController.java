package edu.dal.eauction.itemManagement.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.dal.eauction.itemManagement.dao.ItemDao;
import edu.dal.eauction.itemManagement.dao.ItemDaoImplementation;

@Controller
public class ViewCategoriesController {
	
	private static final Logger LOG = LogManager.getLogger();
	
	@RequestMapping(value = { "/viewCategories" })
	public String getCategories(@ModelAttribute(name="getCategories") String getCategory,Model model) {
		LOG.info("Viewing Categories");
		ItemDao itemDaoImplement = new ItemDaoImplementation(); 
		List<String> categoryList = itemDaoImplement.readCategories();
		LOG.info("Total Categories : "+categoryList.size());
		model.addAttribute("categoryList",categoryList);
		return "viewCategories";
	}

}
