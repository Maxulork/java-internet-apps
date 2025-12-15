// Príklad 23: MovingWindow.java
import java.awt.Font; import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener; import javax.swing.JFrame;
import javax.swing.JLabel; import javax.swing.JPanel;
public class MovingWindow extends JFrame implements ComponentListener {
    private JLabel xPosition, yPosition, width, heigth;
    public MovingWindow() {
        setTitle("Moving window Example");
        JPanel panel = new JPanel(); panel.setLayout(null);
        xPosition = new JLabel("x-coordinate: ");
        xPosition.setBounds(20, 20, 100, 25);
        yPosition = new JLabel("y-coordinate: ");
        yPosition.setBounds(140, 20, 100, 25);
        width = new JLabel("Frame width: ");
        width.setBounds(20, 45, 120, 25);
        heigth = new JLabel("Frame height: ");
        heigth.setBounds(140, 45, 120, 25);
        panel.add(xPosition); panel.add(yPosition);
        panel.add(width); panel.add(heigth); add(panel);
// Zaregistrujeme poslucháča pre inštanciu triedy MovingWindow
        addComponentListener(this); setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE); setVisible(true);
    }
    // Implementácia obsluhy udalostí pri zmene veľkosti okna
    public void componentResized(ComponentEvent e) {
// tu môžeme umiestniť kód, ktorý sa vykoná pri zmene veľkosti okna
        int dx = e.getComponent().getBounds().width;
        int dy = e.getComponent().getBounds().height;
        width.setText("Frame width: " + dx);
        heigth.setText("Frame height: " + dy);
    }
    // Implementácia obsluhy udalostí pri zmene polohy okna
    public void componentMoved(ComponentEvent e) {
// Získame x a y súradnicu okna
// Všimnite si, že musíme implementovať všetky štyri metódy,
// ktoré sú dostupné v ComponentListener, aj keď ich nebudeme používať.
        int x = e.getComponent().getX();
        int y = e.getComponent().getY();
        xPosition.setText("x-coordinate: " + x);
        yPosition.setText("y-coordinate: " + y);
    }
    // Implementácia obsluhy udalostí pri zobrazení okna
    public void componentShown(ComponentEvent e) { }
    // Implementácia obsluhy udalostí pri skrytí okna
    public void componentHidden(ComponentEvent e) { }
    public static void main(String[] args) {
        new MovingWindow();
    }
}
