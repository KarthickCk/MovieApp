package com.movieapp.vo;

import java.util.ArrayList;

import com.movieapp.bean.Category;
import com.movieapp.bean.Customer;
import com.movieapp.bean.Extra;
import com.movieapp.bean.Movie;
import com.movieapp.bean.MovieShow;
import com.movieapp.bean.Screen;
import com.movieapp.bean.Seat;
import com.movieapp.bean.ShowDetail;
import com.movieapp.bean.TicketCharge;
import com.movieapp.wrapperbean.TicketBookingWrapperBean;

public class TicketWrapperVO 
{
	ArrayList<Seat> seats=new ArrayList<>();
	ArrayList<Category> categories=new ArrayList<>();
	ArrayList<TicketCharge> ticketCharges=new ArrayList<>();
	ArrayList<Extra> extras=new ArrayList<>();
	ArrayList<Customer> customers=new ArrayList<>();
	ArrayList<MovieShow> movieShows=new ArrayList<>();
	ArrayList<Screen> screens=new ArrayList<>();
	ArrayList<ShowDetail> showDetails=new ArrayList<>();
	ArrayList<Movie> movies=new ArrayList<>();
	TicketBookingWrapperBean ticket=new TicketBookingWrapperBean();

	public TicketBookingWrapperBean getTicket() {
		return ticket;
	}


	public void setTicket(TicketBookingWrapperBean tickets) {
		this.ticket = tickets;
	}


	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}


	public ArrayList<Seat> getSeats() {
		return seats;
	}
	
	
	public ArrayList<Category> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	public ArrayList<TicketCharge> getTicketCharges() {
		return ticketCharges;
	}
	public void setTicketCharges(ArrayList<TicketCharge> ticketCharges) {
		this.ticketCharges = ticketCharges;
	}
	public ArrayList<Extra> getExtras() {
		return extras;
	}
	public void setExtras(ArrayList<Extra> extras) {
		this.extras = extras;
	}
	public ArrayList<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}
	public ArrayList<MovieShow> getMovieShows() {
		return movieShows;
	}
	public void setMovieShows(ArrayList<MovieShow> movieShows) {
		this.movieShows = movieShows;
	}
	public ArrayList<Screen> getScreens() {
		return screens;
	}
	public void setScreens(ArrayList<Screen> screens) {
		this.screens = screens;
	}
	public ArrayList<ShowDetail> getShowDetails() {
		return showDetails;
	}
	public void setShowDetails(ArrayList<ShowDetail> showDetails) {
		this.showDetails = showDetails;
	}
	public ArrayList<Movie> getMovies() {
		return movies;
	}
	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}
	
	@Override
	public String toString() {
		return "TicketWrapperVO [seats=" + seats + ", categories=" + categories + ", ticketCharges=" + ticketCharges
				+ ", extras=" + extras + ", customers=" + customers + ", movieShows=" + movieShows + ", screens="
				+ screens + ", showDetails=" + showDetails + ", movies=" + movies + ", tickets=" + ticket + "]";
	}

	
	
	
}
