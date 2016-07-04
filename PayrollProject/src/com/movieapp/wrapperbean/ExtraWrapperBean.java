package com.movieapp.wrapperbean;

import com.movieapp.bean.Extra;

public class ExtraWrapperBean 
{
	private Extra extra;

	@Override
	public String toString() {
		return "ExtraWrapperBean [extra=" + extra + "]";
	}

	public Extra getExtras() {
		return extra;
	}

	public void setExtras(Extra extra) {
		this.extra = extra;
	}
	
}
