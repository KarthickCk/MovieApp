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
import com.movieapp.bean.Movie;
import com.movieapp.constants.DBConstants;

public class MovieDAOImpl extends ApplicationDAO<Movie> 
{
	public static String TABLE_NAME="Movie";

	private Movie getMovieData(Row row)
	{
		Movie movies=new Movie();
		long id=(long) row.get(DBConstants.MOVIE_ID);
		String name=(String) row.get(DBConstants.MOVIE_NAME);
		String genre=(String) row.get(DBConstants.MOVIE_GENRE);
		String description=(String) row.get(DBConstants.MOVIE_DESCRIPTION);
		String language=(String) row.get(DBConstants.MOVIE_LANGUAGE);
		String category=(String)row.get(DBConstants.MOVIE_CATEGORY);
		String imageUrl=(String)row.get(DBConstants.MOVIE_IMAGE_URL);
		String duration=(String)row.get(DBConstants.MOVIE_DURATION);
		String releaseDate=(String)row.get(DBConstants.MOVIE_DATE_RELEASED);
		String certificate=(String)row.get(DBConstants.MOVIE_CERTIFICATE);
		movies.setID(id);
		movies.setMovieName(name);
		movies.setGenre(genre);
		movies.setCategory(category);
		movies.setDescription(description);
		movies.setImageURL(imageUrl);
		movies.setLanguage(language);
		movies.setReleasedDate(releaseDate);
		movies.setDuration(duration);
		movies.setCertificate(certificate);
		return movies;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public Movie getAsBean(Row row) 
	{
		//Persistence persist;
		try 
		{
			//persist = (Persistence) BeanUtil.lookup("Persistence");
			//Criteria criteria=new Criteria(new Column(MovieDAOImpl.TABLE_NAME,DBConstants.MOVIE_ID),"",QueryConstants.EQUAL);
			//DataObject data = persist.get(MovieDAOImpl.TABLE_NAME, criteria);
			//Row rows = data.getRow(MovieDAOImpl.TABLE_NAME);
			Movie movie=getMovieData(row);
			return movie;

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void setFromBean(Row row, Movie data) 
	{
		
	}

	@Override
	public Row getAsRow(Movie data) 
	{
		Row row=new Row(TABLE_NAME);
		row.set(DBConstants.MOVIE_NAME, data.getMovieName());
		row.set(DBConstants.MOVIE_CATEGORY, data.getCategory());
		row.set(DBConstants.MOVIE_CERTIFICATE, data.getCertificate());
		row.set(DBConstants.MOVIE_GENRE, data.getGenre());
		row.set(DBConstants.MOVIE_DATE_RELEASED, data.getReleasedDate());
		row.set(DBConstants.MOVIE_DURATION, data.getDuration());
		row.set(DBConstants.MOVIE_IMAGE_URL, data.getImageURL());
		row.set(DBConstants.MOVIE_LANGUAGE, data.getLanguage());
		row.set(DBConstants.MOVIE_DESCRIPTION, data.getDescription());
		return row;
	}

	@Override
	public String getIDColumnName() {
		return DBConstants.MOVIE_ID;
	}

	@Override
	public Movie getAsBean(DataSet dataSet) {
		Movie movie=new Movie();
		try 
		{
			movie.setID((long)dataSet.getValue(DBConstants.MOVIE_ID));
			movie.setMovieName((String)dataSet.getValue(DBConstants.MOVIE_NAME));
			movie.setCategory((String)dataSet.getValue(DBConstants.MOVIE_CATEGORY));
			movie.setCertificate((String)dataSet.getValue(DBConstants.MOVIE_CERTIFICATE));
			movie.setDescription((String)dataSet.getValue(DBConstants.MOVIE_DESCRIPTION));
			movie.setDuration((String)dataSet.getValue(DBConstants.MOVIE_DURATION));
			movie.setGenre((String)dataSet.getValue(DBConstants.MOVIE_GENRE));
			movie.setImageURL((String)dataSet.getValue(DBConstants.MOVIE_IMAGE_URL));
			movie.setLanguage((String)dataSet.getValue(DBConstants.MOVIE_LANGUAGE));
			return movie;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isMovieDeleteable(String movieID) 
	{
		String tableName=MovieShowDAOImpl.TABLE_NAME;

		Join msJoin=getJoinQuery(tableName,ShowSeatDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_ID}, new String[]{DBConstants.SS_MS_ID});

		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(tableName));
		ArrayList<Column> columns=new ArrayList<>();
		Criteria criteria=new Criteria(Column.getColumn(ShowSeatDAOImpl.TABLE_NAME, DBConstants.SS_TICKET_ID), "0", QueryConstants.NOT_EQUAL);
		Criteria msCriteria=new Criteria(Column.getColumn(tableName, DBConstants.MS_MOVIE_ID), movieID, QueryConstants.EQUAL);
		criteria=criteria.and(msCriteria);
		columns.add(Column.getColumn(tableName, DBConstants.MS_MOVIE_ID));
		selectQueryImpl.addSelectColumns(columns);
		selectQueryImpl.setCriteria(criteria);
		selectQueryImpl.addJoin(msJoin);
		Row row=getRow(selectQueryImpl,tableName);
		if(row!=null)
		{
			return false;
		}
		
		return true;
	}

}
