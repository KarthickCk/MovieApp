package com.movieapp.bdao;

import java.util.ArrayList;

import com.adventnet.persistence.DataAccessException;
import com.movieapp.bean.Category;
import com.movieapp.bean.Customer;
import com.movieapp.bean.Extra;
import com.movieapp.bean.Movie;
import com.movieapp.bean.MovieShow;
import com.movieapp.bean.Screen;
import com.movieapp.bean.Seat;
import com.movieapp.bean.ShowSeat;
import com.movieapp.bean.Ticket;
import com.movieapp.constants.DBConstants;
import com.movieapp.dao.CategoryDAOImpl;
import com.movieapp.dao.CustomerDAOImpl;
import com.movieapp.dao.ExtraDAOImpl;
import com.movieapp.dao.MovieDAOImpl;
import com.movieapp.dao.MovieShowDAOImpl;
import com.movieapp.dao.ScreenDAOImpl;
import com.movieapp.dao.SeatDAOImpl;
import com.movieapp.dao.ShowSeatDAOImpl;
import com.movieapp.dao.ShowsDAOImpl;
import com.movieapp.dao.TicketDAOImpl;
import com.movieapp.exception.UserException;
import com.movieapp.factory.MovieDAOFactory;
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
			boolean isToDelete=seat.getIsDeleted();
			String seatId=String.valueOf(seatID);
			boolean isSeatUpdateAble=seatDAOImpl.isSeatUpdateable(seatId);
			if(!isSeatUpdateAble)
			{
				throw new UserException("Seat gets booked,not able to update");
			}
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
		boolean isScreenLayoutChangeable=screenDAOImpl.isScreenUpdateable(String.valueOf(screenID));
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

	private ArrayList<Object> getExtraValues(Extra extra)
	{
		ArrayList<Object> updateColumns=new ArrayList<>();
		updateColumns.add((String)extra.getName());
		updateColumns.add((float)extra.getCost());
		return updateColumns;
	} 

	private ArrayList<String> getExtraUpdateColumns()
	{
		ArrayList<String> updateColumns=new ArrayList<>();
		updateColumns.add(DBConstants.EXTRA_NAME);
		updateColumns.add(DBConstants.EXTRA_COST);
		return updateColumns;
	}
	
	private ArrayList<String> getCustomerUpdateColumns()
	{
		ArrayList<String> updateColumns=new ArrayList<>();
		updateColumns.add(DBConstants.CUSTOMER_NAME);
		updateColumns.add(DBConstants.CUSTOMER_EMAIL);
		updateColumns.add(DBConstants.CUSTOMER_PHONE);
		return updateColumns;
	}

	private ArrayList<Object> getCustomerValues(Customer customer)
	{
		ArrayList<Object> updateColumns=new ArrayList<>();
		updateColumns.add((String)customer.getName());
		updateColumns.add((String)customer.getEmail());
		updateColumns.add((String)customer.getPhoneNumber());
		return updateColumns;
	}
	
	private ArrayList<String> getCategoryUpdateColumns()
	{
		ArrayList<String> updateColumns=new ArrayList<>();
		updateColumns.add(DBConstants.CATEGORY_NAME);
		updateColumns.add(DBConstants.CATEGORY_FARE);
		return updateColumns;
	}
	
	private ArrayList<Object> getCategoryValues(Category category)
	{
		ArrayList<Object> updateColumns=new ArrayList<>();
		updateColumns.add((String)category.getCategoryName());
		updateColumns.add((float)category.getFare());
		return updateColumns;
	}
	
	private ArrayList<String> getShowSeatUpdateColumns()
	{
		ArrayList<String> updateColumns=new ArrayList<>();
		updateColumns.add(DBConstants.SS_MS_ID);
		updateColumns.add(DBConstants.SS_SEAT_ID);
		updateColumns.add(DBConstants.SS_TICKET_ID);
		return updateColumns;
	}

	private ArrayList<Object> getShowSeatUpdateValues(ShowSeat showSeat)
	{
		ArrayList<Object> updateColumns=new ArrayList<>();
		updateColumns.add((long)showSeat.getMovieShowID());
		updateColumns.add((long)showSeat.getSeatID());
		updateColumns.add((long)showSeat.getTicketID());
		return updateColumns;
	}
	
	private ArrayList<String> getTicketUpdateColumns()
	{
		ArrayList<String> updateColumns=new ArrayList<>();
		updateColumns.add(DBConstants.TICKET_ID);
		updateColumns.add(DBConstants.TK_MS_ID);
		updateColumns.add(DBConstants.TK_CUSTOMER_ID);
		updateColumns.add(DBConstants.TICKET_COST);
		return updateColumns;
	}

	private ArrayList<Object> getTicketUpdateValues(Ticket ticket)
	{
		ArrayList<Object> updateColumns=new ArrayList<>();
		updateColumns.add((long)ticket.getId());
		updateColumns.add((long)ticket.getMovieShowID());
		updateColumns.add((long)ticket.getCustomerID());
		updateColumns.add((float)ticket.getTotalCost());
		return updateColumns;
	}

	@Override
	public String deleteScreen(String screenID) 
	{
		String responseMessage="Ticket for the screen is booked";
		ScreenDAOImpl screenDAOImpl=MovieDAOFactory.getScreenDAO();
		if(screenDAOImpl.isScreenUpdateable(screenID))
		{
			screenDAOImpl.delete(screenID);
			responseMessage="Deleted successfully";
			return responseMessage;
		}

		throw new UserException(responseMessage, 1001);
	}

	@Override
	public String deleteMovieShow(String id) 
	{
		String responseMessage="Ticket for the show is booked";
		MovieShowDAOImpl movieShowDAOImpl=MovieDAOFactory.getMovieShowDAO();
		if(movieShowDAOImpl.isMovieShowDeleteable(id))
		{
			movieShowDAOImpl.delete(id);
			responseMessage="Deleted successfully";
			return responseMessage;
		}

		throw new UserException(responseMessage, 1001);
	}


	@Override
	public String deleteMovie(String id) 
	{
		String responseMessage="Movie tickets gets booked";
		MovieDAOImpl movieDAOImpl=new MovieDAOImpl();
		if(movieDAOImpl.isMovieDeleteable(id))
		{
			movieDAOImpl.delete(id);
			responseMessage="Deleted successfully";
			return responseMessage;
		}

		throw new UserException(responseMessage,1002);
	}


	@Override
	public String deleteShow(String showID) 
	{
		String responseMessage="Ticket for the screen is booked";
		ShowsDAOImpl ShowsDAOImpl=MovieDAOFactory.getShowDAO();
		if(ShowsDAOImpl.isShowDeleteable(showID))
		{
			ShowsDAOImpl.delete(showID);
			responseMessage="Deleted successfully";
			return responseMessage;
		}

		throw new UserException(responseMessage,1002);
	}

	@Override
	public String deleteCustomer(String id) 
	{
		String responseMessage="Customer is assigned to ticket,not able to delete";
		CustomerDAOImpl customerDAOImpl=MovieDAOFactory.getCustomerDAO();
		if(customerDAOImpl.isCustomerDeleteable(id))
		{
			customerDAOImpl.delete(id);
			responseMessage="Deleted successfully";
			return responseMessage;
		}
		throw new UserException(responseMessage, 1001);
	}

	@Override
	public String deleteExtra(String id) 
	{
		String responseMessage="Extras is assigned to ticket,not able to delete";
		ExtraDAOImpl extraDAOImpl=MovieDAOFactory.getExtasDAO();
		if(extraDAOImpl.isExtrasDeleteable(id))
		{
			extraDAOImpl.delete(id);
			responseMessage="Deleted successfully";
			return responseMessage;
		}

		throw new UserException(responseMessage, 1001);
	}

	@Override
	public String deleteCategory(String id) 
	{
		String responseMessage="Category is assigned to seats,not able to delete";
		CategoryDAOImpl categoryDAOImpl=MovieDAOFactory.getCategoryDAO();
		if(categoryDAOImpl.isCategoryDeletable(id))
		{
			categoryDAOImpl.delete(id);
			responseMessage="Deleted successfully";
			return responseMessage;
		}

		throw new UserException(responseMessage, 1001);
	}

	@Override
	public Extra updateExtras(String id, Extra extra) 
	{
		ExtraDAOImpl extraDAOImpl=new ExtraDAOImpl();
		extraDAOImpl.setUpdateColumn(getExtraUpdateColumns());
		extraDAOImpl.setUpdateValue(getExtraValues(extra));
		return 	extraDAOImpl.update(id);	
	}

	@Override
	public Customer updateCustomer(String id, Customer customer) 
	{
		CustomerDAOImpl customerDAOImpl=new CustomerDAOImpl();
		customerDAOImpl.setUpdateColumn(getCustomerUpdateColumns());
		customerDAOImpl.setUpdateValue(getCustomerValues(customer));
		return 	customerDAOImpl.update(id);	
	}

	@Override
	public Category updateCategory(String id, Category category) 
	{
		CategoryDAOImpl categoryDAOImpl=new CategoryDAOImpl();
		categoryDAOImpl.setUpdateColumn(getCategoryUpdateColumns());
		categoryDAOImpl.setUpdateValue(getCategoryValues(category));
		return categoryDAOImpl.update(id);
	}

	@Override
	public String deleteSeat(String id) 
	{
		checkSeatEditable(id);
		SeatDAOImpl seatDAOImpl=new SeatDAOImpl();
		seatDAOImpl.delete(id);
		return "Deleted successfully";
	}

	@Override
	public Seat updateSeat(String id, Seat seat) 
	{
		checkSeatEditable(id);
		SeatDAOImpl seatDAOImpl=new SeatDAOImpl();
		seatDAOImpl.setUpdateColumn(getSeatUpdateColumns());
		seatDAOImpl.setUpdateValue(getSeatUpdateValues(seat));
		return seatDAOImpl.update(id);
	}
	
	private void checkSeatEditable(String id)
	{
		SeatDAOImpl seatDAOImpl=new SeatDAOImpl();
		boolean isSeatUpdateAble=seatDAOImpl.isSeatUpdateable(id);
		if(!isSeatUpdateAble)
		{
			throw new UserException("Seat gets booked,not able to update");
		}
	}
	
	private void checkShowSeatEditable(String id)
	{
		ShowSeatDAOImpl showSeatDAOImpl=new ShowSeatDAOImpl();
		boolean isShowSeatUpdateAble=showSeatDAOImpl.isShowSeatUpdateable(id);
		if(!isShowSeatUpdateAble)
		{
			throw new UserException("Seat gets booked,not able to update");
		}
	}

	@Override
	public Seat addSeat(Seat seat) 
	{
		try 
		{
			return MovieDAOFactory.getSeatDAO().insert(seat);
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return seat;
	}

	@Override
	public ShowSeat addShowSeat(ShowSeat showSeat) 
	{
		ShowSeatDAOImpl showSeatDAOImpl=new ShowSeatDAOImpl();
		try 
		{
			return showSeatDAOImpl.insert(showSeat);
		}
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return showSeat;
	}

	@Override
	public String deleteShowSeat(String id) 
	{
		checkShowSeatEditable(id);
		ShowSeatDAOImpl showSeatDAOImpl=new ShowSeatDAOImpl();
		showSeatDAOImpl.delete(id);
		return "Deleted successfully";
	}

	@Override
	public ShowSeat updateShowSeat(String id, ShowSeat showSeat) 
	{
		checkShowSeatEditable(id);
		ShowSeatDAOImpl showSeatDAOImpl=new ShowSeatDAOImpl();
		showSeatDAOImpl.setUpdateColumn(getShowSeatUpdateColumns());
		showSeatDAOImpl.setUpdateValue(getShowSeatUpdateValues(showSeat));
		return showSeatDAOImpl.update(id);
	}

	@Override
	public String deleteTicket(String id) 
	{
		TicketDAOImpl ticketDAOImpl=new TicketDAOImpl();
		ticketDAOImpl.delete(id);
		return "Ticket deleted successfully";
	}

	@Override
	public Ticket updateTicket(String id,Ticket ticket) 
	{
		TicketDAOImpl ticketDAOImpl=new TicketDAOImpl();
		ticketDAOImpl.setUpdateColumn(getTicketUpdateColumns());
		ticketDAOImpl.setUpdateValue(getTicketUpdateValues(ticket));
		return ticketDAOImpl.update(id);
	}

	@Override
	public Ticket getTicket(String id) 
	{
		return MovieDAOFactory.getTicketDAO().retrieveWithID(String.valueOf(id));
	}

	@Override
	public Ticket addTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return null;
	}


}
