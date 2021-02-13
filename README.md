# RegressionTreeJava
L'apprendimento dell'albero decisionale è un metodo comunemente utilizzato nel data mining. L'obiettivo è creare un modello che preveda il valore di una variabile target sulla 
base di diverse variabili di input. Gli algoritmi per la costruzione di alberi decisionali di solito funzionano dall'alto verso il basso, scegliendo una variabile in ogni 
passaggio che suddivide al meglio l'insieme di elementi. Diversi algoritmi utilizzano metriche diverse per misurare il "migliore". Questi generalmente misurano l'omogeneità della 
variabile obiettivo all'interno dei sottoinsiemi.
Il programma in questione prende i dati da un database per poi caricarli in memoria in strutture dati opportune, crea l'albero di regressione e lo percorre. Si basa su 
un'architettura client/server. Una volta avviato il server, questo svolge diverse attività, quali:
* Rimanere in ascolto fino a quando un client non si collega
* Instaura un collegamento con il client
* Riceve ed elabora le richieste del client
* Carica i dati dal database
* Crea l'albero di regressione
* Percorre l'albero dalla radice al nodo foglia in base ai messaggi del client

Il client, invece, interagisce con il server indicando la tabella del database da cui prendere i dati e guida la scansione dell'albero dalla radice al nodo foglia.

Nella cartella "Guida Utente" troverete il file da me creato per guidarvi nell'installazione del programma e alcuni casi di test.
Nella cartella "Jar e Script" troverete gli script e il jar da poter scaricare ed eseguire direttamente.
