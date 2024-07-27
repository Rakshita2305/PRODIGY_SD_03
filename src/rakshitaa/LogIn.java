package rakshitaa;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogIn extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt1;
	private JLabel lbl2;
	private JButton btnNewButton;
	private JPasswordField passf;
	private JLabel lbl3;
	private JButton signU;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
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
	public LogIn() {
		
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
		
		JLabel lblNewLabel = new JLabel("ENTER DETAILS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(50, 11, 228, 43);
		contentPane.add(lblNewLabel);
		
		txt1 = new JTextField();
		txt1.setBounds(80, 75, 181, 36);
		contentPane.add(txt1);
		txt1.setColumns(10);
		
		JLabel lbl1 = new JLabel("USERNAME");
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl1.setBounds(80, 53, 76, 22);
		contentPane.add(lbl1);
		
		lbl2 = new JLabel("PASSWORD");
		lbl2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl2.setBounds(80, 114, 76, 22);
		contentPane.add(lbl2);
		
		btnNewButton = new JButton("LOG IN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
					String user = txt1.getText();
					char[] passy = passf.getPassword();
					String pass = new String(passy);
					String userpass = user+pass;
					String query = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'rakshita' AND TABLE_NAME = ?";
					
					try(PreparedStatement ps = con.prepareStatement(query))
					{
						ps.setString(1, userpass);

						try(ResultSet rs = ps.executeQuery())
						{
							if(rs.next() && rs.getInt(1)>0)
							{
								LogIn.this.setVisible(false);
								List ls = new List(userpass);
								ls.setVisible(true);
							}
							else
							{
								lbl3.setText("You Don't Have an Account -> ");
								signU.setVisible(true);
							}
						}
					}
				}
				catch(SQLException a)
				{
					
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		btnNewButton.setBounds(109, 208, 125, 43);
		contentPane.add(btnNewButton);
		
		passf = new JPasswordField();
		passf.setBounds(80, 133, 181, 36);
		contentPane.add(passf);
		
		lbl3 = new JLabel("");
		lbl3.setBounds(39, 267, 195, 22);
		contentPane.add(lbl3);
		
		signU = new JButton("SIGN UP");
		signU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogIn.this.setVisible(false);
				SignUp sig = new SignUp();
				sig.setVisible(true);
			}
		});
		signU.setFont(new Font("Tahoma", Font.PLAIN, 10));
		signU.setBounds(244, 267, 76, 22);
		contentPane.add(signU);
		
		lblNewLabel_1 = new JLabel("Powered By");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(109, 330, 125, 14);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("KOOGLE");
		lblNewLabel_2.setBackground(new Color(240, 240, 240));
		lblNewLabel_2.setForeground(new Color(128, 0, 255));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(109, 343, 125, 22);
		contentPane.add(lblNewLabel_2);
		signU.setVisible(false);
	}
}
