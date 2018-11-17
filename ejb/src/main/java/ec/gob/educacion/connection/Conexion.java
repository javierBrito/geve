package ec.gob.educacion.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	public static Connection conectar() {
		Connection con = null;
		
		String usuario = "CUPOS";
		String password = "cupos";
		String url = "jdbc:mysql://localhost:3306/gventas?user=" + usuario
				+ "&password=" + password;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			con = DriverManager.getConnection(url);
			if (con != null) {
				System.out.println("Conectado");
			}
		} catch (SQLException e) {
			System.out.println("No se pudo conectar a la base de datos");
			e.printStackTrace();
		}
		
		return con;
	}
}