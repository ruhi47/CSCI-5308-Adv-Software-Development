package edu.dal.eauction.itemManagement.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.dal.eauction.itemManagement.dao.ItemDao;
import edu.dal.eauction.itemManagement.dao.ItemDaoImplementation;
import edu.dal.eauction.itemManagement.dto.ItemDto;
import edu.dal.eauction.itemManagement.entities.Item;
import edu.dal.eauction.itemManagement.service.IItemManagementService;
import edu.dal.eauction.itemManagement.service.ItemManagementService;
import edu.dal.eauction.itemManagement.validations.ItemDtoValidator;

@Controller
public class UpdateItemController {
	
	private static final Logger LOG = LogManager.getLogger();
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private ItemDtoValidator itemDtoValidator = new ItemDtoValidator();
	private IItemManagementService itemUpdationService = new ItemManagementService();

	public UpdateItemController() {
		super();
	}
	
	public UpdateItemController(ItemDtoValidator itemDtoValidator,
			IItemManagementService itemUpdationService) {
		this.itemDtoValidator = itemDtoValidator;
		this.itemUpdationService = itemUpdationService;
	}

	@InitBinder
	public void binderUpdateItemFormData(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		binder.registerCustomEditor(Date.class, "purchaseDate", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "expiryDate", new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = { "/updateItem/{id}" }, method=RequestMethod.GET)
	public String goToItemUpdationPage(@PathVariable Integer id, @ModelAttribute(name="getCategories") String getCategory,Model model) {
		LOG.info("Routing to Item Updation Page");
		ItemDao itemDaoImplement = new ItemDaoImplementation(); 
		List<String> categoryList = itemDaoImplement.readCategories();
		LOG.info("Total options for Categories : "+categoryList.size());
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("id",id);
		return "updateItems";
	}
	
	@PostMapping("/items/update/{id}")
	public String itemUpdation(@PathVariable Integer id, @ModelAttribute("newItem") ItemDto newItem, Model model) {
		LOG.info("Updating Item ID: " + id + " with details= " + newItem.toString());
		newItem.setStatus("ACTIVE");
		newItem.setItemID(id);
		String template = null;
		List<String> errors = itemDtoValidator.validateRequest(newItem);
		
		if (errors.isEmpty()) {
			template = processItemUpdation(new Item(newItem), model);
		} else {
			model.addAttribute("message", errors);
			template = "updateItems";
		}
		
		return template;
	}

	private String processItemUpdation(Item item, Model model) {
		
		try {
			itemUpdationService.itemUpdation(item);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "updateItems";
		}
		
		model.addAttribute("message", "Item updated successfully");
		return "viewAllItemsCreatedByYou";
	}

}
