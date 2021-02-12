package tree;

import java.io.Serializable;

import data.Data;

/**
 * Questa classe serve per modellare l'astrazione dell'entità nodo (fogliare o intermedia) dell'albero di decisione.
 *
 *
 */
public abstract class Node implements Serializable{
	
	protected static int idNodeCount=0; /** Contatore dei nodi generati nell'albero*/
	protected int idNode; /**identificativo numerico del nodo*/
	private int beginExampleIndex; /** indice nell'array del training set del primo esempio del nodo corrente.*/
	private int endExampleIndex; /**indice nell'array del training set dell'ultimo esempio del nodo corrente. beginExampleIndex e endExampleIndex individuano un sotto-insieme di trainig*/
	private double variance; /** Valore della varianza calcolata, rispetto all'attributo di classe, nel sotto-insieme di training del nodo*/
	
	/**
	 *	   Avvalora gli attributi primitivi di classe, inclusa la varianza che viene calcolata rispetto all'attributo da predire nel sotto-insieme di training coperto dal nodo
	 * @param trainingSet oggetto di classe Data contenente il training set completo
	 * @param beginExampleIndex indice di inizio dell'intervallo di training che contiene il nodo corrente
	 * @param endExampleIndex indice di fine dell'intervallo di training che contiene il nodo corrente
	 */
	Node(Data trainingSet, int beginExampleIndex, int endExampleIndex){
		
		this.idNode=idNodeCount++;
		this.beginExampleIndex=beginExampleIndex;
		this.endExampleIndex=endExampleIndex;
		int i,numeroelementi;
		Double somma1=0.0D, somma2=0.0D;
		
		numeroelementi=this.endExampleIndex-this.beginExampleIndex+1;
		i=beginExampleIndex;
		
		while(i<=this.endExampleIndex) {
			somma1=somma1+trainingSet.getClassValue(i);
			somma2=somma2+(trainingSet.getClassValue(i)*trainingSet.getClassValue(i));
			i++;			
		}
		
		variance=somma2-((somma1*somma1)/numeroelementi);		
	}
	/**
	 *     Restituisce il valore del membro idNode
	 * 
	 *     @return identificativo numerico del nodo*/
	public int getIdNode() {
		return(this.idNode);
	}
	
	
	/**
	 *  Restituisce il valore del membro beginExampleIndex
	 * @return indice del primo esempio del sotto-insieme rispetto al training set complessivo
	 */
	public int getBeginExampleIndex() {
		return(this.beginExampleIndex);
		
	}
	
	/**
	 * Restituisce il valore del membro endExampleIndex
	 * @return indice del ultimo esempio del sotto-insieme rispetto al training set complessivo
	 */
	public int getEndExampleIndex() {
		return(this.endExampleIndex);
		
	}
	
	
	/**
	 * 
	 * Valore dello SSE dell’attributo da predire rispetto al nodo corrente
	 * @return Restituisce il valore del membro variance
	 */
	public double getVariance() {
		return(this.variance);
	}

	
	/**
	 *   E' un metodo astratto la cui implementazione riguarda i nodi di tipo test (split node) dai quali si possono generare figli, 
	 *   uno per ogni split prodotto. Restituisce il numero di tali nodi figli.
	 * 
	 *    @return valore del numero di nodi sottostanti
	 */
	 abstract int getNumberOfChildren();
	
	
	/**
	 *  Concatena in un oggetto String i valori di beginExampleIndex,endExampleIndex, variance e restituisce la stringa finale.
	 *  
	 *  @return Una variabile di tipo <b> String</b> che contiene le infromazioni.
	 * 
	 */
	@Override
	public String toString() {
		String frase="",begin="",end="",var="";
		begin+=String.valueOf(this.beginExampleIndex);
		end+=String.valueOf(this.endExampleIndex);
		var+=String.valueOf(this.variance);
		frase+=" Nodo: [Examples: "+begin+"-"+end+"] "+"variance: "+var;		
		
		return frase;
	}

}