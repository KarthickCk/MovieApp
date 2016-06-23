package com.movieapp.interfaces;

import java.util.List;
import java.util.Properties;

import com.movieapp.bean.Category;
import com.movieapp.bean.Customer;
import com.movieapp.bean.Extra;
import com.movieapp.bean.Movie;
import com.movieapp.bean.MovieShow;
import com.movieapp.bean.Screen;
import com.movieapp.bean.ShowDetail;

public interface MovieBO 
{
	public Movie addMovie(Movie movie);
	public List<Movie> getMovies();
	public Movie getMovie(String id);
	public String deleteMovie(String id);
	
	public ShowDetail addShow(ShowDetail showDetail);
	public String deleteShow(String showID);
	public List<ShowDetail> getShows();
	public ShowDetail getShow(String id);
	
	public Screen addScreen(String data);
	public String deleteScreen(String screenID);
	public Screen getScreen(String id);

	public MovieShow addMovieshow(String data);
	public String deleteMovieShow(String id);
	
	public Category addCategory(Category category);
	public Category getCategory(String id);

	
	public List<Extra> getExtras();
	public Extra addExtra(Extra extra);
	
	public Customer addCustomer(Customer customer);
	public Customer getCustomer(String mailID);

	
	public Properties addTicket(String ticket);
	public Properties getBookedTicketProperties(String id);

}
