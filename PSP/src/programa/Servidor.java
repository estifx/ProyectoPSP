package programa;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class Servidor {

public static void main(String[] args) {
		
		System.setProperty("javax.net.ssl.keyStore", "./Certificados SSL/AlmacenServidorSSL"); 
		System.setProperty("javax.net.ssl.keyStorePassword", "951357"); 
		
		int puerto= 60000;

		try {
			
			SSLServerSocketFactory serverSSL = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault(); 
			SSLServerSocket servidor = (SSLServerSocket) serverSSL.createServerSocket(puerto);
			SSLSocket cliente = null;
			
			while (true) { 
				System.out.println("--------  Servidor -----------");
				System.out.println("Servidor iniciado...\n");
				System.out.println("Esperando Cliente...\n");

				

				// Espera Cliente
				cliente = (SSLSocket)servidor.accept();//esperando cliente

				System.out.println("Cliente conectado\n");
				// Crea hilo y lo lanza servidor donde se atiende al cliente
				HiloServidor hilo = new HiloServidor(cliente); 
				hilo.start();

			}

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    

	}

}
