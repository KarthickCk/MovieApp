package com.movieapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.persistence.Row;
import com.movieapp.bean.ShowDetail;
import com.movieapp.constants.DBConstants;

public class ShowsDAOImpl extends ApplicationDAO<ShowDetail>  
{
	public static String TABLE_NAME="ShowDetail";

	@Override
	public String getTableName() 
	{
		return TABLE_NAME;
	}
	
	@Override
	public void setFromBean(Row row, ShowDetail data) 
	{
		
	}

	@Override
	public Row getAsRow(ShowDetail data) 
	{
		Row row=new Row(TABLE_NAME);
		row.set(DBConstants.SHOW_NAME, data.getShowName());
		row.set(DBConstants.SHOW_START_TIME, data.getStartTime());
		row.set(DBConstants.SHOW_END_TIME, data.getEndTime());
		return row;
	}

	@Override
	public ShowDetail getAsBean(Row row) 
	{
		ShowDetail showDetail=new ShowDetail();
		showDetail.setID((long)row.get(DBConstants.SHOW_ID));
		showDetail.setShowName((String)row.get(DBConstants.SHOW_NAME));
		showDetail.setStartTime((String)row.get(DBConstants.SHOW_START_TIME));
		showDetail.setEndTime((String)row.get(DBConstants.SHOW_END_TIME));
		return showDetail;
	}

	@Override
	public String getIDColumnName() {
		return DBConstants.SHOW_ID;
	}

	@Override
	public ShowDetail getAsBean(DataSet dataSet) 
	{
		ShowDetail showDetail=new ShowDetail();
		try 
		{
			showDetail.setID((long)dataSet.getValue(DBConstants.SHOW_ID));
			showDetail.setShowName((String)dataSet.getValue(DBConstants.SHOW_NAME));
			showDetail.setStartTime((String)dataSet.getValue(DBConstants.SHOW_START_TIME));
			showDetail.setEndTime((String)dataSet.getValue(DBConstants.SHOW_END_TIME));
			return showDetail;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public boolean isShowDeleteable(String showID) 
	{
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(MovieShowDAOImpl.TABLE_NAME));
		Criteria criteria=new Criteria(Column.getColumn(ShowSeatDAOImpl.TABLE_NAME, DBConstants.SS_TICKET_ID), "0", QueryConstants.NOT_EQUAL);
		Criteria screenCriteria=new Criteria(Column.getColumn(MovieShowDAOImpl.TABLE_NAME, DBConstants.MS_SHOW_ID),showID, QueryConstants.EQUAL);
		criteria=criteria.and(screenCriteria);
		//Join join=getJoinQuery(getTableName(),MovieShowDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.SHOW_ID}, new String[]{DBConstants.MS_SHOW_ID});
		Join showSeatJoin=getJoinQuery(MovieShowDAOImpl.TABLE_NAME,ShowSeatDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_ID}, new String[]{DBConstants.SS_MS_ID});
		//selectQueryImpl.addJoin(join);
		selectQueryImpl.addJoin(showSeatJoin);
		selectQueryImpl.setCriteria(criteria);
		
		ArrayList<Column> columns=new ArrayList<>();
		columns.add(Column.getColumn(MovieShowDAOImpl.TABLE_NAME, DBConstants.MS_MOVIE_ID));
		selectQueryImpl.addSelectColumns(columns);
		
		Row row=getRow(selectQueryImpl,getTableName());
		if(row!=null)
		{
			return false;
		}
		
		return true;
	}

}
