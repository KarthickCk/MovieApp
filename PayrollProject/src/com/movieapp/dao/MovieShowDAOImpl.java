package com.movieapp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.GroupByClause;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.persistence.Row;
import com.movieapp.bean.MovieShow;
import com.movieapp.constants.DBConstants;
import com.movieapp.vo.MovieShowWrapperVO;

public class MovieShowDAOImpl extends ApplicationDAO<MovieShow> 
{

	public static String TABLE_NAME="MovieShow";

	@Override
	public String getTableName() 
	{
		return TABLE_NAME;
	}

	@Override
	public MovieShow getAsBean(Row row) 
	{
		MovieShow movieShow=new MovieShow();
		long movieShowID=(long)row.get(DBConstants.MS_ID);
		movieShow.setID(movieShowID);
		movieShow.setMovieID((long)row.get(DBConstants.MS_MOVIE_ID));
		movieShow.setScreenID((long)row.get(DBConstants.MS_SCREEN_ID));
		movieShow.setShowID((long)row.get(DBConstants.MS_SHOW_ID));
		movieShow.setMovieDate((String)row.get(DBConstants.MS_DATE));
		return movieShow;
	}

	@Override
	public void setFromBean(Row row, MovieShow data) 
	{
		
	}

	@Override
	public Row getAsRow(MovieShow data) 
	{
		Row row=new Row(getTableName());
		row.set(DBConstants.MS_MOVIE_ID, data.getMovieID());
		row.set(DBConstants.MS_SCREEN_ID, data.getScreenID());
		row.set(DBConstants.MS_SHOW_ID, data.getShowID());
		row.set(DBConstants.MS_DATE, data.getMovieDate());
		return row;
	}

	@Override
	public String getIDColumnName() 
	{
		return DBConstants.MS_ID;
	}

	@Override
	public MovieShow getAsBean(DataSet dataSet) 
	{
		MovieShow movieShow=new MovieShow();
		long movieShowID;
		try 
		{
			movieShowID = (long)dataSet.getValue(DBConstants.MS_ID);
			movieShow.setID(movieShowID);
			movieShow.setMovieID((long)dataSet.getValue(DBConstants.MS_MOVIE_ID));
			movieShow.setScreenID((long)dataSet.getValue(DBConstants.MS_SCREEN_ID));
			movieShow.setShowID((long)dataSet.getValue(DBConstants.MS_SHOW_ID));
			movieShow.setMovieDate((String)dataSet.getValue(DBConstants.MS_DATE));
			movieShow.setAvailableSeats((int)dataSet.getValue(24));

			return movieShow;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public MovieShowWrapperVO getMovieShowListSelectQuery() 
	{
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(getTableName()));
		Criteria criteria=new Criteria(Column.getColumn(ShowSeatDAOImpl.TABLE_NAME, DBConstants.SS_TICKET_ID), "0", QueryConstants.EQUAL);
		Criteria movieShowCriteria=getCriteria();
		if(movieShowCriteria!=null)
		{
			criteria=movieShowCriteria.and(criteria);
		}
		ArrayList<Column> groupList=new ArrayList<>();
		groupList.add(Column.getColumn(getTableName(), DBConstants.MS_ID));
		Join join=getJoinQuery(getTableName(),ShowSeatDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_ID}, new String[]{DBConstants.SS_MS_ID});
		selectQueryImpl.addJoin(join);
		Join movieJoin=getJoinQuery(getTableName(),MovieDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_MOVIE_ID}, new String[]{DBConstants.MOVIE_ID});
		selectQueryImpl.addJoin(movieJoin);
		Join screenJoin=getJoinQuery(getTableName(),ScreenDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_SCREEN_ID}, new String[]{DBConstants.SCREEN_ID});
		selectQueryImpl.addJoin(screenJoin);
		Join showJoin=getJoinQuery(getTableName(),ShowsDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_SHOW_ID}, new String[]{DBConstants.SHOW_ID});
		selectQueryImpl.addJoin(showJoin);
		selectQueryImpl.setCriteria(criteria);
		GroupByClause group=new GroupByClause(groupList);
		selectQueryImpl.setGroupByClause(group);
		ArrayList<Column> columns=new ArrayList<>();
		columns.add(Column.getColumn(getTableName(),"*"));
		columns.add(Column.getColumn(MovieDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(ScreenDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(ShowsDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(ShowSeatDAOImpl.TABLE_NAME, DBConstants.SS_TICKET_ID).count());
		selectQueryImpl.addSelectColumns(columns);
		MovieShowWrapperVO msdetails = new MovieShowWrapperVO();
		addMovieShowList(selectQueryImpl, msdetails);
		addMovieList(selectQueryImpl, msdetails);
		addScreenList(selectQueryImpl, msdetails);
		addShowList(selectQueryImpl, msdetails);
		return msdetails;
	}

	public boolean isMovieShowDeleteable(String movieShowID) 
	{
		String tableName=ShowSeatDAOImpl.TABLE_NAME;
		ArrayList<Column> groupList=new ArrayList<>();
		groupList.add(Column.getColumn(tableName, DBConstants.SS_TICKET_ID));
		GroupByClause group=new GroupByClause(groupList);

		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(tableName));
		ArrayList<Column> columns=new ArrayList<>();
		Criteria criteria=new Criteria(Column.getColumn(tableName, DBConstants.SS_TICKET_ID), "0", QueryConstants.NOT_EQUAL);
		Criteria msCriteria=new Criteria(Column.getColumn(tableName, DBConstants.SS_MS_ID), movieShowID, QueryConstants.EQUAL);
		criteria=criteria.and(msCriteria);
		columns.add(Column.getColumn(tableName, DBConstants.SHOW_SEAT_ID));
		columns.add(Column.getColumn(tableName, DBConstants.SS_TICKET_ID).count());
		selectQueryImpl.addSelectColumns(columns);
		selectQueryImpl.setCriteria(criteria);
		selectQueryImpl.setGroupByClause(group);
		Row row=getRow(selectQueryImpl,tableName);
		if(row!=null)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean isToAddMovieShow(MovieShow movieShow) 
	{
		String tableName=getTableName();
		String movieID=String.valueOf(movieShow.getMovieID());
		String screenID=String.valueOf(movieShow.getScreenID());
		String showID=String.valueOf(movieShow.getShowID());
		String date=movieShow.getMovieDate();
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(tableName));
		ArrayList<Column> columns=new ArrayList<>();
		Criteria criteria=new Criteria(Column.getColumn(tableName, DBConstants.MS_MOVIE_ID), movieID, QueryConstants.EQUAL);
		Criteria screenCriteria=new Criteria(Column.getColumn(tableName, DBConstants.MS_SCREEN_ID), screenID, QueryConstants.EQUAL);
		Criteria showCriteria=new Criteria(Column.getColumn(tableName, DBConstants.MS_SHOW_ID), showID, QueryConstants.EQUAL);
		Criteria dateCriteria=new Criteria(Column.getColumn(tableName, DBConstants.MS_DATE), date, QueryConstants.EQUAL);
		criteria=criteria.and(screenCriteria).and(showCriteria).and(dateCriteria);
		columns.add(Column.getColumn(tableName, DBConstants.MS_ID));
		selectQueryImpl.addSelectColumns(columns);
		selectQueryImpl.setCriteria(criteria);
		Row row=getRow(selectQueryImpl,tableName);
		if(row!=null)
		{
			return false;
		}
		
		return true;
	}
	
	private void addMovieShowList(SelectQueryImpl selectQueryImpl, MovieShowWrapperVO msdetails)
	{
		msdetails.setMovieShowList( (ArrayList<MovieShow>) executeQuery(selectQueryImpl) );
	}
	
	private void addMovieList(SelectQueryImpl selectQueryImpl, MovieShowWrapperVO msdetails)
	{
		MovieDAOImpl movieDAOImpl=new MovieDAOImpl();
		List<Row> movieList=getRows(selectQueryImpl, movieDAOImpl.getTableName());
		int size=movieList.size();
		for(int i=0;i<size;i++)
		{
			msdetails.getMovieList().add(movieDAOImpl.getAsBean(movieList.get(i)));
		}
	}
	
	private void addShowList(SelectQueryImpl selectQueryImpl, MovieShowWrapperVO msdetails)
	{
		ShowsDAOImpl showSeatDAOImpl=new ShowsDAOImpl();
		List<Row> showList=getRows(selectQueryImpl, showSeatDAOImpl.getTableName());
		int size=showList.size();
		for(int i=0;i<size;i++)
		{
			msdetails.getShowList().add(showSeatDAOImpl.getAsBean(showList.get(i)));
		}
	}
	
	private void addScreenList(SelectQueryImpl selectQueryImpl, MovieShowWrapperVO msdetails)
	{
		ScreenDAOImpl screenDAOImpl=new ScreenDAOImpl();
		List<Row> screenList=getRows(selectQueryImpl, screenDAOImpl.getTableName());
		int size=screenList.size();
		for(int i=0;i<size;i++)
		{
			msdetails.getScreenList().add(screenDAOImpl.getAsBean(screenList.get(i)));
		}
	}

}
