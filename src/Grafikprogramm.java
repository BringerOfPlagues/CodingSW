import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

        //ActionListener für alle Datei-Funktionen
        ActionListener dateiListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();

                switch (action) {
                    case actionNeu:
                        //Ein Fenster öffnet sich, in dem man bestätigen muss, dass man das Feld leeren möchte
                        int sicher = JOptionPane.showConfirmDialog(Grafikprogramm.this,
                                "Sind Sie sicher, dass Sie eine neue Datei anlegen wollen?\nNicht gespeicherte Änderungen gehen verloren!");

                        //Betätigt man "Ja" wird sicher auf 0 gesetzt und das Feld wird initialisiert
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

        //ActionListener für alle Tools
        ActionListener toolListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();

                switch (action) {
                    case actionLinie:
                        //Aktuelles Tool auf Linie setzen
                        zeichenfeld.setTool(Zeichenfeld.toolLinie);
                        break;

                    case actionRechteck:
                        zeichenfeld.setTool(Zeichenfeld.toolRechteck);
                        break;

                    case actionEllipse:
                        zeichenfeld.setTool(Zeichenfeld.toolEllipse);
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
        buttonLinie.setActionCommand(actionLinie);
        buttonLinie.addActionListener(toolListener);

        JButton buttonRechteck = new JButton("Rechteck");
        buttonRechteck.setToolTipText("Werkzeug Rechteck");
        toolbar.add(buttonRechteck);
        buttonRechteck.setActionCommand(actionRechteck);
        buttonRechteck.addActionListener(toolListener);

        JButton buttonEllipse = new JButton("Ellipse");
        buttonEllipse.setToolTipText("Werkzeug Ellipse");
        toolbar.add(buttonEllipse);
        buttonEllipse.setActionCommand(actionEllipse);
        buttonEllipse.addActionListener(toolListener);

        //Toolbar hinzufügen, Anordnung oben
        add(toolbar, BorderLayout.NORTH);

        //Zeichenfeld in das Fenster hinzufügen
        zeichenfeld = new Zeichenfeld();
        add(zeichenfeld);

        setVisible(true);
    }

    private FileFilter jpgFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File f) {
                //Das braucht man, damit man über Ordner und Verzeichnisse navigieren kann
                if (f.isDirectory()) {
                    return true;
                }
                //Name der ausgewählten Datei wird in Kleinbuchstaben in fileName geschrieben
                //Dann muss man nur .jpg prüfen und kann Groß- und Kleinschreibung ignorieren
                String fileName = f.getName().toLowerCase();

                //Überprüfung, ob Datei in .jpg endet
                return fileName.endsWith(".jpg");
            }

            @Override
            public String getDescription() {
                return "Nur Dateityp .jpg zulässig!";
            }
        };
    }
}











