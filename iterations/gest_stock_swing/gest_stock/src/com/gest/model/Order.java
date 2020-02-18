/**
 * 
 */
package com.gest.model;

import java.util.HashMap;

/**
 * @author visibog
 *
 */
public class Order {
private HashMap<String, ArticleStock> articles;

	
	public Order() {
		articles = new HashMap<String, ArticleStock>();
	}
	
	
	public void add(ArticleStock article)
	{
		articles.put(article.getBarcode(), article);
	}
	
	public ArticleStock get(String barcode)
	{
		if (articles.containsKey(barcode) == true)
		{
			return articles.get(barcode);
		}
		return null;
	}
	
	public void remove(String barcode)
	{
		if (articles.containsKey(barcode) == true)
			articles.remove(barcode);
	}
	
	
		
	
	/**
	 * @return the articles
	 */
	public HashMap<String, ArticleStock> getArticles() {
		return articles;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(HashMap<String, ArticleStock> articles) {
		this.articles = articles;
	}
	
	public void cancelOrder()
	{
		this.articles.clear();
	}
}
