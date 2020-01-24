package dl;

import java.util.ArrayList;
import java.util.List;

public class Problem {
	
	public Interaction enactedInteraction;
	public List<Interaction> problemInteractionList = new ArrayList<Interaction>();
	public int wrongNumber=0;
	
	
	
	public ErrorIndex errorIndex;//为了表明出现问题的位置，以及这个位置出现问题的次数
	
	public int getWrongNumber() {
		return wrongNumber;
	}
	public void setWrongNumber(int wrongNumber) {
		this.wrongNumber = wrongNumber;
	}
	public List<Interaction> getProblemInteractionList() {
		return problemInteractionList;
	}
	public void setProblemInteractionList(List<Interaction> problemInteractionList) {
		this.problemInteractionList = problemInteractionList;
	}
	public Interaction getEnactedInteraction() {
		return enactedInteraction;
	}
	public void setEnactedInteraction(Interaction enactedInteraction) {
		this.enactedInteraction = enactedInteraction;
	}
	public ErrorIndex getErrorIndex() {
		return errorIndex;
	}
	public void setErrorIndex(ErrorIndex errorIndex) {
		this.errorIndex = errorIndex;
	}
	
	
	public void incrementWrongNumber() {
		this.wrongNumber++;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getEnactedInteraction() + " "+ this.getProblemInteractionList()+" "+this.getWrongNumber();
	}
	
	
	
	
}
