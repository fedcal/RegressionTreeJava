package tree;

import java.io.Serializable;

import data.Data;

/**
 * 
 *Classe per rappresentare un nodo fogliare dell'albero.
 *
 */
public class LeafNode extends Node implements Serializable{
	private Double predictedClassValue; /**Valore dell'attributo di classe espresso nella foglia corrente*/
	
	/**
	 * Istanzia un un oggetto invocando il costruttore della superclasse e avvallora l'attribute predictedClassValue (come media dei valori dell'attributo di classe che ricadono nella partizione) 
	 * @param trainingSet Trainingset complessivo
	 * @param beginExampleIndex Indice di inizo della partizione di trainingset
	 * @param endExampleIndex Indice di fine della partizione di trainingset
	 */
	public LeafNode(Data trainingSet, int beginExampleIndex, int endExampleIndex){
		super(trainingSet,beginExampleIndex,endExampleIndex);
		int numberExample=endExampleIndex-beginExampleIndex+1;
		Double somma=0.0D;
		for(int i=beginExampleIndex;i<=endExampleIndex;i++) {
			somma=somma+trainingSet.getClassValue(i);
		}
		this.predictedClassValue=somma/numberExample;
		
	}
	/**
	 * Restituisce il valore del membro predictedClassValue
	 * @return Restituisce il valore del membro predictedClassValue
	 */
	public Double getPredictedClassValue() {
		return this.predictedClassValue;
	}
	/**
	 *  Restituisce il numero di split originanti dal nodo foglia, ovvero 0
	 *  @return Restituisce un intero che rappresenta il numero di figli del nodo, in questo caso essendo un nodo fogliare, restituisce 0
	 */
	protected int getNumberOfChildren() {
		return 0;
	}
	
	/**
	 * Invoca il metodo della superclasse e assegna anche il valore di classe della foglia.
	 * 
	 * @return una Stringa che rappresenta lo stato di ognri oggetto della classe
	 */
	public String toString() {
		String pCV= Double.toString(this.predictedClassValue);
		String frase= "LEAF: class= "+pCV+" "+super.toString();
		return frase;
	}
	
	

}
