package map7Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import utility.Keyboard;


public class MainTest {

	/**
	 * 
	 * Client del progetto che si collega al server e inizia a interagire con l'utente per scegliere da dove acquisire i dati,
	 * inizia la predizione dell'albero scandendo ogni nodo dalla radice fino alla foglia, facendo scegliere all'utente il ramo 
	 * da scorrere. Finita la scansione dell'albero, fa scegliere all'utente se ripetere l'operazione di scansione ripartendo dalla radice 
	 * oppure terminare il processo.
	 *  @param args vettore per i valori passati da linea di comando
	 */
	public static void main(String args[]){
		/*
		 InetAddress addr;
		try {
			addr = InetAddress.getByName("127.0.0.1");
		} catch (UnknownHostException e) {
			System.out.println(e.toString());
			return;
		}
		Socket socket=null;
		ObjectOutputStream out=null;
		ObjectInputStream in=null;
		try {
			socket = new Socket(addr,  8080);
			System.out.println(socket);		
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			System.out.println("\n Socket creata\n");
		}  catch (IOException e) {
			System.out.println(e.toString());
			return;
		}		
		String answer="";
		int decision=0;
		do{
			System.out.println("Learn Regression Tree from data [1]");
			System.out.println("Load Regression Tree from archive [2]");
			decision=Keyboard.readInt();
		}while(!(decision==1) && !(decision ==2));
		
		String tableName="";
		System.out.println("Table name:");
		tableName=Keyboard.readString();
		try{
		
		if(decision==1)
		{
			System.out.println("Starting data acquisition phase!");			
			out.writeObject(0);
			out.writeObject(tableName);
			answer=in.readObject().toString();
			if(!answer.equals("OK")){
				System.out.println(answer);
				return;
			}	
			System.out.println("Starting learning phase!");
			out.writeObject(1);
		}
		else
		{
			out.writeObject(2);
			out.writeObject(tableName);
		}
		answer=in.readObject().toString();
		if(!answer.equals("OK")){
			System.out.println(answer);
			return;
		}
		// .........
		char risp='y';
		do{
			out.writeObject(3);
			System.out.println("Starting prediction phase!");
			answer=in.readObject().toString();
			while(answer.equals("QUERY")){
				// Formualting query, reading answer
				answer=in.readObject().toString();
				System.out.println(answer);
				int path=Keyboard.readInt();
				out.writeObject(path);
				answer=in.readObject().toString();
			}
			if(answer.equals("OK"))
			{ // Reading prediction
				answer=in.readObject().toString();
				System.out.println("Predicted class:"+answer);
			}
			else //Printing error message
				System.out.println(answer);		
			System.out.println("Would you repeat ? (y/n)");
			risp=Keyboard.readChar();	
		}while (Character.toUpperCase(risp)=='Y');
		}
		catch(IOException | ClassNotFoundException e){
			System.out.println(e.toString());
		}*/
		
		InetAddress addr;
		try {
			addr = InetAddress.getByName("127.0.0.1");
		} catch (UnknownHostException e) {
			System.out.println(e.toString());
			return;
		}
		Socket socket=null;
		ObjectOutputStream out=null;
		ObjectInputStream in=null;
		try {
			socket = new Socket(addr,  8080);
			System.out.println(socket);		
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			System.out.println("\n Socket creata\n");
		}  catch (IOException e) {
			System.out.println(e.toString());
			return;
		}		
		String answer="";
		int decision=0;
		do{
			System.out.println("Learn Regression Tree from data [1]");
			System.out.println("Load Regression Tree from archive [2]");
			decision=Keyboard.readInt();
		}while(!(decision==1) && !(decision ==2));
		
		String tableName="";
		System.out.println("Table name:");
		tableName=Keyboard.readString();
		try{
		
		if(decision==1)
		{
			System.out.println("Starting data acquisition phase!");			
			out.writeObject(0);
			try {
				out.writeObject(tableName);
			}catch(Exception e) {
				System.out.println("Impossibile trovare la tabella. Reinserirla!!!");
				System.out.println("Table name:");
				tableName=Keyboard.readString();
				out.writeObject(tableName);
			}
			answer=in.readObject().toString();
			if(!answer.equals("OK")){
				System.out.println(answer);
				return;
			}	
			System.out.println("Starting learning phase!");
			out.writeObject(1);
		}
		else
		{
			out.writeObject(2);
			out.writeObject(tableName);
		}
		answer=in.readObject().toString();
		if(!answer.equals("OK")){
			System.out.println(answer);
			return;
		}
		// .........
		char risp='y';
		do{
			out.writeObject(3);
			System.out.println("Starting prediction phase!");
			answer=in.readObject().toString();
			while(answer.equals("QUERY")){
				// Formualting query, reading answer
				answer=in.readObject().toString();
				System.out.println(answer);
				int path=Keyboard.readInt();
				if(!answer.contains(String.valueOf(path+":").toString())) {
					do {
						System.out.println("\n Risposta errata. Riprovare!\n");
						System.out.println(answer);
						path=Keyboard.readInt();
					}while(!answer.contains(String.valueOf(path+":").toString()));
					out.writeObject(path);
				}else {
					out.writeObject(path);
				}
				answer=in.readObject().toString();
			}
			if(answer.equals("OK"))
			{ // Reading prediction
				answer=in.readObject().toString();
				System.out.println("Predicted class:"+answer);
			}
			else //Printing error message
				System.out.println(answer);		
			System.out.println("Would you repeat ? (y/n)");
			risp=Keyboard.readChar();
			if(risp!='y'&& risp!='n'&& risp!='Y'&& risp!='N') {
				System.out.println("Risposta errata!!!");
				
				do{
					System.out.println("Would you repeat ? (y/n)");
					risp=Keyboard.readChar();
				}while(risp!='y'&& risp!='n'&& risp!='Y'&& risp!='N');
			}
				
		}while (Character.toUpperCase(risp)=='Y');
		}
		catch(IOException | ClassNotFoundException e){
			System.out.println(e.toString());
		}
	}

}
