package tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import data.Attribute;

import data.Data;



/**
 * 
 * Modella l'astrazione dell'entità nodo di split (continuo o discreto) estendendo la classe Node.
 *
 */
abstract class SplitNode extends Node implements Comparable<SplitNode>, Serializable {
	
	/**
	 * 
	 * Inner class che aggrega tutte le informazioni riguardanti un nodo di split
	 *
	 */
	 class  SplitInfo implements Serializable{/**Classe che aggrega tutte le informazioni riguardanti un nodo di split*/
		Object splitValue;/** Valore di tipo Object di un attributo indipendente che definisce uno split*/
		int beginIndex;/** Indice di inizio dello split*/
		int endIndex;/** Indice di fine dello split*/
		int numberChild;/** Numero di split (nodi figli) generati dal nodo corrente*/
		String comparator="=";/** Operatore matematico che definisce il test nel nodo corrente ("=" per valori discreti)*/
		/**
		 * Costruttore che avvalora gli attributi di classe per split a valori discreti 
		 * @param splitValue Object valore di tipo Object di un attributo indipendente che definisce uno split
		 * @param beginIndex Int indice di inizio del sotto insieme di training
		 * @param endIndex Int indice di fine del sotto insieme di training 
		 * @param numberChild Int numero di split (nodi figli) generati dal nodo corrente
		 */
		public SplitInfo(Object splitValue,int beginIndex,int endIndex,int numberChild){
			this.splitValue=splitValue;
			this.beginIndex=beginIndex;
			this.endIndex=endIndex;
			this.numberChild=numberChild;
		}
		/**
		 * Costruttore che avvalora gli attributi di classe per generici split per valori continui
		 * @param splitValue Object valore dello split
		 * @param beginIndex Int indice di inizio del sotto insieme di training
		 * @param endIndex Int indice di fine del sotto insieme di training
		 * @param numberChild Int numero di figli dello split
		 * @param comparator String indica il comparator
		 */
		public SplitInfo(Object splitValue,int beginIndex,int endIndex,int numberChild, String comparator){
			this.splitValue=splitValue;
			this.beginIndex=beginIndex;
			this.endIndex=endIndex;
			this.numberChild=numberChild;
			this.comparator=comparator;
		}
		/**
		 * Ricava l'idince di inizio dello split dall'attributo di classe
		 * @return Restituisce  l'attributo beginIndex
		 */
		public int getBeginIndex(){
			return this.beginIndex;			
		}
		/**
		 * Ricava l'idince di fine dello split dall'attributo di classe
		 * @return Restituisce  l'attributo endIndex
		 */
		public int getEndIndex(){
			return this.endIndex;
		}
		
		/**
		 * Ricava la varianza di split dall'attributo di classe
		 * @return Restituisce il valore della varianza dello split
		 */
		 public Object getSplitValue(){
			return this.splitValue;
		}
		
		 /**
		  *  Ricava il numero di figli del nodo di split dall'attributo di classe
		  * @return Restituisce un intero rappresentante il numero di figli del nodo di split
		  */
		 public int getNumberChild() {
			 return this.numberChild;
		 }
		 /**
		  * Metodo per rappresentare lo stato dell'oggetto di tipo SplitInfo sotto forma di stringa
		  * @return I valori delgi attributi dell'oggetto.
		  */
		 public String toString(){
			return "child " + numberChild +" split value"+comparator+splitValue + "[Examples:"+beginIndex+"-"+endIndex+"]";
		}
		 /**
		  * 
		  * @return Restituisce il valore dell'attributo comparator
		  */
		 public String getComparator(){
			return this.comparator;
		}
	}

	private Attribute attribute;	/**Oggetto che modella l'attributo indipendete sul quale lo split è generato*/

	protected List<SplitInfo>mapSplit= new ArrayList<SplitInfo>(); /**ArrayList per memorizzare gli split candidati in una struttura pari ai possibili valori di test*/
	
	private double splitVariance;/**Attributo che contiene il valore di varianza a seguito del partizionamento indotto dallo split corrente*/
	
	
	/**
	 * 
	 * Metodo abstract per generare le informazioni necessarie per ciascuno degli split candidati (in mapSplit)
	 * 
	 * @param trainingSet Training set complessivo
	 * @param beginExampelIndex Indice di inizio della partizione dei dati
	 * @param endExampleIndex Indice di fine della partizione dei dati
	 * @param attribute Attributo indipendente sul quale si definisce lo split
	 */
	public abstract void setSplitInfo(Data trainingSet,int beginExampelIndex, int endExampleIndex, Attribute attribute);
	
	/**
	 * 
	 * Metodo abstract per modellare la condizione di test (ad ogni valore di test c'è un ramo dallo split)
	 * 
	 * @param value Valore dell'attributo che si vuole testare rispetto a tutti gli split
	 * @return risultato Risultato del test
	 */
	public abstract int testCondition (Object value);
	
	/**
	 * Invoca il costruttore della superclasse, ordina i valori dell'attributo di input per gli esempi beginExampleIndex-endExampleIndex e sfrutta questo ordinamento per determinare i possibili 
	 * split e popolare l'ArrayList mapSplit, computa la varianza (splitVariance) per l'attributo usato nello split sulla base del partizionamento indotto dallo split 
	 * (lo stesso è la somma delle varianze calcolate su ciascuno SplitInfo colelzioanto in mapSplit)
	 * 
	 * 
	 * @param trainingSet Training set complessivo
	 * @param beginExampleIndex Inizio della sotto-partizione
	 * @param endExampleIndex Fine della sotto-partizione
	 * @param attribute Attributo indipendente sul quale si definisce lo split
	 */
	SplitNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute){
		super(trainingSet, beginExampleIndex,endExampleIndex);
		this.attribute=attribute;
		trainingSet.sort(attribute, beginExampleIndex, endExampleIndex);// order by attribute
		setSplitInfo(trainingSet, beginExampleIndex, endExampleIndex, attribute);
		splitVariance=0;
		for(int i = 0; i < this.mapSplit.size(); i++) {
            double localVariance = (new LeafNode(trainingSet, ((SplitNode.SplitInfo)this.mapSplit.get(i)).getBeginIndex(), ((SplitNode.SplitInfo)this.mapSplit.get(i)).getEndIndex())).getVariance();
            this.splitVariance += localVariance;
        }
			
			
	}
	
	/**
	 *  Metodo che restituisce l'attributo sul quale si è effettuato lo split
	 * @return Attributo di split di tio Attribute
	 */
	public Attribute getAttribute(){
		return this.attribute;
	}
	
	/**
	 * Metodo che restituisce la varianza di split
	 * 
	 * @return Restituisce un double che rappresenta la varianza
	 */
	public double getVariance(){
		return this.splitVariance;
	}
	
	@Override
	/**
	 * Restituisce il numero di rami origininati nel nodo corrente
	 * 
	 * @return Restituisce un valore di tipo Int
	 */
	public int getNumberOfChildren(){
		return  this.mapSplit.size();
	}
	
	
	/**
	 * Restituisce le informazioni per il ramo in mapSplit indicizzato da child
	 * @param child Indice del ramo di cui si vogliono avere informazioni
	 * @return SplitInfo Split di cui si vogliono avere informazioni indicizzato dall'indice child
	 */
	public SplitInfo getSplitInfo(int child){
		return this.mapSplit.get(child);
	}
	
	/**
	 * Concatena le informazioni di ciascun test (attributo, operatore e valore) in una stringa finale. 
	 * 
	 * @return Tutte le informazioni concatenate relative allo stato di ciascun test 
	 */
	public String formulateQuery(){
		String query = "";
		Iterator<SplitInfo> i=mapSplit.iterator();
		int cont=0;
		while(i.hasNext()) {
			SplitInfo temp=(SplitInfo)i.next();
			query+=cont+":"+attribute.getName()+temp.getComparator()+temp.getSplitValue()+"\n";
			cont++;
		}
		return query;
	}
	
	/**
	 * Concatena le informazioni di ciascun test in una stringa finale.
	 * @return String
	 */
	public String toString(){
		String v= "SPLIT : attribute=" +attribute.getName() +" "+ super.toString()+  " Split Variance: " + getVariance()+ "\n" ;
		Iterator<SplitInfo> i= mapSplit.iterator();
		while(i.hasNext()) {
			v+= "\t"+((SplitInfo)i.next()).toString()+"\n";
		}
		
		return v;
	}
	/**
	 * Metodo per confrontare due oggetti di tipo SplitNode
	 * 
	 * @param o oggetto di tipo SplitNode da confrontare con l'oggetto corrente
	 * @return Int 0 se sono uguali, -1 gain minore, 1 gain maggiore
	 */
	public int compareTo(SplitNode o) {
		if(this.splitVariance>o.getVariance())
			return 1;
		else if(this.splitVariance<o.getVariance())
			return -1;
		else
			return 0;
		
	}
	/**
	 * Metodo per iterare sugli attributi di classe
	 * @return Iterator
	 */
	public Iterator<SplitInfo> iterator() {
		return mapSplit.iterator();
	}
	
}