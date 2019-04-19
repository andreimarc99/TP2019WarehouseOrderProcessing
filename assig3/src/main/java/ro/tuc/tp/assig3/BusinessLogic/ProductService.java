package ro.tuc.tp.assig3.BusinessLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import ro.tuc.tp.assig3.Database.Database;
import ro.tuc.tp.assig3.Model.Product;
/**
 * Clasa ProductService contine metode care lucreaza cu continutul din baza de date ce au legatura cu produsele.
 * @author Marc
 *
 */
public class ProductService {
	static Database database;
	/**
	 * Adauga un produs in baza de date
	 * @param product produsul care trebuie urmeaza sa fie adaugat in baza de date
	 */
	public static void saveProduct(final Product product) {
		database = new Database();
		final Connection connection = database.getConnection();

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(
					"insert into WAREHOUSE.PRODUCT (productName, price, stock) values (?, ?, ?)");
			preparedStatement.setString(1, product.getName());
			preparedStatement.setFloat(2, product.getPrice());
			preparedStatement.setInt(3, product.getStock());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sterge un produs din baza de date, impreuna cu toate comenzile pentru acel produs
	 * @param clientName numele produsului care trebuie sters
	 */
	public static void removeProduct(String productName) { 
		database = new Database();
		final Connection connection = database.getConnection();

		PreparedStatement preparedStatement;
		PreparedStatement orderStatement;
		try {
			orderStatement = connection.prepareStatement("delete from ORDER_ where ORDER_.productId = ?");
			orderStatement.setInt(1, ProductService.retrieveProductIdByName(productName));
			orderStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(
					"delete from PRODUCT where productName = ?");
			preparedStatement.setString(1, productName);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Schimba datele unui produs - in cazul in care unii parametri nu sunt dati, vor fi lasati cei dinainte
	 * @param productName numele vechi al produsului
	 * @param newName numele nou al produsului
	 * @param newPrice pretul nou al produsului
	 * @param newStock stocul nou al produsului
	 */
	public static void editProduct(String productName, String newName, float newPrice, int newStock) {
		database = new Database();
		final Connection connection = database.getConnection();

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("update PRODUCT set productName = ?, price = ?, stock = ? where productName = ?");
			preparedStatement.setString(1, newName);
			preparedStatement.setFloat(2, newPrice);
			preparedStatement.setInt(3, newStock);
			preparedStatement.setString(4, productName);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Cauta un produs dupa nume si ii returneaza id-ul
	 * @param product produsul a carui nume va fi folosit pentru cautare
	 * @return id-ul produsului cautat
	 */
	public static int retrieveProductIdByName(String productName) {
		database = new Database();
		final Connection connection = database.getConnection();
		int id = -1;

		PreparedStatement preparedStatement;
		ResultSet resultSet = null; 
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM PRODUCT WHERE PRODUCT.productName = ?");
			preparedStatement.setString(1, productName);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				id =  resultSet.getInt("productId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	/**
	 * Cauta un produs dupa id
	 * @param id id-ul produsului cautat
	 * @return produsul cautat dupa id
	 */
	public static Product retrieveProductById(int id) {
		database = new Database();
		final Connection connection = database.getConnection();
		Product product = new Product();

		PreparedStatement preparedStatement;
		ResultSet resultSet = null; 
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM PRODUCT WHERE PRODUCT.productId = ?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				product.setName(resultSet.getString("productName"));
				product.setPrice(resultSet.getFloat("price"));
				product.setStock(resultSet.getInt("stock"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}
	/**
	 * Cauta pretul unui produs dat prin numele sau
	 * @param productName numele produsului care trebuie cautat
	 * @return pretul produsului cautat
	 */
	public static float retrieveProductPriceByName(String productName) {
		database = new Database();
		final Connection connection = database.getConnection();
		float price = 0;

		PreparedStatement preparedStatement;
		ResultSet resultSet = null; 
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM PRODUCT WHERE PRODUCT.productName = ?");
			preparedStatement.setString(1, productName);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				price =  resultSet.getFloat("price");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return price;
	}
	/**
	 * Returneaza stocul disponibil dupa numele unui produs
	 * @param productName numele produsului respectiv
	 * @return stocul disponibil
	 */
	public static int retrieveProductStockByName(String productName) {
		database = new Database();
		final Connection connection = database.getConnection();
		int stock = 0;

		PreparedStatement preparedStatement;
		ResultSet resultSet = null; 
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM PRODUCT WHERE PRODUCT.productName = ?");
			preparedStatement.setString(1, productName);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				stock =  resultSet.getInt("stock");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stock;
	}
	/**
	 * Cauta toate produsele din baza de date
	 * @return toate produsele din baza de date sub forma de HashMap
	 */
	public static HashMap<Integer, Product> getProducts() {
		database = new Database();
		final Connection connection = database.getConnection();
		HashMap<Integer, Product> products = new HashMap<Integer, Product>();
		int counter = 0;

		PreparedStatement preparedStatement;
		ResultSet resultSet = null; 
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM PRODUCT");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Product product = new Product();
				product.setName(resultSet.getString("productName"));
				product.setPrice(resultSet.getFloat("price"));
				product.setStock(resultSet.getInt("stock"));
				products.put(counter++, product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	/**
	 * Returneaza stocul disponibil al unui produs dupa nume, fiind dat produsul ca parametru
	 * @param product produsul respectiv
	 * @return stocul disponibil
	 */
	public static int retrieveProductStockByName(Product product) {
		database = new Database();
		final Connection connection = database.getConnection();
		int stock = -1;

		PreparedStatement preparedStatement;
		ResultSet resultSet = null; 
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM PRODUCT WHERE PRODUCT.productName = ?");
			preparedStatement.setString(1, product.getName());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				stock =  resultSet.getInt("stock");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stock;
	}
}
