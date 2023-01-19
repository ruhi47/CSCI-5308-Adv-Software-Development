package edu.dal.eauction.itemManagement.entities;

import java.util.Date;

import edu.dal.eauction.itemManagement.dto.*;

public class Item {
	
	public Item() {
		super();
	}
	
	public Item(ItemDto itemDto) {
		this.itemID = itemDto.getItemID();
		this.itemName = itemDto.getItemName();
		this.brandName = itemDto.getBrandName();
		this.description = itemDto.getDescription();
		this.purchaseDate = itemDto.getPurchaseDate();
		this.expiryDate = itemDto.getExpiryDate();
		this.minPrice = itemDto.getMinPrice();
		this.imageURL = itemDto.getImageURL();
		this.status = ItemStatus.valueOf(itemDto.getStatus());
		this.category = ItemType.valueOf(itemDto.getCategory());
	}
	
	public Item(int itemID, String itemName, String brandName, String description, Date purchaseDate, Date expiryDate,
			float minPrice, String imageURL, ItemStatus status, ItemType category) {
		super();
		this.itemID = itemID;
		this.itemName = itemName;
		this.brandName = brandName;
		this.description = description;
		this.purchaseDate = purchaseDate;
		this.expiryDate = expiryDate;
		this.minPrice = minPrice;
		this.imageURL = imageURL;
		this.status = status;
		this.category = category;
	}
	
	private int itemID;
	private String itemName;
	private String brandName;
	private String description;
	private Date purchaseDate;
	private Date expiryDate;
	private float minPrice;
	private String imageURL;
	private int userID;
	private String addedBy;
	private ItemStatus status;
	private ItemType category;
	
	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public float getMinPrice() {
		return minPrice;
	}
	
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}
	
	public String getImageURL() {
		return imageURL;
	}
	
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getAddedBy() {
		return addedBy;
	}
	
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	
	public ItemStatus getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = ItemStatus.valueOf(status);
	}
	
	public ItemType getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = ItemType.valueOf(category);
	}
	
	public boolean isActiveItem() {
		return ItemStatus.ACTIVE.equals(status);
	}
	
	@Override
	public String toString() {
		return "Item [itemID=" + itemID + ", itemName=" + itemName + ", brandName=" + brandName + ", description=" + description
				+ ", purchaseDate=" + purchaseDate + ", expiryDate=" + expiryDate + ", minPrice=" + minPrice
				+ ", userID=" + userID + ", imageURL=" + imageURL + ", status=" + status
				+ ", category=" + category + "]";
	}
		
}
