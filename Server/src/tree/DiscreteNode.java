package tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import data.*;

/**
 * 
 * Classe per modellare l'entità nodo di split relativo ad un attributo indipendente discreto.
 *
 */
public class DiscreteNode extends SplitNode implements Serializable{
	/**
	 * Istanzia un oggetto invocando il costruttore della superclasse con il parametro attribute
	 * 
	 * @param trainingSet Trainingset coplessivo
	 * @param beginExampleIndex Indice di inizio della partizione di trainingset
	 * @param endExampleIndex Indice di fine della partizione di traininfset
	 * @param attribute Attributo indipendente sul quale definire lo split
	 */
	public DiscreteNode(Data trainingSet, int beginExampleIndex, int endExampleIndex,DiscreteAttribute attribute) {
		super(trainingSet,beginExampleIndex,endExampleIndex,attribute);

	}
	
	@Override
	/**
	 * (Implementazione da class abstract) Istanzia oggetti di SplitInfo (definita come inner class in Splitnode) con ciascuno dei valori discreti dell'attributo relativamete al sotto-insieme
	 * di training set corrente (ossia la porzione di training set compresa tra beginExampleIndex e endExampleIndex), popola l'ArrayList c mapSplit con tali oggetti
	 * 
	 * @param trainingSet Trainingset complessivo
	 * @param beginExampleIndex Indice di inizio della sottopartizione del trainingSet
	 * @param endExampleIndex Indice di fine della sottopartizione del trainingSet
	 * @param attribute Attributo sul quale si definisce lo split
	 */
	public void setSplitInfo(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute) {
		int cont=1;
		for(int i=beginExampleIndex;i<endExampleIndex;i++) {
			
			String v1=(String)trainingSet.getExplanatoryValue(i, attribute.getIndex());
			String v2=(String)trainingSet.getExplanatoryValue(i+1,attribute.getIndex());
			if(!v1.equals(v2))
				cont++;	
		}
		
		int m=0;
		int inizio=beginExampleIndex;
		
		SplitInfo temp=null;
		for(int i=beginExampleIndex;i<endExampleIndex;i++) {
			String v1=(String)trainingSet.getExplanatoryValue(i, attribute.getIndex());
			String v2=(String)trainingSet.getExplanatoryValue(i+1,attribute.getIndex());
			if(!v1.equals(v2)){
				temp=new SplitInfo(trainingSet.getExplanatoryValue(i, attribute.getIndex()),inizio,i,m);
				mapSplit.add(temp);
				inizio=i+1;
				m++;
			}
			temp=new SplitInfo(trainingSet.getExplanatoryValue(i, attribute.getIndex()),inizio,endExampleIndex,m);
				
		}
		mapSplit.add(temp);	
	}

		
		


	@Override
	/**
	 * (Implementazione da class abstract) Effettua il confronto del valore in input rispetto al valore contenuto nell'attributo splitValue di ciascun egli oggetti SplitInfo
	 * collezionati in mapSplit e restituisce l'identificativo dello split (indice della posizione nell'erray mapSplit) con cui il test è positivo.
	 * 
	 * @param value Valore discreto dell'attributo che si vuole testare rispetto a tutti gli split
	 * @return Numero del ramo di split
	 */
	public int testCondition(Object value) {
		
		int ramo=0;
		for(int i=0; i<getNumberOfChildren();i++) {
			
			if(value.equals(this.getSplitInfo(i).getSplitValue()))
				ramo= i;
			
		}
		return ramo;
		
	}
	/**
	 * Invoca il metodo della super classe specializzandolo per i discreti
	 * @return La stringa contenente tutte le informazioni relative al nodo
	 */
	public String toString() {
		String frase="DISCRETE "+super.toString();
		return frase;
	}
	
	

}