package com.gest.daos;

import java.util.List;

import com.gest.exception.DaoException;
import com.gest.model.ArticleStock;
import com.gest.model.Stock;

public interface StockDao {

	public void AddArticle(ArticleStock article) throws DaoException;
	public Stock getStock() throws DaoException;
	public void removeStock(ArticleStock article) throws DaoException;
	public ArticleStock getArticle(String barcode) throws DaoException;
	public List<ArticleStock> getStockList() throws DaoException;
	public void updateStock(ArticleStock oldArticle, ArticleStock newArticle) throws DaoException;
	public void updateStock(List<ArticleStock> articles) throws DaoException;
}
