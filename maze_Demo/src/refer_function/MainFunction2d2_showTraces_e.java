package level2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/*
 * this edition is going to draw the sets afforded by the phenomena with the agent learns them
 * this edition is going to change the current belief state when the agent encounted with the marked belief state
 * result: have finished the modify of current belief state ,delete the line drawing below the countnumber. 
 * let the intended experience above the enacted experience.
 */



public class MainFunction2d2_showTraces_e extends JFrame{
	
	/* array[i][j]  j={actionType,resultNumber,Value,colorIndex} 
	 * 0:feel left
	 * 1:swap left
	 * 2:feel both
	 * 3:feel right
	 * 4:swap right
	 */
	public int width = 200; 
	public int rate = 8;
	public int intervalExt = width/3;
	//public int intervalInn = width/5;
	public int intervalInn = 0;
	public int intervalUpAndDown = width/4;
	
	public int x_index = 500;
	public int y_index = 500;
	
	public MainFunction2d2_showTraces_e() {
		setSize(100,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(Color.white);
	}
	
	public int experiences[][] = {
			{0,0,0,2},{0,1,0,1},
			{1,0,0,2},{1,1,0,1},
			{2,0,0,2},{2,1,0,4},{2,2,10,1},
			{3,0,0,2},{3,1,0,1},
			{4,0,0,2},{4,1,0,1}};
	
	public int getScore(int actionType,int result) {
		// get the score from the experiences
		int score = 0;
		for(int i=0;i<11;i++){
			if(experiences[i][0] == actionType) {
				if(experiences[i][1]==result) {
					score = experiences[i][2];
				};
			}else if(experiences[i][0] > actionType)break;
		}
		return score;
	}
	
	public void drawFeelLeft(int x,int y,int result,Graphics2D g) {
		int rate = 8;
		int x1 = x;
		int y1 = y;
		int x2 = x+width;
		int y2 = y+width/rate;
		int x3 = x+width;
		int y3 = y+width-width/rate;
		int x4 = x;
		int y4 = y+width;
		int x5 = x;
		int y5 = y;
		int x_array[] = {x1,x2,x3,x4,x5};
		int y_array[] = {y1,y2,y3,y4,y5};
		int p = y_array.length;
		if(result == 1) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		g.fillPolygon(x_array, y_array, p);
		g.setColor(Color.BLACK);
		g.drawPolygon(x_array, y_array, p);
	}

	public void drawLittleFeelLeft(int x,int y,int result,Graphics2D g) {
		int littleWidth = width/2;
		int rate = 8;
		int x1 = x;
		int y1 = y;
		int x2 = x+littleWidth;
		int y2 = y+littleWidth/rate;
		int x3 = x+littleWidth;
		int y3 = y+littleWidth-littleWidth/rate;
		int x4 = x;
		int y4 = y+littleWidth;
		int x5 = x;
		int y5 = y;
		int x_array[] = {x1,x2,x3,x4,x5};
		int y_array[] = {y1,y2,y3,y4,y5};
		int p = y_array.length;
		if(result == 1) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		g.fillPolygon(x_array, y_array, p);
		g.setColor(Color.BLACK);
		g.drawPolygon(x_array, y_array, p);
	}
	
	public void drawFlipleft(int x,int y,int result,Graphics2D g) {
		if(result == 1) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		int bias = width/2;
		int x_rect = x+bias;
		g.fillArc(x, y, width, width, 90, 180);
		g.fillRect(x_rect, y, width/2, width);
		g.setColor(Color.BLACK);
		g.drawArc(x, y, width, width, 90, 180);
		g.drawLine(x_rect, y, x_rect+bias, y);
		g.drawLine(x_rect+bias, y, x_rect+bias, y+width);
		g.drawLine(x_rect, y+width, x_rect+bias, y+width);
	}
	
	public void drawLittleFlipleft(int x,int y,int result,Graphics2D g) {
		int littleWidth = width/2;
		if(result == 1) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		int bias = littleWidth/2;
		int x_rect = x+bias;
		g.fillArc(x, y, littleWidth, littleWidth, 90, 180);
		g.fillRect(x_rect, y, littleWidth/2, littleWidth);
		g.setColor(Color.BLACK);
		g.drawArc(x, y, littleWidth, littleWidth, 90, 180);
		g.drawLine(x_rect, y, x_rect+bias, y);
		g.drawLine(x_rect+bias, y, x_rect+bias, y+littleWidth);
		g.drawLine(x_rect, y+littleWidth, x_rect+bias, y+littleWidth);
	}
	
	public void drawFeelBoth(int x,int y,int result,Graphics2D g) {
		if(result == 2) {
			g.setColor(Color.GREEN);
		}else if(result == 1){
			g.setColor(Color.PINK);
		}else {
			g.setColor(Color.RED);
		}
		g.fillRect(x, y, width, width);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, width);
	}

	public void drawLittleFeelBoth(int x,int y,int result,Graphics2D g) {
		int littleWidth = width/2;
		if(result == 2) {
			g.setColor(Color.GREEN);
		}else if(result == 1){
			g.setColor(Color.PINK);
		}else {
			g.setColor(Color.RED);
		}
		g.fillRect(x, y, littleWidth, littleWidth);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, littleWidth, littleWidth);
	}
	
	public void drawFeelright(int x,int y,int result,Graphics2D g) {
		int x1 = x;
		int y1 = y+width/rate;
		int x2 = x+width;
		int y2 = y;
		int x3 = x+width;
		int y3 = y+width;
		int x4 = x;
		int y4 = y+width-width/rate;
		int x5 = x;
		int y5 = y+width/rate;
		int x_array[] = {x1,x2,x3,x4,x5};
		int y_array[] = {y1,y2,y3,y4,y5};
		int p = y_array.length;
		if(result == 1) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		g.fillPolygon(x_array, y_array, p);
		g.setColor(Color.BLACK);
		g.drawPolygon(x_array, y_array, p);
	}

	public void drawLittleFeelright(int x,int y,int result,Graphics2D g) {
		int littleWidth = width/2;
		int x1 = x;
		int y1 = y+littleWidth/rate;
		int x2 = x+littleWidth;
		int y2 = y;
		int x3 = x+littleWidth;
		int y3 = y+littleWidth;
		int x4 = x;
		int y4 = y+littleWidth-littleWidth/rate;
		int x5 = x;
		int y5 = y+littleWidth/rate;
		int x_array[] = {x1,x2,x3,x4,x5};
		int y_array[] = {y1,y2,y3,y4,y5};
		int p = y_array.length;
		if(result == 1) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		g.fillPolygon(x_array, y_array, p);
		g.setColor(Color.BLACK);
		g.drawPolygon(x_array, y_array, p);
	}
	
	public void drawFlipRight(int x,int y,int result,Graphics2D g) {
		if(result == 1) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		int bias = width/2;
		int x_rect = x-bias;
		g.fillRect(x, y, width/2, width);
		g.fillArc(x, y, width, width, -90, 180);
		g.setColor(Color.BLACK);
		g.drawArc(x, y, width, width, -90, 180);
		g.drawLine(x, y, x+bias, y);
		g.drawLine(x, y, x, y+width);
		g.drawLine(x, y+width, x+bias, y+width);
	}
	
	public void drawLittleFlipRight(int x,int y,int result,Graphics2D g) {
		int littleWidth = width/2;
		if(result == 1) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		int bias = littleWidth/2;
		int x_rect = x-bias;
		g.fillRect(x, y, littleWidth/2, littleWidth);
		g.fillArc(x, y, littleWidth, littleWidth, -90, 180);
		g.setColor(Color.BLACK);
		g.drawArc(x, y, littleWidth, littleWidth, -90, 180);
		g.drawLine(x, y, x+bias, y);
		g.drawLine(x, y, x, y+littleWidth);
		g.drawLine(x, y+littleWidth, x+bias, y+littleWidth);
	}
	
	public void drawFunctionInitial(int x,int y,Graphics2D g) {
		System.out.println(x+" "+y+" "+width);
		int x_rect = x-width-width/4;
		int y_rect = y-(width/8)*7;
		g.setColor(Color.WHITE);
		g.fillRect(x_rect, y_rect, (width/4)*3, (width/4)*3);
		g.setFont(new Font("Arial", Font.PLAIN, (width/4)*3));
		int x_font = x_rect+width/8;
		int y_font = y_rect+(width/8)*5;
		g.setColor(Color.BLACK);
		g.drawString("1" ,x_font,y_font);
		g.drawRect(x_rect, y_rect, (width/4)*3, (width/4)*3);
		
		/*
		int x2_rect = x_rect-width/4;
		int y2_rect = y_rect+width-width/4+intervalUpAndDown;
		g.setColor(Color.WHITE);
		g.fillRect(x2_rect, y2_rect, (width/4)*3, (width/4)*3);
		int x2_font = x2_rect+width/8;
		int y2_font = y2_rect+(width/8)*5;
		g.setColor(Color.BLACK);
		g.drawString("2" ,x2_font,y2_font);
		g.drawRect(x2_rect, y2_rect, (width/4)*3, (width/4)*3);
		*/
		
		int x3_rect = x_rect;
		int y3_rect = y_rect+width+intervalUpAndDown;
		g.setColor(Color.WHITE);
		g.fillRect(x3_rect, y3_rect, (width/4)*3, (width/4)*3);
		int x3_font = x3_rect+width/8;
		int y3_font = y3_rect+(width/8)*5;
		g.setColor(Color.BLACK);
		g.drawString("2" ,x3_font,y3_font);
		g.drawRect(x3_rect, y3_rect, (width/4)*3, (width/4)*3);
		
		int x4_rect = x3_rect;
		int y4_rect = y3_rect+width+intervalUpAndDown;
		g.setColor(Color.WHITE);
		g.fillRect(x4_rect, y4_rect, (width/4)*3, (width/4)*3);
		int x4_font = x4_rect+width/8;
		int y4_font = y4_rect+(width/8)*5;
		g.setColor(Color.BLACK);
		g.drawString("3" ,x4_font,y4_font);
		g.drawRect(x4_rect, y4_rect, (width/4)*3, (width/4)*3);
		
		int x5_rect = x4_rect;
		int y5_rect = y4_rect+width+intervalUpAndDown;
		g.setColor(Color.WHITE);
		g.fillRect(x5_rect, y5_rect, (width/4)*3, (width/4)*3);
		int x5_font = x5_rect+width/8;
		int y5_font = y5_rect+(width/8)*5;
		g.setColor(Color.BLACK);
		g.drawString("4" ,x5_font,y5_font);
		g.drawRect(x5_rect, y5_rect, (width/4)*3, (width/4)*3);
		
		/*
		int x6_rect = x5_rect;
		int y6_rect = y5_rect+width+intervalUpAndDown;
		g.setColor(Color.WHITE);
		g.fillRect(x6_rect, y6_rect, (width/4)*3, (width/4)*3);
		int x6_font = x6_rect+width/8;
		int y6_font = y6_rect+(width/8)*5;
		g.setColor(Color.BLACK);
		g.drawString("6" ,x6_font,y6_font);
		g.drawRect(x6_rect, y6_rect, (width/4)*3, (width/4)*3);
		*/
	}
	
	public void drawFunction(int x, int y,Experience exp,Graphics2D g) {
		int actionType = exp.action;
		int result = exp.result;
		switch(actionType) {
		case 0:drawFeelLeft(x,y,result,g);break;
		case 1:drawFlipleft(x,y,result,g);break;
		case 2:drawFeelBoth(x,y,result,g);break;
		case 3:drawFeelright(x,y,result,g);break;
		case 4:drawFlipRight(x,y,result,g);break;
		}
	}
	
	public void drawLittleFunction(int x, int y,Experience exp,boolean isPhenomena, Graphics2D g) {
		int actionType = exp.action;
		int result = exp.result;
		if(isPhenomena) {
			x = x+width/4;
			y = y+width/3;
		}else {
			x = x + width/4;
			y = y + width/4;
		}
		switch(actionType) {
		case 0:drawLittleFeelLeft(x,y,result,g);break;
		case 1:drawLittleFlipleft(x,y,result,g);break;
		case 2:drawLittleFeelBoth(x,y,result,g);break;
		case 3:drawLittleFeelright(x,y,result,g);break;
		case 4:drawLittleFlipRight(x,y,result,g);break;
		}
	}
	
	public void drawFunctionFont(int x, int y,Integer countNum,Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, (width/4)*3));
		int widthPont=0;
		int heightPont=y-width;
		if(countNum<10) {
			widthPont = x+width/3;
		}else if(countNum/100 > 0) {
			widthPont = x-width/8;
		}else {
			widthPont = x+width/5-15;
		}
		g.drawString(countNum.toString() ,widthPont,heightPont);
	}
	
	public void drawFunctionLine(int x,int y,Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawLine(x ,y-width/2,x+width+intervalExt,y-width/2);
	}
	
	public void drawFunctionValence(int x,int y,Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(x_index+intervalInn, y-(width/4)*3, width, width/4);
	}
	
	public void drawFunctionPointRect(int x,int y,Graphics2D g) {
		int x1 = x;
		int y1 = y+width/2;
		int x2 = x+width/2;
		int y2 = y+width/4;
		int x3 = x+width;
		int y3 = y+width/2;
		int x_array[] = {x1,x2,x3,x1};
		int y_array[] = {y1,y2,y3,y1};
		int p = y_array.length;
		g.setColor(Color.GRAY);
		g.fillPolygon(x_array, y_array, p);
		g.fillRect(x1, y1, width, width/4);
		g.setColor(Color.BLACK);
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y2, x3, y3);
		g.drawLine(x1, y1, x1, y1+width/4);
		g.drawLine(x1, y+width*3/4, x+width, y+width*3/4);
		g.drawLine(x+width, y+width/2, x+width, y+width*3/4);
	}
	
	public void drawFunctionExcited(int x,int y,int excitementCount,Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, (width/6)*excitementCount);
	}
	
	public void drawFunctionCurious(int x,int y,Graphics2D g) {
		int x_font = x+width/4;
		int y_font = y+width- width/4;
		g.setColor(Color.BLACK);
		g.drawString("?" ,x_font,y_font);
	}
	
	public void drawFunctionHedonist(int x,int y,Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillArc(x, y, width, width, 0, 360);
		g.setColor(Color.BLACK);
		g.drawArc(x, y, width, width, 0, 360);
	}
	
	public void drawFunctionFinishLearn(int x,int y,Graphics2D g) {
		int x1 = x;
		int y1 = y+width/2;
		int x2 = x+width/2;
		int y2 = y+width/4;
		int x3 = x+width;
		int y3 = y+width/2;
		int x_array[] = {x1,x2,x3,x1};
		int y_array[] = {y1,y2,y3,y1};
		int p = y_array.length;
		g.setColor(Color.GREEN);
		g.fillPolygon(x_array, y_array, p);
		g.fillRect(x1, y1, width, width/4);
		g.setColor(Color.BLACK);
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y2, x3, y3);
		g.drawLine(x1, y1, x1, y1+width/4);
		g.drawLine(x1, y+width*3/4, x+width, y+width*3/4);
		g.drawLine(x+width, y+width/2, x+width, y+width*3/4);
	}
	
	public void drawPhenomena(int x, int y, Experience exp,Graphics2D g) {
		int rate = 3;
		int x1 = x;
		int y1 = y+width/rate;
		int x2 = x+width/2;
		int y2 = y;
		int x3 = x+width;
		int y3 = y+width/rate;
		int x4 = x+width;
		int y4 = y+width;
		int x5 = x;
		int y5 = y+width;
		int x_array[] = {x1,x2,x3,x4,x5};
		int y_array[] = {y1,y2,y3,y4,y5};
		int p = y_array.length;
		g.setColor(Color.BLACK);
		boolean isPhenomena = true;
		g.drawPolygon(x_array, y_array, p);
		drawLittleFunction(x,y,exp,isPhenomena,g);
	}
	
	public void drawAfford(int x, int y, Experience exp,Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, width);
		boolean isPhenomena = false;
		drawLittleFunction(x,y,exp,isPhenomena,g);
	}
	
	public void paint(Graphics graphic) {
		Graphics2D g2d = (Graphics2D)graphic;
		BufferedImage targetImg = new BufferedImage(100000, 1500, BufferedImage.TYPE_INT_ARGB);
		//graphic = targetImg.createGraphics();
		g2d = targetImg.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(7,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND));
		//g2d.setBackground(Color.WHITE);
		drawFunctionInitial(x_index,y_index,g2d);
		/*
		int min = 0;
		int max = 4;
		int historicalDepth = 10; // I have no idea about this variate
		*/
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
		String fileName = "ExperimentResult"+df.format(new Date()).toString();
		try {
			System.setOut(new PrintStream(new FileOutputStream("./Result/"+fileName+".txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//**************************************** the preparing work for implementation ****************************************************
		
		
		int threshold = 5;
		int winScore = 10;
		int score = 0;
		String experienceLabels[]= {"Feel Left","Swap Left","Feel Both","Feel Right","Swap Right"};
		String states[] = {"unknown","sporadic","persistent"}; // unknown 0, sopradic 1, persistent 2
		String moodLabels[] = {"curious","hedonist","excited"};// curious 0, hedonist 1, excited 2
		int mood = 0;
		
		BeliefState unknownBeliefState = new BeliefState(); // the initial beliefState is unknown
		BeliefState allknownBeliefState = new BeliefState(1);
		BeliefState currentBeliefState = unknownBeliefState;
		List<BeliefState> newCreatedBeliefList = new ArrayList<BeliefState>();//use to save the new created belief state
		
		List<Integer> sporadicActionList = new ArrayList<Integer>();
		List<Experience> sporadicExperienceList = new ArrayList<Experience>();
		List<List<Experience>> perSpoPerList = new ArrayList<List<Experience>>();
		Map<List<Experience>,Integer> perPerSpoMap = new HashMap<List<Experience>,Integer>();//experience,experience,sporadicAction
		Map<List<Experience>,Integer> perSpoPerIsConfirmMap = new HashMap<List<Experience>,Integer>();//experience,sporadicExperience,experience,isConfirmed
		
		Environment env = new Environment();
		Experience exp00 = new Experience(0,0,0);
		Experience exp01 = new Experience(0,1,0);
		Experience exp10 = new Experience(1,0,0);
		Experience exp11 = new Experience(1,1,0);
		Experience exp20 = new Experience(2,0,0);
		Experience exp21 = new Experience(2,1,0);
		Experience exp22 = new Experience(2,2,0);
		Experience exp30 = new Experience(3,0,0);
		Experience exp31 = new Experience(3,1,0);
		Experience exp40 = new Experience(4,0,0);
		Experience exp41 = new Experience(4,1,0);
		Experience Experiences[][] = {{exp00,exp01},{exp10,exp11},{exp20,exp21,exp22},{exp30,exp31},{exp40,exp41}};
		
		// Experience marked code domain
		Map<Experience,Integer> experienceMarkedMap = new HashMap<Experience, Integer>();
		experienceMarkedMap.put(exp00, 0);
		experienceMarkedMap.put(exp01, 0);
		experienceMarkedMap.put(exp10, 0);
		experienceMarkedMap.put(exp11, 0);
		experienceMarkedMap.put(exp20, 0);
		experienceMarkedMap.put(exp21, 0);
		experienceMarkedMap.put(exp22, 0);
		experienceMarkedMap.put(exp30, 0);
		experienceMarkedMap.put(exp31, 0);
		experienceMarkedMap.put(exp40, 0);
		experienceMarkedMap.put(exp41, 0);
		
		boolean isInitial = true;
		boolean isAllTried = false;
		boolean isMarked = false;
		boolean isAllMarked = false;
		boolean isUpdate = false;
		
		List<Experience> experienceList = new ArrayList<Experience>();//record the experienced experience
		List<Integer> recentExperience = new ArrayList<Integer>();
		
		
		//************************************************ the initial work *****************************************
		
		int excitementCount = 1;          //the number of excitement
		int excitementThreshold = 5;
		Experience intendedExperience = null;
		Experience enactedExperience = null;
		Integer countNum = 0;
		Integer countNumOut = 0;
		int result = 0;
		int mark;
		int recentCount = 27;
		List<List<Integer>> numScoreList = new ArrayList<List<Integer>>();// to show the countNum and Score
		//********************************************** start the algorithm *****************************************
		
		
		while(countNumOut++ < 100) {
			//the function of this switch is to chose the next experience
			countNum++;
			System.out.println("The Current Count Number is:"+countNum);
			System.out.println("the BeliefState is:"+currentBeliefState.toString());
			System.out.println("the Mood  State is:"+mood);
			
				
			drawFunctionFont(x_index,y_index-width/2,countNum,g2d);
			//drawFunctionLine(x_index,y_index,g2d);
			//System.out.println("the BeliefState is:"+currentBeliefState.toString());
			//System.out.println("the Mood  State is:"+mood);
			
			
			
			
			// get the intented experience to execute
			switch(mood){
			case 0://curious
				if(isInitial){
					intendedExperience = exp00;
					isInitial = false;
				}else{
					System.out.println("get the intendedexperience from function leasTriedExperience");
					intendedExperience = currentBeliefState.leastTriedExperience();
				}break;
			case 1://hedonist
				System.out.println("=====================================================");
				//parameters(perPerSpoMap)try to get the max valence with the map of (persistent,persistent Sporadic action)
				intendedExperience = currentBeliefState.intentionWithMaxExpectedOutcomeValence(perSpoPerList);
				System.out.println("the hedonist experience is"+intendedExperience.toString());
				//intendedExperience = beliefState.intentionWithMaxExpectedOutcomeValence(currentBeliefState,
				//unknownBeliefList,sporaticBeliefList,persistentBeliefList);
				break;
			case 2://excited------------------------------------------------------------
				intendedExperience = enactedExperience;
				break;
			}//end switch
			
			System.out.println("the IntendedExperience is:"+intendedExperience.toString());
			drawFunction(x_index,y_index-width,intendedExperience,g2d);
			result = env.getResult(intendedExperience.action);
			enactedExperience = Experiences[intendedExperience.action][result];
			System.out.println("the EnactedExperience  is:"+enactedExperience.toString());
			drawFunction(x_index+intervalInn,y_index+intervalInn+intervalUpAndDown,enactedExperience,g2d);
			//System.out.println("the x_index value is"+x_index+" and the y_value is"+y_index);
			score = score + enactedExperience.valence;
			if(enactedExperience.valence>0) {
				//drawFunctionValence(x_index,y_index,g2d);
			}
			System.out.println("The current score is:"+score);
			experienceList.add(intendedExperience);
			currentBeliefState.updateTriedNumberofExperience(intendedExperience);
			System.out.println("updated the triedNumber Experience");
			
			
			//**************************************************************************************************
			if(intendedExperience.result != enactedExperience.result ) {
				if(currentBeliefState.persistentExperience != null) {
					currentBeliefState.affordExperienceList.add(enactedExperience);
				}
			}
			//*************************************************************************************************
			
			
			if(mood == 2) {
				if(!(enactedExperience.action == intendedExperience.action && enactedExperience.result == intendedExperience.result)) {
					for(Experience key : experienceMarkedMap.keySet()) {
						if(key.action == intendedExperience.action  && key.result == intendedExperience.result) {
							experienceMarkedMap.put(key,1);
							System.out.println("the intended experience is sporadic and update the experienceMarkedMap");
						}
					}
					
					// how to update the state of mood and current beliefstate ?
					mood = 0;// refer to step 11
					currentBeliefState = unknownBeliefState; // refer to step 11
					isMarked = true;
					excitementCount = 1;
					if(!sporadicActionList.contains(intendedExperience.action)) {
						sporadicActionList.add(intendedExperience.action);
					}
					System.out.println("add the sporadicExperience into the sporadicExperienceList");
					if(!sporadicExperienceList.contains(intendedExperience)) {
						sporadicExperienceList.add(intendedExperience);
					}
				}else if(!(excitementCount < excitementThreshold)) {
					for(Experience key : experienceMarkedMap.keySet()) {
						if(key.action == intendedExperience.action  && key.result == intendedExperience.result) {
							experienceMarkedMap.put(key,2);
							currentBeliefState = new BeliefState(enactedExperience);
							newCreatedBeliefList.add(currentBeliefState);// createNewBeliefSta
							//currentBeliefState = newBeliefState;
							System.out.println("the intended experience is persistent and update the experienceMarkedMap");
						}
					}
					mood = 0;
					excitementCount = 1;
					isMarked = true;
				}else{
					excitementCount++;
				}
			}//end if(mood = 2)
			
			
			mark = currentBeliefState.getMarkFromMap(experienceMarkedMap,enactedExperience);
			if(mark == 0) {
				System.out.println("get the markedState from the map that shows this enactedExperience marked state is: "+mark);
				mood = 2;
				currentBeliefState = unknownBeliefState;
			}else {
				System.out.println("the  enacted Experience is already marked and the mark is:"+mark);
				if(mark == 2) {
					for(int newPersistentIndex=0;newPersistentIndex<newCreatedBeliefList.size();newPersistentIndex++) {
						BeliefState beliefStateFromList = newCreatedBeliefList.get(newPersistentIndex);
						Experience persistentExperienceFromList = beliefStateFromList.persistentExperience;
						if(persistentExperienceFromList.action == enactedExperience.action &&
								persistentExperienceFromList.result == enactedExperience.result) {
							currentBeliefState = beliefStateFromList;
						}
					}
				}
			}
			
			if(mood == 1) {
				currentBeliefState = allknownBeliefState;
			}
			
			// draw beliefstate
			System.out.println("the draw current belief state is:"+currentBeliefState.toString());
			System.out.println("the draw current mood   state is:"+mood);
			if(currentBeliefState.equals(unknownBeliefState)) {
				drawFunctionPointRect(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
			}else if(currentBeliefState.equals(allknownBeliefState)){
				drawFunctionFinishLearn(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
			}else {
				drawFunction(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,
						currentBeliefState.persistentExperience,g2d);
			}
			//draw mood
			if(mood == 2 && excitementCount >0){
				drawFunctionExcited(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,
						excitementCount,g2d);
			}else if(mood == 0) {
				drawFunctionCurious(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
			}
			else if(mood == 1) {
				drawFunctionHedonist(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
				break;
			}
			
			
			
			
			int x_persistent =  x_index+intervalInn;
			int y_persistent =  y_index+(width+intervalUpAndDown)*3+intervalInn;
			if(currentBeliefState.persistentExperience != null) {
				//drawPhenomena(x_persistent,y_persistent,currentBeliefState.persistentExperience,g2d);
				if(currentBeliefState.affordExperienceList != null && currentBeliefState.affordExperienceList.size()>0) {
					for(int affordIndex = 0; affordIndex< currentBeliefState.affordExperienceList.size();affordIndex++) {
						int x_afford = x_persistent;
						int y_afford = y_persistent+width;
						Experience affordExperience = currentBeliefState.affordExperienceList.get(affordIndex);
						//drawAfford(x_afford,y_afford,affordExperience,g2d);
					}
				}
			}
			
			
			
			
			
			
			System.out.println();
			System.out.println("The sporadic experience List is:");
			for(int sporadicIndex=0;sporadicIndex<sporadicExperienceList.size();sporadicIndex++) {
				System.out.println(sporadicExperienceList.get(sporadicIndex).toString());
			}
			if(newCreatedBeliefList.size() == 0) {
				System.out.println("The newCreatedBeliefList is NULL");
			}else {
				System.out.println("The createNewBeliefStateList is:");
				for(int index = 0;index < newCreatedBeliefList.size();index ++) {
					System.out.println(newCreatedBeliefList.get(index).persistentExperience.toString());
				}
			}
			
			
			
			
			
			
			
			
			
			/*
			BeliefState nextBeliefState = currentBeliefState.updateAndGetBelief(enactedExperience);//该函数的作用使用来构建各个节点之间的相互关系的。
			if(nextBeliefState == null) {
				//currentBeliefState = unknownBeliefState;
			}else {
				currentBeliefState = nextBeliefState;
			}
			*/		
			
			/*
			 *  use the list to learn the arcs between different type experiences, but how to handle with the settings of currentbeliefstate
			 *  this procedure only happy in the situation when the isMarked changed
			 */
			//the dual analysis(这里双重分析) consider the current situation of the environment(state of phenomenonLeft and phenomenonRight)
			boolean isChanged = false; //to flag the previous persistent experience to post persistent experience 构建关系的list如果发生变化，则该flag状态为true
			if(isMarked) {
				System.out.println();
				System.out.println("Enter the connected procedure process");
				if(sporadicExperienceList.size()>0 && newCreatedBeliefList.size()>0) {
					loop:for(int sporExpIndex=0;sporExpIndex<sporadicExperienceList.size();sporExpIndex++) {
						Experience sporadicExperience = sporadicExperienceList.get(sporExpIndex);
						int sporadicAction = sporadicExperience.action;
						System.out.println("sporadicAction is:"+sporadicAction);
						for(int persistentIndex=0;persistentIndex<newCreatedBeliefList.size();persistentIndex++) {
							
							System.out.println();
							System.out.println("11111111111111111111111111111111111111111111111111");
							BeliefState observerBeliefState = newCreatedBeliefList.get(persistentIndex);
							Experience observer = observerBeliefState.persistentExperience;
							System.out.println("observer is:"+observer.toString());
							System.out.println("The state of the environment is:  left:"+env.phenomenonLeft+"  right:"+env.phenomenonRight);
							int observerResultPre = env.getResult(observer.action);
							Experience enactedExperiencePre = Experiences[observer.action][observerResultPre];
							System.out.println("the intendtedExperience  is:"+observer.toString());
							System.out.println("the enactedExperiencePre is:"+enactedExperiencePre.toString());
							
							countNum = countNum+1;
							System.out.println("The Current Count Number is:"+countNum);
							x_index=x_index+width+intervalExt;
							drawFunctionFont(x_index,y_index-width/2,countNum,g2d);
							//drawFunctionLine(x_index,y_index,g2d);
							drawFunction(x_index,y_index-width,observer,g2d);
							drawFunction(x_index+intervalInn,y_index+intervalInn+intervalUpAndDown,enactedExperiencePre,g2d);
							score = score + enactedExperiencePre.valence;
							if(enactedExperiencePre.valence>0) {
								drawFunctionValence(x_index,y_index,g2d);
							}
							
							
							
							if(observerResultPre != observer.result) {// the persistent experience is not the current persistent experience
								System.out.println("enter the function if(observerResultPre != observer.result)");
								int persistentMark = currentBeliefState.getMarkFromMap(experienceMarkedMap,enactedExperiencePre);
								if(persistentMark == 0) {
									System.out.println("persistentMark is 0, so the agent need to check the mark of this enactedExperience");
									mood = 2;
									currentBeliefState = unknownBeliefState;
									isUpdate = true;
									enactedExperience = enactedExperiencePre;
									
									// draw beliefstate
									if(currentBeliefState.equals(unknownBeliefState)) {
										drawFunctionPointRect(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
									}else if(currentBeliefState.equals(allknownBeliefState)){
										drawFunctionFinishLearn(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
									}else {
										drawFunction(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,
												currentBeliefState.persistentExperience,g2d);
									}
									//draw mood
									if(mood == 2 && excitementCount >0){
										drawFunctionExcited(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,
												excitementCount,g2d);
									}else if(mood == 0) {
										drawFunctionCurious(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
									}
									else if(mood == 1) {
										drawFunctionHedonist(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
									}
									break loop;
									
								}else {// the persistent experience is already marked
									if(persistentMark == 2) {
										for(int newPersistentIndex=0;newPersistentIndex<newCreatedBeliefList.size();newPersistentIndex++) {
											BeliefState beliefStateFromList = newCreatedBeliefList.get(newPersistentIndex);
											Experience persistentExperienceFromList = beliefStateFromList.persistentExperience;
											if(persistentExperienceFromList.action == enactedExperiencePre.action &&
													persistentExperienceFromList.result == enactedExperiencePre.result) {
												currentBeliefState = beliefStateFromList;
											}
										}
									}
									
									// draw beliefstate
									if(currentBeliefState.equals(unknownBeliefState)) {
										drawFunctionPointRect(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
									}else if(currentBeliefState.equals(allknownBeliefState)){
										drawFunctionFinishLearn(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
									}else {
										drawFunction(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,
												currentBeliefState.persistentExperience,g2d);
									}
									//draw mood
									if(mood == 2 && excitementCount >0){
										drawFunctionExcited(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,
												excitementCount,g2d);
									}else if(mood == 0) {
										drawFunctionCurious(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
									}
									else if(mood == 1) {
										drawFunctionHedonist(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
									}
									
									
									
									
									System.out.println();
									System.out.println("22222222222222222222222222222222222222222222222222222222");
									isUpdate = false;
									//try the sporadic experience.
									int observedResult = env.getResult(sporadicAction);
									Experience enactedExperienceSpo = Experiences[sporadicAction][observedResult];
									System.out.println("the intendtedExperience  is:"+sporadicExperience.toString());
									System.out.println("the enactedExperienceSpo is :"+enactedExperienceSpo.toString());
									countNum = countNum+1;
									System.out.println("The Current Count Number is:"+countNum);
									x_index=x_index+width+intervalExt;
									drawFunctionFont(x_index,y_index-width/2,countNum,g2d);
									//drawFunctionLine(x_index,y_index,g2d);
									drawFunction(x_index,y_index-width,sporadicExperience,g2d);
									drawFunction(x_index+intervalInn,y_index+intervalInn+intervalUpAndDown,enactedExperienceSpo,g2d);
									score = score + enactedExperienceSpo.valence;
									if(enactedExperienceSpo.valence>0) {
										drawFunctionValence(x_index,y_index,g2d);
									}
									currentBeliefState = unknownBeliefState;
									// draw beliefstate
									if(currentBeliefState.equals(unknownBeliefState)) {
										drawFunctionPointRect(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
									}else if(currentBeliefState.equals(allknownBeliefState)){
										drawFunctionFinishLearn(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
									}else {
										drawFunction(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,
												currentBeliefState.persistentExperience,g2d);
									}
									//draw mood
									if(mood == 2 && excitementCount >0){
										drawFunctionExcited(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,
												excitementCount,g2d);
									}else if(mood == 0) {
										drawFunctionCurious(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
									}
									else if(mood == 1) {
										drawFunctionHedonist(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
									}
									
									
									
									
									
									System.out.println();
									System.out.println("33333333333333333333333333333333333333333333333333333333333333");
									int observerResultPos = env.getResult(observer.action);
									Experience enactedExperiencePos = Experiences[observer.action][observerResultPos];
									System.out.println("the intendedExperience is:"+observer.toString());
									System.out.println("the enactedExperiencePos is :"+enactedExperiencePos.toString());
									for(int newPersistentIndex=0;newPersistentIndex<newCreatedBeliefList.size();newPersistentIndex++) {
										BeliefState beliefStateFromList = newCreatedBeliefList.get(newPersistentIndex);
										Experience persistentExperienceFromList = beliefStateFromList.persistentExperience;
										if(persistentExperienceFromList.action == enactedExperiencePos.action &&
												persistentExperienceFromList.result == enactedExperiencePos.result) {
											currentBeliefState = beliefStateFromList;
										}
									}
									countNum = countNum+1;
									System.out.println("The Current Count Number is:"+countNum);
									x_index=x_index+width+intervalExt;
									drawFunctionFont(x_index,y_index-width/2,countNum,g2d);
									//drawFunctionLine(x_index,y_index,g2d);
									drawFunction(x_index,y_index-width,observer,g2d);
									drawFunction(x_index+intervalInn,y_index+intervalInn+intervalUpAndDown,enactedExperiencePos,g2d);
									score = score + enactedExperiencePos.valence;
									if(enactedExperiencePos.valence>0) {
										drawFunctionValence(x_index,y_index,g2d);
									}
									// draw beliefstate
									if(currentBeliefState.equals(unknownBeliefState)) {
										drawFunctionPointRect(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
									}else if(currentBeliefState.equals(allknownBeliefState)){
										drawFunctionFinishLearn(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
									}else {
										drawFunction(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,
												currentBeliefState.persistentExperience,g2d);
									}
									//draw mood
									if(mood == 2 && excitementCount >0){
										drawFunctionExcited(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,
												excitementCount,g2d);
									}else if(mood == 0) {
										drawFunctionCurious(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
									}
									else if(mood == 1) {
										drawFunctionHedonist(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
									}
									
									
									
									
									
									if(observerResultPos == observer.result) {
										List<Experience> prePos = new ArrayList<Experience>();
										List<Experience> perSpoPerListSmall = new ArrayList<Experience>();
										prePos.add(enactedExperiencePre);
										prePos.add(enactedExperiencePos);
										
										perSpoPerListSmall.add(enactedExperiencePre);
										perSpoPerListSmall.add(enactedExperienceSpo);
										perSpoPerListSmall.add(enactedExperiencePos);
										// should store the beliefstate not just the persistent experience.
										boolean isSame = currentBeliefState.getIsSame(perSpoPerList,enactedExperiencePre,
												enactedExperienceSpo,enactedExperiencePos);
										if(!isSame) {
											perSpoPerList.add(perSpoPerListSmall);
											isChanged = true;
										}
										
										if(!perPerSpoMap.containsKey(prePos)) {
											perPerSpoMap.put(prePos, sporadicAction);
											//isChanged = true;
										}
									}
								}
							}else {// the same result with before persistent result.
								// draw beliefstate
								currentBeliefState = observerBeliefState;
								System.out.println("enter the same result function");
								if(currentBeliefState.equals(unknownBeliefState)) {
									drawFunctionPointRect(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
								}else if(currentBeliefState.equals(allknownBeliefState)){
									drawFunctionFinishLearn(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
								}else {
									drawFunction(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,
											currentBeliefState.persistentExperience,g2d);
								}
								//draw mood
								if(mood == 2 && excitementCount >0){
									drawFunctionExcited(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,
											excitementCount,g2d);
								}else if(mood == 0) {
									drawFunctionCurious(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
								}
								else if(mood == 1) {
									drawFunctionHedonist(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
								}
								
								
								
								System.out.println();
								System.out.println("444444444444444444444444444444444444444444444444444444444444444444");
								int observedResult = env.getResult(sporadicAction);
								Experience enactedExperienceSpo = Experiences[sporadicAction][observedResult];
								System.out.println("the intendedExperience  is:"+sporadicExperience.toString());
								System.out.println("the enactedExperienceSpo is :"+enactedExperienceSpo.toString());
								countNum = countNum+1;
								System.out.println("The Current Count Number is:"+countNum);
								x_index=x_index+width+intervalExt;
								drawFunctionFont(x_index,y_index-width/2,countNum,g2d);
								//drawFunctionLine(x_index,y_index,g2d);
								drawFunction(x_index,y_index-width,sporadicExperience,g2d);
								drawFunction(x_index+intervalInn,y_index+intervalInn+intervalUpAndDown,enactedExperienceSpo,g2d);
								score = score + enactedExperienceSpo.valence;
								if(enactedExperienceSpo.valence>0) {
									drawFunctionValence(x_index,y_index,g2d);
								}
								currentBeliefState = unknownBeliefState;
								// draw beliefstate
								if(currentBeliefState.equals(unknownBeliefState)) {
									drawFunctionPointRect(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
								}else if(currentBeliefState.equals(allknownBeliefState)){
									drawFunctionFinishLearn(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
								}else {
									drawFunction(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,
											currentBeliefState.persistentExperience,g2d);
								}
								//draw mood
								if(mood == 2 && excitementCount >0){
									drawFunctionExcited(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,
											excitementCount,g2d);
								}else if(mood == 0) {
									drawFunctionCurious(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
								}
								else if(mood == 1) {
									drawFunctionHedonist(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
								}
								
								
								
								System.out.println();
								System.out.println("555555555555555555555555555555555555555555555555555555555555555555555555");
								int observerResultPos = env.getResult(observer.action);
								Experience enactedExperiencePos = Experiences[observer.action][observerResultPos];
								System.out.println("the intendedExperience is:"+observer.toString());
								System.out.println("the enactedExperiencePos is:"+enactedExperiencePos.toString());
								countNum = countNum+1;
								System.out.println("The Current Count Number is:"+countNum);
								x_index=x_index+width+intervalExt;
								drawFunctionFont(x_index,y_index-width/2,countNum,g2d);
								//drawFunctionLine(x_index,y_index,g2d);
								drawFunction(x_index,y_index-width,observer,g2d);
								drawFunction(x_index+intervalInn,y_index+intervalInn+intervalUpAndDown,enactedExperiencePos,g2d);
								score = score + enactedExperiencePos.valence;
								if(enactedExperiencePos.valence>0) {
									drawFunctionValence(x_index,y_index,g2d);
								}
								
								if(observerResultPos != observer.result) {// shows the sporadic action works on current persistent experience
									System.out.println("enter the function if(observerResultPos != observer.result)");
									int persistentMark = currentBeliefState.getMarkFromMap(experienceMarkedMap,enactedExperiencePos);
									if(persistentMark == 0) {
										System.out.println("persistentMark is 0, so the agent need to check the mark of this enactedExperience");
										mood = 2;
										currentBeliefState = unknownBeliefState;
										isUpdate = true;
										enactedExperience = enactedExperiencePos;
										// draw beliefstate
										if(currentBeliefState.equals(unknownBeliefState)) {
											drawFunctionPointRect(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
										}else if(currentBeliefState.equals(allknownBeliefState)){
											drawFunctionFinishLearn(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
										}else {
											drawFunction(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,
													currentBeliefState.persistentExperience,g2d);
										}
										//draw mood
										if(mood == 2 && excitementCount >0){
											drawFunctionExcited(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,
													excitementCount,g2d);
										}else if(mood == 0) {
											drawFunctionCurious(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
										}
										else if(mood == 1) {
											drawFunctionHedonist(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
										}
										break loop;
									}else {
										// draw beliefstate
										if(persistentMark == 2) {
											for(int newPersistentIndex=0;newPersistentIndex<newCreatedBeliefList.size();newPersistentIndex++) {
												BeliefState beliefStateFromList = newCreatedBeliefList.get(newPersistentIndex);
												Experience persistentExperienceFromList = beliefStateFromList.persistentExperience;
												if(persistentExperienceFromList.action == enactedExperiencePos.action &&
														persistentExperienceFromList.result == enactedExperiencePos.result) {
													currentBeliefState = beliefStateFromList;
												}
											}
										}
										
										
										
										
										
										if(currentBeliefState.equals(unknownBeliefState)) {
											drawFunctionPointRect(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
										}else if(currentBeliefState.equals(allknownBeliefState)){
											drawFunctionFinishLearn(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
										}else {
											drawFunction(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,
													currentBeliefState.persistentExperience,g2d);
										}
										//draw mood
										if(mood == 2 && excitementCount >0){
											drawFunctionExcited(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,
													excitementCount,g2d);
										}else if(mood == 0) {
											drawFunctionCurious(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
										}
										else if(mood == 1) {
											drawFunctionHedonist(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
										}
										isUpdate = false;
										List<Experience> prePos = new ArrayList<Experience>();
										List<Experience> perSpoPerListSmall = new ArrayList<Experience>();
										prePos.add(enactedExperiencePre);
										prePos.add(enactedExperiencePos);
										
										perSpoPerListSmall.add(enactedExperiencePre);
										perSpoPerListSmall.add(enactedExperienceSpo);
										perSpoPerListSmall.add(enactedExperiencePos);
										boolean isSame = currentBeliefState.getIsSame(perSpoPerList,enactedExperiencePre,
												enactedExperienceSpo,enactedExperiencePos);
										if(!isSame) {
											perSpoPerList.add(perSpoPerListSmall);
											isChanged = true;
										}
										if(!perPerSpoMap.containsKey(prePos)) {
											perPerSpoMap.put(prePos, sporadicAction);
											//isChanged = true;
										}
									}
								}else {
									// draw beliefstate
									for(int newPersistentIndex=0;newPersistentIndex<newCreatedBeliefList.size();newPersistentIndex++) {
										BeliefState beliefStateFromList = newCreatedBeliefList.get(newPersistentIndex);
										Experience persistentExperienceFromList = beliefStateFromList.persistentExperience;
										if(persistentExperienceFromList.action == enactedExperiencePos.action &&
												persistentExperienceFromList.result == enactedExperiencePos.result) {
											currentBeliefState = beliefStateFromList;
										}
									}
									
									
									
									if(currentBeliefState.equals(unknownBeliefState)) {
										drawFunctionPointRect(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
									}else if(currentBeliefState.equals(allknownBeliefState)){
										drawFunctionFinishLearn(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,g2d);
									}else {
										drawFunction(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn+intervalUpAndDown,
												currentBeliefState.persistentExperience,g2d);
									}
									//draw mood
									if(mood == 2 && excitementCount >0){
										drawFunctionExcited(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,
												excitementCount,g2d);
									}else if(mood == 0) {
										drawFunctionCurious(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
									}
									else if(mood == 1) {
										drawFunctionHedonist(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn+intervalUpAndDown,g2d);
									}
								}
							}
						}
					}
				}
				isMarked = false;
				//currentBeliefState = currentBeliefState.updateAndGetBelief(enactedExperience,mark,newCreatedBeliefList,env,Experiences);
			}//end if(isMarked)
			
			
			System.out.println("isChanged is:"+isChanged);
			if(isChanged)recentExperience.add(1);
			else recentExperience.add(0);
			System.out.println();
			// get the Marked-State of the EnactedExperience  *should check this state for every EnactedExperience
			mark = currentBeliefState.getMarkFromMap(experienceMarkedMap,enactedExperience);
			if(!isUpdate) {
				if(mark == 0) {
					System.out.println("This EnactedExperience Marked is: "+mark);
					mood = 2;
					currentBeliefState = unknownBeliefState;
				}else {
					System.out.println("This EnactedExperience is already Marked ! mark is: "+mark);
				}
			}

			
			// defined the status for agent become hedonist
			isAllTried = currentBeliefState.isAllTried();
			if(isAllTried) {
				//if(isAllTried)mood  = 0;
				System.out.println("The experiences are AllTried in this belief!");
			}
			isAllMarked = currentBeliefState.isAllMarked(experienceMarkedMap);
			if(isAllMarked) {
				System.out.println("The experiences are isAllMarked!");
			}
			boolean isRecentChange = currentBeliefState.isRecentExperienceChanged(recentExperience,recentCount);
			System.out.println("ischange is:"+recentExperience);
			if(isAllTried && isAllMarked && (!isRecentChange)) {
				mood = 1;
				currentBeliefState = allknownBeliefState;
			}
			
			
			
			System.out.println();
			System.out.println("the experience marked map is as follows:");
			for(Experience key : experienceMarkedMap.keySet()) {
				System.out.println(key.toString()+" Marked:"+experienceMarkedMap.get(key));
			}
			System.out.println();
			System.out.println("The updated currentBeliefState is:"+currentBeliefState);
			System.out.println("The updated mood is:"+mood);
			System.out.println();
			System.out.println("The currentBeliefState numberOfTriedMap");
			for(Experience key : currentBeliefState.numberOfTriedMap.keySet()) {
				System.out.println(key.toString()+" number:"+currentBeliefState.numberOfTriedMap.get(key));
			}
			
			//System.out.println("The persistent persistent sporadic Map is:");
			for(List<Experience> key : perPerSpoMap.keySet()) {
				//System.out.println(key.toString()+" "+perPerSpoMap.get(key));
			}
			System.out.println("The persistent sporadic persistent list is:");
			for(int showIndex = 0;showIndex<perSpoPerList.size();showIndex++) {
				System.out.println(perSpoPerList.get(showIndex));
			}
			System.out.println("The state of the environment is:  left:"+env.phenomenonLeft+"  right:"+env.phenomenonRight);
			
			List<Integer> numScoreSmall = new ArrayList<Integer>();
			numScoreSmall.add(score);
			numScoreSmall.add(countNum);
			numScoreList.add(numScoreSmall);
			System.out.println();
			//System.out.println("numScoreList is:");
			//System.out.println(numScoreList);
			
		
			
			
			
			/*
			//draw Persistent Experience
			System.out.println();
			System.out.println("The newCreatedBeliefList is:"+newCreatedBeliefList);
			System.out.println("The size of newCreatedBeliefList is:"+newCreatedBeliefList.size());
			int x_persistent =  x_index+intervalInn;
			int y_persistent =  y_index+(width+intervalUpAndDown)*3+intervalInn;
			
			if(newCreatedBeliefList.size()>0) {
				for(int perIndex = 0;perIndex<newCreatedBeliefList.size();perIndex++) {
					System.out.println("the drawing of newCreatedBeliefList is "+newCreatedBeliefList.get(perIndex).persistentExperience.toString());
					drawFunction(x_persistent,y_persistent,
							newCreatedBeliefList.get(perIndex).persistentExperience,g2d);
					y_persistent=y_persistent+(width/6)*7;
				}
			}
			
			if(newCreatedBeliefList.size()>0) {
				drawPhenomena(x_persistent,y_persistent,newCreatedBeliefList.get(0).persistentExperience,g2d);
			}
			*/
			
			
			x_index=x_index+width+intervalExt;
			System.out.println("---------------------------------------------------------");
		}//end while
		
		
		
		
		
		graphic.dispose();
		try {
			ImageIO.write(targetImg, "PNG", new File("./Images/"+fileName+".PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}//paint
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		new MainFunction2d2_showTraces_e();
	}//end main

}
