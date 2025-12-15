import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingAppExample extends JFrame {

    public SwingAppExample() {
        setTitle("Swing App Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // JLabel
        JLabel label = new JLabel("This is a JLabel");
        add(label);

        // JButton
        JButton button = new JButton("Click me!");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SwingAppExample.this, "Button Clicked!");
            }
        });
        add(button);

        // JCheckBox
        JCheckBox checkBox = new JCheckBox("Check me");
        add(checkBox);

        // JRadioButton
        JRadioButton radioButton = new JRadioButton("Radio me");
        add(radioButton);

        // JComboBox
        String[] fruits = {"Apple", "Banana", "Orange"};
        JComboBox<String> comboBox = new JComboBox<>(fruits);
        add(comboBox);

        // JList
        String[] colors = {"Red", "Green", "Blue"};
        JList<String> colorList = new JList<>(colors);
        add(colorList);

        // JProgressBar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(50);
        add(progressBar);

        // JSlider
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        add(slider);

        // JTextField
        JTextField textField = new JTextField("Enter text here");
        add(textField);

        // JTextArea
        JTextArea textArea = new JTextArea("This is a JTextArea");
        textArea.setRows(5);
        textArea.setColumns(20);
        add(new JScrollPane(textArea));

        // JEditorPane
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setText("<html><body><h1>This is a JEditorPane</h1></body></html>");
        add(new JScrollPane(editorPane));

        // JTextPane
        JTextPane textPane = new JTextPane();
        textPane.setText("This is a JTextPane");
        add(new JScrollPane(textPane));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SwingAppExample();
            }
        });
    }
}
