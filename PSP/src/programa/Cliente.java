package programa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import entrada.Teclado;

public class Cliente {
	public static boolean comprobar=false;
	public static Usuario usuarioGuardado;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String cadena = "", mensaje;
		String host = "localhost"; 
		Usuario usuario;
		TransferenciaCuentas transferencia=null;
		Object objeto = null;
		int puerto = 60000;

		Socket cliente = new Socket(host, puerto);
		//Comunicaci�n con servidor
		ObjectInputStream objetoEntrada= null;
		ObjectOutputStream objetoSalida= null;

		System.out.println("*************Cliente*************");

		//BufferedReader in = new BufferedReader (new InputStreamReader(System.in)); 



		objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
		objetoEntrada = new ObjectInputStream(cliente.getInputStream());

		while(cadena != null && !cadena.trim().equals("salir")) {
			//env�a al servidor
			objeto = menu();
			objetoSalida.writeObject(objeto);

			//recibe del servidor
			try {
				objeto = (Object) objetoEntrada.readObject();
				if(objeto instanceof String) {
					mensaje = (String) objeto;
					if(mensaje.equals("existe")) {
						sesionAbierta(mensaje);
						System.out.println("Sesi�n abierta");
					}
					else if(mensaje.equals("salir")) {
						cadena = mensaje;
					}
					else if(mensaje.equals("ingresado")) {
						System.out.println("Ingreso realizado con �xito");
					}
				}
				if(objeto instanceof TransferenciaCuentas) {
					transferencia=(TransferenciaCuentas) objeto;
					
					
					
				}
				//System.out.print("Introduce cadena: "); 
				//cadena = in.readLine();
				if(cadena.equals("exit")) {
					objeto = cadena;
				}

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		objetoEntrada.close();
		objetoSalida.close();
		cliente.close();
	}

	//crear m�todo leer objeto y llamarlo dentro del bucle, le enviamos el objeto

	public static Object menu() throws IOException {
		Object objeto=null;
		TransferenciaCuentas transferencia;
		Usuario usuario;
		
		Cuenta cuenta;
		String email, contrasena, cadena;
		int numCuenta, opcion;
		double saldo, cantidad;

		if(!comprobar) {
			System.out.println("Iniciar sesi�n");
			email = Teclado.leerCadena("Email:");
			contrasena = Teclado.leerCadena("Contrasena:");
			usuario = new Usuario(email, contrasena);
			objeto = usuario;
			usuarioGuardado= usuario;
		}
		else {
			//desde buffered hasta cadena se debe sustituir por el bot�n seleccionado del men� de usuarios
			visualizarMenuOpciones();
			opcion=Teclado.leerEntero("Operaci�n a realizar:");
			if(opcion == 1) {
				numCuenta = Teclado.leerEntero("�N�mero de cuenta?");
				saldo= Teclado.leerReal("�Cantidad a ingresar?");
				cuenta = new Cuenta(numCuenta, saldo);
				objeto = cuenta;
			}
			else if(opcion == 2) {
				System.out.println("Indica la cuenta a la que deseas realizar la transferencia:");
				numCuenta= Teclado.leerEntero("Indica n�mero de cuenta?");
				cantidad= Teclado.leerReal("Indica cantidad a transferir:");
				transferencia = new TransferenciaCuentas(usuarioGuardado,numCuenta,cantidad);
				transferenciaRealizada(transferencia);
			}
			else if(opcion == 3) {
				
			}
		}
		return objeto;
	}

	public static void sesionAbierta(String mensaje) {
		if(mensaje.equals("existe")) {
			comprobar = true;
		}
	}
	
	public static void transferenciaRealizada(TransferenciaCuentas transferencia) {
		Cuenta cuentaOrigen = null, cuentaDestino= null;
		int numCuentaOrigen, numCuentaDestino;
		double cantidad;
		if(transferencia != null) {
			System.out.println("Transferencia realizada con �xito.");
			cuentaOrigen= transferencia.getCuentaOrigen();
			cuentaDestino= transferencia.getCuentaDestino();
			cantidad= transferencia.getCantidadATransferir();
			System.out.println("Se ha realizado una transferencia de la cuenta # "
					+ cuentaOrigen.getNumCuenta()+ " a la cuenta # "
					+ cuentaDestino.getNumCuenta() + " la cantidad de: "
					+ cantidad);
		}
		else {
			System.out.println("No se puede realizar la transferencia. Saldo insuficiente o cuenta destino incorrecta");
		}

	}
	
	public static void visualizarMenuOpciones() {
		System.out.println("*************************************************************");
		System.out.println("(0) Salir.");
		System.out.println("(1) Ingresar saldo a cuenta.");
		System.out.println("(2) Consultar saldo de cuenta.");
		System.out.println("(3) Transferir monto a otra cuenta.");
		System.out.println("*************************************************************");
	}
}
