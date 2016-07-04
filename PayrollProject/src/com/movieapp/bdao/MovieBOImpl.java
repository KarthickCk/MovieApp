package com.movieapp.bdao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.SystemException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adventnet.ds.query.Criteria;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
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
import com.movieapp.dao.MovieShowDAOImpl;
import com.movieapp.dao.ScreenDAOImpl;
import com.movieapp.dao.SeatDAOImpl;
import com.movieapp.dao.ShowSeatDAOImpl;
import com.movieapp.dao.TicketDAOImpl;
import com.movieapp.exception.UserException;
import com.movieapp.factory.MovieDAOFactory;
import com.movieapp.interfaces.MovieBO;
import com.movieapp.util.MovieAppUtil;
import com.movieapp.vo.MovieShowWrapperVO;
import com.movieapp.vo.ScreenSeatsVO;
import com.movieapp.vo.ShowSeatWrapperVO;
import com.movieapp.vo.TicketWrapperVO;
import com.movieapp.wrapperbean.MovieShowsWapperBean;
import com.movieapp.wrapperbean.ScreenWrapper;
import com.movieapp.wrapperbean.TicketAddingWrapperBean;

public class MovieBOImpl implements MovieBO
{
	MovieAppUtil movieAppUtil=MovieAppUtil.INSTANCES;
	
	@Override
	public MovieShowWrapperVO getMovieShowDetails(ArrayList<String> columns,ArrayList<String> values)
	{
		UserBOImpl userBO=new UserBOImpl();
		Criteria criteria=movieAppUtil.getCriteria(MovieShowDAOImpl.TABLE_NAME, getMovieShowsColumnsToFilter(columns), values);
		return userBO.getMovieShowProperties(criteria);
	}

	public ScreenSeatsVO getScreenProperties(String id)
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

	private void addShowSeats(long screenID,long movieShowID) throws DataAccessException, Exception
	{
		List<Long> showSeats=getSeatIDs(screenID);
		int size=showSeats.size();
		for(int i=0;i<size;i++)
		{
			ShowSeatDAOImpl seatDAOImpl=MovieDAOFactory.getShowSeatDAO();
			seatDAOImpl.insert(getShowSeat(showSeats.get(i), movieShowID));
		}
	}

	public MovieShowWrapperVO getMovieShowProperties(ArrayList<String> columns,ArrayList<String> values)
	{
		UserBOImpl userBO=new UserBOImpl();
		Criteria criteria=movieAppUtil.getCriteria(MovieShowDAOImpl.TABLE_NAME, getMovieShowsColumnsToFilter(columns), values);
		return userBO.getMovieShowProperties(criteria);
	}

	public ShowSeatWrapperVO getShowSeatProperties(ArrayList<String> columns,ArrayList<String> values)
	{
		UserBOImpl userBO=new UserBOImpl();
		Criteria criteria=movieAppUtil.getCriteria(ShowSeatDAOImpl.TABLE_NAME, getShowSeatsColumnsToFilter(columns), values);
		return userBO.getShowSeatProperties(criteria);
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
		customer.setEmail("admin@zohocorp.com");
		return customer;
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
		Movie movieData;
		try 
		{
			movieData = MovieDAOFactory.getMovieDAO().insert(movie);
			return movieData;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return movie;
	}

	@Override
	public Movie getMovie(String id) 
	{
		return MovieDAOFactory.getMovieDAO().retrieveWithID(String.valueOf(id));
	}

	@Override
	public ShowDetail addShow(ShowDetail showDetail) 
	{
		ShowDetail showData;
		try 
		{
			showData = MovieDAOFactory.getShowDAO().insert(showDetail);
			return showData;
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return showDetail;
	}

	

	@Override
	public ShowDetail getShow(String id) 
	{
		return MovieDAOFactory.getShowDAO().retrieveWithID(id);
	}

	

	@Override
	public Screen getScreen(String id) 
	{
		return MovieDAOFactory.getScreenDAO().retrieveWithID(id);
	}

	@Override
	public Category addCategory(Category category) 
	{
		Category categoryData;
		try 
		{
			categoryData = MovieDAOFactory.getCategoryDAO().insert(category);
			return categoryData;
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return category;
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
		Extra extraData;
		try 
		{
			extraData = MovieDAOFactory.getExtasDAO().insert(getExtra());
			return extraData;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return extra;
	}

	@Override
	public Customer addCustomer(Customer customer) 
	{
		Customer customerData;
		try 
		{
			customerData = MovieDAOFactory.getCustomerDAO().insert(customer);
			return customerData;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return customer;	
	}

	@Override
	public TicketWrapperVO addTicket(TicketAddingWrapperBean ticketAddingWrapperBean) 
	{
		long ticketID=0;
		try 
		{
			DataAccess.getTransactionManager().begin();
			Ticket ticket=MovieDAOFactory.getTicketDAO().insert(ticketAddingWrapperBean.getTicket());
			ticketID=ticket.getId();
			updateSeatID(ticketAddingWrapperBean, ticketID);
			addTicketCharge(ticketAddingWrapperBean, ticketID);
			DataAccess.getTransactionManager().commit();
		} 
		catch (Exception e) 
		{
			rollBack();
			throw new UserException("Ticket not booked");
		}

		return getBookedTicketProperties(String.valueOf(ticketID));
	}

	private void rollBack()
	{
		try {
			DataAccess.getTransactionManager().rollback();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
	}

	private void updateSeatID(TicketAddingWrapperBean ticketAddingWrapperBean,long ticketID) throws UserException, JSONException, DataAccessException
	{

		ArrayList<String> showSeatIDs=(ArrayList<String>) ticketAddingWrapperBean.getShowseats();
		int size=showSeatIDs.size();
		ShowSeatDAOImpl seatDAOImpl=new ShowSeatDAOImpl();
		ArrayList<String> criteriaColumns=new ArrayList<>();
		criteriaColumns.add(DBConstants.SHOW_SEAT_ID);
		criteriaColumns.add(DBConstants.SS_TICKET_ID);
		seatDAOImpl.setCriteriaColumnNames(criteriaColumns);
		for(int i=0;i<size;i++)
		{
			ArrayList<String> criteriaValues=new ArrayList<>();
			criteriaValues.add(showSeatIDs.get(i));
			criteriaValues.add("0");
			seatDAOImpl.setCriteriaValues(criteriaValues);
			int count=seatDAOImpl.updateSeatCount(String.valueOf(ticketID));
			if(count==0)
			{
				throw new UserException("Ticket already booked");
			}
		}

	}

	private void addTicketCharge(TicketAddingWrapperBean ticketAddingWrapperBean,long ticketID)
	{
		try
		{
			ArrayList<TicketCharge> ticketCharges=(ArrayList<TicketCharge>) ticketAddingWrapperBean.getTicketcharges();
			int size=ticketCharges.size();
			for(int i=0;i<size;i++)
			{
				TicketCharge ticketCharge=ticketCharges.get(i);
				ticketCharge.setTicketID(ticketID);
				MovieDAOFactory.getTicketChargeDAO().insert(ticketCharge);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public TicketWrapperVO getBookedTicketProperties(String id) 
	{
		UserBOImpl userBOImpl=new UserBOImpl();
		ArrayList<String> columns=new ArrayList<>();
		columns.add(DBConstants.TICKET_ID);
		ArrayList<String> values=new ArrayList<>();
		values.add(id);
		Criteria criteria=movieAppUtil.getCriteria(TicketDAOImpl.TABLE_NAME,columns, values);
		Criteria showSeatCriteria=movieAppUtil.getCriteria(ShowSeatDAOImpl.TABLE_NAME,columns, values);
		criteria=criteria.and(showSeatCriteria);
		return userBOImpl.getTicketProperties(criteria);
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

	private void insertSeats(ArrayList<Seat> seats,long screenID) throws DataAccessException, Exception
	{
		int size=seats.size();
		SeatDAOImpl seatDAOImpl=new SeatDAOImpl();
		for(int i=0;i<size;i++)
		{
			Seat seat=seats.get(i);
			seat.setScreenID(screenID);
			String seatName=String.valueOf(seat.getRowNumber())+String.valueOf(seat.getColumnNumber());
			seat.setName(seatName);
			seatDAOImpl.insert(seat);
		}
	}

	private void addMovieShows(List<MovieShow> movieShows)
	{
		MovieShowDAOImpl movieShowDAOImpl=new MovieShowDAOImpl();

		try 
		{
			DataAccess.getTransactionManager().begin();
			int length=movieShows.size();
			for(int i=0;i<length;i++)
			{
				MovieShow movieShow=movieShows.get(i);
				if(!movieShowDAOImpl.isToAddMovieShow(movieShow))
				{
					continue;
				}
				movieShow=movieShowDAOImpl.insert(movieShow);
				long screenID=movieShow.getScreenID();
				long movieShowID=movieShow.getID();
				addShowSeats(screenID, movieShowID);
			}

		} catch (DataAccessException e) 
		{
			rollBack();
			e.printStackTrace();
		} catch (Exception e) 
		{
			rollBack();
			e.printStackTrace();
		}



	}

	public MovieShow updateMovieShow(MovieShow movieShow,String movieShowID) 
	{
		AdminBOImpl adminBOImpl=new AdminBOImpl();
		return adminBOImpl.updateMovieShow(movieShow,movieShowID);
	}

	@Override
	public Customer getCustomer(String mailID,String criteriaColumn) 
	{
		ArrayList<String> criteriaColumns=new ArrayList<>();
		criteriaColumns.add(criteriaColumn);
		ArrayList<String> criteriaValues=new ArrayList<>();
		criteriaValues.add(mailID);

		CustomerDAOImpl customerDAOImpl=new CustomerDAOImpl();
		customerDAOImpl.setCriteriaColumnNames(criteriaColumns);
		customerDAOImpl.setCriteriaValues(criteriaValues);
		Customer customer=customerDAOImpl.retrieveWithCriteria();
		return customer;
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

	@Override
	public MovieShow addMovieshow(MovieShowsWapperBean movieShowWapperBean) 
	{
		addMovieShows(movieShowWapperBean.getMovieshows());
		return null;
	}

	@Override
	public Screen addScreen(ScreenWrapper screenWrapper) 
	{
		ScreenDAOImpl screenDAOImpl=new ScreenDAOImpl();
		Screen screen=screenWrapper.getScreen().getScreen();
		String screenName=screen.getScreenName();
		if(!screenDAOImpl.isToAddScreen(screenName))
		{
			throw new UserException("Screen name already present", 1001);
		}
		try 
		{
			DataAccess.getTransactionManager().begin();
			screen=MovieDAOFactory.getScreenDAO().insert(screen);
			long screenID=screen.getID();
			ArrayList<Seat> seats=(ArrayList<Seat>) screenWrapper.getScreen().getSeats();
			insertSeats(seats,screenID);
			DataAccess.getTransactionManager().commit();
		} 
		catch (DataAccessException e) 
		{
			rollBack();
			throw new UserException("Error while adding screen");
		} 
		catch (Exception e) 
		{
			rollBack();
			throw new UserException("Error while adding screen");
		}

		return screen;
	}

	@Override
	public Extra getExtra(String id) 
	{
		return MovieDAOFactory.getExtasDAO().retrieveWithID(String.valueOf(id));
	}

	@Override
	public Seat getSeat(String id) 
	{
		return MovieDAOFactory.getSeatDAO().retrieveWithID(String.valueOf(id));
	}

	@Override
	public ShowSeat getShowSeat(String id) 
	{
		return MovieDAOFactory.getShowSeatDAO().retrieveWithID(String.valueOf(id));
	}

}

