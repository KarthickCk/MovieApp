package com.movieapp.bdao;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.adventnet.ds.query.Criteria;
import com.movieapp.bean.Category;
import com.movieapp.bean.Customer;
import com.movieapp.bean.Extra;
import com.movieapp.bean.Movie;
import com.movieapp.bean.MovieShow;
import com.movieapp.bean.Screen;
import com.movieapp.bean.Seat;
import com.movieapp.bean.ShowDetail;
import com.movieapp.bean.ShowSeat;
import com.movieapp.bean.Ticket;
import com.movieapp.bean.TicketCharge;
import com.movieapp.constants.DBConstants;
import com.movieapp.dao.CustomerDAOImpl;
import com.movieapp.dao.MovieDAOImpl;
import com.movieapp.dao.MovieShowDAOImpl;
import com.movieapp.dao.ScreenDAOImpl;
import com.movieapp.dao.SeatDAOImpl;
import com.movieapp.dao.ShowSeatDAOImpl;
import com.movieapp.dao.ShowsDAOImpl;
import com.movieapp.dao.TicketDAOImpl;
import com.movieapp.factory.MovieDAOFactory;
import com.movieapp.interfaces.MovieBO;
import com.movieapp.util.MovieAppUtil;

public class MovieBOImpl implements MovieBO
{
	MovieAppUtil movieAppUtil=MovieAppUtil.INSTANCES;

	public Properties getMovieShowDetails(ArrayList<String> columns,ArrayList<String> values)
	{
		UserBOImpl userBO=new UserBOImpl();
		Criteria criteria=movieAppUtil.getCriteria(MovieShowDAOImpl.TABLE_NAME, getMovieShowsColumnsToFilter(columns), values);
		return userBO.getMovieShowProperties(criteria);
	}

	public Properties getScreenProperties(String id)
	{
		UserBOImpl userBO=new UserBOImpl();
		Criteria criteria=null;
		if(id!=null)
		{
			ArrayList<String> criteriaColumns=new ArrayList<>();
			criteriaColumns.add(DBConstants.SCREEN_ID);
			ArrayList<String> criteriaValues=new ArrayList<>();
			criteriaValues.add(id);
			criteria=movieAppUtil.getCriteria(ScreenDAOImpl.TABLE_NAME, criteriaColumns, criteriaValues);
		}
		return userBO.getScreenProperties(criteria);
	}

	private List<Long> getSeatIDs(long screenID)
	{
		SeatDAOImpl seatDAOImpl=MovieDAOFactory.getSeatDAO();
		ArrayList<String> columns=new ArrayList<>();
		columns.add(DBConstants.SCREEN_ID);
		ArrayList<String> values=new ArrayList<>();
		values.add(String.valueOf(screenID));
		seatDAOImpl.setCriteriaColumnNames(columns);
		seatDAOImpl.setCriteriaValues(values);
		List<Long> seatIDs=seatDAOImpl.getDistinctIDs(DBConstants.SEAT_ID);
		return seatIDs;
	}

	private void addShowSeats(long screenID,long movieShowID)
	{
		List<Long> showSeats=getSeatIDs(screenID);
		int size=showSeats.size();
		for(int i=0;i<size;i++)
		{
			ShowSeatDAOImpl seatDAOImpl=MovieDAOFactory.getShowSeatDAO();
			seatDAOImpl.insert(getShowSeat(showSeats.get(i), movieShowID));
		}
	}

	public Properties getMovieShowProperties(ArrayList<String> columns,ArrayList<String> values)
	{
		UserBOImpl userBO=new UserBOImpl();
		Criteria criteria=movieAppUtil.getCriteria(MovieShowDAOImpl.TABLE_NAME, getMovieShowsColumnsToFilter(columns), values);
		return userBO.getMovieShowProperties(criteria);
	}

	public Properties getShowSeatProperties(ArrayList<String> columns,ArrayList<String> values)
	{
		UserBOImpl userBO=new UserBOImpl();
		Criteria criteria=movieAppUtil.getCriteria(ShowSeatDAOImpl.TABLE_NAME, getShowSeatsColumnsToFilter(columns), values);
		return userBO.getShowSeatProperties(criteria);
	}

	public void addSeat(Seat seat)
	{
		MovieDAOFactory.getSeatDAO().insert(seat);
	}

	private Movie getMovie()
	{
		Movie movie=new Movie();
		movie.setMovieName("Mangatha");
		movie.setCategory("Category");
		movie.setCertificate("U");
		movie.setDescription("Nalla movie");
		movie.setGenre("Etho onnu");
		movie.setImageURL("www.google.com/Mangatha");
		movie.setReleasedDate("14-06-2016");
		movie.setLanguage("Tamil");
		movie.setDuration("120 mins");
		return movie;
	}

	private ShowDetail getShowDetail()
	{
		ShowDetail showDetail=new ShowDetail();
		showDetail.setShowName("Night");
		showDetail.setStartTime("9.30 PM");
		showDetail.setEndTime("12.00 AM");
		return showDetail;
	}

	private Screen getScreen()
	{
		Screen screen=new Screen();
		screen.setScreenName("Screen 2");
		screen.setScreenRows(5);
		screen.setScreenColumns(5);
		return screen;
	}

	private MovieShow getMovieShow()
	{
		MovieShow movieShow=new MovieShow();
		movieShow.setMovieID(11004);
		movieShow.setScreenID(6004);
		movieShow.setShowID(8004);
		movieShow.setMovieDate("14-06-2016");
		return movieShow;
	}

	private Category getCategory()
	{
		Category category=new Category();
		category.setCategoryName("FirstClass");
		category.setFare(120);
		return category;
	}

	private Seat getSeats(long screenID,long categoryID,int row,int column,String screenName)
	{
		Seat  seat=new Seat();
		seat.setCategoryID(categoryID);
		seat.setScreenID(screenID);
		seat.setRowNumber(row);
		seat.setColumnNumber(column);
		seat.setName(screenName);
		seat.setStatus(true);
		return seat;
	}

	public ShowSeat getShowSeat(long seatID,long movieShowID)
	{
		ShowSeat showSeat=new ShowSeat();
		showSeat.setMovieShowID(movieShowID);
		showSeat.setSeatID(seatID);
		return showSeat;
	}

	public Extra getExtra()
	{
		Extra extra=new Extra();
		extra.setName("Extra 1");
		extra.setCost(200);
		return extra;
	}

	public Customer getCustomer()
	{
		Customer customer=new Customer();
		customer.setName("Karthick");
		customer.setPhoneNumber("9790198648");
		customer.setEmail("karthickck0@gmail.com");
		return customer;
	}

	private Ticket getTicket(String ticketString)
	{
		Ticket ticket=new Ticket();
		try
		{
			JSONObject ticketJson=new JSONObject(ticketString);
			float ticketCost=Float.parseFloat(ticketJson.optString("totalCost"));
			long movieShowID=Long.parseLong(ticketJson.optString("movieShowID"));
			long customerID=Long.parseLong(ticketJson.optString("customerID"));
			ticket.setTotalCost(ticketCost);
			ticket.setMovieShowID(movieShowID);
			ticket.setCustomerID(customerID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ticket;

	}

	private TicketCharge getTicketCharge(JSONObject extraJson,long ticketID)
	{
		TicketCharge ticketCharge=new TicketCharge();
		try
		{
			long extraID=Long.parseLong(extraJson.optString("extraID"));
			int quantity=Integer.parseInt(extraJson.optString("quantity"));
			ticketCharge.setTicketID(ticketID);
			ticketCharge.setQuantity(quantity);
			ticketCharge.setExtraID(extraID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ticketCharge;

	}

	private ArrayList<String> getMovieShowsColumnsToFilter(ArrayList<String> columns)
	{
		int size=columns.size();
		ArrayList<String> column=new ArrayList<>();
		for(int i=0;i<size;i++)
		{
			switch (columns.get(i)) 
			{
			case "date":
				column.add(DBConstants.MS_DATE);
				break;

			case "movie":
				column.add(DBConstants.MS_MOVIE_ID);
				break;

			case "screen":
				column.add(DBConstants.MS_SCREEN_ID);
				break;

			case "id":
				column.add(DBConstants.MS_ID);
				break;

			default:
				break;
			}
		}
		return column;
	}

	private ArrayList<String> getShowSeatsColumnsToFilter(ArrayList<String> columns)
	{
		int size=columns.size();
		ArrayList<String> column=new ArrayList<>();
		for(int i=0;i<size;i++)
		{
			switch (columns.get(i)) 
			{
			case "movieShowID":
				column.add(DBConstants.MS_ID);
				break;

			case "movie":
				column.add(DBConstants.MS_MOVIE_ID);
				break;

			case "screen":
				column.add(DBConstants.MS_SCREEN_ID);
				break;

			default:
				break;
			}
		}
		return column;
	}

	@Override
	public Movie addMovie(Movie movie) 
	{
		Movie movieData=MovieDAOFactory.getMovieDAO().insert(movie);
		return movieData;
	}

	@Override
	public Movie getMovie(String id) 
	{
		return MovieDAOFactory.getMovieDAO().retrieveWithID(String.valueOf(id));
	}

	@Override
	public ShowDetail addShow(ShowDetail showDetail) 
	{
		ShowDetail showData=MovieDAOFactory.getShowDAO().insert(getShowDetail());
		return showData;
	}

	@Override
	public String deleteShow(String showID) 
	{
		String responseMessage="Ticket for the screen is booked";
		ShowsDAOImpl ShowsDAOImpl=MovieDAOFactory.getShowDAO();
		if(ShowsDAOImpl.isShowDeleteable(showID))
		{
			//ShowsDAOImpl.delete(showID);
			responseMessage="Deleted successfully";
		}
		return responseMessage; 
	}

	@Override
	public ShowDetail getShow(String id) 
	{
		return MovieDAOFactory.getShowDAO().retrieveWithID(id);
	}

	@Override
	public Screen addScreen(String screenData) 
	{
		Screen screen=getScreenObject(screenData);
		screen=MovieDAOFactory.getScreenDAO().insert(screen);
		ArrayList<Seat> seats=getSeats(screenData, screen.getID());
		insertSeats(seats);
		return screen;
	}

	@Override
	public String deleteScreen(String screenID) 
	{
		String responseMessage="Ticket for the screen is booked";
		ScreenDAOImpl screenDAOImpl=MovieDAOFactory.getScreenDAO();
		if(screenDAOImpl.isScreenShowDeleteable(screenID))
		{
			screenDAOImpl.delete(screenID);
			responseMessage="Deleted successfully";
		}
		return responseMessage; 
	}

	@Override
	public Screen getScreen(String id) 
	{
		return MovieDAOFactory.getScreenDAO().retrieveWithID(id);
	}

	@Override
	public MovieShow addMovieshow(String data) 
	{
		addMovieShows(data);
		return null;
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
		}
		return responseMessage;
	}

	@Override
	public Category addCategory(Category category) 
	{
		Category categoryData=MovieDAOFactory.getCategoryDAO().insert(getCategory());
		return categoryData;
	}

	@Override
	public List<Movie> getMovies() 
	{
		return MovieDAOFactory.getMovieDAO().retrieveAll();
	}

	@Override
	public List<ShowDetail> getShows() 
	{
		return MovieDAOFactory.getShowDAO().retrieveAll();
	}

	@Override
	public List<Extra> getExtras() 
	{
		return MovieDAOFactory.getExtasDAO().retrieveAll();
	}

	@Override
	public Extra addExtra(Extra extra) 
	{
		Extra extraData=MovieDAOFactory.getExtasDAO().insert(getExtra());
		return extraData;
	}

	@Override
	public Customer addCustomer(Customer customer) 
	{
		Customer customerData=MovieDAOFactory.getCustomerDAO().insert(customer);
		return customerData;	
	}

	@Override
	public Properties addTicket(String ticket) 
	{
		Ticket ticketData=MovieDAOFactory.getTicketDAO().insert(getTicket(ticket));
		long ticketID=ticketData.getId();
		updateSeatID(ticket, ticketID);
		addTicketCharge(ticket, ticketID);
		return getBookedTicketProperties(String.valueOf(ticketID));
	}

	private void updateSeatID(String ticket,long ticketID)
	{
		try
		{
			JSONObject ticketObject=new JSONObject(ticket);
			JSONArray seats=ticketObject.optJSONArray("showseats");
			int length=seats.length();
			ShowSeatDAOImpl seatDAOImpl=new ShowSeatDAOImpl();
			ArrayList<String> updateColumns=new ArrayList<>();
			updateColumns.add(DBConstants.SS_TICKET_ID);
			ArrayList<Object> updateValues=new ArrayList<>();
			updateValues.add(ticketID);
			seatDAOImpl.setUpdateColumn(updateColumns);
			seatDAOImpl.setUpdateValue(updateValues);
			ArrayList<String> criteriaColumns=new ArrayList<>();
			criteriaColumns.add(DBConstants.SHOW_SEAT_ID);

			for(int i=0;i<length;i++)
			{
				ArrayList<String> criteriaValues=new ArrayList<>();
				String seatID=seats.optString(i);
				criteriaValues.add(seats.optString(i));
				seatDAOImpl.update(seatID);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void addTicketCharge(String data,long ticketID)
	{
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONArray ticketCharge=jsonObject.optJSONArray("ticketcharges");
			int length=ticketCharge.length();
			for(int i=0;i<length;i++)
			{
				JSONObject ticketChargeObject=(JSONObject) ticketCharge.get(i);
				MovieDAOFactory.getTicketChargeDAO().insert(getTicketCharge(ticketChargeObject, ticketID));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public Properties getBookedTicketProperties(String id) 
	{
		UserBOImpl userBOImpl=new UserBOImpl();
		ArrayList<String> columns=new ArrayList<>();
		columns.add(DBConstants.TICKET_ID);
		ArrayList<String> values=new ArrayList<>();
		values.add(id);
		Criteria criteria=movieAppUtil.getCriteria(TicketDAOImpl.TABLE_NAME,columns, values);
		Criteria showSeatCriteria=movieAppUtil.getCriteria(ShowSeatDAOImpl.TABLE_NAME,columns, values);
		criteria=criteria.and(showSeatCriteria);
		Properties properties=userBOImpl.getTicketProperties(criteria);		
		return properties;
	}

	public String updateScreenLayout(String screenData,boolean isChangeLayout)
	{
		AdminBOImpl adminBOImpl=new AdminBOImpl();
		Screen screen=getScreenObject(screenData);
		ArrayList<Seat> seats=getSeats(screenData, screen.getID());
		if(isChangeLayout)
		{
			return adminBOImpl.changeScreenLayout(screen, seats);
		}
		return adminBOImpl.updateScreenSeats(screen, seats);
	}

	private Screen getScreenObject(String screenData)
	{
		try
		{
			JSONObject screenObject=new JSONObject(screenData);
			JSONObject screenValue=screenObject.optJSONObject("screen");
			screenValue.remove("seats");
			Screen screen=(Screen) movieAppUtil.getObject(screenValue.toString(), Screen.class);

			return screen;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<Seat> getSeats(String screenData,long screenId)
	{
		ArrayList<Seat> seats=new ArrayList<>();

		try
		{
			JSONObject screenObject=new JSONObject(screenData);
			JSONObject screenValue=screenObject.optJSONObject("screen");
			JSONArray seatArray=(JSONArray) screenValue.remove("seats");
			int seatLength=seatArray.length();
			for(int i=0;i<seatLength;i++)
			{
				JSONObject seatObject=seatArray.optJSONObject(i);
				Seat seat=(Seat) movieAppUtil.getObject(seatObject.toString(), Seat.class);
				String seatName=String.valueOf(seat.getRowNumber())+String.valueOf(seat.getColumnNumber());
				seat.setName(seatName);
				seat.setScreenID(screenId);
				seats.add(seat);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return seats;
	}

	private void insertSeats(ArrayList<Seat> seats)
	{
		int size=seats.size();
		SeatDAOImpl seatDAOImpl=new SeatDAOImpl();
		for(int i=0;i<size;i++)
		{
			Seat seat=seats.get(i);
			seatDAOImpl.insert(seat);
		}
	}

	private void addMovieShows(String data)
	{
		MovieShowDAOImpl movieShowDAOImpl=new MovieShowDAOImpl();
		
		try
		{
			JSONObject movieShowsObject=new JSONObject(data);
			JSONArray array=movieShowsObject.optJSONArray("movieshows");
			int length=array.length();
			for(int i=0;i<length;i++)
			{
				JSONObject jsonObject=array.optJSONObject(i);
				MovieShow movieShow=(MovieShow) movieAppUtil.getObject(jsonObject.toString(),MovieShow.class);
				if(!movieShowDAOImpl.isToAddMovieShow(movieShow))
				{
					continue;
				}
				movieShow=movieShowDAOImpl.insert(movieShow);
				long screenID=movieShow.getScreenID();
				long movieShowID=movieShow.getID();
				addShowSeats(screenID, movieShowID);
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}

	public MovieShow updateMovieShow(MovieShow movieShow,String movieShowID) 
	{
		AdminBOImpl adminBOImpl=new AdminBOImpl();
		return adminBOImpl.updateMovieShow(movieShow,movieShowID);
	}

	@Override
	public Customer getCustomer(String mailID) 
	{
		ArrayList<String> criteriaColumns=new ArrayList<>();
		criteriaColumns.add(DBConstants.CUSTOMER_EMAIL);
		ArrayList<String> criteriaValues=new ArrayList<>();
		criteriaValues.add(mailID);

		CustomerDAOImpl customerDAOImpl=new CustomerDAOImpl();
		customerDAOImpl.setCriteriaColumnNames(criteriaColumns);
		customerDAOImpl.setCriteriaValues(criteriaValues);
		Customer customer=customerDAOImpl.retrieveWithCriteria();
		return customer;
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
		}
		return responseMessage;
	}
	
	public Movie updateMovie(Movie movie,String movieID)
	{
		AdminBOImpl  adminBOImpl=new AdminBOImpl();
		return adminBOImpl.updateMovie(movie, movieID);
	}

	@Override
	public Category getCategory(String id) 
	{
		return MovieDAOFactory.getCategoryDAO().retrieveWithID(String.valueOf(id));
	}

}

