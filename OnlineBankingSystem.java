import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OnlineBankingSystem extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;
    JTextField usernameField;
    JPasswordField passwordField;
    JLabel welcomeLabel, balanceLabel;
    double balance = 5000.0;
    ArrayList<String> transactions = new ArrayList<>();

    public OnlineBankingSystem() {
        setTitle("Online Banking System");
        setSize(550, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(loginPanel(), "Login");
        mainPanel.add(dashboardPanel(), "Dashboard");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");

        setVisible(true);
    }

    // 🔐 Login Panel
    private JPanel loginPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(225, 245, 254));

        JLabel title = new JLabel("Bank Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(170, 30, 200, 30);
        panel.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(100, 90, 100, 25);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 90, 200, 25);
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(100, 130, 100, 25);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 130, 200, 25);
        panel.add(passwordField);

        JButton okBtn = new JButton("OK");
        okBtn.setBounds(200, 190, 100, 30);
        okBtn.setBackground(new Color(30, 136, 229));
        okBtn.setForeground(Color.WHITE);

        okBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            if (user.equals("admin") && pass.equals("1234")) {
                welcomeLabel.setText("Welcome, " + user + "!");
                balanceLabel.setText("Balance: ₹" + balance);
                cardLayout.show(mainPanel, "Dashboard");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        });

        panel.add(okBtn);

        return panel;
    }

    // 🏦 Dashboard Panel
    private JPanel dashboardPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(232, 245, 233));

        welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBounds(150, 20, 250, 30);
        panel.add(welcomeLabel);

        balanceLabel = new JLabel("Balance: ₹" + balance);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        balanceLabel.setBounds(180, 60, 200, 25);
        panel.add(balanceLabel);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(100, 110, 120, 30);
        depositBtn.addActionListener(e -> depositMoney());
        panel.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(280, 110, 120, 30);
        withdrawBtn.addActionListener(e -> withdrawMoney());
        panel.add(withdrawBtn);

        JButton historyBtn = new JButton("Transaction History");
        historyBtn.setBounds(160, 160, 200, 30);
        historyBtn.addActionListener(e -> showTransactionHistory());
        panel.add(historyBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(200, 220, 120, 30);
        logoutBtn.setBackground(Color.LIGHT_GRAY);
        logoutBtn.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            cardLayout.show(mainPanel, "Login");
        });
        panel.add(logoutBtn);

        return panel;
    }

    // 💰 Deposit Function
    private void depositMoney() {
        String input = JOptionPane.showInputDialog(this, "Enter deposit amount:");
        if (input != null) {
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0) {
                    balance += amount;
                    transactions.add("Deposited ₹" + amount);
                    balanceLabel.setText("Balance: ₹" + balance);
                    JOptionPane.showMessageDialog(this, "₹" + amount + " deposited successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Enter a valid positive amount.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input.");
            }
        }
    }

    // 💸 Withdraw Function
    private void withdrawMoney() {
        String input = JOptionPane.showInputDialog(this, "Enter withdrawal amount:");
        if (input != null) {
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0 && amount <= balance) {
                    balance -= amount;
                    transactions.add("Withdrew ₹" + amount);
                    balanceLabel.setText("Balance: ₹" + balance);
                    JOptionPane.showMessageDialog(this, "₹" + amount + " withdrawn successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid amount or insufficient balance.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input.");
            }
        }
    }

    // 📄 Transaction History
    private void showTransactionHistory() {
        if (transactions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transactions yet.");
            return;
        }

        JTextArea area = new JTextArea();
        for (String t : transactions) {
            area.append(t + "\n");
        }
        area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        JOptionPane.showMessageDialog(this, scrollPane, "Transaction History", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OnlineBankingSystem::new);
    }
}
