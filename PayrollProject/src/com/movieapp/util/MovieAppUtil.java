package com.movieapp.util;

import java.io.IOException;
import java.util.ArrayList;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum MovieAppUtil 
{
	INSTANCES;
	public Join getJoinQuery(String baseTable,String refTableName,int joinType,String[] baseColumns,String[] refColumns)
	{
		Join join=new Join(baseTable, refTableName,baseColumns,refColumns, joinType);
		return join;
	}

	public Criteria getCriteria(String tableName,ArrayList<String> criteriaColumnName,ArrayList<String> criteriaValue)
	{
		Criteria criteria=null;
		int length=criteriaColumnName.size();
		for(int i=0;i<length;i++)
		{
			Criteria andCriteria=new Criteria(new Column(tableName,criteriaColumnName.get(i)),criteriaValue.get(i),QueryConstants.EQUAL);
			if(criteria==null)
			{
				criteria=andCriteria;
				continue;
			}
			criteria=criteria.and(andCriteria);
		}
		return criteria;
	}
	
	public Object getObject(String json,Class classValue)
	{
		try 
		{
			Object object=new ObjectMapper().readValue(json, classValue);
			return object;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
