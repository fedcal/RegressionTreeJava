package tree;

/**
 * 
 * Classe per gestire il caso di acquisizione di valore mancante o fuori range di un attributo di un nuovo esempio da classificare.
 *
 */
public class UnknownValueException extends Throwable{
	/**
	 * Costruttore di classe
	 * @param c String messaggio di errore
	 */
	public UnknownValueException(String c){
		System.out.println(c);
	}

}
