import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import entrada.Teclado;

public class Main {

	public static void main(String args[]) throws IOException { 
		int codigo;
		ServerSocket servidor; 
		servidor = new ServerSocket(60000);    

		System.out.println("--------  Servidor -----------");
		System.out.println("Servidor iniciado...\n");
		System.out.println("1 para iniciar sesión");
		System.out.println("2 realizar transferencia");
		System.out.println("0 para salir");

		codigo = Teclado.leerEntero("Opción a elegir:");
		while (true) { 
			Socket cliente = new Socket(); 

			// Espera Cliente
			cliente = servidor.accept();//esperando cliente

		}

	}
}
