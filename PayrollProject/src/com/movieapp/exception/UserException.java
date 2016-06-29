package com.movieapp.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserException extends WebApplicationException
{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	public UserException(String message,int errorCode) 
	{
        super(Response.status(errorCode)
            .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
	
	public UserException(String message) 
	{
        super(Response.status(Response.Status.NOT_IMPLEMENTED)
            .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
	
}
