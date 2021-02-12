package data;

import java.io.Serializable;

/**
 *   Estende la classe Attribute e rappresenta un attributo continuo
 * 
 *   
 *
 */

public class ContinuousAttribute extends Attribute implements Serializable{
	
	
	/**
	 *   Invoca il costruttore della super-classe
	 * 
	 *   @param name valori per nome simbolico dell'attributo
	 *   @param index identificativo numerico dell'attributo
	 */
	public ContinuousAttribute (String name, int index) {
		super(name,index);
	}
	
	
	/**
	 *   Metodo che rappresenta lo stato dell'oggetto in un determinato momento
	 * 
	 * 
	 *   @return  String
	 */
	public String toString() {
		
		String frase=super.toString();
		
		return frase;
	}

}