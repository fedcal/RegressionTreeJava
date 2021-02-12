package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * Classe per gestire l'accesso al database e stabilire la connessione
 *
 */

public class DbAccess {
	private static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";/** Driver esterno per la gestione del database*/
	private final String DBMS = "jdbc:mysql";
	private final String SERVER = "localhost";/**Contiene l'identificativo del server su cui risiede la base di dati*/
	private final String DATABASE = "MapDB";/**Contiene il nome della base di dati*/
	private final int PORT = 3306;/**La porta su cui il DBMS MySQL accetta le connessioni*/
	private final String USER_ID = "MapUser";/**Contiene il nome dell'utente per l'accesso alla base di dati*/
	private final String PASSWORD = "map";/**Contiene la password di autenticazione per l'utente identificato da USER_ID*/
	private Connection conn;/**Gestisce la connessione*/
	
	/**
	 * Costruttore di classe
	 */
	public DbAccess(){
		try {
			initConnection();
		} catch (DatabaseConnectionException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * Impartisce al class loader l'ordine di caricare il driver mysql e inizializza la connessione riferita.
	 * Il metodo solleva e propaga un'eccezione di tipo DatabaseConnectionException in caso di fallimento nella 
	 * connessione al database.
	 * 
	 * @throws DatabaseConnectionException espelle l'eccezione dovuta al mancato collegamento al database
	 */
	public void initConnection() throws DatabaseConnectionException{
		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		} catch(ClassNotFoundException e) {
			throw new DatabaseConnectionException("Attenzione!!! Driver non trovato: " + e.getMessage());
		} catch(InstantiationException e){
			throw new DatabaseConnectionException("Attenzione!!! Errore durante l'instanzazione: " + e.getMessage());
		} catch(IllegalAccessException e){
			throw new DatabaseConnectionException("Attenzione!!! Accesso al driver negato: " + e.getMessage());
		}
		String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
					+ "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
			
		System.out.println("Connessione al database: " + connectionString);
			
			
		try {			
			this.conn = DriverManager.getConnection(connectionString);
		} catch(SQLException e) {
			throw new DatabaseConnectionException("Attenzione!!! SQLException: " + e.getMessage());
		}
		
	}
	
	/**
	 * Metodo che restituisce conn
	 * @return Restituisce la connessione al server 
	 */
	public Connection getConnection() {
		return this.conn;
	}
	/**
	 * Metodo per chiudere la connessione al server
	 * @throws SQLException Potrebbe generare un'eccezione di questo tipo nel caso in cui la chiusura della connessione non va a buon fine
	 */
	public void closeConnection() throws SQLException {
		this.conn.close();
	}
}
