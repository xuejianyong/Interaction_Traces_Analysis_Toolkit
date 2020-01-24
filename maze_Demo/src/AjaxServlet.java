import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class AjaxServlet
 */
@WebServlet("/AjaxServlet")
public class AjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public int env[][] = {
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
	
	public String isInitialState = "";
	public String isFirstInteraction = "";
	public String direction = "";
	public boolean fromServlet = false;//用来表示后台的服务已经处理完成了。
	public Position nextPosition;
	public int actionIndex = 0;
	public double influentFactor = 0.9;//这是给前进方向的奖励
	public double gamma = 0.5;//这是传递给之前状态的概率更迭的力度。
	public double influentPredict = 0.6;//预测参数 
	public int valence = 0;
	public int valenceBump = 0;
	public int valenceMoveForward = 0;
	public int valenceTurn = 0;
	
	public String currentDirection;
	public Position currentPosition;
	public Position previousPosition;
	public String isBoundary;
	public String mostLikelyDirection;
	// actions: move forward, turn left, turn right, turn backward, feel the front.
	//在给定的少量的actions当中，然后给出不同的actions，这样会学会使用新的动作，学会新的behavioral patterns
	public String[] actions={"move_forward","turn_left","turn_right","turn_backward","feel_forward"};	
	public List<Position> positionList = new ArrayList<Position>();
	public String[] directions = {"up","right","down","left"};
	Random random = new Random();
	public Map<Position,Integer> envMap = new HashMap<Position,Integer>();
	
	
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
		String tempDirection = directions[random.nextInt(4)];//有四个不同的方向，随机选择一个方向
		initialPosition.setDirection(tempDirection);
		positionList.add(initialPosition);
		return initialPosition;
	}
	
	public void reactAction(Position currentPosition,int actionIndex,String currentDirection) {
		System.out.println("Begining of the function of reactAction() "
				+ "The coordinates are:("+currentPosition.getPointX()+","+currentPosition.getPointY()+") "
				+ "CurrentDirection is:("
						+ ""+currentDirection+"), Action is:("+actions[actionIndex]+")");
		switch(actionIndex) {
		case 0:
			boolean isBound = isBound(currentPosition,currentDirection);
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
		
		System.out.println("The next position is:"+indexX+","+indexY);
		int listLongth = positionList.size();
		System.out.println("the current length of the positionList is: "+listLongth);
		if(env[indexX][indexY] == 1) {//bumped,then update the serial positions of possibilities with different directions
			valence-=valenceBump;
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
			//根据当前bumped状态，来更新一些列的在不同方向上的概率
			
			nextPosition = positionList.get(listLongth-1);//由于bumped，所以 下一阶段的位置为当前位置，但是下一阶段需要转向，而不是继续前进。
			nextPosition.setBoump(true);
			System.out.println("The current position isBump state is: "+cp.isBoump());
			System.out.println("The agent bumped with the wall in this interaction !!!");
			//***********************方向的设置，当前已经bump，那么下一步不能 move forward,同时也要找到下一步的方向.***************************************************************
		}else {//success of moving forward.
			//Setting the member variable for the next position, need to predict different directions.
			/* *************************************************Math.pow(gamma,listLongth-1-i);***********************
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
			*/
			valence+=valenceMoveForward;
			Map<String,Double> tempcPMap = cp.getPosibilityMap();
			if(tempcPMap.containsKey(cD)) {
				for(String key : tempcPMap.keySet()) {
					if(key.equals(cD)) {
						Double tempPosibility = tempcPMap.get(key)*(1+influentFactor);
						tempcPMap.put(key, tempPosibility);
					}else {
						Double tempPosibility = tempcPMap.get(key)*(1-influentFactor/3);
						tempcPMap.put(key, tempPosibility);
					}
				}//for
			}//tempMap.containsKey(cD)
			
			Position tempNextPostion = new Position();
			tempNextPostion.setPointX(indexX);
			tempNextPostion.setPointY(indexY);
			tempNextPostion.setDirection(cD);
			
			//预设下一步的不同方向概率.influentPredict这里表示预设下一步不同方向概率的影响因子。这个影响因子也可以逐渐的改变。
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
		String tempMapString = "The updated posibilities for current position: ";
		for(String key : currentPosition.getPosibilityMap().keySet()) {//show the updated map in each postion.
			tempMapString = tempMapString+"("+key+","+currentPosition.getPosibilityMap().get(key)+")";
		}
		previousPosition = cp;
		System.out.println(tempMapString);
		return isBound;
	}
	
	public void updateEnvMap(Position cP) {
		for(Position key : envMap.keySet()) {//show the updated map in each postion.
			if(cP.getPointX() == key.getPointX() && cP.getPointY() == key.getPointY()) {
				int value = (int)envMap.get(key)+1;
				envMap.put(key, value);
			}
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
		Double biggestPosibility = (Double)obj[3];//取得最大概率，之后要找到最大概率的方向。
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
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("dl");
		isInitialState = request.getParameter("isInitialState");
		System.out.println("Entering the function of Servlet");
		System.out.println("The isInitialState is: "+isInitialState);
		if(isInitialState.equals("yes")) {
			currentPosition = getInitialPosition();
			direction = currentPosition.getDirection();
			
			//initial the environment map for counting experienced times of the agent.
			for(int i=0;i<env.length;i++) {
				for(int j=0;j<env[0].length;j++) {
					Position tempPo = new Position();
					tempPo.setPointX(i);
					tempPo.setPointY(j);
					envMap.put(tempPo, 0);
				}
			}
			
			JSONObject jsonObject = new JSONObject();  //创建Json对象
			try {
				jsonObject.put("coordiX",currentPosition.getPointX());
				jsonObject.put("coordiY", currentPosition.getPointY());
				jsonObject.put("direction", direction);
				jsonObject.put("isInitialState", "no");
				jsonObject.put("valence", valence);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println("The initial json is: "+jsonObject.toString());
			response.getWriter().write(jsonObject.toString());
		}else {// the isInitialState = "no"
			isFirstInteraction = request.getParameter("isFirstInteraction");
			valenceBump =  Integer.parseInt(request.getParameter("valenceBump"));
			valenceMoveForward = Integer.parseInt(request.getParameter("valenceMoveForward"));
			valenceTurn = Integer.parseInt(request.getParameter("valenceTurn"));
			System.out.println("the value of valence:"+valenceBump+" "+valenceMoveForward+" "+valenceTurn);
			
			if(isFirstInteraction.equals("yes")) {
				currentDirection = currentPosition.getDirection();
				actionIndex = 0;//************************************************************************
				reactAction(currentPosition,actionIndex,currentDirection);
				updateEnvMap(currentPosition);
				
				JSONObject jsonObject = new JSONObject();  //创建Json对象
				try {
					jsonObject.put("coordiX",currentPosition.getPointX());
					jsonObject.put("coordiY", currentPosition.getPointY());
					jsonObject.put("direction", currentPosition.getDirection());
					jsonObject.put("isFirstInteraction", "no");
					jsonObject.put("preCoordiX",previousPosition.getPointX());
					jsonObject.put("preCoordiY", previousPosition.getPointY());
					jsonObject.put("valence", valence);
					jsonObject.put("actionIndex", actionIndex);
					
					if(currentPosition.isBoump()) {
						isBoundary = "yes";
					}else {
						isBoundary = "no";
					}
					jsonObject.put("isBoundary", isBoundary);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(jsonObject.toString());
				response.getWriter().write(jsonObject.toString());
				
			}else {//isFirstInteraction == "no"
				currentPosition = positionList.get(positionList.size()-1);//从上一次的交互中得到当前的位置。
				currentDirection = currentPosition.getDirection();
				if(!currentPosition.isBoump()) {//没有bump
					//获取最有可能的方向
					mostLikelyDirection = getProbableDirection(currentPosition);//找到概率最大的方向。这是比较重要的一个环节。
					//检查一下当前方向是否是最大概率的方向
					
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
						actionIndex = 0;//如果概率最大的方向与当前的方向一致，则继续沿着这个方向走。
					}else {   
						//***********************转向需要增加转到的方向的概率*********************************
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
				
				JSONObject jsonObject = new JSONObject();  //创建Json对象
				try {
					jsonObject.put("coordiX",currentPosition.getPointX());
					jsonObject.put("coordiY", currentPosition.getPointY());
					jsonObject.put("direction", currentPosition.getDirection());
					jsonObject.put("isFirstInteraction", "no");
					jsonObject.put("preCoordiX",previousPosition.getPointX());
					jsonObject.put("preCoordiY", previousPosition.getPointY());
					jsonObject.put("valence", valence);
					jsonObject.put("actionIndex", actionIndex);
					
					
					if(currentPosition.isBoump) {
						isBoundary = "yes";
					}else {
						isBoundary = "no";
					}
					jsonObject.put("isBoundary", isBoundary);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(jsonObject.toString());
				response.getWriter().write(jsonObject.toString());
			}
		}
		
	}

	public int getRandowIndex(){
		int randomAction = random.nextInt(3);
		return randomAction;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	
	
	
}
