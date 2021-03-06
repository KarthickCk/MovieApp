package com.movieapp.bean;

public class Ticket {
	private long id;
	private long movieShowID;
	private long customerID;
	private float totalCost;
	
	public Ticket()
	{
		
	}
	
	public Ticket(long id,long movieShowID,long customerID,float totalCost)
	{
		this.id=id;
		this.movieShowID=movieShowID;
		this.customerID=customerID;
		this.totalCost=totalCost;
	}
	
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
	
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", movieShowID=" + movieShowID + ", customerID=" + customerID + ", totalCost="
				+ totalCost + "]";
	}
	
}
