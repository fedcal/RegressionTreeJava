package data;

import java.io.Serializable;

/**
 * Classe Attribute è una classe che serve a modellare un generico attributo discreto o continuo. 
 * 
 * 
 *
 */



abstract public class Attribute implements Serializable{
	
	private String name; /**Nome dell'attributo*/
	private int index; /**Indice della colonna in cui l'attributo risiede nello schema*/
	
	
	/**
	 * 
	 *   E' il costruttore di classe. Inizializza i valori dei membri name, index
	 *   
	 *   @param name stringa che rappresenta il nome simbolico dell'attributo
	 *   @param index intero che rappresenta l'identificativo numerico dell'attributo
	 */
	public Attribute(String name, int index){
		this.index=index;
		this.name=name;
	}
	
	
	/**
	 *   Restituisce il valore nel membro name
	 *   
	 *   @return  String Nome simbolico dell'attributo
	 */
	 public String getName() {
		return this.name;
	}
	
	/**
	 *   
	 *  
	 *  Restituisce il valore nel membro index;
	 *   @return  intero identificativo numerico dell'attributo
	 */
	 public int getIndex() {
		return this.index;
	}
	
	/**
	 *   Metodo che restituisce lo stato dell'oggetto Attribute
	 * 
	 * 
	 *   @return  String stringa che rappresenta lo stato dell'oggetto
	 */
	public String toString() {
		String frase="", i="";
		i= String.valueOf(index);
		frase+= "NOME: "+this.name+" INDICE: "+i;
		return (frase);
	}
}
