/**
 * 
 */
package com.gest.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author 
 *
 */
public class DaoFactory {
	private String url;
	private String user;
	private String pass;
	
	
	public DaoFactory(String url, String user, String pass) {
		this.url = url;
		this.user = user;
		this.pass = pass;
	}
	
	
	public static DaoFactory getInstance()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new DaoFactory("jdbc:mysql://localhost:3306/gest?serverTimezone=EST5EDT", "gestuser", "password");
	}
	
	public Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(url,user, pass);
	}
	
	
	public StockDao getStockDao()
	{
		return new StockDaoImpl(this);
	}
	
	public OrderDao getOrderDao()
	{
		return new OrderDaoImpl(this);
	}

}
