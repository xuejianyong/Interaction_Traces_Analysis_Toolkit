package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<String> stringList = new ArrayList<String>();
		Map<String,Double> posibilityMap = new HashMap<String,Double>();
		posibilityMap.put("up", 0.2);
		posibilityMap.put("right", 0.3);
		posibilityMap.put("down", 0.1);
		posibilityMap.put("left", 0.4);
		stringList.add("aaaaaa");
		stringList.add("cccccc");
		stringList.add("bbbbbb");
		
		
		Collection<Double> c = posibilityMap.values();
		Object[] obj = c.toArray();
		Arrays.sort(obj);
		//System.out.println(obj[0]+","+obj[1]+","+obj[2]+","+obj[3]);
		//System.out.println(posibilityMap.values());
		
		Double b1 = 0.024999999999999994;
		Double b2 = 0.024999999999999994;
		System.out.println(b1+" "+b2);
		if(b1<b2) {
			System.out.println("small");
		}else if(b1==b2){
			System.out.println("equal");
		}else {
			System.out.println("big");
		}
		
		System.out.println("");
		Double b3 = 0.024999999999999994;
		Double b4 = 0.024999999999999997;
		System.out.println(b3);
		System.out.println(b4);
		
		BigDecimal b31 = new BigDecimal(b3);
		BigDecimal b41 = new BigDecimal(b4);
		System.out.println(b31);
		System.out.println(b41);
		
		if(b31.compareTo(b41)<0) {
			System.out.println("small");
		}else if(b31.compareTo(b41)==0){
			System.out.println("equal");
		}else {
			System.out.println("big");
		}
		
		
		
		String str = "123";
		System.out.println(Integer.parseInt(str));
		
		/*
		List<Position> positionList = new ArrayList<Position>();
		Position p1 = new Position();
		p1.setDirection("up");
		Position p2 = new Position();
		p2.setDirection("down");
		
		positionList.add(p1);
		positionList.add(p2);
		for(int i=0;i<positionList.size();i++) {
			System.out.println(positionList.get(i).getDirection());
		}
		System.out.println("-------------------------------------------");
		p2.setDirection("up");
		for(int i=0;i<positionList.size();i++) {
			System.out.println(positionList.get(i).getDirection());
		}
		*/
		
		
	}
	
	
	
	/*
	private void updatePosibility(boolean isBound, Position cP, String cD) {
		// TODO Auto-generated method stub
		int listLongth = positionList.size();
		System.out.println("listLongth is: "+listLongth);
		for(int i=listLongth-1;(i>0 || i==0);i--) {
			Map<String,Double> tempMap = positionList.get(i).getPosibilityMap();
			if(tempMap.containsKey(cD)) {
				for(String key : tempMap.keySet()) {
					if(!isBound) {//move forward, update the posibilities for different directions.
						if(key.equals(cD)) {
							Double tempPosibility = tempMap.get(key)*(1+influentFactor)*Math.pow(gamma,listLongth-1-i);
							tempMap.put(key, tempPosibility);
						}else {
							Double tempPosibility = tempMap.get(key)*(1-influentFactor/3)*Math.pow(gamma,listLongth-1-i);
							tempMap.put(key, tempPosibility);
						}
					}else {//bumped, update the posibilities for different directions
						if(key.equals(cD)) {
							Double tempPosibility = tempMap.get(key)*(1-influentFactor)*Math.pow(gamma,listLongth-1-i);
							tempMap.put(key, tempPosibility);
						}else {
							Double tempPosibility = tempMap.get(key)*(1+influentFactor/3)*Math.pow(gamma,listLongth-1-i);
							tempMap.put(key, tempPosibility);
						}
					}
					
				}//for
			}//not contains
			String tempMapString = "";
			for(String key : tempMap.keySet()) {//show the updated map in each postion.
				tempMapString = tempMapString+"("+key+","+tempMap.get(key)+")";
			}
			System.out.println(tempMapString);
		}//for positionList
	}
	*/

	/*
	String probableDirection = getProbableDirection(currentPosition);
	if(currentDirection.equals(probableDirection)) {
		actionIndex = 0;
	}else {
		actionIndex = random.nextInt(2)+1;
	}*/
	
	
	
	
	
	//direction = request.getParameter("direction");
			//String actionIndex = request.getParameter("actionIndex");
			//System.out.println("The direction get from the page is:"+actionIndex);
			//indexAction = getRandowIndex();
			//System.out.println(indexAction);
			
			/*
			String json="{"; 
			json+="\"direction\":\"buzhidao\","; 
			json+="\"actionIndex\":\"buzhidao\"}"; 
			System.out.println(json);
			*/
			
			/*
			JSONObject jsonObject = new JSONObject();  //创建Json对象
			try {
				jsonObject.put("direction","direction");
				jsonObject.put("indexAction", "indexAction");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(jsonObject.toString());
			response.getWriter().write(jsonObject.toString());
			*/
	
	
	
	
	
	
	
}
