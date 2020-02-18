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

public class GestPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected JTextField tfCodeBar;
	protected JTextField tfName;
	protected JTextField tfQuantity;
	protected JTextField tfPrice;
	protected JTextField tfTreshold;
	protected JTextField tfCategory;
	private JPanel panel_1;
	protected JButton backBtn;
	protected JButton addBtn;
	protected JButton removeBtn;
	protected JButton editBtn;
	protected JButton addMenuBtn;
	protected JButton removeMenuBtn;
	protected JButton editMenuBtn;
	protected JButton btnCancel;
	private JPanel panel;
	private JPanel panel_3;
	private JPanel panelMenu;
	protected JTable table;
	protected JLabel error;
	protected ListSelectionModel selected; 
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public GestPanel() {
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
		panel.setVisible(false);
		this.initMenu();

		
		
	}
	
	private void initMenu()
	{
		panelMenu = new JPanel();
		panelMenu.setBounds(0, 0, 800, 600);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		addMenuBtn = new JButton("Ajouter un article");
		addMenuBtn.setBounds(300, 200, 200, 30);
		panelMenu.add(addMenuBtn);
		removeMenuBtn = new JButton("Supprimer un article");
		removeMenuBtn.setBounds(300, 260, 200, 30);
		panelMenu.add(removeMenuBtn);
		editMenuBtn = new JButton("Modifier un article");
		editMenuBtn.setBounds(300, 320, 200, 30);
		panelMenu.add(editMenuBtn);
		backBtn = new JButton("Retour");
		backBtn.setBounds(300, 380, 200, 30);
		panelMenu.add(backBtn);
		
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

		JLabel lblPrice = new JLabel("Prix");
		lblPrice.setBounds(10, 160, 200, 15);
		panel_1.add(lblPrice);

		JLabel lblTreshold = new JLabel("Seuille");
		lblTreshold.setBounds(10, 210, 200, 15);
		panel_1.add(lblTreshold);

		JLabel lblCategory = new JLabel("Catégorie");
		lblCategory.setBounds(10, 260, 200, 15);
		panel_1.add(lblCategory);

		
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
		
		tfPrice = new JTextField();
		tfPrice.setBounds(10, 175, 220, 20);
		panel_1.add(tfPrice);
		tfPrice.setColumns(10);
		
		tfTreshold = new JTextField();
		tfTreshold.setBounds(10, 225, 220, 20);
		panel_1.add(tfTreshold);
		tfTreshold.setColumns(10);
		
		tfCategory = new JTextField();
		tfCategory.setBounds(10, 275, 220, 20);
		panel_1.add(tfCategory);
		tfCategory.setColumns(10);
		
		btnCancel = new JButton("Annuler");
		btnCancel.setBounds(10, 380, 120, 25);
		panel_1.add(btnCancel);
		
		addBtn = new JButton("Ajouter");
		addBtn.setBounds(160, 380, 120, 25);
		panel_1.add(addBtn);
		
		editBtn = new JButton("Modifier");
		editBtn.setBounds(160, 380, 120, 25);
		panel_1.add(editBtn);
		
		removeBtn = new JButton("Supprimer");
		removeBtn.setBounds(160, 380, 120, 25);
		panel_1.add(removeBtn);
		
		error = new JLabel("");
		error.setBounds(10, 510, 600, 300);
		panel_1.add(error);
	}
	
	
	protected void showMenuPanel()
	{
		panel.setVisible(false);
		panelMenu.setVisible(true);
	}
	
	protected void showRemoveMenu()
	{
		panel.setVisible(true);
		panelMenu.setVisible(false);
		showRemoveBtn();
	}
	
	protected void showEditMenu()
	{
		panel.setVisible(true);
		panelMenu.setVisible(false);
		showEditBtn();
	}
	
	protected void showAddMenu()
	{
		panel.setVisible(true);
		panelMenu.setVisible(false);
		showAddBtn();
	}
	
	
	private void showAddBtn()
	{
		addBtn.setVisible(true);
		removeBtn.setVisible(false);
		editBtn.setVisible(false);
	}
	
	private void showEditBtn()
	{
		addBtn.setVisible(false);
		removeBtn.setVisible(false);
		editBtn.setVisible(true);
	}
	
	private void showRemoveBtn()
	{
		addBtn.setVisible(false);
		removeBtn.setVisible(true);
		editBtn.setVisible(false);
	}
	
	protected void displayStockTable(List<ArticleStock> articles) {
		
		ArticleViewModel model = new ArticleViewModel(articles);
		table.setModel(model);
		
	}
}
