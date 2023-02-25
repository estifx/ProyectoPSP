package programa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class HiloServidor extends Thread{

	String mensaje;
	Socket cliente = null;
	Object objeto;
	Object objetoNuevo;
	ObjectInputStream objEntrada; 
	ObjectOutputStream objSalida;

	// Constructor
	public HiloServidor(Socket cliente) throws IOException {
		this.cliente = cliente;
		objEntrada = new ObjectInputStream(cliente.getInputStream()); 
		objSalida = new ObjectOutputStream(cliente.getOutputStream());
	}

	public void run() {
		String cadena="";
		while (cadena != null && !cadena.trim().equals("exit")) { 
			System.out.println("COMUNICO CON: "+ cliente.toString());
			//Lee lo enviado por el cliente
			try {
				objeto = objEntrada.readObject();
				objetoNuevo = comprobarObjeto(objeto);
				if(objetoNuevo instanceof String) {
					if(objetoNuevo.equals("salir")) {
						cadena = "exit";
					}
				}
				//escribe a cliente
				objSalida.writeObject(objetoNuevo);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 	
		}
		System.out.println("\nConexión finalizada con: "+ cliente.toString());
		try {
			objEntrada.close();
			objSalida.close(); 
		cliente.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object comprobarObjeto(Object objeto) throws ClassNotFoundException, SQLException {
		String mensaje;
		Object objetoNuevo = null;
		Usuario usuario;
		Cuenta cuenta;

		if(objeto instanceof Usuario) {
			usuario = (Usuario)objeto;
			objetoNuevo= inicioSesion(usuario);
		}
		else if(objeto instanceof String) {
			mensaje = (String) objeto;
			if(mensaje.equals("exit")) {
				objetoNuevo= "salir";
			}
		}
		else if(objeto instanceof Cuenta) {
			cuenta = (Cuenta) objeto;
			objetoNuevo= ingresarEnCuenta(cuenta);
		}
		return objetoNuevo;
	}
	
	public synchronized Object inicioSesion(Usuario usuario) throws ClassNotFoundException, SQLException {
		Object objeto=null;
		Usuario user;
		Cuenta cuenta;
		int numCuenta=0;
		String mensaje;
		
		cuenta = Operaciones.comprobarUsuario(usuario);
		numCuenta = cuenta.getNumCuenta();
		if(numCuenta != 0){
			objeto= cuenta;
		}
		else {
			mensaje ="no existe";
			objeto= mensaje;
		}
		return objeto;
	}
	
	public synchronized Object ingresarEnCuenta(Cuenta cuenta) throws ClassNotFoundException, SQLException {
		Object objeto=null;
		String mensaje;
		if(Operaciones.ingresarSaldo(cuenta)){
			mensaje ="ingresado";
			objeto= mensaje;
		}
		return objeto;
	}
	
	public synchronized Object transferirEntreCuentas(Transferencia transferencia , double dineroTransferido) throws ClassNotFoundException, SQLException{
		Object objeto = null;
		String mensaje;
		if(Operaciones.Transferencia(transferencia, dineroTransferido)) {
			mensaje="transferencia realizada";
			objeto= mensaje;
		}
		else {
			mensaje ="saldo insuficiente";
			objeto= mensaje;
		}

		return objeto;
	}

}
