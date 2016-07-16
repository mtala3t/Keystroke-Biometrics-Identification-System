import java.util.ArrayList;


public class DigraphUtility {
	
	public static ArrayList<Digraph> constructDigraphs(String phrase, ArrayList<Char> chars){
		
		ArrayList<Digraph> digraphs = new ArrayList<Digraph>();
		
		for(int i = 0 ; i<phrase.trim().length()-1; i++){
			Digraph digraph1 = new Digraph(phrase.charAt(i),'^',0);
			Digraph digraph2 = new Digraph(phrase.charAt(i),phrase.charAt(i+1),0);
			
			digraphs.add(digraph1);
			digraphs.add(digraph2);
		}
		
		digraphs.add(new Digraph(phrase.charAt(phrase.trim().length()-1),'^',0));
		
		
		
		return computeLatency(digraphs,chars);
	}
	
	private static ArrayList<Digraph> computeLatency(ArrayList<Digraph> digraphs, ArrayList<Char> chars) {
		
		int count = 1;
		for(int i = 0 ; i < digraphs.size() ; i++){
			
			if(i%2 == 0){
				
				long latency = chars.get(i/2).getReleasingTime() - chars.get(i/2).getPressingTime() ; 
				digraphs.get(i).setLatency(latency);
			} else {
				
				long latency = chars.get(i-count+1).getPressingTime() - chars.get(i-count).getPressingTime(); 
				digraphs.get(i).setLatency(latency);
				count++;
				
			}
		}
		
		
		return digraphs;
	}
	
	
	public static StringProfile constructStringProfile(ArrayList<ArrayList<Digraph>> trainingData){
		
		StringProfile profile = new StringProfile();
		
		
		for(int i = 0; i < trainingData.get(0).size(); i++){
			
			StringProfileEntry entry = new StringProfileEntry();
			entry.setDigraph(trainingData.get(0).get(i));
			double meanLatency = 0;
			double sumOfX = 0;
			double sumOfXSquared = 0;
			
			for(int j = 0 ; j < trainingData.size(); j++){
				
				sumOfX += trainingData.get(j).get(i).getLatency();
				sumOfXSquared += Math.pow(trainingData.get(j).get(i).getLatency(), 2);
			}
			
			entry.setSumOfX(sumOfX);
			meanLatency = sumOfX / Constants.NUMBER_OF_TRAINING_TRIALS;
			
			entry.setMeanLatency(meanLatency);
			entry.setSumOfX(sumOfX);
			entry.setSumOfSquaredX(sumOfXSquared);
			
			profile.addProfileEntry(entry);
		}
		
		return profile;
	}
	
	
	public static void main(String args[]){
		
		/*ArrayList<Digraph> digraphs = constructDigraphs("Golden");
		
		for(int i =0 ;i <digraphs.size() ; i++){
			System.out.println(digraphs.get(i).getFirstChar() + "   " + digraphs.get(i).getSecondChar() );
			
		}
		*/
	}
	
}

