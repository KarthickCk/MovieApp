package com.movieapp.vo;

import java.util.ArrayList;

import com.movieapp.bean.Category;
import com.movieapp.bean.Seat;
import com.movieapp.bean.ShowSeat;

public class ShowSeatWrapperVO 
{
	private ArrayList<ShowSeat> showSeats=new ArrayList<>();
	private ArrayList<Seat> seats=new ArrayList<>();
	private ArrayList<Category> categories=new ArrayList<>();

	public ArrayList<Category> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	
	@Override
	public String toString() {
		return "ShowSeatWrapperVO [showSeats=" + showSeats + ", seats=" + seats + "]";
	}

	public ArrayList<ShowSeat> getShowSeats() {
		return showSeats;
	}
	
	public void setShowSeats(ArrayList<ShowSeat> showSeats) {
		this.showSeats = showSeats;
	}
	
	public ArrayList<Seat> getSeats() {
		return seats;
	}
	
	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}
}
