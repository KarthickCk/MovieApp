package com.movieapp.wrapperbean;

import com.movieapp.bean.Category;

public class CategoryWrapperBean 
{
	private Category category;
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "CategoryWrapperBean [category=" + category + "]";
	}
	
	
}
