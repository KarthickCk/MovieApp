package com.movieapp.bean;

public class Extra {
	private long id;
	private float cost;
	private String name;
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCost(float cost) {
		this.cost = cost;
	}
	
	public float getCost() {
		return cost;
	}
}
