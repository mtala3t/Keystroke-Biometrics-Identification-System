import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LearningUI extends JFrame implements ActionListener, KeyListener {

	private JLabel userNameLabel, passwordLabel, userPhraseLabel, formTilteLabel ;
	private JTextField userNameField, userPhraseField;
	private JPasswordField passField;
	private JButton nextBtn, cancelBtn;
	
	private static int numberOfLogins = 1;
	
	private User user = null;
	
	private ArrayList<Char> userNameChars = null; 
	private ArrayList<Char> passwordChars = null;
	private ArrayList<Char> stPhraseChars = null;
	
	private int userNameCount1 = 0,userNameCount2 = 0, passwordCount1 = 0, passwordCount2 = 0, stPhraseCount1 = 0, stPhraseCount2 = 0;
	
	private static ArrayList<ArrayList<Digraph>> userNameTrainingData = new ArrayList<ArrayList<Digraph>>();
	private static ArrayList<ArrayList<Digraph>> passwordTrainingData = new ArrayList<ArrayList<Digraph>>();
	private static ArrayList<ArrayList<Digraph>> stPhraseTrainingData = new ArrayList<ArrayList<Digraph>>();
	
	double width=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    double height=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    
	public LearningUI(User user){
		
		
		this.user = user;
		
		userNameChars = new ArrayList<Char>(); 
		passwordChars = new ArrayList<Char>();
		stPhraseChars = new ArrayList<Char>();
		
		setTitle("Learning Mode: Login Number " + numberOfLogins);
		
		setBounds((int)(width/2.6),(int)(height/3.6),300,270);
		setIconImage(getToolkit().getImage("Images/Home.gif"));
		setResizable(false);
		setLayout(null);
		setFont(new Font("Arial",Font.CENTER_BASELINE,15));
		getContentPane().setBackground(new Color(204,255,204));
		
		formTilteLabel = new JLabel("Login Number " + numberOfLogins );
		formTilteLabel.setBounds(85,5, 280,25);
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
		
		if(numberOfLogins == Constants.NUMBER_OF_TRAINING_TRIALS)
			nextBtn = new JButton("Finish");
		else
			nextBtn = new JButton("Next");
		
		nextBtn.setBounds(60, 200, 80, 25);
		nextBtn.addActionListener(this);
		
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
		getContentPane().add(nextBtn);
		getContentPane().add(cancelBtn);
		
		setVisible(true);
		
		numberOfLogins++;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		
		if (obj == nextBtn) {

			if (isValidUserName()) {
				if (isValidPassword()) {
					if (isValidPhrase()) {

						userNameTrainingData.add(DigraphUtility
								.constructDigraphs(user.getUserName(),
										userNameChars));
						passwordTrainingData.add(DigraphUtility
								.constructDigraphs(user.getPassword(),
										passwordChars));
						stPhraseTrainingData.add(DigraphUtility
								.constructDigraphs(Constants.STANDARD_PHRASE,
										stPhraseChars));

						setVisible(false);
						dispose();

						if (numberOfLogins <= Constants.NUMBER_OF_TRAINING_TRIALS)
							new LearningUI(user);
						else {
							
							//Construct the profile here
							
							StringProfile usernameProfile = DigraphUtility.constructStringProfile(userNameTrainingData);
							StringProfile passwordProfile = DigraphUtility.constructStringProfile(passwordTrainingData);
							StringProfile stPhraseProfile = DigraphUtility.constructStringProfile(stPhraseTrainingData);
							
							UserSignatureProfile userProfile = new UserSignatureProfile();
							userProfile.setUserNameProfile(usernameProfile);
							userProfile.setPasswordProfile(passwordProfile);
							userProfile.setPhraseProfile(stPhraseProfile);
							
							user.setProfile(userProfile);
							user.setLoginNo(Constants.NUMBER_OF_TRAINING_TRIALS);
							user.setSuccessfulLoginNo(Constants.NUMBER_OF_TRAINING_TRIALS);
							user.setUserNameLoginNo(Constants.NUMBER_OF_TRAINING_TRIALS);
							user.setPasswordLoginNo(Constants.NUMBER_OF_TRAINING_TRIALS);
							user.setPhraseLoginNo(Constants.NUMBER_OF_TRAINING_TRIALS);
							
							if (DBUtility.insertUserSignatureProfile(user) && DBUtility.updateUserLoginStatitics(user) ){
								JOptionPane.showMessageDialog(null, "Your training session is completed successfully.");
								setVisible(false);
								dispose();
								new MainUI();
							} else {
								
							}
							
							for (int j = 0; j < userNameTrainingData.size(); j++) {
								for (int i = 0; i < userNameTrainingData.get(j)
										.size(); i++) {

									System.out.println(userNameTrainingData
											.get(j).get(i).getFirstChar()
											+ "-"
											+ userNameTrainingData.get(j)
													.get(i).getSecondChar()
											+ "   "
											+ userNameTrainingData.get(j)
													.get(i).getLatency());
								}
								System.out.println("--------------------");
							}
						}

					} else {
						JOptionPane.showConfirmDialog(null, "Invalid standard phrase, you must enter ("
												+ Constants.STANDARD_PHRASE + "). Retry this learning trial again. ",
										"Error", JOptionPane.CLOSED_OPTION,
										JOptionPane.ERROR_MESSAGE);
						setVisible(false);
						dispose();
						numberOfLogins--;
						new LearningUI(user);
					}
				} else {
					JOptionPane.showConfirmDialog(null, "Invalid password, you must enter ("+ user.getPassword()
									+ "). Retry this learning trial again. ", "Error", JOptionPane.CLOSED_OPTION,
							JOptionPane.ERROR_MESSAGE);
					setVisible(false);
					dispose();
					numberOfLogins--;
					new LearningUI(user);
				}

			} else {
				JOptionPane.showConfirmDialog(null, "Invalid username, you must enter ("+ user.getUserName()
								+ "). Retry this learning trial again. ", "Error", JOptionPane.CLOSED_OPTION,
						JOptionPane.ERROR_MESSAGE);
				setVisible(false);
				dispose();
				numberOfLogins--;
				new LearningUI(user);
			}

		}
		
		if(obj == cancelBtn){
			setVisible(false);
			dispose();
		}
	}
	
	public static void main(String args[]){
		User user = new User("tetosoft","mohamed");
		user.setId(5);
		new LearningUI(user);
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

			if (userNameCount1 < user.getUserName().length()) {
				if (ch == user.getUserName().charAt(userNameCount1)) {

					Char c = new Char(ch);
					c.setPressingTime(ev.getWhen());
					userNameChars.add(c);
					System.out.println(ch + "  press" + ev.getWhen());
					userNameCount1++;

				} else {
					retryAgain("You typed unexpected char, please retry again this training trial.");
					return;
				}
			} else {
				retryAgain("Invalid username, please retry again this training trial.");
				return;				
			}

		} else if (obj == passField) {

			if (passwordCount1 < user.getPassword().length()) {
				if (ch == user.getPassword().charAt(passwordCount1)) {
					Char c = new Char(ch);
					c.setPressingTime(ev.getWhen());
					passwordChars.add(c);
					System.out.println(ch + "  press" + ev.getWhen());
					passwordCount1++;
				} else {
					retryAgain("You typed unexpected char, please retry again this training trial.");
					return;
				}
			} else {
				retryAgain("Invalid password, please retry again this training trial.");
				return;	
			}

		} else if (obj == userPhraseField) {

			if (stPhraseCount1 < Constants.STANDARD_PHRASE.length()) {
				if (ch == Constants.STANDARD_PHRASE.charAt(stPhraseCount1)) {
					Char c = new Char(ch);
					c.setPressingTime(ev.getWhen());
					stPhraseChars.add(c);
					System.out.println(ch + "  press" + ev.getWhen());
					stPhraseCount1++;

				} else {
					retryAgain("You typed unexpected char, please retry again this training trial.");
					return;
				}
			} else {
				retryAgain("Invalid pharse, please retry again this training trial.");
				return;	
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent ev) {
		
		char ch = ev.getKeyChar();
		
		Object obj = ev.getSource();
		if(obj == userNameField){
			
			if(ch == user.getUserName().charAt(userNameCount2))
			{
				System.out.println(ch + "  release" + ev.getWhen());
				userNameChars.get(userNameCount2).setReleasingTime(ev.getWhen());
				userNameCount2++;
				
				
			} else {
				
			}
			
		} else if(obj == passField){
			
			if(ch == user.getPassword().charAt(passwordCount2))
			{
				System.out.println(ch + "  release" + ev.getWhen());
				passwordChars.get(passwordCount2).setReleasingTime(ev.getWhen());
				passwordCount2++;
				
			} else {
				
			}
			
		} else if(obj == userPhraseField) {
			
			if(ch == Constants.STANDARD_PHRASE.charAt(stPhraseCount2))
			{
				System.out.println(ch + "  release" + ev.getWhen());
				stPhraseChars.get(stPhraseCount2).setReleasingTime(ev.getWhen());
				stPhraseCount2++;
				
			} else {
				
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent ev) {
		// TODO Auto-generated method stub
	}

	private boolean isValidUserName(){
		
		if(userNameField.getText() == null || userNameField.getText().trim().length() == 0 || (!userNameField.getText().equals(user.getUserName()))){
			return false;
		}
		
		return true;
	}
	
	private boolean isValidPassword(){
		
		if(passField.getText() == null || passField.getText().trim().length() == 0 || (!passField.getText().equals(user.getPassword()))){
			return false;
		}
		
		return true;
	}
	
	private boolean isValidPhrase(){
		
		if(userPhraseField.getText() == null || userPhraseField.getText().trim().length() == 0 || (!userPhraseField.getText().equals(Constants.STANDARD_PHRASE))){
			return false;
		}
		
		return true;
	}
	
	private void retryAgain(String message){
		JOptionPane.showConfirmDialog(null,
				message, "Error",
				JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
		setVisible(false);
		dispose();
		numberOfLogins--;
		new LearningUI(user);
	}
}
