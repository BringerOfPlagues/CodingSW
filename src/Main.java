import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // zur Vermeidung von Fehlern zur Laufzeit
        SwingUtilities.invokeLater(Grafikprogramm::new);
    }
}
