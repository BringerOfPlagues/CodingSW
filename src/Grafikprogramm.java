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

public class Grafikprogramm extends JFrame {
    // Hier wird das Fenster erstellt
    public Zeichenfeld zeichenfeld; //Zum später aufrufen

    //Die folgenden zwei Objekte werden im toolListener benötigt
    //und müssen deshalb davor als Instanzvariablen deklariert werden
    private JPanel vorschauDicke;
    private JSlider sliderDicke;

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
    final String actionDicker = "Dicker";
    final String actionDuenner = "Dünner";


    public Grafikprogramm() {
        setTitle("Grafikprogramm");
        setSize(1200,800);
        setResizable(false); //feste Fenstergröße, damit Zeichenfläche und Bild immer gleich groß ist
        setLocationRelativeTo(null);
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
                    vorschauDicke.repaint(); //Aktualisierung Farbe des Vorschau-Punktes
                    break;

                //Strichdicke über das Menü oder über Shortcuts verändern
                case actionDicker:
                    if (sliderDicke.getValue() < sliderDicke.getMaximum()) {
                        sliderDicke.setValue(sliderDicke.getValue() + 1);
                    }
                    break;

                case actionDuenner:
                    if (sliderDicke.getValue() > sliderDicke.getMinimum()) {
                        sliderDicke.setValue(sliderDicke.getValue() - 1);
                    }
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

        //Menü für die Formen erzeugen
        JMenu menuFormen = new JMenu("Formen");
        menuBar.add(menuFormen);

        JMenuItem linieItem = new JMenuItem("Linie");
        menuFormen.add(linieItem);
        linieItem.setActionCommand(actionLinie);
        linieItem.addActionListener(toolListener);
        linieItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK)); //Formauswahl Linie: CTRL + L

        JMenuItem rechteckItem = new JMenuItem("Rechteck");
        menuFormen.add(rechteckItem);
        rechteckItem.setActionCommand(actionRechteck);
        rechteckItem.addActionListener(toolListener);
        rechteckItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK)); //Formauswahl Rechteck: CTRL + R

        JMenuItem ellipseItem = new JMenuItem("Ellipse");
        menuFormen.add(ellipseItem);
        ellipseItem.setActionCommand(actionEllipse);
        ellipseItem.addActionListener(toolListener);
        ellipseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK)); //Formauswahl Ellipse: CTRL + E

        //Werkzeugmenü erzeugen
        JMenu menuTools = new JMenu("Werkzeuge");
        menuBar.add(menuTools);

        JMenuItem stiftItem = new JMenuItem("Stift");
        menuTools.add(stiftItem);
        stiftItem.setActionCommand(actionStift);
        stiftItem.addActionListener(toolListener);
        stiftItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK)); //Werkzeugauswahl Stift: CTRL + B

        JMenuItem radiererItem = new JMenuItem("Radierer");
        menuTools.add(radiererItem);
        radiererItem.setActionCommand(actionRadierer);
        radiererItem.addActionListener(toolListener);
        radiererItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK)); //Werkzeugauswahl Radierer: CTRL + D

        JMenuItem farbeItem = new JMenuItem("Farbpalette");
        menuTools.add(farbeItem);
        farbeItem.setActionCommand(actionFarbe);
        farbeItem.addActionListener(toolListener);
        farbeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK)); //Werkzeugauswahl Farbpalette: CTRL + F

        menuTools.addSeparator();

        JMenuItem dickerItem = new JMenuItem("Dicke erhöhen");
        menuTools.add(dickerItem);
        dickerItem.setActionCommand(actionDicker);
        dickerItem.addActionListener(toolListener);
        dickerItem.setAccelerator((KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.CTRL_DOWN_MASK))); //Strichdicke erhöhen: CTRL + rechte Pfeiltaste

        JMenuItem duennerItem = new JMenuItem("Dicke reduzieren");
        menuTools.add(duennerItem);
        duennerItem.setActionCommand(actionDuenner);
        duennerItem.addActionListener(toolListener);
        duennerItem.setAccelerator((KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.CTRL_DOWN_MASK))); //Strichdicke reduzieren: CTRL + linke Pfeiltaste

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
        sliderDicke = new JSlider(1, 50, 9); //Von 1 bis 50, Startwert 9
        sliderDicke.setToolTipText("Strichdicke ändern");

        //Vorschau für die Auswahl der Dicke
        vorschauDicke = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int dicke = sliderDicke.getValue();
                Graphics2D g2 = (Graphics2D) g.create(); //Zur Darstellung der Live-Vorschau, ohne die Bilddatei zu verändern
                g2.setColor(Zeichenfeld.farbauswahl);

                int x = (getWidth() / 2 - dicke / 2) - 1; //Damit der Vorschau-Kreis zentriert angezeigt wird
                int y = (getHeight() / 2 - dicke / 2) - 1; //-1, da unten der Kreis 1 px größer dargestellt wird

                //Vorschau-Kreis zeichnen
                g2.fillOval(x, y, dicke +1, dicke +1); //dicke +1, weil Vorschaudicke kleiner wirkt als die Dicke beim Zeichnen
                g2.dispose();
            }
        };

        //Größe des Vorschaufensters einstellen. Min- und Max-Size braucht man, weil sonst PreferredSize überschrieben wird
        Dimension vorschauFenster = new Dimension(60, 60);
        vorschauDicke.setMinimumSize(vorschauFenster);
        vorschauDicke.setPreferredSize(vorschauFenster);
        vorschauDicke.setMaximumSize(vorschauFenster);
        vorschauDicke.setOpaque(false); //Vorschaufenster an die Hintergrundfarbe anpassen
        vorschauDicke.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); //Dünner Rahmen in hellgrau um die Vorschau

        //Label, dass die aktuelle Strichdicke anzeigt
        JLabel strichDicke = new JLabel(sliderDicke.getValue() + " px");
        JPanel sliderPanel = new JPanel();

        //Hier wird ein beschrifteter Rahmen um das SliderPanel gezogen, sieht besser aus
        sliderPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Strichdicke"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5))); //Inhalt des Rahmens im Abstand 5 zu jeder Seite
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS)); //Slider und Label untereinander anordnen
        sliderPanel.setOpaque(false);

        //Anzeige der Dicke und Slider linksbündig darstellen
        strichDicke.setAlignmentX(Component.LEFT_ALIGNMENT);
        sliderDicke.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Es wird abgefragt, wo der Slider steht und dann die Dicke über setStrichdicke() dementsprechend angepasst
        sliderDicke.addChangeListener(e -> {
            int wert = sliderDicke.getValue();
            zeichenfeld.setStrichdicke(wert);
            strichDicke.setText(wert + " px");
            vorschauDicke.repaint(); //Aktualisierung Vorschau-Kreis
        });

        toolbar.addSeparator();
        toolbar.add(vorschauDicke);
        toolbar.addSeparator();
        sliderPanel.add(sliderDicke);
        sliderPanel.add(strichDicke);
        toolbar.add(sliderPanel);
        toolbar.addSeparator(new Dimension(30, 10));

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
        fileChooser.setFileFilter(jpgFilter()); //Hier wird der Filter von oben benutzt

        //Das Fenster für die Speicherung wird geöffnet und die Aktion, die der Benutzer ausführt, wird in auswahl geschrieben
        int auswahl = fileChooser.showSaveDialog(this);

        //Wird nur ausgeführt, wenn der Benutzer einen Pfad ausgewählt und "speichern" gedrückt hat
        if(auswahl == JFileChooser.APPROVE_OPTION) {

            //Datei holen
            File file = fileChooser.getSelectedFile();

            //Dateiname holen und wieder in Kleinbuchstaben speichern, für eine einfachere Prüfung
            String name = file.getName().toLowerCase();

            if (!name.endsWith(".jpg")) {
                //file.getParentFile() gibt uns den Pfad zum ausgewählten Ordner zurück und danach wird der Dateiname mit Endung ".jpg" angehangen
                file = new File(file.getParentFile(), file.getName() + ".jpg");
            }

            try {
                //Aktuelles Bild holen und als .jpg am ausgewählten Speicherort speichern. Rückgabe true, falls erfolgreich
                boolean speicherversuch = ImageIO.write(zeichenfeld.getBild(), "jpg", file);

                if (!speicherversuch) {
                    //Falls nicht erfolgreich, Fehlermeldung ausgeben. Fenster im Design einer Fehlermeldung.
                    JOptionPane.showMessageDialog(this, "Das Bild konnte nicht gespeichert werden!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Wird ausgelöst, wenn das Programm die Datei aus technischen Gründen nicht speichern kann (I/O-Fehler)
            catch (IOException e) {
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
                //Die Pixel der .jpg-Datei werden in "laden" gespeichert, damit kann es dann auf dem Zeichenfeld wieder projiziert werden
                BufferedImage laden = ImageIO.read(file);

                if(laden == null) {
                    JOptionPane.showMessageDialog(this, "Datei kann nicht geladen werden", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else {
                    zeichenfeld.ladeBild(laden);
                }
            }

            catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Fehler beim Öffnen" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}












