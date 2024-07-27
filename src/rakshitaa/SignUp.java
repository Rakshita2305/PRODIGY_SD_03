package rakshitaa;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.sql.*;

public class SignUp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt2;
	private JTextField txt3;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignUp() {
		
		String url = "jdbc:mysql://localhost:3306/rakshita";
		String usernme = "root";
		String pass = "Rakshu2305";
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 355, 455);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SIGN UP DETAILS");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblNewLabel.setBounds(46, 0, 255, 44);
		contentPane.add(lblNewLabel);
		
		txt2 = new JTextField();
		txt2.setBounds(69, 55, 189, 27);
		contentPane.add(txt2);
		txt2.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("EMAIL");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(70, 36, 87, 23);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("USERNAME");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(69, 86, 73, 27);
		contentPane.add(lblNewLabel_2);
		
		txt3 = new JTextField();
		txt3.setBounds(69, 107, 189, 27);
		contentPane.add(txt3);
		txt3.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("SET PASSWORD");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(69, 139, 114, 27);
		contentPane.add(lblNewLabel_3);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(69, 160, 189, 27);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(69, 213, 189, 25);
		contentPane.add(passwordField_1);
		
		JLabel lbl5 = new JLabel("");
		lbl5.setForeground(new Color(221, 0, 17));
		lbl5.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl5.setHorizontalAlignment(SwingConstants.CENTER);
		lbl5.setBounds(26, 290, 183, 27);
		contentPane.add(lbl5);
		
		JButton logg = new JButton("LOG IN");
		logg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp.this.setVisible(false);
				LogIn login = new LogIn();
				login.setVisible(true);
			}
		});
		logg.setFont(new Font("Tahoma", Font.BOLD, 15));
		logg.setBounds(213, 292, 99, 23);
		contentPane.add(logg);
		
		logg.setVisible(false);
		
		JButton btnNewButton = new JButton("SIGN UP");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				char[] a = passwordField.getPassword();
				char[] b = passwordField_1.getPassword();
				
				String passwordF = new String(a);
				String passwordF1 = new String(b);
				
				if(passwordF.equals(passwordF1))
				{
					try {
			            // Load the MySQL JDBC driver
			            Class.forName("com.mysql.cj.jdbc.Driver");
			        } catch (ClassNotFoundException f) {
			            f.printStackTrace();
			            return;
			        } 
					
					try(Connection con = DriverManager.getConnection(url,usernme,pass))
					{
						if(con!=null)
						{
							System.out.println("Database connected");
						}
						String user = txt3.getText();
						char[] pass = passwordField.getPassword();
						String passw = new String(pass);
						String userpass = user+passw;
						String query = "create table "+userpass+"( name varchar(100) , contact varchar(10))";
						
		                DatabaseMetaData dbm = con.getMetaData();
	
						try(ResultSet rs = dbm.getTables(null,null,userpass,null))
						{
							if(rs.next())
							{
								logg.setVisible(true);
								lbl5.setText("You are already a user");
							}
							else
							{
								try(Statement stmt = con.createStatement())
								{
									stmt.executeUpdate(query);
									lbl5.setText("You can login now->");
									logg.setVisible(true);
								}
							}
						}
					}
					catch(SQLException f)
					{
						
					}
				}
				else
				{
					lbl5.setText("Password Doen't Match");
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		btnNewButton.setBounds(95, 249, 125, 31);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_4 = new JLabel("REPEAT PASSWORD");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(69, 190, 125, 27);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Powered By");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(95, 341, 125, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("KOOGLE");
		lblNewLabel_6.setForeground(new Color(128, 0, 255));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_6.setBounds(95, 354, 125, 14);
		contentPane.add(lblNewLabel_6);
		
	}
}
