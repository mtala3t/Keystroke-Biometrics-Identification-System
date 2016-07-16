import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginUI extends JFrame implements ActionListener, KeyListener {

	private JLabel userNameLabel, passwordLabel, userPhraseLabel, formTilteLabel ;
	private JTextField userNameField, userPhraseField;
	private JPasswordField passField;
	private JButton loginBtn, cancelBtn;
	
	private int userNameCount1 = 0,userNameCount2 = 0, passwordCount1 = 0, passwordCount2 = 0, stPhraseCount1 = 0, stPhraseCount2 = 0;
	private ArrayList<Char> userNameChars = null; 
	private ArrayList<Char> passwordChars = null;
	private ArrayList<Char> stPhraseChars = null;
	
	double width=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    double height=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public LoginUI(){
				
		userNameChars = new ArrayList<Char>(); 
		passwordChars = new ArrayList<Char>();
		stPhraseChars = new ArrayList<Char>();
		
		setTitle("Login");
		
		setBounds((int)(width/2.6),(int)(height/3.6),300,270);
		setIconImage(getToolkit().getImage("Images/Keys.gif"));
		getContentPane().setBackground(new Color(204,255,204));
		
		setResizable(false);
		setLayout(null);
		setFont(new Font("Arial",Font.CENTER_BASELINE,15));
		
		formTilteLabel = new JLabel("Type your credentials:" );
		formTilteLabel.setBounds(65,5, 280,25);
		formTilteLabel.setFont(new Font("Arial",Font.CENTER_BASELINE,17));
		
		userNameLabel = new JLabel("Enter Username: ");
		userNameLabel.setBounds(20, 30, 180, 25);
		
		userNameField = new JTextField();
		userNameField.setBounds(20, 55, 230, 25);
		userNameField.addKeyListener(this);
		
		passwordLabel = new JLabel("Enter Password: ");
		passwordLabel.setBounds(20, 85, 180, 25);
		
		passField = new JPasswordField();
		passField.setBounds(20, 110, 230, 25);
		passField.addKeyListener(this);
		
		userPhraseLabel = new JLabel("Type Phrase \"the brown fox\" : ");
		userPhraseLabel.setBounds(20, 135 , 180, 25);
		
		userPhraseField = new JTextField();
		userPhraseField.setBounds(20, 160, 230, 25);
		userPhraseField.addKeyListener(this);
		
		loginBtn = new JButton("Login");
		loginBtn.setBounds(60, 200, 80, 25);
		loginBtn.addActionListener(this);
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(150, 200, 80, 25);
		cancelBtn.addActionListener(this);
		
		getContentPane().add(formTilteLabel);
		getContentPane().add(userNameLabel);
		getContentPane().add(userNameField);	
		getContentPane().add(passwordLabel);
		getContentPane().add(passField);
		getContentPane().add(userPhraseLabel);
		getContentPane().add(userPhraseField);
		getContentPane().add(loginBtn);
		getContentPane().add(cancelBtn);
		
		setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Object obj = e.getSource();
		
		if(obj == loginBtn){
			
			String userName = userNameField.getText();
			String password = passField.getText();
			String stPhrase = userPhraseField.getText();
			
			if(verifyFormTextFields()){
				
				User user = DBUtility.findByUsername(userName);
				
				if(user == null)
					retryAgain("There is no user exists with that username");
				else {
					if(verifyUserCredentials(user))
					{
						ArrayList<Digraph> userNameDigraphs = DigraphUtility.constructDigraphs(userName, userNameChars);
						ArrayList<Digraph> passwordDigraphs = DigraphUtility.constructDigraphs(password, passwordChars);
						ArrayList<Digraph> stPhraseDigraphs = DigraphUtility.constructDigraphs(Constants.STANDARD_PHRASE, stPhraseChars);
						
						UserSignatureProfile userProfile = DBUtility.loadUserSignatureProfile(user);
						
						VerificationStatisticsHandler handler = new VerificationStatisticsHandler();
			
						boolean valid_username = handler.verify(user, userProfile.getUserNameProfile(), userNameDigraphs,"username");
						boolean valid_password = handler.verify(user, userProfile.getPasswordProfile(), passwordDigraphs, "password");
						boolean valid_phrase = handler.verify(user, userProfile.getPhraseProfile(), stPhraseDigraphs, "phrase");
						
						int verified = 0;
						if(valid_username){
							user.setUserNameLoginNo(user.getUserNameLoginNo() + 1);							
							DBUtility.updateUserProfile(user, userProfile.getUserNameProfile(), userNameDigraphs,"username");
							verified++;
							System.out.println("Valid Username");
						}
						
						if(valid_password){
							user.setPasswordLoginNo(user.getPasswordLoginNo() + 1);
							DBUtility.updateUserProfile(user, userProfile.getPasswordProfile(), passwordDigraphs,"password");
							verified++;
							System.out.println("Valid password");
						}
						
						if(valid_phrase){
							user.setPhraseLoginNo(user.getPhraseLoginNo() + 1);
							DBUtility.updateUserProfile(user, userProfile.getPhraseProfile(), stPhraseDigraphs,"phrase");
							verified++;
							System.out.println("Valid phrase");
						}
						
						user.setLoginNo(user.getLoginNo() + 1);
						
						
						if(verified >=2) {
							user.setSuccessfulLoginNo(user.getSuccessfulLoginNo() + 1);
						
							JOptionPane.showMessageDialog(null, "Login matched Profile Keystroke Characteristics,Thankyou!");
							setVisible(false);
							dispose();
						}
						else{
							retryAgain("Login did not match Profile Keystroke Characteristics,try again!");
							//JOptionPane.showMessageDialog(null, "Login did not match Profile Keystroke Characteristics,try again!");
						}
						DBUtility.updateUserLoginStatitics(user);
					}
				}
			}
			
		}
		
		if(obj == cancelBtn){
			setVisible(false);
			dispose();
		}
	}
	
	public static void main(String args[]){
		new LoginUI();
	}

	@Override
	public void keyPressed(KeyEvent ev) {
		
		char ch = ev.getKeyChar();
		
		if(ev.getKeyCode() == 8 || ev.getKeyCode() == 46){
			retryAgain("Cannot Edit text or use Delete or Backspace, invalidates keystroke analysis, Retry again this trial !");
			return;
		}
		
		Object obj = ev.getSource();
		if (obj == userNameField) {
			if (userNameCount1 <= 12) {

				Char c = new Char(ch);
				c.setPressingTime(ev.getWhen());
				userNameChars.add(c);
				System.out.println(ch + "  press" + ev.getWhen());
				userNameCount1++;

			} else {
				retryAgain("Maximum of 12 Characters for Username allowed, Retry again");
				return;
			}

		} else if (obj == passField) {

			if (passwordCount1 <= 25) {

				Char c = new Char(ch);
				c.setPressingTime(ev.getWhen());
				passwordChars.add(c);
				System.out.println(ch + "  press" + ev.getWhen());
				passwordCount1++;

			} else {
				retryAgain("Maximum of 25 Characters for Password allowed, Retry again");
				return;
			}

		} else if (obj == userPhraseField) {

			if (stPhraseCount1 <= Constants.STANDARD_PHRASE.length()) {

				Char c = new Char(ch);
				c.setPressingTime(ev.getWhen());
				stPhraseChars.add(c);
				System.out.println(ch + "  press" + ev.getWhen());
				stPhraseCount1++;

			} else {
				retryAgain("Maximum of 13 Characters for Standard Phrase allowed, Retry again");
				return;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent ev) {
		
		char ch = ev.getKeyChar();
		
		Object obj = ev.getSource();
		if (obj == userNameField) {

			System.out.println(ch + "  release" + ev.getWhen());
			userNameChars.get(userNameCount2).setReleasingTime(ev.getWhen());
			userNameCount2++;

		} else if (obj == passField) {

			System.out.println(ch + "  release" + ev.getWhen());
			passwordChars.get(passwordCount2).setReleasingTime(ev.getWhen());
			passwordCount2++;

		} else if (obj == userPhraseField) {

			if (ch == Constants.STANDARD_PHRASE.charAt(stPhraseCount2)) {
				System.out.println(ch + "  release" + ev.getWhen());
				stPhraseChars.get(stPhraseCount2)
						.setReleasingTime(ev.getWhen());
				stPhraseCount2++;

			}
		}

		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	private void retryAgain(String message){
		JOptionPane.showConfirmDialog(null,
				message, "Error",
				JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
		setVisible(false);
		dispose();
		new LoginUI();
	}
	
	private boolean verifyFormTextFields() {
		
		String userName = userNameField.getText();
		String password = passField.getText();
		String stPhrase = userPhraseField.getText();
		
		boolean flag = false;
		
		if(userName != null && userName.trim().length()>0){
			
			if(password != null && password.trim().length()>0){
				if(stPhrase != null && stPhrase.trim().length()>0){
					flag = true;
				} else {
					retryAgain("Missing Standard Phrase, please retry again.");
					flag = false;
				}
				
			} else {
				retryAgain("Missing Password, please retry again.");
				flag = false;
			}
			
		} else{
			
			retryAgain("Missing Username, please retry again.");
			flag = false;
		}
	
		return flag ;
	}
	
	private boolean verifyUserCredentials(User user){
		
		if(user == null)
			return false;
		
		if(!user.getPassword().equals(passField.getText())) {
			retryAgain("The Password is incorrect, please retry again.");
			return false;
		}
			
			
		if(!Constants.STANDARD_PHRASE.equals(userPhraseField.getText())){
			retryAgain("The Standard phrase is incorrect, please type the required text, Retry again.");
			return false ;
		}
			
		return true;
	}
	
}
