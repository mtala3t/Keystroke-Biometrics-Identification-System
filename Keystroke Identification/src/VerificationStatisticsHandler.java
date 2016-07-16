import java.util.ArrayList;


public class VerificationStatisticsHandler {

	public VerificationStatistics generateVerificationStatistics(User user,
			StringProfile profile, ArrayList<Digraph> digraphs, String type){
		
		VerificationStatistics statistics = new VerificationStatistics();
		
		for(int i = 0; i < digraphs.size(); i++){
			
			double meanLetancy = profile.getStringProfileEntries().get(i).getMeanLatency();
			double sumOfX = profile.getStringProfileEntries().get(i).getSumOfX();
			double sumOfXSquared = profile.getStringProfileEntries().get(i).getSumOfSquaredX();
			int logins = 0;
			
			if(type.equals("username"))
				logins = user.getUserNameLoginNo();
			else if(type.equals("password"))
				logins = user.getPasswordLoginNo();
			else if(type.equals("phrase"));
				logins = user.getPhraseLoginNo();
				
			double stdDev = Math.sqrt((sumOfXSquared/logins)-(Math.pow((sumOfX/logins), 2)));
			double intermediateWeight = meanLetancy / stdDev;
			VerificationEntry entry  = new VerificationEntry();
			
			entry.setStdDev(stdDev);
			entry.setIntermediateWeight(intermediateWeight);
			
			statistics.addEntry(entry);
			
		}
		
		double sumOfInterWeights = 0;
		
		for(int i = 0; i < digraphs.size(); i++){
			sumOfInterWeights += statistics.getEntry(i).getIntermediateWeight();			
		}
		
		for(int i = 0; i < digraphs.size(); i++){
			double finalWieght = ((statistics.getEntry(i).getIntermediateWeight()) * (1 / sumOfInterWeights));
			statistics.getEntry(i).setFinalWeight(finalWieght);			
		}
		
		return statistics;
	}
	
	public  boolean verify(User user, StringProfile profile, ArrayList<Digraph> digraphs, String type){
	
		VerificationStatistics stats =  generateVerificationStatistics(user, profile, digraphs,type);
		
		int match = 0;
		double totalWeight = 0;
		for(int i = 0; i < digraphs.size(); i++){
		
			double meanLetancy = profile.getStringProfileEntries().get(i).getMeanLatency();
			double digraphLetancy = digraphs.get(i).getLatency();
			double stdDev = stats.getEntry(i).getStdDev();
			
			if((digraphLetancy > (meanLetancy - stdDev)) && (digraphLetancy < (meanLetancy + stdDev))){
				
				match++;
				totalWeight += stats.getEntry(i).getFinalWeight();	
			}
				
		}
		
		System.out.println("Total Weight " + totalWeight);
		System.out.println("match " + match+ "  from "+digraphs.size());
		
		if (totalWeight > 0.5 || ((match / (digraphs.size())) > 0.75))		
			return true;
	
	
		
		return false;
	}
	
}
