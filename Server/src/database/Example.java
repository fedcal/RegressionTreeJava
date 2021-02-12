package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe che rappresenta le tuple dei dati che formano il trainingset caricate dal database
 *
 */
public class Example implements Comparable<Example>, Iterable<Object> {
	private List<Object> example=new ArrayList<Object>();/**Lista di tuple*/
	
	public Example() {
    }
	/**
	 * Metodo che riceve in input la tupla del trainingset da inserire nella lista
	 * @param o tupla da inserire nella lista
	 */
	public void add(Object o){
		this.example.add(o);
	}
	/**
	 * Metodo per accedere a determinata tupla nella posizione indicata dall'indice in input
	 * @param i Indice della posizione della tupla
	 * @return Object La tupla indicizzata
	 */
	public Object get(int i){
		return this.example.get(i);
	}
	/**
	 * Cofronta l'oggetto corrente con loggetto di tipo Example passotole in input per verificare se sono uguali
	 * 
	 * @param ex Oggetto di tipo Example da dover confrontare con l'oggetto chiamante
	 * @return int Restituisce un intero, 0 se sono uguali, >=1 se la tupla corrente è maggiore rispetto a quella passata in input,>= -1 se la tupla passata in input è maggiore di quella corrente
	 */
	public int compareTo(Example ex) {
		int i=0;
		for(Object o:ex.example){
			if(!o.equals(this.example.get(i)))
				return ((Comparable)o).compareTo(this.example.get(i));
			i++;
		}
		return 0;
	}
	
	/**
	 * Restituisce la lista elencandone le tuple
	 * 
	 * @return String Concatena le tuple inserite nella lista in un'unica stringa
	 */
	public String toString(){
		String str="";
		for(Object o:example)
			str+=o.toString()+ " ";
		return str;
	}
	
	@Override
	public Iterator<Object> iterator() {
		
		return null;
	}


}
