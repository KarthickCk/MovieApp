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
import com.movieapp.bean.Category;
import com.movieapp.constants.DBConstants;

public class CategoryDAOImpl extends ApplicationDAO<Category> 
{
	public static String TABLE_NAME="Category";

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public Category getAsBean(Row row) 
	{
		Category category=new Category();
		category.setId((long)row.get(DBConstants.CATEGORY_ID));
		category.setCategoryName((String)row.get(DBConstants.CATEGORY_NAME));
		category.setFare((float)row.get(DBConstants.CATEGORY_FARE));
		return category;
	}

	@Override
	public void setFromBean(Row row, Category data) {

	}

	@Override
	public Row getAsRow(Category data) 
	{
		Row row=new Row(getTableName());
		row.set(DBConstants.CATEGORY_NAME, data.getCategoryName());
		row.set(DBConstants.CATEGORY_FARE, data.getFare());
		return row;
	}

	@Override
	public String getIDColumnName() {
		return DBConstants.CATEGORY_ID;
	}

	@Override
	public Category getAsBean(DataSet dataSet) 
	{
		Category category=new Category();
		try 
		{
			category.setId((long)dataSet.getValue(DBConstants.CATEGORY_ID));
			category.setCategoryName((String)dataSet.getValue(DBConstants.CATEGORY_NAME));
			category.setFare((float)dataSet.getValue(DBConstants.CATEGORY_FARE));
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}

	public boolean isCategoryDeletable(String id)
	{
		String tableName=SeatDAOImpl.TABLE_NAME;
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(tableName));
		Criteria criteria=new Criteria(Column.getColumn(tableName, DBConstants.SEAT_CATEGORY_ID),id, QueryConstants.EQUAL);
		selectQueryImpl.setCriteria(criteria);
		ArrayList<Column> columns=new ArrayList<>();
		columns.add(Column.getColumn(tableName,DBConstants.SEAT_ID));
		selectQueryImpl.addSelectColumns(columns);

		Row row=getRow(selectQueryImpl,tableName);
		if(row!=null)
		{
			return false;
		}
		return true;	
	}

}
