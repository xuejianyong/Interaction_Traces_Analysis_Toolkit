package dl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Anticipation  implements AnticipationInterface{

	public Experience experience;
	//public Interaction interaction;
	public int proclivity;
	public int visiteTimes;
	//public List<Interaction> anticipations;
	
	public List<Integer> actionList;//在activated的anticipation当中获取动作序列，然后按照第一个动作进行分组
	public List<Anticipation> anticipationList;//用来保存当前default interaction的activated interaction的序列
	public boolean isCategorized = false;  //用来标志当前的得到的proposed anticipation 在对应default anticipation的过程当中是否已经处理过，是否已经检测过了，在下次检测的时候会忽略
	public boolean isPrimitive = true; //说明该default anticipation当中的anticipationList为空，即为一个动作而已
	public boolean isInteractionPrimitive = false;//是否只有一个interaction的情况，如果该anticipation只有一个interaction， 为了保持一致，需要记录result，然后之后需要合成。
	public int interactionResult = 2;  //postInteraction 为 primitive interaction的情况，保存该primitive interaction的result
	public int compositeWeight = 0;
	
	public Interaction activatedInteraction;
	public List<Interaction> activatedInteractionList;
	
	
	
	
	
	

	public List<Interaction> getActivatedInteractionList() {
		return activatedInteractionList;
	}

	public void setActivatedInteractionList(List<Interaction> activatedInteractionList) {
		this.activatedInteractionList = activatedInteractionList;
	}

	public Interaction getActivatedInteraction() {
		return activatedInteraction;
	}

	public void setActivatedInteraction(Interaction activatedInteraction) {
		this.activatedInteraction = activatedInteraction;
	}

	public int getInteractionResult() {
		return interactionResult;
	}

	public void setInteractionResult(int interactionResult) {
		this.interactionResult = interactionResult;
	}

	public boolean isInteractionPrimitive() {
		return isInteractionPrimitive;
	}

	public void setInteractionPrimitive(boolean isInteractionPrimitive) {
		this.isInteractionPrimitive = isInteractionPrimitive;
	}

	public Anticipation(Experience experience, int i) {
		// TODO Auto-generated constructor stub
		this.experience = experience;
		this.proclivity = i;
	}
	
	public int getCompositeWeight() {
		return compositeWeight;
	}
	public void setCompositeWeight(int compositeWeight) {
		this.compositeWeight = compositeWeight;
	}

	public boolean isPrimitive() {
		return isPrimitive;
	}
	public void setPrimitive(boolean isPrimitive) {
		this.isPrimitive = isPrimitive;
	}

	public List<Anticipation> getAnticipationList() {
		return anticipationList;
	}
	public void setAnticipationList(List<Anticipation> anticipationList) {
		this.anticipationList = anticipationList;
	}

	public boolean isCategorized() {
		return isCategorized;
	}
	public void setCategorized(boolean isCategorized) {
		this.isCategorized = isCategorized;
	}

	public List<Integer> getActionList() {
		return actionList;
	}
	public void setActionList(List<Integer> actionList) {
		this.actionList = actionList;
	}
	
	public Experience getExperience() {
		return experience;
	}
	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	/*public Interaction getInteraction() {
		return interaction;
	}
	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}*/
	public int getProclivity() {
		return proclivity;
	}
	public void setProclivity(int proclivity) {
		this.proclivity = proclivity;
	}
	public int getVisiteTimes() {
		return visiteTimes;
	}
	public void setVisiteTimes(int visiteTimes) {
		this.visiteTimes = visiteTimes;
	}
	

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return ((Anticipation)obj).getExperience() == this.experience && ((Anticipation)obj).getInteractionResult() == this.interactionResult;
	} 
	
	public int compareTo(Anticipation anticipation1){
		return new Integer((anticipation1).getProclivity()).compareTo(this.proclivity);
	}
	public void addProclivity(int i) {
		// TODO Auto-generated method stub
		this.setProclivity(this.getProclivity()+i);
	}
	
	public void addCompositeWeight(int i) {
		// TODO Auto-generated method stub
		this.setCompositeWeight(this.getCompositeWeight()+i);
	}
	
	
	
}
