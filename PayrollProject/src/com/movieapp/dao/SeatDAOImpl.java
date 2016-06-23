package com.movieapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.persistence.Row;
import com.movieapp.bean.Seat;
import com.movieapp.constants.DBConstants;

public class SeatDAOImpl extends ApplicationDAO<Seat> 
{

	public static String TABLE_NAME="Seat";
	
	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public Seat getAsBean(Row row) {
		Seat seat=new Seat();
		seat.setId((long)row.get(DBConstants.SEAT_ID));
		seat.setScreenID((long)row.get(DBConstants.SEAT_SCREEN_ID));
		seat.setCategoryID((long)row.get(DBConstants.SEAT_CATEGORY_ID));
		seat.setName((String)row.get(DBConstants.SEAT_NAME));
		seat.setRowNumber((long)row.get(DBConstants.SEAT_ROW));
		seat.setColumnNumber((long)row.get(DBConstants.SEAT_COLUMN));
		seat.setStatus((boolean)row.get(DBConstants.SEAT_STATUS));
		return seat;
	}

	@Override
	public void setFromBean(Row row, Seat data) {
		
	}

	@Override
	public Row getAsRow(Seat data) 
	{
		Row row=new Row(getTableName());
		row.set(DBConstants.SEAT_SCREEN_ID,data.getScreenID());
		row.set(DBConstants.SEAT_CATEGORY_ID,data.getCategoryID());
		row.set(DBConstants.SEAT_NAME,data.getName());
		row.set(DBConstants.SEAT_ROW,data.getRowNumber());
		row.set(DBConstants.SEAT_COLUMN,data.getColumnNumber());
		row.set(DBConstants.SEAT_STATUS,data.getStatus());

		return row;
	}

	@Override
	public String getIDColumnName() 
	{
		return DBConstants.SEAT_ID;
	}

	@Override
	public Seat getAsBean(DataSet dataSet) 
	{
		Seat seat=new Seat();
		try 
		{
			seat.setId((long)dataSet.getValue(DBConstants.SEAT_ID));
			seat.setName((String)dataSet.getValue(DBConstants.SEAT_NAME));
			seat.setCategoryID((long)dataSet.getValue(DBConstants.SEAT_CATEGORY_ID));
			seat.setScreenID((long)dataSet.getValue(DBConstants.SEAT_SCREEN_ID));
			seat.setRowNumber((long)dataSet.getValue(DBConstants.SEAT_ROW));
			seat.setColumnNumber((long)dataSet.getValue(DBConstants.SEAT_COLUMN));
			seat.setStatus((boolean)dataSet.getValue(DBConstants.SEAT_STATUS));
			return seat;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isSeatDeleteable(String seatID)
	{
		String tableName=ShowSeatDAOImpl.TABLE_NAME;
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(tableName));
		ArrayList<Column> columns=new ArrayList<>();
		Criteria criteria=new Criteria(Column.getColumn(tableName, DBConstants.SS_TICKET_ID), "0", QueryConstants.NOT_EQUAL);
		Criteria seatCriteria=new Criteria(Column.getColumn(tableName, DBConstants.SS_SEAT_ID), seatID, QueryConstants.EQUAL);
		criteria=criteria.and(seatCriteria);
		columns.add(Column.getColumn(tableName, DBConstants.SHOW_SEAT_ID));
		selectQueryImpl.addSelectColumns(columns);
		selectQueryImpl.setCriteria(criteria);
		Row row=getRow(selectQueryImpl,tableName);
		if(row!=null)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean isSeatUpdate(String screenID)
	{
		return false;
	}

}
