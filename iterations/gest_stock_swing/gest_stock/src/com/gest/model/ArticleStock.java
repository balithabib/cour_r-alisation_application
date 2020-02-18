package com.gest.model;

public class ArticleStock extends Article{
	private int quantity;
	private int treshold;
	
	
	public ArticleStock() {
		super();
	}
	
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	
	/**
	 * @return the treshold
	 */
	public int getTreshold() {
		return treshold;
	}

	/**
	 * @param treshold the treshold to set
	 */
	public void setTreshold(int treshold) {
		this.treshold = treshold;
	}

	@Override
	public String toString() {
		return super.toString() + "[ quantity=" + quantity + "]";
	}
	
	
}
