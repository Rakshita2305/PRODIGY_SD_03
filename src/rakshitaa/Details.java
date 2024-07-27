package rakshitaa;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.*;

public class Details extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel iconLabel;

    private String url = "jdbc:mysql://localhost:3306/rakshita";
    private String dbUsername = "root";
    private String dbPassword = "Rakshu2305";
    private String userpass;
    private String SelectedName;
    private JLabel lbl3;
    private JLabel lbl4;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Details frame = new Details("", "");
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Details(String userpass, String SelectedName) {
        this.userpass = userpass;
        this.SelectedName = SelectedName;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 355, 455);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        iconLabel = new JLabel("");
        iconLabel.setBounds(115, 11, 115, 82);
        contentPane.add(iconLabel);

        JLabel lbl1 = new JLabel("Name:-");
        lbl1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lbl1.setBounds(35, 131, 80, 20);
        contentPane.add(lbl1);

        JLabel lbl2 = new JLabel("Phone No.:-");
        lbl2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lbl2.setBounds(35, 175, 80, 20);
        contentPane.add(lbl2);
        
        lbl3 = new JLabel("");
        lbl3.setBounds(125, 131, 161, 20);
        contentPane.add(lbl3);
        
        lbl4 = new JLabel("");
        lbl4.setBounds(125, 175, 161, 19);
        contentPane.add(lbl4);

        JButton btnNewButton = new JButton("CALL");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewButton.addActionListener(e -> JOptionPane.showMessageDialog(contentPane, "Calling......."));
        btnNewButton.setBounds(49, 306, 89, 23);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("BACK");
        btnNewButton_1.addActionListener(e -> {
            Details.this.setVisible(false);
            List lll = new List(userpass);
            lll.setVisible(true);
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewButton_1.setBounds(197, 306, 89, 23);
        contentPane.add(btnNewButton_1);

        fetchContactDetails(SelectedName);
    }

    private void fetchContactDetails(String Name) {
        if (Name == null || Name.isEmpty()) {
            System.out.println("No contact selected.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT name, contact FROM " + userpass + " WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, Name);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");
                        String phone = resultSet.getString("contact");

                        lbl3.setText(name);
                        lbl4.setText(phone);

                        String firstLetter = name.substring(0, 1).toUpperCase();
                        ImageIcon icon = getIconForLetter(firstLetter, 100);
                        iconLabel.setIcon(icon);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ImageIcon getIconForLetter(String letter, int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, size, size);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, size / 2));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (size - metrics.stringWidth(letter)) / 2;
        int y = ((size - metrics.getHeight()) / 2) + metrics.getAscent();

        g.drawString(letter, x, y);
        g.dispose();
        return new ImageIcon(image);
    }
}
