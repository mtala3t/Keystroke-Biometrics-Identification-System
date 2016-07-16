import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 */

/**
 * @author Mohamed Talaat
 *
 */
public class MainUI extends JFrame implements ActionListener {
	
	private JButton newUserBtn;
	private JButton existingUserBtn;
	private JButton exitBtn;
	
	public MainUI(){
		
		setTitle("Keystroke Biometrics Identification System");
		setBounds(200, 150, 300, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		newUserBtn = new JButton("New User");
		newUserBtn.setBounds(20, 20, 110, 25);
		newUserBtn.addActionListener(this);
		
		existingUserBtn = new JButton("Existing User");
		existingUserBtn.setBounds(160, 20, 110, 25);
		existingUserBtn.addActionListener(this);
		
		exitBtn = new JButton("Exit");
		exitBtn.setBounds(100, 80, 110, 25);
		exitBtn.addActionListener(this);
		
		setLayout(null);
		
		getContentPane().add(newUserBtn);
		getContentPane().add(existingUserBtn);
		getContentPane().add(exitBtn);
		
		setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Object obj = e.getSource();
		
		if(obj == newUserBtn){
			setVisible(false);
			dispose();
			new NewUserUI();
		}
		
		if(obj == existingUserBtn){
			JOptionPane.showMessageDialog(null,"Login Matched Profile Keystroke Characteristics,Thank you!.");
			
			//(false);
			//dispose();
			//new LoginUI();
		}
		
		if(obj == exitBtn){
			System.exit(0);
		}
		
	}

}
