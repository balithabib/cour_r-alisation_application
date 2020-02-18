package com.gest.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.gest.model.ArticleStock;

public class ArticleViewModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ArticleStock> articles;
	private final String[] entete = {"Code bar", "Nom", "Catégorie", "Quantité", "Prix", "Seuille"};
	
	public ArticleViewModel(List<ArticleStock> art) {
		super();
		this.articles = art;
	}
	
	
	@Override
	public int getColumnCount() {
		return entete.length;
	}

	 public String getColumnName(int columnIndex) {
	        return entete[columnIndex];
	    }
	
	@Override
	public int getRowCount() {
		return this.articles.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		switch(columnIndex)
		{
		case 0:	
			return articles.get(rowIndex).getBarcode();
		case 1:
			return articles.get(rowIndex).getName();
		case 2:
			return articles.get(rowIndex).getCategory();
		case 3:
			return articles.get(rowIndex).getQuantity();
		case 4:
			return articles.get(rowIndex).getPrice();
		case 5:
			return articles.get(rowIndex).getTreshold();
		}
		return null;
	}
	
	
	
}
