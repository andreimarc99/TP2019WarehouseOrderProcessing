package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import model.Client;
import model.Order;
import model.Product;

/**
 * Clasa OrderService contine metode care lucreaza cu continutul din baza de
 * date ce au legatura cu comenzile.
 * 
 * @author Marc
 *
 */
public class OrderService {
	static Database database;

	/**
	 * Creeaza o noua comanda, iar in acelasi timp, creeaza un client si un produs nou
	 * 
	 * @param clientName numele clientului ce trimite comanda
	 * @param productName numele produsului creat
	 * @param productPrice pretul produsului creat
	 * @param quantity cantitatea care se va comanda
	 * @return pretul total al comenzii
	 */
	public static float placeOrderWhileCreatingNewProduct(String clientName, String productName, float productPrice,
			int quantity) {
		database = new Database();
		final Connection connection = database.getConnection();
		float totalPrice = 0;

		PreparedStatement preparedStatement;
		try {
			Client client = new Client();
			client.setName(clientName);
			ClientService.saveClient(client);
			Product product = new Product();
			product.setName(productName);
			product.setPrice(productPrice);
			ProductService.saveProduct(product);
			int clientId = ClientService.retrieveClientIdByName(client);
			int productId = ProductService.retrieveProductIdByName(product.getName());
			preparedStatement = connection
					.prepareStatement("insert into WAREHOUSE.ORDER_ (productId, clientId, quantity) values (?, ?, ?)");
			preparedStatement.setInt(1, productId);
			preparedStatement.setInt(2, clientId);
			preparedStatement.setInt(3, quantity);
			preparedStatement.executeUpdate();

			totalPrice = productPrice * quantity;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalPrice;
	}

	/**
	 * Plaseaza o comanda pentru un produs existent
	 * 
	 * @param clientName   numele clientului ce plaseaza comanda
	 * @param productName  numele produsului ce va fi comandat
	 * @param productPrice pretul produsului ce va fi comandat
	 * @param quantity     cantitatea de produse comandate
	 * @return pretul total al comenzii
	 */
	public static float placeOrder(String clientName, String productName, float productPrice, int quantity) {
		database = new Database();
		final Connection connection = database.getConnection();
		float totalPrice = 0;

		PreparedStatement preparedStatement;
		PreparedStatement preparedStatement1;
		try {
			Client client = new Client();
			client.setName(clientName);
			//ClientService.saveClient(client);
			Product product = new Product();
			product.setName(productName);
			product.setPrice(productPrice);
			int stock = ProductService.retrieveProductStockByName(product.getName());
			if (stock >= quantity) {
				preparedStatement = connection.prepareStatement(
						"insert into WAREHOUSE.ORDER_ (productId, clientId, quantity) values (?, ?, ?)");
				preparedStatement.setInt(1, ProductService.retrieveProductIdByName(product.getName()));
				preparedStatement.setInt(2, ClientService.retrieveClientIdByName(client));
				preparedStatement.setInt(3, quantity);
				preparedStatement.executeUpdate();
				stock -= quantity;
				preparedStatement1 = connection.prepareStatement("UPDATE PRODUCT SET stock = ? WHERE PRODUCT.productName = ?");
				preparedStatement1.setInt(1, stock);
				preparedStatement1.setString(2, productName);
				preparedStatement1.executeUpdate();
				totalPrice = productPrice * quantity;
			} else {
				JOptionPane.showMessageDialog(null, "Not enough items in stock at the moment");
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalPrice;
	}
	
	public static float placeOrderWhileCreatingNewClient(String clientName, String productName, float productPrice, int quantity) {
		database = new Database();
		final Connection connection = database.getConnection();
		float totalPrice = 0;

		PreparedStatement preparedStatement;
		PreparedStatement preparedStatement1;
		try {
			Client client = new Client();
			client.setName(clientName);
			ClientService.saveClient(client);
			Product product = new Product();
			product.setName(productName);
			product.setPrice(productPrice);
			int stock = ProductService.retrieveProductStockByName(product.getName());
			if (stock >= quantity) {
				preparedStatement = connection.prepareStatement(
						"insert into WAREHOUSE.ORDER_ (productId, clientId, quantity) values (?, ?, ?)");
				preparedStatement.setInt(1, ProductService.retrieveProductIdByName(product.getName()));
				preparedStatement.setInt(2, ClientService.retrieveClientIdByName(client));
				preparedStatement.setInt(3, quantity);
				preparedStatement.executeUpdate();
				stock -= quantity;
				preparedStatement1 = connection.prepareStatement("UPDATE PRODUCT SET stock = ? WHERE PRODUCT.productName = ?");
				preparedStatement1.setInt(1, stock);
				preparedStatement1.setString(2, productName);
				preparedStatement1.executeUpdate();
				totalPrice = productPrice * quantity;
			} else {
				JOptionPane.showMessageDialog(null, "Not enough items in stock at the moment");
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalPrice;
	}

	/**
	 * Cauta toate comenzile din baza de date
	 * 
	 * @return toate comenzile sub forma unui HashMap
	 */
	public static HashMap<Integer, Order> retrieveOrders() {
		database = new Database();
		final Connection connection = database.getConnection();
		HashMap<Integer, Order> orders = new HashMap<Integer, Order>();
		int counter = 0;

		PreparedStatement preparedStatement;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM ORDER_");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Order order = new Order();
				order.setClientName(ClientService.retrieveClientNameById(resultSet.getInt("clientId")));
				order.setQuantity(resultSet.getInt("quantity"));
				order.setProduct(ProductService.retrieveProductById(resultSet.getInt("productId")));
				orders.put(counter++, order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}
}
