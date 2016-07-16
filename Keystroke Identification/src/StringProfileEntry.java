import java.util.ArrayList;


/**
 * 
 */

/**
 * @author Mohamed Talaat
 *
 */
public class StringProfileEntry {

	private int id;
	private Digraph digraph ;
	private double meanLatency;
	private double sumOfX;
	private double sumOfSquaredX;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Digraph getDigraph() {
		return digraph;
	}
	
	public void setDigraph(Digraph digraph) {
		this.digraph = digraph;
	}
	
	public double getMeanLatency() {
		return meanLatency;
	}
	
	public void setMeanLatency(double meanLatency) {
		this.meanLatency = meanLatency;
	}
	
	public double getSumOfX() {
		return sumOfX;
	}
	
	public void setSumOfX(double sumOfX) {
		this.sumOfX = sumOfX;
	}
	
	public double getSumOfSquaredX() {
		return sumOfSquaredX;
	}
	
	public void setSumOfSquaredX(double sumOfSquaredX) {
		this.sumOfSquaredX = sumOfSquaredX;
	}
	
	public String toString(){
		return ""+id+"  "+digraph +"   " +meanLatency +"    "+sumOfX + "   "+sumOfSquaredX;
	}
	
}
