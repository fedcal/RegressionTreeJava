package tree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;
import java.util.TreeSet;
import data.*;

/**
 * Classe per modellare l'entità albero di decisione come insieme di sotto-alberi, contenente i dati del trainingSet
 *
 */

public class RegressionTree implements Serializable {
	
	private Node root;/**Radice dell'albero,*/
	private RegressionTree childTree[];/**Vettore di tipo Regression Tree che contiene i sotto alberi generati dalla radice oppure un nodo fogliare*/
	
	/**
	 * Istanzia un albero vuoto
	 */
	public RegressionTree() {
    }
	
	/**
	 * Istanzia un sotto-albero dell'intero albero e avvia l'induzione dell'albero dagli esempi di training in input
	 * 
	 * @param trainingSet training set complessivo.
	 */
	public RegressionTree(Data trainingSet){
		learnTree(trainingSet,0,trainingSet.getNumberOfExamples()-1,trainingSet.getNumberOfExamples()*10/100);
	}
	/**
	 * Genera un sotto-albero con il sotto-insieme di input istanziando un nodo fogliare o un nodo di split, in tal caso determina il miglior nodo rispetto al sotto-albero di input
	 * ed a tale nodo esso associa un sotto-albero avente radice il nodo medesimo e avente un numero di rami pari al numero dei figli determinati dallo split. Ricorsivamente ogni oggetto 
	 * DecisionTree in childTree[] sarà re-invocato il metodo learnTree() per l'apprendimento su un insieme ridotto del sotto insieme attuale. Nella condizione in cui il nodo di split non origina
	 * figli, il nodo diventa fogliare.
	 * @param trainingSet TrainingSet complessivo
	 * @param begin Indice di inizio del sotto-insieme di training
	 * @param end Indice di fine del sotto-insieme di training
	 * @param numberOfExamplesPerLeaf Numero massimo che una foglia deve contenere
	 */
	private void learnTree(Data trainingSet,int begin, int end,int numberOfExamplesPerLeaf){
		if( isLeaf(trainingSet, begin, end, numberOfExamplesPerLeaf)){
			root=new LeafNode(trainingSet,begin,end);
		}
		else 
		{
			root=determineBestSplitNode(trainingSet, begin, end);
			if(root.getNumberOfChildren()>1){
				childTree=new RegressionTree[root.getNumberOfChildren()];
				for(int i=0;i<root.getNumberOfChildren();i++){
					childTree[i]=new RegressionTree();
					childTree[i].learnTree(trainingSet, ((SplitNode)root).getSplitInfo(i).beginIndex, ((SplitNode)root).getSplitInfo(i).endIndex, numberOfExamplesPerLeaf);
				}
			}
			else {
				root=new LeafNode(trainingSet,begin,end);
			}

		}
	}
		/**
		 * Visualizza tutte le informazioni dell'albero, con annessa intestazione.
		 */
	private void printTree(){
		System.out.println("********* TREE **********\n");
		System.out.println(toString());
		System.out.println("*************************\n");
		
	}
	
	/**
	 * Concatena in una String tutte le informazioni di root-childTree[] correnti invocando i relativi metodo toString(): nel caso il root corrente è di split vengono concatenate
	 * anche le informazioni dei rami. Fa uso di per riconoscere se root è SplitNode o LeafNode.
	 * 
	 * @return Oggetto String con le informazioni dell'intero albero 
	 */
	public String toString(){
		String tree=root.toString()+"\n";
		
		if( root instanceof LeafNode){
		
		}
		else 
		{
			for(int i=0;i<childTree.length;i++)
				tree +=childTree[i];
 		}
		return tree;
	}
	
	/**
	 * Verifica se il sotto-insieme corrente può essere coperto da un nodo foglia controllando che il numero di esempi del training set compresi tra begin ed end sia minore uguale di 
	 * numberOfExamplesPerLeaf
	 * 	 
	 * @param trainingSet Trainingset precedentemente caricato in memoria
	 * @param begin Indice di inizio della porzione di trainingset
	 * @param end Indice di fine della porzione di trainingset
	 * @param numberOfExamplesPerLeaf Numero minimo che una foglia deve contenere.
	 * @return Esito sulle condizioni per i nodi fogliari.
	 */
	private boolean isLeaf(Data trainingSet,int begin, int end, int numberOfExamplesPerLeaf) {
		
		if ((end - begin+1) > numberOfExamplesPerLeaf) 
			return false;
		else
			return true;
	}
	
	/**
	 * Metodo per determinare lo split migliore nella porzione di trainingset passata
	 * @param trainingSet insieme di dati precedentemente carciati in memoria
	 * @param begin indice di inizio della porzione di dati
	 * @param end indice di fine della porzione di dati
	 * @return SplitNode 
	 */
	private SplitNode determineBestSplitNode(Data trainingSet, int begin,int end) {
		
		TreeSet<SplitNode>ts=new TreeSet<SplitNode>();
		Attribute a;
		SplitNode currentNode;
		for(int i=0;i<trainingSet.getNumberOfExplanatoryAttributes();i++) {
			
			a=trainingSet.getExplanatoryAttribute(i);
			
			if(a instanceof DiscreteAttribute) {
				DiscreteAttribute attribute=(DiscreteAttribute) trainingSet.getExplanatoryAttribute(i);
				currentNode= new DiscreteNode(trainingSet,begin,end,attribute );
			}else {
				ContinuousAttribute attribute=(ContinuousAttribute) trainingSet.getExplanatoryAttribute(i);
				currentNode= new ContinuousNode(trainingSet,begin,end,attribute );
			}
			ts.add(currentNode);
			
		}
		trainingSet.sort(((SplitNode)ts.first()).getAttribute(), begin, end);
		return (SplitNode)ts.first();
		
	}
	
	
	/**
	 * Scandisce ciascun ramo dell'albero completo dalla radice alla foglia concatenando le informazioni dei nodi di split fino al nodo foglia. In particolare per ogni sotto-albero 
	 * (oggetto DecisionTree) in childTree[] concatena le informazioni del nodo root: se è di split discende ricorsivamente l'albero per ottenere le informazioni del nodo sottostante 
	 * (necessario per ricostruire le condizioni in AND) di ogni ramo-regola, se è di foglia (leaf) termina l'attraversamento visualizzando la regola.
	 */
	public void printRules() {
		Node currentNode = this.root;
        String current = "";
        String finalString = "\n********* RULES **********\n";
        if (currentNode instanceof LeafNode) {
            System.out.println(((LeafNode)currentNode).toString());
        } else {
            current = current + ((SplitNode)currentNode).getAttribute().getName();

            for(int i = 0; i < this.childTree.length; ++i) {
                finalString = finalString + this.childTree[i].printRules(current + ((SplitNode)currentNode).getSplitInfo(i).getComparator() + ((SplitNode)currentNode).getSplitInfo(i).getSplitValue());
            }
        }

        finalString = finalString + "*************************\n";
        System.out.println(finalString);
		
	}
	
	/**
	 *  Supporta il metodo printRules(), concatena alle informazioni in current del precedente nodo quelle del nodo root del corrente sotto-albero:
	 *  se il nodo corrente è di split il metodo viene indicato ricorsivamente con current e le informazoni del nodo corrente, se è fogliare visualizza tutte 
	 *  le informazioni concatenate. 
	 *  
	 * 
	 * @param current Informazioni del nodo di split del sotto-albero al livello superiore
	 */
	private String printRules(String current) {
		Node currentNode = this.root;
        String finalString = "";
        if (currentNode instanceof LeafNode) {
            return current + "  ==> Class = " + ((LeafNode)currentNode).getPredictedClassValue() + "\n";
        } else {
            current = current + " AND " + ((SplitNode)currentNode).getAttribute().getName();

            for(int i = 0; i < this.childTree.length; ++i) {
                finalString = finalString + this.childTree[i].printRules(current + ((SplitNode)currentNode).getSplitInfo(i).getComparator() + ((SplitNode)currentNode).getSplitInfo(i).getSplitValue());
            }

            return finalString;
        }
        }
	/**
	 *  Visualizza le informazioni di ciascuno split dell'albero e per il corrispondente attributo acquisice il valore dell'esempio da predire da tastiera. 
	 *  Se il nodo corrente è leaf termina l'acquisizione e  visualizza la predizione della classe.
	 *  
	 * @param objectOutputStream Serializza gli oggetti dello stream del client	
	 * @param objectInputStream Desrializza gli oggetti dello stream edl client precedentemente serializzati 
	 * @throws IOException Espelle l'eccezzione di tipo IOException
	 * @throws UnknownValueException Espelle l'eccezzione di tipo UnkownValueException in caso l'utente inserisci un elemento non valido
	 * @throws ClassNotFoundException Espelle un eccezione nel caso in cui 
	 */
	public void predictClass(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException, UnknownValueException, ClassNotFoundException {
        if (this.root instanceof LeafNode) {
            objectOutputStream.writeObject("OK");
            objectOutputStream.writeObject(((LeafNode)this.root).getPredictedClassValue().toString());
        } else {
            objectOutputStream.writeObject("QUERY");
            objectOutputStream.writeObject(((SplitNode)this.root).formulateQuery());
            int risp = Integer.parseInt(objectInputStream.readObject().toString());
            if (risp < 0 || risp >= this.root.getNumberOfChildren()) {
                int e = this.root.getNumberOfChildren();
                throw new UnknownValueException("La risposta dovrebbe essere un valore intero compreso tra 0 " + (e - 1) + "!");
            }

            this.childTree[risp].predictClass(objectOutputStream, objectInputStream);
        }

    }
	/**
	 * Funzione per serializzare i dati in un file per poi riprendere l'esecuzione successivamente. Tutte le classi estendono Serializable per poter essere serializzate
	 * @param nomeFile Stringa del file sul quale l'utente vuole serializzare i dati
	 * @throws IOException Questo metodo può generare un'eccezione di input/output che viene espulsa
	 * @throws FileNotFoundException Oltre a un'eccezione di tipo IOException, questo metodo può espellere anche un'eccezione di tipo FileNotFoundException nel caso in cui l'operazione di serializzazionenon vada a buon fine
	 */
	public void salva (String nomeFile) throws IOException,FileNotFoundException {
		String file=nomeFile+".dat";
		FileOutputStream outFile=new FileOutputStream(file);
		ObjectOutputStream outStream= new ObjectOutputStream(outFile);
		outStream.writeObject(this);
		outStream.close();
	}
	/**
 * Funzione per deserializzare i dati da file e caricarli nella struttura
	 * @param nomeFile nome del file da desiarilizzare
	 * @return RegressionTree
	 * @throws FileNotFoundException eccenzione generata nel caso in cui non si trova il file
	 * @throws IOException eccezione generata da un errore i comunicazione tra gli streem
	 * @throws ClassNotFoundException eccezione generata dal mancato recupero di una classe
	 */
	public static RegressionTree carica(String nomeFile) throws FileNotFoundException,IOException,ClassNotFoundException{
		 FileInputStream inFile = new FileInputStream(nomeFile);
	     ObjectInputStream outStream = new ObjectInputStream(inFile);
	     RegressionTree x = (RegressionTree)outStream.readObject();
	     outStream.close();
		return x;
	}
		
	
}