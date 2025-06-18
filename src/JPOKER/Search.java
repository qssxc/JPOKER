package JPOKER;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class Search {

	private JFrame frmSearch;
	private JTextField idText;
	private JButton btnSearch;
	private JTextArea resultArea;
	private String playerName;
	private String playerId;
	
	public Search() {
		this(null, null);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search window = new Search();
					window.frmSearch.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Search(String playerName, String playerId) {
		this.playerName = playerName;
		this.playerId = playerId;
		initialize();
		frmSearch.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSearch = new JFrame();
		frmSearch.setTitle("전적검색");
		frmSearch.setBounds(100, 100, 760, 550);
		frmSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSearch.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(228, 228, 228));
		panel.setBounds(106, 56, 534, 401);
		frmSearch.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel idLabel = new JLabel("검색할 ID:");
		idLabel.setBounds(12, 82, 100, 24);
		idLabel.setFont(new Font("D2Coding", Font.PLAIN, 20));
		panel.add(idLabel);

		idText = new JTextField();
		idText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSearch.doClick();
			}
		});
		idText.setBounds(116, 79, 310, 30);
		idText.setFont(new Font("D2Coding", Font.PLAIN, 20));
		panel.add(idText);
		idText.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String playerId = idText.getText().trim();

				if (playerId.isEmpty()) {
					resultArea.setText("검색할 ID를 입력해주세요.");
					return;
				}

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");

					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JPOKER", "root", "0000");

					// 검색
					String sql = "SELECT * FROM player_info WHERE player_id = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, playerId);
					ResultSet rs = pstmt.executeQuery();

					// 결과
					if (rs.next()) {
						String name = rs.getString("name");
						String email = rs.getString("email");

						resultArea.setText("전적 검색 결과:\n\n" + "ID: " + playerId + "\n" + "이름: " + name + "\n" + "이메일: "
								+ email);
					} else {
						resultArea.setText("해당 ID는 존재하지 않습니다.");
					}

					rs.close();
					pstmt.close();
					conn.close();

				} catch (Exception ex) {
					ex.printStackTrace();
					resultArea.setText("오류가 발생했습니다: " + ex.getMessage());
				}
			}
		});
		btnSearch.setBounds(438, 78, 73, 33);
		btnSearch.setFont(new Font("D2Coding", Font.PLAIN, 18));
		panel.add(btnSearch);

		JLabel searchLabel = new JLabel("전적 검색창");
		searchLabel.setFont(new Font("D2Coding", Font.BOLD, 24));
		searchLabel.setBounds(198, 10, 137, 58);
		panel.add(searchLabel);

		resultArea = new JTextArea();
		resultArea.setFont(new Font("D2Coding", Font.PLAIN, 20));
		resultArea.setBounds(12, 119, 507, 272);
		panel.add(resultArea);

		JButton btnStart = new JButton("게임 시작");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameTable.getInstance(playerId);
			}
		});
		btnStart.setBackground(new Color(255, 0, 0));
		btnStart.setForeground(new Color(255, 255, 255));
		btnStart.setFont(new Font("D2Coding", Font.PLAIN, 24));
		btnStart.setBounds(570, 467, 164, 36);
		frmSearch.getContentPane().add(btnStart);

		JButton btnBack = new JButton("돌아가기");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSearch.dispose();
				new Main();
			}
		});
		btnBack.setForeground(new Color(0, 0, 0));
		btnBack.setFont(new Font("D2Coding", Font.PLAIN, 24));
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setBounds(12, 467, 164, 36);
		frmSearch.getContentPane().add(btnBack);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblNewLabel.setBounds(106, 10, 600, 36);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		String greeting = (playerName != null && !playerName.isEmpty())
			? playerName + "님, JPOKER에 오신 걸 환영합니다!"
			: "JPOKER에 오신 걸 환영합니다!";
		lblNewLabel.setText(greeting);

		frmSearch.getContentPane().add(lblNewLabel);
	}
}
