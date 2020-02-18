package com.gest.controller;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.gest.daos.DaoFactory;
import com.gest.daos.StockDao;
import com.gest.exception.DaoException;
import com.gest.forms.ArticleForm;
import com.gest.model.ArticleStock;
import com.gest.view.GestPanel;

public class GestController extends GestPanel {
	private static final long serialVersionUID = 1L;
	private StockDao stock;
	private int selectedArticle;
	private List<ArticleStock> articles;
	
	public GestController()
	{
		super();
		DaoFactory factory = DaoFactory.getInstance();
		this.stock = factory.getStockDao();
		super.btnCancel.addActionListener(this::cancelListener);
		super.addMenuBtn.addActionListener(this::addMenuListner);
		super.removeMenuBtn.addActionListener(this::removeMenuListner);
		super.editMenuBtn.addActionListener(this::editMenuListner);
		super.backBtn.addActionListener(this::backListener);
		super.addBtn.addActionListener(this::addArticleListenner);
		super.removeBtn.addActionListener(this::removeBtnListener);
		super.editBtn.addActionListener(this::editBtnListener);
		super.selected.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				tableEventListener();
			}
		});
		this.displayStock();

	}
	
	private void addMenuListner(ActionEvent event)
	{
		super.showAddMenu();
	}
	
	private void editMenuListner(ActionEvent event)
	{
		super.showEditMenu();
	}
	
	private void removeMenuListner(ActionEvent event)
	{
		super.showRemoveMenu();
	}
	
	private void cancelListener(ActionEvent event)
	{
		super.showMenuPanel();
	}
	
	private void backListener(ActionEvent event)
	{
		super.setVisible(false);
	}
	
	private void tableEventListener()
	{
		selectedArticle = super.table.getSelectedRow();
		if (!(articles.isEmpty()) && selectedArticle != -1) {
			ArticleStock selectedArt = articles.get(selectedArticle);
			super.tfCodeBar.setText(selectedArt.getBarcode());
			super.tfName.setText(selectedArt.getName());
			super.tfCategory.setText(selectedArt.getCategory());
			super.tfQuantity.setText(new Integer(selectedArt.getQuantity()).toString());
			super.tfPrice.setText(new Double(selectedArt.getPrice()).toString());
			super.tfTreshold.setText(new Integer(selectedArt.getTreshold()).toString());
		}
		
	}
	
	private void addArticleListenner(ActionEvent event)
	{
		String[] fields = new String[6];
		fields[0] = super.tfCodeBar.getText();
		fields[1] = super.tfName.getText();
		fields[2] = super.tfCategory.getText();
		fields[4] = super.tfQuantity.getText();
		fields[5] = super.tfTreshold.getText();
		fields[3] = super.tfPrice.getText();
		ArticleForm check = new ArticleForm();
		if (check.checkArticleStockForm(fields) == true)
		{
			try {
				ArticleStock a = check.getArticle();
				stock.AddArticle(a);
				articles.add(a);
				this.displayStock();
			} catch (DaoException e) {
				super.error.setText(e.getMessage());
				super.error.setVisible(true);
			}
		}
		else 
		{
			System.out.println(check.getMessage());
			super.error.setText(check.getMessage());
			super.error.setVisible(true);

		}
	}
	
	private void removeBtnListener(ActionEvent event)
	{
		if (selectedArticle < 0 || articles.size() < selectedArticle)
		{
			return;
		}
		try {
			ArticleStock a = articles.get(selectedArticle);
			stock.removeStock(a);
			articles.clear();
		} catch (DaoException e) {
			super.error.setText(e.getMessage());
		}
		this.displayStock();
	}
	
	private void editBtnListener(ActionEvent event)
	{
		if (selectedArticle < 0 || articles.size() < selectedArticle)
		{
			return;
		}
		ArticleStock oldArticle = articles.get(selectedArticle);
		String[] fields = new String[6];
		try {
			stock.removeStock(oldArticle);
		}catch (DaoException e) {
			super.error.setText(e.getMessage());
		}		
		fields[0] = super.tfCodeBar.getText();
		fields[1] = super.tfName.getText();
		fields[2] = super.tfCategory.getText();
		fields[4] = super.tfQuantity.getText();
		fields[5] = super.tfTreshold.getText();
		fields[3] = super.tfPrice.getText();
		ArticleForm check = new ArticleForm();
		if (check.checkArticleStockForm(fields) == true)
		{
			ArticleStock newArticle = check.getArticle();
			try {
				stock.AddArticle(newArticle);
			} catch (DaoException e) {
				super.error.setText(e.getMessage());
			}
		}
		articles.clear();
		this.displayStock();
	}
	
	private void displayStock()
	{
		try {
			articles = stock.getStockList();
			super.displayStockTable(articles);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
}
