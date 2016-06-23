package com.movieapp.dao;

import com.adventnet.ds.query.DataSet;
import com.adventnet.persistence.Row;
import com.movieapp.bean.Ticket;
import com.movieapp.constants.DBConstants;

public class TicketDAOImpl extends ApplicationDAO<Ticket> 
{
	public static String TABLE_NAME="Ticket";
	
	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public Ticket getAsBean(Row row) 
	{
		Ticket ticket=new Ticket();
		ticket.setId((long)row.get(DBConstants.TICKET_ID));
		ticket.setCustomerID((long)row.get(DBConstants.TK_CUSTOMER_ID));
		ticket.setMovieShowID((long)row.get(DBConstants.TK_MS_ID));
		ticket.setTotalCost((float)row.get(DBConstants.TICKET_COST));
		return ticket;
	}

	@Override
	public void setFromBean(Row row, Ticket data) 
	{
		
	}

	@Override
	public Row getAsRow(Ticket data) 
	{
		Row row=new Row(TABLE_NAME);
		row.set(DBConstants.TICKET_COST, data.getTotalCost());
		row.set(DBConstants.TK_CUSTOMER_ID, data.getCustomerID());
		row.set(DBConstants.TK_MS_ID, data.getMovieShowID());
		return row;
	}

	@Override
	public String getIDColumnName() {
		return DBConstants.TICKET_ID;
	}

	@Override
	public Ticket getAsBean(DataSet dataSet) 
	{
		return null;
	}

}
