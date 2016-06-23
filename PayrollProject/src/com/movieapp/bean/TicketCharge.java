package com.movieapp.bean;

public class TicketCharge {
	private long id;
	private long ticketID;
	private long extraID;
	private int quantity;

	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setTicketID(long ticketID) {
		this.ticketID = ticketID;
	}
	
	public long getTicketID() {
		return ticketID;
	}
	
	public void setExtraID(long extraID) {
		this.extraID = extraID;
	}
	
	public long getExtraID() {
		return extraID;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
}
