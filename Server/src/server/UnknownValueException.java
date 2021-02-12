package server;
/**
 * 
 * Classe per gestire il caso di acquisizione di valore mancante o fuori range di un attributo di un nuovo esempio da classificare.
 *
 */
public class UnknownValueException extends Throwable{
	/**
	 * Costruttore di classe che visualizza il messaggio di errore
	 * @param c String contenente il messaggio
	 */
	public UnknownValueException(String c){
		System.out.println(c);
	}
}
