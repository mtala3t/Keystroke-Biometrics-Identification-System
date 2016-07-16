import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 
 */

/**
 * @author Mohamed Talaat
 *
 */
public class NewUserUI extends JFrame implements ActionListener {
	
	private JLabel userNameLabel, passwordLabel, confirmPasswordLabel , formTilteLabel;
	private JTextField userNameField;
	private JPasswordField passField, confirmPassField;
	private JButton okBtn, cancelBtn;
	
	double width=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    double height=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    
	public NewUserUI(){
		
		setTitle("Select your Identity Information");
		setBounds((int)(width/2.6),(int)(height/3.6),300,270);
		setIconImage(getToolkit().getImage("Images/NotePad.gif"));
		setResizable(false);
		setLayout(null);
		setFont(new Font("Arial",Font.CENTER_BASELINE,15));
		getContentPane().setBackground(new Color(204,255,204));
		
		formTilteLabel = new JLabel("Please fill the following form: ");
		formTilteLabel.setBounds(20,5, 280,25);
		formTilteLabel.setFont(new Font("Arial",Font.CENTER_BASELINE,17));
		
		userNameLabel = new JLabel("Select username (5 to 12): ");
		userNameLabel.setBounds(20, 30, 180, 25);
		
		userNameField = new JTextField();
		userNameField.setBounds(20, 55, 230, 25);
		
		passwordLabel = new JLabel("Select Password (8 to 25): ");
		passwordLabel.setBounds(20, 85, 180, 25);
		
		passField = new JPasswordField();
		passField.setBounds(20, 110, 230, 25);
		
		confirmPasswordLabel = new JLabel("Confirm Password: ");
		confirmPasswordLabel.setBounds(20, 135 , 180, 25);
		
		confirmPassField = new JPasswordField();
		confirmPassField.setBounds(20, 160, 230, 25);
		
		okBtn = new JButton("OK");
		okBtn.setBounds(50, 200, 80, 25);
		okBtn.addActionListener(this);
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(160, 200, 80, 25);
		cancelBtn.addActionListener(this);
		
		getContentPane().add(formTilteLabel);
		getContentPane().add(userNameLabel);
		getContentPane().add(userNameField);	
		getContentPane().add(passwordLabel);
		getContentPane().add(passField);
		getContentPane().add(confirmPasswordLabel);
		getContentPane().add(confirmPassField);
		getContentPane().add(okBtn);
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
		
		if(obj == okBtn){
			
			if(userNameField.getText().trim().equals("") || passField.getText().trim().equals("") ||
					confirmPassField.getText().trim().equals("")){
				
				JOptionPane.showConfirmDialog(null, "You did not fill all required fields, you must fill all.","Error",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
			}
			else if(validateFieldSizes()== false){
				
			}
			else if(!passField.getText().equals(confirmPassField.getText())){
				JOptionPane.showConfirmDialog(null, "Confirm password filed did not match with the entered password, \n please type it again correctly.","Error",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
				passField.setText("");
				confirmPassField.setText("");
				passField.requestFocus();
			}
			else {
				
				
				if(DBUtility.findByUsername(userNameField.getText())== null){
					
					User user = new User(userNameField.getText(),passField.getText());
 					boolean added = DBUtility.insertNewUser(user);
					if(added){
						JOptionPane.showMessageDialog(null, "Now, you will enter the training mode.\nSo, you must enter your login credentails "+Constants.NUMBER_OF_TRAINING_TRIALS+" times");
						setVisible(false);
						dispose();
						new LearningUI(user);
					}else {
						JOptionPane.showMessageDialog(null, "Some Error Happend");
					}
					
					
				} else {
					
					JOptionPane.showMessageDialog(null,"User with that name is already exist,\nPlease enter another username.","Kamosi Message",JOptionPane.OK_OPTION);
                    userNameField.setText("");
                    passField.setText("");
                    confirmPassField.setText("");
                    userNameField.requestFocus();
				}
				
			}
			
		}
		
		if(obj == cancelBtn){
			setVisible(false);
			dispose();
		}
	}
	
	private boolean validateFieldSizes(){
		
		if(userNameField.getText().length()<5 || userNameField.getText().length() > 12){
			JOptionPane.showConfirmDialog(null, "Please enter your username with length from 5 to 12.","Error",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
			
			userNameField.setText("");
			userNameField.requestFocus();
			return false;
		}			
		
		if(passField.getText().length() < 8 || passField.getText().length() > 25){
			JOptionPane.showConfirmDialog(null, "Please enter a password of length 8 to 25.","Error",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
			passField.setText("");
			confirmPassField.setText("");
			passField.requestFocus();
			
			return false;
		}
			
		return true;
	}

}
