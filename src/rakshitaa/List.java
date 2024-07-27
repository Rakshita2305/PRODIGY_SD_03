package rakshitaa;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class List extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JList<String> nameList;

    // Database connection parameters
    private String url = "jdbc:mysql://localhost:3306/rakshita";
    private String dbUsername = "root";
    private String dbPassword = "Rakshu2305";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                List frame = new List("");
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public List(String userpass) {
    	setSize(355,456);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 355, 456);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel spacerPanel = new JPanel();
        spacerPanel.setBackground(new Color(128, 255, 255));
        spacerPanel.setPreferredSize(new Dimension(getWidth(), 50)); // Adjust height as needed
        contentPane.add(spacerPanel, BorderLayout.NORTH);
        spacerPanel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("CONTACTS");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(95, 11, 156, 39);
        spacerPanel.add(lblNewLabel);
        
        JButton btnNewButton = new JButton("+");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AddContact addFrame = new AddContact(userpass);
                addFrame.setVisible(true);
                List.this.setVisible(false);
        	}
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 30));
        btnNewButton.setBounds(270, 1, 60, 50);
        spacerPanel.add(btnNewButton);
        
        // Fetch contact names from the database
        DefaultListModel<String> listModel = new DefaultListModel<>();
        fetchContactNames(userpass, listModel);

        // JList to display names
        nameList = new JList<>(listModel);
        nameList.setFont(new Font("Tahoma", Font.PLAIN, 18));
        nameList.setBackground(new Color(255, 255, 0));
        nameList.setForeground(new Color(0, 0, 0));
        nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        nameList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setText(value.toString());

                // Set an icon with the first letter of the name
                String firstLetter = value.toString().substring(0, 1).toUpperCase();
                ImageIcon icon = getIconForLetter(firstLetter);
                label.setIcon(icon);

                return label;
            }
        });

        // Add the JList to a JScrollPane (for scrolling)
        JScrollPane scrollPane = new JScrollPane(nameList);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Add a listener to handle selection events
        nameList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedName = nameList.getSelectedValue();
                // Display the selected name details (for example purposes)
                if(selectedName!=null)
                {
                	List.this.setVisible(false);
                	Details de = new Details(userpass,selectedName);
                	de.setVisible(true);
                }
            }
        });
    }

    private void fetchContactNames(String userpass, DefaultListModel<String> listModel) {
        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT name FROM " + userpass+ " ORDER BY name ASC"; // Adjust this query to match your database schema
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    String contactName = resultSet.getString("name");
                    listModel.addElement(contactName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private ImageIcon getIconForLetter(String letter) {
        // Placeholder logic to create an icon based on the first letter
        int size = 24;
        Image image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, size, size);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, size / 2));
        g.drawString(letter, size / 4, size * 3 / 4);
        g.dispose();
        return new ImageIcon(image);
    }
}
