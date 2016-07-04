package com.movieapp.wrapperbean;

import com.movieapp.bean.ShowSeat;

public class ShowSeatWrapperBean 
{
	private ShowSeat showSeats;

	@Override
	public String toString() {
		return "ShowSeatWrapperBean [showSeats=" + showSeats + "]";
	}

	public ShowSeat getShowseats() {
		return showSeats;
	}

	public void setShowseats(ShowSeat showSeats) {
		this.showSeats = showSeats;
	}
	
	
}
