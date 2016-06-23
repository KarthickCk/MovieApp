package com.movieapp.bean;

public class Seat 
{
	private long id;
	private long screenID;
	private long categoryID;
	private String name;
	private boolean isDeleted;
	private long rowNumber;
	private long columnNumber;
	private boolean status;
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setScreenID(long screenID) {
		this.screenID = screenID;
	}
	
	public long getScreenID() {
		return screenID;
	}
	
	public void setCategoryID(long categoryID) {
		this.categoryID = categoryID;
	}
	
	public long getCategoryID() {
		return categoryID;
	}
	
	public void setRowNumber(long rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	public long getRowNumber() {
		return rowNumber;
	}
	
	public void setColumnNumber(long columnNumber) {
		this.columnNumber = columnNumber;
	}
	
	public long getColumnNumber() {
		return columnNumber;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean getStatus() {
		return status;
	}
	
}
