package com.movieapp.vo;

import java.util.ArrayList;

import com.movieapp.bean.Movie;
import com.movieapp.bean.MovieShow;
import com.movieapp.bean.Screen;
import com.movieapp.bean.ShowDetail;

public class MovieShowWrapperVO 
{
	private ArrayList<Movie> movies=new ArrayList<>();
	private ArrayList<ShowDetail> shows=new ArrayList<>();
	private ArrayList<Screen> screens=new ArrayList<>();
	private ArrayList<MovieShow> movieShows=new ArrayList<>();

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}

	public ArrayList<ShowDetail> getShows() {
		return shows;
	}

	public void setShows(ArrayList<ShowDetail> shows) {
		this.shows = shows;
	}

	public ArrayList<Screen> getScreens() {
		return screens;
	}

	public void setScreens(ArrayList<Screen> screens) {
		this.screens = screens;
	}

	public ArrayList<MovieShow> getMovieshows() {
		return movieShows;
	}

	public void setMovieshows(ArrayList<MovieShow> movieShows) {
		this.movieShows = movieShows;
	}

	public boolean isMovieAdded(Movie movie)
	{
		return movies.contains(movie);
	}
	
	public boolean isShowAdded(ShowDetail showDetail)
	{
		return shows.contains(showDetail);
	}
	
	public boolean isSCreenAdded(Screen screen)
	{
		return screens.contains(screen);
	}
	
	@Override
	public String toString() {
		return "MovieShowWrapperVO [movieList=" + movies + ", showList=" + shows + ", screenList=" + screens
				+ ", movieShowList=" + movieShows + "]";
	}

}
