import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Zeichenfeld extends JPanel {

    private final BufferedImage bild;
    private int pushX;
    private int pushY;
    private int releaseX;
    private int releaseY;
    private Color Farbauswahl = Color.BLACK;

    public static final int toolLinie = 0;
    public static final int toolRechteck = 1;
    public static final int toolEllipse = 2;
    public static final int toolRadierer = 3;

    private final int hoehe = 800;
    private final int breite = 1200;


    int aktuellesTool = toolLinie; //im Initialzustand der Anwendung ist "Linie" ausgewählt

    //Hier wird die Zeichenfläche erstellt
    public Zeichenfeld () {

        bild = new BufferedImage(breite, hoehe, BufferedImage.TYPE_INT_RGB);

        Graphics2D Grafik2D = bild.createGraphics(); //zum Bearbeiten des BufferedImage
        Grafik2D.setColor(Color.WHITE); //Farbauswahl: weiß
        Grafik2D.fillRect(0, 0, breite, hoehe); //Hier wird das Zeichenfeld einmal komplett weiß eingefärbt
        Grafik2D.dispose(); //beenden

        //Die gewünschten Dimensionen für die Fläche angeben
        setPreferredSize(new Dimension(breite, hoehe));

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                //Koordinaten beim Drücken der Maustaste in Variablen schreiben
                pushX = e.getX();
                pushY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                //Koordinaten beim Loslassen der Maustaste in Variablen schreiben
                releaseX = e.getX();
                releaseY = e.getY();

                //Beim Loslassen die Linie/Rechteck/Ellipse erscheinen lassen
                Graphics2D Grafik2D = bild.createGraphics();
                Grafik2D.setColor(Farbauswahl);

                //Hier wird geschaut, ob X bzw. Y beim Drücken oder Loslassen kleiner ist
                //Dadurch ergibt sich dann die linke Ecke, damit Rechteck und Ellipse richtig gezeichnet werden
                int linkeEckeX = Math.min(pushX, releaseX);
                int linkeEckeY = Math.min(pushY, releaseY);

                //Hier werden Höhe und Breite für Rechteck und Ellipse berechnet
                //Math.abs gibt den Betrag aus, damit keine negativen Werte auftauchen
                int breite = Math.abs(releaseX - pushX);
                int hoehe = Math.abs(releaseY - pushY);

                switch (aktuellesTool) {
                    case toolLinie:
                        Grafik2D.drawLine(pushX, pushY, releaseX, releaseY);
                        break;

                    case toolRechteck:
                        Grafik2D.drawRect(linkeEckeX, linkeEckeY, breite, hoehe);
                        break;

                    case toolEllipse:
                        Grafik2D.drawOval(linkeEckeX, linkeEckeY, breite, hoehe);
                        break;
                }

                Grafik2D.dispose(); //schließen
                repaint();//Zeichenfeld updaten
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (aktuellesTool == toolRadierer) {
                    Graphics2D Grafik2D = bild.createGraphics();
                    Grafik2D.setColor(Color.WHITE); //Radierer soll natürlich weiß "zeichnen" (löschen)

                    int laenge = 25; //Seitenlänge des Radierer-Quadrats
                    int x = e.getX() - laenge / 2; // laenge / 2 muss abgezogen werden, damit das Quadrat mittig vom Mauszeiger erscheint
                    int y = e.getY() - laenge / 2;

                    //Rund zeichnen beim Ziehen des Mauszeigers über den Bildschirm
                    Grafik2D.fillRoundRect(x, y, laenge, laenge, 90, 90);
                    Grafik2D.dispose();
                    repaint();
                }
            }
        };
        addMouseListener(adapter); //MouseListener hinzufügen
        addMouseMotionListener(adapter); //MouseListener der Bewegungen erkennt hinzufügen
    }

    public void setFarbe(Color neueFarbe) {
        if (neueFarbe != null) { //Wenn die Methode aufgerufen wird und eine neue Farbe ausgewählt wurde --> Farbe updaten
            Farbauswahl = neueFarbe;
        }
    }

    public void setTool(int werkzeug) {
        aktuellesTool = werkzeug;
    }

    //Zum Leeren der Zeichenfläche
    public void leeren() {
        Graphics2D Grafik2D = bild.createGraphics();
        Grafik2D.setColor(Color.WHITE); //Farbauswahl: weiß
        Grafik2D.fillRect(0, 0, bild.getWidth(), bild.getHeight());
        Grafik2D.dispose();
        repaint(); //Braucht man, um das Fenster zu aktualisieren
    }

    //Zum Übergeben des aktuellen Bildes
    public BufferedImage getBild() {
        return bild;
    }

    public void ladeBild(BufferedImage laden) {
        Graphics2D Grafik2D = bild.createGraphics();
        Grafik2D.setColor(Color.WHITE); //Farbauswahl: weiß
        Grafik2D.fillRect(0, 0, bild.getWidth(), bild.getHeight()); //Zeichenfeld erstmal weiß einfärben
        //Ausgewähltes Bild der Öffnen-Methode auf das Zeichenfeld projezieren
        Grafik2D.drawImage(laden, 0, 0, bild.getWidth(), bild.getHeight(), null);
        Grafik2D.dispose();
        repaint();

    }

    //Hier entsteht die Funktion zum Zeichnen später
    //paintComponent wird von JComponents bereitgestellt
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bild, 0, 0, null); //Bild auf dem Zeichenfeld anzeigen lassen
    }
}
