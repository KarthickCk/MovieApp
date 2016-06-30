package com.movieapp.dao;

import java.util.ArrayList;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.persistence.Row;
import com.movieapp.bean.Customer;
import com.movieapp.constants.DBConstants;

public class CustomerDAOImpl extends ApplicationDAO<Customer> 
{

	public static String TABLE_NAME="Customer";
	
	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public Customer getAsBean(Row row) 
	{
		Customer customer=new Customer();
		customer.setId((long)row.get(DBConstants.CUSTOMER_ID));
		customer.setName((String)row.get(DBConstants.CUSTOMER_NAME));
		customer.setEmail((String)row.get(DBConstants.CUSTOMER_EMAIL));
		customer.setPhoneNumber((String)row.get(DBConstants.CUSTOMER_PHONE));
		return customer;
	}

	@Override
	public void setFromBean(Row row, Customer data) 
	{

	}

	@Override
	public Row getAsRow(Customer data) 
	{
		Row row=new Row(getTableName());
		row.set(DBConstants.CUSTOMER_NAME, data.getName());
		row.set(DBConstants.CUSTOMER_EMAIL, data.getEmail());
		row.set(DBConstants.CUSTOMER_PHONE, data.getPhoneNumber());
		return row;
	}

	@Override
	public String getIDColumnName() {
		return DBConstants.CUSTOMER_ID;
	}

	@Override
	public Customer getAsBean(DataSet dataSet) 
	{
		return null;
	}
	
	public boolean isCustomerDeleteable(String id) 
	{
		String tableName=TicketDAOImpl.TABLE_NAME;
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(tableName));
		Criteria criteria=new Criteria(Column.getColumn(tableName, DBConstants.TK_CUSTOMER_ID),id, QueryConstants.EQUAL);
		selectQueryImpl.setCriteria(criteria);
		ArrayList<Column> columns=new ArrayList<>();
		columns.add(Column.getColumn(tableName,DBConstants.TICKET_ID));
		selectQueryImpl.addSelectColumns(columns);
		Row row=getRow(selectQueryImpl,tableName);
		if(row!=null)
		{
			return false;
		}
		return true;	
	}

}
