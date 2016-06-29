package com.movieapp.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.movieapp.bean.TicketCharge;
import com.movieapp.exception.UserException;
import com.movieapp.vo.MovieShowWrapperVO;
import com.movieapp.vo.ScreenSeatsVO;
import com.movieapp.vo.ShowSeatWrapperVO;
import com.movieapp.vo.TicketWrapperVO;

@Path("/movieapp")
public class MovieAppRestApi 
{
	MovieBOImpl movieBO=new MovieBOImpl();

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
		String message=movieBO.deleteMovie(id);
		return Response.ok(message).build();
	}
	
	@PUT
	@Path("/movies/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMovie(@PathParam("id") String id,String data)
	{			
		HashMap<String, Movie> movieData=new HashMap<>();
		movieData.put("movie", null);
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONObject movieObject=jsonObject.optJSONObject("movie");
			Movie movie=(Movie) getObject(movieObject.toString(), Movie.class);
			movie=movieBO.updateMovie(movie, id);
			movieData.put("movie", movie);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return Response.ok(movieData).build();
	}

	@POST
	@Path("/movies")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMovie(String data) 
	{
		HashMap<String, Movie> movieData=new HashMap<>();
		movieData.put("movie", null);

		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONObject movieObject=jsonObject.optJSONObject("movie");
			Movie movie=(Movie) getObject(movieObject.toString(), Movie.class);
			movie=movieBO.addMovie(movie);
			movieData.put("movie", movie);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response addScreen(String data) 
	{
		Screen screen=movieBO.addScreen(data);
		HashMap<String, Screen> screenData=new HashMap<>();
		screenData.put("screen", screen);
		return Response.ok(screenData).build();
	}

	@GET
	@Path("/screens/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScreenDetails(@PathParam("id") String id) 
	{
		ScreenSeatsVO screenSeatsVO=movieBO.getScreenProperties(id);
		return Response.ok(getScreenResponse(screenSeatsVO)).build();
	}
	
	@PUT
	@Path("/screens/{id}")
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
		String response=getScreenSeatResponse(screenSeatsVO);
		return Response.ok(response).build();
	}
	
	@DELETE
	@Path("/screens/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteScreen(@PathParam("id") String id)
	{
		String message=movieBO.deleteScreen(id);
		return Response.ok(message).build();
	}

	@POST
	@Path("/movieshows")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMovieShows(String data) 
	{
		MovieShow movieShow=movieBO.addMovieshow(data);
		HashMap<String, MovieShow> movieShowData=new HashMap<>();
		movieShowData.put("movieshow", movieShow);
		return Response.ok(movieShowData).build();
	}
	
	@PUT
	@Path("/movieshows/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMovieShows(@PathParam("id")String id,String data) 
	{
		HashMap<String, MovieShow> movieShowData=new HashMap<>();
		movieShowData.put("movieshow", null);

		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONObject movieShowObject=jsonObject.optJSONObject("movieshow");
			MovieShow movieShow=(MovieShow) getObject(movieShowObject.toString(), MovieShow.class);
			movieShow=movieBO.updateMovieShow(movieShow,id);
			movieShowData.put("movieshow", movieShow);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
		String response=getMovieShowResponse(movieShowWrapperVO);
		return Response.ok(response).build();
	}

	@DELETE
	@Path("/movieshows/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMovieShow(@PathParam("id") String id)
	{
		String message=movieBO.deleteMovieShow(id);
		return Response.ok(message).build();
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
		return Response.ok(getMovieShow(movieShowWrapperVO)).build();
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response addShow(@QueryParam("data") String data) 
	{
		ShowDetail showDetail=movieBO.addShow(null);
		HashMap<String, ShowDetail> showData=new HashMap<>();
		showData.put("show", showDetail);
		return Response.ok(showData).build();
	}

	@DELETE
	@Path("/shows/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteShow(@PathParam("id") String id) 
	{
		String response=movieBO.deleteShow(id);
		return Response.ok(response).build();
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCategory(@QueryParam("data") String data) 
	{
		Category category=movieBO.addCategory(null);
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
		String showSeatResponse=getShowSeatResponse(showSeatWrapperVO);
		return Response.ok(showSeatResponse).build();
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response addExtras(@QueryParam("data") String data) 
	{
		//Extra screen=(Extra) getObject(data, Extra.class);
		Extra extra=movieBO.addExtra(null);
		HashMap<String, Extra> extraData=new HashMap<>();
		extraData.put("extra", extra);
		return Response.ok(extraData).build();
	}
	
	@POST
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCustomer(String data) 
	{
		HashMap<String, Customer> customerData=new  HashMap<>();
		customerData.put("customer", null);
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONObject customerObject=jsonObject.optJSONObject("customer");
			Customer customer=(Customer)getObject(customerObject.toString(), Customer.class);
			customer=movieBO.addCustomer(customer);
			customerData.put("customer", customer);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Response.ok(customerData).build();
		
	}
	
	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(@QueryParam("email") String emailID) 
	{
		Customer customer=movieBO.getCustomer(emailID);
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
		customerData.put("customer", customers);
		return Response.ok(customerData).build();		
	}
	
	@POST
	@Path("/tickets")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTicket(@QueryParam("action") String action,String data) 
	{
		String ticket=getBeanJSON(data, "ticket");
		TicketWrapperVO ticketWrapperVO=movieBO.addTicket(ticket);
		return Response.ok(getBookedTicketResponse(ticketWrapperVO)).build();
	}
	
	@GET
	@Path("/tickets/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTicketDetails(@PathParam("id") String id) 
	{
		TicketWrapperVO ticketWrapperVO=movieBO.getBookedTicketProperties(id);
		return Response.ok(getBookedTicketResponse(ticketWrapperVO)).build();
	}

	private String getMovieShowResponse(MovieShowWrapperVO movieShowWrapperVO)
	{
		try
		{
			List<MovieShow> movieShows=movieShowWrapperVO.getMovieShowList();
			List<Movie> movieList=movieShowWrapperVO.getMovieList();
			List<Screen> screenList=movieShowWrapperVO.getScreenList();
			List<ShowDetail> showList=movieShowWrapperVO.getShowList();

			ObjectMapper objectMapper=new ObjectMapper();
			String movieShowData=objectMapper.writer().withRootName("movieshows").writeValueAsString(movieShows);
			String movieData=objectMapper.writer().withRootName("movies").writeValueAsString(movieList);
			String screenData=objectMapper.writer().withRootName("screens").writeValueAsString(screenList);
			String showData=objectMapper.writer().withRootName("shows").writeValueAsString(showList);

			movieShowData=movieShowData.substring(1, movieShowData.length()-1);
			movieData=movieData.substring(1, movieData.length()-1);
			screenData=screenData.substring(1, screenData.length()-1);
			showData=showData.substring(1, showData.length()-1);

			String data="{"+movieShowData+","+movieData+","+showData+","+screenData+"}";
			return data;
		} 
		catch (JsonProcessingException e1) 
		{
			e1.printStackTrace();
		}
		catch (Exception e) {
		}

		return null;
	}
	
	private String getMovieShow(MovieShowWrapperVO movieShowWrapperVO)
	{
		try
		{
			List<MovieShow> movieShows=movieShowWrapperVO.getMovieShowList();
			List<Movie> movieList=movieShowWrapperVO.getMovieList();
			List<Screen> screenList=movieShowWrapperVO.getScreenList();
			List<ShowDetail> showList=movieShowWrapperVO.getShowList();

			ObjectMapper objectMapper=new ObjectMapper();
			String movieShowData=objectMapper.writer().withRootName("movieshows").writeValueAsString(movieShows.get(0));
			String movieData=objectMapper.writer().withRootName("movies").writeValueAsString(movieList.get(0));
			String screenData=objectMapper.writer().withRootName("screens").writeValueAsString(screenList.get(0));
			String showData=objectMapper.writer().withRootName("shows").writeValueAsString(showList.get(0));

			movieShowData=movieShowData.substring(1, movieShowData.length()-1);
			movieData=movieData.substring(1, movieData.length()-1);
			screenData=screenData.substring(1, screenData.length()-1);
			showData=showData.substring(1, showData.length()-1);

			String data="{"+movieShowData+","+movieData+","+showData+","+screenData+"}";
			return data;
		} 
		catch (JsonProcessingException e1) 
		{
			e1.printStackTrace();
		}
		catch (Exception e) {
		}

		return null;
	}

	private String getShowSeatResponse(ShowSeatWrapperVO showSeatWrapperVO)
	{
		try
		{
			List<ShowSeat> showSeats=showSeatWrapperVO.getShowSeats();
			List<Seat> seatLists=showSeatWrapperVO.getSeats();
			List<Category> categories=(List<Category>) showSeatWrapperVO.getCategories();

			ObjectMapper objectMapper=new ObjectMapper();
			String showSeatData=objectMapper.writer().withRootName("showseats").writeValueAsString(showSeats);
			String seatData=objectMapper.writer().withRootName("seats").writeValueAsString(seatLists);
			String categoryData=objectMapper.writer().withRootName("categories").writeValueAsString(categories);

			showSeatData=showSeatData.substring(1, showSeatData.length()-1);
			seatData=seatData.substring(1, seatData.length()-1);
			categoryData=categoryData.substring(1, categoryData.length()-1);

			String data="{"+showSeatData+","+seatData+","+categoryData+"}";
			return data;
		} 
		catch (JsonProcessingException e1) 
		{
			e1.printStackTrace();
		}
		catch (Exception e) {
		}

		return null;
	}

	private String getScreenSeatResponse(ScreenSeatsVO screenSeatsVO)
	{
		List<Screen> screenList=screenSeatsVO.getScreens();
		List<Seat> seatLists=screenSeatsVO.getSeats();
		List<Category> categoryList=screenSeatsVO.getCategorys();

		String screenData=getJSON("screens", screenList);
		String seatData=getJSON("seats", seatLists);
		String categoryData=getJSON("categories", categoryList);

		screenData=screenData.substring(1, screenData.length()-1);
		seatData=seatData.substring(1, seatData.length()-1);
		categoryData=categoryData.substring(1, categoryData.length()-1);

		String data="{"+screenData+","+seatData+","+categoryData+"}";
		return data;
	}
	
	private String getScreenResponse(ScreenSeatsVO screenSeatsVO)
	{
		List<Screen> screenList=screenSeatsVO.getScreens();
		List<Seat> seatLists=screenSeatsVO.getSeats();
		List<Category> categoryList=screenSeatsVO.getCategorys();
		Screen screen=new Screen();
		if(screenList.size()>0)
		{
			screen=screenList.get(0);
		}
		else
		{
			throw new UserException("No screen found",212);
		}
		
		String screenData=getJSON("screens", screen);
		String seatData=getJSON("seats", seatLists);
		String categoryData=getJSON("categories", categoryList);

		screenData=screenData.substring(1, screenData.length()-1);
		seatData=seatData.substring(1, seatData.length()-1);
		categoryData=categoryData.substring(1, categoryData.length()-1);

		String data="{"+screenData+","+seatData+","+categoryData+"}";
		return data;
	}
	
	private String getBookedTicketResponse(TicketWrapperVO ticketWrapperVO)
	{
		List<Screen> screenList=ticketWrapperVO.getScreens();
		List<Seat> seatLists=ticketWrapperVO.getSeats();
		List<Movie> movieLists=ticketWrapperVO.getMovies();
		List<ShowDetail> showLists=ticketWrapperVO.getShowDetails();
		List<MovieShow> movieShows=ticketWrapperVO.getMovieShows();
		List<Customer> customers=ticketWrapperVO.getCustomers();
		List<TicketCharge> ticketCharges=ticketWrapperVO.getTicketCharges();
		List<Extra> extras=ticketWrapperVO.getExtras();
		List<Category> categories=ticketWrapperVO.getCategories();
		List<Ticket> tickets=ticketWrapperVO.getTickets();
		List<Long> ticketChargeIds=ticketWrapperVO.getTicketChargeIDs();
		List<Long> seatIds=ticketWrapperVO.getSeatIDs();

		String screenData=getJSON("screens", screenList);
		String seatData=getJSON("seats", seatLists);
		String movieData=getJSON("movies", movieLists);
		String showData=getJSON("shows", showLists);
		String movieShowData=getJSON("movieshows", movieShows);
		String customerData=getJSON("customers", customers);
		String ticketChargeData=getJSON("ticketcharges", ticketCharges);
		String extraData=getJSON("extras", extras);
		String categoryData=getJSON("categories", categories);
		String ticketData=getJSONWithoutRootName(tickets);
		String ticketChargeIDsData=getJSONWithoutRootName(ticketChargeIds);
		String seatIDsData=getJSONWithoutRootName(seatIds);
		
		screenData=screenData.substring(1, screenData.length()-1);
		seatData=seatData.substring(1, seatData.length()-1);
		movieData=movieData.substring(1, movieData.length()-1);
		showData=showData.substring(1, showData.length()-1);
		movieShowData=movieShowData.substring(1, movieShowData.length()-1);
		customerData=customerData.substring(1, customerData.length()-1);
		ticketChargeData=ticketChargeData.substring(1, ticketChargeData.length()-1);
		extraData=extraData.substring(1, extraData.length()-1);
		categoryData=categoryData.substring(1, categoryData.length()-1);
		ticketData=ticketData.substring(1, ticketData.length()-1);
		//seatIDsData=seatIDsData.substring(1, seatIDsData.length()-1);
		ticketData=getTicketJSON(ticketData,seatIDsData,ticketChargeIDsData).toString();
		ticketData=ticketData.substring(1, ticketData.length()-1);

		String data="{"+ticketData+","+screenData+","+seatData+","+movieData+","+showData+","+movieShowData+","+customerData+","+ticketChargeData+","+extraData+","+categoryData+"}";
		return data;
	}

	private String getJSON(String objectName,Object value)
	{
		try 
		{
			ObjectMapper objectMapper=new ObjectMapper();
			String response=objectMapper.writer().withRootName(objectName).writeValueAsString(value);
			return response;
		} 
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return "";

	}
	
	private JSONObject getTicketJSON(String ticketValue,String seatIds,String ticketChargeIDs)
	{
		try 
		{
			JSONObject jsonObject = new JSONObject(ticketValue);
			JSONArray seatId=new JSONArray(seatIds);
			JSONArray chargeId=new JSONArray(ticketChargeIDs);
			jsonObject.put("seats", seatId);
			jsonObject.put("ticketcharges", chargeId);
			JSONArray array=new JSONArray();
			array.put(jsonObject);
			JSONObject object=new JSONObject();
			object.put("tickets", array);
			return object;
		} catch (JSONException e) 
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	private String getJSONWithoutRootName(Object value)
	{
		try 
		{
			ObjectMapper objectMapper=new ObjectMapper();
			String response=objectMapper.writer().withoutRootName().writeValueAsString(value);
			return response;
		} 
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return "";

	}

	// {"totalCost":760,"movieShowID":"16004","showseats":["16018","16020","16036"],"ticketcharges":[{"extraID":"55004","quantity":2}],"customerID":"131004"}
	private Object getObject(String json,Class classValue)
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
	
	private String getBeanJSON(String data,String key)
	{
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONObject responseObject=jsonObject.optJSONObject(key);
			return responseObject.toString();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	

} 
