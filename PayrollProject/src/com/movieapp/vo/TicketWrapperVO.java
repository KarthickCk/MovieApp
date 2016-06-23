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
import com.movieapp.bean.Ticket;
import com.movieapp.bean.TicketCharge;

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
	ArrayList<Long> ticketChargeIDs=new ArrayList<>();
	ArrayList<Ticket> tickets=new ArrayList<>();

	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}

	public ArrayList<Long> getTicketChargeIDs() {
		return ticketChargeIDs;
	}
	
	public void setTicketChargeIDs(ArrayList<Long> ticketChargeIDs) {
		this.ticketChargeIDs = ticketChargeIDs;
	}
	public ArrayList<Long> getSeatIDs() {
		return seatIDs;
	}
	public void setSeatIDs(ArrayList<Long> seatIDs) {
		this.seatIDs = seatIDs;
	}
	ArrayList<Long> seatIDs=new ArrayList<>();

	public ArrayList<Seat> getSeats() {
		return seats;
	}
	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
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
	
	
}
