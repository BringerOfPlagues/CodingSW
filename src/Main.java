//GitHub Repository: https://github.com/BringerOfPlagues/CodingSW

// Verwendete Icons aus "Material Design Icons" – https://pictogrammers.com
// Lizenz: Apache License 2.0

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Die Oberfläche wird im "Event-Thread" aufgerufen,
        // damit Swing richtig funktioniert und keine Darstellungsfehler auftreten
        SwingUtilities.invokeLater(Grafikprogramm::new);
    }
}
