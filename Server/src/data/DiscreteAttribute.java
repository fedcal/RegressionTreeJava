package data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 *   DiscreteAttribute è una classe concreta che estende Attribute.
 *   Serve a rappresentare un attributo discreto.
  */
public class DiscreteAttribute extends Attribute implements Iterable<String>,Serializable {

	 private Set<String> values= new TreeSet<String>();/** Insieme per memorizzare i valori che quella colonna dello schema può assumere*/
	
	
	
	/**
	 * 
	 *    Costruttore di classe che avvalora gli attributi di classe. Invoca il costruttore della super-classe e avvalora il TreeSet values con i valori discreti in input
	 *    
	 *    
	 *    @param name String rappresenta il nome simbolico dell'attributo
	 *    @param index Int identificativo numerico dell'attributo
	 *    @param values Set di oggetti di tipo String rappresentanti i valori discreti
	 */
	 public DiscreteAttribute(String name, int index, Set<String> values) {
			super(name,index);
			this.values=values;
		}
	
	
	/**
	 *   Restituisce la cardinalità del TreeSet values
	 *   @return  numero di valori discreti dell'attributo
	 */
	public int getNumberOfDistinctValues() { //Restituisce la dimensione dell'array
		
		return values.size();
		
	}
	
	/**
	 *  Restituisce il valore dell'elemento in posizione i del TreeSet values
	 *   
	 *   @param indice di un solo valore discreto rispetto al TreeSet values
	 *   @return valore discreto indicizzato dall'indice in input
	 */
	public String getValue(int i) {
		
		String value="";
		Iterator<String> it=values.iterator();
		int cont=0;
		while(it.hasNext()) {
			if(cont==i) {
				value=(String)it.next();
				cont++;
			}else
				it.next();
		}
		return value;
		
	}
	
	
	/**
	 *   Restituise lo stato dell'oggetto
	 * 
	 * 
	 *   @return  String che esplicita contenente lo stasto degli attributi della classe DiscreteAttribute.
	 */
	public String toString() {
		String frase="";
		frase+=super.toString();
		frase+=" ";
		Iterator<String> i=values.iterator();
		while(i.hasNext()) {
			frase+=(String)i.next()+" ";
		}
		
		return (frase);
	}


	@Override
	public Iterator<String> iterator() {
		return values.iterator();
	}
}