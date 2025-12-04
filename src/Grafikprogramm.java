import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grafikprogramm extends JFrame {
    // Hier wird das Fenster erstellt
    public Zeichenfeld zeichenfeld; // zum später aufrufen
    public Grafikprogramm() {
        setTitle("Grafikprogramm");
        setSize(800, 600);
        setLocationRelativeTo(null); //Fenster zentrieren
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Menüleiste erstellen und platzieren
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //Dateimenü erzeugen
        JMenu menuDatei = new JMenu("Datei");
        menuBar.add(menuDatei);

        //Beenden-Funktion im Dateimenü erzeugen
        JMenuItem beenden = new JMenuItem("Beenden");
        menuDatei.add(beenden);

        //Schließen der Anwendung, wenn "beenden" betätigt wird
        beenden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Zeichenfeld in das Fenster hinzufügen
        zeichenfeld = new Zeichenfeld();
        add(zeichenfeld);

        setVisible(true);
    }
}

