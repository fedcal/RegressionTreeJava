package database;

/**
 * Classe per contenere le informazioni relative alla colonna di un database
 *
 */
public class Column {
	private String name;/**Nome della colona*/
	private String type;/**Tipo della colonna*/
	
	/**
	 *  Costruttore di classe per avvalorare gli attributi di classe 
	 * @param name String nome della colonna
	 * @param type String tipo della colonna
	 */
	Column(String name,String type){
		this.name=name;
		this.type=type;
	}
	/**
	 * Metodo che restituisce il nome della colonna
	 * @return Nome della colonna
	 */
	public String getColumnName(){
		return this.name;
	}
	/**
	 * Metodo per verificare se una colonna è di tipo numerico o meno
	 * @return Vero se la colonna è di tipo numerico, Falso altrimenti
	 */
	public boolean isNumber(){
		return this.type.equals("number");
	}
	/**
	 * Metodo per ottenere lo stato dell'oggetto
	 * 
	 * @return Concatena lo stato dell'oggetto in un'unica stringa
	 */
	public String toString(){
		return this.name+":"+this.type;
	}

}