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
    private int lastX;
    private int lastY;
    private int vorschauX;
    private int vorschauY;
    private boolean vorschauAktiv = false;
    private int strichdicke = 4;
    private Color farbauswahl = Color.BLACK;

    public static final int toolLinie = 0;
    public static final int toolRechteck = 1;
    public static final int toolEllipse = 2;
    public static final int toolRadierer = 3;
    public static final int toolStift = 4;

    private int aktuellesTool = toolLinie; //im Initialzustand der Anwendung ist "Linie" ausgewählt

    //Hier wird die Zeichenfläche erstellt
    public Zeichenfeld () {
        int breite = 1200;
        int hoehe = 800;
        bild = new BufferedImage(breite, hoehe, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = bild.createGraphics(); //zum Bearbeiten des BufferedImages
        g2.setColor(Color.WHITE); //Farbauswahl: weiß
        g2.fillRect(0, 0, breite, hoehe); //Hier wird das Zeichenfeld einmal komplett weiß eingefärbt
        g2.dispose();

        //Die gewünschten Dimensionen für die Fläche angeben
        setPreferredSize(new Dimension(breite, hoehe));

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                //Koordinaten beim Drücken der Maustaste in Variablen schreiben
                pushX = e.getX();
                pushY = e.getY();

                //Die letzten Koordinaten speichern
                lastX = pushX;
                lastY = pushY;

                //Vorschau bei Linie, Rechteck und Ellipse
                if (aktuellesTool == toolLinie || aktuellesTool == toolRechteck || aktuellesTool == toolEllipse) {
                    vorschauX = pushX;
                    vorschauY = pushY;
                    vorschauAktiv = true; //Vorschau aktivieren
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                //Koordinaten beim Loslassen der Maustaste in Variablen schreiben
                releaseX = e.getX();
                releaseY = e.getY();

                //Vorschau beenden, sobald losgelassen wird
                vorschauAktiv = false;

                if (aktuellesTool == toolLinie || aktuellesTool == toolRechteck || aktuellesTool == toolEllipse) {
                    //Beim Loslassen die Linie/Rechteck/Ellipse erscheinen lassen
                    Graphics2D g2 = bild.createGraphics();
                    zeichneForm(g2, pushX, pushY, releaseX, releaseY);
                    g2.dispose();
                    repaint(); //Zeichenfeld updaten
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                int x = e.getX(); //Aktuelle Koordinaten holen und speichern
                int y = e.getY();

                //Vorschau bei Linie, Rechteck und Ellipse
                if (aktuellesTool == toolLinie || aktuellesTool == toolRechteck || aktuellesTool == toolEllipse) {
                    vorschauX = x;
                    vorschauY = y;
                    repaint(); //Vorschau aktualisieren
                }
                if (aktuellesTool == toolRadierer) {
                    Graphics2D g2 = bild.createGraphics();
                    g2.setColor(Color.WHITE); //Radierer soll natürlich weiß "zeichnen" (löschen)

                    //Damit kann man Dicke und Form des Radierers einstellen. Hier sind die Linie und Ecken rund
                    g2.setStroke(new BasicStroke(strichdicke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(lastX, lastY, x, y); //Linie zeichnen von letzten Koordinaten zu aktuellen Koordinaten
                    g2.dispose();
                    repaint();

                    lastX = x; //Hier werden die aktuellen Koordinaten in die letzten Koordinaten geschrieben
                    lastY = y; //Für eine durchgängige Linie
                }

                else if (aktuellesTool == toolStift) {
                    //Wie Radierer, nur mit Farbe
                    Graphics2D g2 = bild.createGraphics();
                    g2.setColor(farbauswahl); //Zeichnen in der ausgewählten Farbe

                    //Damit kann man Dicke und Form des Stifts einstellen. Hier sind die Linie und Ecken rund
                    g2.setStroke(new BasicStroke(strichdicke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(lastX, lastY, x, y); //Linie zeichnen von letzten Koordinaten zu aktuellen Koordinaten
                    g2.dispose();
                    repaint();

                    lastX = x; //Hier werden die aktuellen Koordinaten in die letzten Koordinaten geschrieben
                    lastY = y; //Für eine durchgängige Linie
                }
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter); //MouseListener der Bewegungen erkennt hinzufügen
    }

    public void setFarbe(Color neueFarbe) {
        if (neueFarbe != null) { //Wenn die Methode aufgerufen wird und eine Farbe ausgewählt wurde --> Farbe updaten
            farbauswahl = neueFarbe;
        }
    }

    public Color getFarbauswahl() {
        return farbauswahl;
    }

    public void setTool(int werkzeug) {
        aktuellesTool = werkzeug;
    }

    public void setStrichdicke(int dicke) {
        strichdicke = dicke;
    }

    //Zum Leeren der Zeichenfläche
    public void leeren() {
        Graphics2D g2 = bild.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, bild.getWidth(), bild.getHeight());
        g2.dispose();
        repaint();
    }

    //Zum Übergeben des aktuellen Bildes
    public BufferedImage getBild() {
        return bild;
    }

    public void ladeBild(BufferedImage laden) {
        Graphics2D g2 = bild.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, bild.getWidth(), bild.getHeight()); //Zeichenfeld erstmal weiß einfärben
        //Ausgewähltes Bild der Öffnen-Methode auf das Zeichenfeld projizieren
        g2.drawImage(laden, 0, 0, bild.getWidth(), bild.getHeight(), null);
        g2.dispose();
        repaint();
    }

    private void zeichneForm(Graphics2D g2, int x1, int y1, int x2, int y2) {

        //Hier wird geschaut, ob X bzw. Y beim Drücken oder Loslassen kleiner ist
        //Dadurch ergibt sich dann die linke Ecke, damit Rechteck und Ellipse richtig gezeichnet werden
        int linkeEckeX = Math.min(x1, x2);
        int linkeEckeY = Math.min(y1, y2);

        //Hier werden Höhe und Breite für Rechteck und Ellipse berechnet
        //Math.abs gibt den Betrag aus, damit keine negativen Werte auftauchen
        int breite = Math.abs(x2 - x1);
        int hoehe  = Math.abs(y2 - y1);

        g2.setStroke(new BasicStroke(strichdicke)); //Zur Änderung der Strichdicke
        g2.setColor(farbauswahl);

        switch (aktuellesTool) {
            case toolLinie:
                g2.drawLine(x1, y1, x2, y2); //Linie zeichnen von Drücken zu Loslassen
                break;
            case toolRechteck:
                g2.drawRect(linkeEckeX, linkeEckeY, breite, hoehe);
                break;
            case toolEllipse:
                g2.drawOval(linkeEckeX, linkeEckeY, breite, hoehe);
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bild, 0, 0, null); //Bild auf dem Zeichenfeld anzeigen lassen

        //Für die Vorschau
        if (vorschauAktiv && (aktuellesTool == toolLinie || aktuellesTool == toolRechteck || aktuellesTool == toolEllipse)) {
            //Vorschau anzeigen, ohne das Bild zu verändern
            Graphics2D g2 = (Graphics2D) g.create();
            zeichneForm(g2, pushX, pushY, vorschauX, vorschauY);
            g2.dispose();
        }
    }
}
