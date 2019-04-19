package ro.tuc.tp.assig3.BusinessLogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import ro.tuc.tp.assig3.Model.Client;
import ro.tuc.tp.assig3.Model.Order;
import ro.tuc.tp.assig3.Model.Product;
import ro.tuc.tp.assig3.gui.GUI;

public class ActionListeners {

	public static void addBtnRegisterProductActionListener() {

		GUI.getBtnRegisterNewProduct().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.getLblProductName().setVisible(true);
				GUI.getLblProductPrice().setVisible(true);
				GUI.getProductNameTextField().setVisible(true);
				GUI.getProductPriceTextField().setVisible(true);
				GUI.getBtnRegister().setVisible(true);
				GUI.getLblProductStock().setVisible(true);
				GUI.getProductStockTextField().setVisible(true);
			}
		});
	}

	public static void addBtnPlaceOrderListener() {
		GUI.getBtnPlaceOrder().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					float totalPrice = -1;
					String clientName = new String();
					if (GUI.getQuantityTextField().getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Complete all the fields");
					} else {
						if (GUI.getClientNameTextField().getText().isEmpty()) {
							clientName = GUI.getClientsComboBox().getSelectedItem().toString();
							totalPrice = OrderService.placeOrder(clientName, GUI.getProductsComboBox().getSelectedItem().toString(),
									ProductService.retrieveProductPriceByName(GUI.getProductsComboBox().getSelectedItem().toString()),
									Integer.parseInt(GUI.getQuantityTextField().getText().trim()));
						} else {
							clientName = GUI.getClientNameTextField().getText().trim();
							totalPrice = OrderService.placeOrderWhileCreatingNewClient(clientName, GUI.getProductsComboBox().getSelectedItem().toString(),
									ProductService.retrieveProductPriceByName(GUI.getProductsComboBox().getSelectedItem().toString()),
									Integer.parseInt(GUI.getQuantityTextField().getText().trim()));
						}
						if (ProductService.retrieveProductStockByName(ProductService.retrieveProductById(ProductService.retrieveProductIdByName(
										GUI.getProductsComboBox().getSelectedItem().toString()))) >= Integer.parseInt(GUI.getQuantityTextField().getText().trim())) {
							JOptionPane.showMessageDialog(null,clientName + " ordered " + GUI.getQuantityTextField().getText().trim() + "x "
											+ GUI.getProductsComboBox().getSelectedItem().toString()
											+ ". Total price will be " + totalPrice);
							BillCreator.createBill(clientName, GUI.getProductsComboBox().getSelectedItem().toString(), Integer.parseInt(GUI.getQuantityTextField().getText().trim()),
									ProductService.retrieveProductPriceByName(GUI.getProductsComboBox().getSelectedItem().toString()));
						}
					}
					int i = 1;
					HashMap<Integer, Order> orders = OrderService.retrieveOrders();
					DefaultTableModel model = (DefaultTableModel) GUI.getTable_1().getModel();
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
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Enter valid values");
				}
			}
		});
	}

	public static void addBtnRegisterActionListener() {
		GUI.getBtnRegister().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (GUI.getProductNameTextField().getText().trim().isEmpty()
							|| GUI.getProductPriceTextField().getText().trim().isEmpty()
							|| GUI.getProductStockTextField().getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please complete all the necessary fields");
					} else {
						Product product = new Product();
						product.setName(GUI.getProductNameTextField().getText().trim());
						product.setPrice(Float.parseFloat(GUI.getProductPriceTextField().getText().trim()));
						product.setStock(Integer.parseInt(GUI.getProductStockTextField().getText().trim()));
						ProductService.saveProduct(product);
						JOptionPane.showMessageDialog(null, "Successfully registered new product - " + product.getName()
								+ " with price - " + product.getPrice() + "$, remaining stock - " + product.getStock());
						GUI.getProductsComboBox().removeAllItems();
						HashMap<Integer, Product> products = ProductService.getProducts();
						for (Entry<Integer, Product> entry : products.entrySet()) {
							products.put(entry.getKey(), entry.getValue());
							GUI.getProductsComboBox().addItem(entry.getValue().getName());
						}
					}

				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Try again");
					e1.printStackTrace();
				}
			}
		});
	}

	public static void addBtnAddClientActionListener() {
		GUI.getBtnAddClient().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newClientName = JOptionPane.showInputDialog("Enter new client's name: ");
				Client client = new Client();
				client.setName(newClientName);
				ClientService.saveClient(client);
				for (int i = 0; i < GUI.getClientsComboBox().getItemCount(); ++i) {
					GUI.getClientsComboBox().removeItemAt(0);
				}
				GUI.getClientsComboBox().setVisible(true);
				HashMap<Integer, Client> clients = ClientService.findAll();
				for (Entry<Integer, Client> entry : clients.entrySet()) {
					clients.put(entry.getKey(), entry.getValue());
					GUI.getClientsComboBox().addItem(entry.getValue().getName());
				}
			}
		});
	}

	public static void addBtnRemoveClientActionListener() {
		GUI.getBtnRemoveClient().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientService.removeClient(GUI.getClientsComboBox().getSelectedItem().toString());
				for (int i = 0; i < GUI.getClientsComboBox().getItemCount(); ++i) {
					GUI.getClientsComboBox().removeItemAt(0);
				}
				GUI.getClientsComboBox().setVisible(true);
				HashMap<Integer, Client> clients = ClientService.findAll();
				for (Entry<Integer, Client> entry : clients.entrySet()) {
					clients.put(entry.getKey(), entry.getValue());
					GUI.getClientsComboBox().addItem(entry.getValue().getName());
				}
			}
		});
	}

	public static void addBtnEditClientActionListener() {
		GUI.getBtnEditClient().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName = JOptionPane.showInputDialog(
						"Insert new name for " + GUI.getClientsComboBox().getSelectedItem().toString());
				if (newName != null) {
					ClientService.updateClient(GUI.getClientsComboBox().getSelectedItem().toString(), newName);
				}
				for (int i = 0; i < GUI.getClientsComboBox().getItemCount(); ++i) {
					GUI.getClientsComboBox().removeItemAt(0);
				}
				GUI.getClientsComboBox().setVisible(true);
				HashMap<Integer, Client> clients = ClientService.findAll();
				for (Entry<Integer, Client> entry : clients.entrySet()) {
					clients.put(entry.getKey(), entry.getValue());
					GUI.getClientsComboBox().addItem(entry.getValue().getName());
				}
			}
		});
	}

	public static void addBtnRefreshClientListActionListener() {
		GUI.getBtnRefreshClientList().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < GUI.getClientsComboBox().getItemCount(); ++i) {
					GUI.getClientsComboBox().removeItemAt(0);
				}
				HashMap<Integer, Client> clients = ClientService.findAll();
				for (Entry<Integer, Client> entry : clients.entrySet()) {
					clients.put(entry.getKey(), entry.getValue());
					GUI.getClientsComboBox().addItem(entry.getValue().getName());
				}
			}
		});
	}

	public static void addBtnRefreshTableActionListener() {
		GUI.getBtnRefreshTable().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 1;
				HashMap<Integer, Order> orders = OrderService.retrieveOrders();
				DefaultTableModel model = (DefaultTableModel) GUI.getTable_1().getModel();
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
		});
	}

	public static void addBtnRemoveProductActionListener() {
		GUI.getBtnRemoveProduct().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.getBtnRemove().setVisible(true);
				GUI.getBtnRemoveProduct().setVisible(false);
				JOptionPane.showMessageDialog(null, "Select the product you wish to delete from the products' list");
			}
		});
	}

	public static void addBtnRemoveActionListener() {
		GUI.getBtnRemove().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ProductService.removeProduct(GUI.getProductsComboBox().getSelectedItem().toString());
				int i = 1;
				HashMap<Integer, Order> orders = OrderService.retrieveOrders();
				DefaultTableModel model = (DefaultTableModel) GUI.getTable_1().getModel();
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

				HashMap<Integer, Product> products = ProductService.getProducts();
				for (Entry<Integer, Product> entry : products.entrySet()) {
					products.put(entry.getKey(), entry.getValue());
					GUI.getProductsComboBox().addItem(entry.getValue().getName());
				}
				GUI.getBtnRemove().setVisible(false);
				GUI.getBtnRemoveProduct().setVisible(true);
			}
		});
	}

	public static void addBtnEditProductActionListener() {
		GUI.getBtnEditProduct().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				GUI.getBtnEdit().setVisible(true);
				GUI.getBtnEditProduct().setVisible(false);
				JOptionPane.showMessageDialog(null, "Select the product you wish to edit from the products' list and then "
							+ "introduce the new values in the product edit area");
				GUI.getLblProductName().setVisible(true);
				GUI.getLblProductPrice().setVisible(true);
				GUI.getProductNameTextField().setVisible(true);
				GUI.getProductPriceTextField().setVisible(true);
				GUI.getLblProductStock().setVisible(true);
				GUI.getProductStockTextField().setVisible(true);
			}
		});
	}

	public static void addBtnEditActionListener() {
		GUI.getBtnEdit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName = new String();
				float newPrice = 0;
				int newStock = 0;
				if (GUI.getProductNameTextField().getText().isEmpty()) {
					newName = GUI.getProductsComboBox().getSelectedItem().toString();
				} else {
					newName = GUI.getProductNameTextField().getText().trim();
				}
				if (GUI.getProductPriceTextField().getText().isEmpty()) {
					newPrice = ProductService.retrieveProductPriceByName(GUI.getProductsComboBox().getSelectedItem().toString());
				} else {
					try {
						newPrice = Float.parseFloat(GUI.getProductPriceTextField().getText().trim());
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "Incorrect format");
					}
				}
				if (GUI.getProductStockTextField().getText().isEmpty()) {
					newStock = ProductService.retrieveProductStockByName(GUI.getProductsComboBox().getSelectedItem().toString());
				} else {
					try {
						newStock = Integer.parseInt(GUI.getProductStockTextField().getText().trim());
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "Incorrect format");
					}
				}
				ProductService.editProduct(GUI.getProductsComboBox().getSelectedItem().toString(), newName, newPrice, newStock);
				JOptionPane.showMessageDialog(null, "Changed product");
				int i = 1;
				HashMap<Integer, Order> orders = OrderService.retrieveOrders();
				DefaultTableModel model = (DefaultTableModel) GUI.getTable_1().getModel();
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
				HashMap<Integer, Product> products = ProductService.getProducts();
				for (Entry<Integer, Product> entry : products.entrySet()) {
					products.put(entry.getKey(), entry.getValue());
					GUI.getProductsComboBox().addItem(entry.getValue().getName());
				}
				GUI.getBtnEdit().setVisible(false);
				GUI.getBtnEditProduct().setVisible(true);
			}
		});
	}
}
