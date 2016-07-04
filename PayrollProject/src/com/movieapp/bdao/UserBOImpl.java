package com.movieapp.bdao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.GroupByClause;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;
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
import com.movieapp.dao.CategoryDAOImpl;
import com.movieapp.dao.CustomerDAOImpl;
import com.movieapp.dao.ExtraDAOImpl;
import com.movieapp.dao.MovieDAOImpl;
import com.movieapp.dao.MovieShowDAOImpl;
import com.movieapp.dao.ScreenDAOImpl;
import com.movieapp.dao.SeatDAOImpl;
import com.movieapp.dao.ShowSeatDAOImpl;
import com.movieapp.dao.ShowsDAOImpl;
import com.movieapp.dao.TicketChargeImpl;
import com.movieapp.dao.TicketDAOImpl;
import com.movieapp.interfaces.UserBO;
import com.movieapp.util.MovieAppUtil;
import com.movieapp.vo.MovieShowWrapperVO;
import com.movieapp.vo.ScreenSeatsVO;
import com.movieapp.vo.ShowSeatWrapperVO;
import com.movieapp.vo.TicketWrapperVO;
import com.movieapp.wrapperbean.TicketBookingWrapperBean;

public class UserBOImpl implements UserBO
{
	MovieAppUtil movieAppUtil=MovieAppUtil.INSTANCES;
	
	private MovieShowWrapperVO getMovieShowListSelectQuery(Criteria movieShowCriteria) 
	{
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(MovieShowDAOImpl.TABLE_NAME));
		Criteria criteria=new Criteria(Column.getColumn(ShowSeatDAOImpl.TABLE_NAME, DBConstants.SS_TICKET_ID), "0", QueryConstants.EQUAL);
		if(movieShowCriteria!=null)
		{
			criteria=movieShowCriteria.and(criteria);
		}
		ArrayList<Column> groupList=new ArrayList<>();
		groupList.add(Column.getColumn(MovieShowDAOImpl.TABLE_NAME, DBConstants.MS_ID));
		Join join=movieAppUtil.getJoinQuery(MovieShowDAOImpl.TABLE_NAME,ShowSeatDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_ID}, new String[]{DBConstants.SS_MS_ID});
		selectQueryImpl.addJoin(join);
		Join movieJoin=movieAppUtil.getJoinQuery(MovieShowDAOImpl.TABLE_NAME,MovieDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_MOVIE_ID}, new String[]{DBConstants.MOVIE_ID});
		selectQueryImpl.addJoin(movieJoin);
		Join screenJoin=movieAppUtil.getJoinQuery(MovieShowDAOImpl.TABLE_NAME,ScreenDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_SCREEN_ID}, new String[]{DBConstants.SCREEN_ID});
		selectQueryImpl.addJoin(screenJoin);
		Join showJoin=movieAppUtil.getJoinQuery(MovieShowDAOImpl.TABLE_NAME,ShowsDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_SHOW_ID}, new String[]{DBConstants.SHOW_ID});
		selectQueryImpl.addJoin(showJoin);
		selectQueryImpl.setCriteria(criteria);
		GroupByClause group=new GroupByClause(groupList);
		selectQueryImpl.setGroupByClause(group);
		ArrayList<Column> columns=new ArrayList<>();
		columns.add(Column.getColumn(MovieShowDAOImpl.TABLE_NAME,"*"));
		columns.add(Column.getColumn(MovieDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(ScreenDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(ShowsDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(ShowSeatDAOImpl.TABLE_NAME, DBConstants.SS_TICKET_ID).count());
		selectQueryImpl.addSelectColumns(columns);
		return executeMovieShowSelectQuery(selectQueryImpl);
	}
	
	private MovieShowWrapperVO executeMovieShowSelectQuery(SelectQuery selectQuery)
	{
		MovieShowWrapperVO msdetails = new MovieShowWrapperVO();

		RelationalAPI relapi = RelationalAPI.getInstance();
		Connection conn = null;
		DataSet ds = null;
		try 
		{
			conn = relapi.getConnection();
			ds = relapi.executeQuery(selectQuery,conn);
			MovieShowDAOImpl movieShowDAOImpl=new MovieShowDAOImpl();
			MovieDAOImpl movieDAOImpl=new MovieDAOImpl();
			ScreenDAOImpl screenDAOImpl=new ScreenDAOImpl();
			ShowsDAOImpl showsDAOImpl=new ShowsDAOImpl();
			while(ds.next())
			{
				msdetails.getMovieshows().add(movieShowDAOImpl.getAsBean(ds));
				Movie movie=movieDAOImpl.getAsBean(ds);
				Screen screen=screenDAOImpl.getAsBean(ds);
				ShowDetail showDetail=showsDAOImpl.getAsBean(ds);
				if(!msdetails.isMovieAdded(movie))
				{
					msdetails.getMovies().add(movie);
				}
				if(!msdetails.isSCreenAdded(screen))
				{
					msdetails.getScreens().add(screen);
				}
				if(!msdetails.isShowAdded(showDetail))
				{
					msdetails.getShows().add(showDetail);
				}
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
		return msdetails;
	}
	
	private ShowSeatWrapperVO getShowSeats(Criteria criteria) 
	{
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(ShowSeatDAOImpl.TABLE_NAME));
		Join join=movieAppUtil.getJoinQuery(ShowSeatDAOImpl.TABLE_NAME,SeatDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.SS_SEAT_ID}, new String[]{DBConstants.SEAT_ID});
		Join categoryJoin=movieAppUtil.getJoinQuery(SeatDAOImpl.TABLE_NAME,CategoryDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.SEAT_CATEGORY_ID}, new String[]{DBConstants.CATEGORY_ID});
		ArrayList<Column> columns=new ArrayList<>();
		columns.add(Column.getColumn(ShowSeatDAOImpl.TABLE_NAME,"*"));
		columns.add(Column.getColumn(SeatDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(CategoryDAOImpl.TABLE_NAME, "*"));
		selectQueryImpl.addSelectColumns(columns);
		selectQueryImpl.addJoin(join);
		selectQueryImpl.addJoin(categoryJoin);
		selectQueryImpl.setCriteria(criteria);
		return executeShowSeatQuery(selectQueryImpl);
	}
	
	private ArrayList<Screen> getScreens(DataObject dataObject)
	{
		ArrayList<Screen> screens=new ArrayList<>();
		ScreenDAOImpl screenDAOImpl=new ScreenDAOImpl();
		try 
		{
			Iterator<Row> iterator=dataObject.getRows(ScreenDAOImpl.TABLE_NAME);
			while(iterator.hasNext())
			{
				Row row=iterator.next();
				screens.add(screenDAOImpl.getAsBean(row));
			}
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return screens;
	}
	
	private TicketBookingWrapperBean getTicket(DataObject dataObject)
	{
		TicketDAOImpl ticketDAOImpl=new TicketDAOImpl();
		
		TicketBookingWrapperBean ticketBookingWrapperBean=new TicketBookingWrapperBean();
		try 
		{
			Row row=dataObject.getFirstRow(TicketDAOImpl.TABLE_NAME);
			Ticket ticket=ticketDAOImpl.getAsBean(row);
			ticketBookingWrapperBean.setId(ticket.getId());
			ticketBookingWrapperBean.setMovieShowID(ticket.getMovieShowID());
			ticketBookingWrapperBean.setCustomerID(ticket.getCustomerID());
			ticketBookingWrapperBean.setTotalCost(ticket.getTotalCost());
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return ticketBookingWrapperBean;
	}
	
	private ArrayList<Seat> getSeats(DataObject dataObject,TicketBookingWrapperBean ticketBookingWrapperBean)
	{
		ArrayList<Seat> seats=new ArrayList<>();
		SeatDAOImpl seatDAOImpl=new SeatDAOImpl();
		try 
		{
			Iterator<Row> iterator=dataObject.getRows(SeatDAOImpl.TABLE_NAME);
			while(iterator.hasNext())
			{
				Row row=iterator.next();
				Seat seat=seatDAOImpl.getAsBean(row);
				long id=seat.getId();
				ticketBookingWrapperBean.getSeats().add(id);
				seats.add(seat);
			}
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return seats;
	}
	
	private ArrayList<Seat> getSeats(DataObject dataObject)
	{
		ArrayList<Seat> seats=new ArrayList<>();
		SeatDAOImpl seatDAOImpl=new SeatDAOImpl();
		try 
		{
			Iterator<Row> iterator=dataObject.getRows(SeatDAOImpl.TABLE_NAME);
			while(iterator.hasNext())
			{
				Row row=iterator.next();
				Seat seat=seatDAOImpl.getAsBean(row);
				long id=seat.getId();
				seats.add(seat);
			}
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return seats;
	}
	
	private ArrayList<Movie> getMovies(DataObject dataObject)
	{
		ArrayList<Movie> movies=new ArrayList<>();
		MovieDAOImpl movieDAOImpl=new MovieDAOImpl();
		try 
		{
			Iterator<Row> iterator=dataObject.getRows(MovieDAOImpl.TABLE_NAME);
			while(iterator.hasNext())
			{
				Row row=iterator.next();
				movies.add(movieDAOImpl.getAsBean(row));
			}
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return movies;
	}
	
	private ArrayList<ShowDetail> getShows(DataObject dataObject)
	{
		ArrayList<ShowDetail> shows=new ArrayList<>();
		ShowsDAOImpl showsDAOImpl=new ShowsDAOImpl();
		try 
		{
			Iterator<Row> iterator=dataObject.getRows(ShowsDAOImpl.TABLE_NAME);
			while(iterator.hasNext())
			{
				Row row=iterator.next();
				shows.add(showsDAOImpl.getAsBean(row));
			}
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return shows;
	}
	
	private ArrayList<MovieShow> getMovieShows(DataObject dataObject)
	{
		ArrayList<MovieShow> movieShows=new ArrayList<>();
		MovieShowDAOImpl movieShowDAOImpl=new MovieShowDAOImpl();
		try 
		{
			Iterator<Row> iterator=dataObject.getRows(MovieShowDAOImpl.TABLE_NAME);
			while(iterator.hasNext())
			{
				Row row=iterator.next();
				movieShows.add(movieShowDAOImpl.getAsBean(row));
			}
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return movieShows;
	}
	
	private ArrayList<Customer> getCustomer(DataObject dataObject)
	{
		ArrayList<Customer> customers=new ArrayList<>();
		CustomerDAOImpl customerDAOImpl=new CustomerDAOImpl();
		try 
		{
			Iterator<Row> iterator=dataObject.getRows(CustomerDAOImpl.TABLE_NAME);
			while(iterator.hasNext())
			{
				Row row=iterator.next();
				customers.add(customerDAOImpl.getAsBean(row));
			}
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return customers;
	}
	
	private ArrayList<TicketCharge> getTicketCharge(DataObject dataObject,TicketBookingWrapperBean ticketBookingWrapperBean)
	{
		ArrayList<TicketCharge> ticketCharge=new ArrayList<>();
		TicketChargeImpl ticketChargeImpl=new TicketChargeImpl();
		try 
		{
			Iterator<Row> iterator=dataObject.getRows(TicketChargeImpl.TABLE_NAME);
			while(iterator.hasNext())
			{
				Row row=iterator.next();
				TicketCharge charge=ticketChargeImpl.getAsBean(row);
				ticketBookingWrapperBean.getTicketCharges().add(charge.getId());
				ticketCharge.add(charge);
			}
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return ticketCharge;
	}
	
	private ArrayList<Extra> getExtra(DataObject dataObject)
	{
		ArrayList<Extra> extras=new ArrayList<>();
		ExtraDAOImpl extraDAOImpl=new ExtraDAOImpl();
		try 
		{
			Iterator<Row> iterator=dataObject.getRows(ExtraDAOImpl.TABLE_NAME);
			while(iterator.hasNext())
			{
				Row row=iterator.next();
				extras.add(extraDAOImpl.getAsBean(row));
			}
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return extras;
	}
	
	private ArrayList<Category> getCategories(DataObject dataObject)
	{
		ArrayList<Category> categories=new ArrayList<>();
		CategoryDAOImpl categoryDAOImpl=new CategoryDAOImpl();
		try 
		{
			Iterator<Row> iterator=dataObject.getRows(CategoryDAOImpl.TABLE_NAME);
			while(iterator.hasNext())
			{
				Row row=iterator.next();
				Category category=categoryDAOImpl.getAsBean(row);
				categories.add(category);
			}
		} 
		catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return categories;
	}
	
	private ShowSeatWrapperVO executeShowSeatQuery(SelectQuery selectQuery)
	{
		ShowSeatWrapperVO seatWrapperVO=new ShowSeatWrapperVO();
		RelationalAPI relapi = RelationalAPI.getInstance();
		Connection conn = null;
		DataSet ds = null;
		try 
		{
			conn = relapi.getConnection();
			ds = relapi.executeQuery(selectQuery,conn);
			ShowSeatDAOImpl showSeatDAOImpl=new ShowSeatDAOImpl();
			SeatDAOImpl seatDAOImpl=new SeatDAOImpl();
			CategoryDAOImpl categoryDAOImpl=new CategoryDAOImpl();

			while(ds.next())
			{
				ShowSeat showSeat=showSeatDAOImpl.getAsBean(ds);
				seatWrapperVO.getShowseats().add(showSeat);
				Seat seat=seatDAOImpl.getAsBean(ds);
				seatWrapperVO.getSeats().add(seat);
				Category category=categoryDAOImpl.getAsBean(ds);
				seatWrapperVO.getCategories().add(category);
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
		return seatWrapperVO;

	}
	
	private ScreenSeatsVO getScreenSeats(Criteria criteria) 
	{
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(ScreenDAOImpl.TABLE_NAME));
		Join join=movieAppUtil.getJoinQuery(ScreenDAOImpl.TABLE_NAME,SeatDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.SCREEN_ID}, new String[]{DBConstants.SEAT_SCREEN_ID});
		Join categoryJoin=movieAppUtil.getJoinQuery(SeatDAOImpl.TABLE_NAME,CategoryDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.SEAT_CATEGORY_ID}, new String[]{DBConstants.CATEGORY_ID});
		ArrayList<Column> columns=new ArrayList<>();
		columns.add(Column.getColumn(ScreenDAOImpl.TABLE_NAME,"*"));
		columns.add(Column.getColumn(SeatDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(CategoryDAOImpl.TABLE_NAME, "*"));
		selectQueryImpl.addSelectColumns(columns);
		selectQueryImpl.addJoin(join);
		selectQueryImpl.addJoin(categoryJoin);
		selectQueryImpl.setCriteria(criteria);
		return getScreenSeatsVO(selectQueryImpl);
	}
	
	private ScreenSeatsVO getScreenSeatsVO(SelectQueryImpl selectQueryImpl)
	{
		ScreenSeatsVO screenSeatsVO=new ScreenSeatsVO();
		
		try 
		{
			Persistence persist = (Persistence) BeanUtil.lookup("Persistence");
			DataObject dataObject = persist.get(selectQueryImpl);
			screenSeatsVO.setScreens(getScreens(dataObject));
			screenSeatsVO.setSeats(getSeats(dataObject));
			screenSeatsVO.setCategories(getCategories(dataObject));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return screenSeatsVO;
	}

	@Override
	public MovieShowWrapperVO getMovieShowProperties(Criteria criteria) 
	{
		MovieShowWrapperVO msdetails =getMovieShowListSelectQuery(criteria);
		return msdetails;
	}
	
	@Override
	public ShowSeatWrapperVO getShowSeatProperties(Criteria criteria)
	{
		ShowSeatWrapperVO seatWrapperVO=getShowSeats(criteria);
		return seatWrapperVO;
	}

	@Override
	public ScreenSeatsVO getScreenProperties(Criteria criteria) 
	{
		ScreenSeatsVO screenSeatsVO=getScreenSeats(criteria);
		return screenSeatsVO;
	}
	
	private SelectQueryImpl getBookedTicketSelectQuery(Criteria criteria) 
	{
		
		SelectQueryImpl selectQueryImpl=new SelectQueryImpl(Table.getTable(TicketDAOImpl.TABLE_NAME));
		Join join=movieAppUtil.getJoinQuery(TicketDAOImpl.TABLE_NAME,MovieShowDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.TK_MS_ID}, new String[]{DBConstants.MS_ID});
		Join screenJoin=movieAppUtil.getJoinQuery(MovieShowDAOImpl.TABLE_NAME,ScreenDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_SCREEN_ID}, new String[]{DBConstants.SCREEN_ID});
		Join movieJoin=movieAppUtil.getJoinQuery(MovieShowDAOImpl.TABLE_NAME,MovieDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_MOVIE_ID}, new String[]{DBConstants.MOVIE_ID});
		Join showJoin=movieAppUtil.getJoinQuery(MovieShowDAOImpl.TABLE_NAME,ShowsDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_SHOW_ID}, new String[]{DBConstants.SHOW_ID});
		Join customerJoin=movieAppUtil.getJoinQuery(TicketDAOImpl.TABLE_NAME,CustomerDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.TK_CUSTOMER_ID}, new String[]{DBConstants.CUSTOMER_ID});
		Join ticketCharge=movieAppUtil.getJoinQuery(TicketDAOImpl.TABLE_NAME,TicketChargeImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.TICKET_ID}, new String[]{DBConstants.TC_TICKET_ID});
		Join extraJoin=movieAppUtil.getJoinQuery(TicketChargeImpl.TABLE_NAME,ExtraDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.TC_EXTRA_ID}, new String[]{DBConstants.EXTRA_ID});
		Join showSeatJoin=movieAppUtil.getJoinQuery(MovieShowDAOImpl.TABLE_NAME,ShowSeatDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.MS_ID}, new String[]{DBConstants.SS_MS_ID});
		Join seatJoin=movieAppUtil.getJoinQuery(ShowSeatDAOImpl.TABLE_NAME,SeatDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.SS_SEAT_ID}, new String[]{DBConstants.SEAT_ID});
		Join categoryJoin=movieAppUtil.getJoinQuery(SeatDAOImpl.TABLE_NAME,CategoryDAOImpl.TABLE_NAME, Join.LEFT_JOIN, new String[]{DBConstants.SEAT_CATEGORY_ID}, new String[]{DBConstants.CATEGORY_ID});

		ArrayList<Column> columns=new ArrayList<>();
		columns.add(Column.getColumn(TicketDAOImpl.TABLE_NAME,"*"));
		columns.add(Column.getColumn(ScreenDAOImpl.TABLE_NAME,"*"));
		columns.add(Column.getColumn(CategoryDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(SeatDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(MovieShowDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(MovieDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(ShowsDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(CustomerDAOImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(TicketChargeImpl.TABLE_NAME, "*"));
		columns.add(Column.getColumn(ExtraDAOImpl.TABLE_NAME, "*"));

		selectQueryImpl.addSelectColumns(columns);
		selectQueryImpl.addJoin(join);
		selectQueryImpl.addJoin(screenJoin);
		selectQueryImpl.addJoin(movieJoin);
		selectQueryImpl.addJoin(customerJoin);
		selectQueryImpl.addJoin(showJoin);
		selectQueryImpl.addJoin(ticketCharge);
		selectQueryImpl.addJoin(extraJoin);
		selectQueryImpl.addJoin(showSeatJoin);
		selectQueryImpl.addJoin(seatJoin);
		selectQueryImpl.addJoin(categoryJoin);

		selectQueryImpl.setCriteria(criteria);
		return selectQueryImpl;
	}
	
	private TicketWrapperVO getBookedTicketObject(SelectQuery selectQuery)
	{
		TicketWrapperVO ticketWrapperVO=new TicketWrapperVO();
		
		try 
		{
			Persistence persist = (Persistence) BeanUtil.lookup("Persistence");
			DataObject dataObject = persist.get(selectQuery);
			TicketBookingWrapperBean ticketBookingWrapperBean=getTicket(dataObject);
			ticketWrapperVO.setScreens(getScreens(dataObject));
			ticketWrapperVO.setSeats(getSeats(dataObject,ticketBookingWrapperBean));
			ticketWrapperVO.setMovies(getMovies(dataObject));
			ticketWrapperVO.setShowDetails(getShows(dataObject));
			ticketWrapperVO.setMovieShows(getMovieShows(dataObject));
			ticketWrapperVO.setCustomers(getCustomer(dataObject));
			ticketWrapperVO.setTicketCharges(getTicketCharge(dataObject,ticketBookingWrapperBean));
			ticketWrapperVO.setExtras(getExtra(dataObject));
			ticketWrapperVO.setCategories(getCategories(dataObject));
			ticketWrapperVO.setTicket(ticketBookingWrapperBean);
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		} 

		return ticketWrapperVO;
	}

	@Override
	public TicketWrapperVO getTicketProperties(Criteria criteria) 
	{
		SelectQuery selectQuery=getBookedTicketSelectQuery(criteria);
		TicketWrapperVO ticketWrapperVO=getBookedTicketObject(selectQuery);
		return ticketWrapperVO;
	}
	
}
