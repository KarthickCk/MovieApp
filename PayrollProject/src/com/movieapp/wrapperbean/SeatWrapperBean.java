package com.movieapp.wrapperbean;

import com.movieapp.bean.Seat;

public class SeatWrapperBean 
{
	private Seat seat;

	@Override
	public String toString() {
		return "SeatWrapperBean [seat=" + seat + "]";
	}

	public Seat getSeats() {
		return seat;
	}

	public void setSeats(Seat seat) {
		this.seat = seat;
	}
	
}
