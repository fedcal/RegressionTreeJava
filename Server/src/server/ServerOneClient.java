package server;

import data.Data;
import data.TrainingDataException;
import database.DatabaseConnectionException;
import database.EmptySetException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import tree.RegressionTree;

/**
 * Classe per far comunicare il server con il client attraverso gli opportuni stream, avviando un thread separato per ogni client.
 */
public class ServerOneClient extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * Costruttore di classe. Inizializza gli attributi socket, in e out. Avvia il thread
     * 
     * 
     * @param s Socket per collegarsi al client e poter comunicare con esso 
     * @throws IOException Questo metodo genera un'eccezione nel caso in cui non riesce a collegarsi con il client
     */
    public ServerOneClient(Socket s) throws IOException {
        this.socket = s;
        this.in = new ObjectInputStream(s.getInputStream());
        this.out = new ObjectOutputStream(s.getOutputStream());
        (new Thread(this::run)).start();
    }

    /**
     * 
     * Riscrive il metodo run della superclasse Thread al fine di gestire le richieste del client in modo da rispondere
     * alle richieste del client.
     */
    public void run() {
    	System.out.println("Connection accepted!");

        try {
            Integer decision = Integer.parseInt(this.in.readObject().toString());
            String tableName = this.in.readObject().toString();
            RegressionTree tree;
            Data trainingSet;
            if (decision.equals(0)) {
                
                trainingSet = new Data(tableName);
                this.out.writeObject("OK");
                Integer.parseInt(this.in.readObject().toString());
                tree = new RegressionTree(trainingSet);
                tree.salva(tableName + ".dmp");
            } else {
                tree = RegressionTree.carica(tableName + ".dmp");
            }

            this.out.writeObject("OK");

            while(true) {
                Integer.parseInt(this.in.readObject().toString());

                try {
                    tree.predictClass(this.out, this.in);
                } catch (tree.UnknownValueException e) {
					e.printStackTrace();
				}
            }
        } catch (SocketException var9) {
            System.out.println("Client aborted connection.");
        } catch (TrainingDataException | ClassNotFoundException | IOException var10) {
            IOException e = (IOException) var10;

            try {
                e.printStackTrace();
                this.out.writeObject(e.getMessage() + "\nAborting");
            } catch (IOException var7) {
                System.out.println(var7.getMessage());
            }
        }

    }
}