import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grafikprogramm extends JFrame {
    // Hier wird das Fenster erstellt
    public Zeichenfeld zeichenfeld;// zum später aufrufen

    final String actionNeu = "Neu";
    final String actionOeffnen = "Öffnen";
    final String actionSpeichern = "Speichern";
    final String actionBeenden = "Beenden";
    final String actionLinie = "Linie";
    final String actionRechteck = "Rechteck";
    final String actionEllipse = "Ellipse";


    public Grafikprogramm() {
        setTitle("Grafikprogramm");
        setSize(800, 600);
        setLocationRelativeTo(null); //Fenster zentrieren
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener dateiListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();

                switch (action) {
                    case actionNeu:
                        int sicher = JOptionPane.showConfirmDialog(Grafikprogramm.this,
                                "Sind Sie sicher, dass Sie eine neue Datei anlegen wollen?\nNicht gespeicherte Änderungen gehen verloren!");

                        //Betätigt man "Ja" wird sicher auf 0 gesetzt
                        if (sicher == 0) zeichenfeld.leeren();
                        break;

                    case actionOeffnen:

                    case actionSpeichern:

                    case actionBeenden:
                        System.exit(0);
                        break;
                }
            }
        };

        //Menüleiste erstellen und platzieren
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //Dateimenü erzeugen
        JMenu menuDatei = new JMenu("Datei");
        menuBar.add(menuDatei);

        //Menü-Funktion "Neu", um neue Datei zu erstellen
        JMenuItem neuItem = new JMenuItem("Neu");
        menuDatei.add(neuItem);
        neuItem.setActionCommand(actionNeu);
        neuItem.addActionListener(dateiListener);

        //Menü-Funktion "Öffnen", um eine Datei zu öffnen
        JMenuItem oeffnenItem = new JMenuItem("Öffnen");
        menuDatei.add(oeffnenItem);
        oeffnenItem.setActionCommand(actionOeffnen);
        oeffnenItem.addActionListener(dateiListener);

        //Menü-Funktion "Speichern", um eine Datei zu speichern
        JMenuItem speichernItem = new JMenuItem("Speichern");
        menuDatei.add(speichernItem);
        speichernItem.setActionCommand(actionSpeichern);
        speichernItem.addActionListener(dateiListener);

        menuDatei.addSeparator(); //Trennstrich, sieht hübscher aus

        //Beenden-Funktion im Dateimenü erzeugen
        JMenuItem beenden = new JMenuItem("Beenden");
        menuDatei.add(beenden);
        beenden.setActionCommand(actionBeenden);
        beenden.addActionListener(dateiListener);

        //Toolbar erzeugen mit einfachen Funktionen
        JToolBar toolbar = new JToolBar();

        JButton buttonNeu = new JButton("Neu");
        buttonNeu.setToolTipText("Neue Datei"); //Einen Tipp anzeigen lassen, wenn man mit der Maus über das Feld geht
        toolbar.add(buttonNeu);
        buttonNeu.setActionCommand(actionNeu);
        buttonNeu.addActionListener(dateiListener);

        JButton buttonOeffnen = new JButton("Öffnen");
        buttonOeffnen.setToolTipText("Datei öffnen");
        toolbar.add(buttonOeffnen);
        buttonOeffnen.setActionCommand(actionOeffnen);
        buttonOeffnen.addActionListener(dateiListener);

        JButton buttonSpeichern = new JButton("Speichern");
        buttonSpeichern.setToolTipText("Datei speichern");
        toolbar.add(buttonSpeichern);
        buttonSpeichern.setActionCommand(actionSpeichern);
        buttonSpeichern.addActionListener(dateiListener);

        JButton buttonLinie = new JButton("Linie");
        buttonLinie.setToolTipText("Werkzeug Linie");
        toolbar.add(buttonLinie);

        buttonLinie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            zeichenfeld.setTool(Zeichenfeld.toolLinie);
            }
        });

        JButton buttonRechteck = new JButton("Rechteck");
        buttonRechteck.setToolTipText("Werkzeug Rechteck");
        toolbar.add(buttonRechteck);

        buttonRechteck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                zeichenfeld.setTool(Zeichenfeld.toolRechteck);
            }
        });

        JButton buttonEllipse = new JButton("Ellipse");
        buttonEllipse.setToolTipText("Werkzeug Ellipse");
        toolbar.add(buttonEllipse);

        buttonEllipse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                zeichenfeld.setTool(Zeichenfeld.toolEllipse);
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

