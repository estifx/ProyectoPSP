import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import entrada.Teclado;

public class HiloServidor extends Thread {
	
	BufferedReader fentrada; 
	PrintWriter fsalida; 
	Socket cliente = null; 
	
	public HiloServidor(Socket cliente) throws IOException {
		this.cliente = cliente; 
		fsalida = new PrintWriter(cliente.getOutputStream(), true); 
		fentrada = new BufferedReader(new InputStreamReader(cliente.getInputStream())); 
	}
	
	
	
}