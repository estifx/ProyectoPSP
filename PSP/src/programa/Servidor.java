package programa;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) {
		ServerSocket servidor;
		try {
			servidor = new ServerSocket(60000);
			while (true) { 
				System.out.println("--------  Servidor -----------");
				System.out.println("Servidor iniciado...\n");
				System.out.println("Esperando Cliente...\n");

				Socket cliente = new Socket(); 

				// Espera Cliente
				cliente = servidor.accept();//esperando cliente

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
