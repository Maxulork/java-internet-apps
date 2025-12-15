import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.List;

public class StudentHelperApp extends JFrame {

    private static final String DATA_FILE = "users.dat";
    private static final String LOGO_PATH = "C:\\Users\\User\\Downloads\\Adobe Express - file.png";

    // CardLayout = simple screen switcher (LOGIN / REGISTER / CABINET)
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    // In-memory "storage" of users (persisted to DATA_FILE)
    private Map<String, User> users;
    private User currentUser;

    // Login UI
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    // Register UI
    private JTextField regUsernameField;
    private JPasswordField regPasswordField;
    private JTextField regEmailField;
    private JTextField regStudentIdField;

    // Cabinet UI
    private JLabel welcomeLabel;
    private DefaultTableModel todoModel;
    private JTextField todoTextField;

    // Profile UI labels
    private JLabel profileUsernameLabel;
    private JLabel profileEmailLabel;
    private JLabel profileStudentIdLabel;

    // Chart panel (custom painted)
    private TaskStatsPanel statsPanel;

    // Theme colors
    private static final Color BG_MAIN = new Color(18, 18, 18);
    private static final Color BG_PANEL = new Color(28, 28, 28);
    private static final Color GREEN = new Color(0, 170, 60);
    private static final Color GREEN_HOVER = new Color(0, 200, 80);

    public StudentHelperApp() {
        loadUsers();

        setTitle("TUKE Student Helper");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_MAIN);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BG_MAIN);

        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createRegisterPanel(), "REGISTER");
        mainPanel.add(createCabinetPanel(), "CABINET");

        setJMenuBar(createMenuBar());
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    // Loads logo from absolute path; shows fallback text if missing
    private JLabel createLogoLabel(int sizePx) {
        File f = new File(LOGO_PATH);
        if (!f.exists()) {
            JLabel fallback = new JLabel("TUKE");
            fallback.setHorizontalAlignment(SwingConstants.CENTER);
            fallback.setForeground(GREEN);
            fallback.setFont(new Font("Consolas", Font.BOLD, 36));
            return fallback;
        }

        ImageIcon icon = new ImageIcon(LOGO_PATH);
        Image scaled = icon.getImage().getScaledInstance(sizePx, sizePx, Image.SCALE_SMOOTH);

        JLabel logo = new JLabel(new ImageIcon(scaled));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setVerticalAlignment(SwingConstants.CENTER);
        return logo;
    }

    // Unified green button style (green background + black text + hover)
    private JButton createButton(String text, Color ignored, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setBackground(GREEN);
        btn.setForeground(Color.BLACK);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Consolas", Font.BOLD, 14));

        if (listener != null) btn.addActionListener(listener);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(GREEN_HOVER); }
            @Override public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(GREEN); }
        });

        return btn;
    }

    private void styleField(JTextField field) {
        field.setBackground(BG_MAIN);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
    }

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();
        bar.setBackground(BG_MAIN);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, GREEN));

        JMenu app = new JMenu("Application");
        app.setForeground(Color.WHITE);

        JMenuItem home = new JMenuItem("Home");
        home.addActionListener(e -> cardLayout.show(mainPanel, currentUser == null ? "LOGIN" : "CABINET"));

        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> logout());

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));

        app.add(home);
        app.add(logout);
        app.addSeparator();
        app.add(exit);

        JMenu help = new JMenu("Help");
        help.setForeground(Color.WHITE);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "TUKE Student Helper\nGreen-Black Edition", "About",
                        JOptionPane.INFORMATION_MESSAGE));

        help.add(about);

        bar.add(app);
        bar.add(help);
        return bar;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BG_PANEL);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30),
                BorderFactory.createLineBorder(GREEN, 1)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        form.add(createLogoLabel(110), gbc);

        JLabel title = new JLabel("TUKE Student Helper");
        title.setFont(new Font("Consolas", Font.BOLD, 24));
        title.setForeground(GREEN);
        gbc.gridy = 1;
        form.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 2; gbc.gridx = 0;
        JLabel u = new JLabel("Username:");
        u.setForeground(Color.WHITE);
        form.add(u, gbc);

        gbc.gridx = 1;
        loginUsernameField = new JTextField(20);
        styleField(loginUsernameField);
        form.add(loginUsernameField, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        JLabel p = new JLabel("Password:");
        p.setForeground(Color.WHITE);
        form.add(p, gbc);

        gbc.gridx = 1;
        loginPasswordField = new JPasswordField(20);
        styleField(loginPasswordField);
        form.add(loginPasswordField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttons = new JPanel();
        buttons.setBackground(BG_PANEL);
        buttons.add(createButton("Login", null, e -> login()));
        buttons.add(createButton("Register", null, e -> cardLayout.show(mainPanel, "REGISTER")));

        form.add(buttons, gbc);

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BG_PANEL);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30),
                BorderFactory.createLineBorder(GREEN, 1)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        form.add(createLogoLabel(110), gbc);

        JLabel title = new JLabel("Register");
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setForeground(GREEN);
        gbc.gridy = 1;
        form.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 2; gbc.gridx = 0;
        JLabel u = new JLabel("Username:");
        u.setForeground(Color.WHITE);
        form.add(u, gbc);
        gbc.gridx = 1;
        regUsernameField = new JTextField(20);
        styleField(regUsernameField);
        form.add(regUsernameField, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        JLabel p = new JLabel("Password:");
        p.setForeground(Color.WHITE);
        form.add(p, gbc);
        gbc.gridx = 1;
        regPasswordField = new JPasswordField(20);
        styleField(regPasswordField);
        form.add(regPasswordField, gbc);

        gbc.gridy = 4; gbc.gridx = 0;
        JLabel e = new JLabel("Email:");
        e.setForeground(Color.WHITE);
        form.add(e, gbc);
        gbc.gridx = 1;
        regEmailField = new JTextField(20);
        styleField(regEmailField);
        form.add(regEmailField, gbc);

        gbc.gridy = 5; gbc.gridx = 0;
        JLabel sid = new JLabel("Student ID:");
        sid.setForeground(Color.WHITE);
        form.add(sid, gbc);
        gbc.gridx = 1;
        regStudentIdField = new JTextField(20);
        styleField(regStudentIdField);
        form.add(regStudentIdField, gbc);

        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel btns = new JPanel();
        btns.setBackground(BG_PANEL);
        btns.add(createButton("Register", null, e2 -> register()));
        btns.add(createButton("Back", null, e2 -> cardLayout.show(mainPanel, "LOGIN")));

        form.add(btns, gbc);

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCabinetPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(GREEN);
        top.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.BLACK);

        JButton logout = createButton("Logout", null, e -> logout());

        top.add(welcomeLabel, BorderLayout.WEST);
        top.add(logout, BorderLayout.EAST);

        panel.add(top, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(BG_PANEL);
        tabs.setBorder(BorderFactory.createLineBorder(GREEN));
        tabs.setFont(new Font("Consolas", Font.PLAIN, 12));

        // Custom tab colors (selected=green/black, others=dark/white)
        tabs.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                              int x, int y, int w, int h, boolean isSelected) {
                g.setColor(isSelected ? GREEN : BG_PANEL);
                g.fillRect(x, y, w, h);
            }
            @Override
            protected void paintText(Graphics g, int tabPlacement, Font font,
                                     FontMetrics metrics, int tabIndex,
                                     String title, Rectangle textRect,
                                     boolean isSelected) {
                g.setFont(font);
                g.setColor(isSelected ? Color.BLACK : Color.WHITE);
                g.drawString(title, textRect.x, textRect.y + metrics.getAscent());
            }
        });

        tabs.add("Quick Links", createQuickLinksPanel());
        tabs.add("ToDo List", createTodoPanel());
        tabs.add("Statistics", createStatisticsPanel());
        tabs.add("Profile", createProfilePanel());

        panel.add(tabs, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createQuickLinksPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBackground(BG_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[][] links = {
                {"TUKE Email", "https://outlook.office365.com"},
                {"Student Portal", "https://student.tuke.sk"},
                {"Week Calculator", "https://lubomirdruga.github.io/jaky-je-boha-tyzden"},
                {"FBERG Portal", "https://fberg.tuke.sk"}
        };

        for (String[] l : links) {
            JButton btn = createButton(l[0], null, e -> openURL(l[1]));
            btn.setPreferredSize(new Dimension(0, 60));
            panel.add(btn);
        }
        return panel;
    }

    private JPanel createTodoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel input = new JPanel(new BorderLayout());
        input.setBackground(BG_PANEL);

        todoTextField = new JTextField();
        styleField(todoTextField);

        JButton add = createButton("Add Task", null, e -> addTodo());
        input.add(todoTextField, BorderLayout.CENTER);
        input.add(add, BorderLayout.EAST);

        String[] cols = {"Task", "Status"};
        todoModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(todoModel);
        table.setBackground(BG_MAIN);
        table.setForeground(Color.WHITE);
        table.setSelectionBackground(GREEN);
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(Color.GRAY);
        table.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(GREEN));
        scroll.getViewport().setBackground(BG_MAIN);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btns.setBackground(BG_PANEL);

        JButton complete = createButton("Mark Complete", null, e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                todoModel.setValueAt("Completed", row, 1);
                updateStatistics();
            }
        });

        JButton del = createButton("Delete", null, e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                todoModel.removeRow(row);
                updateStatistics();
            }
        });

        btns.add(complete);
        btns.add(del);

        panel.add(input, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btns, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Task Statistics", SwingConstants.CENTER);
        title.setFont(new Font("Consolas", Font.BOLD, 18));
        title.setForeground(GREEN);

        statsPanel = new TaskStatsPanel();
        statsPanel.setBackground(BG_MAIN);

        JButton refresh = createButton("Refresh", null, e -> updateStatistics());

        JPanel btn = new JPanel();
        btn.setBackground(BG_PANEL);
        btn.add(refresh);

        panel.add(title, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);

        updateStatistics();
        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_PANEL);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Profile");
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setForeground(GREEN);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        gbc.gridx = 0;
        JLabel u = new JLabel("Username:");
        u.setForeground(Color.WHITE);
        panel.add(u, gbc);

        gbc.gridx = 1;
        profileUsernameLabel = new JLabel();
        profileUsernameLabel.setForeground(Color.WHITE);
        panel.add(profileUsernameLabel, gbc);

        gbc.gridy++; gbc.gridx = 0;
        JLabel e = new JLabel("Email:");
        e.setForeground(Color.WHITE);
        panel.add(e, gbc);

        gbc.gridx = 1;
        profileEmailLabel = new JLabel();
        profileEmailLabel.setForeground(Color.WHITE);
        panel.add(profileEmailLabel, gbc);

        gbc.gridy++; gbc.gridx = 0;
        JLabel s = new JLabel("Student ID:");
        s.setForeground(Color.WHITE);
        panel.add(s, gbc);

        gbc.gridx = 1;
        profileStudentIdLabel = new JLabel();
        profileStudentIdLabel.setForeground(Color.WHITE);
        panel.add(profileStudentIdLabel, gbc);

        return panel;
    }

    // ===== Business logic =====

    private void login() {
        String user = loginUsernameField.getText().trim();
        String pass = new String(loginPasswordField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields!");
            return;
        }

        User u = users.get(user);
        if (u != null && u.password.equals(pass)) {
            currentUser = u;
            welcomeLabel.setText("Welcome, " + currentUser.username + "!");
            loadUserTodos();
            updateProfilePanel();
            updateStatistics();
            cardLayout.show(mainPanel, "CABINET");
            loginPasswordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }

    private void register() {
        String user = regUsernameField.getText().trim();
        String pass = new String(regPasswordField.getPassword());
        String email = regEmailField.getText().trim();
        String sid = regStudentIdField.getText().trim();

        if (user.isEmpty() || pass.isEmpty() || email.isEmpty() || sid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields!");
            return;
        }

        if (users.containsKey(user)) {
            JOptionPane.showMessageDialog(this, "User already exists!");
            return;
        }

        User u = new User(user, pass, email, sid);
        users.put(user, u);
        saveUsers();

        JOptionPane.showMessageDialog(this, "Registered!");

        regUsernameField.setText("");
        regPasswordField.setText("");
        regEmailField.setText("");
        regStudentIdField.setText("");

        cardLayout.show(mainPanel, "LOGIN");
    }

    private void logout() {
        if (currentUser != null) saveUserTodos();

        currentUser = null;
        if (todoModel != null) todoModel.setRowCount(0);

        loginUsernameField.setText("");
        loginPasswordField.setText("");

        cardLayout.show(mainPanel, "LOGIN");
    }

    private void addTodo() {
        String t = todoTextField.getText().trim();
        if (t.isEmpty()) return;

        todoModel.addRow(new Object[]{t, "Pending"});
        todoTextField.setText("");
        updateStatistics();
    }

    private void updateProfilePanel() {
        if (currentUser == null) return;
        profileUsernameLabel.setText(currentUser.username);
        profileEmailLabel.setText(currentUser.email);
        profileStudentIdLabel.setText(currentUser.studentId);
    }

    private void openURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot open link!");
        }
    }

    private void loadUserTodos() {
        todoModel.setRowCount(0);
        if (currentUser != null && currentUser.todos != null) {
            for (Todo t : currentUser.todos) {
                todoModel.addRow(new Object[]{t.task, t.status});
            }
        }
    }

    private void saveUserTodos() {
        if (currentUser == null) return;

        currentUser.todos = new ArrayList<>();
        for (int i = 0; i < todoModel.getRowCount(); i++) {
            String t = (String) todoModel.getValueAt(i, 0);
            String s = (String) todoModel.getValueAt(i, 1);
            currentUser.todos.add(new Todo(t, s));
        }
        saveUsers();
    }

    private void updateStatistics() {
        if (statsPanel == null || todoModel == null) return;

        int pending = 0, completed = 0;
        for (int i = 0; i < todoModel.getRowCount(); i++) {
            String s = (String) todoModel.getValueAt(i, 1);
            if ("Completed".equals(s)) completed++;
            else pending++;
        }

        statsPanel.setData(pending, completed);
        statsPanel.repaint();
    }

    // ===== Persistence (file-based, not a database) =====

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            users = (Map<String, User>) ois.readObject();
        } catch (Exception e) {
            users = new HashMap<>();
        }
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException ignored) {
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new StudentHelperApp().setVisible(true);
        });
    }
}

class User implements Serializable {
    private static final long serialVersionUID = 1L;

    String username, password, email, studentId;
    List<Todo> todos = new ArrayList<>();

    public User(String u, String p, String e, String id) {
        username = u;
        password = p;
        email = e;
        studentId = id;
    }
}

class Todo implements Serializable {
    private static final long serialVersionUID = 1L;

    String task, status;

    public Todo(String t, String s) {
        task = t;
        status = s;
    }
}

class TaskStatsPanel extends JPanel {

    private int pending = 0;
    private int completed = 0;

    public void setData(int p, int c) {
        pending = p;
        completed = c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth(), h = getHeight();
        int max = Math.max(1, Math.max(pending, completed));

        int barW = w / 6;
        int gap = barW;
        int baseY = h - 40;
        int maxH = h - 100;

        int x1 = w / 2 - barW - gap / 2;
        int x2 = w / 2 + gap / 2;

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.drawLine(40, baseY, w - 40, baseY);

        int h1 = (int) (maxH * (pending / (double) max));
        int h2 = (int) (maxH * (completed / (double) max));

        g2.setColor(new Color(200, 200, 0));
        g2.fillRect(x1, baseY - h1, barW, h1);
        g2.setColor(Color.WHITE);
        g2.drawString("Pending: " + pending, x1, baseY + 20);

        g2.setColor(new Color(0, 220, 80));
        g2.fillRect(x2, baseY - h2, barW, h2);
        g2.setColor(Color.WHITE);
        g2.drawString("Completed: " + completed, x2, baseY + 20);
    }
}
