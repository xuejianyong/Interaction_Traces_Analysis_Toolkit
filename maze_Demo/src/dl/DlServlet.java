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
	public enum Mood {SATISFIED, FRUSTRATED, BORED, PAINED, PLEASED};//�Խ�����⣬�����⣬���ģ������ˣ�����
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
	
	
	//public boolean isInSelectionProcess = true; //����������ƻ�����
    public boolean isGetCompositeInteraction = false; //�Ƿ���enact composite interaction��״̬���������enact��״̬������Ҫֱ��ȥ����composite interaction��˳��ȥִ��
    public boolean isEnactCompositeInteraction = false;  //���״̬��Ϊ��������selection����֮�󣬵õ���һ��composite interaction����ô��������Ҫ��ʼenact���composite interaction
    public Interaction compositeInteractionForEnacting = null; //���ڳе�composite interaction����ε�ȥenact���е�primitive interaction
    
    public Stack<Node> nodeStack = new Stack<Node>();
    public boolean isFirstNode = true;
    public Interaction previousEnactedCompositeInteraction = null;
    
    public boolean isFirstConstructNodeList = true; //���ڵõ���composite interaction����һ���Ĳ�νṹ�������ڸտ�ʼ��ʱ����Ҫ�������composite interaction�Ľṹ������һ�νڵ㹹���Ĳ���
    public List<Node> nodeList = new ArrayList<Node>();
    public Node node = null;
    //public Node root = null;
    public Stack<Interaction> interactionStack = new Stack();//���ڹ���enactedCompositeInteraction
    
    public int bumpTimes = 0;
    public String isFinishString = "no";
    public boolean isPrimitiveIntendedInteraction = false;
    public String isFirstEnactedcompositeInteraction = "no"; //�Ƿ�����Ҫenact �� composite interaction�ĵ�һ���ڵ�
    public String isInEnactedCompositeInteraction = "no"; // ��ǰ�Ƿ���enact composite interaction���̵���
    public String compositeInteractionForEnactingString = ""; //��ǰenact composite interaction�Ĵ���ʲô
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
		
		
		isInitialState = request.getParameter("isInitialState");//�������ֳ�ʼ�������뽻������
		
		//�������һ�����⣬��ʼ״̬��λ�úͷ�����ں����������̵�Ӱ�졣
		if(isInitialState.equals("yes")) {
			//�ڳ�ʼ�������棬java������Ҫ�ṩһ��agent�ڻ������еĳ�ʼλ�ú�һ����ʼ����
			System.out.println("isInitialState");
			
			//���еı������г�ʼ��, Ϊreset������׼��
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
			interactionStack.clear();//�����齨 enactCompositeInteraction
			
			
			
			randomPosition = getInitialPosition();//������Ҫ�ú�����
			direction = randomPosition.getDirection();//������Ҫ�ú�����
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
			
			
			
			/***************************************** �����ɴ˿�ʼ   ***************************************/
			//��ʼ������������ɨ�軷�����£�Ȼ��ʼ��������
			String envString = request.getParameter("env");
			String[] envSmall = envString.split(",");
			for(int i=0;i<100;i++) {
				int row = i/10;
				int colume = i%10;
				env[row][colume] = Integer.parseInt(envSmall[i]);
			}
			
			//��java��̨��ʾ�������µ����
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
			
			
			
			if(loopNum == 1) {//���е�һ�ν���
				System.out.println("-------------------------- The beginning of the interaction -----------------------------------------------");
				System.out.println("loop number is:"+loopNum);
				System.out.println("The first interaction and the all values initialization process.......");
				
				//�õ��������action��valenceֵ
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
				
				//Ĭ�ϵĶ����飬����������intendedInteraction��enactedInteraction�ıȽϵ���֪��������interaction������
				//Interaction i0a = addOrGetPrimitiveInteraction(e0, result, valence);
				
				//��ʼ��ÿ��experience��intended interaction���������ٶ���INTERACTIONS�ı���, �����������
				e0.setIntendedInteraction(i01);
				e1.setIntendedInteraction(i11);
				e2.setIntendedInteraction(i21);
				e3.setIntendedInteraction(i30);
				e4.setIntendedInteraction(i40);
				e5.setIntendedInteraction(i50);
				
				
				//��һ�ν�����agentĿǰ����λ��״̬
				//��primitive interactions������ѡ�����ߴ�experience or action������ѡ�������Ҫ�ú�����
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
				//�������ֵ����Ľ������ݣ�����Ҫ����Ŀǰ��cognitive architecture���з��������ⲿ�ֵ�ʵ�����ݷ����㷨���С�
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
					jsonObject.put("drawInteractionString",drawInteractionString);//��ǰѧϰ����composite interaction���ַ���
					jsonObject.put("drawAnticipationString", drawAnticipationString);//���ν������е�anticipation�ַ���
					//jsonObject.put("isclickEnv",isclickEnv);
					jsonObject.put("enactedValence", enactedValence);
					jsonObject.put("bumpTimes", bumpTimes);
					//jsonObject.put("isEnactCompositeInteraction", isEnactCompositeInteraction);
					
					//jsonObject.put("compositeInteractionForEnactingString", compositeInteractionForEnactingString);//��ǰenact composite interaction�� string
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
			}else {// �ڶ��κͺ����Ľ���
				System.out.println("-------------------------- The beginning of the algorithm -----------------------------------------------");
				System.out.println("loop number is:"+loopNum);
				
				//get the enacted interaction, novelty could be produced in this part. be used for comparing the intended interaction with enacted interaction
				/*********************************************************************************/
				Interaction enactedInteraction1 = this.getEnactedInteraction();//get the previous enacted interaction ��reasoning�������õ�
				
				
				if(!isGetCompositeInteraction ) {
					//û����enact composite interaction �Ľ��̵��У���ͨ�Ľ�������
					//���� get anticipations �� select anticipations ������̵���
					drawInteractionString = "";
					drawAnticipationString = "";
					
					isPrimitiveIntendedInteraction = false;
					isFinishString = "no";
					
					isFirstEnactedcompositeInteraction = "no";//yes or no ������ҳ�浱��ʹ��
					isInEnactedCompositeInteraction = "no"; //yes  or no ������ҳ�浱��ʹ��
					compositeInteractionForEnactingString = "";
					resultEnactedcompositeInteractionString = "";
					
					
					List<Anticipation> anticipations = anticipate();
					Anticipation selectedAnticipation = selectInteraction(anticipations);
					//testici ���
					
					Interaction selectedInteraction = null;
					if(selectedAnticipation.isPrimitive()) {//�õ���default anticipation����anticipationListΪ�գ�ֱ��ִ�ж�������
						selectedInteraction = selectedAnticipation.getExperience().getIntendedInteraction();
					}else {
						//��anticipationList���������ݣ�������Ҫ�����һ����single����multiple������
						Anticipation optimalAnticipation = selectedAnticipation.getAnticipationList().get(0);
						if(optimalAnticipation.isInteractionPrimitive()) {//anticipationList����ѡ�е�Ϊsingle interaction������
							System.out.println("The best choice of the anticipation interaction is Primitive");
							selectedInteraction = addOrGetPrimitiveInteraction(optimalAnticipation.getExperience(),optimalAnticipation.getInteractionResult(),0);//�õ�ԭʼ��primitive postInteraction
						}else {//anticipationList ѡ�е�Ϊ multiple interaction�ĵ�����
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
						
						
						//anticipation ���е�anticipationList���ǿյģ�����enacted (single/multiple) interaction�����
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
						//�õ���intendedInteraction��һ��primitive��interaction
						intendedInteraction = selectedInteraction;
						isPrimitiveIntendedInteraction = true;
					}else {
						System.out.println("the intended interaction is composite interaction");
						System.out.println("the intended composite interaction is: "+selectedInteraction);
						compositeInteractionForEnacting = selectedInteraction;  //����enact��������composite interaction
						compositeInteractionForEnactingString = compositeInteractionForEnacting.toString();
						isGetCompositeInteraction = true;  //��ʼ����enact composite interaction״̬
						isEnactCompositeInteraction = true;  //�Ƿ���enact composite interaction��״̬
						isFirstConstructNodeList = true;    //���ڹ����ڵ㣬�ڵ�һ�λ��composite interaction֮�󣬿�ʼ������֮��ֹͣ������ֱ��enact���̽�����
						
						isFirstEnactedcompositeInteraction = "yes";//��composite interaction�ĵ�һ���Ľڵ㣬��ҳ�浱��Ҫ��¼��ǰ��x����ֵ
						isInEnactedCompositeInteraction = "yes"; //�Ѿ�������enact composite interaction�Ĺ��̵���
						
					}
					
					//�ڻ�ȡ����intended interactin֮�󣬶��ڵ�ǰ���㷨ִ�к�Ľ������������Ϊ����agent��mood���ԡ�
					/*�⼸��mood�����Կ���Ϊ���µļ��������Σ� 
					 * 1, pleased��valence>0, ˵�������Ľ���Ƚ�����
					 * 2, unpleased, valence<0, ˵�������Ľ�������
					 * 3�� satisfied,   ��intendedInteraction == enactedInteraction�����ε���
					 * 4�� unSatisfied, ��intendedInteraction != enactedInteraction�����ε���
					 */
					/*
					if(this.getMood().equals(Mood.PLEASED)) {
						//�����������
						//intendedInteraction = enactedInteraction1;
						//isEnacting = true;
					}else {//��εĽ�����������ˣ������������Ľ������в����ṩ֧��
						//List<Anticipation> anticipations = anticipate();
						//Interaction selectedInteraction = selectInteraction(anticipations).getIntendedInteraction();
						//intendedInteraction
					}
					*/
				}//���û����enact composite interaction���У���ô����֮ǰ��ɸѡ�������У������enact composite interaction���̵��У�ֱ�Ӱ���enact��
				
				//������enact composite interaction �׶Σ��𲽵İ���composite interaction��˳�򽻻�
				if(isEnactCompositeInteraction && compositeInteractionForEnacting != null) {
					System.out.println("Is going to enact the composite interaction: "+compositeInteractionForEnacting);
					if(isFirstConstructNodeList) {//�״ΰ��ն�������ɽڵ�Ĵ洢��֮��ֱ��ʹ�ü���
						Node mainNode = new Node(compositeInteractionForEnacting);
						mainNode.setUpperNode(null);
						mainNode.setUpperNodeDirection(0);//0Ϊ�У�1Ϊleft��2Ϊ��
						nodeList.add(mainNode);
						nodeStack.push(mainNode);
						//�����еĽڵ㶼������һ���б��У�ͨ����������б���ʵ�ֶ���composite interaction��neact����Ҫ�Ǹ������ϲ�����²��������
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
						farRightNode.setLast(true);//�趨���ұߵĽڵ��־
						System.out.println("The size of the nodeList is: "+nodeList.size());
						isFirstConstructNodeList = false;
						//�ڻ����composite interaction�����н��֮�󣬿�ʼѡ��Ŀ�ʼ���������ݡ�
					}
					
					//�����composite interaction���н�㹹��֮����Ҫ���enact���е�interaction
					Node root = nodeList.get(0);
					if(isFirstNode) {//enact composite interaction ��һ�ν���ѡ���node������ߵ�interaction�ڵ㣩
						node = farLeft(root);
						intendedInteraction = node.getInteraction();
						updateNodes(node);
						isFirstNode = false;
					}else {
						node = rightNearest(node,nodeList);
						intendedInteraction = node.getInteraction();
						updateNodes(node);//��Ȼ����composite interaction�����һ������ˣ����ǻ���Ҫ��֤�������Ƿ����Ҫ��
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
				
				//��������Ķ���intended interaction��enacted interaction�ıȽϣ�ֻ����enact composite  interaction
				//���вŻ���֣���Ҳ��һ����Ҫע������ݣ����agent��Ҫȥenact һ�����е�interaction���������ݿ��Է���һ��
				//predictive��ģ�͵��С�
				/*
				if(intendedInteraction.equals(this.getEnactedInteraction())) {
					node = rightNearest(node,nodeList);
					intendedInteraction = node.getInteraction();
				}else {
					isEnactCompositeInteraction = false;
					isGetCompositeInteraction = false;
				}*/
				
				//������˽���������֮����Ҫ���һЩ��β���ʵĹ���������һ����״̬Ϊ��һ�εĽ�����׼����
				Interaction enactedCompositeInteraction = null; //���ڷ���enact composite interaction�Ĵ�Ľ��
				if(isEnactCompositeInteraction) {//�����enact composite interaction�Ľ׶�
					/*
					 * ��Ҫ�Ƚ���Щinteraction�������ͬ����Ҫ����һ��enacted interaction���������composite interaction������
					 * ͬʱ����Ҫ�˳���ǰ����composite interaction�Ľ������ݡ�
					 * ����enacted interaction���齨
					 * �����齨composite interaction��enacted interaction���С�
					 */
					if(intendedInteraction.equals(enactedInteraction2)) {
						System.out.println("The intendedInteraction equals with the enactedInteraction");
						Node root = nodeList.get(0);
						System.out.println("The root visited state is: "+root.isVisited());//����ʾcomposite interaction�Ƿ��Ѿ����enact��ɡ�
						if(root.isVisited()) {//���е�interaction���Ѿ�enact�ˣ����ҽ����ȷ���������ִ���Ѿ�Բ�������
							enactedCompositeInteraction = compositeInteractionForEnacting;
							isEnactCompositeInteraction = false;
							isGetCompositeInteraction = false;//��ʼ���µ�previous interaction exploitation�׶�
							nodeList.clear();
							node = null;
							isFirstConstructNodeList = true;
							isFirstNode = true;
							enactedInteraction2 = enactedCompositeInteraction;//��Ҫ����learnCompositeInteraction()����ʹ��
							isFinishString = "yes";
							
							interactionStack.clear();
							//Բ�����������composite�����ݣ�����ں��湹��composite interaction���ܵ�Ӱ�졣
							//���Ҳ����Ҫ����˼���Ĳ��֡�
							//��Ȼ�����һ����㣬���ҽ����ɹ�������Ŀǰ��״̬������enact composite interaction��ϵ�е���
						}else{//�����ǰ��intended ��enacted��ͬ������composite interaction��û�з��ʽ���������˳�����������
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
					}else{//���intended ��enacted��ͬ������µ�composite interaction�Ĺ�����Ȼ���˳���ǰ���ظ�����
						//����Ҫ���nodeList,node
						System.out.println("************** test start ***************");
						System.out.println("************** the intended interaction is different with enacted interaction ***************");
						System.out.println("intended interaction is: "+intendedInteraction);
						System.out.println("enated interaction is: "+enactedInteraction2);
						//���composite interaction��enacted interaction
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
						//��Ȼ��ǰ�Ľ��������ͬ�����ǵ�ǰ��״̬������enact composite interaction�Ĺ��̵��С�
						isFinishString = "yes";
						System.out.println("The real enacted composite interaction is: "+enactedInteraction2);
					}
				}
				
				if(!isEnactCompositeInteraction){//û����enact composite interaction״̬����
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
				JSONObject jsonObject = new JSONObject();  //����Json����
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

				//��enact composite interaction���У�ִ���˵�һ��interaction֮��ʹ��֮�����״̬���ı䡣
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
		//�õ�����Ҫ��list<list>
		
		
		
		//������һ�ε�interaction ��action��Ҫ���previous interactions
		//�õ���ͬ���е�interaction֮��Ҫѡ��ȥִ���ĸ�candidate list,����exploitation�����û�������exploration���̡�
		String multiInteractionListString = "";
		if(multiInteractionList.size()>0) {
			int shortDistance=100;
			double meanValence = -20;
			for(int h=0;h<multiInteractionList.size();h++) {
				int tempTotalValence = 0;//���ڼ���ƽ��value
				List<Interaction> tempInteractionList = multiInteractionList.get(h);
				multiInteractionListString = multiInteractionListString+tempInteractionList+"\r\n";
				int tempSize = tempInteractionList.size();
				if(tempSize>0) {
					boolean isHaveMaxValence = false;
					for(int l=0;l<tempSize;l++) {
						Interaction inte = tempInteractionList.get(l);
						tempTotalValence+=inte.getValence();
						if(inte.getValence() >=  maxValence) {
							isHaveMaxValence = true;//ֻ�дﵽmaxValua��tempInteractionList�Ż���֮���interaction���п���
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
			}// end for ��ѡ�б�
			System.out.println("shortDistance is:"+shortDistance+" meanValence is:"+meanValence);
			System.out.println();
		}// end if multiInteractionList.size() > 0 
		System.out.println("multiInteractionList is:");
		System.out.println(multiInteractionListString);
		System.out.println();
		System.out.println("the selected intendedInteractionList is:"+bestSelection);
		
		
		//avoidance action selection ���⡰��������������
		if(bestSelection == null) {//�������������Ҫ�ų�֮ǰû�дﵽvalence��actions������һ�ε�interaction���б�������action
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
		//�����defaultAnticipation��������Ե�proclivity��������
		Collections.sort(anticipations);
		for(Anticipation anti:anticipations) {
			//ÿ��defaultAnticipation���е�anticipationListҲ����һ������
			if(!anti.isPrimitive())Collections.sort(anti.getAnticipationList());
		}
		
		drawAnticipationString = "";
		for(Anticipation anti : anticipations) {
			if(!anti.isPrimitive) {
				//default anti��������
				String notPrimitiveString = anti.getExperience().getIntendedInteraction().getLabel()+"_"+anti.getProclivity()+",";
				//detail anti ��������
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
		
		//������selectedAnticipationyou���������һ����ԭʼ��default anticipation����Ϊԭʼ�Ķ���
		//��һ���Ǽ����Ķ��У�����Ҳ�����������һ����enacted primitive interaction��һ����enact multiInteraction�� 
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
		Position initialPosition = (Position)availablePositionList.get(0);//ȡһ��λ�á�
		//String tempDirection = directions[random.nextInt(4)];//���ĸ���ͬ�ķ������ѡ��һ������
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
		//����ֻ��������ȡÿ��anticipation���е�anticipationList���е�actionList,������һ������ʡ�ԣ����Ǽ���Ҳû��ʲôӰ��
		
		//System.out.println("postInteraction is: "+postInteraction);
		
		List<Integer> anticipationActionList = new ArrayList<Integer>();//���ڱ��涯�����е�list
		
		//������������ķ�ʽ������compositeInteraction���е�interaction��˳���ȡ��������
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
		//List<Anticipation> anticipations = getDefaultAnticipations(); //׼��Ĭ�ϵ�anticipation ���ƵĶ�����
		
		List<Anticipation> defaultAnticipations = getDefaultAnticipations(); //Ĭ�ϵ�׼���飬������
		List<Interaction> activatedInteractions =  getActivatedInteractions();// ������е� activated composite interaction with <pre,post>
		List<Anticipation> anticipationTemporary = new ArrayList<Anticipation>();//������ʱ���� ��default anticipation�����post interaction
		
		//��һ��������Ҫ���ڵ�һ�ν�����������ڶ������ں����������activatedInteraction�����
		if(this.getEnactedInteraction() != null && activatedInteractions.size()>0) {//����activatedInteraction.size>0,ֻ���ڻ�ü����������£����µĴ�����̲��ܽ���
			//������ʼ��anticipation list��׼������Ӧ��proclivity�Ĵ����͸���
			
			for(Interaction activatedInteraction : activatedInteractions) {
				//System.out.println();
				//System.out.println("activated Interaction is: "+activatedInteraction);
				int proclivitytem = activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence();
				//System.out.println("proclivitytem is: "+proclivitytem);
				
				//����activatedInteraction���е�postInteraction��primitive Interaction�����,��Ҫ����result�ļ�¼���Ա�����enact enacted primitive interaction���п����һ�֮ǰ�Ľ����
				Anticipation anticipationForAction = new Anticipation(activatedInteraction.getPostInteraction().getExperience(),proclivitytem);
				anticipationForAction.setActivatedInteraction(activatedInteraction);
				
				if(activatedInteraction.getPostInteraction().isPrimitive()) {
					anticipationForAction.setInteractionPrimitive(true);//���anticipation���е�interaction��һ��������interaction���������
					anticipationForAction.setInteractionResult(activatedInteraction.getPostInteraction().getResult());
				}
				anticipationForAction.setCompositeWeight(activatedInteraction.getWeight());
				
				
				//���ݼ����activated interaction��post interaction�γɲ�ͬ��anticipations,�����䱣����anticipationTemporary����
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
					
					
					//������ƺ�ʵ��ϸ����Ҫ�ú�����
					//��ʵ activated composite interaction��post interaction��valence һֱ�ǲ������仯�ġ�
					//�ȸ������е�anticipation�����г��ֿ��Լ���������ͬ�������
					//������ͬ��post interaction���������Ӧ��Ȩ��Ҳ�������ӣ������ں���ļ��㵱�У�
					//���ڳ��ֲ�ͬ��������������໥���������
					anticipationTemporary.get(index).addProclivity(proclivitytem);//ֱ�Ӽ��ϸ�proclivityֵ����
					anticipationTemporary.get(index).addCompositeWeight(anticipationForAction.getCompositeWeight());
					
					//����֮ǰ�������anticipation����ô�鿴�Ƿ���Ҫ�������anticipation��weight
					/*
					if(anticipationTemporary.get(index).getCompositeWeight()<anticipationForAction.getCompositeWeight()) {
						anticipationTemporary.get(index).setCompositeWeight(anticipationForAction.getCompositeWeight());
					}*/
				}
			}
			//System.out.println();
			
			
			
			//��ɶ�����һ�׶ε�anticipation���ж������еĹ���
			for(Anticipation antiForActions : anticipationTemporary) {
				List<Integer> anticipationActionList = getAnticipationActionList(antiForActions);
				antiForActions.setActionList(anticipationActionList);
			}
			
			
			
			//����anticipation��action List���е�һ����������ʼ��activated interaction���з���
			//������֮ǰ�Ľ���������action selection���е����ã���Ҫ����һ�鵱������
			for(Anticipation defaultAnticipation:defaultAnticipations) {
				
				//��Ҫ��������defaultAnticipation������������ԣ�һ���ǹ����ö���������anticipationList��ͬʱ������Ӧ��proclivity
				List<Anticipation> defaultAnti = new ArrayList<Anticipation>();
				int defaultProclivity = 0;
				
				//���δ�anticipationTemporary������������ൽָ����defaultAnticipation����
				for(Anticipation antiForCategorizing:anticipationTemporary) {
					if(!antiForCategorizing.isCategorized()) {//Ϊ�˼���ÿ�α����Ĵ�����רע��û�з����detail anticipation
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
							antiForCategorizing.setCategorized(true);//��anticipation�Ѿ������ʹ��ˣ��������´η���ʱ��ֱ��������
						}
					}//!isCategorized
				}
				
				//ִ��һ�α���anticipationTemporary֮�󣬸�����Ӧ��anticipationList��proclivity
				defaultAnticipation.setAnticipationList(defaultAnti);
				defaultAnticipation.setProclivity(defaultProclivity);
				if(!defaultAnti.isEmpty())defaultAnticipation.setPrimitive(false);//�����anticipation������anticipationList����ô��Ҫenact composite interaction
			}//dfaultAnticipation
		}
		return defaultAnticipations;
		
		
		/*
		System.out.println("-------------------------- �ָ��� ---------------------------");
		if (this.getEnactedInteraction() != null){
			for (Interaction activatedInteraction : activatedInteractions){
				//System.out.println("the post interaction is:"+activatedInteraction.getPostInteraction());
				int proclivitytem = activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence();
				//������ڵ������ǣ�����ÿ��������ͬprevious interaction��composite interaction
				//ÿ�ζ�Ҫ����һ��ͳ�ƣ�����Щ��Ϣ������Ĭ�ϵ�ѡ��У�Ȼ��ѡ��һ�����ʵ�ѡ��������Ҫ�ú�����
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
		
		//show ��ʾ���м����composite interaction
		String activatedString = "\n";
		for (Interaction activatedInteraction : activatedInteractions) {
			activatedString=activatedString+activatedInteraction+"\n";
		}
		System.out.println("all activated interactions are : "+activatedString);
		
		return activatedInteractions;
	}
	
	
	
}