package com.movieapp.wrapperbean;

import java.util.ArrayList;
import java.util.List;

public class TicketBookingWrapperBean 
{
	private long id;
	private long movieShowID;
	private long customerID;
	private float totalCost;
	private List<Long> seats=new ArrayList<>();
	private List<Long> ticketCharges=new ArrayList<>();
	
	public long getId() 
	{
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMovieShowID() {
		return movieShowID;
	}

	public void setMovieShowID(long movieShowID) {
		this.movieShowID = movieShowID;
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public List<Long> getSeats() {
		return seats;
	}

	public void setSeats(List<Long> seats) {
		this.seats = seats;
	}

	public List<Long> getTicketCharges() {
		return ticketCharges;
	}

	public void setTicketCharges(List<Long> ticketCharges) {
		this.ticketCharges = ticketCharges;
	}
	
}
