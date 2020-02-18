package com.gest.forms;

import com.gest.model.ArticleStock;

public class ArticleForm {
	private String fields = "";
	private String[] artFields = new String[6];
	
	
	
	public boolean checkArticleStockForm(String [] art)
	{
		artFields = art;
		String barcode = art[0];
		String name = art[1];
		String category = art[2];
		String price = art[3];
		String quantity = art[4];
		String treshold = art[5];

		if (barcode.matches("^\\d+?") == false || barcode.length() != 13) 
		{
			fields += "Barcode must be digit with size 13.\n";
			return false;
		}
		if (price.matches("^\\d+(\\.\\d+)?") == false || price.length() == 0)
		{
			fields += "Price must be digit.\n";
			return false;
		}
		if (quantity.matches("^\\d+?") == false || quantity.length() == 0)
		{
			fields += "Quantity must be digit.\n";
			return false;
		}
		if (treshold.matches("^\\d+?") == false || treshold.length() == 0)
		{
			fields += "Treshold must be digit.\n";
			return false;
		}
		if (name.length() == 0)
		{
			fields += "Name must not be null.\n";
			return false;
		}
		if (category.length() == 0)
		{
			fields += "Category must not be null.\n";
			return false;
		}
		return true;
	}

	
	public ArticleStock getArticle()
	{
		ArticleStock art = new ArticleStock();
		art.setBarcode(artFields[0]);
		art.setName(artFields[1]);
		art.setCategory(artFields[2]);
		art.setPrice(new Double(artFields[3]));
		art.setQuantity(new Integer(artFields[4]));
		art.setTreshold(new Integer(artFields[5]));
		return art;
	}
	
	
	public String getMessage() {
		return fields;
	}
	
	
	
}