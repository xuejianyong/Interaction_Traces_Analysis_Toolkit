package dl;

import java.util.ArrayList;
import java.util.List;

public class AvoidInteraction {
	
	public Interaction enactedInteraction;
	public List<Interaction> avoidInteractionList = new ArrayList<Interaction>();
	
	
	public Interaction getEnactedInteraction() {
		return enactedInteraction;
	}
	public void setEnactedInteraction(Interaction enactedInteraction) {
		this.enactedInteraction = enactedInteraction;
	}
	public List<Interaction> getAvoidInteractionList() {
		return avoidInteractionList;
	}
	public void setAvoidInteractionList(List<Interaction> avoidInteractionList) {
		this.avoidInteractionList = avoidInteractionList;
	}
	
	
	

}
