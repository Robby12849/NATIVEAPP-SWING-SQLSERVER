package debari.roberto.database;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class AlunnoFrame extends JFrame implements ActionListener {
    Connection conn = null;

    private JPanel pan = new JPanel();
    private JPanel panDati = new JPanel();

    private JLabel lblnome = new JLabel("Nome", JLabel.RIGHT);
    private JLabel lblcognome = new JLabel("Cognome", JLabel.RIGHT);
    private JLabel lblclasse = new JLabel("Classe", JLabel.RIGHT);
    private JLabel lbletà = new JLabel("Età ", JLabel.RIGHT);
    private JLabel lbldatan = new JLabel("Data di Nascita", JLabel.RIGHT);

    private JTextField txtnome = new JTextField(10);
    private JTextField txtcognome = new JTextField(10);
    private JTextField txtclasse = new JTextField(10);
    private JTextField txtetà = new JTextField(10);
    private JTextField txtdatan = new JTextField(10);

    private JPanel panPulsanti = new JPanel();
    private JButton btndelete = new JButton("Elimina");
    private JButton btnadd = new JButton("Inserisci");
    private JButton btnvisual = new JButton("Visualizza");
    private JButton btnStatus = new JButton("Connessione");
    private JButton btnOpenScuolaFrame = new JButton("Gestisci Scuole");

    private JTable tblvisual = new JTable();

   

    public AlunnoFrame() {
    	 setTitle("Gestione Alunni");
        BorderLayout layout1 = new BorderLayout();
        this.setLayout(layout1);

        btndelete.setActionCommand("Elimina");
        btnadd.setActionCommand("Inserisci");
        btnvisual.setActionCommand("Visualizza");
        btnOpenScuolaFrame.setActionCommand("Gestisci Scuole");
        btndelete.addActionListener(this);
        btnadd.addActionListener(this);
        btnvisual.addActionListener(this);
        btnOpenScuolaFrame.addActionListener(this);

        panDati.add(lblnome);
        panDati.add(txtnome);
        panDati.add(lblcognome);
        panDati.add(txtcognome);
        panDati.add(lblclasse);
        panDati.add(txtclasse);
        panDati.add(lbletà);
        panDati.add(txtetà);
        panDati.add(lbldatan);
        panDati.add(txtdatan);

        panPulsanti.add(btndelete);
        panPulsanti.add(btnadd);
        panPulsanti.add(btnvisual);
        panPulsanti.add(btnOpenScuolaFrame);
        panPulsanti.add(btnStatus);

        JScrollPane scrollPane = new JScrollPane(tblvisual);
        pan.add(scrollPane);
        this.add(panDati, layout1.NORTH);
        this.add(panPulsanti, layout1.CENTER);
        this.add(pan, layout1.SOUTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        final String URL = "jdbc:sqlserver://LAPTOP-GN41V36E:1433;database=dbprova_1;encrypt=false;trustServerCertificate=true";
        final String USERNAME = "roberto";
        final String PASSWORD = "robb2";
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (conn != null) {
                btnStatus.setForeground(Color.GREEN);
                btnStatus.setText("Connessione al database riuscita");
            }
        } catch (SQLException e1) {
            btnStatus.setForeground(Color.RED);
            btnStatus.setText("Connessione al database non riuscita");
            e1.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if ("Elimina".equals(e.getActionCommand())) {
            String input = JOptionPane.showInputDialog(this, "Inserisci l'ID da eliminare:");
            if (input != null) {
                try {
                    int idDaEliminare = Integer.parseInt(input);
                    String deleteQuery = "DELETE FROM alunno WHERE ID = ?";
                    try (PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery)) {
                        preparedStatement.setInt(1, idDaEliminare);
                        int righeEliminate = preparedStatement.executeUpdate();
                        if (righeEliminate > 0) {
                            JOptionPane.showMessageDialog(null, "Riga eliminata con successo", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Non ci sono righe corrispondenti all'ID", "Messaggio", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else if ("Inserisci".equals(e.getActionCommand())) {
            try {
                String getMaxIdQuery = "SELECT MAX(ID) FROM alunno";
                Statement getMaxIdStatement = conn.createStatement();
                ResultSet resultSet = getMaxIdStatement.executeQuery(getMaxIdQuery);
                int id = 0;
                if (resultSet.next()) {
                    id = resultSet.getInt(1) + 1;
                }
                String nome = txtnome.getText();
                String cognome = txtcognome.getText();
                String classe = txtclasse.getText();
                int età= Integer.parseInt(txtetà.getText());
                String datan = txtdatan.getText();
                String query = "INSERT INTO alunno (ID, nome, cognome, classe, età, datan) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, nome);
                    preparedStatement.setString(3, cognome);
                    preparedStatement.setString(4, classe);
                    preparedStatement.setInt(5, età);
                    preparedStatement.setString(6, datan);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Inserimento nel database riuscito!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e1) {
                System.out.println("Errore nell'inserimento nel database: " + e1.getMessage());
                JOptionPane.showMessageDialog(null, "Errore nell'inserimento nel database: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e2) {
                JOptionPane.showMessageDialog(null, "Inserisci un valore numerico valido per l'etÃ ", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else if ("Visualizza".equals(e.getActionCommand())) {
            String query = "SELECT * FROM alunno";
            ResultSet resultSet = null;
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                resultSet = statement.executeQuery();
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("ID");
                model.addColumn("Nome");
                model.addColumn("Cognome");
                model.addColumn("Classe");
                model.addColumn("Età ");
                model.addColumn("DataNascita");
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String nome = resultSet.getString("nome");
                    String cognome = resultSet.getString("cognome");
                    String classe = resultSet.getString("classe");
                    int età= resultSet.getInt("età");
                    String dataNascita = resultSet.getString("datan");
                    model.addRow(new Object[]{id, nome, cognome, classe, età, dataNascita});
                }
                tblvisual.setModel(model);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } else if ("Gestisci Scuole".equals(e.getActionCommand())) {
            openScuolaFrame();
            dispose();
        }
    }

    private void openScuolaFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ScuolaFrame();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AlunnoFrame();
            }
        });
    }
}