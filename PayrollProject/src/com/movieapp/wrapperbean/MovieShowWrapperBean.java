package com.movieapp.wrapperbean;

import com.movieapp.bean.MovieShow;

public class MovieShowWrapperBean 
{
	private MovieShow movieShow;

	public MovieShow getMovieShow() {
		return movieShow;
	}

	public void setMovieShow(MovieShow movieShow) {
		this.movieShow = movieShow;
	}

	@Override
	public String toString() {
		return "MovieShowWrapperBean [movieShow=" + movieShow + "]";
	}
	
}
