package com.gest.daos;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.gest.exception.DaoException;
import com.gest.model.ArticleOrder;
import com.gest.model.ArticleStock;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class OrderDaoImpl implements OrderDao {
	private DaoFactory daoFactory;
	
	
	public OrderDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
		
	
	
	@Override
	public void validateOrder(ArticleOrder order) throws DaoException {
		int id = order.getId();
		String date = order.getDate();
		Connection connect = null;
		PreparedStatement stmt = null;
		List<ArticleStock> articles = order.getOrderList();
		try {
			connect = daoFactory.getConnection();
			for (ArticleStock art : articles) {
				stmt = connect.prepareStatement("INSERT INTO Command(id , barcode, quantity, date) VALUES (?,?,?,?);");
				stmt.setString(2, art.getBarcode());
				stmt.setString(4, date);
				stmt.setInt(1, id);
				stmt.setInt(3, art.getQuantity());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DaoException("Error while trying to connect to database");
		}
		
	}
	
	
	public void generateBill(ArticleOrder order) {
		String path = "./orders/"+order.getId()+".pdf";
		List<ArticleStock> arts = order.getOrderList();
		Document doc = new Document();
		try {
			FileOutputStream file = new FileOutputStream(path);
			PdfWriter.getInstance(doc, file);
			doc.open();
			Paragraph paragraph = new Paragraph(order.getDate());
            paragraph.setAlignment(Element.ALIGN_CENTER);
            doc.add(paragraph);
			Paragraph paragraph2 = new Paragraph("Commande n°:" + order.getId());
            paragraph2.setAlignment(Element.ALIGN_LEFT);
            doc.add(paragraph2);
            PdfPTable table = new PdfPTable(4);
            table.getDefaultCell().setBorder(0);
            double total = 0.0;
            for (ArticleStock art : arts)
            {
            	Double subTotal = new Double(art.getQuantity() * art.getPrice());
            	total += subTotal;
            	table.addCell(art.getBarcode());
            	table.addCell(art.getName());
            	table.addCell(new Double(art.getPrice()).toString()+" €/unité");
            	table.addCell(subTotal.toString()+" €/sous total");
            }
            doc.add(table);
            Paragraph paragraph3 = new Paragraph("Total : " + new Double(total).toString() + " €");
            paragraph3.setAlignment(Element.ALIGN_LEFT);
            doc.add(paragraph3);
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
		doc.close();
		this.viewBill(path);
	}
	
	
	private void viewBill(String pathname)
	{
		File file = new File(pathname);	
		if (file.exists())
		{
			if (Desktop.isDesktopSupported())
			{
				try {
					Desktop.getDesktop().open(file);
				} catch (IOException e) {
					
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
