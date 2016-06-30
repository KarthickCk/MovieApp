package com.movieapp.wrapperbean;

import java.util.List;

import com.movieapp.bean.MovieShow;

public class MovieShowWapperBean 
{
	private List<MovieShow> movieShows;

	public List<MovieShow> getMovieshows() {
		return movieShows;
	}

	public void setMovieshows(List<MovieShow> movieShows) {
		this.movieShows = movieShows;
	}

	@Override
	public String toString() {
		return "MovieShowWapperBean [movieShows=" + movieShows + "]";
	}

}
