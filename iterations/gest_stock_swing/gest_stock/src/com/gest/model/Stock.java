package com.gest.model;

import java.util.HashMap;

public class Stock {
	private HashMap<String, ArticleStock> articles;

	
	public Stock() {
		articles = new HashMap<String, ArticleStock>();
	}
	
	
	
	
	
	
	/**
	 * @return the articles
	 */
	public HashMap<String, ArticleStock> getArticles() {
		return articles;
	}

	/**
	 * @param article to add to stock.
	 */
	
	public void addArticle (ArticleStock art)
	{
		this.articles.put(art.getBarcode(), art);
	}
	
	
	/**
	 * @param articles the articles to set
	 */
	public void setArticles(HashMap<String, ArticleStock> articles) {
		this.articles = articles;
	}
	
}
