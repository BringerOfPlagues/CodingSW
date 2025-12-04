import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Zeichenfeld extends JPanel {

    private BufferedImage bild;

    //Hier wird die Zeichenfläche erstellt
    public Zeichenfeld () {
        int hoehe = 600;
        int breite = 800;

        bild = new BufferedImage(breite, hoehe, BufferedImage.TYPE_INT_RGB);

        Graphics2D Grafik2D = bild.createGraphics();
        Grafik2D.setColor(Color.WHITE); //Farbauswahl: weiß
        Grafik2D.fillRect(0, 0, breite, hoehe); //Hier wird das Zeichenfeld einmal komplett weiß eingefärbt
        Grafik2D.dispose(); //beenden

        //Die gewünschten Dimensionen für die Fläche angeben
        setPreferredSize(new Dimension(breite, hoehe));
    }

    //Zum Leeren der Zeichenfläche
    public void leeren() {
        Graphics2D Grafik2D = bild.createGraphics();
        Grafik2D.setColor(Color.WHITE); //Farbauswahl: weiß
        Grafik2D.fillRect(0, 0, bild.getWidth(), bild.getHeight());
        Grafik2D.dispose();
        repaint(); //Braucht man, um das Fenster zu aktualisieren
    }

    //Zum Abspeichern des Bildes
    public BufferedImage getBild() {
        return bild;
    }

    //Hier entsteht die Funktion zum Zeichnen später
    //paintComponent wird von JComponents bereitgestellt
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bild, 0, 0, null); //Bild auf dem Zeichenfeld anzeigen lassen
    }
}
