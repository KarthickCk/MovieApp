package com.movieapp.vo;

import java.util.ArrayList;

import com.movieapp.bean.Category;
import com.movieapp.bean.Screen;
import com.movieapp.bean.Seat;

public class ScreenSeatsVO 
{
	private ArrayList<Screen> screens=new ArrayList<>();
	private ArrayList<Seat> seats=new ArrayList<>();
	private ArrayList<Category> categorys=new ArrayList<>();

	public ArrayList<Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(ArrayList<Category> categorys) {
		this.categorys = categorys;
	}

	public ArrayList<Screen> getScreens() 
	{
		return screens;
	}
	
	public void setScreens(ArrayList<Screen> screens) 
	{
		this.screens = screens;
	}
	public ArrayList<Seat> getSeats() 
	{
		return seats;
	}
	
	public void setSeats(ArrayList<Seat> seats) 
	{
		this.seats = seats;
	}
}
