package com.movieapp.interfaces;

import com.adventnet.ds.query.Criteria;
import com.movieapp.vo.MovieShowWrapperVO;
import com.movieapp.vo.ScreenSeatsVO;
import com.movieapp.vo.ShowSeatWrapperVO;
import com.movieapp.vo.TicketWrapperVO;

public interface UserBO 
{
	public MovieShowWrapperVO getMovieShowProperties(Criteria criteria);
	public ShowSeatWrapperVO getShowSeatProperties(Criteria criteria);
	public ScreenSeatsVO getScreenProperties(Criteria criteria);
	public TicketWrapperVO getTicketProperties(Criteria criteria);

}
