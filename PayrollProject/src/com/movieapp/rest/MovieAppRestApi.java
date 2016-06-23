package com.movieapp.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
import com.movieapp.bean.Ticket;
import com.movieapp.bean.TicketCharge;

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
		return Response.ok(getJSON("movies", moviesList)).build();
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
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONObject movieObject=jsonObject.optJSONObject("movie");
			Movie movie=(Movie) getObject(movieObject.toString(), Movie.class);
			movie=movieBO.updateMovie(movie, id);
			return Response.ok(getJSON("movie", movie)).build();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Response.ok(getJSON("movie", "")).build();
	}

	@POST
	@Path("/movies")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMovie(String data) 
	{
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONObject movieObject=jsonObject.optJSONObject("movie");
			Movie movie=(Movie) getObject(movieObject.toString(), Movie.class);
			movie=movieBO.addMovie(movie);
			return Response.ok(getJSON("movie", movie)).build();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Response.ok(getJSON("movie", "")).build();

	}

	@GET
	@Path("/movies/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovieDetails(@PathParam("id") String id) 
	{

		Movie moviesList=movieBO.getMovie(id);
		return Response.ok(getJSON("movie", moviesList)).build();
	}

	@POST
	@Path("/screens")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addScreen(String data) 
	{
		Screen screen=movieBO.addScreen(data);
		return Response.ok(getJSON("screen", screen)).build();
	}

	@GET
	@Path("/screens/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScreenDetails(@PathParam("id") String id) 
	{
		Properties screenProperties=movieBO.getScreenProperties(id);
		return Response.ok(getScreenResponse(screenProperties)).build();
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
		Properties screenProperties=movieBO.getScreenProperties(null);
		String response=getScreenSeatResponse(screenProperties);
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
		return Response.ok(getJSON("movieshow", movieShow)).build();
	}
	
	@PUT
	@Path("/movieshows/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMovieShows(@PathParam("id")String id,String data) 
	{
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONObject movieShowObject=jsonObject.optJSONObject("movieshow");
			MovieShow movieShow=(MovieShow) getObject(movieShowObject.toString(), MovieShow.class);
			movieShow=movieBO.updateMovieShow(movieShow,id);
			return Response.ok(getJSON("movieshow", movieShow)).build();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Response.ok(getJSON("movieshow", "")).build();

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

		Properties movieShowProperties=movieBO.getMovieShowProperties(columns,values);
		String response=getMovieShowResponse(movieShowProperties);
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

		Properties movieShow=movieBO.getMovieShowDetails(columns,values);
		return Response.ok(getMovieShow(movieShow)).build();
	}

	@GET
	@Path("/shows")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShowList() 
	{
		List<ShowDetail> showList=movieBO.getShows();
		return Response.ok(getJSON("shows", showList)).build();
	}

	@POST
	@Path("/shows")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addShow(@QueryParam("data") String data) 
	{
		ShowDetail showDetail=movieBO.addShow(null);
		return Response.ok(getJSON("show", showDetail)).build();
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
		return Response.ok(getJSON("show", showDetails)).build();
	}
	
	@GET
	@Path("/categories/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategory(@PathParam("id") String id) 
	{
		Category category=movieBO.getCategory(id);
		return Response.ok(getJSON("category", category)).build();
	}

	@POST
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCategory(@QueryParam("data") String data) 
	{
		Category category=movieBO.addCategory(null);
		return Response.ok(getJSON("category", category)).build();
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

		Properties showSeatProperties=movieBO.getShowSeatProperties(columns,values);
		String showSeatResponse=getShowSeatResponse(showSeatProperties);
		return Response.ok(showSeatResponse).build();
	}
	
	@GET
	@Path("/extras")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExtras() 
	{
		List<Extra> extraList=movieBO.getExtras();
		return Response.ok(getJSON("extras", extraList)).build();
	}
	
	@POST
	@Path("/extras")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addExtras(@QueryParam("data") String data) 
	{
		//Extra screen=(Extra) getObject(data, Extra.class);
		Extra extra=movieBO.addExtra(null);
		return Response.ok(getJSON("extra", extra)).build();
	}
	
	@POST
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCustomer(String data) 
	{
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONObject customerObject=jsonObject.optJSONObject("customer");
			Customer customer=(Customer)getObject(customerObject.toString(), Customer.class);
			customer=movieBO.addCustomer(customer);
			return Response.ok(getJSON("customer", customer)).build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Response.ok("").build();
		
	}
	
	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(@QueryParam("email") String emailID) 
	{
		Customer customer=movieBO.getCustomer(emailID);
		ArrayList<Customer> customers=new ArrayList<>();
		customers.add(customer);
		return Response.ok(getJSON("customers", customers)).build();		
	}
	
	@POST
	@Path("/tickets")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTicket(@QueryParam("action") String action,String data) 
	{
		String ticket=getBeanJSON(data, "ticket");
		Properties properties=movieBO.addTicket(ticket);
		return Response.ok(getBookedTicketResponse(properties)).build();
	}
	
	@GET
	@Path("/tickets/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTicketDetails(@PathParam("id") String id) 
	{
		Properties properties=movieBO.getBookedTicketProperties(id);
		return Response.ok(getBookedTicketResponse(properties)).build();
	}

	private String getMovieShowResponse(Properties movieShowProperties)
	{
		try
		{
			List<MovieShow> movieShows=(List<MovieShow>) movieShowProperties.get("MovieShowList");
			List<Movie> movieList=(List<Movie>) movieShowProperties.get("Movie");
			List<Screen> screenList=(List<Screen>) movieShowProperties.get("Screen");
			List<ShowDetail> showList=(List<ShowDetail>) movieShowProperties.get("Show");

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
	
	private String getMovieShow(Properties movieShowProperties)
	{
		try
		{
			List<MovieShow> movieShows=(List<MovieShow>) movieShowProperties.get("MovieShowList");
			List<Movie> movieList=(List<Movie>) movieShowProperties.get("Movie");
			List<Screen> screenList=(List<Screen>) movieShowProperties.get("Screen");
			List<ShowDetail> showList=(List<ShowDetail>) movieShowProperties.get("Show");

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

	private String getShowSeatResponse(Properties showSeatProperties)
	{
		try
		{
			List<MovieShow> showSeats=(List<MovieShow>) showSeatProperties.get("ShowSeats");
			List<Movie> seatLists=(List<Movie>) showSeatProperties.get("Seats");
			List<Category> categories=(List<Category>) showSeatProperties.get("Categories");

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

	private String getScreenSeatResponse(Properties showSeatProperties)
	{
		List<Screen> screenList=(List<Screen>) showSeatProperties.get("Screens");
		List<Seat> seatLists=(List<Seat>) showSeatProperties.get("Seats");
		List<Category> categoryList=(List<Category>) showSeatProperties.get("Categories");

		String screenData=getJSON("screens", screenList);
		String seatData=getJSON("seats", seatLists);
		String categoryData=getJSON("categories", categoryList);

		screenData=screenData.substring(1, screenData.length()-1);
		seatData=seatData.substring(1, seatData.length()-1);
		categoryData=categoryData.substring(1, categoryData.length()-1);

		String data="{"+screenData+","+seatData+","+categoryData+"}";
		return data;
	}
	
	private String getScreenResponse(Properties screenProperties)
	{
		List<Screen> screenList=(List<Screen>) screenProperties.get("Screens");
		List<Seat> seatLists=(List<Seat>) screenProperties.get("Seats");
		List<Category> categoryList=(List<Category>) screenProperties.get("Categories");
		Screen screen=new Screen();
		if(screenList.size()>0)
		{
			screen=screenList.get(0);
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
	
	private String getBookedTicketResponse(Properties showSeatProperties)
	{
		List<Screen> screenList=(List<Screen>) showSeatProperties.get("Screens");
		List<Seat> seatLists=(List<Seat>) showSeatProperties.get("Seats");
		List<Movie> movieLists=(List<Movie>) showSeatProperties.get("Movies");
		List<ShowDetail> showLists=(List<ShowDetail>) showSeatProperties.get("Shows");
		List<MovieShow> movieShows=(List<MovieShow>) showSeatProperties.get("MovieShows");
		List<Customer> customers=(List<Customer>) showSeatProperties.get("Customers");
		List<TicketCharge> ticketCharges=(List<TicketCharge>) showSeatProperties.get("TicketCharges");
		List<Extra> extras=(List<Extra>) showSeatProperties.get("Extras");
		List<Category> categories=(List<Category>) showSeatProperties.get("Categories");
		List<Ticket> tickets=(List<Ticket>) showSeatProperties.get("Tickets");
		List<Long> ticketChargeIds=(List<Long>) showSeatProperties.get("TicketChargeIDs");
		List<Long> seatIds=(List<Long>) showSeatProperties.get("SeatIDs");

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
