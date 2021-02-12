package database;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe che modella l'insieme di transazioni collezionate nella tabella del database. La singola transazione è modellata dalla classe Example
 *
 */
public class TableData {
	private DbAccess db;/**Connessione al database*/
	
	public enum QUERY_TYPE {
		MIN, MAX
	}
	/**
	 * Costruttore di classe che riceve in input la connesione al database per instanziarla
	 * @param db  variabile che contiene la connesione al database
	 */
	public TableData(DbAccess db) {
		this.db = db;
	}
	/**
	 * Ricava lo schema della tabella con nome table. Esegue una interrogazione per estrarre le tuple distinte da tale tabella. 
	 * Per ogni tupla del resultset, si crea un oggetto, istanza della classe Example, il cui riferimento va incluso nella lista da restituire. 
	 * In particolare, per la tupla corrente nel resultset, si estraggono i valori dei singoli campi, e li 
	 * si aggiungono all’oggetto istanza della classe Example che si sta costruendo.
	 * 
	 * @param table nome della tabella nel database.
	 * @return List<Example> Lista di transazioni memorizzate nella tabella.
	 * @throws SQLException Espelle eccezioni legate a un'errata esecuzione della query
	 * @throws EmptySetException Eccezione nel caso la tabella del database non contiene valori
	 */
	public List<Example> getTransazioni(String table) throws SQLException, EmptySetException {
		LinkedList<Example> transSet = new LinkedList<Example>();
		Statement statement;
		TableSchema tSchema = new TableSchema(db, table);

		String query = "select ";

		for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
			Column c = tSchema.getColumn(i);
			if (i > 0)
				query += ",";
			query += c.getColumnName();
		}
		if (tSchema.getNumberOfAttributes() == 0)
			throw new SQLException();
		else{
			query += " FROM " + table;
			statement = this.db.getConnection().createStatement();
			ResultSet rs = statement.executeQuery(query);
			boolean empty = true;
			while (rs.next()) {
				empty = false;
				Example currentTuple = new Example();
				for (int i = 0; i < tSchema.getNumberOfAttributes(); i++)
					if (tSchema.getColumn(i).isNumber())
						currentTuple.add(rs.getDouble(i + 1));
					else
						currentTuple.add(rs.getString(i + 1));
				transSet.add(currentTuple);
			}
			rs.close();
			statement.close();
			if (empty)
				throw new EmptySetException();
			else
				return transSet;
		}

		

	}
	/**
	 * Formula ed esegue una interrogazione SQL per estrarre i valori distinti ordinati di column e popolare un insieme da restituire (scegliere opportunamente in Set da utilizzare)
	 * 
	 * @param table Nome daella tabella del dataset
	 * @param column Nome della colonna presente nella tabella
	 * @return Set<Object> Insieme in cui vengono inseriti i dati della colonna
	 * @throws SQLException eccezione generata da un errore nella formulazione dell'interrogazione del database
	 * @throws EmptySetException espelle l'eccezione nel caso in cui la tabella del database è vuota
	 */
	public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException, EmptySetException {
		TreeSet<Object> x = new TreeSet<Object>();
		TableSchema tSchema = new TableSchema(this.db, table);
		if(tSchema.getNumberOfAttributes()==0)
			throw new SQLException();
		else{
			String query = "select " + column.getColumnName() + " from " + table;
			Statement statement = db.getConnection().createStatement();
			ResultSet rs = statement.executeQuery(query);
			boolean empty = true;
			while(rs.next()) {
				empty = false;
				x.add(rs.getString(column.getColumnName()));
			}
			rs.close();
			statement.close();
			if (empty) {
	            throw new EmptySetException();
	        } else {
	            return x;
	        }
		}
		
	}

}
