package com.movieapp.wrapperbean;

public class ScreenWrapper 
{
	private ScreenSeatsWrapperBean screenSeatsWrapperBean;

	public ScreenSeatsWrapperBean getScreen() {
		return screenSeatsWrapperBean;
	}

	public void setScreen(ScreenSeatsWrapperBean screenSeatsWrapperBean) {
		this.screenSeatsWrapperBean = screenSeatsWrapperBean;
	}

	@Override
	public String toString() {
		return "ScreenWrapper [screenSeatsWrapperBean=" + screenSeatsWrapperBean + "]";
	}
	
	
}
