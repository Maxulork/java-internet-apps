import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("Simple Swing App");

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame
        frame.setSize(300, 200);

        // Create a JPanel to hold components
        JPanel panel = new JPanel();

        // Create a button
        JButton button = new JButton("Click Me!");

        // NEW CODE
        // Create a JMenuBar
        JMenuBar menuBar = new JMenuBar();

        // Create a JMenu
        JMenu fileMenu = new JMenu("File");

        JMenu helpMenu = new JMenu("Help");

        // Create JMenuItems
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit", new ImageIcon("exit.png")); // Add an icon

        // Add JMenuItems to the File menu
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator(); // Add a separator
        fileMenu.add(exitItem);

        // Create a submenu
        JMenu subMenu = new JMenu("Recent Files");
        JMenuItem file1 = new JMenuItem("File 1");
        JMenuItem file2 = new JMenuItem("File 2");
        JMenuItem file3 = new JMenuItem("File 3");
        subMenu.add(file1);
        subMenu.add(file2);
        subMenu.add(file3);
        fileMenu.add(subMenu);


        // Add the File menu to the JMenuBar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        // Set the JMenuBar for the JFrame
        frame.setJMenuBar(menuBar);

        // Create a JTextArea to display content
        JTextArea textArea = new JTextArea();
        frame.add(new JScrollPane(textArea));

        // Create a JPopupMenu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");

        // NEW CODE END

        // Add an ActionListener to the button
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show a message dialog when the button is clicked
                JOptionPane.showMessageDialog(frame, "Button Clicked!");
            }
        });

        // Add the button to the panel
        panel.add(button);
        // Add the panel to the frame
        frame.add(panel);
        // Set the frame to be visible
        frame.setVisible(true);
    }
}
