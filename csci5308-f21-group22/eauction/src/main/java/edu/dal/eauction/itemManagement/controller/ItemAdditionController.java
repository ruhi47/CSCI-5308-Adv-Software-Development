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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.dal.eauction.itemManagement.dao.ItemDao;
import edu.dal.eauction.itemManagement.dao.ItemDaoImplementation;
import edu.dal.eauction.itemManagement.dto.ItemDto;
import edu.dal.eauction.itemManagement.validations.ItemDtoValidator;
import edu.dal.eauction.itemManagement.entities.Item;
import edu.dal.eauction.itemManagement.service.IItemManagementService;
import edu.dal.eauction.itemManagement.service.ItemManagementService;

@Controller
public class ItemAdditionController {
	
	private static final Logger LOG = LogManager.getLogger();	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private ItemDtoValidator itemDtoValidator = new ItemDtoValidator();
	private IItemManagementService itemAdditionService = new ItemManagementService();

	public ItemAdditionController() {
		super();
	}
	
	public ItemAdditionController(ItemDtoValidator itemDtoValidator,
			IItemManagementService itemAdditionService) {
		this.itemDtoValidator = itemDtoValidator;
		this.itemAdditionService = itemAdditionService;
	}

	@InitBinder
	public void binderAddItemFormData(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		binder.registerCustomEditor(Date.class, "purchaseDate", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "expiryDate", new CustomDateEditor(dateFormat, true));
	}
	
	@GetMapping(value = { "/addItems" })
	public String goToItemAdditionPage(@ModelAttribute(name="getCategories") String getCategory,Model model) {
		LOG.info("Routing to Item Addition Page");
		ItemDao itemDaoImplement = new ItemDaoImplementation(); 
		List<String> categoryList = itemDaoImplement.readCategories();
		LOG.info("Total options for Categories : "+categoryList.size());
		model.addAttribute("categoryList",categoryList);
		return "addItems";
	}

	@PostMapping("/items/add")
	public String itemAddition(@ModelAttribute("newItem") ItemDto newItem, Model model) {
		LOG.info("Adding Item : " + newItem.toString());
		newItem.setStatus("ACTIVE");
		String template = null;
		List<String> errors = itemDtoValidator.validateRequest(newItem);
		
		if (errors.isEmpty()) {
			template = processItemAddition(new Item(newItem), model);
		} else {
			model.addAttribute("message", errors);
			template = "addItems";
		}
		
		return template;
	}

	private String processItemAddition(Item item, Model model) {
		
		try {
			itemAdditionService.itemAddition(item);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "addItems";
		}
		
		model.addAttribute("message", "Item added successfully");
		return "userDashboard";
	}

}
