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

public class ScuolaFrame extends JFrame implements ActionListener {
	
    private JTextField txtNomeScuola = new JTextField(10);
    private JTextField txtIndirizzoScuola = new JTextField(10);
    private JTextField txtCittaScuola = new JTextField(10);
    private JTextField txtProvScuola = new JTextField(10);
    private JTextField txtCapScuola = new JTextField(10);
    private JButton btnAggiungiScuola = new JButton("Aggiungi Scuola");
    private JButton btnVisualizzaScuola = new JButton("Visualizza Scuola");
    private JButton btnEliminaScuola = new JButton("Elimina Scuola");
    private JButton btnOpenAlunnoFrame = new JButton("Gestisci Alunni");
    private JButton btnStatus = new JButton("Connessione");
    
    private JTable tblvisual = new JTable();
    private Connection conn = null;
    
    

    public ScuolaFrame() {
        setTitle("Gestione Scuola");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panScuola = new JPanel();
        JPanel panButton = new JPanel();
        JPanel panTable = new JPanel();

        panScuola.add(new JLabel("Nome Scuola:"));
        panScuola.add(txtNomeScuola);
        panScuola.add(new JLabel("Indirizzo Scuola:"));
        panScuola.add(txtIndirizzoScuola);
        panScuola.add(new JLabel("CittÃ  Scuola:"));
        panScuola.add(txtCittaScuola);
        panScuola.add(new JLabel("Provincia Scuola:"));
        panScuola.add(txtProvScuola);
        panScuola.add(new JLabel("CAP Scuola:"));
        panScuola.add(txtCapScuola);

        btnAggiungiScuola.addActionListener(this);
        btnVisualizzaScuola.addActionListener(this);
        btnEliminaScuola.addActionListener(this);
        btnOpenAlunnoFrame.addActionListener(this);
        btnStatus.addActionListener(this);
        panButton.add(btnAggiungiScuola);
        panButton.add(btnVisualizzaScuola);
        panButton.add(btnEliminaScuola);
        panButton.add(btnOpenAlunnoFrame);
        panButton.add(btnStatus);
        btnStatus.setEnabled(false);
        panTable.add(new JScrollPane(tblvisual));
        add(panScuola, BorderLayout.NORTH);
        add(panButton, BorderLayout.CENTER);
        add(panTable, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        final String URL = "jdbc:sqlserver://LAPTOP-GN41V36E:1433;database=dbprova_1;encrypt=false;trustServerCertificate=true";
        final String USERNAME = "roberto";
        final String PASSWORD = "robb2";
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            btnStatus.setForeground(Color.GREEN);
            btnStatus.setText("Connessione al database riuscita");
        } catch (SQLException e) {
            btnStatus.setForeground(Color.RED);
            btnStatus.setText("Connessione al database non riuscita");
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAggiungiScuola) {
            if (!isString(txtNomeScuola.getText()) || !isString(txtIndirizzoScuola.getText()) || !isString(txtCittaScuola.getText()) || !isString(txtProvScuola.getText())) {
                JOptionPane.showMessageDialog(this, "Inserisci solo testo nei campi Nome Scuola, Indirizzo Scuola, CittÃ  Scuola e Provincia Scuola", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isNumeric(txtCapScuola.getText())) {
                JOptionPane.showMessageDialog(this, "Inserisci solo numeri nel campo CAP Scuola", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
             int maxid = 0;
            try {
                String getMaxIdQuery = "SELECT MAX(ID_scuola) FROM alunno";
                Statement getMaxIdStatement = conn.createStatement();
                ResultSet resultSet = getMaxIdStatement.executeQuery(getMaxIdQuery);
                if (resultSet.next()) {
                    maxid = resultSet.getInt(1) + 1;
                }
            }catch(SQLException ex) {
            	 ex.printStackTrace();
                 JOptionPane.showMessageDialog(this, "Errore durante l'inserimento della scuola nel database", "Errore", JOptionPane.ERROR_MESSAGE);
            }
            try {
                String query = "INSERT INTO scuola (ID_scuola, nome, indirizzo, cittÃ , provincia, CAP) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, maxid);
                preparedStatement.setString(2, txtNomeScuola.getText());
                preparedStatement.setString(3, txtIndirizzoScuola.getText());
                preparedStatement.setString(4, txtCittaScuola.getText());
                preparedStatement.setString(5, txtProvScuola.getText());
                preparedStatement.setString(6, txtCapScuola.getText());
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Scuola inserita con successo nel database", "Successo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Errore durante l'inserimento della scuola nel database", "Errore", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore durante l'inserimento della scuola nel database", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnVisualizzaScuola) {
            try {
                String query = "SELECT * FROM scuola";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("ID");
                model.addColumn("Nome");
                model.addColumn("Indirizzo");
                model.addColumn("CittÃ ");
                model.addColumn("Provincia");
                model.addColumn("CAP");
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID_scuola");
                    String nome = resultSet.getString("nome");
                    String indirizzo = resultSet.getString("indirizzo");
                    String città = resultSet.getString("città");
                    String provincia = resultSet.getString("provincia");
                    String cap = resultSet.getString("CAP");
                    model.addRow(new Object[]{id, nome, indirizzo, città, provincia, cap});
                }
                tblvisual.setModel(model);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore durante la visualizzazione della tabella scuole");
            }
        } else if (e.getSource() == btnEliminaScuola) {
            boolean inputValido = false;
            int idDaEliminare = 0;
            while (!inputValido) {
                String input = JOptionPane.showInputDialog(this, "Inserisci l'ID della scuola da eliminare (solo numeri):");
                if (input != null) {
                    if (input.matches("\\d+")) {
                        idDaEliminare = Integer.parseInt(input);
                        inputValido = true;
                    } else {
                        JOptionPane.showMessageDialog(this, "Inserisci solo numeri interi positivi", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    return;
                }
            }
            try {
                String deleteQuery = "DELETE FROM scuola WHERE ID_scuola = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, idDaEliminare);
                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Scuola eliminata con successo", "Eliminazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Nessuna scuola trovata con l'ID specificato", "Nessuna scuola trovata", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore durante l'eliminazione della scuola dal database", "Errore di eliminazione", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnOpenAlunnoFrame) {
            openAlunnoFrame();
            dispose();
        } else if (e.getSource() == btnStatus) {
            if (conn != null) {
                btnStatus.setForeground(Color.GREEN);
                btnStatus.setText("Connessione al database riuscita");
            } else {
                btnStatus.setForeground(Color.RED);
                btnStatus.setText("Connessione al database non riuscita");
            }
        }
    }

    private void openAlunnoFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AlunnoFrame();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ScuolaFrame();
            }
        });
    }

    public boolean isString(String input) {
        return input.matches("[a-zA-Z]+");
    }

    public boolean isNumeric(String input) {
        return input.matches("[0-9]+");
    }
}