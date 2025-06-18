package JPOKER;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameTable {
	private static GameTable singleton;
	private Dealer dealer = new Dealer();

	private JFrame frame;
	private String[] p1deck;
	private String[] p2deck;
	private JLabel p1d1;
	private JLabel p1d2;
	private JLabel p2d1;
	private JLabel p2d2;
	private ImageIcon igp1d1;
	private ImageIcon igp1d2;
	private ImageIcon igp2d1;
	private ImageIcon igp2d2;
	private JPanel mainpanel;
	private JButton btnDeck;
	private JButton btnBetting;
	private JButton btnFold;
	private JLabel lblNowMoeny;
	private int money = 10000;
	private String gamePhase = "gamestart";
	private JLabel lblWL;
	private JTextField txfBetting;
	private int totalgame;
	private int totalwin;
	
	ArrayList<Location> locations = new ArrayList<>();

	private String insertSql;

	private Connection conn;

	private String player_id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameTable window = new GameTable(null);
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
	public GameTable(String player_id) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JPOKER", "root", "1234");

			// 검색
			String sql = "SELECT * FROM game_info WHERE Player_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, player_id);
			ResultSet rs = pstmt.executeQuery();

			this.player_id = player_id;

			// 결과
			if (rs.next()) {
				money = Integer.parseInt(rs.getString("Money"));
				totalwin = rs.getInt("totalwin");
				totalgame = rs.getInt("totalgame");
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		initialize();
		frame.setVisible(true);
	}
	public static GameTable getInstance(String playerid) {
        if (singleton == null) {
        	singleton = new GameTable(playerid);
        }
        
        return singleton;
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 460);
		igp1d1 = null;
		igp1d2 = null;
		igp2d1 = null;
		igp2d2 = null;
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				for (Location loc : locations) {
					setlocation(loc.getJcomponent(), loc.getWidth(), loc.getHeight(), loc.getX(), loc.getY());
				}
			}
		});

		mainpanel = new JPanel();
		frame.getContentPane().add(mainpanel);
		mainpanel.setLayout(null);

		p1d1 = new JLabel("");
		p1d1.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				switch (gamePhase) {
				case "gamestart":
					setdeck(0);
					break;
				case "gameend":
					setdeck(1);
					setdeck(2);
					setdeck(3);
					setdeck(4);
					break;
				case "Betting":
					setdeck(0);
					setdeck(1);
					setdeck(3);
					setdeck(4);
					break;
				}
			}
		});
		p1d1.setBounds(10, 10, 150, 200);
		initlocation(p1d1);
		mainpanel.add(p1d1);
		p1d1.setIcon(getimage(igp1d1));

		p2d1 = new JLabel("");
		p2d1.setBounds(10, 220, 150, 200);
		initlocation(p2d1);
		mainpanel.add(p2d1);
		p2d1.setIcon(getimage(igp2d1));

		p1d2 = new JLabel("");
		p1d2.setBounds(170, 10, 150, 200);
		initlocation(p1d2);
		mainpanel.add(p1d2);
		p1d2.setIcon(getimage(igp1d2));

		p2d2 = new JLabel("");
		p2d2.setBounds(170, 220, 150, 200);
		initlocation(p2d2);
		mainpanel.add(p2d2);
		p2d2.setIcon(getimage(igp2d2));

		JLabel lblNowBetting = new JLabel("현재 베팅 : ");
		lblNowBetting.setBounds(486, 171, 100, 20);
		initlocation(lblNowBetting);
		mainpanel.add(lblNowBetting);
		lblNowBetting.setAlignmentX(Component.CENTER_ALIGNMENT);

		lblNowMoeny = new JLabel("현재 금액");
		lblNowMoeny.setBounds(486, 146, 100, 20);
		initlocation(lblNowMoeny);
		initlocation(lblNowMoeny);
		mainpanel.add(lblNowMoeny);

		lblWL = new JLabel("");
		lblWL.setBounds(484, 124, 50, 20);
		initlocation(lblWL);
		mainpanel.add(lblWL);
		lblWL.setVisible(false);

		txfBetting = new JTextField();
		txfBetting.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txfBetting.getText().equals("베팅금액") || txfBetting.getText().equals("숫자만 입력해주세요")) {
					txfBetting.setText("");
					txfBetting.setForeground(new Color(0, 0, 0));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txfBetting.getText().equals("")) {
					txfBetting.setForeground(new Color(192, 192, 192));
					txfBetting.setText("베팅금액");
				}
			}
		});
		txfBetting.setBounds(486, 196, 100, 20);
		initlocation(txfBetting);
		initlocation(txfBetting);
		mainpanel.add(txfBetting);
		txfBetting.setColumns(10);

		btnDeck = new JButton("패섞기");
		btnDeck.setBounds(486, 227, 100, 40);
		initlocation(btnDeck);
		mainpanel.add(btnDeck);
		btnDeck.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnDeck.setVisible(true);
		btnDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePhase = "Betting";
				totalgame += 1;
				if (money < 100) {
					JOptionPane.showMessageDialog(frame, "기본금 지급");
					money = 10000;
				}
				lblNowMoeny.setText("현재 금액 : " + money);
				lblNowBetting.setText("현재 베팅 : " + 0);
				p1deck = dealer.deck();
				p2deck = dealer.deck();
				setdeck(0);
				setdeck(1);
				setdeck(3);
				setdeck(4);
				btnDeck.setVisible(false);
				btnBetting.setVisible(true);
				btnFold.setVisible(true);
			}
		});

		btnBetting = new JButton("베팅");
		btnBetting.setBounds(486, 277, 100, 40);
		initlocation(btnBetting);
		mainpanel.add(btnBetting);
		btnBetting.setVisible(false);
		btnBetting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePhase = "gameend";
				if (!txfBetting.getText().matches("[0-9]+"))
					JOptionPane.showMessageDialog(frame, "정수만 입력할수있습니다.");

				else if (Integer.parseInt(txfBetting.getText()) < 100)
					JOptionPane.showMessageDialog(frame, "최소베팅액은 100입니다");

				else if (Integer.parseInt(txfBetting.getText()) > money)
					JOptionPane.showMessageDialog(frame, "보유금액보다 많이베팅할 수 없습니다");

				else {
					boolean call;
					int nowbetting = Integer.parseInt(txfBetting.getText());

					money -= nowbetting;
					lblNowMoeny.setText("현재 금액 : " + money);
					lblNowBetting.setText("현재 베팅 : " + nowbetting);
					setdeck(2);

					dealer.Batting(nowbetting);

					call = dealer.guessPower(p1deck[0], p1deck[1], p2deck[0]);
					if (dealer.open(call, p1deck[0], p1deck[1], p2deck[0], p2deck[1])) {
						money += nowbetting * 2;
						lblWL.setText("승리");
						totalwin += 1;
					} else
						lblWL.setText("패배");
					lblWL.setVisible(true);
					lblNowMoeny.setText("현재 금액 : " + money);
					saveGameData();

					btnDeck.setVisible(true);
					btnBetting.setVisible(false);
					btnFold.setVisible(false);
				}
			}
		});
		btnBetting.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnFold = new JButton("폴드");
		btnFold.setBounds(486, 322, 100, 40);
		initlocation(btnFold);
		mainpanel.add(btnFold);
		btnFold.setVisible(false);
		btnFold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePhase = "gameend";
				setdeck(2);
				lblWL.setText("패배");
				money -= 100;
				lblNowMoeny.setText("현재 금액 : " + money);
				saveGameData();

				btnDeck.setVisible(true);
				btnBetting.setVisible(false);
				btnFold.setVisible(false);
			}
		});
		btnFold.setAlignmentX(Component.CENTER_ALIGNMENT);

		frame.setVisible(true);
	}

	public ImageIcon getimage(ImageIcon s) {
		s = new ImageIcon(GameTable.class.getResource("/card/Card-back.png"));
		Image cg = s.getImage();
		cg = cg.getScaledInstance(p1d1.getWidth(), p1d1.getHeight(), Image.SCALE_SMOOTH);
		s = new ImageIcon(cg);
		return s;
	}

	public ImageIcon getimage(ImageIcon s, String c) {
		s = new ImageIcon(GameTable.class.getResource("/card/" + c + ".png"));
		Image cg = s.getImage();
		cg = cg.getScaledInstance(p1d1.getWidth(), p1d1.getHeight(), Image.SCALE_SMOOTH);
		s = new ImageIcon(cg);
		return s;
	}

	public void setdeck(int pd) {
		switch (pd) {
		case 1:
			p1d1.setIcon(getimage(igp1d1, (p1deck[0] + "C")));
			break;
		case 2:
			p1d2.setIcon(getimage(igp1d2, (p1deck[1] + "H")));
			break;
		case 3:
			p2d1.setIcon(getimage(igp2d1, (p2deck[0] + "S")));
			break;
		case 4:
			p2d2.setIcon(getimage(igp2d2, (p2deck[1] + "D")));
			break;
		default:
			p1d1.setIcon(getimage(igp1d1));
			p1d2.setIcon(getimage(igp1d1));
			p2d1.setIcon(getimage(igp1d1));
			p2d2.setIcon(getimage(igp1d1));
			break;
		}
	}

	public void setlocation(JComponent jcomponent, float width, float heigth, float x, float y) {
		int buttonWidth = (int) (frame.getWidth() * width);
		int buttonHeight = (int) (frame.getHeight() * heigth);
		jcomponent.setSize(buttonWidth, buttonHeight);

		int x1 = (int) (frame.getWidth() * x);
		int y2 = (int) (frame.getHeight() * y);
		jcomponent.setLocation(x1, y2);
	}

	public void initlocation(JComponent jcomponent) {
		float initwidth = (float) jcomponent.getWidth() / frame.getWidth();
		float initheigth = (float) jcomponent.getHeight() / frame.getHeight();
		float initx = (float) jcomponent.getX() / frame.getWidth();
		float inity = (float) jcomponent.getY() / frame.getHeight();
		locations.add(new Location(jcomponent, initwidth, initheigth, initx, inity));

	}

	public void saveGameData() {
		try {
			insertSql = "UPDATE playerinfo SET totalgame = ?, totalwin = ?, money = ? WHERE id = ?";
			PreparedStatement insertStmt1 = conn.prepareStatement(insertSql);
			insertStmt1.setLong(1, 0);
			insertStmt1.setLong(2, 0);
			insertStmt1.setLong(3, 0);
			insertStmt1.setString(4, player_id);
			insertStmt1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
