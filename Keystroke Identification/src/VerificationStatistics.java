import java.util.ArrayList;


public class VerificationStatistics {

	ArrayList<VerificationEntry> statistics = null;
	
	public VerificationStatistics(){
		statistics = new ArrayList<VerificationEntry>();
	}
	
	public void addEntry(VerificationEntry entry){
		statistics.add(entry);
	}
	
	public VerificationEntry getEntry(int index){
		return statistics.get(index);
	}
}

class VerificationEntry {
	
	private double stdDev;
	private double intermediateWeight;
	private double finalWeight;
	private double passWeight;
	
	public double getStdDev() {
		return stdDev;
	}
	public void setStdDev(double stdDev) {
		this.stdDev = stdDev;
	}
	public double getIntermediateWeight() {
		return intermediateWeight;
	}
	public void setIntermediateWeight(double intermediateWeight) {
		this.intermediateWeight = intermediateWeight;
	}
	public double getFinalWeight() {
		return finalWeight;
	}
	public void setFinalWeight(double finalWeight) {
		this.finalWeight = finalWeight;
	}
	public double getPassWeight() {
		return passWeight;
	}
	public void setPassWeight(double passWeight) {
		this.passWeight = passWeight;
	}
	
	
}