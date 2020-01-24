package POMDP;

import java.io.IOException;
import java.util.Arrays;

public class POMDP {

	// IMPLEMENTATION OF POMDP (PARTIALLY OBSERVABLE MARKOV DECISION CHAIN)
	// for CPSC422 (Advanced Artificial Intelligence or Intelligent Systems) class in UBC

	// CODE WRITTEN BY: OSMAN HAJIYEV

	
	// State class defined here
	static class States {

		// Bunch of variables
		public int column;
		public int row;
		public double val = 0;
		public boolean invalid = false;//无效
		public boolean terminal = false;//终点
		public boolean wall1 = false;//是否是墙

		// Constructor for States class
		public States(int column, int row, double val){
			this.column = column;
			this.row = row;
			this.val = val;
		}	
	}

	// Predefining all the states inside the grid
	public static States state11, state12, state13, state21, state22, state23, state31, 
	state32, state33, state41, state42, state43;

	// table that contains all the states
	public static States[][] table;   


	// Initialize when starting state is not given
	public static void initializeStatesUnknown(){

		// 2-walls
		state11 = new States(1,1,0.111);
		state11.invalid = false;
		state11.wall1 = false;

		// 2-walls
		state12 = new States(1,2,0.111);
		state12.invalid = false;
		state12.wall1 = false;

		// 2-walls
		state13 = new States(1,3,0.111);
		state13.invalid = false;
		state13.wall1 = false;

		// 2-walls
		state21 = new States(2,1,0.111);
		state21.invalid = false;
		state21.wall1 = false;

		// INVALID STATE, VAL=0 ALWAYS
		state22 = new States(2,2,0);
		state22.invalid = true;

		// 2-walls
		state23 = new States(2,3,0.111);
		state23.invalid = false;
		state23.wall1 = false;

		// 1-wall
		state31 = new States(3,1,0.111);
		state31.invalid = false;
		state31.wall1 = true;

		// 1-wall
		state32 = new States(3,2,0.111);
		state32.invalid = false;
		state32.wall1 = true;

		// 1-wall
		state33 = new States(3,3,0.111);
		state33.invalid = false;
		state33.wall1 = true;

		// 2-walls
		state41 = new States(4,1,0.111);
		state41.invalid = false;
		state41.wall1 = false;

		// Terminal
		state42 = new States(4,2,0);
		state42.invalid = false;
		state42.terminal = true;

		// Terminal
		state43 = new States(4,3,0);
		state43.invalid = false;
		state43.terminal = true;

		table = new States[][] {{state11, state12, state13}, {state21, state22, state23}, 
			{state31, state32, state33}, {state41, state42, state43}}; 
	}

	// Initialize when state position is given
	public static void initializeStatesKnown(int column, int row){

		// 2-walls
		state11 = new States(1,1,0);
		state11.invalid = false;
		state11.wall1 = false;

		// 2-walls
		state12 = new States(1,2,0);
		state12.invalid = false;
		state12.wall1 = false;

		// 2-walls
		state13 = new States(1,3,0);
		state13.invalid = false;
		state13.wall1 = false;

		// 2-walls
		state21 = new States(2,1,0);
		state21.invalid = false;
		state21.wall1 = false;

		// INVALID STATE, VAL=0 ALWAYS
		state22 = new States(2,2,0);
		state22.invalid = true;

		// 2-walls
		state23 = new States(2,3,0);
		state23.invalid = false;
		state23.wall1 = false;

		// 1-wall
		state31 = new States(3,1,0);
		state31.invalid = false;
		state31.wall1 = true;

		// 1-wall
		state32 = new States(3,2,0);
		state32.invalid = false;
		state32.wall1 = true;

		// 1-wall
		state33 = new States(3,3,0);
		state33.invalid = false;
		state33.wall1 = true;

		// 2-walls
		state41 = new States(4,1,0);
		state41.invalid = false;
		state41.wall1 = false;

		// Terminal
		state42 = new States(4,2,0);
		state42.invalid = false;
		state42.terminal = true;

		// Terminal
		state43 = new States(4,3,0);
		state43.invalid = false;
		state43.terminal = true;

		// Assign states to table
		table = new States[][] {{state11, state12, state13}, {state21, state22, state23}, 
			{state31, state32, state33}, {state41, state42, state43}}; 

		// Set known state belief state to 1
		table[column-1][row-1].val = 1;
	}

	// Initialize when state position is given
		public static void arrayCopy(States[][] mainTable, States[][] tableCopy){

			for(int column = 0; column < mainTable.length; column++){
				for(int row = 0; row < mainTable[column].length; row++){
					tableCopy[column][row] = new States(mainTable[column][row].column, mainTable[column][row].row, mainTable[column][row].val);
					tableCopy[column][row].invalid = mainTable[column][row].invalid;
					tableCopy[column][row].terminal = mainTable[column][row].terminal;
				}
			}
			
		}
	
	// Belief state updater
	public static void beliefUpdate(String[] actions, String[] observations){
		for(int i = 0; i<actions.length; i++){

			// Perform formula: b'(s') = a*P(e|s')*E:P(S'|a,s)*b(s) (for every s)
			// (without a/alpha) P(e|s')*E:P(S'|a,s)*b(s) (for every s) Part of the formula is done inside goUp, goDown, goLeft, goRight functions
			// Normalization/alpha is computed and added inside normalization() function

			// When action is up
			//遍历所有的action，这里已经知道了action的序列了
			if(actions[i].equals("up")){
				//System.out.println("up");
				goUp(observations[i]);
				normalize();
				//printBelief();
			}

			// When action is down
			if(actions[i].equals("down")){
				goDown(observations[i]);
				normalize();
			}

			// When action is left
			if(actions[i].equals("left")){
				goLeft(observations[i]);
				normalize();
			}

			// When action is right
			if(actions[i].equals("right")){
				//System.out.println("right");
				goRight(observations[i]);
				normalize();
				//printBelief();
			}
		}
	}

	// The alpha part of the formula. Forces the sum of all belief states sum up to 1
	private static void normalize() {
		double sum = 0;
		// Compute sum
		for(States[] column: table){
			for(States element: column){
				sum = sum+element.val;
			}
		}
		// Normalize
		if(sum!=0){
			for(States[] column: table){
				for(States element: column){
					// Vi = Vi/(Sum of all Vx's)
					element.val = element.val/sum;
				}
			}
		}
	}

	private static void goUp(String observation) {
		
		// Function variables
		States[][] tableCopy = new States[4][3];
		arrayCopy( table, tableCopy );
		double valDown = 0, valLeft = 0, valRight = 0, evidenceProb = 0;

		// Loop over every element and update its belief state
		for(States[] column: tableCopy){
			for(States element: column){
				// System.out.println("column,row: " + element.column + " ," + element.row);
				int rowNum = element.row - 1;
				int columnNum = element.column - 1;
//				if(columnNum == 3){
//					System.out.println();
//				}

				// Check if state is valid or update is required
				if(!element.terminal && !element.invalid && !observation.equals("end")){

					// belief of state coming from down
					valDown = actionHelper(columnNum, rowNum, element, 1);
					//System.out.println("valDown " + valDown);
					// belief of state coming from left
					valLeft = actionHelper(columnNum, rowNum, element, 2);

					// belief of state coming from right
					valRight = actionHelper(columnNum, rowNum, element, 3);

					// Part P(e|s')
					if(element.column==3){
						if(observation.equals("1")){
							evidenceProb = 0.9;
						}
						else{
							evidenceProb = 0.1;
						}
					} else {
						if(observation.equals("1")){
							evidenceProb = 0.1;
						}
						else{
							evidenceProb = 0.9;
						}
					}

					// FORMULA without a (normalization), a is applied after this function
					element.val = evidenceProb*(0.8*valDown+0.1*valLeft+0.1*valRight);
				}
				else if(observation.equals("end")){
					for(States[] column1: tableCopy){
						for(States element1: column1){
							if(element1.terminal){
								valLeft = actionHelper(columnNum, rowNum, element, 2);
								valDown = actionHelper(columnNum, rowNum, element, 1);
								//System.out.println("valLeft: " + valLeft + " valDown: " + valDown);
								element.val = (0.8*valDown+0.1*valLeft);
								//element.val = 0.5;
							} else {
								element1.val = 0;
							}
						}
					}
				}	
			} // End of the inner for loop
		} // End of the outer for loop
		arrayCopy(tableCopy, table);
	}
	
	private static void goDown(String observation) {
		// Function variables
		States[][] tableCopy = new States[4][3];
		arrayCopy( table, tableCopy );
		double valUp = 0, valLeft = 0, valRight = 0, evidenceProb = 0;

		// Loop over every element and update its belief state
		for(States[] column: tableCopy){
			for(States element: column){
				// System.out.println("column,row: " + element.column + " ," + element.row);
				int rowNum = element.row - 1;
				int columnNum = element.column - 1;

				// Check if state is valid or update is required
				if(!element.terminal && !element.invalid && !observation.equals("end")){

					// belief of state coming from up
					valUp = actionHelper(columnNum, rowNum, element, 0);

					// belief of state coming from left
					valLeft = actionHelper(columnNum, rowNum, element, 2);

					// belief of state coming from right
					valRight = actionHelper(columnNum, rowNum, element, 3);

					// Part P(e|s')
					if(element.column==3){
						if(observation.equals("1")){
							evidenceProb = 0.9;
						}
						else{
							evidenceProb = 0.1;
						}
					}
					else{
						if(observation.equals("1")){
							evidenceProb = 0.1;
						}
						else{
							evidenceProb = 0.9;
						}
					}

					// FORMULA without a (normalization), a is applied after this function
					element.val = evidenceProb*(0.8*valUp+0.1*valLeft+0.1*valRight);
				}
				else if(observation.equals("end")){
					for(States[] column1: tableCopy){
						for(States element1: column1){
							if(element1.terminal){
								valLeft = actionHelper(columnNum, rowNum, element, 2);
								// valDown
								valUp = actionHelper(columnNum, rowNum, element, 1);
								element.val = (0.8*valUp+0.1*valLeft);
							} else {
								element1.val = 0;
							}
						}
					}
				}	
			} // End of the inner for loop
		} // End of the outer for loop
		arrayCopy(tableCopy, table);
	}

	
	private static void goRight(String observation) {
		// Function variables
		States[][] tableCopy = new States[4][3];
		arrayCopy( table, tableCopy );
		double valUp = 0, valLeft = 0, valDown = 0, evidenceProb = 0;

		// Loop over every element and update its belief state
		for(States[] column: tableCopy){
			for(States element: column){
				// System.out.println("column,row: " + element.column + " ," + element.row);
				int rowNum = element.row - 1;
				int columnNum = element.column - 1;

				// Check if state is valid or update is required
				if(!element.terminal && !element.invalid && !observation.equals("end")){

					// belief of state coming from down
					valUp = actionHelper(columnNum, rowNum, element, 0);

					// belief of state coming from left
					valLeft = actionHelper(columnNum, rowNum, element, 2);

					// belief of state coming from right
					valDown = actionHelper(columnNum, rowNum, element, 1);

					// Part P(e|s')
					if(element.column==3){
						if(observation.equals("1")){
							evidenceProb = 0.9;
						}
						else{
							evidenceProb = 0.1;
						}
					}
					else{
						if(observation.equals("1")){
							evidenceProb = 0.1;
						}
						else{
							evidenceProb = 0.9;
						}
					}

					// FORMULA without a (normalization), a is applied after this function
					element.val = evidenceProb*(0.1*valUp+0.8*valLeft+0.1*valDown);
				}
				else if(observation.equals("end")){
					for(States[] column1: tableCopy){
						for(States element1: column1){
							if(element1.terminal){
								valLeft = actionHelper(columnNum, rowNum, element, 2);
								// ValDown
								valUp = actionHelper(columnNum, rowNum, element, 1);
								element.val = (0.8*valUp+0.1*valLeft);
							} else {
								element1.val = 0;
							}
						}
					}
				}	
			} // End of the inner for loop
		} // End of the outer for loop
		arrayCopy(tableCopy, table);
	}
	
	private static void goLeft(String observation) {
		// Function variables
		States[][] tableCopy = new States[4][3];
		arrayCopy( table, tableCopy );
		double valUp = 0, valRight = 0, valDown = 0, evidenceProb = 0;

		// Loop over every element and update its belief state
		for(States[] column: tableCopy){
			for(States element: column){
				// System.out.println("column,row: " + element.column + " ," + element.row);
				int rowNum = element.row - 1;
				int columnNum = element.column - 1;

				// Check if state is valid or update is required
				if(!element.terminal && !element.invalid && !observation.equals("end")){

					// belief of state coming from down
					valUp = actionHelper(columnNum, rowNum, element, 0);

					// belief of state coming from right
					valRight = actionHelper(columnNum, rowNum, element, 3);

					// belief of state coming from right
					valDown = actionHelper(columnNum, rowNum, element, 1);

					// Part P(e|s')
					if(element.column==3){
						if(observation.equals("1")){
							evidenceProb = 0.9;
						}
						else{
							evidenceProb = 0.1;
						}
					}
					else{
						if(observation.equals("1")){
							evidenceProb = 0.1;
						}
						else{
							evidenceProb = 0.9;
						}
					}

					// FORMULA without a (normalization), a is applied after this function
					element.val = evidenceProb*(0.1*valUp+0.8*valRight+0.1*valDown);
				}
				else if(observation.equals("end")){
					for(States[] column1: tableCopy){
						for(States element1: column1){
							if(element1.terminal){
								valRight = actionHelper(columnNum, rowNum, element, 2);
								// ValDown
								valUp = actionHelper(columnNum, rowNum, element, 1);
								element.val = (0.8*valUp+0.1*valRight);
							} else {
								element1.val = 0;
							}
						}
					}
				}	
			} // End of the inner for loop
		} // End of the outer for loop
		arrayCopy(tableCopy, table);
	}
	

	// In the end print resulting belief state
	public static void printBelief(){
		for(States[] column: table){
			for(States element: column){
				System.out.println("(" + element.column + "," + element.row + ") - " + element.val);
			}
		}
	}

	// function to compute impact of neighboring belief states
	public static double actionHelper(int columnNum, int rowNum, States element, int fromWhere){
		// fromWhere = 0 when from up, 1 when from down, 2 when from left, 3 when from right
		double result = element.val;
		
		// from up
		if(fromWhere == 0){
			if(1<=element.row+1 && element.row+1<=3){
				if(!table[columnNum][rowNum+1].invalid && !table[columnNum][rowNum+1].terminal){
					result = table[columnNum][rowNum+1].val;
				}
			}
		} else
		
		// from down
		if(fromWhere == 1){
			if(1<=element.row-1 && element.row-1<=3){
				if(!table[columnNum][rowNum-1].invalid && !table[columnNum][rowNum-1].terminal){
					result = table[columnNum][rowNum-1].val;
				}
			}
		} else
			
		// from left
		if(fromWhere == 2){
			if(1<=element.column-1 && element.column-1<=3){
				if(!table[columnNum-1][rowNum].invalid && !table[columnNum-1][rowNum].terminal){
					result = table[columnNum-1][rowNum].val;
				}
			}
		} else
			
		// from right
		if(fromWhere == 3){
			if(1<=element.column+1 && element.column+1<=3){
				if(!table[columnNum+1][rowNum].invalid && !table[columnNum+1][rowNum].terminal){
					result = table[columnNum+1][rowNum].val;
				}
			}
		}
		return result;
	}

	public static void main(String[] args) throws IOException {

		// Examples

		// test 1
		String[] actions1 = {"up", "up", "up"};
		String[] observations1 = {"2", "2", "2"};

		// test 2
		String[] actions2 = {"up", "up", "up"};
		String[] observations2 = {"1", "1", "1"};

		// test 3
		String[] actions3 = {"right", "right", "up"};
		String[] observations3 = {"1", "1", "end"};
		// States initState2 = new States(2, 3, 1);

		// test 4
		String[] actions4 = {"up", "right", "right", "right"};
		String[] observations4 = {"2", "2", "1", "1"};
		// States initState1 = new States(1, 1, 1);

		System.out.println("TEST1:");
		initializeStatesUnknown();
		beliefUpdate(actions1, observations1);
		printBelief();
		System.out.println("");
		

		System.out.println("TEST2:");
		initializeStatesUnknown();
		beliefUpdate(actions2, observations2);
		printBelief();
		System.out.println("");

		
		System.out.println("TEST3:");
		initializeStatesKnown(2, 3);
		beliefUpdate(actions3, observations3);
		printBelief();
		System.out.println("");

		System.out.println("TEST4:");
		initializeStatesKnown(1, 1);
		beliefUpdate(actions4, observations4);
		printBelief();
		System.out.println("");
	}
}
