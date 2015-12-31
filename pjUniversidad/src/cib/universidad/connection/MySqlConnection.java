package cib.universidad.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlConnection {

	private static Connection cn;

	public static Connection getConnection() throws SQLException{
		if(cn != null || !cn.isClosed())
			return cn;
		connect();
		return cn;
	}

	public static Connection connect() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Error" + e.getMessage());
		}
		cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pjuniversidad","root","H81eeptm");
		return cn;

	}

	public static void closeConnections(ResultSet rs, PreparedStatement pstm){
		try {
			if(rs != null)
				rs.close();
			if(pstm != null)
				pstm.close();
			if(cn != null){
				cn.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


}
