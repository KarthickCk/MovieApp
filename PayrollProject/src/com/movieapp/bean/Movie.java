package com.movieapp.bean;

public class Movie 
{
	private long id;
	private String movieName;
	private String genre;
	private String category;
	private String language;
	private String duration;
	private String description;
	private String imageURL;
	private String releasedDate;
	private String certificate;
	
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(String releasedDate) {
		this.releasedDate = releasedDate;
	}

	public void setID(long id) {
		this.id = id;
	}
	
	public long getID() {
		return id;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	public void setCertificate(String certifiate) {
		this.certificate = certifiate;
	}
	
	public String getCertificate() {
		return certificate;
	}
	
	public void setMovieName(String name) {
		this.movieName = name;
	}
	
	public String getMovieName() {
		return movieName;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
