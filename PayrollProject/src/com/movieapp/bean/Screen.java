package com.movieapp.bean;

public class Screen 
{
	private long id;
	private String screenName;
	private int screenRows;
	private int screenColumns;

	public void setID(long id) {
		this.id = id;
	}
	
	public long getID() {
		return id;
	}
	
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	public String getScreenName() {
		return screenName;
	}
	
	public void setScreenRows(int screenRows) {
		this.screenRows = screenRows;
	}
	
	public int getScreenRows() {
		return screenRows;
	}
	
	public void setScreenColumns(int screenColumns) {
		this.screenColumns = screenColumns;
	}
	
	public int getScreenColumns() {
		return screenColumns;
	}
}
