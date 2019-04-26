package businessLogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.jar.Attributes.Name;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dataAccess.ClientService;
import dataAccess.OrderService;
import dataAccess.ProductService;
import model.Client;
import model.Order;
import model.Product;
import presentation.GUI;

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
					int quantity = QuantityValidator.parseQuantity(GUI.getQuantityTextField().getText().trim());
					if (quantity != -1) {
						if (!NameValidator.isNameValid(GUI.getClientNameTextField().getText().trim())) {
							clientName = GUI.getClientsComboBox().getSelectedItem().toString();
							totalPrice = OrderService.placeOrder(clientName,
									GUI.getProductsComboBox().getSelectedItem().toString(),
									ProductService.retrieveProductPriceByName(
											GUI.getProductsComboBox().getSelectedItem().toString()),
									Integer.parseInt(GUI.getQuantityTextField().getText().trim()));
						} else {
							clientName = GUI.getClientNameTextField().getText().trim();
							totalPrice = OrderService.placeOrderWhileCreatingNewClient(clientName,
									GUI.getProductsComboBox().getSelectedItem().toString(),
									ProductService.retrieveProductPriceByName(
											GUI.getProductsComboBox().getSelectedItem().toString()),
									Integer.parseInt(GUI.getQuantityTextField().getText().trim()));
						}
						if (ProductService.retrieveProductStockByName(
								ProductService.retrieveProductById(ProductService.retrieveProductIdByName(
										GUI.getProductsComboBox().getSelectedItem().toString()))) >= QuantityValidator
												.parseQuantity(GUI.getQuantityTextField().getText().trim())) {
							JOptionPane.showMessageDialog(null,
									clientName + " ordered " + GUI.getQuantityTextField().getText().trim() + "x "
											+ GUI.getProductsComboBox().getSelectedItem().toString()
											+ ". Total price will be " + totalPrice);
							BillCreator.createBill(clientName, GUI.getProductsComboBox().getSelectedItem().toString(),
									Integer.parseInt(GUI.getQuantityTextField().getText().trim()),
									ProductService.retrieveProductPriceByName(
											GUI.getProductsComboBox().getSelectedItem().toString()));
						}
					}
					ContentRefresher.refreshTable();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Enter valid values");
				}
			}
		});
	}

	public static void addBtnRegisterActionListener() {
		GUI.getBtnRegister().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float price = -1;
				int stock = -1;
				if (NameValidator.isNameValid(GUI.getProductNameTextField().getText().trim())) {
					price = PriceValidator.parsePrice(GUI.getProductPriceTextField().getText().trim());
				}
				if (NameValidator.isNameValid(GUI.getProductStockTextField().getText().trim())) {
					stock = StockValidator.parseStock(GUI.getProductStockTextField().getText().trim());
				}
				String name = GUI.getProductNameTextField().getText().trim();
				if (NameValidator.isNameValid(name) && price != -1 && stock != -1) {
					Product product = new Product();
					product.setName(name);
					product.setPrice(price);
					product.setStock(stock);
					ProductService.saveProduct(product);
					JOptionPane.showMessageDialog(null, "Successfully registered new product - " + product.getName()
							+ " with price - " + product.getPrice() + "$, remaining stock - " + product.getStock());
					ContentRefresher.refreshProductsList();
				} else {
					System.out.println(price + " " + stock + " " + name);
					JOptionPane.showMessageDialog(null, "Try again");
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
				ContentRefresher.refreshClientsList();
			}
		});
	}

	public static void addBtnRemoveClientActionListener() {
		GUI.getBtnRemoveClient().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientService.removeClient(GUI.getClientsComboBox().getSelectedItem().toString());
				ContentRefresher.refreshClientsList();
			}
		});
	}

	public static void addBtnEditClientActionListener() {
		GUI.getBtnEditClient().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName = JOptionPane.showInputDialog(
						"Insert new name for " + GUI.getClientsComboBox().getSelectedItem().toString());
				if (NameValidator.isNameValid(newName)) {
					ClientService.updateClient(GUI.getClientsComboBox().getSelectedItem().toString(), newName);
				}
				ContentRefresher.refreshClientsList();
			}
		});
	}

	public static void addBtnRefreshClientListActionListener() {
		GUI.getBtnRefreshClientList().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ContentRefresher.refreshClientsList();
			}
		});
	}

	public static void addBtnRefreshTableActionListener() {
		GUI.getBtnRefreshTable().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContentRefresher.refreshTable();
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
				ContentRefresher.refreshTable();

				ContentRefresher.refreshProductsList();
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
				JOptionPane.showMessageDialog(null,
						"Select the product you wish to edit from the products' list and then "
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
				if (!NameValidator.isNameValid(GUI.getProductNameTextField().getText().trim())) {
					newName = GUI.getProductsComboBox().getSelectedItem().toString();
				} else {
					newName = GUI.getProductNameTextField().getText().trim();
				}
				if (!NameValidator.isNameValid(GUI.getProductPriceTextField().getText().trim())) {
					newPrice = ProductService
							.retrieveProductPriceByName(GUI.getProductsComboBox().getSelectedItem().toString());
				} else {
					newPrice = PriceValidator.parsePrice(GUI.getProductPriceTextField().getText().trim());
				}
				if (!NameValidator.isNameValid(GUI.getProductStockTextField().getText())) {
					newStock = ProductService
							.retrieveProductStockByName(GUI.getProductsComboBox().getSelectedItem().toString());
				} else {
					newStock = StockValidator.parseStock(GUI.getProductStockTextField().getText().trim());
				}
				if (newStock == -1 || newPrice == -1) {
					JOptionPane.showMessageDialog(null, "Try again");
				} else {
					ProductService.editProduct(GUI.getProductsComboBox().getSelectedItem().toString(), newName,
							newPrice, newStock);
					JOptionPane.showMessageDialog(null, "Changed product");
					ContentRefresher.refreshTable();
					ContentRefresher.refreshProductsList();
					GUI.getBtnEdit().setVisible(false);
					GUI.getBtnEditProduct().setVisible(true);
				}
			}
		});
	}
}
