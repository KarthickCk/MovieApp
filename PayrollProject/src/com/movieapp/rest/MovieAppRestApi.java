package com.movieapp.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.movieapp.bdao.AdminBOImpl;
import com.movieapp.bdao.MovieBOImpl;
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
import com.movieapp.constants.DBConstants;
import com.movieapp.exception.UserException;
import com.movieapp.vo.MovieShowWrapperVO;
import com.movieapp.vo.ScreenSeatsVO;
import com.movieapp.vo.ShowSeatWrapperVO;
import com.movieapp.vo.TicketWrapperVO;
import com.movieapp.wrapperbean.AddTicketBean;
import com.movieapp.wrapperbean.CategoryWrapperBean;
import com.movieapp.wrapperbean.CustomerWrapperBean;
import com.movieapp.wrapperbean.ExtraWrapperBean;
import com.movieapp.wrapperbean.MovieShowWrapperBean;
import com.movieapp.wrapperbean.MovieShowsWapperBean;
import com.movieapp.wrapperbean.MovieWrapperBean;
import com.movieapp.wrapperbean.ScreenWrapper;
import com.movieapp.wrapperbean.SeatWrapperBean;
import com.movieapp.wrapperbean.ShowSeatWrapperBean;
import com.movieapp.wrapperbean.ShowWrapperBean;
import com.movieapp.wrapperbean.TicketAddingWrapperBean;
import com.movieapp.wrapperbean.TicketWrapperBean;

@Path("/movieapp")
public class MovieAppRestApi 
{
	MovieBOImpl movieBO=new MovieBOImpl();
	AdminBOImpl adminBO=new AdminBOImpl();

	@GET 		 
	@Path("/movies")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMoviesList() 
	{
		List<Movie> moviesList=movieBO.getMovies();
		HashMap<String, List<Movie>> movieData=new HashMap<>();
		movieData.put("movies", moviesList);
		return Response.ok(movieData).build();
	}
	
	@DELETE
	@Path("/movies/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMovie(@PathParam("id") String id)
	{
		String message=adminBO.deleteMovie(id);
		return Response.ok(null).build();
	}
	
	@PUT
	@Path("/movies/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMovie(@PathParam("id") String id,MovieWrapperBean movieWrapperBean)
	{			
		HashMap<String, Movie> movieData=new HashMap<>();
		Movie movie=movieWrapperBean.getMovie();
		movie=movieBO.updateMovie(movie, id);
		movieData.put("movie", movie);
		return Response.ok(movieData).build();
	}

	@POST
	@Path("/movies")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMovie(MovieWrapperBean movieWrapperBean) 
	{
		HashMap<String, Movie> movieData=new HashMap<>();
		Movie movie=movieWrapperBean.getMovie();
		movie=movieBO.addMovie(movie);
		movieData.put("movie", movie);
		return Response.ok(movieData).build();

	}

	@GET
	@Path("/movies/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovieDetails(@PathParam("id") String id) 
	{
		HashMap<String, Movie> movieData=new HashMap<>();

		Movie movie=movieBO.getMovie(id);
		movieData.put("movie", movie);

		if(movie==null)
		{
			throw new UserException("No movie found",212);
		}
		return Response.ok(movieData).build();
	}

	@POST
	@Path("/screens")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addScreen(ScreenWrapper screenWrapper) 
	{
		Screen screen=movieBO.addScreen(screenWrapper);
		HashMap<String, Screen> screenData=new HashMap<>();
		screenData.put("screen", screen);
		return Response.ok(screen).build();
	}

	@GET
	@Path("/screens/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScreenDetails(@PathParam("id") String id) 
	{
		ScreenSeatsVO screenSeatsVO=movieBO.getScreenProperties(id);
		return Response.ok(screenSeatsVO).build();
	}
	
	@PUT
	@Path("/screens/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateScreen(@QueryParam("action") String action,@PathParam("id") String id,String data) 
	{
		boolean isChangeLayout = true;
		if(action!=null)
		{
			isChangeLayout=false;
		}
		
		String response=movieBO.updateScreenLayout(data,isChangeLayout);
		return Response.ok(response).build();
	}

	@GET
	@Path("/screens")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScreenList() 
	{
		ScreenSeatsVO screenSeatsVO=movieBO.getScreenProperties(null);
		//String response=getScreenSeatResponse(screenSeatsVO);
		return Response.ok(screenSeatsVO).build();
	}
	
	@DELETE
	@Path("/screens/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteScreen(@PathParam("id") String id)
	{
		String message=adminBO.deleteScreen(id);
		return Response.ok(null).build();
	}

	@POST
	@Path("/movieshows")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMovieShows(MovieShowsWapperBean movieShowWapperBean) 
	{
		MovieShow movieShow=movieBO.addMovieshow(movieShowWapperBean);
		HashMap<String, MovieShow> movieShowData=new HashMap<>();
		movieShowData.put("movieshow", movieShow);
		return Response.ok(movieShowData).build();
	}
	
	@PUT
	@Path("/movieshows/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMovieShows(@PathParam("id")String id,MovieShowWrapperBean movieShowWrapperBean) 
	{
		HashMap<String, MovieShow> movieShowData=new HashMap<>();
		MovieShow movieShow=movieShowWrapperBean.getMovieShow();
		movieShow=movieBO.updateMovieShow(movieShow,id);
		movieShowData.put("movieshow", movieShow);
		return Response.ok(movieShowData).build();
	}

	@GET
	@Path("/movieshows")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovieShowsWithDate(@QueryParam("date") String date,@QueryParam("movie") String movie,@QueryParam("screen") String screen) 
	{
		ArrayList<String> values=new ArrayList<String>();
		ArrayList<String> columns=new ArrayList<>();

		if(date!=null)
		{
			columns.add("date");
			values.add(date);
		}
		if(movie!=null)
		{
			columns.add("movie");
			values.add(movie);
		}
		if(screen!=null)
		{
			columns.add("screen");
			values.add(screen);
		}

		MovieShowWrapperVO movieShowWrapperVO=movieBO.getMovieShowProperties(columns,values);
		//String response=getMovieShowResponse(movieShowWrapperVO);
		//HashMap<String, MovieShowWrapperVO> movieShow=new HashMap<>();
		//movieShow.put("movieshows", movieShowWrapperVO);
		return Response.ok(movieShowWrapperVO).build();
	}

	@DELETE
	@Path("/movieshows/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMovieShow(@PathParam("id") String id)
	{
		String message=adminBO.deleteMovieShow(id);
		return Response.ok(null).build();
	}

	@GET
	@Path("/movieshows/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovieShowDetails(@PathParam("id") String id) 
	{		
		ArrayList<String> values=new ArrayList<String>();
		ArrayList<String> columns=new ArrayList<>();
		columns.add("id");
		values.add(id);

		MovieShowWrapperVO movieShowWrapperVO=movieBO.getMovieShowDetails(columns,values);
		return Response.ok(movieShowWrapperVO).build();
	}

	@GET
	@Path("/shows")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShowList() 
	{
		List<ShowDetail> showList=movieBO.getShows();
		HashMap<String, List<ShowDetail>> showDetail=new HashMap<>();
		showDetail.put("shows", showList);
		return Response.ok(showDetail).build();
	}

	@POST
	@Path("/shows")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addShow(ShowWrapperBean showWrapperBean) 
	{
		ShowDetail showDetail=movieBO.addShow(showWrapperBean.getShow());
		HashMap<String, ShowDetail> showData=new HashMap<>();
		showData.put("show", showDetail);
		return Response.ok(showWrapperBean).build();
	}

	@DELETE
	@Path("/shows/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteShow(@PathParam("id") String id) 
	{
		String response=adminBO.deleteShow(id);
		return Response.ok(null).build();
	}

	@GET
	@Path("/shows/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShowDetails(@PathParam("id") String id) 
	{
		ShowDetail showDetails=movieBO.getShow(id);
		HashMap<String, ShowDetail> showData=new HashMap<>();
		showData.put("show", showDetails);
		return Response.ok(showData).build();
	}
	
	@GET
	@Path("/categories/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategory(@PathParam("id") String id) 
	{
		Category category=movieBO.getCategory(id);
		HashMap<String, Category> categoryData=new HashMap<>();
		categoryData.put("category", category);
		return Response.ok(categoryData).build();
	}

	@POST
	@Path("/categories")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCategory(CategoryWrapperBean categoryWrapperBean) 
	{
		Category category=movieBO.addCategory(categoryWrapperBean.getCategory());
		HashMap<String, Category> categoryData=new HashMap<>();
		categoryData.put("category", category);
		return Response.ok(categoryData).build();
	}
	
	@DELETE
	@Path("/categories/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCategory(@PathParam("id") String id) 
	{
		String response=adminBO.deleteCategory(id);
		return Response.ok(response).build();
	}
	
	@PUT
	@Path("/categories/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCategory(@PathParam("id")String id,CategoryWrapperBean categoryWrapperBean) 
	{
		Category category=categoryWrapperBean.getCategory();
		category=adminBO.updateCategory(id, category);
		HashMap<String, Category> categoryData=new HashMap<>();
		categoryData.put("category", category);
		return Response.ok(categoryData).build();
	}

	@GET
	@Path("/showseats")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShowSeats(@QueryParam("movieShowId") String movieShowID) 
	{
		ArrayList<String> values=new ArrayList<String>();
		ArrayList<String> columns=new ArrayList<>();

		if(movieShowID!=null)
		{
			columns.add("movieShowID");
			values.add(movieShowID);
		}

		ShowSeatWrapperVO showSeatWrapperVO=movieBO.getShowSeatProperties(columns,values);
		//String showSeatResponse=getShowSeatResponse(showSeatWrapperVO);
		return Response.ok(showSeatWrapperVO).build();
	}
	
	@GET
	@Path("/extras")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExtras() 
	{
		List<Extra> extraList=movieBO.getExtras();
		HashMap<String, List<Extra>> extraData=new HashMap<>();
		extraData.put("extras", extraList);
		return Response.ok(extraData).build();
	}
	
	@POST
	@Path("/extras")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addExtras(String data) 
	{
		Extra extra=movieBO.addExtra(null);
		HashMap<String, Extra> extraData=new HashMap<>();
		extraData.put("extras", extra);
		return Response.ok(extraData).build();
	}
	
	@DELETE
	@Path("/extras/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteExtras(@PathParam("id") String id) 
	{
		String response=adminBO.deleteExtra(id);
		return Response.ok(response).build();
	}
	
	@GET
	@Path("/extras/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExtra(@PathParam("id") String id) 
	{
		Extra extra=movieBO.getExtra(id);
		HashMap<String, Extra> extraData=new HashMap<>();
		extraData.put("extras", extra);
		return Response.ok(extraData).build();
	}
	
	@PUT
	@Path("/extras/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateExtras(@PathParam("id")String id,ExtraWrapperBean extraWrapperBean) 
	{
		Extra extra=extraWrapperBean.getExtras();
		extra=adminBO.updateExtras(id, extra);
		HashMap<String, Extra> extraData=new HashMap<>();
		extraData.put("extras", extra);
		return Response.ok(extraData).build();
	}
	
	@POST
	@Path("/customers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCustomer(CustomerWrapperBean customerWrapperBean) 
	{
		HashMap<String, Customer> customerData=new  HashMap<>();
		Customer customer=customerWrapperBean.getCustomers();
		customer=movieBO.addCustomer(customer);
		customerData.put("customers", customer);
		return Response.ok(customerData).build();
	}
	
	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(@QueryParam("email") String emailID) 
	{
		Customer customer=movieBO.getCustomer(emailID,DBConstants.CUSTOMER_EMAIL);
		ArrayList<Customer> customers=new ArrayList<>();
		if(customers.size()==0)
		{
			try 
			{
				throw new UserException("No user found",0);
			} 
			catch (UserException e) 
			{
				e.printStackTrace();
			}
		}
		customers.add(customer);
		HashMap<String, ArrayList<Customer>> customerData=new  HashMap<>();
		customerData.put("customers", customers);
		return Response.ok(customerData).build();		
	}
	
	@GET
	@Path("/customers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerWithID(@PathParam("id") String id) 
	{
		Customer customer=movieBO.getCustomer(id,DBConstants.CUSTOMER_ID);
		ArrayList<Customer> customers=new ArrayList<>();
		if(customers.size()==0)
		{
			try 
			{
				throw new UserException("No user found",0);
			} 
			catch (UserException e) 
			{
				e.printStackTrace();
			}
		}
		customers.add(customer);
		HashMap<String, ArrayList<Customer>> customerData=new  HashMap<>();
		customerData.put("customers", customers);
		return Response.ok(customerData).build();		
	}
	
	@DELETE
	@Path("/customers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCustomer(@PathParam("id") String id) 
	{
		String response=adminBO.deleteCustomer(id);
		return Response.ok(response).build();
	}
	
	@PUT
	@Path("/customers/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(@PathParam("id")String id,CustomerWrapperBean customerWrapperBean) 
	{
		Customer customer=customerWrapperBean.getCustomers();
		customer=adminBO.updateCustomer(id, customer);
		HashMap<String, Customer> customerData=new HashMap<>();
		customerData.put("customers", customer);
		return Response.ok(customerData).build();
	}
	
	@GET
	@Path("/showseats/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShowSeat(@PathParam("id") String id) 
	{
		ShowSeat showSeat=movieBO.getShowSeat(id);
		HashMap<String, ShowSeat> showSeatData=new HashMap<>();
		showSeatData.put("showseats", showSeat);
		return Response.ok(showSeatData).build();
	}

	@POST
	@Path("/showseats")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addShowSeat(ShowSeatWrapperBean showSeatWrapperBean) 
	{
		ShowSeat showSeat=showSeatWrapperBean.getShowseats();
		showSeat=adminBO.addShowSeat(showSeat);
		HashMap<String, ShowSeat> showSeatData=new HashMap<>();
		showSeatData.put("showseats", showSeat);
		return Response.ok(showSeatData).build();
	}
	
	@DELETE
	@Path("/showseats/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteShowSeat(@PathParam("id") String id) 
	{
		String response=adminBO.deleteShowSeat(id);
		return Response.ok(response).build();
	}
	
	@PUT
	@Path("/showseats/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateShowSeat(@PathParam("id")String id,ShowSeatWrapperBean showSeatWrapperBean) 
	{
		ShowSeat showSeat=showSeatWrapperBean.getShowseats();
		showSeat=adminBO.updateShowSeat(id, showSeat);
		HashMap<String, ShowSeat> showSeatData=new HashMap<>();
		showSeatData.put("showseats", showSeat);
		return Response.ok(showSeatData).build();
	}
	
	@GET
	@Path("/seats/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSeat(@PathParam("id") String id) 
	{
		Seat seat=movieBO.getSeat(id);
		HashMap<String, Seat> seatData=new HashMap<>();
		seatData.put("seats", seat);
		return Response.ok(seatData).build();
	}

	@POST
	@Path("/seats")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSeat(SeatWrapperBean seatWrapperBean) 
	{
		Seat seat=seatWrapperBean.getSeats();
		seat=adminBO.addSeat(seat);
		HashMap<String, Seat> seatData=new HashMap<>();
		seatData.put("seats", seat);
		return Response.ok(seatData).build();
	}
	
	@DELETE
	@Path("/seats/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSeat(@PathParam("id") String id) 
	{
		String response=adminBO.deleteSeat(id);
		return Response.ok(response).build();
	}
	
	@PUT
	@Path("/seats/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSeat(@PathParam("id")String id,SeatWrapperBean seatWrapperBean) 
	{
		Seat seat=seatWrapperBean.getSeats();
		seat=adminBO.updateSeat(id, seat);
		HashMap<String, Seat> seatData=new HashMap<>();
		seatData.put("seats", seat);
		return Response.ok(seatData).build();
	}
	
	@POST
	@Path("/tickets")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTicket(@QueryParam("action") String action,AddTicketBean addTicketBean) 
	{
		TicketAddingWrapperBean ticketAddingWrapperBean=addTicketBean.getTicket();
		TicketWrapperVO ticketWrapperVO=movieBO.addTicket(ticketAddingWrapperBean);
		return Response.ok(ticketWrapperVO).build();
	}
	
	@GET
	@Path("/tickets/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTicketDetails(@PathParam("id") String id) 
	{
		TicketWrapperVO ticketWrapperVO=movieBO.getBookedTicketProperties(id);
		return Response.ok(ticketWrapperVO).build();
	}
	
	@DELETE
	@Path("/tickets/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTicket(@PathParam("id") String id) 
	{
		String response=adminBO.deleteTicket(id);
		return Response.ok(response).build();
	}
	
	@PUT
	@Path("/tickets/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTicket(@PathParam("id")String id,TicketWrapperBean ticketWrapperBean) 
	{
		Ticket ticket=ticketWrapperBean.getTickets();
		ticket=adminBO.updateTicket(id, ticket);
		HashMap<String, Ticket> ticketData=new HashMap<>();
		ticketData.put("tickets", ticket);
		return Response.ok(ticketData).build();
	}
	
} 
