package com.movieapp.bean;

public class Category 
{
	private long id;
	private String categoryName;
	private float fare;
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setFare(float fare) {
		this.fare = fare;
	}
	
	public float getFare() {
		return fare;
	}
}
