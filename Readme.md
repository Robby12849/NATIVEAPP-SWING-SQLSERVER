# INTERFACCIA CHE GESTISCE QUERY CRD SUI RECORD DI UN DATABASE

Questo è un lavoro svolto nella libreria **SWING** di **Java** che permette la realizzazione di interfacce grafiche. Qui ci sono due interfacce grafiche: una che gestisce la tabella scuola e l'altra che gestisce la tabella alunni.

## COME ESEGUIRE IL CODICE

### 1. Installare i Programmi Principali

#### 1.1 Installazione di Eclipse IDE
1. **Scarica Eclipse IDE**
   - Vai al [sito ufficiale di Eclipse](https://www.eclipse.org/downloads/packages/release/2024-03/r/eclipse-ide-enterprise-java-and-web-developers).
   - Seleziona la versione "Eclipse IDE for Enterprise Java and Web Developers".
   - Scarica il pacchetto appropriato per il tuo sistema operativo (Windows, macOS, o Linux).

2. **Installa Eclipse IDE**
   - **Windows**:
     - Estrai l'archivio zip scaricato.
     - Esegui `eclipse.exe` per avviare l'installazione.
   - **macOS**:
     - Apri il file `.dmg` scaricato.
     - Trascina l'icona di Eclipse nella cartella `Applications`.
   - **Linux**:
     - Estrai l'archivio tar.gz scaricato.
     - Apri una terminale e spostati nella directory estratta.
     - Esegui `./eclipse-inst` per avviare l'installazione.

#### 1.2 Installazione di SQL Server
1. **Scarica SQL Server**
   - Vai al [sito ufficiale di SQL Server](https://www.microsoft.com/en-us/sql-server/sql-server-downloads).
   - Seleziona la versione desiderata e scarica il pacchetto per il tuo sistema operativo.

2. **Installa SQL Server**
   - **Windows**:
     - Esegui il file di installazione scaricato.
     - Segui le istruzioni guidate per completare l'installazione, selezionando le opzioni desiderate.
   - **macOS**:
     - Utilizza Docker per installare SQL Server su macOS.
     - Scarica e installa Docker Desktop dal [sito ufficiale di Docker](https://www.docker.com/products/docker-desktop).
     - Esegui il seguente comando nel terminale:
       ```bash
       docker pull mcr.microsoft.com/mssql/server:2019-latest
       docker run -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=yourStrong(!)Password' -p 1433:1433 --name sqlserver -d mcr.microsoft.com/mssql/server:2019-latest
       ```
   - **Linux**:
     - A seconda della distribuzione, segui le istruzioni specifiche per l'installazione.
     - Ad esempio, su Ubuntu:
       ```bash
       wget -qO- https://packages.microsoft.com/keys/microsoft.asc | sudo apt-key add -
       sudo add-apt-repository "$(wget -qO- https://packages.microsoft.com/config/ubuntu/20.04/mssql-server-2019.list)"
       sudo apt-get update
       sudo apt-get install -y mssql-server
       sudo /opt/mssql/bin/mssql-conf setup
       sudo systemctl status mssql-server
       ```

3. **Configura SQL Server**
   - Una volta completata l'installazione, è necessario configurare SQL Server.
   - **Windows**:
     - Utilizza SQL Server Management Studio (SSMS) per connetterti al server e configurare i database.
   - **macOS e Linux**:
     - Utilizza strumenti come `sqlcmd` o applicazioni di terze parti come Azure Data Studio per gestire il server.

### 2. Importare il Progetto in Eclipse

1. **Avvia Eclipse IDE**
   - Apri Eclipse IDE dal tuo sistema operativo.

2. **Apri la Finestra di Importazione**
   - Vai al menu `File` e seleziona `Import...`.

3. **Seleziona il Tipo di Progetto da Importare**
   - Nella finestra di dialogo che si apre, espandi la categoria `General` e seleziona `Existing Projects into Workspace`.
   - Clicca su `Next`.

4. **Scegli la Cartella del Progetto**
   - Nella sezione `Select root directory`, clicca su `Browse...` e naviga fino alla cartella che contiene il progetto che desideri importare.
   - Seleziona la cartella e clicca su `OK`.

5. **Seleziona il Progetto**
   - Dopo aver scelto la cartella, vedrai una lista di progetti disponibili per l'importazione nella sezione `Projects`.
   - Assicurati che il progetto che desideri importare sia selezionato.

6. **Configura le Opzioni di Importazione**
   - (Opzionale) Se desideri copiare il progetto nella workspace di Eclipse, seleziona l'opzione `Copy projects into workspace`.
   - (Opzionale) Puoi scegliere di nascondere i progetti chiusi selezionando l'opzione `Hide closed projects`.

7. **Completa l'Importazione**
   - Clicca su `Finish` per completare l'importazione del progetto.

8. **Verifica l'Importazione**
   - Il progetto dovrebbe ora apparire nel `Package Explorer` o nel `Project Explorer` di Eclipse.
   - Verifica che tutte le risorse del progetto siano state importate correttamente e che non ci siano errori di configurazione.

### 3. Importare lo Script del Database in SQL Server

1. **Avvia SQL Server Management Studio (SSMS)**
   - Apri SSMS dal tuo sistema operativo.

2. **Connetti al Server SQL**
   - Nella finestra di connessione, inserisci i dettagli del server SQL (nome del server, tipo di autenticazione e credenziali).
   - Clicca su `Connect`.

3. **Apri una Nuova Query**
   - Nel `Object Explorer`, seleziona il database in cui vuoi importare lo script.
   - Fai clic destro su `New Query`.

4. **Esegui lo Script del Database**
   - Copia e incolla il contenuto dello script SQL nel nuovo editor di query.
   - Clicca su `Execute` per eseguire lo script.

5. **Verifica l'Importazione**
   - Verifica che le tabelle e i dati siano stati importati correttamente eseguendo query di selezione sul database.

### 4. Eseguire il Codice in Eclipse IDE

1. **Esegui il Progetto**
   - Nel `Package Explorer`, trova i file contenenti il metodo `main`.
   - Fai clic destro sul file e seleziona `Run As` > `Java Application`.

2. **Visualizza l'Uscita**
   - L'output del programma apparirà nella `Console` di Eclipse, situata nella parte inferiore dell'interfaccia.

Seguendo questi passaggi, sarai in grado di installare i programmi necessari, importare il progetto e il database, ed eseguire il codice in Eclipse IDE.
