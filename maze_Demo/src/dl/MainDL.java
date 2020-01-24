package dl;

import java.util.HashMap;
import java.util.Map;



public class MainDL {

	public int v_moveSucess = 10;
	public int v_moveFailture = -10;
	public int v_turn = -3;
	public int v_feelEmpty = 3;
	public int v_feelWall = -3;
	
	public Position currentPosition;
	
	
	public Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();
	public Map<String , Interaction> INTERACTIONS = new HashMap<String , Interaction>() ;
	
	public int[][] env = {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,0,0,0,0,0,1},
			{1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,1,1,1,1,0,1},
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
	/* 0:move forward
	 * 1:turn 90 degrees to the left
	 * 2:turn 90 degrees to the right
	 * 3:feel the front
	 * 4:feel the left
	 * 5:feel the right
	 * //
	 */
	
	public void initial() {
		Experience e0 = addOrGetExperience("e0",0);//move forward  1:sucess,0:fail
		Experience e1 = addOrGetExperience("e1",1);//turn left
		Experience e2 = addOrGetExperience("e2",2);//turn right
		Experience e3 = addOrGetExperience("e3",3);//feel front    0:feel empy,1:feel wall
		Experience e4 = addOrGetExperience("e4",4);//feel left     0:feel empy,1:feel wall
		Experience e5 = addOrGetExperience("e5",5);//feel right    0:feel empy,1:feel wall

		Interaction i01 = addOrGetPrimitiveInteraction(e0, 1, v_moveSucess);
		Interaction i00 = addOrGetPrimitiveInteraction(e0, 0, v_moveFailture);
		Interaction i10 = addOrGetPrimitiveInteraction(e1, 0, v_turn);
		Interaction i20 = addOrGetPrimitiveInteraction(e2, 0, v_turn);
		
		Interaction i30 = addOrGetPrimitiveInteraction(e3, 0, v_feelEmpty);
		Interaction i31 = addOrGetPrimitiveInteraction(e3, 1, v_feelWall);
		Interaction i40 = addOrGetPrimitiveInteraction(e4, 0, v_feelEmpty);
		Interaction i41 = addOrGetPrimitiveInteraction(e4, 1, v_feelWall);
		Interaction i50 = addOrGetPrimitiveInteraction(e5, 0, v_feelEmpty);
		Interaction i51 = addOrGetPrimitiveInteraction(e5, 1, v_feelWall);
	}
	
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
	
	
	
	
	public MainDL() {
		initial();
		
		int index = 0;
		while(index<100) {
			
			
			
			
			
			
			
			index++;
		}
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new MainDL();
		
		/*initial the primitive experiences
		 * loop()
		 * start()
		 * initial(), 
		 * anticipations(), 
		 * intendedInteractions selection(), 
		 * enact(), 
		 * compositeInteraction()
		 * updatingParameters()
		 * end
		 * 
		 */
		
		
		System.out.println("asdfdf");
	}

}
