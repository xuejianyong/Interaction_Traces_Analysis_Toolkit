package dl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Interaction {

	public String label;
	
	public Experience experience;
	public int result;
	public int valence;
	
	public Interaction preInteraction;
	public Interaction postInteraction; 
	
	public boolean isStable = true;//���interaction�Ƿ��ȶ���ָ�������κε�context���У�����õ�intended interaction = enacted interaction�Ľ��
	public boolean isReliable = true;//�Ƿ��������ָ�����������ִ��֮���Ƿ��õ���Ҫ�Ľ����Ҳ�����������ִ��֮�󣬵õ��Ľ����Ԥ����ͬ��
	//���������Ҫ����һ��˵���� ���interaction�Ƿ���Ըı�״̬����������ָ�����������ִ��֮���Ƿ�����𻷾��ı仯��������øö����Ƿ�ı价����
	//һ������ִ��֮�󣬵õ��Ľ��Ҳ�ǲ�ͬ�ģ��������Ҫ��Ҫ���ϣ�����Ҫ�ú�����
	
	public boolean isVisited = false;
	public int visitedTimes = 0;
	public int level = 0;
	
	
	
	public int weight=0;// the importance
	public int temps=1;// wait time
	public int trust = 0;
	public double intermediate=1;//�м������������Ϊ�����Ĭ����ʱ������
	
	
	public Interaction compositeInteraction;
	
	
	//public boolean isVisited = false;
	//public int visitedTimes;
	public List<CandidateInteraction> candidateList = new ArrayList<CandidateInteraction>();
	
	
	
	
	//public Map<Experience,Integer> numberOfTriedMap = new HashMap<Experience, Integer>();
	
	
	
	public List<CandidateInteraction> getCandidateList() {
		return candidateList;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isReliable() {
		return isReliable;
	}

	public void setReliable(boolean isReliable) {
		this.isReliable = isReliable;
	}

	public boolean isStable() {
		return isStable;
	}

	public void setStable(boolean isStable) {
		this.isStable = isStable;
	}

	public Interaction getCompositeInteraction() {
		return compositeInteraction;
	}

	public void setCompositeInteraction(Interaction compositeInteraction) {
		this.compositeInteraction = compositeInteraction;
	}

	public int getVisitedTimes() {
		return visitedTimes;
	}

	public void setVisitedTimes(int visitedTimes) {
		this.visitedTimes = visitedTimes;
	}

	public void setCandidateList(List<CandidateInteraction> candidateList) {
		this.candidateList = candidateList;
	}

	public int getTrust() {
		return trust;
	}

	public void setTrust(int trust) {
		this.trust = trust;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public double getIntermediate() {
		return intermediate;
	}

	public void setIntermediate(double intermediate) {
		this.intermediate = intermediate;
	}

	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Interaction(String label) {
		// TODO Auto-generated constructor stub
		this.setLabel(label);
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Interaction getPreInteraction() {
		return preInteraction;
	}

	public void setPreInteraction(Interaction preInteraction) {
		this.preInteraction = preInteraction;
	}

	public Interaction getPostInteraction() {
		return postInteraction;
	}

	public void setPostInteraction(Interaction postInteraction) {
		this.postInteraction = postInteraction;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getValence() {
		return valence;
	}

	public void setValence(int valence) {
		this.valence = valence;
	}
	
	public boolean isPrimitive(){
		return this.getPreInteraction() == null;
	}
	
	public void incrementTemps() {
		intermediate = intermediate*1.5;
		this.setTemps((int)intermediate);
		//this.temps++;
	}
	
	public void incrementWeight() {
		this.weight++;
	}
	
	public void incrementTrust() {
		this.trust++;
	}
	
	public void decreaseWeight() {
		this.weight--;
	}
	
	public void incrementVisitedTimes() {
		this.visitedTimes++;
	}
	
	@Override
	public String toString(){
		//return "(label:"+this.getLabel() + " valence:" + this.getValence() + " weight:" + this.weight+" isVisited:"+this.isVisited+" time:"+this.temps+")";
		return ""+this.getLabel() + "_" + this.getValence() + "_" + this.weight+"_"+this.level;
	}
}
