import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sqlite.SQLiteConfig;


public class GestionDatos {

	public boolean comprobarUsuario() {
	
		boolean comprobado=false;
		
		
		

	return comprobado;
	
	}
	
	public boolean buscarCuenta(int numCuenta) throws ClassNotFoundException, SQLException {

		boolean ingresado=false;
		Connection conexion = null;
		
		try {

			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			config.enforceForeignKeys(true);
			conexion = DriverManager.getConnection("jdbc:sqlite:db\\personal.db");
			String sentenciaInsertar = "SELECT saldo FROM cuenta" + "JOIN usuario  on(dni=dni_usuario)"
									+ "	where num_cuenta = "+numCuenta;
		}
		finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return ingresado;

		//esquema main
		//se solicita un numero de cuenta
		//ejecutamos metodo para buscar cuenta
		
		//si se encuentra se procede a añadir saldo 
		//ejecutamos metodo para añadir saldo
		//mostrar saldo final por pantalla
		
		//si no, mensaje de error
		
		
		
	}
	
}
