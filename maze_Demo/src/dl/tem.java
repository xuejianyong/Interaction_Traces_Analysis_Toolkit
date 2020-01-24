package dl;

import java.util.ArrayList;
import java.util.List;

public class tem {

	
	
	
	public Interaction selectOtherInteraction(Interaction enactedInteraction,int index) {
		System.out.println();
		System.out.println("Select interaction with other actiontype "+index);
		Interaction interaction = null;
		
		int actionType1 = enactedInteraction.getExperience().getAction();
		int triedTimes = actionMap.get(actionType1);
		actionMap.put(actionType1, triedTimes+1);
		
		/*
		for(Interaction inter: INTERACTIONS.values()) {
			if(inter.isPrimitive()) {
				if(inter.getExperience().getAction() != enactedInteraction.getExperience().getAction()){
					interaction = inter;
				}
			}
		}
		*/
		
		
		/*
		for(Interaction inter: INTERACTIONS.values()) {
			if(inter.isPrimitive()) {
				if(inter.getExperience().getAction() != enactedInteraction.getExperience().getAction()){
					interaction = inter;
				}
			}
		}
		*/
		
		/*
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(0);
		intList.add(1);
		intList.add(2);
		intList.add(3);
		intList.add(4);
		intList.add(5);
		intList.remove(enactedInteraction.getExperience().getAction());
		int randomIndex = random.nextInt(5);
		for(Interaction inter: INTERACTIONS.values()) {
			if(inter.isPrimitive()) {
				if(inter.getExperience().getAction() == randomIndex){
					interaction = inter;
				}
			}
		}
		*/
		
		
		
		System.out.println("the selected interaction is:"+interaction);
		return interaction;
	}
	
	
	

	/*
	for(Interaction inter: INTERACTIONS.values()) {
		if(inter.isPrimitive()) {
			if(inter.getExperience().getAction() != enactedInteraction.getExperience().getAction()){
				interaction = inter;
			}
		}
	}*/
	
	/*
	List<Integer> intList = new ArrayList<Integer>();
	intList.add(0);
	intList.add(1);
	intList.add(2);
	intList.add(3);
	intList.add(4);
	intList.add(5);
	int avoidActionNum = avoidActoinList.size();
	for(int avoid=0;avoid<avoidActionNum;avoid++) {
		intList.remove(avoidActoinList.get(avoid));
	}
	//intList.remove(enactedInteraction.getExperience().getAction());
	if(avoidActoinList.size() != 6) {
		int randomIndex = random.nextInt(6-avoidActionNum);
		for(Interaction inter: INTERACTIONS.values()) {
			if(inter.isPrimitive()) {
				if(inter.getExperience().getAction() == intList.get(randomIndex)){
					interaction = inter;
				}
			}
		}
	}else {
		for(Interaction inter: INTERACTIONS.values()) {
			if(inter.isPrimitive()) {
				if(inter.getExperience().getAction() != enactedInteraction.getExperience().getAction()){
					interaction = inter;
				}
			}
		}
	}
	avoidActoinList.clear();
	*/

	
	/*
	System.out.println("actionList is:"+actionList);
	for(Interaction inter1: INTERACTIONS.values()) {
		if(inter1.isPrimitive()) {
			if(actionList.size()<6) {
				if(!actionList.contains(inter1.getExperience().getAction())) {
					interaction = inter1;
				}
			}else {
				if(avoidActoinList.size()>0 && avoidActoinList.size()<6) {
					System.out.println("the void action process");
					System.out.println("avoidActoinList is:"+avoidActoinList);
					System.out.println("inter is:"+inter1);
					if(!avoidActoinList.contains(inter1.getExperience().getAction())){
						interaction = inter1;
						System.out.println("the action got for the next interacton");
						System.out.println("interaction is:"+interaction);
					}
					
					avoidActoinList.clear();
				}else {
					if(inter1.getExperience().getAction() != enactedInteraction.getExperience().getAction()){
						interaction = inter1;
					}
				}
			}
		}
	}
	*/
	
	
	//avoidActoinList
	
	/*
	for(Interaction inter: INTERACTIONS.values()) {
		if(inter.isPrimitive()) {
			if(avoidActoinList.size() != 0 && avoidActoinList.size()<6) {
				if(!avoidActoinList.contains(inter.getExperience().getAction())){
					interaction = inter;
				}
			}else {
				if(inter.getExperience().getAction() != enactedInteraction.getExperience().getAction()){
					interaction = inter;
				}
			}
		}
	}
	avoidActoinList.clear();*/
	
	
	//avoidance action selection 的问题
	if(bestSelection == null) {
		//在这种情况下需要排除之前没有达到valence的actions，在下一次的interaction当中避免这种action
		avoidActoinList.clear();
		avoidActoinList.add(enactedInteraction1.getExperience().getAction());
		
		if(multiInteractionList.size() > 0) {
			AvoidInteraction ai = new AvoidInteraction();
			ai.setEnactedInteraction(enactedInteraction1);
			List<Interaction> avoidInteractionList = new ArrayList<Interaction>();
			for(int g=0;g<multiInteractionList.size();g++) {
				List<Interaction> tempInteractionListForAvoiding = multiInteractionList.get(g);
				if(tempInteractionListForAvoiding.size()>1) {
					if(!avoidInteractionList.contains(tempInteractionListForAvoiding.get(1))) {
						avoidInteractionList.add(tempInteractionListForAvoiding.get(1));
					}
				}
			}//end for
			
			
			boolean isHaveAvoidInteracton = false;
			for(AvoidInteraction aitemp:aiList) {
				if(aitemp.getEnactedInteraction().equals(enactedInteraction1)) {
					isHaveAvoidInteracton = true;
				}
			}
			if(isHaveAvoidInteracton) {
				for(AvoidInteraction aitemp:aiList) {
					if(aitemp.getEnactedInteraction().equals(enactedInteraction1)) {
						List<Interaction> inteList = aitemp.getAvoidInteractionList();
						inteList.removeAll(avoidInteractionList);
						inteList.addAll(avoidInteractionList);
						aitemp.setAvoidInteractionList(avoidInteractionList);
					}
				}
			}else {
				ai.setAvoidInteractionList(avoidInteractionList);
				aiList.add(ai);
			}
			
		}
	}
	
	
	//-----------------------------------------------------------------------------
	
	
	 /*for (Interaction interaction : this.INTERACTIONS.values()){
			if (interaction.isPrimitive()){
				Anticipation anticipation = new Anticipation(interaction, 0);
				anticipations.add(anticipation);
			}
		}*/
	
	
	//-----------------------------------------------------------------------------
	

	/*
	//获取交互结果
	System.out.println("intendedInteraction is:"+intendedInteraction);
	int result;
	if(resultString.equals("yes"))result = 0;
	else result = 1;
	resultString = "";
	
	Interaction enactedInteraction1 = addOrGetPrimitiveInteraction(intendedInteraction.getExperience(),result,0);
	System.out.println("enactedInteraction is:"+enactedInteraction1);
	totalValence=totalValence+enactedInteraction1.getValence();
	
	
	boolean isContainsAction = false;
	Action targetAction = null;
	int targetActionType = intendedInteraction.getExperience().getAction();
	for(Action a:actionList) {
		if(a.getActionType() == targetActionType) {
			isContainsAction = true;
			targetAction = a;
		}
	}
	if(!isContainsAction) {
		Action action = new Action();
		action.setActionType(targetActionType);
		action.increaseNum();
		action.setTotalValence(enactedInteraction1.getValence());
		double meanActionValence = (double)action.getTotalValence()/(double)action.getNum();
		action.setMeanValence(meanActionValence);
		action.getEnactedList().add(enactedInteraction1);
		actionList.add(action);
	}else {
		targetAction.increaseNum();
		int totalActionValence = targetAction.getTotalValence()+enactedInteraction1.getValence();
		targetAction.setTotalValence(totalActionValence);
		double meanActionValence = (double)targetAction.getTotalValence()/(double)targetAction.getNum();
		targetAction.setMeanValence(meanActionValence);
		targetAction.getEnactedList().add(enactedInteraction1);
	}
	
	System.out.println("the mean valence of this action");
	for(Action a:actionList) {
		System.out.println(a.getActionType()+" "+a.getEnactedList()+" "+a.getMeanValence());
	}
	
	
	learnCompositeInteraction(enactedInteraction1);
	String interactionString = "";
	int compositeInteractionIndex = 0;
	for(Interaction inter: INTERACTIONS.values()) {
		if(!inter.isPrimitive()) {
			interactionString = interactionString+inter.toString()+"\r\n";
			compositeInteractionIndex++;
		}
	}
	System.out.println();
	//System.out.println("composite interactions are:"+" num:"+compositeInteractionIndex);
	//System.out.println(interactionString);

	
	this.setEnactedInteraction(enactedInteraction1);
	enactedInteractionList.add(enactedInteraction1);
	if (enactedInteraction1.getValence() >= 0) {
		this.setMood(Mood.PLEASED);
	}else {
		this.setMood(Mood.PAINED);
	}
	*/
	
	
	//----------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
}
