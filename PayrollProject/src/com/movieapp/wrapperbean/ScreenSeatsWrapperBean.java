package com.movieapp.wrapperbean;

import java.util.List;

import com.movieapp.bean.Screen;
import com.movieapp.bean.Seat;

public class ScreenSeatsWrapperBean 
{
	private long id;
	private String screenName;
	private int screenRows;
	private int screenColumns;
	private List<Seat> seats;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getScreenName() {
		return screenName;
	}
	
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	public int getScreenRows() {
		return screenRows;
	}
	
	public void setScreenRows(int screenRows) {
		this.screenRows = screenRows;
	}
	
	public int getScreenColumns() {
		return screenColumns;
	}
	
	public void setScreenColumns(int screenColumns) {
		this.screenColumns = screenColumns;
	}
	
	public List<Seat> getSeats() {
		return seats;
	}
	
	public void setSeats(List<Seat> seats) 
	{
		this.seats = seats;
	}
	
	public Screen getScreen()
	{
		Screen screen=new Screen(id, screenRows, screenColumns, screenName);
		return screen;
	}

	@Override
	public String toString() {
		return "ScreenSeatsWrapperBean [id=" + id + ", screenName=" + screenName + ", screenRows=" + screenRows
				+ ", screenColumns=" + screenColumns + ", seats=" + seats + "]";
	}
	
	

}
