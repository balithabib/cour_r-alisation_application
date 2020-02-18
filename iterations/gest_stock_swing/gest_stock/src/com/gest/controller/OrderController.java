package com.gest.controller;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.gest.daos.DaoFactory;
import com.gest.daos.OrderDao;
import com.gest.daos.StockDao;
import com.gest.exception.DaoException;
import com.gest.model.ArticleOrder;
import com.gest.model.ArticleStock;
import com.gest.view.OrderPanel;

public class OrderController extends OrderPanel {
	
	private static final long serialVersionUID = 1L;
	private StockDao stock;
	private OrderDao orderDao;
	private HashMap<String, ArticleStock> articles;
	private int selectedArticle;
	private ArticleOrder order;
	
	public OrderController()
	{
		super();
		order = new ArticleOrder();
		DaoFactory factory = DaoFactory.getInstance();
		this.stock = factory.getStockDao();
		this.orderDao = factory.getOrderDao();
		try {
			articles = stock.getStock().getArticles();
			System.out.println(articles.values().toString());
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		super.btnCancel.addActionListener(this::btnCancelListener);
		super.addBtn.addActionListener(this::addBtnListener);
		super.editBtn.addActionListener(this::editBtnListener);
		super.removeBtn.addActionListener(this::removeBtnListener);
		super.selected.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				tableEventListener();
			}
		});
		super.btnValidate.addActionListener(this::btnValidateListener);
	}
	
	
	public void addBtnListener(ActionEvent event)
	{
		if (order == null) {
			order = new ArticleOrder();
			super.tfCodeBar.setText("");
			super.tfQuantity.setText("");
			super.tfName.setText("");
		}
		ArticleStock art = null;
		if (articles.containsKey(super.tfCodeBar.getText()))
		{
			art = articles.get(super.tfCodeBar.getText());
			int quantity = (super.tfQuantity.getText().contentEquals("") == true) ? 1 : new Integer(super.tfQuantity.getText());
			art.setQuantity(quantity);
			order.add(art);
		}
		this.displayOrder();
		
	}
	
	private void tableEventListener()
	{
		selectedArticle = super.table.getSelectedRow();
		if (!(articles.isEmpty()) && selectedArticle != -1) {
			List<ArticleStock> arts = order.getOrderList();
			ArticleStock selectedArt = arts.get(selectedArticle);
			super.tfCodeBar.setText(selectedArt.getBarcode());
			super.tfQuantity.setText(new Integer(selectedArt.getQuantity()).toString());
			super.tfName.setText(selectedArt.getName());
		}
	}
	
	private boolean checkFields()
	{
		if (super.tfCodeBar.getText().isEmpty() || super.tfQuantity.getText().isEmpty())
			return false;
		return true;
	}
	
	private void editBtnListener(ActionEvent event)
	{
		if (! this.checkFields())
			return;
		int quantity = new Integer(super.tfQuantity.getText());
		if (quantity > 0)
		{
			this.addBtnListener(event);
		}
		else 
		{
			this.removeBtnListener(event);
		}
	}
	
	private void removeBtnListener(ActionEvent event)
	{
		if (! this.checkFields())
			return;
		order.remove(super.tfCodeBar.getText());
		this.displayOrder();
	}
	
	public void btnCancelListener(ActionEvent event)
	{
		if (order != null)
			order.cancelOrder();
		super.dispose();
	}
	
	public void btnValidateListener(ActionEvent event)
	{
		try {
			stock.updateStock(order.getOrderList());
			orderDao.validateOrder(order);
			orderDao.generateBill(order);
			order.cancelOrder();
			this.displayOrder();
			order = null;
			super.dispose();
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	public double getTotalPrice()
	{
		double price = 0.0;
		List<ArticleStock> arts = order.getOrderList();
		for (ArticleStock art : arts)
		{
			price += art.getPrice() * art.getQuantity(); 
		}
		return price;
	}

	
	
	public void displayOrder()
	{
		List<ArticleStock> arts = order.getOrderList();
		super.displayOrderTable(arts);
		Double price = new Double(this.getTotalPrice()); 
		super.lblTotalPrice.setText(price.toString());
	}
	
	
}
