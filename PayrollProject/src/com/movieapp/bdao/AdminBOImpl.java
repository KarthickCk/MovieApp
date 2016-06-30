package com.movieapp.bdao;

import java.util.ArrayList;

import com.adventnet.persistence.DataAccessException;
import com.movieapp.bean.Movie;
import com.movieapp.bean.MovieShow;
import com.movieapp.bean.Screen;
import com.movieapp.bean.Seat;
import com.movieapp.constants.DBConstants;
import com.movieapp.dao.MovieDAOImpl;
import com.movieapp.dao.MovieShowDAOImpl;
import com.movieapp.dao.ScreenDAOImpl;
import com.movieapp.dao.SeatDAOImpl;
import com.movieapp.interfaces.AdminBO;

public class AdminBOImpl implements AdminBO
{

	@Override
	public String updateScreenSeats(Screen screen, ArrayList<Seat> seats) 
	{
		SeatDAOImpl seatDAOImpl=new SeatDAOImpl();
		seatDAOImpl.setUpdateColumn(getSeatUpdateColumns());
		ScreenDAOImpl screenDAOImpl=new ScreenDAOImpl();
		screenDAOImpl.setUpdateColumn(getScreenUpdateColumns());
		screenDAOImpl.setUpdateValue(getScreenUpdateValues(screen));
		String screenId=String.valueOf(screen.getID());
		screenDAOImpl.update(screenId);

		int size=seats.size();
		for(int i=0;i<size;i++)	
		{
			Seat seat=seats.get(i);
			long seatID=seat.getId();
			boolean isToDelete=seat.isDeleted();
			String seatId=String.valueOf(seatID);
			boolean isSeatUpdateAble=seatDAOImpl.isSeatDeleteable(seatId);
			if(isToDelete && isSeatUpdateAble)
			{
				seatDAOImpl.delete(seatId);
			}
			else if(!isToDelete && seatID>0 && isSeatUpdateAble)
			{
				seatDAOImpl.setUpdateValue(getSeatUpdateValues(seat));
				seatDAOImpl.update(seatId);
			}
			else if(seatID==0)
			{
				try 
				{
					seatDAOImpl.insert(seat);
				} 
				catch (DataAccessException e) 
				{
					e.printStackTrace();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}

		return "Screen seats updated";	
	}

	@Override
	public String changeScreenLayout(Screen screen, ArrayList<Seat> seats) 
	{
		long screenID=screen.getID();
		ScreenDAOImpl screenDAOImpl=new ScreenDAOImpl();
		boolean isScreenLayoutChangeable=screenDAOImpl.isScreenShowDeleteable(String.valueOf(screenID));
		if(isScreenLayoutChangeable)
		{
			screenDAOImpl.setUpdateColumn(getScreenUpdateColumns());
			screenDAOImpl.setUpdateValue(getScreenUpdateValues(screen));
			String screenIDValue=String.valueOf(screenID);
			//screenDAOImpl.retrieveWithLockID(screenIDValue);
			screenDAOImpl.update(screenIDValue);
			SeatDAOImpl seatDAOImpl=new SeatDAOImpl();
			ArrayList<String> criteriaColumn=new ArrayList<>();
			criteriaColumn.add(DBConstants.SEAT_SCREEN_ID);
			ArrayList<String> criteriaValues=new ArrayList<>();
			criteriaValues.add(String.valueOf(screenID));
			seatDAOImpl.setCriteriaColumnNames(criteriaColumn);
			seatDAOImpl.setCriteriaValues(criteriaValues);
			seatDAOImpl.delete(null);
			try {
				insertSeats(seats);
			} 
			catch (DataAccessException e) 
			{
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Screen seats are changed";
		}

		return "Screen seats not changeable";
	}

	private void insertSeats(ArrayList<Seat> seats) throws DataAccessException, Exception
	{
		int size=seats.size();
		SeatDAOImpl seatDAOImpl=new SeatDAOImpl();
		for(int i=0;i<size;i++)
		{
			Seat seat=seats.get(i);
			seatDAOImpl.insert(seat);
		}
	}
	
	private ArrayList<String> getSeatUpdateColumns()
	{
		ArrayList<String> updateColumns=new ArrayList<>();
		updateColumns.add(DBConstants.SEAT_ID);
		updateColumns.add(DBConstants.SEAT_SCREEN_ID);
		updateColumns.add(DBConstants.SEAT_CATEGORY_ID);
		updateColumns.add(DBConstants.SEAT_ROW);
		updateColumns.add(DBConstants.SEAT_COLUMN);
		updateColumns.add(DBConstants.SEAT_STATUS);
		updateColumns.add(DBConstants.SEAT_NAME);

		return updateColumns;
	}
	
	private ArrayList<Object> getSeatUpdateValues(Seat seat)
	{
		ArrayList<Object> updateColumns=new ArrayList<>();
		updateColumns.add((long)seat.getId());
		updateColumns.add((long)seat.getScreenID());
		updateColumns.add((long)seat.getCategoryID());
		updateColumns.add((long)seat.getRowNumber());
		updateColumns.add((long)seat.getColumnNumber());
		updateColumns.add(seat.getStatus());
		updateColumns.add(seat.getName());
		return updateColumns;
	}

	@Override
	public MovieShow updateMovieShow(MovieShow movieShow,String movieShowID) 
	{
		MovieShowDAOImpl movieShowDAOImpl=new MovieShowDAOImpl();
		movieShowDAOImpl.setUpdateColumn(getMovieShowUpdateColumns());
		movieShowDAOImpl.setUpdateValue(getMovieShowUpdateValues(movieShow,Long.parseLong(movieShowID)));
		movieShow=movieShowDAOImpl.update(movieShowID);
		return movieShow;
	}
	
	private ArrayList<String> getMovieShowUpdateColumns()
	{
		ArrayList<String> updateColumns=new ArrayList<>();
		//updateColumns.add(DBConstants.MS_ID);
		updateColumns.add(DBConstants.MS_SHOW_ID);
		updateColumns.add(DBConstants.MS_SCREEN_ID);
		updateColumns.add(DBConstants.MS_MOVIE_ID);
		updateColumns.add(DBConstants.MS_DATE);
		return updateColumns;
	}
	
	private ArrayList<Object> getMovieShowUpdateValues(MovieShow movieShow,long movieShowID)
	{
		ArrayList<Object> updateColumns=new ArrayList<>();
		//updateColumns.add(movieShow.getID());
		updateColumns.add((long)movieShow.getShowID());
		updateColumns.add((long)movieShow.getScreenID());
		updateColumns.add((long)movieShow.getMovieID());
		updateColumns.add(movieShow.getMovieDate());
		
		return updateColumns;
	}
	
	private ArrayList<String> getScreenUpdateColumns()
	{
		ArrayList<String> updateColumns=new ArrayList<>();
		//updateColumns.add(DBConstants.MS_ID);
		updateColumns.add(DBConstants.SCREEN_NAME);
		updateColumns.add(DBConstants.SCREEN_ROWS);
		updateColumns.add(DBConstants.SCREEN_COLUMNS);
		return updateColumns;
	}
	
	private ArrayList<Object> getScreenUpdateValues(Screen screen)
	{
		ArrayList<Object> updateColumns=new ArrayList<>();
		//updateColumns.add(movieShow.getID());
		updateColumns.add((String)screen.getScreenName());
		updateColumns.add((long)screen.getScreenRows());
		updateColumns.add((long)screen.getScreenColumns());
		
		return updateColumns;
	}

	@Override
	public Movie updateMovie(Movie movie, String movieID) 
	{
		MovieDAOImpl movieDAOImpl=new MovieDAOImpl();
		movieDAOImpl.setUpdateColumn(getMovieUpdateColumns());
		movieDAOImpl.setUpdateValue(getMovieUpdateValues(movie));
		return 	movieDAOImpl.update(movieID);
	}
	
	private ArrayList<String> getMovieUpdateColumns()
	{
		ArrayList<String> updateColumns=new ArrayList<>();
		updateColumns.add(DBConstants.MOVIE_NAME);
		updateColumns.add(DBConstants.MOVIE_GENRE);
		updateColumns.add(DBConstants.MOVIE_CATEGORY);
		updateColumns.add(DBConstants.MOVIE_LANGUAGE);
		updateColumns.add(DBConstants.MOVIE_DURATION);
		updateColumns.add(DBConstants.MOVIE_DESCRIPTION);
		updateColumns.add(DBConstants.MOVIE_IMAGE_URL);
		updateColumns.add(DBConstants.MOVIE_DATE_RELEASED);
		updateColumns.add(DBConstants.MOVIE_CERTIFICATE);
		return updateColumns;
	}
	
	private ArrayList<Object> getMovieUpdateValues(Movie movie)
	{
		ArrayList<Object> updateColumns=new ArrayList<>();
		updateColumns.add((String)movie.getMovieName());
		updateColumns.add((String)movie.getGenre());
		updateColumns.add((String)movie.getCategory());
		updateColumns.add((String)movie.getLanguage());
		updateColumns.add((String)movie.getDuration());
		updateColumns.add((String)movie.getDescription());
		updateColumns.add((String)movie.getImageURL());
		updateColumns.add((String)movie.getReleasedDate());
		updateColumns.add((String)movie.getCertificate());
		return updateColumns;
	}

}
