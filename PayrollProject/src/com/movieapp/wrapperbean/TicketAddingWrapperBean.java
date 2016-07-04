package com.movieapp.wrapperbean;

import java.util.ArrayList;
import java.util.List;

import com.movieapp.bean.Ticket;
import com.movieapp.bean.TicketCharge;

public class TicketAddingWrapperBean 
{
	private long id;
	private long movieShowID;
	private long customerID;
	private float totalCost;
	private List<String> showSeats=new ArrayList<>();
	private List<TicketCharge> ticketCharges=new ArrayList<>();
	
	public long getId() {
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
	public List<String> getShowseats() {
		return showSeats;
	}
	public void setShowseats(List<String> showSeats) {
		this.showSeats = showSeats;
	}
	
	public List<TicketCharge> getTicketcharges() {
		return ticketCharges;
	}
	
	public void setTicketcharges(List<TicketCharge> ticketCharges) {
		this.ticketCharges = ticketCharges;
	}
	
	public Ticket getTicket()
	{
		Ticket ticket1=new Ticket(id, movieShowID, customerID, totalCost);
		return ticket1;
	}

	@Override
	public String toString() {
		return "TicketAddingWrapperBean [id=" + id + ", movieShowID=" + movieShowID + ", customerID=" + customerID
				+ ", totalCost=" + totalCost + ", showSeats=" + showSeats + ", ticketCharges=" + ticketCharges + "]";
	}
	
}
