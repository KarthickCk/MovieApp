package com.movieapp.bean;

public class ShowSeat {
	private long id;
	private long ticketID;
	private long seatID;
	private long movieShowID;
	private boolean isAvailable;

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

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
	
	public void setSeatID(long seatID) {
		this.seatID = seatID;
	}
	
	public long getSeatID() {
		return seatID;
	}
	
	public void setMovieShowID(long movieShowID) {
		this.movieShowID = movieShowID;
	}
	
	public long getMovieShowID() {
		return movieShowID;
	}

	@Override
	public String toString() {
		return "ShowSeat [id=" + id + ", ticketID=" + ticketID + ", seatID=" + seatID + ", movieShowID=" + movieShowID
				+ ", isAvailable=" + isAvailable + "]";
	}
}
