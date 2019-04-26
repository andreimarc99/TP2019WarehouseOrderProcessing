package dataAccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Marc
 *
 *Clasa Database realizeaza conexiunea cu baza de date.
 */

public class Database {

	private static Database databaseConnection;

	private Connection connection = null;
	private Statement statement = null;
	
	public Database() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/WAREHOUSE", "user", "password");
			statement = connection.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeStatement(final String sqlQuery) {
		try {
			return statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Connection getConnection() {
		return connection;
	}

	public static Database getInstance() {
		if (databaseConnection == null) {
			databaseConnection = new Database();
		}
		return databaseConnection;
	}
}
