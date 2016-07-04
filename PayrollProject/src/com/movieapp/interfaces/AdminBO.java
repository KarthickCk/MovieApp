package com.movieapp.interfaces;

import java.util.ArrayList;

import com.movieapp.bean.Category;
import com.movieapp.bean.Customer;
import com.movieapp.bean.Extra;
import com.movieapp.bean.Movie;
import com.movieapp.bean.MovieShow;
import com.movieapp.bean.Screen;
import com.movieapp.bean.Seat;
import com.movieapp.bean.ShowSeat;
import com.movieapp.bean.Ticket;

public interface AdminBO 
{
	
	public String updateScreenSeats(Screen screen, ArrayList<Seat> seats);
	public String changeScreenLayout(Screen screen,ArrayList<Seat> seats);
	public String deleteScreen(String screenID);
	
	public MovieShow updateMovieShow(MovieShow movieShow,String movieShowID);
	public String deleteMovieShow(String id);
	
	public String deleteMovie(String id);
	public Movie updateMovie(Movie movie,String movieID);
	
	public String deleteShow(String showID);
	
	public String deleteCustomer(String id);
	public Customer updateCustomer(String id,Customer customer);
	
	public String deleteExtra(String id);
	public Extra updateExtras(String id,Extra extra);
	
	public String deleteCategory(String id);
	public Category updateCategory(String id,Category category);
	
	public Seat addSeat(Seat seat);
	public String deleteSeat(String id);
	public Seat updateSeat(String id,Seat seat);
	
	public ShowSeat addShowSeat(ShowSeat showSeat);
	public String deleteShowSeat(String id);
	public ShowSeat updateShowSeat(String id,ShowSeat showSeat);
	
	public Ticket addTicket(Ticket ticket);
	public String deleteTicket(String id);
	public Ticket updateTicket(String id,Ticket ticket);
	public Ticket getTicket(String id);
	
}
