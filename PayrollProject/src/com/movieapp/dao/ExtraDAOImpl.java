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
import com.movieapp.bean.Extra;
import com.movieapp.constants.DBConstants;

public class ExtraDAOImpl extends ApplicationDAO<Extra> 
{

	public static String TABLE_NAME="Extra";
	
	@Override
	public String getTableName() 
	{
		return TABLE_NAME;
	}

	@Override
	public Extra getAsBean(Row row) 
	{
		Extra extra=new Extra();
		extra.setId((long)row.get(DBConstants.EXTRA_ID));
		extra.setName((String)row.get(DBConstants.EXTRA_NAME));
		extra.setCost((float)row.get(DBConstants.EXTRA_COST));
		return extra;
	}

	@Override
	public void setFromBean(Row row, Extra data) 
	{
		
	}

	@Override
	public Row getAsRow(Extra data) 
	{
		Row row=new Row(getTableName());
		row.set(DBConstants.EXTRA_NAME, data.getName());
		row.set(DBConstants.EXTRA_COST, data.getCost());
		return row;
	}

	@Override
	public String getIDColumnName() 
	{
		return DBConstants.EXTRA_ID;
	}

	@Override
	public Extra getAsBean(DataSet dataSet) 
	{
		Extra extra=new Extra();
		try 
		{
			extra.setId((long)dataSet.getValue(DBConstants.EXTRA_ID));
			extra.setName((String)dataSet.getValue(DBConstants.EXTRA_NAME));
			extra.setCost((float)dataSet.getValue(DBConstants.EXTRA_COST));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return extra;
	}
	
	public boolean isExtrasDeleteable(String extraID) 
	{
		String tableName=TicketChargeImpl.TABLE_NAME;
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(tableName));
		Criteria criteria=new Criteria(Column.getColumn(tableName, DBConstants.TC_EXTRA_ID),extraID, QueryConstants.EQUAL);
		selectQueryImpl.setCriteria(criteria);
		ArrayList<Column> columns=new ArrayList<>();
		columns.add(Column.getColumn(tableName,DBConstants.TICKET_CHARGE_ID));
		selectQueryImpl.addSelectColumns(columns);
		Row row=getRow(selectQueryImpl,tableName);
		if(row!=null)
		{
			return false;
		}
		return true;	
	}

}
