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
    private int Strichdicke = 4;
    private Color Farbauswahl = Color.BLACK;

    public static final int toolLinie = 0;
    public static final int toolRechteck = 1;
    public static final int toolEllipse = 2;
    public static final int toolRadierer = 3;
    public static final int toolStift = 4;


    int aktuellesTool = toolLinie; //im Initialzustand der Anwendung ist "Linie" ausgewählt

    //Hier wird die Zeichenfläche erstellt
    public Zeichenfeld () {
        int breite = 1200;
        int hoehe = 800;
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
                            Grafik2D.setStroke(new BasicStroke(Strichdicke)); //Zur Änderung der Strichdicke
                            Grafik2D.drawLine(pushX, pushY, releaseX, releaseY); //Linie zeichnen von Drücken zu Loslassen
                            break;

                        case toolRechteck:
                            Grafik2D.setStroke(new BasicStroke(Strichdicke));
                            Grafik2D.drawRect(linkeEckeX, linkeEckeY, breite, hoehe);
                            break;

                        case toolEllipse:
                            Grafik2D.setStroke(new BasicStroke(Strichdicke));
                            Grafik2D.drawOval(linkeEckeX, linkeEckeY, breite, hoehe);
                            break;
                    }

                    Grafik2D.dispose(); //schließen
                    repaint();//Zeichenfeld updaten
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
                    Graphics2D Grafik2D = bild.createGraphics();
                    Grafik2D.setColor(Color.WHITE); //Radierer soll natürlich weiß "zeichnen" (löschen)

                    //Damit kann man Dicke und Form des Radierers einstellen. Hier sind die Linie und Ecken rund
                    Grafik2D.setStroke(new BasicStroke(Strichdicke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    Grafik2D.drawLine(lastX, lastY, x, y); //Linie zeichnen von letzten Koordinaten zu aktuellen Koordinaten
                    Grafik2D.dispose();
                    repaint();

                    lastX = x; //Hier werden die aktuellen Koordinaten in die letzten Koordinaten geschrieben
                    lastY = y; //Für eine durchgängige Linie
                }

                else if (aktuellesTool == toolStift) {
                    Graphics2D Grafik2D = bild.createGraphics();
                    Grafik2D.setColor(Farbauswahl); //Zeichnen in der ausgewählten Farbe

                    //Damit kann man Dicke und Form des Stifts einstellen. Hier sind die Linie und Ecken rund
                    Grafik2D.setStroke(new BasicStroke(Strichdicke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    Grafik2D.drawLine(lastX, lastY, x, y); //Linie zeichnen von letzten Koordinaten zu aktuellen Koordinaten
                    Grafik2D.dispose();
                    repaint();

                    lastX = x; //Hier werden die aktuellen Koordinaten in die letzten Koordinaten geschrieben
                    lastY = y; //Für eine durchgängige Linie
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

    public void setStrichdicke(int dicke) {
        Strichdicke = dicke;
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bild, 0, 0, null); //Bild auf dem Zeichenfeld anzeigen lassen

        //Für die Vorschau
        if (vorschauAktiv && (aktuellesTool == toolLinie || aktuellesTool == toolRechteck || aktuellesTool == toolEllipse)) {
            //Vorschau anzeigen, ohne das Bild zu verändern
            Graphics2D Grafik2D = (Graphics2D) g.create();

            //Der Rest ist wie bei MouseReleased, nur statt releaseX/-Y wird vorschauX/-Y verwendet
            Grafik2D.setColor(Farbauswahl);

            int linkeEckeX = Math.min(pushX, vorschauX);
            int linkeEckeY = Math.min(pushY, vorschauY);

            int breite = Math.abs(vorschauX - pushX);
            int hoehe = Math.abs(vorschauY - pushY);

            switch (aktuellesTool) {
                case toolLinie:
                    Grafik2D.setStroke(new BasicStroke(Strichdicke)); //Zur Änderung der Strichdicke
                    Grafik2D.drawLine(pushX, pushY, vorschauX, vorschauY); //Linie zeichnen von Drücken zu Loslassen
                    break;

                case toolRechteck:
                    Grafik2D.setStroke(new BasicStroke(Strichdicke));
                    Grafik2D.drawRect(linkeEckeX, linkeEckeY, breite, hoehe);
                    break;

                case toolEllipse:
                    Grafik2D.setStroke(new BasicStroke(Strichdicke));
                    Grafik2D.drawOval(linkeEckeX, linkeEckeY, breite, hoehe);
                    break;
            }

            Grafik2D.dispose(); //schließen
        }
    }
}
