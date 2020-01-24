package dl;

import java.util.ArrayList;
import java.util.List;

public class CandidateInteraction {
	
	public int weight;
	public List<Interaction> candiInteractionList = new ArrayList<Interaction>();
	
	public int distance;
	public double meanValence;
	
	public boolean isStable;
	
	
	public int index;
	public int indexSnum;//success sum number
	public int indexFnum;//fail sum number
	public int indexTotal;//total executed times
	
	
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public List<Interaction> getCandiInteractionList() {
		return candiInteractionList;
	}
	public void setCandiInteractionList(List<Interaction> candiInteractionList) {
		this.candiInteractionList = candiInteractionList;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public double getMeanValence() {
		return meanValence;
	}
	public void setMeanValence(double meanValence) {
		this.meanValence = meanValence;
	}
	public boolean isStable() {
		return isStable;
	}
	public void setStable(boolean isStable) {
		this.isStable = isStable;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getIndexSnum() {
		return indexSnum;
	}
	public void setIndexSnum(int indexSnum) {
		this.indexSnum = indexSnum;
	}
	public int getIndexFnum() {
		return indexFnum;
	}
	public void setIndexFnum(int indexFnum) {
		this.indexFnum = indexFnum;
	}
	public int getIndexTotal() {
		return indexTotal;
	}
	public void setIndexTotal(int indexTotal) {
		this.indexTotal = indexTotal;
	}
	
	
	
	
	
	
	
}
