package businessLogic;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.table.DefaultTableModel;

import dataAccess.ClientService;
import dataAccess.OrderService;
import dataAccess.ProductService;
import model.Client;
import model.Order;
import model.Product;
import presentation.GUI;

public class ContentRefresher {

	public static void refreshTable() {
		HashMap<Integer, Order> orders = OrderService.retrieveOrders();
		DefaultTableModel model = (DefaultTableModel) GUI.getTable_1().getModel();
		int i = 1;
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		for (Entry<Integer, Order> entry : orders.entrySet()) {
			model.addRow(new Object[] { i++, entry.getValue().getProduct().getStock(),
					entry.getValue().getProduct().getName(), entry.getValue().getProduct().getPrice(),
					entry.getValue().getQuantity(),
					(float) entry.getValue().getProduct().getPrice() * entry.getValue().getQuantity(),
					entry.getValue().getClientName() });
		}
	}
	
	public static void refreshClientsList() {
		for (int i = 0; i < GUI.getClientsComboBox().getItemCount(); ++i) {
			GUI.getClientsComboBox().removeItemAt(0);
		}
		HashMap<Integer, Client> clients = ClientService.findAll();
		for (Entry<Integer, Client> entry : clients.entrySet()) {
			clients.put(entry.getKey(), entry.getValue());
			GUI.getClientsComboBox().addItem(entry.getValue().getName());
		}
	}
	
	public static void refreshProductsList() {
		GUI.getProductsComboBox().removeAllItems();
		HashMap<Integer, Product> products = ProductService.getProducts();
		for (Entry<Integer, Product> entry : products.entrySet()) {
			products.put(entry.getKey(), entry.getValue());
			GUI.getProductsComboBox().addItem(entry.getValue().getName());
		}
	}
}
