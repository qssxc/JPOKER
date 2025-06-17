package JPOKER;
 
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {

	private JFrame frame;
	private JTextField idTextField;
	private JTextField passTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("로그인");
		frame.setBounds(100, 100, 650, 438);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(213, 213, 213));
		panel.setBounds(124, 28, 387, 345);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnLogin = new JButton("로그인");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputId = idTextField.getText().trim();
				String inputPw = passTextField.getText().trim();

				if (inputId.equals("") || inputId.equals("아이디") || inputPw.equals("") || inputPw.equals("패스워드")) {
					JOptionPane.showMessageDialog(frame, "아이디와 비밀번호를 모두 입력해주세요.");
					return;
				}

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/JPOKER", "root", "0000");

					String sql = "SELECT * FROM player_info WHERE player_id = ? AND password = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, inputId);
					stmt.setString(2, inputPw);

					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						String playerName = rs.getString("name");
						String playerId = rs.getString("player_id");
						JOptionPane.showMessageDialog(frame, "로그인 성공! 환영합니다, " + rs.getString("name") + "님.");
						conn.close();
						
						frame.dispose();
						new Search(playerName, playerId);
						
						rs.close();
						stmt.close();
						conn.close();
						
					} else {
						JOptionPane.showMessageDialog(frame, "아이디 또는 비밀번호가 일치하지 않습니다.");
						rs.close();
						stmt.close();
						conn.close();
					}
					
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "DB 오류: " + ex.getMessage());
				}
			}
		});
		btnLogin.setBackground(new Color(192, 192, 192));
		btnLogin.setForeground(new Color(0, 0, 0));
		btnLogin.setBounds(102, 179, 183, 105);
		panel.add(btnLogin);
		btnLogin.setFont(new Font("D2Coding", Font.PLAIN, 22));
		
		idTextField = new JTextField();
		idTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passTextField.requestFocusInWindow();
			}
		});
		idTextField.setBounds(60, 69, 267, 45);
		panel.add(idTextField);
		idTextField.setForeground(new Color(192, 192, 192));
		idTextField.setText("아이디");
		idTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(idTextField.getText().equals("아이디")) {
					idTextField.setText("");
					idTextField.setForeground(new Color(0, 0, 0));
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(idTextField.getText().equals("")) {
					idTextField.setForeground(new Color(192, 192, 192));
					idTextField.setText("아이디");
				}
			}
		});
		idTextField.setFont(new Font("D2Coding", Font.PLAIN, 24));
		idTextField.setColumns(10);
		
		passTextField = new JTextField();
		passTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLogin.doClick();
			}
		});
		passTextField.setBounds(60, 124, 267, 45);
		panel.add(passTextField);
		passTextField.setForeground(new Color(192, 192, 192));
		passTextField.setText("패스워드");
		passTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(passTextField.getText().equals("패스워드")) {
					passTextField.setText("");
					passTextField.setForeground(new Color(0, 0, 0));
				}
			
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(passTextField.getText().equals("")) {
					passTextField.setForeground(new Color(192, 192, 192));
					passTextField.setText("패스워드");
				}
			}
		});
		passTextField.setFont(new Font("D2Coding", Font.PLAIN, 24));
		passTextField.setColumns(10);
		
		JButton btnJoin = new JButton("회원가입");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Join.main(null);
			}
		});
		btnJoin.setBackground(new Color(192, 192, 192));
		btnJoin.setBounds(102, 298, 183, 37);
		panel.add(btnJoin);
		btnJoin.setFont(new Font("D2Coding", Font.PLAIN, 24));
		
		JLabel lblNewLabel = new JLabel("JPOKER");
		lblNewLabel.setFont(new Font("D2Coding", Font.PLAIN, 25));
		lblNewLabel.setBounds(146, 10, 95, 37);
		panel.add(lblNewLabel);
	}
}
