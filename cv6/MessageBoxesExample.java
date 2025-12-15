import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import javax.swing.JMenu; 
import javax.swing.JMenuBar; 
import javax.swing.JMenuItem; 
import javax.swing.JFrame; 
import javax.swing.JOptionPane; 
import javax.swing.JPanel; 

public class MessageBoxesExample extends JFrame { 
	private JPanel panel; 
	public MessageBoxesExample() { 
		setTitle("Message Boxes Example"); 
		JMenuBar menubar = new JMenuBar(); 
		JMenu fileMenu = new JMenu("File"); 
		menubar.add(fileMenu); 
		setJMenuBar(menubar); 
		JMenuItem errorItem = new JMenuItem("Error message"); 
		JMenuItem questionItem = new JMenuItem("Question message"); 
		JMenuItem informationItem = new JMenuItem("Infomation message"); 
		JMenuItem warningItem = new JMenuItem("Warning message"); 
		fileMenu.add(errorItem); fileMenu.add(warningItem); 
		fileMenu.add(questionItem); fileMenu.add(informationItem); 
		panel = new JPanel(); errorItem.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) { 
				JOptionPane.showMessageDialog(panel, "Could not read file.", "Error", JOptionPane.ERROR_MESSAGE); 
				} 
			}); 
		warningItem.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) { 
				JOptionPane.showMessageDialog(panel, "Unknown document type.", "Warning", JOptionPane.WARNING_MESSAGE); 
				} 
			}); 
		questionItem.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) { 
				JOptionPane.showMessageDialog(panel, "Are you sure to quit?", "Question", JOptionPane.QUESTION_MESSAGE); 
				} 
			}); 
		informationItem.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent event) { 
				JOptionPane.showMessageDialog(panel, "Printing completed", "Question", JOptionPane.INFORMATION_MESSAGE); 
				} 
			}); 
		add(panel); 
		setSize(300, 200); 
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setVisible(true); 
		} 
	public static void main(String[] args) { 
		new MessageBoxesExample(); 
		} 
	}
