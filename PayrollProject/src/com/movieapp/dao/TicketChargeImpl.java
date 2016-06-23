package com.movieapp.dao;

import com.adventnet.ds.query.DataSet;
import com.adventnet.persistence.Row;
import com.movieapp.bean.TicketCharge;
import com.movieapp.constants.DBConstants;

public class TicketChargeImpl extends ApplicationDAO<TicketCharge> 
{
	public static String TABLE_NAME="TicketCharge";
	
	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public TicketCharge getAsBean(Row row) 
	{
		TicketCharge ticketCharge=new TicketCharge();
		ticketCharge.setId((long)row.get(DBConstants.TICKET_CHARGE_ID));
		ticketCharge.setTicketID((long)row.get(DBConstants.TC_TICKET_ID));
		ticketCharge.setExtraID((long)row.get(DBConstants.TC_EXTRA_ID));
		ticketCharge.setQuantity((int)row.get(DBConstants.TC_QUANTITY));
		return ticketCharge;
	}

	@Override
	public void setFromBean(Row row, TicketCharge data) {
		
	}

	@Override
	public Row getAsRow(TicketCharge data) 
	{
		Row row=new Row(getTableName());
		row.set(DBConstants.TC_TICKET_ID, data.getTicketID());
		row.set(DBConstants.TC_EXTRA_ID, data.getExtraID());
		row.set(DBConstants.TC_QUANTITY, data.getQuantity());
		return row;
	}

	@Override
	public String getIDColumnName() 
	{
		return null;
	}

	@Override
	public TicketCharge getAsBean(DataSet dataSet) {
		// TODO Auto-generated method stub
		return null;
	}
}
