package JPOKER;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class GameTable {
	
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameTable window = new GameTable();
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
	public GameTable() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 747, 468);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnDeck = new JButton("패섞기");
		btnDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p1deck = dealer.deck();
				p2deck = dealer.deck();
				p1d1.setIcon(getimage(igp1d1, (p1deck[0] +"C")));
				p1d2.setIcon(getimage(igp1d2, (p1deck[1] +"H")));
				p2d1.setIcon(getimage(igp2d1, (p2deck[0] +"S")));
				p2d2.setIcon(getimage(igp2d2, (p2deck[1] +"D")));
			}
		});
		btnDeck.setBounds(417, 341, 93, 23);
		frame.getContentPane().add(btnDeck);
		
		p1d1 = new JLabel("player1hand1");
		igp1d1 = null;
		p1d1.setIcon(getimage(igp1d1));
		p1d1.setBounds(50, 10, 150, 200);
		
		frame.getContentPane().add(p1d1);
		
		p1d2 = new JLabel("player1hand2");
		igp1d2 = null;
		p1d2.setIcon(getimage(igp1d2));
		p1d2.setBounds(230, 10, 150, 200);
		frame.getContentPane().add(p1d2);
		
		p2d1 = new JLabel("player2hand1");
		igp2d1 = null;
		p2d1.setIcon(getimage(igp2d1));
		p2d1.setBounds(50, 220, 150, 200);
		frame.getContentPane().add(p2d1);
		
		p2d2 = new JLabel("player2hand2");
		igp2d2 = null;
		p2d2.setIcon(getimage(igp2d2));
		p2d2.setBounds(230, 220, 150, 200);
		frame.getContentPane().add(p2d2);
		
		frame.setVisible(true);
	}
	public static ImageIcon getimage(ImageIcon s) {
		s = new ImageIcon(GameTable.class.getResource("/card/Card-back.png"));
		Image cg = s.getImage();
		cg = cg.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
		s = new ImageIcon(cg);
		return s;
	}
	public static ImageIcon getimage(ImageIcon s,String c) {
		s = new ImageIcon(GameTable.class.getResource("/card/"+ c +".png"));
		Image cg = s.getImage();
		cg = cg.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
		s = new ImageIcon(cg);
		return s;
	}
}

