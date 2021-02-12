package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import database.*;



/**
 *   Modella l'insieme di esempi i training 
 *
 */

public class Data implements Serializable{
	
	private List<Example> data= new ArrayList<Example>(); /** ArrayList di tipo Example che contiene il traning set, che viene organizzato come una matrice di dimension (numero di esempi)x(numero di attributi)*/
	private int numberOfExamples;/** Intero che rappresenta la cardinalità del traning set*/
	private List<Attribute> explanatorySet=new LinkedList<Attribute>();/** Array di oggetti di tipo Attribute per rappresentare gli attributi indipendenti.*/
	private ContinuousAttribute classAttribute;/** Oggetto per modellare l'attributo di classe*/
	
	/**
	 *   Costruttore di classe. Avvalora la LinkedList explanatorySet con gli attributi indipendenti presenti nella tabella del database associando ad ognuno un TreeSet dei valori discreti che esso può assumere,
	 *   avvalora classAttribute istanziando un oggetto di tipo ContinuousAttribute, avvalora il numero di esempi e popola l'ArrayList  data con gli esempi di training.
	 *   
	 *   @param nameTable String rappresenta il nome della tabella da cui bisogna prendere i dati
	 *   @throws TrainingDataException Espellere le eccezioni relative ad errori dovuti alla struttura dello schema dei dati
	 */
	public Data(String nameTable) throws TrainingDataException {	
		 DbAccess dbacc = new DbAccess();
		 
		TableSchema schema=null;
		try{
			schema=new TableSchema(dbacc,nameTable);
		}catch(SQLException e) {
            throw new TrainingDataException(e.getMessage() + ": impossibile trovare la tabella " + nameTable);
            }
		
		TableData dati=new TableData(dbacc);
		try {
			this.data=dati.getTransazioni(nameTable);
		} catch (SQLException | EmptySetException e) {
			System.out.println(e.getMessage()+" Tabella vuota");
		}
		this.numberOfExamples=data.size();
		
		if(schema.getNumberOfAttributes()<2) 
			throw new  TrainingDataException("La tabella ha meno di due colonne");
		if(!schema.getColumn(schema.getNumberOfAttributes()-1).isNumber())
			throw new TrainingDataException("L'ultrima colonna non contiene dati numerici");
		for(int i=0; i<schema.getNumberOfAttributes()-1;i++) {
			if(schema.getColumn(i).isNumber()==true) {
				ContinuousAttribute temp=new ContinuousAttribute(schema.getColumn(i).getColumnName(),i);
				explanatorySet.add(temp);
			}else {
				TreeSet<Object> t=null;
				try {
					t = (TreeSet<Object>) dati.getDistinctColumnValues(nameTable, schema.getColumn(i));
				} catch (SQLException e) {
					System.out.println(e.getMessage()+"SQLException");
				} catch (EmptySetException e) {
					System.out.println(e.getMessage()+"Set vuoto");
				}
				TreeSet<String> l=new TreeSet<String>();
				Iterator<Object> p=t.iterator();
				while(p.hasNext()) {
					l.add((String)p.next());
				}
				DiscreteAttribute temp= new DiscreteAttribute(schema.getColumn(i).getColumnName(),i,l);
				explanatorySet.add(temp);
			}
		}
		int numberofColoumn= schema.getNumberOfAttributes();
		Column ultimateColumn= schema.getColumn(numberofColoumn-1);
		if(!ultimateColumn.isNumber()) {
			throw new TrainingDataException("Attenzione!!! L'ultima colonna del training set non un valore numerico\n");
		}
		ContinuousAttribute e= new ContinuousAttribute (ultimateColumn.getColumnName(),numberofColoumn-1);
		this.classAttribute=e;
		try {
			dbacc.closeConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}	
	
	/**
	 * 		Metodo volto a restituire il numero di esempi totali che sono stati caricati nell'ArrayList data.
	 * 
	 * 		@return Int cardinalità dell'insieme di esempi 
	 */
	public int getNumberOfExamples() {
		return this.numberOfExamples;
	}
	
	/**
	 * 		Metodo volto a restituire il numero degli attributi ella tabella.
	 * 
	 * 		@return Int numero totale attributi
	 */
	public int getNumberOfExplanatoryAttributes() {
		return this.explanatorySet.size();
	}
	
	/**
	 *    Metodo che restituisce il valore dell'attributo di classe per l'esempio exampleIndex
	 *    
	 *    @param exampleIndex Int indice dell'esempio 
	 *    @return Double valore dell'attributo di classe per l'esempio indicizzato in input
	 */
	public Double getClassValue(int exampleIndex) { 
		return (Double)(((Example)data.get(exampleIndex)).get(classAttribute.getIndex()));
	}
	
	
	/**
	 *    Metodo che restituisce il valore dell'attributo indicizzato da attributeIndex per l'esempio exampleIndex
	 * 
	 *    @param exampleIndex Int indice dell'esempio
	 *    @param attributeIndex Int indice dell'attributo
	 *    @return Object restiruisce il valore dell'attributo indipendente per l'esempio indicizzato in input
	 */
	public Object getExplanatoryValue (int exampleIndex, int attributeIndex ) {
		return (data.get(exampleIndex)).get(attributeIndex);
	}
		
	
	/**
	 *    Metodo che restituisce un attributo indicizzato da index in explanatorySet
	 * 
	 *    @param index Int che rappresenta la posizione dell'attributo indipendente in explanatorySet
	 *    @return Attribute che viene indicizzato da index
	 */
	public Attribute getExplanatoryAttribute(int index) {
		return (Attribute)explanatorySet.get(index);
		
	}
	
	/**
	 * 
	 *   Restituisce l'oggetto corrispondente all'attributo di classe.
	 * 
	 *   @return ContinuousAttribute  oggetto associato al membro classAttribute;
	 * 
	 */
	public ContinuousAttribute getClassAttribute() {
		return this.classAttribute;
	}
	
	/**
	 * 
	 *   Metodo che legge i valori di tutti gli attributi per ogni esempio di data e li concatena
	 *   in un oggetto String che restituisce come risultato finale in forma di sequenze di testi.
	 * 
	 * @return String
	 */
	public String toString(){
		String value = "";

        for(int i = 0; i < this.numberOfExamples; ++i) {
            for(int j = 0; j < this.explanatorySet.size(); ++j) {
                value = value + ((Example)this.data.get(i)).get(j) + ",";
            }

            value = value + ((Example)this.data.get(i)).get(this.explanatorySet.size()) + "\n";
        }

        return value;
		
	}

	/**
	 *   Ordina il sottoinsieme di esempi compresi nell'intervallo [inf, sup] in data rispetto allo specifico attributo attribute. Usa l'algoritmo quicksort per l'ordinamento
	 *   di una LinkedList di Example usado come relazione d'ordine totale "<=". La LinkedList è data dai valori assunti dall'attributo passato in input. 
	 *  
	 * 
	 *    @param attribute Rappresenta i vari valori dell'intervallo
	 *    @param beginExampleIndex Indice di inizio dell'intervallo da ordinare
	 *    @param endExampleIndex Indice di fine dell'intervallo da ordinare
	 */
	public void sort(Attribute attribute, int beginExampleIndex, int endExampleIndex){
	
			quicksort(attribute, beginExampleIndex, endExampleIndex);
	}
		
	/**
	 *    Metodo per eseguire lo scambio di due valori in fase di ordinamento
	 *    
	 *    @param i Indice del primo elemento da scambiare
	 *    @param j Indice del secondo elemento da scambiare
	 */
	private void swap(int i,int j){
	          Example temp1=data.get(i);
	          data.set(i, data.get(j));
	          data.set(j, temp1);
	          
	         
	}
	

	
	/**
	 *   Partiziona rispetto all'elemento x e restiutisce il punto di separazione come intero, nel caso in cui 
	 *   l'attributo passato nella funzione di ordinamento è un istanza della classe DiscreteAttribute
	 *   
	 *   @param attribute    Contiene i valori da partizionare e confrontare
	 *   
	 *   @param inf          Rappresenta l'indice della parte superiore da dover confrontare
	 *   
	 *   @param sup          Rappresenta l'indice della parte inferiore da dover confrontare
	 *   
	 *   @return             Restituisce l'indice della parte superiore che rappresenta il punto di separazione
	 */
	private  int partition(DiscreteAttribute attribute, int inf, int sup){
		int i,j;
	
		i=inf; 
		j=sup; 
		int	med=(inf+sup)/2;
		String x=(String)getExplanatoryValue(med, attribute.getIndex());
		swap(inf,med);
	
		while (true) 
		{
			
			while(i<=sup && ((String)getExplanatoryValue(i, attribute.getIndex())).compareTo(x)<=0){ 
				i++; 
				
			}
		
			while(((String)getExplanatoryValue(j, attribute.getIndex())).compareTo(x)>0) {
				j--;
			
			}
			
			if(i<j) { 
				swap(i,j);
			}
			else break;
		}
		swap(inf,j);
		return j;

	}
	/**
	 *  Partiziona rispetto all'elemento x e restiutisce il punto di separazione come intero, nel caso in cui 
	 *  l'attributo passato nella funzione di ordinamento è un istanza della classe ContinuousAttribute
	 * @param attribute Contiene i valori da partizionare e confrontare
	 * @param inf Rappresenta l'indice della parte superiore da dover confrontare
	 * @param sup Rappresenta l'indice della parte inferiore da dover confrontare
	 * @return Restituisce l'indice della parte superiore che rappresenta il punto di separazione
	 */
	private  int partition(ContinuousAttribute attribute, int inf, int sup){
		int i,j;
	
		i=inf; 
		j=sup; 
		int	med=(inf+sup)/2;
		Double x=(Double)getExplanatoryValue(med, attribute.getIndex());
		swap(inf,med);
	
		while (true) 
		{
			
			while(i<=sup && ((Double)getExplanatoryValue(i, attribute.getIndex())).compareTo(x)<=0){ 
				i++; 
				
			}
		
			while(((Double)getExplanatoryValue(j, attribute.getIndex())).compareTo(x)>0) {
				j--;
			
			}
			
			if(i<j) { 
				swap(i,j);
			}
			else break;
		}
		swap(inf,j);
		return j;

	}
	
	/** 
	 *    Algoritmo quicksort per l'ordinamento usando come relazione d'ordine totale minore o uguale
	 *    
	 *    @param attribute Rappresenta i valori da ordinare
	 *    
	 *    @param inf Parte inferiore da confrontare
	 *    
	 *    @param sup Parte superiore da confrontare
	 */
	public void quicksort(Attribute attribute, int inf, int sup){
		
		if(sup>=inf){
			
			int pos;
			if(attribute instanceof DiscreteAttribute)
				pos=partition((DiscreteAttribute)attribute, inf, sup);
			else
				pos=partition((ContinuousAttribute)attribute, inf, sup);
					
			if ((pos-inf) < (sup-pos+1)) {
				quicksort(attribute, inf, pos-1); 
				quicksort(attribute, pos+1,sup);
			}
			else
			{
				quicksort(attribute, pos+1, sup); 
				quicksort(attribute, inf, pos-1);
			}
			
			
		}
		
	}
	/**
	 * Metodo di supporto per scandire la struttura contenente i dati.
	 * @return Restituisce un valore di tipo Iterator
	 */
	public Iterator<Attribute> iterator() {
		return explanatorySet.iterator();
	}
}

