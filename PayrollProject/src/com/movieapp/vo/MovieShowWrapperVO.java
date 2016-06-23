package com.movieapp.vo;

import java.util.ArrayList;

import com.movieapp.bean.Movie;
import com.movieapp.bean.MovieShow;
import com.movieapp.bean.Screen;
import com.movieapp.bean.ShowDetail;

public class MovieShowWrapperVO 
{
	private ArrayList<Movie> movieList=new ArrayList<>();
	private ArrayList<ShowDetail> showList=new ArrayList<>();
	private ArrayList<Screen> screenList=new ArrayList<>();
	private ArrayList<MovieShow> movieShowList=new ArrayList<>();

	public ArrayList<Movie> getMovieList() {
		return movieList;
	}
	
	public void setMovieList(ArrayList<Movie> movieList) {
		this.movieList = movieList;
	}
	
	public ArrayList<ShowDetail> getShowList() {
		return showList;
	}
	
	public void setShowList(ArrayList<ShowDetail> showList) {
		this.showList = showList;
	}
	
	public ArrayList<Screen> getScreenList() {
		return screenList;
	}
	
	public void setScreenList(ArrayList<Screen> screenList) {
		this.screenList = screenList;
	}
	public ArrayList<MovieShow> getMovieShowList() {
		return movieShowList;
	}
	public void setMovieShowList(ArrayList<MovieShow> movieShowList) {
		this.movieShowList = movieShowList;
	}
	
	public boolean isMovieAdded(Movie movie)
	{
		return movieList.contains(movie);
	}
	
	public boolean isShowAdded(ShowDetail showDetail)
	{
		return showList.contains(showDetail);
	}
	
	public boolean isSCreenAdded(Screen screen)
	{
		return screenList.contains(screen);
	}
	
	@Override
	public String toString() {
		return "MovieShowWrapperVO [movieList=" + movieList + ", showList=" + showList + ", screenList=" + screenList
				+ ", movieShowList=" + movieShowList + "]";
	}

}
