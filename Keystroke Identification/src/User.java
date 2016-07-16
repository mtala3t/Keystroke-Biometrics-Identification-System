/**
 * 
 */

/**
 * @author Mohamed Talaat
 *
 */


public class User {

	private int id ;
	private String userName;
	private String password;
	
	private int successfulLoginNo;
	private int userNameLoginNo;
	private int passwordLoginNo;
	private int phraseLoginNo;
	
	private UserSignatureProfile profile;
	
	public User(){}
	
	private int loginNo;
	public int getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(int loginNo) {
		this.loginNo = loginNo;
	}

	public int getSuccessfulLoginNo() {
		return successfulLoginNo;
	}

	public void setSuccessfulLoginNo(int successfulLoginNo) {
		this.successfulLoginNo = successfulLoginNo;
	}

	public int getUserNameLoginNo() {
		return userNameLoginNo;
	}

	public void setUserNameLoginNo(int userNameLoginNo) {
		this.userNameLoginNo = userNameLoginNo;
	}

	public int getPasswordLoginNo() {
		return passwordLoginNo;
	}

	public void setPasswordLoginNo(int passwordLoginNo) {
		this.passwordLoginNo = passwordLoginNo;
	}

	public int getPhraseLoginNo() {
		return phraseLoginNo;
	}

	public void setPhraseLoginNo(int phraseLoginNo) {
		this.phraseLoginNo = phraseLoginNo;
	}
	
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName= userName;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public UserSignatureProfile getProfile(){
		return profile;
	}
	
	public void setProfile(UserSignatureProfile profile){
		this.profile = profile;
	}
}
