package ProjectJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Connection {
	
	private static Statement statement;
	private static Connection connection;
	private static String dbname = "Project";
	
	public static String getDBname() {
		return dbname;
	}

	public DB_Connection() {
		while (true) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
		        
		        App.setUserString("Database password: "); //Input String
		        setConnection(DriverManager.getConnection("jdbc:mysql://localhost:3306/" + getDBname(), "root", App.getUserString()));
		        System.out.println("Database '" + dbname + "' has been loaded successfully!");
		        setStatement(getConnection().createStatement());
				break;
			} catch (SQLException e) {
				System.err.println("Incorrect password or database '" + getDBname() + "' does not exist, please try again!\n");
			} catch (ClassNotFoundException e) {
				System.err.println("JDBC not found! - " + e);
				System.exit(1);
				break;
			}
		}
	}

	public static void setConnection(Connection connection) {
		DB_Connection.connection = connection;
	}
	
	public static Connection getConnection() {
		return connection;
	}

	public static void setStatement(Statement statement) {
		DB_Connection.statement = statement;
	}

	public static Statement getStatement() {
		return statement;
	}
}
