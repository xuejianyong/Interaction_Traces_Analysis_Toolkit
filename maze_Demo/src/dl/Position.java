package dl;
import java.util.HashMap;
import java.util.Map;

public class Position {

	public int pointX;
	public int pointY;
	
	public boolean isBoump = false;//表达的是上一步执行动作之后，是否bump,描述的是上一步的状态。
	public String direction;
	
	public Map<String,Double> posibilityMap = new HashMap<String,Double>(){{
		put("up", 0.25);
		put("right", 0.25);
		put("down", 0.25);
		put("left", 0.25);
	}};
	

	public Map<String, Double> getPosibilityMap() {
		return posibilityMap;
	}
	public void setPosibilityMap(Map<String, Double> posibilityMap) {
		this.posibilityMap = posibilityMap;
	}
	
	public boolean isBoump() {
		return isBoump;
	}
	public void setBoump(boolean isBoump) {
		this.isBoump = isBoump;
	}

	public double probabilityUp = 0.25;
	public double probabilityRight = 0.25;
	public double probabilityDown = 0.25;
	public double probabilityLeft = 0.25;
	
	public double getProbabilityUp() {
		return probabilityUp;
	}
	public void setProbabilityUp(double probabilityUp) {
		this.probabilityUp = probabilityUp;
	}
	public double getProbabilityRight() {
		return probabilityRight;
	}
	public void setProbabilityRight(double probabilityRight) {
		this.probabilityRight = probabilityRight;
	}
	public double getProbabilityDown() {
		return probabilityDown;
	}
	public void setProbabilityDown(double probabilityDown) {
		this.probabilityDown = probabilityDown;
	}
	public double getProbabilityLeft() {
		return probabilityLeft;
	}
	public void setProbabilityLeft(double probabilityLeft) {
		this.probabilityLeft = probabilityLeft;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public int getPointX() {
		return pointX;
	}
	public void setPointX(int pointX) {
		this.pointX = pointX;
	}
	public int getPointY() {
		return pointY;
	}
	public void setPointY(int pointY) {
		this.pointY = pointY;
	}
	
}
