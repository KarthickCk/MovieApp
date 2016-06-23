package com.movieapp.rest;

public enum MovieBO 
{
	INSTANCE;
//	MovieAppUtil movieAppUtil=MovieAppUtil.INSTANCES;
//	
//	public long addMovie()
//	{
//		long movieID=MovieDAOFactory.getMovieDAO().insert(getMovie());
//		return movieID;
//	}
//
//	public List<Movie> getMoviesList()
//	{
//		return MovieDAOFactory.getMovieDAO().retrieveAll();
//	}
//
//	public List<Movie> getMoviesList(List<Long> ids)
//	{
//		int size=ids.size();
//		ArrayList<Movie> movieList=new ArrayList<>();
//		for(int i=0;i<size;i++)
//		{
//			movieList.add(MovieDAOFactory.getMovieDAO().retrieveWithID(String.valueOf(ids.get(i))));
//		}
//		return movieList;
//	}
//
//	public Movie getMovieDetails(long id)
//	{
//		return MovieDAOFactory.getMovieDAO().retrieveWithID(String.valueOf(id));
//	}
//
//	public long addShow()
//	{
//		long showID=MovieDAOFactory.getShowDAO().insert(getShowDetail());
//		return showID;
//	}
//	
//	public String deleteShow(String showID)
//	{
//		String responseMessage="Ticket for the screen is booked";
//		ShowsDAOImpl ShowsDAOImpl=MovieDAOFactory.getShowDAO();
//		if(ShowsDAOImpl.isShowDeleteable(showID))
//		{
//			//screenDAOImpl.delete(screenID);
//			responseMessage="Deleted successfully";
//		}
//		return responseMessage; 
//	}
//
//	public List<ShowDetail> getShowList()
//	{
//		return MovieDAOFactory.getShowDAO().retrieveAll();
//	}
//
//	public ShowDetail getShowDetails(long id)
//	{
//		return MovieDAOFactory.getShowDAO().retrieveWithID(String.valueOf(id));
//	}
//
//	public List<ShowDetail> getShowList(List<Long> ids)
//	{
//		int size=ids.size();
//		ArrayList<ShowDetail> showList=new ArrayList<>();
//		for(int i=0;i<size;i++)
//		{
//			showList.add(MovieDAOFactory.getShowDAO().retrieveWithID(String.valueOf(ids.get(i))));
//		}
//		return showList;
//	}
//
//	public void addScreen()
//	{
//		Screen screen=getScreen();
//		int row=screen.getScreenRows();
//		int column=screen.getScreenColumns();
//		long screenID=MovieDAOFactory.getScreenDAO().insert(screen);
//		String screenName=screen.getScreenName();
//		for(int i=0;i<row;i++)
//		{
//			for(int j=0;j<column;j++)
//			{
//				addSeat(screenID,5049,i,j,screenName);
//			}
//		}
//	}
//	
//	public String deleteScreen(String screenID)
//	{
//		String responseMessage="Ticket for the screen is booked";
//		ScreenDAOImpl screenDAOImpl=MovieDAOFactory.getScreenDAO();
//		if(screenDAOImpl.isScreenShowDeleteable(screenID))
//		{
//			screenDAOImpl.delete(screenID);
//			responseMessage="Deleted successfully";
//		}
//		return responseMessage; 
//	}
//
//	public Properties getMovieShowDetails(ArrayList<String> columns,ArrayList<String> values)
//	{
//		UserBOImpl userBO=new UserBOImpl();
//		Criteria criteria=movieAppUtil.getCriteria(MovieShowDAOImpl.TABLE_NAME, getMovieShowsColumnsToFilter(columns), values);
//		return userBO.getMovieShowProperties(criteria);
//	}
//
//	public Properties getScreenProperties()
//	{
//		UserBOImpl userBO=new UserBOImpl();
//		return userBO.getScreenProperties(null);
//	}
//
//	public List<Screen> getScreenList(List<Long> ids)
//	{
//		int size=ids.size();
//		ArrayList<Screen> screenList=new ArrayList<>();
//		for(int i=0;i<size;i++)
//		{
//			screenList.add(MovieDAOFactory.getScreenDAO().retrieveWithID(String.valueOf(ids.get(i))));
//		}
//		return screenList;
//	}
//	
//	public List<Seat> getSeatList(List<Long> ids)
//	{
//		int size=ids.size();
//		ArrayList<Seat> seatList=new ArrayList<>();
//		for(int i=0;i<size;i++)
//		{
//			seatList.add(MovieDAOFactory.getSeatDAO().retrieveWithID(String.valueOf(ids.get(i))));
//		}
//		return seatList;
//	}
//
//	public Screen getScreenDetails(long id)
//	{
//		return MovieDAOFactory.getScreenDAO().retrieveWithID(String.valueOf(id));
//	}
//
//	public long addMovieshows()
//	{
//		MovieShow movieShow=getMovieShow();
//		long screenID=movieShow.getScreenID();
//		long movieShowID=MovieDAOFactory.getMovieShowDAO().insert(movieShow);
//		addShowSeats(screenID, movieShowID);
//		return movieShowID;
//	}
//	
//	public String deleteMovieShow(String id)
//	{
//		String responseMessage="Ticket for the show is booked";
//		MovieShowDAOImpl movieShowDAOImpl=MovieDAOFactory.getMovieShowDAO();
//		if(movieShowDAOImpl.isMovieShowDeleteable(id))
//		{
//			movieShowDAOImpl.delete(id);
//			responseMessage="Deleted successfully";
//		}
//		return responseMessage;
//	}
//	
//	private List<Long> getSeatIDs(long screenID)
//	{
//		SeatDAOImpl seatDAOImpl=MovieDAOFactory.getSeatDAO();
//		ArrayList<String> columns=new ArrayList<>();
//		columns.add(DBConstants.SCREEN_ID);
//		ArrayList<String> values=new ArrayList<>();
//		values.add(String.valueOf(screenID));
//		seatDAOImpl.setCriteriaColumnNames(columns);
//		seatDAOImpl.setCriteriaValues(values);
//		List<Long> seatIDs=seatDAOImpl.getDistinctIDs(DBConstants.SEAT_ID);
//		return seatIDs;
//	}
//	
//	private void addShowSeats(long screenID,long movieShowID)
//	{
//		List<Long> showSeats=getSeatIDs(screenID);
//		int size=showSeats.size();
//		for(int i=0;i<size;i++)
//		{
//			ShowSeatDAOImpl seatDAOImpl=MovieDAOFactory.getShowSeatDAO();
//			seatDAOImpl.insert(getShowSeat(showSeats.get(i), movieShowID));
//		}
//	}
//
//	public Properties getMovieShowProperties(ArrayList<String> columns,ArrayList<String> values)
//	{
//		UserBOImpl userBO=new UserBOImpl();
//		Criteria criteria=movieAppUtil.getCriteria(MovieShowDAOImpl.TABLE_NAME, getMovieShowsColumnsToFilter(columns), values);
//		return userBO.getMovieShowProperties(criteria);
//	}
//
//	public long addCategory()
//	{
//		long id=MovieDAOFactory.getCategoryDAO().insert(getCategory());
//		return id;
//	}
//
//	public Properties getShowSeatProperties(ArrayList<String> columns,ArrayList<String> values)
//	{
//		UserBOImpl userBO=new UserBOImpl();
//		Criteria criteria=movieAppUtil.getCriteria(ShowSeatDAOImpl.TABLE_NAME, getShowSeatsColumnsToFilter(columns), values);
//		return userBO.getShowSeatProperties(criteria);
//	}
//	
//	public void addSeat(long screenID,long categoryID,int row,int column,String screenName)
//	{
//		MovieDAOFactory.getSeatDAO().insert(getSeats(screenID, categoryID,row,column,screenName));
//	}
//	
//	public List<Extra> getExtrasList()
//	{
//		return MovieDAOFactory.getExtasDAO().retrieveAll();
//	}
//	
//	public long addExtra()
//	{
//		long extraId=MovieDAOFactory.getExtasDAO().insert(getExtra());
//		return extraId;
//	}
//	
//	public long addCustomer(Customer customer)
//	{
//		long customerID=MovieDAOFactory.getCustomerDAO().insert(getCustomer());
//		return customerID;
//	}
//	
//	public long addTicket(String ticket)
//	{
//		long customerID=MovieDAOFactory.getTicketDAO().insert(getTicket(ticket));
//		return customerID;
//	}
//	
//	private Movie getMovie()
//	{
//		Movie movie=new Movie();
//		movie.setMovieName("Mangatha");
//		movie.setCategory("Category");
//		movie.setCertificate("U");
//		movie.setDescription("Nalla movie");
//		movie.setGenre("Etho onnu");
//		movie.setImageURL("www.google.com/Mangatha");
//		movie.setReleasedDate("14-06-2016");
//		movie.setLanguage("Tamil");
//		movie.setDuration("120 mins");
//		return movie;
//	}
//
//	private ShowDetail getShowDetail()
//	{
//		ShowDetail showDetail=new ShowDetail();
//		showDetail.setShowName("Evening");
//		showDetail.setStartTime("9.30 PM");
//		showDetail.setEndTime("12.00 AM");
//		return showDetail;
//	}
//
//	private Screen getScreen()
//	{
//		Screen screen=new Screen();
//		screen.setScreenName("Screen 2");
//		screen.setScreenRows(5);
//		screen.setScreenColumns(5);
//		return screen;
//	}
//
//	private MovieShow getMovieShow()
//	{
//		MovieShow movieShow=new MovieShow();
//		movieShow.setMovieID(11004);
//		movieShow.setScreenID(6004);
//		movieShow.setShowID(8004);
//		movieShow.setMovieDate("14-06-2016");
//		return movieShow;
//	}
//
//	private Category getCategory()
//	{
//		Category category=new Category();
//		category.setCategoryName("FirstClass");
//		category.setFare(120);
//		return category;
//	}
//
//	private Seat getSeats(long screenID,long categoryID,int row,int column,String screenName)
//	{
//		Seat  seat=new Seat();
//		seat.setCategoryID(categoryID);
//		seat.setScreenID(screenID);
//		seat.setRowNumber(row);
//		seat.setColumnNumber(column);
//		seat.setScreenName(screenName);
//		seat.setStatus(true);
//		return seat;
//	}
//
//	public ShowSeat getShowSeat(long seatID,long movieShowID)
//	{
//		ShowSeat showSeat=new ShowSeat();
//		showSeat.setMovieShowID(movieShowID);
//		showSeat.setSeatID(seatID);
//		return showSeat;
//	}
//	
//	public Extra getExtra()
//	{
//		Extra extra=new Extra();
//		extra.setName("Extra 1");
//		extra.setCost(200);
//		return extra;
//	}
//	
//	public Customer getCustomer()
//	{
//		Customer customer=new Customer();
//		customer.setName("Karthick");
//		customer.setPhoneNumber("9790198648");
//		customer.setEmail("karthickck0@gmail.com");
//		return customer;
//	}
//	
//	private Ticket getTicket(String ticketString)
//	{
//		Ticket ticket=new Ticket();
//		try
//		{
//			JSONObject ticketJson=new JSONObject(ticketString);
//			float ticketCost=Float.parseFloat(ticketJson.optString("totalCost"));
//			long movieShowID=Long.parseLong(ticketJson.optString(""));
//			long customerID=Long.parseLong(ticketJson.optString("customerID"));
//			ticket.setTotalCost(ticketCost);
//			ticket.setMovieShowID(movieShowID);
//			ticket.setCustomerID(customerID);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		return ticket;
//		
//	}
//	
//	private ArrayList<String> getMovieShowsColumnsToFilter(ArrayList<String> columns)
//	{
//		int size=columns.size();
//		ArrayList<String> column=new ArrayList<>();
//		for(int i=0;i<size;i++)
//		{
//			switch (columns.get(i)) 
//			{
//			case "date":
//				column.add(DBConstants.MS_DATE);
//				break;
//				
//			case "movie":
//				column.add(DBConstants.MS_MOVIE_ID);
//				break;
//				
//			case "screen":
//				column.add(DBConstants.MS_SCREEN_ID);
//				break;
//				
//			case "id":
//				column.add(DBConstants.MS_ID);
//				break;
//
//			default:
//				break;
//			}
//		}
//		return column;
//	}
//	
//	private ArrayList<String> getShowSeatsColumnsToFilter(ArrayList<String> columns)
//	{
//		int size=columns.size();
//		ArrayList<String> column=new ArrayList<>();
//		for(int i=0;i<size;i++)
//		{
//			switch (columns.get(i)) 
//			{
//			case "movieShowID":
//				column.add(DBConstants.MS_ID);
//				break;
//				
//			case "movie":
//				column.add(DBConstants.MS_MOVIE_ID);
//				break;
//				
//			case "screen":
//				column.add(DBConstants.MS_SCREEN_ID);
//				break;
//
//			default:
//				break;
//			}
//		}
//		return column;
//	}
//

}
