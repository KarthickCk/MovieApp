package com.movieapp.dao;

import java.sql.SQLException;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;
import com.movieapp.bean.ShowSeat;
import com.movieapp.constants.DBConstants;

public class ShowSeatDAOImpl extends ApplicationDAO<ShowSeat> 
{

	public static String TABLE_NAME="ShowSeat";

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public ShowSeat getAsBean(Row row) 
	{
		ShowSeat showSeat=new ShowSeat();
		showSeat.setId((long)row.get(DBConstants.SHOW_SEAT_ID));
		showSeat.setMovieShowID((long)row.get(DBConstants.SS_MS_ID));
		showSeat.setSeatID((long)row.get(DBConstants.SS_SEAT_ID));
		long ticketID=(long)row.get(DBConstants.SS_TICKET_ID);
		showSeat.setTicketID(ticketID);
		if(ticketID==0)
		{
			showSeat.setAvailable(true);
		}
		return showSeat;
	}

	@Override
	public void setFromBean(Row row, ShowSeat data) {

	}

	@Override
	public Row getAsRow(ShowSeat data) 
	{
		Row row=new Row(getTableName());
		row.set(DBConstants.SS_MS_ID, data.getMovieShowID());
		row.set(DBConstants.SS_SEAT_ID, data.getSeatID());
		row.set(DBConstants.SS_TICKET_ID, data.getTicketID());
		return row;
	}

	@Override
	public String getIDColumnName() {
		return DBConstants.SHOW_SEAT_ID;
	}

	@Override
	public ShowSeat getAsBean(DataSet dataSet) 
	{
		ShowSeat showSeat=new ShowSeat();
		try 
		{
			showSeat.setId((long)dataSet.getValue(DBConstants.SHOW_SEAT_ID));
			showSeat.setMovieShowID((long)dataSet.getValue(DBConstants.SS_MS_ID));
			showSeat.setSeatID((long)dataSet.getValue(DBConstants.SS_SEAT_ID));
			long ticketID=(long)dataSet.getValue(DBConstants.SS_TICKET_ID);
			showSeat.setTicketID(ticketID);
			if(ticketID==0)
			{
				showSeat.setAvailable(true);
			}
			return showSeat;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public int updateSeatCount(String ticketID) throws DataAccessException
	{
		
		try {
			Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
			SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(getTableName()));
			selectQueryImpl.setCriteria(getCriteria());
			selectQueryImpl.addSelectColumn(Column.getColumn(getTableName(), "*"));
			DataObject dataObject=DataAccess.get(selectQueryImpl);

			Row r = dataObject.getRow(getTableName());
			r.set(DBConstants.SS_TICKET_ID, ticketID);
			dataObject.updateRow(r);
			dataObject=persistence.update(dataObject);
			return dataObject.size(getTableName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}

}
