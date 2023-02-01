import java.io.IOException;
import java.net.Socket;

public class Cliente {
	public static void main(String[] args) throws IOException { 

		String host = "localhost"; 
		int puerto = 6000;

		Socket cliente = new Socket(host, puerto); 
	}
}
