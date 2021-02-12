package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * Instanzia il server sulla porta e lo mette in ascolto
 */
public class MultiServer {
	private int PORT=8080;/**Porta su cui si connette il server*/
	/**
	 *Costruttore di classe. Inizializza la port ed invoca run()
	 * @param port Int numero di porta sulla quale istanziare la connessione
	 * @throws IOException
	 */
	public MultiServer(int port) throws IOException {
		this.PORT=port;
		run();
	}
	/**
	 * Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di richiesta di connessioni 
	 * da parte del client. Ad ogni nuova richiesta connessione si istanzia ServerOneClient
	 * @throws IOException
	 */
	public void run() throws IOException {
			ServerSocket serverSocket = new ServerSocket();
	        InetSocketAddress serverAddr = new InetSocketAddress("localhost", this.PORT);
	        serverSocket.bind(serverAddr);
	        System.out.println(serverSocket.toString());

	        while(true) {
	            Socket s = serverSocket.accept();
	            new ServerOneClient(s);
	        }
	}

}
 