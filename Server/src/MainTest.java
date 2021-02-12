import java.io.IOException;

import server.MultiServer;
/**
 * 
 * Classe per stabilire la connessione al Server, e una volta effettuata, invia e riceve messagi dipendentemente alla scelta effettuata dall'utente
 *
 */
public class MainTest {
	/**
	 * Costruttore di clase senza argomenti
	 */
	public MainTest() {
    }
	/**
	 * Funzione main che istanzia il server e lo mette in scolto dei client
	 * @param args
	 * @throws IOException
	 */
    public static void main(String[] args) throws IOException {
        new MultiServer(8080);
    }

}
