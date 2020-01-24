package dl;

public class Node {
	public Interaction interaction;
	public boolean isVisited=false;
	public int level;
	public Node upperNode;
	public int upperNodeDirection;//是上级节点的哪个方向
	public Node leftNode;
	public Node rightNode;
	public int numId;
	public boolean isLast = false;
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.interaction+" "+this.level+" "+this.upperNode+" "+this.numId;
	}

	public Node(Interaction inte) {
		// TODO Auto-generated constructor stub
		this.interaction = inte;
	}
	
	
	
	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public int getNumId() {
		return numId;
	}

	public void setNumId(int numId) {
		this.numId = numId;
	}

	public int getUpperNodeDirection() {
		return upperNodeDirection;
	}

	public void setUpperNodeDirection(int upperNodeDirection) {
		this.upperNodeDirection = upperNodeDirection;
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Node getUpperNode() {
		return upperNode;
	}
	public void setUpperNode(Node upperNode) {
		this.upperNode = upperNode;
	}
	public Node getLeftNode() {
		return leftNode;
	}
	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}
	public Node getRightNode() {
		return rightNode;
	}
	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}
	
	public Interaction getInteraction() {
		return interaction;
	}

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}

	public boolean isVisited() {
		return isVisited;
	}
	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
	

}
