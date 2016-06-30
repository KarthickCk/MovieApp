package com.movieapp.wrapperbean;

import com.movieapp.bean.ShowDetail;

public class ShowWrapperBean 
{
	private ShowDetail showdetail;

	public ShowDetail getShow() {
		return showdetail;
	}

	public void setShow(ShowDetail showdetail) {
		this.showdetail = showdetail;
	}

	@Override
	public String toString() {
		return "ShowWrapperBean [showDetail=" + showdetail + "]";
	}
	
}
