import javax.swing.*;
import java.awt.*;
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

        //Menü-Funktion "Neu", um neue Datei zu erstellen
        JMenuItem neuItem = new JMenuItem("Neu");
        menuDatei.add(neuItem);

        neuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //FUNKTION MUSS NOCH IMPLEMENTIERT WERDEN!!!!!

            }
        });

        //Menü-Funktion "Öffnen", um eine Datei zu öffnen
        JMenuItem oeffnenItem = new JMenuItem("Öffnen");
        menuDatei.add(oeffnenItem);

        oeffnenItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //FUNKTION MUSS NOCH IMPLEMENTIERT WERDEN!!!!!

            }
        });

        //Menü-Funktion "Speichern", um eine Datei zu speichern
        JMenuItem speichernItem = new JMenuItem("Speichern");
        menuDatei.add(speichernItem);

        speichernItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //FUNKTION MUSS NOCH IMPLEMENTIERT WERDEN!!!!!

            }
        });

        menuDatei.addSeparator(); //Trennstrich, sieht hübscher aus

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

        //Toolbar erzeugen mit einfachen Funktionen
        JToolBar toolbar = new JToolBar();

        JButton buttonNeu = new JButton("Neu");
        buttonNeu.setToolTipText("Neue Datei"); //Einen Tipp anzeigen lassen, wenn man mit der Maus über das Feld geht
        toolbar.add(buttonNeu);

        buttonNeu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //FUNKTION MUSS NOCH IMPLEMENTIERT WERDEN!!!!!

            }
        });

        JButton buttonOeffnen = new JButton("Öffnen");
        buttonOeffnen.setToolTipText("Datei öffnen");
        toolbar.add(buttonOeffnen);

        buttonOeffnen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //FUNKTION MUSS NOCH IMPLEMENTIERT WERDEN!!!!!

            }
        });

        JButton buttonSpeichern = new JButton("Speichern");
        buttonSpeichern.setToolTipText("Datei speichern");
        toolbar.add(buttonSpeichern);

        buttonSpeichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //FUNKTION MUSS NOCH IMPLEMENTIERT WERDEN!!!!!

            }
        });

        //Toolbar hinzufügen, Anordnung oben
        add(toolbar, BorderLayout.NORTH);

        //Zeichenfeld in das Fenster hinzufügen
        zeichenfeld = new Zeichenfeld();
        add(zeichenfeld);

        setVisible(true);
    }
}

