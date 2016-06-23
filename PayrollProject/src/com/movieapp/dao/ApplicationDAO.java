package com.movieapp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;

@SuppressWarnings("unchecked")
public abstract class ApplicationDAO <T>
{
	private ArrayList<String> criteriaColumnName;
	private ArrayList<String> criteriaValue;
	private ArrayList<String> updateColumn;
	private ArrayList<Object> updateValue;

	public ArrayList<String> getUpdateColumn() {
		return updateColumn;
	}
	
	public void setUpdateColumn(ArrayList<String> updateColumn) {
		this.updateColumn = updateColumn;
	}
	
	public ArrayList<Object> getUpdateValue() {
		return updateValue;
	}
	
	public void setUpdateValue(ArrayList<Object> updateValue) {
		this.updateValue = updateValue;
	}
	
	public abstract String getTableName();
	public abstract T getAsBean(Row row);
	public abstract T getAsBean(DataSet dataSet);
	public abstract void setFromBean(Row row, T data);
	public abstract Row getAsRow(T data);
	public abstract String  getIDColumnName();

	public  T insert(T data)
	{
		try 
		{
			Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
			DataObject dataObject = DataAccess.constructDataObject();
			Row rowsToInsert=getAsRow(data);
			dataObject.addRow(rowsToInsert);
			persistence.add(dataObject);
			Iterator<Row> iterator=dataObject.getRows(getTableName());
			while(iterator.hasNext())
			{
				Row row=iterator.next();
				long id=(long) row.get(getIDColumnName());
				return retrieveWithID(String.valueOf(id));
			}

		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		return data;
	}
	
	public  T update(String id)
	{
		try 
		{
			Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
			Criteria criteria=new Criteria(Column.getColumn(getTableName(), getIDColumnName()), id ,QueryConstants.EQUAL);  
			UpdateQuery updateQueryImpl=new UpdateQueryImpl(getTableName());
			updateQueryImpl.setCriteria(criteria);
			int updateColumnSize=updateColumn.size();
			for(int i=0;i<updateColumnSize;i++)
			{
				updateQueryImpl.setUpdateColumn(updateColumn.get(i), updateValue.get(i));
			}
			persistence.update(updateQueryImpl);
			return retrieveWithID(id);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;

	}

	public  void delete(String id)
	{
		try 
		{
			Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
			Criteria criteria=getCriteria();
			if(criteria==null)
			{
				criteria=new Criteria(Column.getColumn(getTableName(), getIDColumnName()), id ,QueryConstants.EQUAL);  
			}
			persistence.delete(criteria);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}

	public  T retrieveWithID(String condition)
	{ 
		Persistence persist;
		try 
		{
			Criteria criteria=new Criteria(new Column(getTableName(), getIDColumnName()),condition,QueryConstants.EQUAL);
			persist = (Persistence) BeanUtil.lookup("Persistence");
			DataObject dataObject = persist.get(getTableName(), criteria);
			Row row = dataObject.getRow(getTableName());
			T data=getAsBean(row);
			return data;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return null; 
	}
	
	public  T retrieveWithCriteria()
	{ 
		Persistence persist;
		try 
		{
			persist = (Persistence) BeanUtil.lookup("Persistence");
			DataObject dataObject = persist.get(getTableName(), getCriteria());
			Row row = dataObject.getRow(getTableName());
			T data=getAsBean(row);
			return data;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return null; 
	}

	public  List<T> retrieveAll()
	{ 
		ArrayList<T> objectList=new ArrayList<>();
		try 
		{

			Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
			DataObject dataObject = persistence.get(getTableName(), getCriteria());
			Iterator<Row> rows = dataObject.getRows(getTableName());

			while(rows.hasNext())
			{
				Row row = (Row)rows.next();
				T data=getAsBean(row);
				objectList.add(data);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		return objectList; 
	}

	public  T retrieveFirst(DataObject dobj)
	{ 
		try 
		{
			T t=(T) dobj.getRow(getTableName());
			return t;
		}
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return null; 
	}

	public List<Long> getDistinctIDs(String columnName)
	{
		ArrayList<Long> objectList=new ArrayList<>();
		RelationalAPI relapi = RelationalAPI.getInstance();
		Connection conn = null;
		DataSet ds = null;
		try 
		{

			conn = relapi.getConnection();
			SelectQuery squery = new SelectQueryImpl(Table.getTable( getTableName()));
			squery.setDistinct(true);
			squery.addSelectColumn(new Column(getTableName(),columnName));
			squery.setCriteria(getCriteria());
			ds = relapi.executeQuery(squery,conn);

			while(ds.next())
			{
				objectList.add((long)ds.getValue(columnName));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (ds != null)
			{
				try {
					ds.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn!=null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return objectList;

	}

	public void setCriteriaColumnNames(ArrayList<String> columnName)
	{
		criteriaColumnName=columnName;
	}

	public Criteria getCriteria()
	{
		Criteria criteria=null;
		if(criteriaValue!=null)
		{
			String tableName=getTableName();
			int length=criteriaColumnName.size();

			for(int i=0;i<length;i++)
			{
				Criteria criteriaValue=new Criteria(new Column(tableName,criteriaColumnName.get(i)),this.criteriaValue.get(i),QueryConstants.EQUAL);
				if(criteria==null)
				{
					criteria=criteriaValue;
					continue;
				}
				criteria=criteria.and(criteriaValue);
			}
		}
		return criteria;
	}

	public void setCriteriaValues(ArrayList<String> criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public Join getJoinQuery(String baseTable,String refTableName,int joinType,String[] baseColumns,String[] refColumns)
	{
		Join join=new Join(baseTable, refTableName,baseColumns,refColumns, joinType);
		return join;
	}

	public List<T> executeQuery(SelectQuery selectQuery)
	{
		ArrayList<T> objectList=new ArrayList<>();
		RelationalAPI relapi = RelationalAPI.getInstance();
		Connection conn = null;
		DataSet ds = null;
		try 
		{
			conn = relapi.getConnection();
			ds = relapi.executeQuery(selectQuery,conn);
			while(ds.next())
			{
				objectList.add(getAsBean(ds));
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (ds != null)
			{
				try {
					ds.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn!=null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return objectList;

	}

	public List<Row> getRows(SelectQuery selectQuery,String tableName)
	{
		ArrayList<Row> rowList=new ArrayList<>();
		try 
		{
			Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
			DataObject dataObject = persistence.get(selectQuery);
			Iterator<Row> rows = dataObject.getRows(tableName);

			while(rows.hasNext())
			{
				Row row = (Row)rows.next();
				rowList.add(row);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return rowList;

	}

	public Row getRow(SelectQuery selectQuery,String tableName)
	{
		try 
		{
			Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
			DataObject dataObject = persistence.get(selectQuery);
			return dataObject.getRow(tableName);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;

	}
}
