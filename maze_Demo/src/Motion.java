import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class Motion{
	
	public int[][] env = {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,1,1,1,0,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1}};
	
	public int[][] map = {
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0}};
	
	public Map<Position,Integer> envMap = new HashMap<Position,Integer>();
	
	/**
	 * @param args
	 */
	public boolean isInitial = true;
	public String[] actions={"move_forward","turn_left","turn_right","turn_backward","feel_forward", };
	public String[] directions = {"up","right","down","left"};
	
	public Position currentPosition;
	public String currentDirection;
	public String mostLikelyDirection;
	public Position nextPosition;
	
	public List<Position> positionList = new ArrayList<Position>();
	public List<Experience> experienceList = new ArrayList<Experience>();//������¼ÿ��һ�Ľ���
	Random random = new Random();
	
	public double influentFactor = 0.9;//���Ǹ�ǰ������Ľ���
	public double gamma = 0.5;//���Ǵ��ݸ�֮ǰ״̬�ĸ��ʸ��������ȡ�
	
	public double influentPredict = 0.6;
	public int actionIndex = 0;
	public int loopFlag = 0;
	
	public int score = 0;
	
	public Motion(){
		//record the result from interactions
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
		String fileName = "ExperimentResult"+df.format(new Date()).toString();
		try {
			System.setOut(new PrintStream(new FileOutputStream("./Result/"+fileName+".txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(int i=0;i<env.length;i++) {
			for(int j=0;j<env[0].length;j++) {
				Position tempPo = new Position();
				tempPo.setPointX(i);
				tempPo.setPointY(j);
				envMap.put(tempPo, 0);
			}
		}

		System.out.println("The initial envMap is:");
		String initialMap = "";
		for(Position key : envMap.keySet()) {
			String tempInitialMap = "("+key.getPointX()+","+key.getPointY()+","+envMap.get(key)+")";
			initialMap = initialMap+tempInitialMap;
		}
		System.out.println(initialMap);
		
		
		while(loopFlag<100) {
			System.out.println("Interaction number is:"+loopFlag);
			if(isInitial) {
				
				System.out.println("-------------------------------------");
				System.out.println("The initialization work start! ");
				System.out.println("-------------------------------------");
				
				currentPosition = getInitialPosition();
				currentDirection = currentPosition.getDirection();
				actionIndex = 0;//���ѡȡһ������������ѡȡ����move_forward
				//int actionIndex = getRandomActionIndex();//getting the initial action for eacting
				System.out.println("The initialPosition is: ("+currentPosition.getPointX()+","+currentPosition.getPointY()+")");
				System.out.println("The initial direction is: "+currentDirection);
				String tempMapString = "Current postion directions posibilities: ";
				for(String key : currentPosition.getPosibilityMap().keySet()) {//show the updated map in each postion.
					tempMapString = tempMapString+"("+key+","+currentPosition.getPosibilityMap().get(key)+")";
				}
				System.out.println(tempMapString);
				System.out.println("The initial action is: "+actions[actionIndex]);
				
				reactAction(currentPosition,actionIndex,currentDirection);
				isInitial = false;
				updateEnvMap(currentPosition);
			}else {//!initial
				
				currentPosition = positionList.get(positionList.size()-1);//����һ�εĽ����еõ���ǰ��λ�á�
				currentDirection = currentPosition.getDirection();
				if(!currentPosition.isBoump) {//û��bump
					//��ȡ���п��ܵķ���
					mostLikelyDirection = getProbableDirection(currentPosition);//�ҵ��������ķ������ǱȽ���Ҫ��һ�����ڡ�
					//���һ�µ�ǰ�����Ƿ��������ʵķ���
					
					System.out.println("---------------------------------------------------------");
					System.out.println("Current direction possibility is: "+currentPosition.getPosibilityMap().get(currentDirection));
					String tempMapString = "Current posibilities for current postion: ";
					for(String key : currentPosition.getPosibilityMap().keySet()) {//show the updated map in each postion.
						tempMapString = tempMapString+"("+key+","+currentPosition.getPosibilityMap().get(key)+")";
					}
					System.out.println(tempMapString);
					boolean isLikylyDirection = checkIsLikelyDirection(currentPosition);
					System.out.println("isLikylyDirection: "+isLikylyDirection);
					System.out.println("---------------------------------------------------------");
					
					if(isLikylyDirection) {
						actionIndex = 0;//����������ķ����뵱ǰ�ķ���һ�£������������������ߡ�
					}else {   
						//*****************************************************************
						actionIndex = random.nextInt(2)+1;
					}
				}else {//bump, then need to turn around.
					actionIndex = random.nextInt(2)+1;
				}
				
				System.out.println("The currentPosition is: ("+currentPosition.getPointX()+","+currentPosition.getPointY()+")");
				System.out.println("The current direction is: "+currentDirection);
				String tempMapString = "Before entering the function reactAction: posibilities for current postion: ";
				for(String key : currentPosition.getPosibilityMap().keySet()) {//show the updated map in each postion.
					tempMapString = tempMapString+"("+key+","+currentPosition.getPosibilityMap().get(key)+")";
				}
				System.out.println(tempMapString);
				System.out.println("The initial action is: "+actions[actionIndex]);
				
				reactAction(currentPosition,actionIndex,currentDirection);
				updateEnvMap(currentPosition);
			}
			
			System.out.println("");
			System.out.println("");
			System.out.println("++++++++++++++++++++++++++++ next position ++++++++++++++++++++++++++++");
			loopFlag++;
		}
		
		System.out.println("The final envMap is:");
		String initialMap1 = "";
		for(Position key : envMap.keySet()) {
			String tempInitialMap1 = "("+key.getPointX()+","+key.getPointY()+","+envMap.get(key)+")";
			initialMap1 = initialMap1+tempInitialMap1;
			map[key.getPointX()][key.getPointY()] = envMap.get(key);
		}
		System.out.println(initialMap1);
		System.out.println();
		for(int i=0;i<map.length;i++) {
			String rowString = "";
			for(int j=0;j<map[0].length;j++) {
				rowString = rowString+ map[i][j]+",";
			}
			System.out.println(rowString);
		}
		
		
	}
	
	public boolean checkIsLikelyDirection(Position cP) {
		// TODO Auto-generated method stub
		boolean isLikylyDirection = true;
		Map<String,Double> tempProMap = cP.getPosibilityMap();
		Double cDPosibility = tempProMap.get(cP.getDirection());
		BigDecimal cDBig = new BigDecimal(cDPosibility);
		for(String key : tempProMap.keySet()) {
			Double tempposibility = (Double)tempProMap.get(key);
			BigDecimal tempBig = new BigDecimal(tempposibility); 
			if(cDBig.compareTo(tempBig) <0) {
				isLikylyDirection = false;
			}
		}
		return isLikylyDirection;
	}

	//find the biggest posibilities for the next interaction
	public String getProbableDirection(Position cP) {
		// TODO Auto-generated method stub
		Map<String,Double> tempProMap = cP.getPosibilityMap();
		Collection<Double> c = tempProMap.values();
		Object[] obj = c.toArray();
		String probableDirection = "";
		Arrays.sort(obj);
		Double biggestPosibility = (Double)obj[3];//ȡ�������ʣ�֮��Ҫ�ҵ������ʵķ���
		for(String key : tempProMap.keySet()) {
			List<String> tempBigestPosList = new ArrayList<String>();
			if(tempProMap.get(key) == biggestPosibility ) {
				tempBigestPosList.add(key);
			}
			if(tempBigestPosList.size()>1) {
				int randomDirectionIndex = random.nextInt(tempBigestPosList.size());
				probableDirection = tempBigestPosList.get(randomDirectionIndex);
			}
		}
		return probableDirection;
	}
	
	public Position getInitialPosition(){
		List<Position> availablePositionList = new ArrayList<Position>();
		int width = env.length;
		int length = env[0].length;
		for(int i=0;i<width;i++){
			for(int j=0;j<length;j++) {
				if(env[i][j] == 0) {
					Position temp = new Position();
					temp.setPointX(i);
					temp.setPointY(j);
					availablePositionList.add(temp);
				}
			}
		}
		int randomIndex = random.nextInt(availablePositionList.size());
		Position initialPosition = (Position)availablePositionList.get(randomIndex);
		String tempDirection = directions[random.nextInt(4)];//���ĸ���ͬ�ķ������ѡ��һ������
		initialPosition.setDirection(tempDirection);
		
		positionList.add(initialPosition);
		return initialPosition;
	}
	
	public boolean isBound(Position cp,String cD) {
		boolean isBound = false;
		int indexX = 0;
		int indexY = 0;
		switch(cD) {
		case "up":
			indexX = cp.getPointX()-1;
			indexY = cp.getPointY();
			break;
		case "right":
			indexX = cp.getPointX();
			indexY = cp.getPointY()+1;
			break;
		case "down":
			indexX = cp.getPointX()+1;
			indexY = cp.getPointY();
			break;
		case "left":
			indexX = cp.getPointX();
			indexY = cp.getPointY()-1;
			break;
		}
		
		int listLongth = positionList.size();
		System.out.println("listLongth is: "+listLongth);
		if(env[indexX][indexY] == 1) {//bumped,then update the serial positions of possibilities with different directions
			score-=10;
			isBound = true;
			for(int i=listLongth-1;(i>0 || i==0);i--) {
				Map<String,Double> tempMap = positionList.get(i).getPosibilityMap();
				if(tempMap.containsKey(cD)) {
					for(String key : tempMap.keySet()) {
						if(key.equals(cD)) {
							Double tempPosibility = tempMap.get(key)*(1-influentFactor)*Math.pow(gamma,listLongth-1-i);
							tempMap.put(key, tempPosibility);
						}else {
							Double tempPosibility = tempMap.get(key)*(1+influentFactor/3)*Math.pow(gamma,listLongth-1-i);
							tempMap.put(key, tempPosibility);
						}
					}//for 
				}//tempMap.containsKey(cD)
			}//for positionList.listLongth
			//���ݵ�ǰbumped״̬��������һЩ�е��ڲ�ͬ�����ϵĸ���
			nextPosition = positionList.get(listLongth-1);//����bumped������ ��һ�׶ε�λ��Ϊ��ǰλ�ã�������һ�׶���Ҫת�򣬶����Ǽ���ǰ����
			nextPosition.setBoump(isBound);
			//��������ã���ǰ�Ѿ�bump����ô��һ������ move forward,ͬʱҲҪ�ҵ���һ���ķ���.
			//**************************************************************************************
		}else {//success of moving forward.
			score+=10;
			//Setting the member variable for the next position, need to predict different directions.
			for(int i=listLongth-1;(i>0 || i==0);i--) {
				Map<String,Double> tempMap = positionList.get(i).getPosibilityMap();
				if(tempMap.containsKey(cD)) {
					for(String key : tempMap.keySet()) {
						if(key.equals(cD)) {
							Double tempPosibility = tempMap.get(key)*(1+influentFactor)*Math.pow(gamma,listLongth-1-i);
							tempMap.put(key, tempPosibility);
						}else {
							Double tempPosibility = tempMap.get(key)*(1-influentFactor/3)*Math.pow(gamma,listLongth-1-i);
							tempMap.put(key, tempPosibility);
						}
					}//for
				}//tempMap.containsKey(cD)
			}//for positionList.listLongth
			
			Position tempNextPostion = new Position();
			tempNextPostion.setPointX(indexX);
			tempNextPostion.setPointY(indexY);
			tempNextPostion.setDirection(cD);
			
			//Ԥ����һ���Ĳ�ͬ�������.influentPredict�����ʾԤ����һ����ͬ������ʵ�Ӱ�����ӡ����Ӱ������Ҳ�����𽥵ĸı䡣
			//*********************************************************************************
			Map<String,Double> tempNextMap = tempNextPostion.getPosibilityMap();
			for(String key : tempNextMap.keySet()) {
				if(key.equals(cD)) {
					Double tempNextPosibility = tempNextMap.get(key)*(1+influentPredict);
					tempNextMap.put(key, tempNextPosibility);
				}else {
					Double tempPosibility = tempNextMap.get(key)*(1-influentPredict/3);
					tempNextMap.put(key, tempPosibility);
					
				}
			}//end for
			nextPosition = tempNextPostion;
			positionList.add(nextPosition);
		}
		
		System.out.println("Is bumping with the wall? "+isBound);
		String tempMapString = "The updated posibilities for current postion: ";
		for(String key : currentPosition.getPosibilityMap().keySet()) {//show the updated map in each postion.
			tempMapString = tempMapString+"("+key+","+currentPosition.getPosibilityMap().get(key)+")";
		}
		System.out.println(tempMapString);
		return isBound;
	}
	
	public String turnDirection(String turnDirection) {
		
		return null;
	}

	public void reactAction(Position currentPosition,int actionIndex,String currentDirection) {
		System.out.println("Begining of the function of reactAction() "
				+ "The direction of currentPosition is:("
				+ ""+currentPosition.getDirection()+"), CurrentDirection is:("
						+ ""+currentDirection+"), Action is:("+actions[actionIndex]+")");
		switch(actionIndex) {
		case 0:
			boolean isBound = isBound(currentPosition,currentDirection);
			
			//updatePosibility(isBound,currentPosition,currentDirection);
			break;
		case 1://turn left
			switch(currentDirection) {
			case "up":
				currentDirection = "left";
				currentPosition.setDirection("left");
				break;
			case "right":
				currentDirection = "up";
				currentPosition.setDirection("up");
				break;
			case "down":
				currentDirection = "right";
				currentPosition.setDirection("right");
				break;
			case "left":
				currentDirection = "down";
				currentPosition.setDirection("down");
				break;
			}
			System.out.println("The updated direction is: "+currentDirection);
			currentPosition.setBoump(false);
			break;
		case 2://turn right
			switch(currentDirection) {
			case "up":
				currentDirection = "right";
				currentPosition.setDirection("right");
				break;
			case "right":
				currentDirection = "down";
				currentPosition.setDirection("down");
				break;
			case "down":
				currentDirection = "left";
				currentPosition.setDirection("left");
				break;
			case "left":
				currentDirection = "up";
				currentPosition.setDirection("up");
				break;
			}
			System.out.println("The updated direction is: "+currentDirection);
			currentPosition.setBoump(false);
			break;
		}//switch
		
		
	}
	
	
	public void updateEnvMap(Position cP) {
		// TODO Auto-generated method stub
		System.out.println("To update the EnvMap");
		for(Position key : envMap.keySet()) {//show the updated map in each postion.
			if(cP.getPointX() == key.getPointX() && cP.getPointY() == key.getPointY()) {
				int value = (int)envMap.get(key)+1;
				//System.out.println("the updated value is:"+value);
				envMap.put(key, value);
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Motion motion = new Motion();
	}
}
