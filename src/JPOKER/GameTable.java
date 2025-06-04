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
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.GridLayout;



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
	private JPanel panelHand2;
	private JPanel panel_2;
	private JPanel Button;
	private JButton btnNewButton;
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
		igp1d1 = null;
		igp1d2 = null;
		igp2d1 = null;
		igp2d2 = null;
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JPanel panelHand1 = new JPanel();
		panel_2.add(panelHand1);
		panelHand1.setLayout(new BoxLayout(panelHand1, BoxLayout.Y_AXIS));
		
		p1d1 = new JLabel("");
		panelHand1.add(p1d1);
		p1d1.setIcon(getimage(igp1d1));
		
		p2d1 = new JLabel("");
		panelHand1.add(p2d1);
		p2d1.setIcon(getimage(igp2d1));
		
		panelHand2 = new JPanel();
		panel_2.add(panelHand2);
		panelHand2.setLayout(new BoxLayout(panelHand2, BoxLayout.Y_AXIS));
		
		p1d2 = new JLabel("player1hand    ");
		panelHand2.add(p1d2);
		p1d2.setIcon(getimage(igp1d2));
		
		p2d2 = new JLabel("player2hand    ");
		panelHand2.add(p2d2);
		p2d2.setIcon(getimage(igp2d2));
		
		Button = new JPanel();
		panel_2.add(Button);
		Button.setLayout(new BoxLayout(Button, BoxLayout.Y_AXIS));
		
		btnNewButton = new JButton("패 오픈");
		Button.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setdeck(1);
				setdeck(2);
				setdeck(3);
				setdeck(4);
			}
		});
		
		JButton btnDeck = new JButton("패섞기");
		Button.add(btnDeck);
		btnDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p1deck = dealer.deck();
				p2deck = dealer.deck();
				setdeck(0);
				setdeck(1);
				setdeck(3);
			}
		});
		
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
	public void setdeck(int pd) {
		switch (pd) {
		case 1: 
			p1d1.setIcon(getimage(igp1d1, (p1deck[0] +"C")));
			break;
		case 2:
			p1d2.setIcon(getimage(igp1d2, (p1deck[1] +"H")));
			break;
		case 3:
			p2d1.setIcon(getimage(igp2d1, (p2deck[0] +"S")));
			break;
		case 4:
			p2d2.setIcon(getimage(igp2d2, (p2deck[1] +"D")));
			break;
		default:
			p1d1.setIcon(getimage(igp1d1));
			p1d2.setIcon(getimage(igp1d1));
			p2d1.setIcon(getimage(igp1d1));
			p2d2.setIcon(getimage(igp1d1));
			break;
		}
	}
}

