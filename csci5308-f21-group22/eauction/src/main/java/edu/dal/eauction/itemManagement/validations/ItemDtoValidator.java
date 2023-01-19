package edu.dal.eauction.itemManagement.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import org.springframework.stereotype.Component;

import edu.dal.eauction.itemManagement.dto.ItemDto;

public class ItemDtoValidator {
	
	private List<String> errors;
	
	public ItemDtoValidator() {
		this.errors = new ArrayList<>();
	}
	
	public List<String> validateRequest(ItemDto dto) {
		
		errors.clear();
		validateItemName(dto.getItemName());
		validateDescription(dto.getDescription());
		validateMinimumPrice(dto.getMinPrice());
		validateItemCategory(dto.getCategory());
		validateItemImages(dto.getImageURL());
		
		return errors;
	}
	
	
	public void validateItemName(String itemName) {
		if (isStringValueNullOrBlank(itemName)) {
			errors.add("Please enter the Item Name");
		} 
	}
	
	public void validateDescription(String description) {
		if (isStringValueNullOrBlank(description)) {
			errors.add("Please give a Description of the Item");
		} 
	}

	public void validateMinimumPrice(float minPrice) {
		String price = Float.toString(minPrice);
		if (isStringValueNullOrBlank(price)) {
			errors.add("Please enter the Minimum Price of Item");
		}
		else if (minPrice <= 0) {
			errors.add("Minimum Price of Item should be greater than 0");
		} 
	}
	
	public void validateItemCategory(String category) {
		if (isStringValueNullOrBlank(category)) {
			errors.add("Please select any one Item Category");
		} 
	}
	
	public void validateItemImages(String imageURL) {
		if (isStringValueNullOrBlank(imageURL)) {
			errors.add("Please attach an Image of the Item");
		} 
	}
	
	public boolean isStringValueNullOrBlank(String fieldValue) {
		boolean isEmpty = Objects.isNull(fieldValue);
		boolean isBlank = Objects.nonNull(fieldValue) ? fieldValue.isBlank() : false;
		return isBlank || isEmpty;
	}

}
