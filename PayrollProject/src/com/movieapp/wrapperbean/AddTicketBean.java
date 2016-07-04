package com.movieapp.wrapperbean;

public class AddTicketBean 
{
	private TicketAddingWrapperBean ticket;

	@Override
	public String toString() {
		return "AddTicketBean [ticket=" + ticket + "]";
	}

	public TicketAddingWrapperBean getTicket() {
		return ticket;
	}

	public void setTicket(TicketAddingWrapperBean ticket) {
		this.ticket = ticket;
	}
	
}
