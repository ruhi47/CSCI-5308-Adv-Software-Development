package edu.dal.eauction.itemManagement.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ItemDto {

	public ItemDto() {
		super();
	}
	
	public ItemDto(int itemID, String itemName, String brandName, String description, Date purchaseDate,
			Date expiryDate, float minPrice, String imageURL, String status, String category) {
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
	private String status;
	private String category;
	
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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
		
	@Override
	public String toString() {
		return "ItemDto [itemID=" + itemID + ", itemName=" + itemName + ", brandName=" + brandName + ", description=" + description
				+ ", purchaseDate=" + purchaseDate + ", expiryDate=" + expiryDate + ", minPrice=" + minPrice
				+ ", imageURL=" + imageURL + ", status=" + status + ", category=" + category + "]";
	}
		
}
