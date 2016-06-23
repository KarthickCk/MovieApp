package com.movieapp.factory;

import com.movieapp.dao.CategoryDAOImpl;
import com.movieapp.dao.CustomerDAOImpl;
import com.movieapp.dao.ExtraDAOImpl;
import com.movieapp.dao.MovieDAOImpl;
import com.movieapp.dao.MovieShowDAOImpl;
import com.movieapp.dao.ScreenDAOImpl;
import com.movieapp.dao.SeatDAOImpl;
import com.movieapp.dao.ShowSeatDAOImpl;
import com.movieapp.dao.ShowsDAOImpl;
import com.movieapp.dao.TicketChargeImpl;
import com.movieapp.dao.TicketDAOImpl;

public class MovieDAOFactory 
{
    public static MovieDAOImpl getMovieDAO() 
    { 
        return new MovieDAOImpl();
    }
    
    public static ShowsDAOImpl getShowDAO() 
    { 
        return new ShowsDAOImpl();
    }
    
    public static ScreenDAOImpl getScreenDAO() 
    { 
        return new ScreenDAOImpl();
    }
    
    public static MovieShowDAOImpl getMovieShowDAO() 
    { 
        return new MovieShowDAOImpl();
    }
    
    public static CategoryDAOImpl getCategoryDAO() 
    { 
        return new CategoryDAOImpl();
    }
    
    public static SeatDAOImpl getSeatDAO() 
    { 
        return new SeatDAOImpl();
    }
    
    public static ShowSeatDAOImpl getShowSeatDAO() 
    { 
        return new ShowSeatDAOImpl();
    }
    
    public static TicketDAOImpl getTicketDAO() 
    { 
        return new TicketDAOImpl();
    }
    
    public static ExtraDAOImpl getExtasDAO() 
    { 
        return new ExtraDAOImpl();
    }
    
    public static CustomerDAOImpl getCustomerDAO() 
    { 
        return new CustomerDAOImpl();
    }
    
    public static TicketChargeImpl getTicketChargeDAO() 
    { 
        return new TicketChargeImpl();
    }
}