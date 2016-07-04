package com.movieapp.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.movieapp.bean.Category;
import com.movieapp.bean.Customer;
import com.movieapp.bean.Extra;
import com.movieapp.bean.Movie;
import com.movieapp.bean.MovieShow;
import com.movieapp.bean.Screen;
import com.movieapp.bean.Seat;
import com.movieapp.bean.ShowDetail;
import com.movieapp.bean.ShowSeat;
import com.movieapp.vo.MovieShowWrapperVO;
import com.movieapp.vo.TicketWrapperVO;
import com.movieapp.wrapperbean.MovieShowsWapperBean;
import com.movieapp.wrapperbean.ScreenWrapper;
import com.movieapp.wrapperbean.TicketAddingWrapperBean;

public interface MovieBO 
{
	public Movie addMovie(Movie movie);
	public List<Movie> getMovies();
	public Movie getMovie(String id);
	
	public ShowDetail addShow(ShowDetail showDetail);
	public List<ShowDetail> getShows();
	public ShowDetail getShow(String id);
	
	public Screen addScreen(ScreenWrapper screenWrapper);
	public Screen getScreen(String id);

	public MovieShow addMovieshow(MovieShowsWapperBean movieShowWapperBean);
	public MovieShowWrapperVO getMovieShowDetails(ArrayList<String> columns,ArrayList<String> values);

	public Category addCategory(Category category);
	public Category getCategory(String id);

	public List<Extra> getExtras();
	public Extra addExtra(Extra extra);
	public Extra getExtra(String id);

	public Customer addCustomer(Customer customer);
	public Customer getCustomer(String mailID,String criteriaColumn);
	
	public Seat getSeat(String id);
	
	public ShowSeat getShowSeat(String id);
	
	public TicketWrapperVO addTicket(TicketAddingWrapperBean ticketAddingWrapperBean);
	public TicketWrapperVO getBookedTicketProperties(String id);

}
