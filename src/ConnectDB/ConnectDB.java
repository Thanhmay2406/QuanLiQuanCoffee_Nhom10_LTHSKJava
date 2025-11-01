package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	private static ConnectDB instance;
	private Connection connection;

	private ConnectDB() {
		try {
			// Thay đổi URL, user, pass cho phù hợp với CSDL của bạn
			String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyQuanBanCoffee_Updated;encrypt=true;trustServerCertificate=true;";
			String user = "sa";
			String password = "sapassword";
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static synchronized ConnectDB getInstance() {
		if (instance == null) {
			instance = new ConnectDB();
		}
		return instance;
	}

	public Connection getConnection() {
		return connection;
	}
}