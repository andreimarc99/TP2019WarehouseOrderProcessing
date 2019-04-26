package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import businessLogic.ActionListeners;
import businessLogic.BillCreator;
import dataAccess.ClientService;
import dataAccess.OrderService;
import dataAccess.ProductService;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.JTable;

public class GUI extends JFrame {

	private JPanel contentPane;
	private static JTable table_1;
	private static JTextField quantityTextField;
	private static JTextField clientNameTextField;
	private static JTextField productNameTextField;
	private static JTextField productPriceTextField;
	private static JLabel lblProductName;
	private static JLabel lblProductPrice;
	private static JButton btnRegister;
	private static JTextField productStockTextField;
	private static JLabel lblProductStock;
	private static JComboBox clientsComboBox;
	private static JButton btnRefreshClientList;
	private static JButton btnRefreshTable;
	private static JButton btnRemoveProduct;
	private static JButton btnRemove;
	private static JButton btnEditProduct;
	private static JButton btnEdit;
	private static JButton btnRegisterNewProduct;
	private static JButton btnPlaceOrder;
	private static JButton btnRemoveClient;
	private static JButton btnAddClient;
	private static JButton btnEditClient;
	private static JComboBox<String> productsComboBox;

	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 992, 513);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnRegisterNewProduct = new JButton("Register new product");
		btnRegisterNewProduct.setBounds(10, 278, 195, 21);
		ActionListeners.addBtnRegisterProductActionListener();
		contentPane.add(btnRegisterNewProduct);

		JLabel lblChooseProduct = new JLabel("Choose product you wish to order:");
		lblChooseProduct.setBounds(10, 49, 225, 13);
		contentPane.add(lblChooseProduct);

		productsComboBox = new JComboBox<String>();
		productsComboBox.setBounds(10, 83, 195, 21);
		HashMap<Integer, Product> products = ProductService.getProducts();
		for (Entry<Integer, Product> entry : products.entrySet()) {
			products.put(entry.getKey(), entry.getValue());
			productsComboBox.addItem(entry.getValue().getName());
		}
		contentPane.add(productsComboBox);

		JLabel lblEnterQuantity = new JLabel("Enter quantity:");
		lblEnterQuantity.setBounds(10, 125, 195, 13);
		contentPane.add(lblEnterQuantity);

		quantityTextField = new JTextField();
		quantityTextField.setBounds(10, 149, 195, 19);
		contentPane.add(quantityTextField);
		quantityTextField.setColumns(10);

		JLabel lblEnterClientsName = new JLabel("Enter client's name:");
		lblEnterClientsName.setBounds(10, 188, 195, 13);
		contentPane.add(lblEnterClientsName);

		clientNameTextField = new JTextField();
		clientNameTextField.setBounds(10, 211, 195, 19);
		contentPane.add(clientNameTextField);
		clientNameTextField.setColumns(10);

		int i = 1;

		btnPlaceOrder = new JButton("Place order");
		btnPlaceOrder.setBounds(101, 247, 104, 21);
		ActionListeners.addBtnPlaceOrderListener();
		contentPane.add(btnPlaceOrder);

		productNameTextField = new JTextField();
		productNameTextField.setBounds(10, 339, 195, 19);
		contentPane.add(productNameTextField);
		productNameTextField.setColumns(10);
		productNameTextField.setVisible(false);

		lblProductName = new JLabel("Product name");
		lblProductName.setBounds(10, 316, 195, 13);
		contentPane.add(lblProductName);
		lblProductName.setVisible(false);

		lblProductPrice = new JLabel("Product price");
		lblProductPrice.setBounds(10, 367, 195, 13);
		contentPane.add(lblProductPrice);
		lblProductPrice.setVisible(false);

		productPriceTextField = new JTextField();
		productPriceTextField.setBounds(10, 390, 195, 19);
		contentPane.add(productPriceTextField);
		productPriceTextField.setColumns(10);
		productPriceTextField.setVisible(false);

		btnRegister = new JButton("Register");
		btnRegister.setBounds(266, 441, 104, 21);
		btnRegister.setVisible(false);
		ActionListeners.addBtnRegisterActionListener();
		contentPane.add(btnRegister);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(266, 57, 691, 383);
		contentPane.add(scrollPane);

		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Order no.", "Stock", "Product",
				"Product Price", "Quantity", "Total Price", "Client" }));
		table_1.setEnabled(false);
		DefaultTableModel model = (DefaultTableModel) table_1.getModel();

		HashMap<Integer, Order> orders = OrderService.retrieveOrders();
		for (Entry<Integer, Order> entry : orders.entrySet()) {
			model.addRow(new Object[] { i++, entry.getValue().getProduct().getStock(),
					entry.getValue().getProduct().getName(), entry.getValue().getProduct().getPrice(),
					entry.getValue().getQuantity(),
					(int) entry.getValue().getProduct().getPrice() * entry.getValue().getQuantity(),
					entry.getValue().getClientName() });
		}

		for (int cnt = 0; cnt < model.getColumnCount(); ++cnt) {
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			table_1.getColumnModel().getColumn(cnt).setCellRenderer(centerRenderer);
		}
		scrollPane.setViewportView(table_1);

		lblProductStock = new JLabel("Product stock");
		lblProductStock.setBounds(10, 419, 195, 13);
		contentPane.add(lblProductStock);
		lblProductStock.setVisible(false);

		productStockTextField = new JTextField();
		productStockTextField.setBounds(10, 442, 195, 19);
		contentPane.add(productStockTextField);
		productStockTextField.setColumns(10);
		
		btnAddClient = new JButton("Add Client");
		ActionListeners.addBtnAddClientActionListener();
		btnAddClient.setBounds(429, 26, 104, 21);
		contentPane.add(btnAddClient);
		
		clientsComboBox = new JComboBox();
		clientsComboBox.setBounds(756, 26, 201, 21);
		contentPane.add(clientsComboBox);
		HashMap<Integer, Client> clients = ClientService.findAll();
		for (Entry<Integer, Client> entry : clients.entrySet()) {
			clients.put(entry.getKey(), entry.getValue());
			clientsComboBox.addItem(entry.getValue().getName());
		}
		productStockTextField.setVisible(false);
		
		btnRemoveClient = new JButton("Remove Client");
		ActionListeners.addBtnRemoveClientActionListener();
		btnRemoveClient.setBounds(533, 26, 116, 21);
		contentPane.add(btnRemoveClient);
		
		btnEditClient = new JButton("Edit Client");
		ActionListeners.addBtnEditClientActionListener();
		btnEditClient.setBounds(648, 26, 98, 21);
		contentPane.add(btnEditClient);
		
		btnRefreshClientList = new JButton("Refresh Client List");
		btnRefreshClientList.setBounds(266, 26, 153, 21);
		ActionListeners.addBtnRefreshClientListActionListener();
		contentPane.add(btnRefreshClientList);
		
		btnRefreshTable = new JButton("Refresh table");
		ActionListeners.addBtnRefreshTableActionListener();
		btnRefreshTable.setBounds(823, 441, 134, 21);
		contentPane.add(btnRefreshTable);
		
		btnRemoveProduct = new JButton("Remove Product");
		ActionListeners.addBtnRemoveProductActionListener();
		btnRemoveProduct.setBounds(402, 441, 139, 21);
		contentPane.add(btnRemoveProduct);
		
		btnRemove = new JButton("Remove");
		btnRemove.setBounds(402, 441, 139, 21);
		btnRemove.setVisible(false);
		ActionListeners.addBtnRemoveActionListener();
		contentPane.add(btnRemove);
		
		btnEditProduct = new JButton("Edit Product");
		btnEditProduct.setBounds(572, 441, 116, 21);
		ActionListeners.addBtnEditProductActionListener();
		contentPane.add(btnEditProduct);
		
		btnEdit = new JButton("Edit");
		btnEdit.setBounds(572, 441, 116, 21);
		ActionListeners.addBtnEditActionListener();
		btnEdit.setVisible(false);
		contentPane.add(btnEdit);

	}

	public static JButton getBtnRefreshClientList() {
		return btnRefreshClientList;
	}

	public static void setBtnRefreshClientList(JButton btnRefreshClientList) {
		GUI.btnRefreshClientList = btnRefreshClientList;
	}

	public static JButton getBtnRefreshTable() {
		return btnRefreshTable;
	}

	public static void setBtnRefreshTable(JButton btnRefreshTable) {
		GUI.btnRefreshTable = btnRefreshTable;
	}

	public static JButton getBtnRemoveProduct() {
		return btnRemoveProduct;
	}

	public static void setBtnRemoveProduct(JButton btnRemoveProduct) {
		GUI.btnRemoveProduct = btnRemoveProduct;
	}

	public static JButton getBtnRemove() {
		return btnRemove;
	}

	public static void setBtnRemove(JButton btnRemove) {
		GUI.btnRemove = btnRemove;
	}

	public static JButton getBtnEditProduct() {
		return btnEditProduct;
	}

	public static void setBtnEditProduct(JButton btnEditProduct) {
		GUI.btnEditProduct = btnEditProduct;
	}

	public static JButton getBtnEdit() {
		return btnEdit;
	}

	public static void setBtnEdit(JButton btnEdit) {
		GUI.btnEdit = btnEdit;
	}

	public static JButton getBtnRegisterNewProduct() {
		return btnRegisterNewProduct;
	}

	public static void setBtnRegisterNewProduct(JButton btnRegisterNewProduct) {
		GUI.btnRegisterNewProduct = btnRegisterNewProduct;
	}

	public static JLabel getLblProductName() {
		return lblProductName;
	}

	public static void setLblProductName(JLabel lblProductName) {
		GUI.lblProductName = lblProductName;
	}

	public static JLabel getLblProductPrice() {
		return lblProductPrice;
	}

	public static void setLblProductPrice(JLabel lblProductPrice) {
		GUI.lblProductPrice = lblProductPrice;
	}

	public static JTextField getProductNameTextField() {
		return productNameTextField;
	}

	public static void setProductNameTextField(JTextField productNameTextField) {
		GUI.productNameTextField = productNameTextField;
	}

	public static JTextField getProductPriceTextField() {
		return productPriceTextField;
	}

	public static void setProductPriceTextField(JTextField productPriceTextField) {
		GUI.productPriceTextField = productPriceTextField;
	}

	public static JButton getBtnRegister() {
		return btnRegister;
	}

	public static void setBtnRegister(JButton btnRegister) {
		GUI.btnRegister = btnRegister;
	}

	public static JTextField getProductStockTextField() {
		return productStockTextField;
	}

	public static void setProductStockTextField(JTextField productStockTextField) {
		GUI.productStockTextField = productStockTextField;
	}

	public static JLabel getLblProductStock() {
		return lblProductStock;
	}

	public static void setLblProductStock(JLabel lblProductStock) {
		GUI.lblProductStock = lblProductStock;
	}

	public static JButton getBtnPlaceOrder() {
		return btnPlaceOrder;
	}

	public static void setBtnPlaceOrder(JButton btnPlaceOrder) {
		GUI.btnPlaceOrder = btnPlaceOrder;
	}

	public static JTextField getQuantityTextField() {
		return quantityTextField;
	}

	public static void setQuantityTextField(JTextField quantityTextField) {
		GUI.quantityTextField = quantityTextField;
	}

	public static JTextField getClientNameTextField() {
		return clientNameTextField;
	}

	public static void setClientNameTextField(JTextField clientNameTextField) {
		GUI.clientNameTextField = clientNameTextField;
	}

	public static JComboBox getClientsComboBox() {
		return clientsComboBox;
	}

	public static void setClientsComboBox(JComboBox clientsComboBox) {
		GUI.clientsComboBox = clientsComboBox;
	}

	public static JButton getBtnRemoveClient() {
		return btnRemoveClient;
	}

	public static void setBtnRemoveClient(JButton btnRemoveClient) {
		GUI.btnRemoveClient = btnRemoveClient;
	}

	public static JButton getBtnAddClient() {
		return btnAddClient;
	}

	public static void setBtnAddClient(JButton btnAddClient) {
		GUI.btnAddClient = btnAddClient;
	}

	public static JButton getBtnEditClient() {
		return btnEditClient;
	}

	public static void setBtnEditClient(JButton btnEditClient) {
		GUI.btnEditClient = btnEditClient;
	}

	public static JComboBox<String> getProductsComboBox() {
		return productsComboBox;
	}

	public static void setProductsComboBox(JComboBox<String> productsComboBox) {
		GUI.productsComboBox = productsComboBox;
	}

	public static JTable getTable_1() {
		return table_1;
	}

	public static void setTable_1(JTable table_1) {
		GUI.table_1 = table_1;
	}
}
