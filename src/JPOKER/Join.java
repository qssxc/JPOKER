package JPOKER;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class Join {

	private JFrame frmJoin;
	private JTextField nameText;
	private JLabel lblNewLabel;
	private JLabel idLabel;
	private JTextField idText;
	private JLabel passLabel;
	private JTextField passText;
	private JLabel mailLabel;
	private JTextField mailText;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Join window = new Join();
					window.frmJoin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Join() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJoin = new JFrame();
		frmJoin.setTitle("Join");
		frmJoin.setBounds(100, 100, 582, 551);
		frmJoin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJoin.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(228, 228, 228));
		panel.setBounds(75, 61, 418, 391);
		frmJoin.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel nameLabel = new JLabel("이름");
		nameLabel.setBounds(56, 99, 108, 28);
		nameLabel.setFont(new Font("D2Coding", Font.PLAIN, 22));
		panel.add(nameLabel);

		nameText = new JTextField();
		nameText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idText.requestFocusInWindow();
			}
		});
		nameText.setFont(new Font("D2Coding", Font.PLAIN, 17));
		nameText.setBounds(165, 99, 187, 28);
		panel.add(nameText);
		nameText.setColumns(10);

		lblNewLabel = new JLabel("회원가입 폼");
		lblNewLabel.setFont(new Font("D2Coding", Font.PLAIN, 24));
		lblNewLabel.setBounds(140, 10, 137, 46);
		panel.add(lblNewLabel);

		JButton btnJoin = new JButton("회원가입");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
				String id = idText.getText();
				String pw = passText.getText();
				String email = mailText.getText();

				if (name.isEmpty() || id.isEmpty() || pw.isEmpty() || email.isEmpty()) {
					JOptionPane.showMessageDialog(frmJoin, "모든 항목을 입력해주세요.");
				}  try {
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(
		                "jdbc:mysql://localhost:3306/JPOKER", "root", "1234");

		            // 중복 아이디 체크
		            String checkSql = "SELECT COUNT(*) FROM player_info WHERE player_id = ?";
		            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
		            checkStmt.setString(1, id);
		            ResultSet rs = checkStmt.executeQuery();
		            rs.next();
		            if (rs.getInt(1) > 0) {
		                JOptionPane.showMessageDialog(frmJoin, "이미 사용 중인 아이디입니다.");
		                conn.close();
		                return;
		            }

		            String insertSql = "INSERT INTO player_info (player_id, password, name, email) VALUES (?, ?, ?, ?)";
		            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
		            insertStmt.setString(1, id);
		            insertStmt.setString(2, pw);
		            insertStmt.setString(3, name);
		            insertStmt.setString(4, email);
		            insertStmt.executeUpdate();

		            JOptionPane.showMessageDialog(frmJoin, "회원가입이 완료되었습니다!\n\n이름: " + name +
		                    "\nID: " + id +
		                    "\n이메일: " + email, "회원가입 성공", JOptionPane.INFORMATION_MESSAGE);
		            
		            insertSql = "INSERT INTO game_info (Player_id, TotalGame, TotalWin, Money) VALUES (?, ?, ?, ?)";
		            PreparedStatement insertStmt1 = conn.prepareStatement(insertSql);
		            insertStmt1.setString(1, id);
		            insertStmt1.setLong(2, 0);
		            insertStmt1.setLong(3, 0);
		            insertStmt1.setLong(4, 0);
		            insertStmt1.executeUpdate();

		            conn.close();
		            frmJoin.dispose();
		            Main.main(null);

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(frmJoin, "DB 오류: " + ex.getMessage());
		        }
		    }
		});
		
		btnJoin.setFont(new Font("D2Coding", Font.PLAIN, 23));
		btnJoin.setBounds(56, 346, 159, 35);
		panel.add(btnJoin);

		JButton btnCancle = new JButton("취소");
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmJoin.dispose();
			}
		});
		btnCancle.setFont(new Font("D2Coding", Font.PLAIN, 23));
		btnCancle.setBounds(268, 346, 84, 35);
		panel.add(btnCancle);

		idLabel = new JLabel("아이디");
		idLabel.setFont(new Font("D2Coding", Font.PLAIN, 22));
		idLabel.setBounds(56, 160, 108, 28);
		panel.add(idLabel);

		idText = new JTextField();
		idText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passText.requestFocusInWindow();
			}
		});
		idText.setFont(new Font("D2Coding", Font.PLAIN, 17));
		idText.setColumns(10);
		idText.setBounds(165, 160, 187, 28);
		panel.add(idText);

		passLabel = new JLabel("비밀번호");
		passLabel.setFont(new Font("D2Coding", Font.PLAIN, 22));
		passLabel.setBounds(56, 219, 108, 28);
		panel.add(passLabel);

		passText = new JTextField();
		passText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mailText.requestFocusInWindow();
			}
		});
		passText.setFont(new Font("D2Coding", Font.PLAIN, 17));
		passText.setColumns(10);
		passText.setBounds(165, 219, 187, 28);
		panel.add(passText);

		mailLabel = new JLabel("이메일");
		mailLabel.setFont(new Font("D2Coding", Font.PLAIN, 22));
		mailLabel.setBounds(56, 277, 108, 28);
		panel.add(mailLabel);

		mailText = new JTextField();
		mailText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnJoin.doClick();
			}
		});
		mailText.setFont(new Font("D2Coding", Font.PLAIN, 17));
		mailText.setColumns(10);
		mailText.setBounds(165, 277, 187, 28);
		panel.add(mailText);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\JAVA_WS_Aban\\LYN\\images\\poker1.jpg"));
		lblNewLabel_1.setBounds(7, 0, 554, 513);
		frmJoin.getContentPane().add(lblNewLabel_1);
	}
}
