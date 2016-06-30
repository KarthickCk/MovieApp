package com.movieapp.bean;

public class ShowDetail 
{
	private long id;
	private String showName;
	private String startTime;
	private String endTime;
	
	public void setID(long iD) {
		id = iD;
	}
	
	public long getID() {
		return id;
	}
	
	public void setShowName(String showName) {
		this.showName = showName;
	}
	
	public String getShowName() {
		return showName;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getEndTime() {
		return endTime;
	}

	@Override
	public String toString() {
		return "ShowDetail [id=" + id + ", showName=" + showName + ", startTime=" + startTime + ", endTime=" + endTime
				+ "]";
	}
	
}
