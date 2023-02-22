package programa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;

public class Operaciones {
	public static final String NOMBRE_DRIVER ="org.sqlite.JDBC";
	public static final String NOMBRE_BASE_DATOS = "data\\cuentasBancarias.db";
	public static final String NOMBRE_CONEXION ="jdbc:sqlite:" + NOMBRE_BASE_DATOS;


	//comprobar usuario en BD
	public static boolean comprobarUsuario(Usuario usuario)
			throws ClassNotFoundException, SQLException {
		boolean existeUsuario=false;
		Connection conexion = null;
		String email = usuario.getEmail();
		String contrasena = usuario.getContrasena();
		
		try {
			Class.forName(NOMBRE_DRIVER);
			conexion = DriverManager.getConnection(NOMBRE_CONEXION);
			String sentenciaConsultar = "SELECT * FROM usuario "+
					"WHERE email = '" + email + "' and contrasena = '"+ contrasena + "'";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);	
			if(resultados.next()) {
				existeUsuario=true;
			}
			resultados.close();
			sentencia.close();
		}
		finally {
			if (conexion != null) {
				conexion.close();
			}
		}		
		return existeUsuario;
	}
	
	public static boolean ingresarSaldo(Cuenta cuenta)throws SQLException, ClassNotFoundException {
		boolean transferido=false;
		double saldo;
		int filasInsertadas= 0,numCuenta;
		
		numCuenta = cuenta.getNumCuenta();
		saldo = cuenta.getSaldo();
		
		String sentenciaModificar = "UPDATE cuenta SET saldo = "+ saldo + " WHERE num_cuenta = "+ numCuenta;
		filasInsertadas = ejecutarModificacion(sentenciaModificar);
		if(filasInsertadas == 1) {
			transferido=true;
		}
		return transferido;
	}
	
	//Insertar usuarios en BD para pruebas
	
	public static void crearUsuario() throws ClassNotFoundException, SQLException {
		Usuario usuario=null;
		boolean creado = false;
		 String nombre, contrasena, email, telefono, dni;
		 
		usuario = new Usuario ("H12742538","Alejandro Mart�nez","alemartinez@gmail.com", "Alem", "636874241");
		if(insertarUsuario(usuario)) {
			 creado = true;
		} 
	}
	public static int ejecutarModificacion(String sentenciaInsertar)
			throws SQLException, ClassNotFoundException{
		Connection conexion = null;
		int filasModificadas=0;
		try {
			Class.forName(NOMBRE_DRIVER);
			SQLiteConfig config = new SQLiteConfig();
			config.enforceForeignKeys(true);
			conexion = DriverManager.getConnection(NOMBRE_CONEXION,
					config.toProperties());
			Statement sentencia = conexion.createStatement();
			filasModificadas= sentencia.executeUpdate(sentenciaInsertar);
		}
		finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return filasModificadas;
	}

	public static boolean insertarUsuario(Usuario usuario)
			throws SQLException, ClassNotFoundException {
		boolean insertado = false;
		int filasInsertadas= 0;
		String sentenciaInsertar = "INSERT INTO usuario (dni, nombre, email,"
				+ " contrasena, telefono)" +
				"VALUES ('" + usuario.getDni() + 
				"', '" + usuario.getNombre() +
				"', '" + usuario.getEmail() +
				"', '" + usuario.getContrasena() +
				"','" + usuario.getTelefono()+ "')";
		filasInsertadas =  ejecutarModificacion(sentenciaInsertar);
		if(filasInsertadas == 1) {
			insertado=true;
		}
		return insertado;
	}

}