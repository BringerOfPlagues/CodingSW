import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

// Verwendete Icons aus "Material Design Icons" – https://pictogrammers.com
// Lizenz: Apache License 2.0

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
    final String actionFarbe = "Farbe";
    final String actionRadierer = "Radierer";
    final String actionStift = "Stift";


    public Grafikprogramm() {
        setTitle("Grafikprogramm");
        setSize(1200,800); //Fenstergröße setzen
        setResizable(false); //Fenstergröße nicht veränderbar
        setLocationRelativeTo(null); //Fenster zentrieren
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //ActionListener für alle Datei-Funktionen
        ActionListener dateiListener = e -> {
            String action = e.getActionCommand();

            switch (action) {
                case actionNeu:
                    //Ein Fenster öffnet sich, in dem man bestätigen muss, dass man das Feld leeren möchte
                    int sicherNeu = JOptionPane.showConfirmDialog(Grafikprogramm.this,
                            "Sind Sie sicher, dass Sie eine neue Datei anlegen wollen?\nNicht gespeicherte Änderungen gehen verloren!");

                    //Betätigt man "Ja" wird sicher auf 0 gesetzt und das Feld wird initialisiert
                    if (sicherNeu == 0) zeichenfeld.leeren();
                    break;

                case actionOeffnen:
                    oeffnen();
                    break;

                case actionSpeichern:
                    speichern();
                    break;

                case actionBeenden:
                    //Ein Fenster öffnet sich, in dem man bestätigen muss, dass man das Programm beenden möchte
                    int sicherBeenden = JOptionPane.showConfirmDialog(Grafikprogramm.this,
                            "Sind Sie sicher, dass Sie das Programm beenden wollen?\nNicht gespeicherte Änderungen gehen verloren!");

                    //Betätigt man "Ja" wird sicher auf 0 gesetzt und das Feld wird initialisiert
                    if (sicherBeenden == 0) System.exit(0);
                    break;
            }
        };

        //ActionListener für alle Tools
        ActionListener toolListener = e -> {
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

                case actionStift:
                    zeichenfeld.setTool(Zeichenfeld.toolStift);
                    break;

                case actionRadierer:
                    zeichenfeld.setTool(Zeichenfeld.toolRadierer);
                    break;

                case actionFarbe:
                    //Das Farbauswahl-Fenster öffnet sich, Auswahl wird in neueFarbe geschrieben
                    Color neueFarbe = JColorChooser.showDialog(Grafikprogramm.this, "Farbe auswählen", Color.BLACK);
                    //Aktuelle Farbe updaten
                    zeichenfeld.setFarbe(neueFarbe);
                    break;
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
        neuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK)); //Neue Datei: CTRL + N

        //Menü-Funktion "Öffnen", um eine Datei zu öffnen
        JMenuItem oeffnenItem = new JMenuItem("Öffnen");
        menuDatei.add(oeffnenItem);
        oeffnenItem.setActionCommand(actionOeffnen);
        oeffnenItem.addActionListener(dateiListener);
        oeffnenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK)); //Datei öffnen: CTRL + O

        //Menü-Funktion "Speichern", um eine Datei zu speichern
        JMenuItem speichernItem = new JMenuItem("Speichern");
        menuDatei.add(speichernItem);
        speichernItem.setActionCommand(actionSpeichern);
        speichernItem.addActionListener(dateiListener);
        speichernItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)); //Datei speichern: CTRL + S

        menuDatei.addSeparator(); //Trennstrich, sieht hübscher aus

        //Beenden-Funktion im Dateimenü erzeugen
        JMenuItem beendenItem = new JMenuItem("Beenden");
        menuDatei.add(beendenItem);
        beendenItem.setActionCommand(actionBeenden);
        beendenItem.addActionListener(dateiListener);
        beendenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK)); //Programm beenden: CTRL + Q

        //Werkzeugmenü erzeugen
        JMenu menuTools = new JMenu("Werkzeuge");
        menuBar.add(menuTools);

        JMenuItem linieItem = new JMenuItem("Linie");
        menuTools.add(linieItem);
        linieItem.setActionCommand(actionLinie);
        linieItem.addActionListener(toolListener);
        linieItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK)); //Werkzeugauswahl Linie: CTRL + L

        JMenuItem rechteckItem = new JMenuItem("Rechteck");
        menuTools.add(rechteckItem);
        rechteckItem.setActionCommand(actionRechteck);
        rechteckItem.addActionListener(toolListener);
        rechteckItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK)); //Werkzeugauswahl Rechteck: CTRL + R

        JMenuItem ellipseItem = new JMenuItem("Ellipse");
        menuTools.add(ellipseItem);
        ellipseItem.setActionCommand(actionEllipse);
        ellipseItem.addActionListener(toolListener);
        ellipseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK)); //Werkzeugauswahl Ellipse: CTRL + E

        menuTools.addSeparator();

        JMenuItem stiftItem = new JMenuItem("Stift");
        menuTools.add(stiftItem);
        stiftItem.setActionCommand(actionStift);
        stiftItem.addActionListener(toolListener);
        stiftItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK)); //Werkzeugauswahl Stift: CTRL + B

        JMenuItem radiererItem = new JMenuItem("Radierer");
        menuTools.add(radiererItem);
        radiererItem.setActionCommand(actionRadierer);
        radiererItem.addActionListener(toolListener);
        radiererItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK)); //Werkzeugauswahl Linie: CTRL + D

        JMenuItem farbeItem = new JMenuItem("Farbpalette");
        menuTools.add(farbeItem);
        farbeItem.setActionCommand(actionFarbe);
        farbeItem.addActionListener(toolListener);
        farbeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK)); //Werkzeugauswahl Linie: CTRL + F

        //Toolbar erzeugen mit einfachen Funktionen
        JToolBar toolbar = new JToolBar();

        //Es folgen die Buttons
        //Icon laden aus Ordner in src. RequireNonNull prüft, ob Icon gefunden wurde
        JButton buttonNeu = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/Neu.png"))));
        buttonNeu.setToolTipText("Neue Datei"); //Einen Tipp anzeigen lassen, wenn man mit der Maus über das Feld geht
        toolbar.add(buttonNeu);
        buttonNeu.setActionCommand(actionNeu);
        buttonNeu.addActionListener(dateiListener);

        JButton buttonOeffnen = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/Oeffnen.png"))));
        buttonOeffnen.setToolTipText("Datei öffnen");
        toolbar.add(buttonOeffnen);
        buttonOeffnen.setActionCommand(actionOeffnen);
        buttonOeffnen.addActionListener(dateiListener);

        JButton buttonSpeichern = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/Speichern.png"))));
        buttonSpeichern.setToolTipText("Datei speichern");
        toolbar.add(buttonSpeichern);
        buttonSpeichern.setActionCommand(actionSpeichern);
        buttonSpeichern.addActionListener(dateiListener);

        toolbar.addSeparator(); //Optische Trennung zwischen Dateifunktionen und Werkzeugfunktionen

        JButton buttonLinie = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/Linie.png"))));
        buttonLinie.setToolTipText("Linie zeichnen");
        toolbar.add(buttonLinie);
        buttonLinie.setActionCommand(actionLinie);
        buttonLinie.addActionListener(toolListener);

        JButton buttonRechteck = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/Rechteck.png"))));
        buttonRechteck.setToolTipText("Rechteck zeichnen");
        toolbar.add(buttonRechteck);
        buttonRechteck.setActionCommand(actionRechteck);
        buttonRechteck.addActionListener(toolListener);

        JButton buttonEllipse = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/Ellipse.png"))));
        buttonEllipse.setToolTipText("Ellipse zeichnen");
        toolbar.add(buttonEllipse);
        buttonEllipse.setActionCommand(actionEllipse);
        buttonEllipse.addActionListener(toolListener);

        JButton buttonStift = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/Stift.png"))));
        buttonStift.setToolTipText("Freihandzeichnen");
        toolbar.add(buttonStift);
        buttonStift.setActionCommand(actionStift);
        buttonStift.addActionListener(toolListener);

        JButton buttonRadierer = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/Radierer.png"))));
        buttonRadierer.setToolTipText("Radierer");
        toolbar.add(buttonRadierer);
        buttonRadierer.setActionCommand(actionRadierer);
        buttonRadierer.addActionListener(toolListener);

        JButton buttonFarbe = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/Farbpalette.png"))));
        buttonFarbe.setToolTipText("Farbe ändern");
        toolbar.add(buttonFarbe);
        buttonFarbe.setActionCommand(actionFarbe);
        buttonFarbe.addActionListener(toolListener);

        //Slider für Strichdicke
        JSlider sliderDicke = new JSlider(1, 50, 4); //Von 1 bis 50, Startwert 4
        sliderDicke.setPreferredSize(new Dimension(120, 30));//Breite und Länge des Sliders
        sliderDicke.setToolTipText("Strichdicke ändern");

        //Vorschau für die Auswahl der Dicke
        JPanel vorschauDicke = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int dicke = sliderDicke.getValue();
                Graphics2D Grafik2D = (Graphics2D) g.create();
                Grafik2D.setColor(Zeichenfeld.Farbauswahl);

                int x = getWidth() / 2 - dicke / 2; //Damit der Vorschau-Kreis zentriert angezeigt wird
                int y = getHeight() / 2 - dicke / 2;

                Grafik2D.fillOval(x, y, dicke, dicke); //Vorschau-Kreis zeichnen
                Grafik2D.dispose();
            }
        };

        vorschauDicke.setPreferredSize(new Dimension(5, 5));

        //Es wird abgefragt, wo der Slider steht und dann die Dicke über setStrichdicke() dementsprechend angepasst
        sliderDicke.addChangeListener(e -> {
            int wert = sliderDicke.getValue();
            zeichenfeld.setStrichdicke(wert);
            vorschauDicke.repaint(); //Aktualisierung Vorschau-Kreis
        });

        toolbar.add(vorschauDicke);
        toolbar.add(sliderDicke);


        //Toolbar hinzufügen, Anordnung oben
        add(toolbar, BorderLayout.NORTH);

        //Zeichenfeld in das Fenster hinzufügen
        zeichenfeld = new Zeichenfeld();
        add(zeichenfeld, BorderLayout.CENTER);

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

    private void speichern() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Bild speichern");
        //Hier wird der Filter von oben benutzt
        fileChooser.setFileFilter(jpgFilter());

        //Das Fenster für die Speicherung wird geöffnet und das was der Benutzer macht wird in auswahl geschrieben
        int auswahl = fileChooser.showSaveDialog(this);

        //Wird nur ausgeführt, wenn der Benutzer einen Pfad ausgewählt und "speichern" gedrückt hat
        if(auswahl == JFileChooser.APPROVE_OPTION) {

            //Ausgewählte Datei holen
            File file = fileChooser.getSelectedFile();

            //Dateiname holen und wieder in Kleinbuchstaben speichern, für eine einfachere Prüfung
            String name = file.getName().toLowerCase();

            if (!name.endsWith(".jpg")) {
                //file.getParentFile() gibt uns den Pfad zum ausgewählten Ordner zurück und danach wird der Dateiname mit Endung ".jpg" angehangen
                file = new File(file.getParentFile(), file.getName() + ".jpg");
            }

            try {
                //Aktuelles Bild holen und als .jpg am ausgewählten Speicherort speichern. Rückgabe true, falls erfolgreich
                boolean Speicherversuch = ImageIO.write(zeichenfeld.getBild(), "jpg", file);

                if (!Speicherversuch) {
                    //Falls nicht erfolgreich, Fehlermeldung ausgeben. Fenster im Design einer Fehlermeldung.
                    JOptionPane.showMessageDialog(this, "Das Bild konnte nicht gespeichert werden!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            //ImageIO.write will, dass man die IOException behandelt
            catch (IOException e) {
                //Wenn etwas schief läuft Fehlermeldung anzeigen
                JOptionPane.showMessageDialog(this, "Fehler beim Speichern" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void oeffnen () {
        //Der Aufbau ist ähnlich wie bei der Speichern-Methode
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Bild öffnen");
        fileChooser.setFileFilter(jpgFilter());

        int auswahl = fileChooser.showOpenDialog(this);

        if (auswahl == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                //Die Pixel der .jpg-Datei werden in "laden" gespeichert, damit kann es dann auf dem Zeichenfeld wieder projeziert werden
                BufferedImage laden = ImageIO.read(file);

                if(laden == null) {
                    JOptionPane.showMessageDialog(this, "Datei kann nicht geladen werden", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else {
                    zeichenfeld.ladeBild(laden);
                    pack();
                    setLocationRelativeTo(null);
                }
            }

            catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Fehler beim Öffnen" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}












