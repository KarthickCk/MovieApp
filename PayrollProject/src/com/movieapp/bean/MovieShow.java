package com.movieapp.bean;

public class MovieShow 
{
	private long id;
	private long screenID;
	private long showID;
	private long movieID;
	private String movieDate;
	
	private int availableSeats; //Calculated

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public void setID(long id) {
		this.id = id;
	}
	
	public long getID() {
		return id;
	}
	
	public void setScreenID(long screenID) {
		this.screenID = screenID;
	}
	
	public long getScreenID() {
		return screenID;
	}
	
	public void setShowID(long showID) {
		this.showID = showID;
	}
	
	public long getShowID() {
		return showID;
	}
	
	public void setMovieID(long movieID) {
		this.movieID = movieID;
	}
	
	public long getMovieID() {
		return movieID;
	}
	
	public void setMovieDate(String movieDate) {
		this.movieDate = movieDate;
	}
	
	public String getMovieDate() {
		return movieDate;
	}

	@Override
	public String toString() {
		return "MovieShow [id=" + id + ", screenID=" + screenID + ", showID=" + showID + ", movieID=" + movieID
				+ ", movieDate=" + movieDate + ", availableSeats=" + availableSeats + "]";
	}
}
