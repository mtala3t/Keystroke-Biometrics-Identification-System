import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Mohamed Talaat
 *
 */

public class StringProfile {
	
	private ArrayList<StringProfileEntry> profileEntries = null;
	
	
	public StringProfile(){
		profileEntries = new ArrayList<StringProfileEntry>();
		
	}

	
	public ArrayList<StringProfileEntry> getStringProfileEntries() {
		return profileEntries;
	}
	
	public void setStringProfileEntries(ArrayList<StringProfileEntry> profileEntries) {
		this.profileEntries = profileEntries;
	}
	
	public void addProfileEntry(StringProfileEntry entry ){
		
		profileEntries.add(entry);
	}
}
