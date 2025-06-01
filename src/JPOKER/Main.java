package JPOKER;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("로그인");
		btnNewButton.setBounds(113, 129, 183, 105);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("회원가입");
		btnNewButton_1.setBounds(10, 232, 93, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		idTextField = new JTextField();
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
		idTextField.setFont(new Font("굴림", Font.PLAIN, 24));
		idTextField.setBounds(71, 24, 267, 45);
		frame.getContentPane().add(idTextField);
		idTextField.setColumns(10);
		
		passTextField = new JTextField();
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
		passTextField.setFont(new Font("굴림", Font.PLAIN, 24));
		passTextField.setColumns(10);
		passTextField.setBounds(71, 79, 267, 45);
		frame.getContentPane().add(passTextField);
	}
}
