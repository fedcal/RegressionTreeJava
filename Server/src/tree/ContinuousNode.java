package tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import data.Attribute;
import data.ContinuousAttribute;
import data.Data;

/**
 * 
 * Classe che rappresenta un elemeto continuo all'interno dell'albero
 *
 */

public class ContinuousNode extends SplitNode implements Serializable{

	/**
	 * Costruttore di classe per istanziare l'oggetto di tipo continuo
	 * 
	 * @param trainingSet di tipo Data rappresenta l'insieme di dati che devono essere elaborati
	 * @param beginExampleIndex Indice di inizio della partizione di dati da elaborare
	 * @param endExampleIndex Indice di fine della partizione di dati da elaborare
	 * @param attribute Attributo su cui effettuare l'elaborazione
	 */
	public ContinuousNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, ContinuousAttribute attribute) {
		super(trainingSet,beginExampleIndex,endExampleIndex,attribute);
	}
	
	/**
	 * Istanzia oggetti di SplitInfo (definita come inner class in Splitnode) con ciascuno dei valoi discreti dell'attributo relativamete al sotto-insieme
	 * di training set corrente, ossia la porzione di trainingSet compresa tra l'indice di inizio e di fine e popola la struttura che lo dovrà contenere
	 * 
	 * @param trainingSet di tipo Data rappresenta l'insieme di dati che devono essere elaborati
	 * @param beginExampleIndex Indice di inizio della partizione di dati da elaborare
	 * @param endExampleIndex Indice di fine della partizione di dati da elaborare
	 * @param attribute Attributo su cui effettuare l'elaborazione
	 */
	public void setSplitInfo(Data trainingSet,int beginExampleIndex, int endExampleIndex, Attribute attribute){
		Double currentSplitValue= (Double)trainingSet.getExplanatoryValue(beginExampleIndex,attribute.getIndex());
		double bestInfoVariance=0.0;
		List <SplitInfo> bestMapSplit=null;
		for(int i=beginExampleIndex+1;i<=endExampleIndex;i++){
			Double value=(Double)trainingSet.getExplanatoryValue(i,attribute.getIndex());
			if(value.doubleValue()!=currentSplitValue.doubleValue()){
				double localVariance=new LeafNode(trainingSet, beginExampleIndex,i-1).getVariance();
				double candidateSplitVariance=localVariance;
				localVariance=new LeafNode(trainingSet, i,endExampleIndex).getVariance();
				candidateSplitVariance+=localVariance;
				if(bestMapSplit==null){
					bestMapSplit=new ArrayList<SplitInfo>();
					bestMapSplit.add(new SplitInfo(currentSplitValue, beginExampleIndex, i-1,0,"<="));
					bestMapSplit.add(new SplitInfo(currentSplitValue, i, endExampleIndex,1,">"));
					bestInfoVariance=candidateSplitVariance;
				}
				else{		
											
					if(candidateSplitVariance<bestInfoVariance){
						bestInfoVariance=candidateSplitVariance;
						bestMapSplit.set(0, new SplitInfo(currentSplitValue, beginExampleIndex, i-1,0,"<="));
						bestMapSplit.set(1, new SplitInfo(currentSplitValue, i, endExampleIndex,1,">"));
					}
				}
				currentSplitValue=value;
			}
		}
		if (bestMapSplit != null) {
            this.mapSplit = bestMapSplit;
            if (this.mapSplit.get(1).beginIndex == this.mapSplit.get(1).getEndIndex()) {
                this.mapSplit.remove(1);
            }
        }
	}
	
	/**
	 *  Funzione per restituire il numero di ramo dello split
	 * 
	 * @param value oggetto di cui si vuole conoscere il ramo
	 * @return il ramo dell'oggetto, intero
	 * 
	 */
	@Override
	public int testCondition(Object value) {
		int ramo=-1;
		for(int i=0; i<getNumberOfChildren();i++) {
					
			if(value.equals(this.getSplitInfo(i).getSplitValue()))
				ramo= i;
			
		}
		return ramo;
	}
	 
	/**
	 * Metodo per visualizzare le informazioni dell'oggetto
	 * 
	 *
	 * @return Stringa
	 */
	public String toString() {
		String frase="CONTINUOUS "+super.toString();
		return frase;
	}
}