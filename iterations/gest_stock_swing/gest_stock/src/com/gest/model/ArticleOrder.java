package com.gest.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleOrder extends Order{
	private String date = "";
	private int id;
	
	
	public ArticleOrder() {
		super();
		this.date = generateDate();
		this.id = generateId();
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	
	public List<ArticleStock> getOrderList()
	{
		return  new ArrayList<ArticleStock>(super.getArticles().values());
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	
	private String generateDate()
	{
		Date date = new Date();
		return date.toString();
	}
	

	private int generateId()
	{
		Date date = new Date();
		return Math.abs(date.toString().hashCode());
	}

	
}
