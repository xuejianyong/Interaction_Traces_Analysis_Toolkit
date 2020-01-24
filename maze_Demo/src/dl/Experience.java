package dl;

public class Experience {
	
	public String label;
	public int action;
	public boolean isAbstract = true;//experience of composite interaction
	
	public Interaction intendedInteraction;
	public boolean isForEnacting = false;//�����Ƚ�intended interaction��enacted interaction,���ֻ�ǵ����Ķ���������Ҫ�Ƚϡ�
	public boolean isStable = true;//�����״ָ̬���Ǹ�action�Ƿ���Ըı�agent��״̬��
	
	
	
	
	
	public boolean isForEnacting() {
		return isForEnacting;
	}

	public void setForEnacting(boolean isForEnacting) {
		this.isForEnacting = isForEnacting;
	}

	public Interaction getIntendedInteraction() {
		return intendedInteraction;
	}

	public void setIntendedInteraction(Interaction intendedInteraction) {
		this.intendedInteraction = intendedInteraction;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}
	
	public void resetAbstract(){
		this.setAbstract(false);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public Experience(String label, int actionType) {
		// TODO Auto-generated constructor stub
		this.setLabel(label);
		this.setAction(actionType);
	}

	@Override
	public String toString() {
		return "( label:"+this.label+" action:"+this.action+")";
	}
	
}
