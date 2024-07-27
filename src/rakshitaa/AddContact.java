package rakshitaa;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddContact extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;

    // Database connection parameters
    private String url = "jdbc:mysql://localhost:3306/rakshita";
    private String usernme = "root";
    private String pass = "Rakshu2305";
    private String userpass;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AddContact frame = new AddContact("yourTableName"); // Replace with your actual table name
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AddContact(String userpass) {
        this.userpass = userpass;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 355, 455);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("NEW CONTACT");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(86, 11, 167, 37);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Name");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(74, 81, 74, 20);
        contentPane.add(lblNewLabel_1);

        textField = new JTextField();
        textField.setBounds(74, 103, 195, 25);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Ph. NO");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_2.setBounds(74, 139, 74, 20);
        contentPane.add(lblNewLabel_2);
        
        JLabel lbl3 = new JLabel("");
        lbl3.setHorizontalAlignment(SwingConstants.CENTER);
        lbl3.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lbl3.setBounds(74, 317, 195, 20);
        contentPane.add(lbl3);

        textField_1 = new JTextField();
        textField_1.setBounds(74, 161, 195, 25);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        JButton btnNewButton = new JButton("SAVE");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = textField.getText().trim();
                String contactNumber = textField_1.getText().trim();

                if (name.isEmpty() || contactNumber.isEmpty()) {
                    lbl3.setText("Fields cannot be empty.");
                    return;
                }

                if (contactExists(name, contactNumber)) {
                    lbl3.setText("Contact already exists.");
                } else {
                    addContact(name, contactNumber);
                    lbl3.setText("Contact added successfully.");
                }
            }
        });

        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnNewButton.setBounds(112, 231, 113, 37);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Show List");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AddContact.this.setVisible(false);
        		List lll = new List(userpass);
        		lll.setVisible(true);
        	}
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewButton_1.setBounds(112, 364, 113, 23);
        contentPane.add(btnNewButton_1);
        
    }

    private boolean contactExists(String name, String contactNumber) {
        try (Connection con = DriverManager.getConnection(url, usernme, pass)) {
            // Adjust the query according to your table schema
            String query = "SELECT COUNT(*) FROM " + userpass + " WHERE name = ? OR contact = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, contactNumber);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addContact(String name, String contactNumber) {
        try (Connection con = DriverManager.getConnection(url, usernme, pass)) {
            // Adjust the query according to your table schema
            String query = "INSERT INTO " + userpass + " (name, contact) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, contactNumber);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
