package com.movieapp.interfaces;

import java.util.Properties;

import com.adventnet.ds.query.Criteria;

public interface UserBO 
{
	public Properties getMovieShowProperties(Criteria criteria);
	public Properties getShowSeatProperties(Criteria criteria);
	public Properties getScreenProperties(Criteria criteria);
	public Properties getTicketProperties(Criteria criteria);

}
