package programa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import entrada.Teclado;
import gui.Login;

public class Cliente {
	public static boolean comprobar=false;
	public static Usuario usuarioGuardado;
	public static Cuenta cuentaGuardada;
	public static String email,contrasena;
	public static Login login;
	public static boolean ventanaCerrada= false;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.setProperty("javax.net.ssl.trustStore", "./Certificados SSL/AlmacenClienteSSL"); 
		System.setProperty("javax.net.ssl.trustStorePassword", "741258");
		
		String cadena = "", mensaje;
		String host = "localhost";
		int puerto = 60000;
		
		SSLSocketFactory sfact = (SSLSocketFactory) SSLSocketFactory.getDefault(); 
		SSLSocket cliente = (SSLSocket) sfact.createSocket(host, puerto);
		
		Usuario usuario;
	
		Transferencia transferencia=null;
		Object objeto = null;
		
		//Objetos para comunicación con servidor
		ObjectInputStream objetoEntrada= null;
		ObjectOutputStream objetoSalida= null;

		System.out.println("*************Cliente Iniciado*************");

		objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
		objetoEntrada = new ObjectInputStream(cliente.getInputStream());

		while(cadena != null && !cadena.trim().equals("salir")) {
			//envía al servidor
			objeto = menu();
			objetoSalida.writeObject(objeto);

			//recibe del servidor
			try {
				objeto = (Object) objetoEntrada.readObject();
				if(objeto instanceof String) {
					mensaje = (String) objeto;
					if(mensaje.equals("salir")) {
						cadena = mensaje;
					}
					else if(mensaje.equals("ingresado")) {
						System.out.println("****Ingreso realizado con éxito****");
					}
					else {
						System.out.println(mensaje);
					}
				}
				else if(objeto instanceof Cuenta) {
					sesionAbierta((Cuenta)objeto);
					if(comprobar) {
						cuentaGuardada =(Cuenta)objeto;
						System.out.println("Sesión abierta");
					}
					else {
						System.out.println("****Email o contraseña incorrecta****");
						System.out.println("");
					}
				}
				
				else if(objeto instanceof Transferencia) {
					transferencia=(Transferencia) objeto;

				}
				if(cadena.equals("exit")) {
					objeto = cadena;
				}

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("*************Cliente Finalizado*************");
		objetoEntrada.close();
		objetoSalida.close();
		cliente.close();
	}

	//crear método leer objeto y llamarlo dentro del bucle, le enviamos el objeto

	public static Object menu() throws IOException {
		Object objeto=null;
		Transferencia transferencia;
		Usuario usuario;
		
		Cuenta cuenta;
		String cadena;
		int numCuenta=0, opcion;
		double saldo, cantidad;

		if(!comprobar) {
			//login = new Login();
			//login.setVisible(true);
			System.out.println("Iniciar sesión");
			email = Teclado.leerCadena("Email: ");
			contrasena = Teclado.leerCadena("Contraseña: ");
			usuario = new Usuario(email, contrasena);
			objeto = usuario;
			usuarioGuardado= usuario;
		}
		else if(login.cerrar) {
			objeto ="exit";
		}
		else {
			//desde buffered hasta cadena se debe sustituir por el botón seleccionado del menú de usuarios
			visualizarMenuOpciones();
			opcion=Teclado.leerEntero("Operación a realizar:");
			if(opcion == 1) {
				numCuenta = cuentaGuardada.getNumCuenta();
				saldo = Teclado.leerReal("¿Cantidad a ingresar a tu cuenta?");
				cuenta = new Cuenta(numCuenta, saldo);
				objeto = cuenta;
			}
			else if(opcion == 2) {
				numCuenta = cuentaGuardada.getNumCuenta();
				cuenta = new Cuenta(numCuenta);
				objeto = cuenta;
			}
			else if(opcion == 3) {
				cantidad= Teclado.leerReal("Indica cantidad a transferir:");
				transferencia = new Transferencia(numCuenta,cantidad);
				transferenciaRealizada(transferencia);
			}
			else if(opcion == 0) {
				objeto ="exit";
			}
		}
		return objeto;
	}

	public static void sesionAbierta(Cuenta cuenta) {
		if(cuenta != null) {
			comprobar = true;
		}
	}
	
	public static void ingresarSaldo(Cuenta cuenta) {
		
		
	}
	public static void transferenciaRealizada(Transferencia transferencia) {
		Cuenta cuentaOrigen = null, cuentaDestino= null;
		double cantidad;
		if(transferencia != null) {
			System.out.println("Transferencia realizada con éxito.");
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
	public static void consultarSaldoCuenta(Cuenta cuenta) {
		
	}
	
	public static void ventanaCerrada() {
		ventanaCerrada = true;
	}
	
	public static void visualizarMenuOpciones() {
		System.out.println("*************************************************************");
		System.out.println("(0) Salir.");
		System.out.println("(1) Ingresar saldo a cuenta.");
		System.out.println("(2) Consultar saldo de cuenta.");
		System.out.println("(3) Transferir a otra cuenta.");
		System.out.println("*************************************************************");
	}
}
