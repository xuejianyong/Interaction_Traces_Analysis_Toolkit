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
	
	public List<Integer> actionList;//��activated��anticipation���л�ȡ�������У�Ȼ���յ�һ���������з���
	public List<Anticipation> anticipationList;//�������浱ǰdefault interaction��activated interaction������
	public boolean isCategorized = false;  //������־��ǰ�ĵõ���proposed anticipation �ڶ�Ӧdefault anticipation�Ĺ��̵����Ƿ��Ѿ���������Ƿ��Ѿ������ˣ����´μ���ʱ������
	public boolean isPrimitive = true; //˵����default anticipation���е�anticipationListΪ�գ���Ϊһ����������
	public boolean isInteractionPrimitive = false;//�Ƿ�ֻ��һ��interaction������������anticipationֻ��һ��interaction�� Ϊ�˱���һ�£���Ҫ��¼result��Ȼ��֮����Ҫ�ϳɡ�
	public int interactionResult = 2;  //postInteraction Ϊ primitive interaction������������primitive interaction��result
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
