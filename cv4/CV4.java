import java.awt.*;
import javax.swing.*;
public class CV4 extends JFrame {
    public Integer LAYOUT_FRAME_LAYER = new Integer(1);
    public CV4() {
        super("Layout Examples"); // konštruktor z JFrame
        setSize(500, 460); // veľkosť rámu (okna)
        JDesktopPane desktopPane = new JDesktopPane(); // kontajner
        getContentPane().add(desktopPane); // ...vložíme do JFrame
        JInternalFrame frame1 = new JInternalFrame("FlowLayout", true, true);
        frame1.setBounds(10, 10, 150, 150); // rozmery 1. rámu
        Container contentPane = frame1.getContentPane(); // kontajner
        contentPane.setLayout(new FlowLayout());// nastavíme rozloženie
        contentPane.add(new JButton("1")); // vložíme tlačidlá
        contentPane.add(new JButton("2"));
        contentPane.add(new JButton("3"));
        contentPane.add(new JButton("4"));
        desktopPane.add(frame1, 0); frame1.show(); // nový rám
        JInternalFrame frame2 = new JInternalFrame("GridLayout", true, true);
        frame2.setBounds(170, 10, 150, 150);
        contentPane = frame2.getContentPane();// kontajner
        contentPane.setLayout(new GridLayout(2, 2));
        contentPane.add(new JButton("1"));
        contentPane.add(new JButton("2"));
        contentPane.add(new JButton("3"));
        contentPane.add(new JButton("4"));
        desktopPane.add(frame2, 0); frame2.show();
        JInternalFrame frame3 = new JInternalFrame("BorderLayout", true, true);
        frame3.setBounds(330, 10, 150, 150);
        contentPane = frame3.getContentPane();
        contentPane.add(new JButton("1"), BorderLayout.NORTH);
        contentPane.add(new JButton("2"), BorderLayout.EAST);
        contentPane.add(new JButton("3"), BorderLayout.SOUTH);
        contentPane.add(new JButton("4"), BorderLayout.WEST);
        desktopPane.add(frame3, 0);
        frame3.show(); // zobrazíme vnútorný rám
        JInternalFrame frame4 = new JInternalFrame("BoxLayout - X", true, true);
        frame4.setBounds(10, 170, 250, 80);
        contentPane = frame4.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.add(new JButton("1")); // nastavíme horizontálny strut
        contentPane.add(Box.createHorizontalStrut(12)); // ...12 pixlov...
        contentPane.add(new JButton("2")); // ...medzi 1. a 2. komponentom
        contentPane.add(Box.createGlue()); // vzdialenosť medzi 2.,3.,4.
        contentPane.add(new JButton("3")); // komponentom sú vyrovnané
        contentPane.add(Box.createHorizontalGlue()); // a zaberajú všetok
        contentPane.add(new JButton("4")); // zvyšný vert. priestor
        desktopPane.add(frame4, 0); // vďaka dvom glue komponentom
        frame4.show();// zobrazíme vnútorný rám
        JInternalFrame frame5 = new JInternalFrame("BoxLayout - Y", true, true);
        frame5.setBounds(330, 170, 150, 200);
        contentPane = frame5.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(new JButton("1")); // nastavíme vertikálny strut
        contentPane.add(Box.createVerticalStrut(10)); // 10 pixlov
        contentPane.add(new JButton("2"));
        contentPane.add(Box.createGlue());
        contentPane.add(new JButton("3"));
        contentPane.add(Box.createVerticalGlue());
        contentPane.add(new JButton("4"));
        desktopPane.add(frame5, 0); frame5.show();
        JInternalFrame frame6 = new JInternalFrame("SpringLayout", true, true);
        frame6.setBounds(10, 260, 250, 170);
        contentPane = frame6.getContentPane();
        contentPane.setLayout(new SpringLayout());
        contentPane.add(new JButton("1"), new SpringLayout.Constraints(
            Spring.constant(10), Spring.constant(10),
            Spring.constant(120), Spring.constant(70)));
        contentPane.add(new JButton("2"), new SpringLayout.Constraints(
            Spring.constant(160), Spring.constant(10),
            Spring.constant(70), Spring.constant(30)));
        contentPane.add(new JButton("3"), new SpringLayout.Constraints(
            Spring.constant(160), Spring.constant(50),
            Spring.constant(70), Spring.constant(30)));
        contentPane.add(new JButton("4"), new SpringLayout.Constraints(
            Spring.constant(10), Spring.constant(90),
            Spring.constant(50), Spring.constant(40)));
        contentPane.add(new JButton("5"), new SpringLayout.Constraints(
            Spring.constant(120), Spring.constant(90),
            Spring.constant(50), Spring.constant(40)));
        desktopPane.add(frame6, 0); frame6.show(); // zobrazíme 6. rám
        desktopPane.setSelectedFrame(frame6); }
    public static void main(String argv[]) {
        CV4 mainFrame = new CV4();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true); }
}
