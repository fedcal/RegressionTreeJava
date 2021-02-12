package data;
/**
 * 
 * Classe per gestire le eccezioni generate da errori nel trainingSet
 *
 */
public class TrainingDataException extends Exception{
	TrainingDataException(){
		
	}
	/**
	 * Costruttore di classe che visualizza il messaggio di errore
	 * @param e stringa contenente il messaggio di errore
	 */
	TrainingDataException(String e){
		super(e);
		System.out.println(e);
	}
}