package com.movieapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.GroupByClause;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.persistence.Row;
import com.movieapp.bean.Screen;
import com.movieapp.constants.DBConstants;

public class ScreenDAOImpl extends ApplicationDAO<Screen> 
{
	public static String TABLE_NAME="Screen";

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public Screen getAsBean(Row row) 
	{
		Screen screen=new Screen();
		screen.setID((long)row.get(DBConstants.SCREEN_ID));
		screen.setScreenName((String)row.get(DBConstants.SCREEN_NAME));
		screen.setScreenRows((int)row.get(DBConstants.SCREEN_ROWS));
		screen.setScreenColumns((int)row.get(DBConstants.SCREEN_COLUMNS));
		return screen;
	}

	@Override
	public void setFromBean(Row row, Screen data) 
	{
		
	}

	@Override
	public Row getAsRow(Screen data) 
	{
		Row row=new Row(TABLE_NAME);
		row.set(DBConstants.SCREEN_NAME, data.getScreenName());
		row.set(DBConstants.SCREEN_ROWS, data.getScreenRows());
		row.set(DBConstants.SCREEN_COLUMNS, data.getScreenColumns());
		return row;
	}

	@Override
	public String getIDColumnName() 
	{
		return DBConstants.SCREEN_ID;
	}

	@Override
	public Screen getAsBean(DataSet dataSet) 
	{
		Screen screen=new Screen();
		try 
		{
			screen.setID((long)dataSet.getValue(DBConstants.SCREEN_ID));
			screen.setScreenName((String)dataSet.getValue(DBConstants.SCREEN_NAME));
			screen.setScreenRows((int)dataSet.getValue(DBConstants.SCREEN_ROWS));
			screen.setScreenColumns((int)dataSet.getValue(DBConstants.SCREEN_COLUMNS));
			return screen;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isScreenShowDeleteable(String screenID) 
	{
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(getTableName()));
		Criteria criteria=new Criteria(Column.getColumn(ShowSeatDAOImpl.TABLE_NAME, DBConstants.SS_TICKET_ID), "0", QueryConstants.NOT_EQUAL);
		Criteria screenCriteria=new Criteria(Column.getColumn(getTableName(), DBConstants.SCREEN_ID),screenID, QueryConstants.EQUAL);
		criteria=criteria.and(screenCriteria);
		ArrayList<Column> groupList=new ArrayList<>();
		groupList.add(Column.getColumn(ShowSeatDAOImpl.TABLE_NAME, DBConstants.SS_TICKET_ID));
		Join join=getJoinQuery(getTableName(),MovieShowDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.SCREEN_ID}, new String[]{DBConstants.MS_SCREEN_ID});
		Join showSeatJoin=getJoinQuery(MovieShowDAOImpl.TABLE_NAME,ShowSeatDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_ID}, new String[]{DBConstants.SS_MS_ID});
		selectQueryImpl.addJoin(join);
		selectQueryImpl.addJoin(showSeatJoin);
		selectQueryImpl.setCriteria(criteria);
		GroupByClause group=new GroupByClause(groupList);
		selectQueryImpl.setGroupByClause(group);
		ArrayList<Column> columns=new ArrayList<>();
		columns.add(Column.getColumn(getTableName(),DBConstants.SCREEN_ID));
		columns.add(Column.getColumn(ShowSeatDAOImpl.TABLE_NAME, DBConstants.SS_TICKET_ID).count());
		selectQueryImpl.addSelectColumns(columns);
		
		Row row=getRow(selectQueryImpl,getTableName());
		if(row!=null)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean isToAddScreen(String screenName) 
	{
		String tableName=getTableName();
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(tableName));
		ArrayList<Column> columns=new ArrayList<>();
		Criteria criteria=new Criteria(Column.getColumn(tableName, DBConstants.SCREEN_NAME), screenName, QueryConstants.EQUAL);
		columns.add(Column.getColumn(tableName, DBConstants.SCREEN_ID));
		selectQueryImpl.addSelectColumns(columns);
		selectQueryImpl.setCriteria(criteria);
		Row row=getRow(selectQueryImpl,tableName);
		if(row!=null)
		{
			return false;
		}
		
		return true;
	}

}
