package com.gest.daos;

import com.gest.exception.DaoException;
import com.gest.model.ArticleOrder;

public interface OrderDao {


	public void validateOrder(ArticleOrder order) throws DaoException;
	public void generateBill(ArticleOrder order);
	
}
