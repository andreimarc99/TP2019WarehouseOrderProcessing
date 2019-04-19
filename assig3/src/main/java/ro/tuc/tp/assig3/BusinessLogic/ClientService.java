package ro.tuc.tp.assig3.BusinessLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import ro.tuc.tp.assig3.Database.Database;
import ro.tuc.tp.assig3.Model.Client;
/**
 * 
 * @author Marc
 * Clasa ClientService contine metode care lucreaza cu continutul din baza de date ce au legatura cu clientii.
 */
public class ClientService {
	static Database database;
	/**
	 * Salveaza un client in baza de date.
	 * @param client clientul care se va salva in baza de date
	 */
	public static void saveClient(final Client client) {
		database = new Database();
		final Connection connection = database.getConnection();

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(
					"insert into WAREHOUSE.CLIENT_ (clientName) values (?)");
			preparedStatement.setString(1, client.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sterge un client din baza de date, impreuna cu toate comenzile efectuate
	 * @param clientName numele clientului care trebuie sters
	 */
	public static void removeClient(String clientName) { 
		database = new Database();
		final Connection connection = database.getConnection();

		PreparedStatement preparedStatement;
		PreparedStatement orderStatement;
		try {
			orderStatement = connection.prepareStatement("delete from ORDER_ where ORDER_.clientId = ?");
			orderStatement.setInt(1, ClientService.retrieveClientIdByName(ClientService.getClientByName(clientName)));
			orderStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(
					"delete from CLIENT_ where clientName = ?");
			preparedStatement.setString(1, clientName);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Modifica numele unui client
	 * @param clientName numele clientului
	 * @param newName noul nume al clientului
	 */
	public static void updateClient(String clientName, String newName) {
		database = new Database();
		final Connection connection = database.getConnection();

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(
					"update CLIENT_ set clientName = ? where clientName = ?");
			preparedStatement.setString(1, newName);
			preparedStatement.setString(2, clientName);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returneaza toti clientii din baza de date
	 * @return HashMap-ul ce contine clientii 
	 */
	public static HashMap<Integer, Client> findAll() {
		database = new Database();
		final Connection connection = database.getConnection();
		HashMap<Integer, Client> clients = new HashMap<Integer, Client>();
		int counter = 0;

		PreparedStatement preparedStatement;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM CLIENT_");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Client client = new Client();
				client.setName(resultSet.getString("clientName"));
				clients.put(counter++, client);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}
	/**
	 * Cauta un client dupa nume si ii returneaza id-ul
	 * @param clientul caruia ii cautam id-ul
	 * @return id-ul clientului
	 */
	public static int retrieveClientIdByName(final Client client) {
		database = new Database();
		final Connection connection = database.getConnection();
		int id = -1;

		PreparedStatement preparedStatement;
		ResultSet resultSet = null; 
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM CLIENT_ WHERE CLIENT_.clientName = ?");
			preparedStatement.setString(1, client.getName());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				id =  resultSet.getInt("clientId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	/**
	 * Cauta un nume dupa id si ii returneaza numele
	 * @param id id-ul clientului cautat
	 * @return numele clientului cautat
	 */
	public static String retrieveClientNameById(int id) {
		database = new Database();
		final Connection connection = database.getConnection();
		String name = null;

		PreparedStatement preparedStatement;
		ResultSet resultSet = null; 
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM CLIENT_ WHERE CLIENT_.clientId = ?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				name =  resultSet.getString("clientName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}
	/**
	 * Gaseste clientul dupa un nume 
	 * @param clientName numele clientului care trebuie cautat
	 * @return clientul gasit
	 */
	public static Client getClientByName(String clientName) {
		database = new Database();
		final Connection connection = database.getConnection();
		Client client = new Client();

		PreparedStatement preparedStatement;
		ResultSet resultSet = null; 
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM CLIENT_ WHERE CLIENT_.clientName = ?");
			preparedStatement.setString(1, clientName);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				client.setName(resultSet.getString("clientName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}
	
	public static boolean clientExists(Client client) {
		database = new Database();
		final Connection connection = database.getConnection();

		PreparedStatement preparedStatement;
		ResultSet resultSet = null; 
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ?" + 
					"AND TABLE_NAME = CLIENT_" + 
					"AND COLUMN_NAME = clientName");
			preparedStatement.setString(1, client.getName());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
