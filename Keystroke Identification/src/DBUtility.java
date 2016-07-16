import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


public class DBUtility {
	
	public static User findByUsername(String userName){
		
		Connection con = DBConnection.getDBConnection();
		Statement stat = null;
		ResultSet rs = null;
		
		User user = null;
		
		try {
			 
			stat = con.createStatement();
			String query = "SELECT * FROM USERS WHERE USERNAME ='" + userName + "'";
			rs = stat.executeQuery(query);
			while(rs.next()){
				String username = rs.getString("USERNAME");
				String password = rs.getString("PASSWORD");
				int id = rs.getInt("ID");
				int loginNo = rs.getInt("login_no");
				int successfulLoginNo = rs.getInt("successful_login_num");
				int userNameLoginNo = rs.getInt("username_login_no");
				int passwordLoginNo = rs.getInt("password_login_no");
				int phraseLoginNo = rs.getInt("phrase_login_no");
				
				user = new User();
				
				user.setId(id);
				user.setUserName(username);
				user.setPassword(password);
				user.setLoginNo(loginNo);
				user.setSuccessfulLoginNo(successfulLoginNo);
				user.setUserNameLoginNo(userNameLoginNo);
				user.setPasswordLoginNo(passwordLoginNo);
				user.setPhraseLoginNo(phraseLoginNo);
			}
			if(user == null){
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return user;
	}
	
	public static boolean insertNewUser(User user){
		String userName = user.getUserName();
		String password = user.getPassword();
		Date date=new Date();
        int year=date.getYear()+1900;
        int month=date.getMonth()+1;
        int day=date.getDate();
        
        String dateStr=""+year+"-"+month+"-"+day;
		
		Connection con = DBConnection.getDBConnection();
		Statement stat = null;
		
		int result = 0;
		
		try {
			 
			stat = con.createStatement();
			String query = "INSERT INTO USERS (USERNAME,PASSWORD,TS) VALUES('" + userName + "','" + password + "','" + dateStr + "')";
			result = stat.executeUpdate(query);
			if (result == 1) {

				ResultSet rs = stat
						.executeQuery("SELECT ID FROM USERS WHERE USERNAME = '"
								+ userName + "'");
				while (rs.next())
					user.setId(rs.getInt("ID"));

				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public static boolean updateUserLoginStatitics(User user){
		
		int id = user.getId();
		String userName = user.getUserName();
		String password = user.getPassword();
		int loginNo = user.getLoginNo();
		int succLoginNo = user.getSuccessfulLoginNo();
		int userNameLoginNo = user.getUserNameLoginNo();
		int passLoginNo = user.getPasswordLoginNo();
		int phraseloginNo = user.getPhraseLoginNo();
		
		Date date=new Date();
        int year=date.getYear()+1900;
        int month=date.getMonth()+1;
        int day=date.getDate();
        
        String dateStr=""+year+"-"+month+"-"+day;
		
		Connection con = DBConnection.getDBConnection();
		Statement stat = null;
		
		int result = 0;
		
		try {
			 
			stat = con.createStatement();
			String query = "UPDATE USERS SET USERNAME = '"+userName+"',PASSWORD='"+password+"',login_no='"+loginNo+"',successful_login_num='"+succLoginNo+"',username_login_no ='"+userNameLoginNo+"', password_login_no = '"+passLoginNo+"', phrase_login_no='"+phraseloginNo+"',TS= '"+dateStr+"' WHERE ID = '"+id+"'";
			result = stat.executeUpdate(query);
			if (result == 1) {
				return true;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		return false;
	}
	
	public static boolean insertUserSignatureProfile(User user){
		
        UserSignatureProfile userProfile = user.getProfile();
        
        StringProfile userNamProfile = userProfile.getUserNameProfile();
        StringProfile passwordProfile = userProfile.getPasswordProfile();
        StringProfile stPhraseProfile = userProfile.getPhraseProfile();
        
		boolean r1 = insertStringProfile(userNamProfile, user.getId(), "username");
		boolean r2 = insertStringProfile(passwordProfile, user.getId(), "password");
		boolean r3 = insertStringProfile(stPhraseProfile, user.getId(), "phrase");
		
		if(r1 && r2 && r3 )
			return true;
		
		return false;
		
	}
	
	private static boolean insertStringProfile(StringProfile profile, int userId, String type){
		
		Date date=new Date();
        int year=date.getYear()+1900;
        int month=date.getMonth()+1;
        int day=date.getDate();
        
        String dateStr=""+year+"-"+month+"-"+day;
        
		Connection con = DBConnection.getDBConnection();
		Statement stat = null;
		
		int result = 0;
		
		try {
			 
			
			for(int i = 0 ; i <profile.getStringProfileEntries().size(); i ++){
				
				double meanLatency = profile.getStringProfileEntries().get(i).getMeanLatency();
				double sumOfX = profile.getStringProfileEntries().get(i).getSumOfX();
				double sumOfXSq = profile.getStringProfileEntries().get(i).getSumOfSquaredX();
				String digraph = "";
				if (profile.getStringProfileEntries().get(i).getDigraph().getSecondChar() != '^'){
					digraph += profile.getStringProfileEntries().get(i).getDigraph().getFirstChar()+"-"+
								profile.getStringProfileEntries().get(i).getDigraph().getSecondChar();
				} else
					digraph += profile.getStringProfileEntries().get(i).getDigraph().getFirstChar();
				
				  
				String query = "INSERT INTO SIGNATURE_PROFILE (USER_ID,DIGRAPH,LATENCY_MEAN,SUM_OF_X,SUM_OF_SQUARED_X,TYPE,TS) " +
						"VALUES('" + userId + "','" + digraph + "','" + meanLatency + "','" + sumOfX + "','" + sumOfXSq + "','" + type + "','" + dateStr + "')";
				stat = con.createStatement();
				result = stat.executeUpdate(query);
				if(result != 1)
					return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	public static UserSignatureProfile loadUserSignatureProfile(User user){
		
		UserSignatureProfile profile = new UserSignatureProfile();
		
		profile.setUserNameProfile(getStringProfile("username",user.getId()));
		profile.setPasswordProfile(getStringProfile("password",user.getId()));
		profile.setPhraseProfile(getStringProfile("phrase",user.getId()));
		
		return profile;
	}
	
	private static StringProfile getStringProfile(String type, int userID) {
		
		StringProfile strProfile = new StringProfile();
		
		Connection con = DBConnection.getDBConnection();
		Statement stat = null;
		ResultSet rs = null;
		
		try {
				  
			String query = "SELECT * FROM SIGNATURE_PROFILE WHERE USER_ID = '"+userID+"' and TYPE = '"+type +"' ORDER BY ID ASC";
						
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			
			while(rs.next()){
				
				StringProfileEntry entry = new StringProfileEntry();
				
				String digraphStr = rs.getString("DIGRAPH");
				entry.setId(rs.getInt("ID"));
				entry.setDigraph(constructDigraph(digraphStr));
				entry.setMeanLatency(rs.getDouble("LATENCY_MEAN"));
				entry.setSumOfX(rs.getDouble("SUM_OF_X"));
				entry.setSumOfSquaredX(rs.getDouble("SUM_OF_SQUARED_X"));
				
				strProfile.addProfileEntry(entry);
				
				System.out.println(rs.getInt("ID") + "  "+entry);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return strProfile;
	}
	
	public static boolean updateUserProfile(User user, StringProfile profile, ArrayList<Digraph> digraphs, String type){
		
		int userId = user.getId();
		String userName = user.getUserName();
		String password = user.getPassword();
		int loginNo = user.getLoginNo();
		int succLoginNo = user.getSuccessfulLoginNo();
		int userNameLoginNo = user.getUserNameLoginNo();
		int passLoginNo = user.getPasswordLoginNo();
		int phraseloginNo = user.getPhraseLoginNo();
		
		Date date=new Date();
        int year=date.getYear()+1900;
        int month=date.getMonth()+1;
        int day=date.getDate();
        
        String dateStr=""+year+"-"+month+"-"+day;
		
		Connection con = DBConnection.getDBConnection();
		Statement stat = null;
		
		int result = 0;
		
		try {
		
			for(int i = 0; i <profile.getStringProfileEntries().size(); i++ ){
			
				double newSumOfX = profile.getStringProfileEntries().get(i).getSumOfX() + digraphs.get(i).getLatency();
				double newSumOfXSquared = profile.getStringProfileEntries().get(i).getSumOfSquaredX() + Math.pow(digraphs.get(i).getLatency(),2);
				double newMeanLetancy = 0;
				int entryId = profile.getStringProfileEntries().get(i).getId();
				if(type.equals("username"))
					newMeanLetancy = newSumOfX / userNameLoginNo;
				else if(type.equals("password"))
					newMeanLetancy = newSumOfX / passLoginNo;
				else if(type.equals("phrase"));
					newMeanLetancy = newSumOfX / phraseloginNo;
				
				stat = con.createStatement();
				String query = "UPDATE SIGNATURE_PROFILE SET USER_ID = '"+userId+"',LATENCY_MEAN='"+newMeanLetancy+"',SUM_OF_X='"+newSumOfX+"',SUM_OF_SQUARED_X='"+newSumOfXSquared+"',TS ='"+dateStr+"' WHERE ID = '"+entryId+"'";
				result = stat.executeUpdate(query);
				if (result == 1) {
					return true;
				
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	private static Digraph constructDigraph(String digraphStr){
		
		Digraph digraph = null;
		
		if(digraphStr.length() == 1)
			return new Digraph(digraphStr.charAt(0),'^');
		else if(digraphStr.length() == 3)
			return new Digraph(digraphStr.charAt(0),digraphStr.charAt(2));
		
		return null;
	}
}
