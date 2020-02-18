package com.gest.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.gest.model.ArticleStock;

public class OrderPanel extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected JTextField tfCodeBar;
	protected JTextField tfName;
	protected JTextField tfQuantity;
	protected JPanel panel_1;
	protected JButton backBtn;
	protected JButton addBtn;
	protected JButton removeBtn;
	protected JButton editBtn;
	protected JButton btnCancel;
	protected JButton btnValidate;
	private JPanel panel;
	private JPanel panel_3;
	protected JTable table;
	protected ListSelectionModel selected; 
	protected JLabel lblTotalPrice;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public OrderPanel() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 800, 600);
		contentPane.add(panel);
		panel.setLayout(null);
		
		this.initDisplayPanel();
		this.initFields();
		panel.setVisible(true);
		
	}
	
	
	
	
	private void initDisplayPanel()
	{
		panel_3 = new JPanel();
		panel_3.setBounds(290, 0, 500, 560);
		panel.add(panel_3);
		
		table = new JTable();
		selected = table.getSelectionModel();
		selected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(0, 0, 500, 500);
		panel_3.add(new JScrollPane(table));
		table.setVisible(true);
		panel_3.setVisible(true);
	}
	
	private void initFields()
	{
		panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 290, 600);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblCodeBar = new JLabel("Code bar");
		lblCodeBar.setBounds(10, 10, 70, 15);
		panel_1.add(lblCodeBar);
		
		JLabel lblName = new JLabel("Nom ");
		lblName.setBounds(10, 60, 200, 15);
		panel_1.add(lblName);

		JLabel lblQuantity = new JLabel("Quantité");
		lblQuantity.setBounds(10, 110, 200, 15);
		panel_1.add(lblQuantity);

		
		tfCodeBar = new JTextField();
		tfCodeBar.setBounds(10, 25, 220, 20);
		panel_1.add(tfCodeBar);
		tfCodeBar.setColumns(10);
		
		tfName = new JTextField();
		tfName.setBounds(10, 75, 220, 20);
		panel_1.add(tfName);
		tfName.setColumns(10);
		
		tfQuantity = new JTextField();
		tfQuantity.setBounds(10, 125, 220, 20);
		panel_1.add(tfQuantity);
		tfQuantity.setColumns(10);
				
		btnCancel = new JButton("Annuler");
		btnCancel.setBounds(10, 430, 120, 25);
		panel_1.add(btnCancel);
		
		btnValidate = new JButton("Validate");
		btnValidate.setBounds(160, 430, 120, 25);
		panel_1.add(btnValidate);
		
		addBtn = new JButton("Ajouter");
		addBtn.setBounds(85, 280, 120, 25);
		panel_1.add(addBtn);
	
		editBtn = new JButton("Modifier");
		editBtn.setBounds(85, 330, 120, 25);
		panel_1.add(editBtn);
		
		removeBtn = new JButton("Supprimer");
		removeBtn.setBounds(85, 380, 120, 25);
		panel_1.add(removeBtn);
		
		JLabel lblTotal = new JLabel("Total: ");
		lblTotal.setBounds(10, 480, 100, 25);
		panel_1.add(lblTotal);
		lblTotalPrice = new JLabel("0.0");
		lblTotalPrice.setBounds(110, 480, 100, 25);
		panel_1.add(lblTotalPrice);
	}

	protected void displayOrderTable(List<ArticleStock> articles) {
		OrderViewModel model = new OrderViewModel(articles);
		table.setModel(model);
	}
	
}
