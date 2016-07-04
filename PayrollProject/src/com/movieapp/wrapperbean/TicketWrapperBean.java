package com.movieapp.wrapperbean;

import com.movieapp.bean.Ticket;

public class TicketWrapperBean 
{
	private Ticket tickets;

	@Override
	public String toString() {
		return "TicketWrapperBean [tickets=" + tickets + "]";
	}

	public Ticket getTickets() {
		return tickets;
	}

	public void setTickets(Ticket tickets) {
		this.tickets = tickets;
	}
	
}
