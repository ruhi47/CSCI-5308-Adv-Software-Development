package edu.dal.eauction.itemManagement.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.dal.eauction.itemManagement.dao.ItemDao;
import edu.dal.eauction.itemManagement.dao.ItemDaoImplementation;
import edu.dal.eauction.itemManagement.entities.Item;
import edu.dal.eauction.userManagement.dao.UserDao;
import edu.dal.eauction.userManagement.dao.UserDaoImpl;
import edu.dal.eauction.userManagement.entities.User;

@Controller
public class ViewOtherItemsController {

	private static final Logger LOG = LogManager.getLogger();
	
	@RequestMapping(value = { "/viewOtherItems" })
	public String getOtherItems(@ModelAttribute(name="getItems") Item getItems,Model model) {
		LOG.info("Viewing Other Items");
		String username = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication.isAuthenticated()) {
			username = authentication.getPrincipal().toString();
		}
		
		UserDao userdao = new UserDaoImpl();
		User user = userdao.readByEmail(username);
		int userID = user.getId();
		ItemDao itemDaoImplement = new ItemDaoImplementation(); 
		List<Item> itemList = itemDaoImplement.readOther(userID);
		LOG.info("Total Items : "+itemList.size());
		model.addAttribute("itemList",itemList);
		return "viewOtherItems";
	}

}
