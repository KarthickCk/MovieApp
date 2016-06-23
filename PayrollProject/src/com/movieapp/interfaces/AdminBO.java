package com.movieapp.interfaces;

import java.util.ArrayList;

import com.movieapp.bean.Movie;
import com.movieapp.bean.MovieShow;
import com.movieapp.bean.Screen;
import com.movieapp.bean.Seat;

public interface AdminBO 
{
	public String updateScreenSeats(Screen screen, ArrayList<Seat> seats);
	public String changeScreenLayout(Screen screen,ArrayList<Seat> seats);
	public MovieShow updateMovieShow(MovieShow movieShow,String movieShowID);
	public Movie updateMovie(Movie movie,String movieID);

}
