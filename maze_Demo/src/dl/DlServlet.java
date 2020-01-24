package dl;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;





/**
 * Servlet implementation class DlServlet
 */
@WebServlet("/DlServlet")
public class DlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*public int env[][] = {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1}, 
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1}};*/

	public int env[][] = {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,1,1},
			{1,0,1,1,1,1,1,0,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,0,1,1,1,1,1,0,1},
			{1,1,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1}};
	
	public String isInitialState = "";
	public String direction = "";
	public int v_moveSucess = 0;
	public int v_moveFailture = 0;
	public int v_turn = 0;
	public int v_feelEmpty = 0;
	public int v_feelWall = 0;
	
	
	public Position randomPosition;
	public List<Position> positionList = new ArrayList<Position>();
	public String[] directions = {"up","right","down","left"};
	public enum Mood {SATISFIED, FRUSTRATED, BORED, PAINED, PLEASED};//对结果满意，不满意，无聊，不高兴，高兴
	public Mood mood;
	Random random = new Random();
	
	public Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();
	public Map<String , Interaction> INTERACTIONS = new HashMap<String , Interaction>() ;
	public Interaction enactedInteraction = null;
	public Interaction intendedInteraction = null;
	public Interaction superInteraction = null;
	
	public List<Interaction> enactedInteractionList = new ArrayList<Interaction>();
	public List<Interaction> novelEnactedInteractionList = new ArrayList<Interaction>();
	public boolean isNovel = false;
	
	//public String resultString = "";
	public int lamda = 6;
	public int loopNum = 1;
	
	public List<Interaction> satisfyList = new ArrayList<Interaction>();
	public List<Action> actionList = new ArrayList<Action>();
	public List<Integer> intendedInteractionActionList = new ArrayList<Integer>();
	public List<Integer> avoidActoinList = new ArrayList<Integer>();
	public List<Problem> problemList = new ArrayList<Problem>();
	public int contextScope = 2;
	public List<AvoidInteraction> aiList = new ArrayList<AvoidInteraction>();
	
	public String beliefState = "unknown";
	public List<Interaction> beliefInteractionList =new ArrayList<Interaction>();
	public List<Interaction> sporadicInteractionList = new ArrayList<Interaction>();
	public boolean isCheck = false;

	public int maxValence = 0;
	public int minValence = 0;
	public boolean isMaxValence = false;
	public boolean isMinValence = false;
	public List<Interaction> intendedInteractionList = null;
	public boolean isCheckList = false;
	public int sequenceIndex = 1;
	
	public int totalValence = 0;
	
	public String isReady = "";
	
	public List<List<Integer>> drawInteractionList = new ArrayList<List<Integer>>();
	public String drawInteractionString = "";
	public String drawAnticipationString = "";
	
	
	//public boolean isInSelectionProcess = true; //这个变量估计会有用
    public boolean isGetCompositeInteraction = false; //是否处于enact composite interaction的状态，如果处于enact的状态，则需要直接去按照composite interaction的顺序去执行
    public boolean isEnactCompositeInteraction = false;  //这个状态是为了区别，在selection操作之后，得到了一个composite interaction，那么接下来就要开始enact这个composite interaction
    public Interaction compositeInteractionForEnacting = null; //用于承担composite interaction，逐次的去enact其中的primitive interaction
    
    public Stack<Node> nodeStack = new Stack<Node>();
    public boolean isFirstNode = true;
    public Interaction previousEnactedCompositeInteraction = null;
    
    public boolean isFirstConstructNodeList = true; //由于得到的composite interaction具有一定的层次结构，所以在刚开始的时候，需要依据这个composite interaction的结构来进行一次节点构建的操作
    public List<Node> nodeList = new ArrayList<Node>();
    public Node node = null;
    //public Node root = null;
    public Stack<Interaction> interactionStack = new Stack();//用于构建enactedCompositeInteraction
    
    public int bumpTimes = 0;
    public String isFinishString = "no";
    public boolean isPrimitiveIntendedInteraction = false;
    public String isFirstEnactedcompositeInteraction = "no"; //是否是正要enact 的 composite interaction的第一个节点
    public String isInEnactedCompositeInteraction = "no"; // 当前是否在enact composite interaction过程当中
    public String compositeInteractionForEnactingString = ""; //当前enact composite interaction的串是什么
    String resultEnactedcompositeInteractionString = "";
    
    public int threshold = 3;
    public int stepNumber = 0;
                  
    
    
	/**
     * @see HttpServlet#HttpServlet()
     */
	/*
    public DlServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    */

	public Interaction getEnactedInteraction() {
		return enactedInteraction;
	}

	public Mood getMood() {
		return mood;
	}

	public void setMood(Mood mood) {
		this.mood = mood;
	}

	public Interaction getIntendedInteraction() {
		return intendedInteraction;
	}

	public void setIntendedInteraction(Interaction intendedInteraction) {
		this.intendedInteraction = intendedInteraction;
	}

	public Interaction getSuperInteraction() {
		return superInteraction;
	}

	public void setSuperInteraction(Interaction superInteraction) {
		this.superInteraction = superInteraction;
	}

	public void setEnactedInteraction(Interaction enactedInteraction) {
		this.enactedInteraction = enactedInteraction;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------------------------------");
		System.out.println();
		
		
		isInitialState = request.getParameter("isInitialState");//用于区分初始化环境与交互过程
		
		//这里存在一个问题，初始状态的位置和方向对于后续交互过程的影响。
		if(isInitialState.equals("yes")) {
			//在初始化程序方面，java程序主要提供一个agent在环境当中的初始位置和一个初始方向。
			System.out.println("isInitialState");
			
			//所有的变量进行初始化, 为reset操作做准备
			loopNum = 1;
			EXPERIENCES.clear();
			INTERACTIONS.clear();
	
			this.enactedInteraction = null;
			this.intendedInteraction = null;
			this.superInteraction = null;
			totalValence = 0;
			enactedInteractionList.clear();
			
			isGetCompositeInteraction = false;
			isEnactCompositeInteraction = false;
			compositeInteractionForEnacting = null;
			
			nodeStack.clear();
			isFirstNode = true;
			previousEnactedCompositeInteraction = null;
			isFirstConstructNodeList = true;
			nodeList.clear();
			node = null;
			interactionStack.clear();//用于组建 enactCompositeInteraction
			
			
			
			randomPosition = getInitialPosition();//这里需要好好想想
			direction = randomPosition.getDirection();//这里需要好好想想
			isReady = "ok";
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("coordiX",randomPosition.getPointX());
				jsonObject.put("coordiY", randomPosition.getPointY());
				jsonObject.put("direction", direction);
				jsonObject.put("isInitialState", "no");
				jsonObject.put("totalValence",totalValence);
				jsonObject.put("isReady",isReady);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.getWriter().write(jsonObject.toString());
		}else {
			
			
			
			/***************************************** 交互由此开始   ***************************************/
			//开始启动交互，先扫描环境更新，然后开始交互过程
			String envString = request.getParameter("env");
			String[] envSmall = envString.split(",");
			for(int i=0;i<100;i++) {
				int row = i/10;
				int colume = i%10;
				env[row][colume] = Integer.parseInt(envSmall[i]);
			}
			
			//在java后台显示环境更新的情况
			/*int width = env.length;
			int height = env[0].length;
			System.out.println("the size of env is:"+width+" "+height);
			for(int k=0;k<width;k++) {
				for(int z=0;z<height;z++) {
					if(env[k][z] == 1)System.out.print("*");
					else if(k==randomPosition.getPointX() && z==randomPosition.getPointY()) {
						System.out.print("+");
					}else {
						System.out.print("-");
					}
				}
				System.out.println();
			}*/
			
			
			
			if(loopNum == 1) {//进行第一次交互
				System.out.println("-------------------------- The beginning of the interaction -----------------------------------------------");
				System.out.println("loop number is:"+loopNum);
				System.out.println("The first interaction and the all values initialization process.......");
				
				//得到交互相关action的valence值
				v_moveSucess = Integer.parseInt(request.getParameter("v_move_forward"));
				v_moveFailture = Integer.parseInt(request.getParameter("v_bump"));
				v_turn = Integer.parseInt(request.getParameter("v_turn"));
				v_feelEmpty = Integer.parseInt(request.getParameter("v_feel_empty"));
				v_feelWall = Integer.parseInt(request.getParameter("v_feel_wall"));
				System.out.println("parameters are{v_moveSucess:"+v_moveSucess+" v_moveFailture:"
						+ ""+v_moveFailture+" v_turn:"+v_turn+" v_feelEmpty:"+v_feelEmpty+" v_feelWall:"+v_feelWall);
				
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
				
				//默认的动作组，这样可以在intendedInteraction与enactedInteraction的比较当中知道动作与interaction的区别
				//Interaction i0a = addOrGetPrimitiveInteraction(e0, result, valence);
				
				//初始化每个experience的intended interaction，这样减少对于INTERACTIONS的遍历, 这里存在问题
				e0.setIntendedInteraction(i01);
				e1.setIntendedInteraction(i11);
				e2.setIntendedInteraction(i21);
				e3.setIntendedInteraction(i30);
				e4.setIntendedInteraction(i40);
				e5.setIntendedInteraction(i50);
				
				
				//第一次交互，agent目前处于位置状态
				//从primitive interactions当中挑选，或者从experience or action当中挑选？这块需要好好想想
				System.out.println("The first interaction...");
				drawInteractionString = "";
				drawAnticipationString = "";
				
				List<Anticipation> anticipations = anticipate();
				intendedInteraction = selectInteraction(anticipations).getExperience().getIntendedInteraction();
				System.out.println("intendedInteraction is: "+intendedInteraction);
				
				int actionType = intendedInteraction.getExperience().getAction();
				int drawResult = intendedInteraction.getResult();
				
				int interactResult = enact(actionType,randomPosition);
				Interaction enactedInteraction1 = addOrGetPrimitiveInteraction(intendedInteraction.getExperience(),interactResult,0);
				System.out.println("enactedInteraction is: "+enactedInteraction1);
				stepNumber+=1;
				//对于这种单独的交互内容，还需要对于目前的cognitive architecture进行分析，将这部分的实现内容放在算法当中。
				/*if(intendedInteraction.equals(enactedInteraction1)){
					intendedInteraction.setReliable(true);
				}else {
					intendedInteraction.setReliable(false);
				}*/
				int enactedValence = enactedInteraction1.getValence();
				totalValence=totalValence+enactedInteraction1.getValence();
				
				learnCompositeInteraction(enactedInteraction1);
				
				this.setEnactedInteraction(enactedInteraction1);
				enactedInteractionList.add(enactedInteraction1);
				if (enactedInteraction1.getValence() >= 0) {
					this.setMood(Mood.PLEASED);
				}else {
					this.setMood(Mood.PAINED);
				}
				
				isReady = "ok";
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("actionType",actionType);
					jsonObject.put("drawResult",drawResult);
					//jsonObject.put("resultString",resultString);
					jsonObject.put("totalValence",totalValence);
					jsonObject.put("loopNum",loopNum);
					loopNum++;
					jsonObject.put("isReady",isReady);
					//jsonObject.put("compositeString",compositeString);
					jsonObject.put("drawInteractionString",drawInteractionString);//当前学习到的composite interaction的字符串
					jsonObject.put("drawAnticipationString", drawAnticipationString);//本次交互当中的anticipation字符串
					//jsonObject.put("isclickEnv",isclickEnv);
					jsonObject.put("enactedValence", enactedValence);
					jsonObject.put("bumpTimes", bumpTimes);
					//jsonObject.put("isEnactCompositeInteraction", isEnactCompositeInteraction);
					
					//jsonObject.put("compositeInteractionForEnactingString", compositeInteractionForEnactingString);//当前enact composite interaction的 string
					//jsonObject.put("resultEnactComIntString", resultEnactedcompositeInteractionString);
					//jsonObject.put("isFinishString", isFinishString);
					
					
					jsonObject.put("enactComIntString", compositeInteractionForEnactingString);
					jsonObject.put("resultEnactComIntString", resultEnactedcompositeInteractionString);
					jsonObject.put("isFinishString", isFinishString);
					jsonObject.put("isFirstEnaComInteraction", isFirstEnactedcompositeInteraction);
					jsonObject.put("isInEnaComInteraction", isInEnactedCompositeInteraction);
					jsonObject.put("stepNumber", stepNumber);
					
					
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				response.getWriter().write(jsonObject.toString());
			}else {// 第二次和后续的交互
				System.out.println("-------------------------- The beginning of the algorithm -----------------------------------------------");
				System.out.println("loop number is:"+loopNum);
				
				//get the enacted interaction, novelty could be produced in this part. be used for comparing the intended interaction with enacted interaction
				/*********************************************************************************/
				Interaction enactedInteraction1 = this.getEnactedInteraction();//get the previous enacted interaction 在reasoning方法中用到
				
				
				if(!isGetCompositeInteraction ) {
					//没有在enact composite interaction 的进程当中，普通的交互过程
					//进入 get anticipations 和 select anticipations 处理过程当中
					drawInteractionString = "";
					drawAnticipationString = "";
					
					isPrimitiveIntendedInteraction = false;
					isFinishString = "no";
					
					isFirstEnactedcompositeInteraction = "no";//yes or no 方便在页面当中使用
					isInEnactedCompositeInteraction = "no"; //yes  or no 方便在页面当中使用
					compositeInteractionForEnactingString = "";
					resultEnactedcompositeInteractionString = "";
					
					
					List<Anticipation> anticipations = anticipate();
					Anticipation selectedAnticipation = selectInteraction(anticipations);
					//testici 标记
					
					Interaction selectedInteraction = null;
					if(selectedAnticipation.isPrimitive()) {//得到的default anticipation当中anticipationList为空，直接执行动作即可
						selectedInteraction = selectedAnticipation.getExperience().getIntendedInteraction();
					}else {
						//在anticipationList当中有内容，这里需要分清第一个是single或者multiple的情形
						Anticipation optimalAnticipation = selectedAnticipation.getAnticipationList().get(0);
						if(optimalAnticipation.isInteractionPrimitive()) {//anticipationList当中选中的为single interaction的情形
							System.out.println("The best choice of the anticipation interaction is Primitive");
							selectedInteraction = addOrGetPrimitiveInteraction(optimalAnticipation.getExperience(),optimalAnticipation.getInteractionResult(),0);//得到原始的primitive postInteraction
						}else {//anticipationList 选中的为 multiple interaction的的情形
							System.out.println("The best choice of the anticipation interaction is composite and its weight is:"+optimalAnticipation.getCompositeWeight());
							if(optimalAnticipation.getCompositeWeight()>threshold && optimalAnticipation.getProclivity()>0) {
								System.out.println("The best choice of the anticipation interaction's weight plus than threshold and proclivity is positive");
								selectedInteraction = optimalAnticipation.getExperience().getIntendedInteraction();
							}else if(optimalAnticipation.getCompositeWeight()<=threshold && optimalAnticipation.getProclivity()>0){
								System.out.println("The best choice of the anticipation interaction's weight less or equal than threshold and proclivity is positive");
								selectedInteraction = optimalAnticipation.getExperience().getIntendedInteraction().getPreInteraction();
							}else {
								System.out.println("The best choice of the anticipation interaction's weight less than threshold or proclivity is negative");
								selectedInteraction = farLeftInteraction(optimalAnticipation.getExperience().getIntendedInteraction());
							}
						}
						
						
						//anticipation 当中的anticipationList不是空的，进入enacted (single/multiple) interaction的情况
						/*if(selectedAnticipation.getCompositeWeight()>=4) {
							if(selectedAnticipation.getAnticipationList().get(0).isInteractionPrimitive()) {
								System.out.println("The best choice of the anticipation interaction is Primitive and its weight plus 4");
								selectedInteraction = addOrGetPrimitiveInteraction(selectedAnticipation.getAnticipationList().get(0).getExperience(),
										selectedAnticipation.getAnticipationList().get(0).getInteractionResult(),0);
							}else {
								System.out.println("The best choice of the anticipation interaction is composite and its weight plus 4");
								selectedInteraction = selectedAnticipation.getAnticipationList().get(0).getExperience().getIntendedInteraction();
							}
							//selectedInteraction = selectedAnticipation.getExperience().getIntendedInteraction();
						}else {
							if(selectedAnticipation.getAnticipationList().get(0).isInteractionPrimitive()) {
								System.out.println("The best choice of the anticipation interaction is Primitive and its weight lower than 4");
								selectedInteraction = addOrGetPrimitiveInteraction(selectedAnticipation.getAnticipationList().get(0).getExperience(),
										selectedAnticipation.getAnticipationList().get(0).getInteractionResult(),0);
							}else {
								System.out.println("The best choice of the anticipation interaction is composite and its weight lower than 4");
								selectedInteraction = farLeftInteraction(selectedAnticipation.getAnticipationList().get(0).getExperience().getIntendedInteraction());
							}
							//selectedInteraction = farLeftInteraction(selectedAnticipation.getExperience().getIntendedInteraction());
						}*/
					}
					
					//Interaction selectedInteraction = selectInteraction(anticipations).getIntendedInteraction();	
					
					if(selectedInteraction.isPrimitive()) {
						//得到的intendedInteraction是一个primitive的interaction
						intendedInteraction = selectedInteraction;
						isPrimitiveIntendedInteraction = true;
					}else {
						System.out.println("the intended interaction is composite interaction");
						System.out.println("the intended composite interaction is: "+selectedInteraction);
						compositeInteractionForEnacting = selectedInteraction;  //用于enact的完整的composite interaction
						compositeInteractionForEnactingString = compositeInteractionForEnacting.toString();
						isGetCompositeInteraction = true;  //开始进入enact composite interaction状态
						isEnactCompositeInteraction = true;  //是否处于enact composite interaction的状态
						isFirstConstructNodeList = true;    //用于构建节点，在第一次获得composite interaction之后，开始构建，之后停止构建，直到enact过程结束。
						
						isFirstEnactedcompositeInteraction = "yes";//是composite interaction的第一个的节点，在页面当中要记录当前的x坐标值
						isInEnactedCompositeInteraction = "yes"; //已经进入了enact composite interaction的过程当中
						
					}
					
					//在获取到了intended interactin之后，对于当前的算法执行后的结果的评估，即为设置agent的mood属性。
					/*这几种mood的属性可以为以下的集中你情形： 
					 * 1, pleased，valence>0, 说明交互的结果比较愉悦
					 * 2, unpleased, valence<0, 说明交互的结果不愉快
					 * 3， satisfied,   在intendedInteraction == enactedInteraction的情形当中
					 * 4， unSatisfied, 在intendedInteraction != enactedInteraction的情形当中
					 */
					/*
					if(this.getMood().equals(Mood.PLEASED)) {
						//这里存在问题
						//intendedInteraction = enactedInteraction1;
						//isEnacting = true;
					}else {//这次的交互结果不高兴，或者在以往的交互当中不能提供支持
						//List<Anticipation> anticipations = anticipate();
						//Interaction selectedInteraction = selectInteraction(anticipations).getIntendedInteraction();
						//intendedInteraction
					}
					*/
				}//如果没有在enact composite interaction当中，那么按照之前的筛选方法进行，如果在enact composite interaction进程当中，直接按序enact。
				
				//进入了enact composite interaction 阶段，逐步的按照composite interaction的顺序交互
				if(isEnactCompositeInteraction && compositeInteractionForEnacting != null) {
					System.out.println("Is going to enact the composite interaction: "+compositeInteractionForEnacting);
					if(isFirstConstructNodeList) {//首次按照二叉树完成节点的存储，之后直接使用即可
						Node mainNode = new Node(compositeInteractionForEnacting);
						mainNode.setUpperNode(null);
						mainNode.setUpperNodeDirection(0);//0为中，1为left，2为右
						nodeList.add(mainNode);
						nodeStack.push(mainNode);
						//把所有的节点都保存在一个列表当中，通过检索这个列表来实现对于composite interaction的neact，主要是根据其上层结点和下层结点来获得
						while(!nodeStack.isEmpty()) {
							Node nodeUpper = (Node)nodeStack.pop();
							if(!nodeUpper.getInteraction().isPrimitive()) {
								
								Node rightNode = new Node(nodeUpper.getInteraction().getPostInteraction());
								rightNode.setUpperNode(nodeUpper);
								rightNode.setUpperNodeDirection(2);
								nodeStack.push(rightNode);
								nodeList.add(rightNode);
								nodeUpper.setRightNode(rightNode);
								
								Node leftNode = new Node(nodeUpper.getInteraction().getPreInteraction());
								leftNode.setUpperNode(nodeUpper);
								leftNode.setUpperNodeDirection(1);
								nodeStack.push(leftNode);
								nodeList.add(leftNode);
								nodeUpper.setLeftNode(leftNode);
								
							}else {
								nodeUpper.setLeftNode(null);
								nodeUpper.setRightNode(null);
							}
						}
						
						Node rootNode = nodeList.get(0);
						Node farRightNode = rootNode;
						while(farRightNode.getRightNode() != null) {
							farRightNode = farRightNode.getRightNode();
						}
						farRightNode.setLast(true);//设定最右边的节点标志
						System.out.println("The size of the nodeList is: "+nodeList.size());
						isFirstConstructNodeList = false;
						//在获得了composite interaction的所有结点之后，开始选择的开始交互的内容。
					}
					
					//在完成composite interaction所有结点构建之后，需要逐个enact其中的interaction
					Node root = nodeList.get(0);
					if(isFirstNode) {//enact composite interaction 第一次交互选择的node（最左边的interaction节点）
						node = farLeft(root);
						intendedInteraction = node.getInteraction();
						updateNodes(node);
						isFirstNode = false;
					}else {
						node = rightNearest(node,nodeList);
						intendedInteraction = node.getInteraction();
						updateNodes(node);//虽然这是composite interaction的最后一个结点了，但是还需要验证这个结点是否符合要求。
					}
				}
				
			
				
				System.out.println("intendedInteraction is: "+intendedInteraction);
				int actionType = intendedInteraction.getExperience().getAction();
				int drawResult = intendedInteraction.getResult();
				int interactResult = enact(actionType,randomPosition);
				Interaction enactedInteraction2 = addOrGetPrimitiveInteraction(intendedInteraction.getExperience(),interactResult,0);
				System.out.println("enactedInteraction is: "+enactedInteraction2);
				stepNumber+=1;
				
				int enactedValence = enactedInteraction2.getValence();
				totalValence=totalValence+enactedInteraction2.getValence();
				
				if(isPrimitiveIntendedInteraction) {
					isFinishString = "yes";
				}
				
				//由于这里的对于intended interaction与enacted interaction的比较，只有在enact composite  interaction
				//当中才会出现，这也是一个需要注意的内容，如果agent需要去enact 一个既有的interaction，这块的内容可以放在一个
				//predictive的模型当中。
				/*
				if(intendedInteraction.equals(this.getEnactedInteraction())) {
					node = rightNearest(node,nodeList);
					intendedInteraction = node.getInteraction();
				}else {
					isEnactCompositeInteraction = false;
					isGetCompositeInteraction = false;
				}*/
				
				//在完成了交互的内容之后，需要完成一些收尾性质的工作，设置一定的状态为下一次的交互做准备。
				Interaction enactedCompositeInteraction = null; //用于返回enact composite interaction的大的结果
				if(isEnactCompositeInteraction) {//如果在enact composite interaction的阶段
					/*
					 * 需要比较这些interaction，如果不同则需要构建一个enacted interaction，并且完成composite interaction的任务
					 * 同时还需要退出当前对于composite interaction的交互内容。
					 * 对于enacted interaction的组建
					 * 用来组建composite interaction的enacted interaction序列。
					 */
					if(intendedInteraction.equals(enactedInteraction2)) {
						System.out.println("The intendedInteraction equals with the enactedInteraction");
						Node root = nodeList.get(0);
						System.out.println("The root visited state is: "+root.isVisited());//来显示composite interaction是否已经逐个enact完成。
						if(root.isVisited()) {//所有的interaction都已经enact了，并且结果正确，所以这个执行已经圆满完成了
							enactedCompositeInteraction = compositeInteractionForEnacting;
							isEnactCompositeInteraction = false;
							isGetCompositeInteraction = false;//开始了新的previous interaction exploitation阶段
							nodeList.clear();
							node = null;
							isFirstConstructNodeList = true;
							isFirstNode = true;
							enactedInteraction2 = enactedCompositeInteraction;//主要用于learnCompositeInteraction()当中使用
							isFinishString = "yes";
							
							interactionStack.clear();
							//圆满完成了整个composite的内容，这对于后面构建composite interaction会受到影响。
							//这块也是需要进行思考的部分。
							//虽然是最后一个结点，并且交互成功，但是目前的状态依旧在enact composite interaction的系列当中
						}else{//如果当前的intended 与enacted相同，但是composite interaction还没有访问结束，则按照顺序继续交互。
							if(node.getUpperNodeDirection() == 1) {
								previousEnactedCompositeInteraction = enactedInteraction2;
								interactionStack.push(previousEnactedCompositeInteraction);
							}else if(node.getUpperNodeDirection() ==2){
								Node previousEnactedNode = node;
								while(previousEnactedNode.getUpperNodeDirection() == 2) {
									previousEnactedNode = previousEnactedNode.getUpperNode();
									previousEnactedCompositeInteraction = previousEnactedNode.getInteraction();
									interactionStack.pop();
									if(previousEnactedNode.getUpperNodeDirection() == 1) {
										interactionStack.push(previousEnactedCompositeInteraction);
									}
								}
								//addOrGetAndReinforceCompositeInteraction(previousEnactedCompositeInteraction, enactedInteraction2);
								//interactionStack.push(previousEnactedCompositeInteraction);
							}
						}
					}else{//如果intended 与enacted不同，完成新的composite interaction的构建，然后退出当前的重复交互
						//还需要清空nodeList,node
						System.out.println("************** test start ***************");
						System.out.println("************** the intended interaction is different with enacted interaction ***************");
						System.out.println("intended interaction is: "+intendedInteraction);
						System.out.println("enated interaction is: "+enactedInteraction2);
						//组件composite interaction的enacted interaction
						Interaction temporaryInteraction = enactedInteraction2;
						while(!interactionStack.isEmpty()) {
							Interaction popInteraction = (Interaction)interactionStack.pop();
							temporaryInteraction = addOrGetAndReinforceCompositeInteraction(popInteraction, temporaryInteraction);
						}
						enactedCompositeInteraction = temporaryInteraction;
						enactedInteraction2 = enactedCompositeInteraction;
						
						
						/*if(node.getUpperNodeDirection() ==1) {
							while(!interactionStack.isEmpty()) {
								Interaction popInteraction = (Interaction)interactionStack.pop();
								temporaryInteraction = addOrGetAndReinforceCompositeInteraction(popInteraction, temporaryInteraction);
							}
							enactedCompositeInteraction = temporaryInteraction;
							enactedInteraction2 = enactedCompositeInteraction;
						}else if(node.getUpperNodeDirection() == 2){
							while(!interactionStack.isEmpty()) {
								Interaction popInteraction = (Interaction)interactionStack.pop();
								temporaryInteraction = addOrGetAndReinforceCompositeInteraction(popInteraction, temporaryInteraction);
							}
							enactedCompositeInteraction = temporaryInteraction;
							enactedInteraction2 = enactedCompositeInteraction;
						}*/
						
						isEnactCompositeInteraction = false;
						isGetCompositeInteraction = false;
						nodeList.clear();
						node = null;
						isFirstConstructNodeList = true;
						isFirstNode = true;
						//虽然当前的交互结果不同，但是当前的状态依旧在enact composite interaction的过程当中。
						isFinishString = "yes";
						System.out.println("The real enacted composite interaction is: "+enactedInteraction2);
					}
				}
				
				if(!isEnactCompositeInteraction){//没有在enact composite interaction状态当中
					learnCompositeInteraction(enactedInteraction2);
					this.setEnactedInteraction(enactedInteraction2);
					enactedInteractionList.add(enactedInteraction2);
				}
		
				System.out.println("The enacted interaction is: "+enactedInteraction2);
				if (enactedInteraction2.getValence() >= 0) {
					this.setMood(Mood.PLEASED);
				}else {
					this.setMood(Mood.PAINED);
				}
				
				resultEnactedcompositeInteractionString = enactedInteraction2.toString();
				
				isReady = "ok";
				JSONObject jsonObject = new JSONObject();  //创建Json对象
				try {
					jsonObject.put("actionType",actionType);
					jsonObject.put("drawResult",drawResult);
					//jsonObject.put("resultString",resultString);
					jsonObject.put("totalValence",totalValence);
					jsonObject.put("loopNum",loopNum);
					jsonObject.put("isReady",isReady);
					//jsonObject.put("compositeString",compositeString);
					jsonObject.put("drawInteractionString",drawInteractionString);
					jsonObject.put("drawAnticipationString", drawAnticipationString);
					//jsonObject.put("isclickEnv",isclickEnv);
					jsonObject.put("enactedValence", enactedValence);
					jsonObject.put("bumpTimes", bumpTimes);
					//jsonObject.put("isEnactCompositeInteraction", isEnactCompositeInteraction);
					
					
					jsonObject.put("enactComIntString", compositeInteractionForEnactingString);
					jsonObject.put("resultEnactComIntString", resultEnactedcompositeInteractionString);
					
					jsonObject.put("isFinishString", isFinishString);
					jsonObject.put("isFirstEnaComInteraction", isFirstEnactedcompositeInteraction);
					jsonObject.put("isInEnaComInteraction", isInEnactedCompositeInteraction);
					
					jsonObject.put("stepNumber", stepNumber);
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}

				//在enact composite interaction当中，执行了第一个interaction之后，使用之后这个状态即改变。
				if(isFirstEnactedcompositeInteraction.equals("yes"))isFirstEnactedcompositeInteraction = "no";
				
				if(!isEnactCompositeInteraction){
					loopNum++;
				}
				
				

				response.getWriter().write(jsonObject.toString());
			}//the following interactions
			//loopNum++;
		}//interaction
	}
	
	public void updateNodes(Node currentnode) {
		// TODO Auto-generated method stub
		currentnode.setVisited(true);
		Node nPoint = currentnode;
		while(nPoint.getUpperNodeDirection() == 2) {
			nPoint = nPoint.getUpperNode();
			nPoint.setVisited(true);
		}
	}

	public Node rightNearest(Node currentNode, List<Node> nodeList) {
		//while(!node.isVisited)
		Node pointNode = currentNode;
		Node rightNearestNode = null;
		while(pointNode.getUpperNodeDirection() == 2) {
			pointNode = pointNode.getUpperNode();
		}
		if(pointNode.getUpperNodeDirection() != 0) {
			pointNode = pointNode.getUpperNode().getRightNode();
			rightNearestNode = farLeft(pointNode);
			return rightNearestNode;
		}else {
			rightNearestNode = null;
			return rightNearestNode;
		}
	}

	public Node farLeft(Node curretRoot) {
		Node farLeftNode = curretRoot;
		while(!farLeftNode.getInteraction().isPrimitive()) {
			farLeftNode = farLeftNode.getLeftNode();
		}
		return farLeftNode;
	}
	
	public Interaction farLeftInteraction(Interaction anticipationInteraction) {
		Interaction farLeftInteraction = anticipationInteraction;
		while(!farLeftInteraction.isPrimitive()) {
			farLeftInteraction = farLeftInteraction.getPreInteraction();
		}
		return farLeftInteraction;
	}
	
	public int enact(int actionType,Position randomPosition) {
		//boolean isBump = false;
		int interactResult = 0; 
		int poX = randomPosition.getPointX();
		int poY = randomPosition.getPointY();
		switch(actionType) {
		case 0://move forward
			switch(direction) {
			case "up":
				if(env[poX-1][poY] == 0) {
					interactResult = 1;
					randomPosition.setPointX(poX-1);
				}else {
					bumpTimes+=1;
				}break;
			case "right":
				if(env[poX][poY+1] == 0) {
					interactResult = 1;
					randomPosition.setPointY(poY+1);
				}else {
					bumpTimes+=1;
				}break;
			case "down":
				if(env[poX+1][poY] == 0) {
					interactResult = 1;
					randomPosition.setPointX(poX+1);
				}else {
					bumpTimes+=1;
				}break;
			case "left":
				if(env[poX][poY-1] == 0) {
					interactResult = 1;
					randomPosition.setPointY(poY-1);
				}else {
					bumpTimes+=1;
				}break;
			}break;
		case 1://turn left
			switch(direction){
			case "up":
				direction = "left";
				break;
			case "right":
				direction = "up";
				break;
			case "down":
				direction = "right";
				break;
			case "left":
				direction = "down";
				break;
			}
			interactResult = 1;
			break;
		case 2://turn right
			switch(direction){
			case "up":
				direction = "right";
				break;
			case "right":
				direction = "down";
				break;
			case "down":
				direction = "left";
				break;
			case "left":
				direction = "up";
				break;
			}
			interactResult = 1;
			break;
		case 3://feel front
			switch(direction) {
			case "up":
				if(env[poX-1][poY] == 0) {
					interactResult = 1;
				}break;
			case "right":
				if(env[poX][poY+1] == 0) {
					interactResult = 1;
				}break;
			case "down":
				if(env[poX+1][poY] == 0) {
					interactResult = 1;
				}break;
			case "left":
				if(env[poX][poY-1] == 0) {
					interactResult = 1;
				}break;
			}
			break;
		case 4://feel left
			switch(direction) {
			case "up":
				if(env[poX][poY-1] == 0) {
					interactResult = 1;
				}break;
			case "right":
				if(env[poX-1][poY] == 0) {
					interactResult = 1;
				}break;
			case "down":
				if(env[poX][poY+1] == 0) {
					interactResult = 1;
				}break;
			case "left":
				if(env[poX+1][poY] == 0) {
					interactResult = 1;
				}break;
			}
			break;
		case 5://feel right
			switch(direction) {
			case "up":
				if(env[poX][poY+1] == 0) {
					interactResult = 1;
				}break;
			case "right":
				if(env[poX+1][poY] == 0) {
					interactResult = 1;
				}break;
			case "down":
				if(env[poX][poY-1] == 0) {
					interactResult = 1;
				}break;
			case "left":
				if(env[poX-1][poY] == 0) {
					interactResult = 1;
				}break;
			}
			break;
		}
		return interactResult;
	}
	
	public Interaction selectOtherIntendedInteraction(List<Integer> intendedInteractionActionList) {
		Interaction interaction = null;
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(0);
		intList.add(1);
		intList.add(2);
		intList.add(3);
		intList.add(4);
		intList.add(5);
		
		int randomIndex = 0;
		if(intendedInteractionActionList.size() ==6) {
			randomIndex = random.nextInt(6);
		}else {
			intList.removeAll(intendedInteractionActionList);
			randomIndex = random.nextInt(intList.size());
		}
		
		for(Interaction inter: INTERACTIONS.values()) {
			if(inter.isPrimitive()) {
				if(inter.getExperience().getAction() == intList.get(randomIndex)){
					interaction = inter;
				}
			}
		}
		
		/*
		for(Interaction inter: INTERACTIONS.values()) {
			if(inter.isPrimitive()) {
				if(intendedInteractionActionList.size() == 6) {
					interaction = inter;
				}else if(!intendedInteractionActionList.contains(inter.getExperience().getAction())){
					interaction = inter;
				}
			}
		}
		*/
		
		return interaction;
	}
	
	public boolean isEqualList(List<Interaction> list1,List<Interaction> list2) {
		boolean isEqual = true;
		if(list1.size() != list2.size()) {
			isEqual = false;
		}else {
			for(int y=0; y<list1.size();y++) {
				if(!list1.get(y).equals(list2.get(y))) {
					isEqual = false;
				}
			}
		}
		return isEqual;
	}
		
	public boolean isInContainlList(List<Problem> problemList,Interaction enactedInteraction1, List<Interaction> list2) {
		boolean isInContainlList = false;
		if(problemList.size() > 0) {
			for(int w=0;w<problemList.size();w++) {
				Problem pb = problemList.get(w);
				if(pb.getEnactedInteraction().equals(enactedInteraction1)) {
					List<Interaction> list1 = pb.getProblemInteractionList();
					if(isEqualList(list1,list2)) {
						isInContainlList = true;
					}
				}
			}
		}
		return isInContainlList;
	}
	
	public Interaction selectOtherInteraction(Interaction enactedInteraction,int index) {
		System.out.println();
		System.out.println("Select other interaction with different action "+index);
		Interaction interaction = null;
		
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(0);
		intList.add(1);
		intList.add(2);
		intList.add(3);
		intList.add(4);
		intList.add(5);
		System.out.println("avoidActoinList is:"+avoidActoinList);
		if(avoidActoinList.size() ==6) {
			intList.remove(enactedInteraction.getExperience().getAction());
		}else {
			intList.removeAll(avoidActoinList);
		}
		int randomIndex = random.nextInt(intList.size());
		for(Interaction inter: INTERACTIONS.values()) {
			if(inter.isPrimitive()) {
				if(inter.getExperience().getAction() == intList.get(randomIndex)){
					interaction = inter;
				}
			}
		}
		avoidActoinList.clear();
		System.out.println("the selected interaction is:"+interaction);
		return interaction;
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

	public List<Interaction> searchProcess(List<Interaction> enactedInteractionList2, Interaction enactedInteraction1) {
		System.out.println();
		System.out.println("searchProcess()");
		List<Interaction> bestSelection = null;
		int size = enactedInteractionList2.size();
		System.out.println("enacted interaction list size is:"+size);
		List<Integer> indexList = new ArrayList<Integer>();
		for(int i=0;i<size;i++) {
			Interaction inter = enactedInteractionList2.get(i);
			if(inter.equals(enactedInteraction1)) {
				indexList.add(i);
			}
		}
		
		List<List<Interaction>> multiInteractionList = new ArrayList<List<Interaction>>();
		int indexListSize = indexList.size();
		int index_pre;
		int index_pos;
		if(indexListSize>0) {
			for(int j=0;j<indexListSize;j++) {
				List<Interaction> interactionSmallList = new ArrayList<Interaction>();
				index_pre = indexList.get(j);
				if(j+1 < indexListSize) {
					index_pos = indexList.get(j+1);
				}else {
					index_pos = size;
				}
				for(int k=index_pre;k<index_pos;k++) {
					interactionSmallList.add(enactedInteractionList2.get(k));
				}
				if(!isContainsList(multiInteractionList,interactionSmallList)) {
					//CandidateInteraction candidate = new CandidateInteraction();
					//candidate.setCandiInteractionList(interactionSmallList);
					//enactedInteraction1.getCandidateList().add(candidate);
					multiInteractionList.add(interactionSmallList);
				}
			}
		}
		//得到了想要的list<list>
		
		
		
		//估计下一次的interaction 的action，要检查previous interactions
		//得到不同序列的interaction之后要选择去执行哪个candidate list,进入exploitation，如果没有则进入exploration过程。
		String multiInteractionListString = "";
		if(multiInteractionList.size()>0) {
			int shortDistance=100;
			double meanValence = -20;
			for(int h=0;h<multiInteractionList.size();h++) {
				int tempTotalValence = 0;//用于计算平均value
				List<Interaction> tempInteractionList = multiInteractionList.get(h);
				multiInteractionListString = multiInteractionListString+tempInteractionList+"\r\n";
				int tempSize = tempInteractionList.size();
				if(tempSize>0) {
					boolean isHaveMaxValence = false;
					for(int l=0;l<tempSize;l++) {
						Interaction inte = tempInteractionList.get(l);
						tempTotalValence+=inte.getValence();
						if(inte.getValence() >=  maxValence) {
							isHaveMaxValence = true;//只有达到maxValua的tempInteractionList才会在之后的interaction当中考虑
						}
					}
					System.out.println("tempInteractionList is:"+tempInteractionList);
					System.out.println("maxValence is:"+maxValence+" isHaveMaxValence is:"+isHaveMaxValence);
					if(isHaveMaxValence) {
						double tempMeanValence = (double)tempTotalValence/(double)tempSize;
						BigDecimal bMeanValence = new BigDecimal(Double.toString(meanValence)); 
				        BigDecimal bTempMeanValence = new BigDecimal(Double.toString(tempMeanValence)); 
				        System.out.println("tempTotalValence is:"+tempTotalValence+" tempSize is:"+tempSize+" tempMeanValence is:"+tempMeanValence);
						if(bMeanValence.compareTo(bTempMeanValence)<0) {
							if(tempSize<shortDistance) {
								shortDistance = tempSize;
								meanValence = tempMeanValence;
								bestSelection = tempInteractionList;
							}
						}
					}else{//if isHaveMaxValence = false, just print the result.
						double tempMeanValence = (double)tempTotalValence/(double)tempSize;
						System.out.println("tempTotalValence is:"+tempTotalValence+" tempSize is:"+tempSize+" tempMeanValence is:"+tempMeanValence);
					}
				}
			}// end for 备选列表
			System.out.println("shortDistance is:"+shortDistance+" meanValence is:"+meanValence);
			System.out.println();
		}// end if multiInteractionList.size() > 0 
		System.out.println("multiInteractionList is:");
		System.out.println(multiInteractionListString);
		System.out.println();
		System.out.println("the selected intendedInteractionList is:"+bestSelection);
		
		
		//avoidance action selection 避免“坏动作”的问题
		if(bestSelection == null) {//在这种情况下需要排除之前没有达到valence的actions，在下一次的interaction当中避免这种action
			avoidActoinList.clear();
			avoidActoinList.add(enactedInteraction1.getExperience().getAction());
			if(multiInteractionList.size()>0) {
				for(int g=0;g<multiInteractionList.size();g++) {
					List<Interaction> tempInteractionListForAvoiding = multiInteractionList.get(g);
					if(tempInteractionListForAvoiding.size()>1) {
						if(!avoidActoinList.contains(tempInteractionListForAvoiding.get(1).getExperience().getAction())) {
							avoidActoinList.add(tempInteractionListForAvoiding.get(1).getExperience().getAction());
						}
					}
				}//end for
			}
		}
		System.out.println("avoidActoinList is:"+avoidActoinList);
		return bestSelection;
	}
	
	public void reasoningProcess() {
		System.out.println("the reasoning process");
	}
	
	public boolean isContainsList(List<List<Interaction>> multiInteractionList,List<Interaction> interactionSmallList) {
		boolean isContains = false;
		for(int k=0;k<multiInteractionList.size();k++) {
			if(isEqualList(multiInteractionList.get(k),interactionSmallList)) {
				isContains = true;
			}
		}
		return isContains;
	}

	public void learnCompositeInteraction(Interaction enactedInteraction2) {
		// TODO Auto-generated method stub
		System.out.println("learnCompositeInteraction()");
		Interaction previousInteraction = this.getEnactedInteraction(); 
		Interaction lastInteraction = enactedInteraction2;
		Interaction previousSuperInteraction = this.getSuperInteraction();
		Interaction lastSuperInteraction = null;
		drawInteractionString = "";
		
		if (previousInteraction != null) {
			lastSuperInteraction = addOrGetAndReinforceCompositeInteraction(previousInteraction, lastInteraction);
		}
		if (previousSuperInteraction != null){
            this.addOrGetAndReinforceCompositeInteraction(previousSuperInteraction.getPreInteraction(), lastSuperInteraction);
            this.addOrGetAndReinforceCompositeInteraction(previousSuperInteraction, lastInteraction);
        }
        this.setSuperInteraction(lastSuperInteraction);
	}

	public Interaction addOrGetAndReinforceCompositeInteraction(Interaction previousInteraction,Interaction lastInteraction) {
		// TODO Auto-generated method stub
		String label = "<" + previousInteraction.getLabel() + lastInteraction.getLabel() + ">";
		Interaction interaction = INTERACTIONS.get(label);
		if (interaction == null){
			interaction = addOrGetInteraction(label);
			interaction.setPreInteraction(previousInteraction);
			interaction.setPostInteraction(lastInteraction);
			interaction.setValence(previousInteraction.getValence() + lastInteraction.getValence());
			addOrGetAbstractExperiment(interaction);
			interaction.incrementWeight();
			int preLevel = previousInteraction.getLevel();
			int posLevel = lastInteraction.getLevel();
			if(preLevel >= posLevel) {
				interaction.setLevel(preLevel+1);
			}else {
				interaction.setLevel(posLevel+1);
			}
			//System.out.println("LEARN:" + interaction.toString()+" level is:"+interaction.getLevel());
			String drawString = "LEARN:" + interaction.toString();
			drawInteractionString = drawInteractionString+drawString+"|";
        }else {
        	interaction.incrementWeight();
			//System.out.println("REINFORCE: " + interaction.toString());
			String drawString = "REINFORCE:" + interaction.toString();
			drawInteractionString = drawInteractionString+drawString+"|";
		}
		System.out.println(drawInteractionString);
		return interaction;
	}

	public Experience addOrGetAbstractExperiment(Interaction interaction) {
		// TODO Auto-generated method stub
		String label = interaction.getLabel().replace('e', 'E').replace('>', '|');
        if (!EXPERIENCES.containsKey(label)){
        	Experience abstractExperience =  new Experience(label,6);
        	abstractExperience.setIntendedInteraction(interaction);
			interaction.setExperience(abstractExperience);
            EXPERIENCES.put(label, abstractExperience);
        }
        return EXPERIENCES.get(label);
	}

	public Interaction addOrGetInteraction(String label) {
		// TODO Auto-generated method stub
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, createInteraction(label));			
		return INTERACTIONS.get(label);
	}

	public Anticipation selectInteraction(List<Anticipation> anticipations) {
		//整体的defaultAnticipation按照其各自的proclivity进行排序
		Collections.sort(anticipations);
		for(Anticipation anti:anticipations) {
			//每个defaultAnticipation当中的anticipationList也进行一次排序
			if(!anti.isPrimitive())Collections.sort(anti.getAnticipationList());
		}
		
		drawAnticipationString = "";
		for(Anticipation anti : anticipations) {
			if(!anti.isPrimitive) {
				//default anti部分内容
				String notPrimitiveString = anti.getExperience().getIntendedInteraction().getLabel()+"_"+anti.getProclivity()+",";
				//detail anti 部分内容
				for(Anticipation antiInner : anti.getAnticipationList()) {
					if(antiInner.isInteractionPrimitive()) {
						Interaction antiPrimitiveInteraction = addOrGetPrimitiveInteraction(antiInner.getExperience(),antiInner.getInteractionResult(),0);
						notPrimitiveString+=antiPrimitiveInteraction.getLabel()+"_"+antiInner.getProclivity()+"_"+antiInner.getCompositeWeight()+".";
					}else {
						notPrimitiveString+=antiInner.getExperience().getIntendedInteraction().getLabel()+"_"+antiInner.getProclivity()+"_"+antiInner.getCompositeWeight()+".";
					}
					
				}
				notPrimitiveString+="|";
				drawAnticipationString+=notPrimitiveString;
				System.out.println(notPrimitiveString);
			}else {
				drawAnticipationString+=anti.getExperience().getIntendedInteraction().getLabel()+"_"+anti.getProclivity()+"|";
				System.out.println(anti.getExperience().getIntendedInteraction().getLabel()+"_"+anti.getProclivity()+"|");
			}
		}
		System.out.println();
		System.out.println("the sorted anticipations are:");
		System.out.println(drawAnticipationString);
		
		Anticipation selectedAnticipation = (Anticipation)anticipations.get(0);
		
		//给出的selectedAnticipationyou两种情况，一种是原始的default anticipation，即为原始的动作
		//另一种是激活后的队列，这里也有两种情况，一种是enacted primitive interaction，一种是enact multiInteraction。 
		if(!selectedAnticipation.isPrimitive()) {
			System.out.println("****************************************");
			System.out.println("the proposed anticipation's anticipationList has something");
		}
		
		return selectedAnticipation;
		
		//return getIntendedInteraction(selectedAnticipation.getInteraction());
	}
	
	public Interaction getIntendedInteraction(Interaction interaction) {
		if(!interaction.isPrimitive()) {
			return getIntendedInteraction(interaction.getPreInteraction());
		}else {
			return interaction;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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
		//int randomIndex = random.nextInt(availablePositionList.size());
		Position initialPosition = (Position)availablePositionList.get(0);//取一个位置。
		//String tempDirection = directions[random.nextInt(4)];//有四个不同的方向，随机选择一个方向
		initialPosition.setDirection("left");
		return initialPosition;
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
	
	public List<Integer> getAnticipationActionList(Anticipation antiForActions){
		Interaction postInteraction = null;
		if(antiForActions.isInteractionPrimitive()) {
			postInteraction = addOrGetPrimitiveInteraction(antiForActions.getExperience(),
					antiForActions.getInteractionResult(),0);
		}else {
			postInteraction = antiForActions.getExperience().getIntendedInteraction();
		}
		//由于只是用来获取每个anticipation当中的anticipationList当中的actionList,所以这一步可以省略，但是加上也没有什么影响
		
		//System.out.println("postInteraction is: "+postInteraction);
		
		List<Integer> anticipationActionList = new ArrayList<Integer>();//用于保存动作序列的list
		
		//采用先序遍历的方式，按照compositeInteraction当中的interaction的顺序获取动作序列
		Stack<Node> actionNodeStack = new Stack<Node>();
		Node actionMainNode = new Node(postInteraction);
		actionNodeStack.push(actionMainNode);
		while(!actionNodeStack.isEmpty()) {
			Node actionNodeUpper = (Node)actionNodeStack.pop();
			if(!actionNodeUpper.getInteraction().isPrimitive()) {
				Node rightActionNode = new Node(actionNodeUpper.getInteraction().getPostInteraction());
				rightActionNode.setUpperNode(actionNodeUpper);
				rightActionNode.setUpperNodeDirection(2);
				actionNodeStack.push(rightActionNode);
				actionNodeUpper.setRightNode(rightActionNode);
				
				Node leftActionNode = new Node(actionNodeUpper.getInteraction().getPreInteraction());
				leftActionNode.setUpperNode(actionNodeUpper);
				leftActionNode.setUpperNodeDirection(1);
				actionNodeStack.push(leftActionNode);
				actionNodeUpper.setLeftNode(leftActionNode);
			}else {
				actionNodeUpper.setLeftNode(null);
				actionNodeUpper.setRightNode(null);
				anticipationActionList.add(actionNodeUpper.getInteraction().getExperience().getAction());
			}
		}
		//System.out.println(actionNodeList.size());
		//System.out.println("anticipationActionList is:"+anticipationActionList);
		return anticipationActionList;
	}
	
	public List<Anticipation> anticipate(){
		//List<Anticipation> anticipations = getDefaultAnticipations(); //准备默认的anticipation 类似的动作组
		
		List<Anticipation> defaultAnticipations = getDefaultAnticipations(); //默认的准备组，动作组
		List<Interaction> activatedInteractions =  getActivatedInteractions();// 获得所有的 activated composite interaction with <pre,post>
		List<Anticipation> anticipationTemporary = new ArrayList<Anticipation>();//用来临时保存 除default anticipation以外的post interaction
		
		//第一个条件主要用于第一次交互的情况，第二个用于后续激活出现activatedInteraction的情况
		if(this.getEnactedInteraction() != null && activatedInteractions.size()>0) {//加上activatedInteraction.size>0,只有在获得激活交互的情况下，以下的处理过程才能进行
			//创建初始的anticipation list，准备好相应的proclivity的创建和更新
			
			for(Interaction activatedInteraction : activatedInteractions) {
				//System.out.println();
				//System.out.println("activated Interaction is: "+activatedInteraction);
				int proclivitytem = activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence();
				//System.out.println("proclivitytem is: "+proclivitytem);
				
				//对于activatedInteraction当中的postInteraction是primitive Interaction的情况,需要对其result的记录，以便于在enact enacted primitive interaction当中可以找回之前的结果。
				Anticipation anticipationForAction = new Anticipation(activatedInteraction.getPostInteraction().getExperience(),proclivitytem);
				anticipationForAction.setActivatedInteraction(activatedInteraction);
				
				if(activatedInteraction.getPostInteraction().isPrimitive()) {
					anticipationForAction.setInteractionPrimitive(true);//这个anticipation当中的interaction是一个单独的interaction，给出标记
					anticipationForAction.setInteractionResult(activatedInteraction.getPostInteraction().getResult());
				}
				anticipationForAction.setCompositeWeight(activatedInteraction.getWeight());
				
				
				//根据激活的activated interaction的post interaction形成不同的anticipations,并将其保存在anticipationTemporary当中
				int index = anticipationTemporary.indexOf(anticipationForAction);
				if (index < 0) {
					if(anticipationForAction.isInteractionPrimitive()) {
						System.out.println("created new temporary anticipation: "
								+ ""+addOrGetPrimitiveInteraction(anticipationForAction.getExperience(),anticipationForAction.getInteractionResult(),0));
					}else {
						System.out.println("created new temporary anticipation: "+anticipationForAction.getExperience().getIntendedInteraction());
					}
					anticipationTemporary.add(anticipationForAction);
				}else {
					if(anticipationForAction.isInteractionPrimitive()) {
						System.out.println("adding  the temporary proclivitytem: "
								+ ""+addOrGetPrimitiveInteraction(anticipationForAction.getExperience(),anticipationForAction.getInteractionResult(),0));
					}else {
						System.out.println("adding  the temporary proclivitytem: "+anticipationForAction.getExperience().getIntendedInteraction());
					}
					//System.out.println("adding  the temporary proclivitytem: "+anticipationForAction.getExperience().getIntendedInteraction());
					
					
					//这块的设计和实现细节需要好好想想
					//其实 activated composite interaction的post interaction的valence 一直是不发生变化的。
					//先给出所有的anticipation，其中出现可以检索到的相同的情况，
					//对于相同的post interaction的情况，对应的权重也会进行相加，由于在后面的计算当中，
					//对于出现不同结果的情况会出现相互抵消的情况
					anticipationTemporary.get(index).addProclivity(proclivitytem);//直接加上该proclivity值即可
					anticipationTemporary.get(index).addCompositeWeight(anticipationForAction.getCompositeWeight());
					
					//发现之前存在这个anticipation，那么查看是否需要更新这个anticipation的weight
					/*
					if(anticipationTemporary.get(index).getCompositeWeight()<anticipationForAction.getCompositeWeight()) {
						anticipationTemporary.get(index).setCompositeWeight(anticipationForAction.getCompositeWeight());
					}*/
				}
			}
			//System.out.println();
			
			
			
			//完成对于上一阶段的anticipation当中动作序列的构建
			for(Anticipation antiForActions : anticipationTemporary) {
				List<Integer> anticipationActionList = getAnticipationActionList(antiForActions);
				antiForActions.setActionList(anticipationActionList);
			}
			
			
			
			//根据anticipation的action List当中第一个动作，开始对activated interaction进行分类
			//来表征之前的交互经验在action selection当中的作用，主要在这一块当中体现
			for(Anticipation defaultAnticipation:defaultAnticipations) {
				
				//主要用来设置defaultAnticipation的两个相关属性，一个是关联该动作的所有anticipationList，同时更新相应的proclivity
				List<Anticipation> defaultAnti = new ArrayList<Anticipation>();
				int defaultProclivity = 0;
				
				//依次从anticipationTemporary遍历，将其归类到指定的defaultAnticipation当中
				for(Anticipation antiForCategorizing:anticipationTemporary) {
					if(!antiForCategorizing.isCategorized()) {//为了减少每次遍历的次数，专注于没有分类的detail anticipation
						if(defaultAnticipation.getExperience().getAction() == antiForCategorizing.getActionList().get(0)) {
							defaultAnti.add(antiForCategorizing);
							defaultProclivity = defaultProclivity+antiForCategorizing.getProclivity();
							
							/*if(antiForCategorizing.isInteractionPrimitive()) {
								Interaction primitiveInteraction = addOrGetPrimitiveInteraction(antiForCategorizing.getExperience(),
										antiForCategorizing.getInteractionResult(),0);
								defaultProclivity = defaultProclivity+primitiveInteraction.getValence();
							}else {
								Interaction primitiveInteraction = farLeftInteraction(antiForCategorizing.getExperience().getIntendedInteraction());
								defaultProclivity = defaultProclivity+primitiveInteraction.getValence();
							}*/
							antiForCategorizing.setCategorized(true);//该anticipation已经被访问过了，所以在下次访问时会直接跳过。
						}
					}//!isCategorized
				}
				
				//执行一次遍历anticipationTemporary之后，更新相应的anticipationList和proclivity
				defaultAnticipation.setAnticipationList(defaultAnti);
				defaultAnticipation.setProclivity(defaultProclivity);
				if(!defaultAnti.isEmpty())defaultAnticipation.setPrimitive(false);//如果该anticipation当中有anticipationList，那么需要enact composite interaction
			}//dfaultAnticipation
		}
		return defaultAnticipations;
		
		
		/*
		System.out.println("-------------------------- 分割线 ---------------------------");
		if (this.getEnactedInteraction() != null){
			for (Interaction activatedInteraction : activatedInteractions){
				//System.out.println("the post interaction is:"+activatedInteraction.getPostInteraction());
				int proclivitytem = activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence();
				//这里存在的问题是，对于每个具有相同previous interaction的composite interaction
				//每次都要进行一次统计，把这些信息反馈到默认的选项当中，然后选择一个合适的选择，这里需要好好想想
				Anticipation anticipation = new Anticipation(activatedInteraction.getPostInteraction().getExperience(),proclivitytem);
				int index = anticipations.indexOf(anticipation);
				if (index < 0) {
					System.out.println("created new anticipation");
					anticipations.add(anticipation);
				}else {
					System.out.println("add the proclivitytem..................");
					anticipations.get(index).addProclivity(proclivitytem);
				}
			}//for
		}
		return anticipations;*/
	}
	
	
	public List<Anticipation> getDefaultAnticipations(){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		for (Experience experience : this.EXPERIENCES.values()){//6 experiences
			if(!experience.isAbstract()) {
				Anticipation anticipation = new Anticipation(experience, 0);
				anticipations.add(anticipation);
			}
		}
		return anticipations;
	}
	
	public List<Interaction> getActivatedInteractions() {
		List<Interaction> contextInteractions = new ArrayList<Interaction>();
		if (this.getEnactedInteraction()!=null) {
			contextInteractions.add(this.getEnactedInteraction());
			
			if (!this.getEnactedInteraction().isPrimitive())
				contextInteractions.add(this.getEnactedInteraction().getPostInteraction());
			if (this.getSuperInteraction() != null)
				contextInteractions.add(this.getSuperInteraction());
			
		}
		
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		for (Interaction activatedInteraction : this.INTERACTIONS.values()){
			if (!activatedInteraction.isPrimitive()) {
				if (contextInteractions.contains(activatedInteraction.getPreInteraction())) {
					activatedInteractions.add(activatedInteraction);
				}
			}
		}
		
		//show 显示所有激活的composite interaction
		String activatedString = "\n";
		for (Interaction activatedInteraction : activatedInteractions) {
			activatedString=activatedString+activatedInteraction+"\n";
		}
		System.out.println("all activated interactions are : "+activatedString);
		
		return activatedInteractions;
	}
	
	
	
}