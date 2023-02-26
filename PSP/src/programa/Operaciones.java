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

	//Método para crear la conexión con la BD
	//y realizar modificaciones según el tipo de sentencia que le indiquemos como parámetro.
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

	//comprobar usuario en BD
	public static Cuenta comprobarUsuario(Usuario usuario)
			throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Cuenta cuenta= null;
		String dni;
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
				dni= resultados.getString("dni");
				cuenta= consultarCuentaXdni(dni);
			}
			resultados.close();
			sentencia.close();
		}
		finally {
			if (conexion != null) {
				conexion.close();
			}
		}		
		return cuenta;
	}
	//Consultar una cuenta pasando como parámetro el dni
	public static Cuenta consultarCuentaXdni(String dni) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Cuenta cuenta = null;
		Usuario usuario;
		double saldo;
		int numCuenta;
		try {
			Class.forName(NOMBRE_DRIVER);
			conexion = DriverManager.getConnection(NOMBRE_CONEXION);
			String sentenciaConsultar = "SELECT * FROM cuenta "+
					"WHERE dni_usuario = '" + dni + "'";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);	
			if(resultados.next()) {
				numCuenta = resultados.getInt("num_cuenta");
				saldo = resultados.getDouble("saldo");
				usuario = new Usuario(resultados.getString("dni_usuario"));
				cuenta = new Cuenta(numCuenta, saldo, usuario);
			}
			resultados.close();
			sentencia.close();
		}
		finally {
			if (conexion != null) {
				conexion.close();
			}
		}		

		return cuenta;
	}

	//Método para consultar el saldo de una cuenta por número de cuenta

	public static double consultarSaldo(Cuenta cuenta)throws SQLException, ClassNotFoundException {
		int numCuenta;
		double saldoActual=0;
		numCuenta = cuenta.getNumCuenta();
		Connection conexion=null;
		try {
			Class.forName(NOMBRE_DRIVER);
			conexion = DriverManager.getConnection(NOMBRE_CONEXION);
			String sentenciaConsultarSaldo = "SELECT saldo from Cuenta WHERE  num_cuenta = "+ numCuenta;
			Statement statement = conexion.createStatement();
			ResultSet resultado = statement.executeQuery(sentenciaConsultarSaldo);
			if(resultado.next()) {
				saldoActual = resultado.getDouble("saldo");
			}
		}
		finally {
			if (conexion != null) {
				conexion.close();
			}
		}		
		return saldoActual;
	}

	//Método para ingresar saldo a una cuenta
	public static boolean ingresarSaldo(Cuenta cuenta)throws SQLException, ClassNotFoundException {
		boolean transferido=false;
		double saldo, saldoAntiguo;
		int filasInsertadas= 0,numCuenta;

		numCuenta = cuenta.getNumCuenta();
		saldoAntiguo= consultarSaldo(cuenta);
		saldo = cuenta.getSaldo() + saldoAntiguo;
		String sentenciaModificar = "UPDATE cuenta SET saldo = "+ saldo + " WHERE num_cuenta = "+ numCuenta;
		filasInsertadas = ejecutarModificacion(sentenciaModificar);
		if(filasInsertadas == 1) {
			transferido=true;
		}
		return transferido;
	}


	//Método transferir que comprueba que la cuenta origen a restado de su saldo la cantidad a sumar en cuenta destino

	public static Transferencia transferencia (Transferencia trans) throws ClassNotFoundException, SQLException {
		Transferencia transferencia= null;
		Cuenta nuevaCuentaO,nuevaCuentaD;

		if ((actualizarSaldoCuentaOrigen(trans.getCuentaOrigen(),trans.getCantidadATransferir()))
				&& (actualizarSaldoCuentaDestino(trans.getCuentaDestino(),trans.getCantidadATransferir())) ) {
			nuevaCuentaO= consultarPorNumCuenta(trans.getCuentaOrigen());
			nuevaCuentaD= consultarPorNumCuenta(trans.getCuentaDestino());
			transferencia = new Transferencia(nuevaCuentaO,nuevaCuentaD);
		} 
		return transferencia;
	}

	//Método para actualizar saldo de cuenta origen
	public static boolean actualizarSaldoCuentaOrigen(Cuenta  cuentaorigen , double dinero) throws SQLException, ClassNotFoundException {
		boolean descontado =false;
		Cuenta cuentAux;
		double saldodescontado  = 0;
		int filasInsertadas=0;

		cuentAux= consultarPorNumCuenta(cuentaorigen);
		if (cuentAux.getSaldo()>0) {
			saldodescontado = cuentAux.getSaldo()- dinero;
			String sentenciaModificada ="UPDATE cuenta SET saldo = "+ saldodescontado + " WHERE num_cuenta = "+cuentAux.getNumCuenta();
			filasInsertadas = ejecutarModificacion(sentenciaModificada);
			if(filasInsertadas == 1) {
				descontado=true;
			}
		} 

		return descontado;
	}

	//Método para actualizar saldo de cuenta destino
	public static boolean actualizarSaldoCuentaDestino(Cuenta cuentadestino , double dinero) throws SQLException, ClassNotFoundException {
		boolean ingresado = false;
		Connection conexion = null;
		Cuenta cuentAux;
		double saldoIngresado= 0;
		int filasInsertadas  =0;

		cuentAux= consultarPorNumCuenta(cuentadestino);
		saldoIngresado = cuentAux.getSaldo()+dinero;
		String sentenciaModificada ="UPDATE cuenta SET saldo = "+ saldoIngresado + " WHERE num_cuenta = "+cuentAux.getNumCuenta();
		filasInsertadas = ejecutarModificacion(sentenciaModificada);
		if(filasInsertadas == 1) {
			ingresado=true;
		}
		return ingresado;
	}

	//Método para obtener una cuenta completa, pasando como parámetro número de cuenta
	public static Cuenta consultarPorNumCuenta(Cuenta cuenta) throws SQLException, ClassNotFoundException {
		Connection conexion=null;
		Cuenta nuevaCuenta = null;
		int numCuenta;
		double saldoActual=0;
		Usuario usuario;
		numCuenta = cuenta.getNumCuenta();

		try {
			Class.forName(NOMBRE_DRIVER);
			conexion = DriverManager.getConnection(NOMBRE_CONEXION);
			String sentenciaConsultarSaldo = "SELECT * from Cuenta WHERE  num_cuenta = "+ numCuenta;
			Statement statement = conexion.createStatement();
			ResultSet resultado = statement.executeQuery(sentenciaConsultarSaldo);
			if(resultado.next()) {
				saldoActual = resultado.getDouble("saldo");
				numCuenta = resultado.getInt("num_cuenta");
				usuario = new Usuario(resultado.getString("dni_usuario"));
				nuevaCuenta = new Cuenta(numCuenta, saldoActual, usuario);
			}
		}
		finally {
			if (conexion != null) {
				conexion.close();
			}
		}		
		return nuevaCuenta;
	}

}
