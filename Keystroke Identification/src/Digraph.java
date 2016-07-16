/**
 * 
 */

/**
 * @author Mohamed Talaat
 *
 */
public class Digraph {

	private char charOne;
	private char charTwo;
	
	private long latency ;
	
	public Digraph(char charOne, char charTwo)
	{
		this.charOne = charOne ;
		this.charTwo = charTwo;
	}
	
	public Digraph(char c1, char c2, long latency){
		this.charOne = c1 ;
		this.charTwo = c2 ;
		this.latency = latency;
	}
	
	public char getFirstChar(){
		return charOne;
	}
	
	public void setFirstChar(char c){
		this.charOne = c;
	}
	
	public char getSecondChar(){
		return charTwo;
	}
	
	public void setSecondChar(char c){
		this.charTwo = c;
	}
	
	public long getLatency(){
		return latency;
	}
	
	public void setLatency(long latency){
		this.latency = latency;
	}
	
	public String toString(){
		return ""+charOne+"-"+charTwo;
	}
}
