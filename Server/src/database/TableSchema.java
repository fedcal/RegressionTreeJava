package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Classe che contiene tutte le informazioni sullo schema della tabella del databse a cui ci si collega per acquisire il dataset.
 *
 */
public class TableSchema implements Iterable<Column> {
	private List<Column> tableSchema=new ArrayList<Column>();/**ArrayList contenente le informazioni su ciascuna colonna del database: nome e tipo*/
	/**
	 * Costruttore di classe che dopo aver effettuato l'accesso al database, memorizza le informazioni relative al nome e al tipo di ciascuna colonna
	 * @param db DbAccess Oggetto nel quale viene istanziata la connessione al database
	 * @param tableName Nome della tabella da cui prendere i dati
	 * @throws SQLException Eccezione che si genera nel caso in cui non si riesce a recuperare le informazioni del database
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException{
		
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
	
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");
		
		
	
		 Connection con=db.getConnection();
		 DatabaseMetaData meta = con.getMetaData();
	     ResultSet res = meta.getColumns(null, null, tableName, null);
	     while (res.next()) {
	         if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
	        	 tableSchema.add(new Column(res.getString("COLUMN_NAME"),mapSQL_JAVATypes.get(res.getString("TYPE_NAME"))));
	      }
	      res.close();
	    }
	  
		/**
		 *Metodo che restituisce il numero di colonne del database
		 * @return Rappreseta il numero di colonne, e quindi di attributi discreti o continui, presenti nella tabella del database 
		 */
		public int getNumberOfAttributes(){
			return tableSchema.size();
		}
		/**
		 * Restituisce la colonna del database idicizzata dal parametro in input
		 * @param index indice della colonna che si vuole
		 * @return Column
		 */
		public Column getColumn(int index){
			return tableSchema.get(index);
		}


		
		public Iterator<Column> iterator() {
		
			return tableSchema.iterator();
		}
}
