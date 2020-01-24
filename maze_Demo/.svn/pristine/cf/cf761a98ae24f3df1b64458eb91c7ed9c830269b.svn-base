package dl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class test  {
	
	public Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();
	public Map<String , Interaction> INTERACTIONS = new HashMap<String , Interaction>() ;
	public int v_moveSucess = 10;
	public int v_moveFailture = -10;
	public int v_turn = -3;
	public int v_feelEmpty = 3;
	public int v_feelWall = -3;
	
	public Experience addOrGetExperience(String label,int actionType) {
		if (!EXPERIENCES.containsKey(label))
			EXPERIENCES.put(label, createExperience(label,actionType));
		return EXPERIENCES.get(label);
	}
	
	public Experience createExperience(String label,int actionType){
		return new Experience(label,actionType);
	}
	
	public Interaction addOrGetPrimitiveInteraction(Experience experience, int result, int valence) {
		String label = experience.getLabel()+result;
		if (!INTERACTIONS.containsKey(label)){
			Interaction interaction = createInteraction(label);
			interaction.setExperience(experience);
			interaction.setResult(result);
			interaction.setValence(valence);
			INTERACTIONS.put(label, interaction);			
		}
		Interaction interaction = INTERACTIONS.get(label);
		return interaction;
	}
	
	public Interaction createInteraction(String label) {
		return new Interaction(label);
	}
	
	
	
	public test() {
		
		Experience e0 = addOrGetExperience("e0",0);e0.resetAbstract();//move forward  1:sucess,0:fail
		Experience e1 = addOrGetExperience("e1",1);e1.resetAbstract();//turn left
		Experience e2 = addOrGetExperience("e2",2);e2.resetAbstract();//turn right
		Experience e3 = addOrGetExperience("e3",3);e3.resetAbstract();//feel front    0:feel empy,1:feel wall
		Experience e4 = addOrGetExperience("e4",4);e4.resetAbstract();//feel left     0:feel empy,1:feel wall
		Experience e5 = addOrGetExperience("e5",5);e5.resetAbstract();//feel right    0:feel empy,1:feel wall

		Interaction i01 = addOrGetPrimitiveInteraction(e0, 1, v_moveSucess);
		Interaction i00 = addOrGetPrimitiveInteraction(e0, 0, v_moveFailture);
		Interaction i11 = addOrGetPrimitiveInteraction(e1, 1, v_turn);
		Interaction i21 = addOrGetPrimitiveInteraction(e2, 1, v_turn);
		
		Interaction i31 = addOrGetPrimitiveInteraction(e3, 1, v_feelEmpty);
		Interaction i30 = addOrGetPrimitiveInteraction(e3, 0, v_feelWall);
		Interaction i41 = addOrGetPrimitiveInteraction(e4, 1, v_feelEmpty);
		Interaction i40 = addOrGetPrimitiveInteraction(e4, 0, v_feelWall);
		Interaction i51 = addOrGetPrimitiveInteraction(e5, 1, v_feelEmpty);
		Interaction i50 = addOrGetPrimitiveInteraction(e5, 0, v_feelWall);
		
		
		
		
		System.out.println("show before the interactions");
		show();
		System.out.println();
		List<Anticipation> anticipations = getDefaultAnticipations();
		System.out.println("show the before anticipations");
		showAnticipations(anticipations);
		System.out.println();
		updateInteractions2(i50);
		System.out.println("show the after  anticipations");
		showAnticipations(anticipations);
		System.out.println();
		
		System.out.println("show after  the interactions");
		show();
		
		
	}
	
	public void showAnticipations(List<Anticipation> anticipations) {
		// TODO Auto-generated method stub
		for(Anticipation anti:anticipations) {
			System.out.println(anti.getInteraction());
		}
		
	}

	public boolean isAllVisited() {
		boolean isAll = true;
		for(Interaction interaction: INTERACTIONS.values()) {
			if(interaction.isPrimitive()) {
				if(!interaction.isVisited)isAll = false;
			}
		}
		return isAll;
	}
	
	
	
	
	
	public void updateInteractions2(Interaction inte) {
		// TODO Auto-generated method stub
		for(Interaction interaction: INTERACTIONS.values()) {
			//if(interaction.equals(inte))
			interaction.setVisited(true);
		}
	}
	
	public List<Anticipation> getDefaultAnticipations(){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		for (Interaction interaction : this.INTERACTIONS.values()){
			if (interaction.isPrimitive()){
				Anticipation anticipation = new Anticipation(interaction, interaction.getTemps());
				anticipations.add(anticipation);
			}
		}
		return anticipations;
	}
	
	public void updateInteractions() {
		// TODO Auto-generated method stub
		for(Interaction interaction: INTERACTIONS.values()) {
			double templ = interaction.getTemps()*1.5;
			interaction.setTemps((int)templ);
		}
	}
	
	
	public void show() {
		// TODO Auto-generated method stub
		for(Interaction interaction: INTERACTIONS.values()) {
			System.out.println(interaction);
		}
	}
	
	
	
	private void clearList(List<String> stringList2) {
		// TODO Auto-generated method stub
		stringList2.clear();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test t1 = new test();
	}

	

}
