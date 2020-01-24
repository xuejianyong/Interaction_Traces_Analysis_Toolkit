package swing1;
	import java.awt.AWTException;
/**
         *界面类：实现一个有直线、填充矩形、填充圆、圆角矩形、3D矩形、文字、任意多边形、
         *  五角星、铅笔、刷子、橡皮、喷枪、吸管以及可以选择画笔颜色的画图程序，并且实现重绘。
	 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dl.Position;

public class ReDrawMain extends JPanel {
	public int x_index = 0;
	public int y_index = 0;
	public int interval = 500/10;
	public int innerDist = interval/2;
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
	
	public Position randomPosition;
	public String direction = "";
	public boolean isRunnning = false;
	
	
	
	public JFrame frame=new JFrame();
	
	
	
	
	
	public static void main(String[] args) {
		new ReDrawMain().initUI();
	}
    
	
	
	// 声明图形对象数组，数组的能存储的元素个数是10000个图形
	private Shape[] shapeArray = new Shape[10000];
	
    
    /**
	 * 初始化界面的方法
	 */
	public void initUI() {
	    frame.setTitle("Developmental Learning and Constructivist Paradigm (DLCP)");
		frame.setDefaultCloseOperation(3);
		frame.setSize(1200, 800);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);

		DrawListenerner dl = new DrawListenerner(this, shapeArray);
		
		
		JPanel panel1 = new JPanel(); // 北边面板（存放铅笔，喷枪等工具按钮）
		panel1.setPreferredSize(new Dimension(120, 50));
		panel1.setBackground(Color.white);
		panel1.setLayout(new FlowLayout());
		frame.add(panel1, BorderLayout.NORTH);
		/*
		String[] str1 = { "铅笔", "刷子", "喷枪", "吸管", "橡皮" };
		for (int i = 0; i < str1.length; i++) {
			JButton button_1 = new JButton(str1[i]);
			panel1.add(button_1);
			button_1.setPreferredSize(new Dimension(110, 30));
			button_1.addActionListener(dl);
		}
		*/

		
		JPanel panel2 = new JPanel(); // 西边面板（存放图形按钮）
		panel2.setPreferredSize(new Dimension(50, 100));
		panel2.setBackground(Color.white);
		frame.add(panel2, BorderLayout.WEST);
		/*
		String[] str2 = { "直线", "填充圆", "文字", "图片", "圆角矩形", "填充矩形", "3d矩形", "五角星",
				"任意多边形" };
		for (int i = 0; i < str2.length; i++) {
			JButton button_2 = new JButton(str2[i]);
			panel2.add(button_2);
			button_2.setPreferredSize(new Dimension(120, 30));
			button_2.addActionListener(dl);
		}*/
		
		
		JPanel panel3 = new JPanel(); // 东边面板（存放颜色按钮）
		panel3.setPreferredSize(new Dimension(50, 100));
		panel3.setBackground(Color.white);
		frame.add(panel3, BorderLayout.EAST);
		/*
		// 定义Color数组，用来存储按钮上要显示的颜色信息
		Color[] colorArray = { Color.BLUE, Color.GREEN, Color.RED, Color.BLACK,
				Color.lightGray, Color.ORANGE, Color.PINK,
				new Color(123, 100, 132) };
		// 循环遍历colorArray数组，根据数组中的元素来实例化按钮对象
		for (int i = 0; i < colorArray.length; i++) {
			JButton button = new JButton();
			button.setBackground(colorArray[i]);
			button.setPreferredSize(new Dimension(50, 50));
			// 4.将事件源按钮对象通过addActionListener()监听方法和事件处理类dl对象绑定。
			button.addActionListener(dl);
			panel3.add(button);
		}*/
		
		
		JPanel panel4 = new JPanel(); // 东边面板（存放颜色按钮）
		panel4.setPreferredSize(new Dimension(100, 50));
		panel4.setBackground(Color.white);
		frame.add(panel4, BorderLayout.SOUTH);
		
		
		
		JPanel panen = new JPanel();
		JButton bStart = new JButton("Start");
		JButton bStop = new JButton("Stop");
		JButton bReset = new JButton("Reset");
		panen.setLayout(new GridLayout(3, 1));
		
		FlowLayout layout = new FlowLayout();
        layout.setHgap(10);
        layout.setVgap(10);
        panen.setLayout(layout);
        
		panen.add(bStart);
		panen.add(bStop);
		panen.add(bReset);
		this.add(panen);
		
		
		
		
		
		
		
		frame.add(this, BorderLayout.CENTER);
		frame.setBackground(Color.gray);
	    frame.setVisible(true);// 可见必须在gr之前，否则看不到画笔

	    Graphics gr = this.getGraphics();// 从窗体上获取画笔对象
		this.addMouseListener(dl);
		this.addMouseMotionListener(dl);
		dl.setG(gr);
		
	}

	// 声明图形对象数组，数组的能存储的元素个数是10000个图形
	

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
		Position initialPosition = (Position)availablePositionList.get(0);
		//String tempDirection = directions[random.nextInt(4)];//有四个不同的方向，随机选择一个方向
		initialPosition.setDirection("left");
		return initialPosition;
	}
	
	
	/**
	 * 重写组件的paint方法
	 */
	public void paint(Graphics graphic) {
		//super.paint(gr);//调用父类的paint（）
		// 循环遍历shapeArray数组
		/*
		for (int i = 0; i < shapeArray.length; i++) {
			Shape shape = shapeArray[i];// 从数组中获取Shape对象
			if(shape!=null)//shape是否不为空，如果不为空则调用画图形的方法
				shape.draw((Graphics2D)gr);//调用绘制图形的方法
			
		}*/
		randomPosition = getInitialPosition();
		direction = randomPosition.getDirection();
		
		Graphics2D g2d = (Graphics2D)graphic;
		drawEnvInitial(x_index,y_index,g2d);
		drawArow(randomPosition,direction, x_index,y_index,g2d);
	}

	public void drawArow(Position randomPosition, String direction, int x, int y, Graphics2D g) {
		// TODO Auto-generated method stub
		x_index = x+innerDist+randomPosition.getPointX()*interval;
		y_index = y+innerDist+randomPosition.getPointY()*interval;
		
		int x1 = x_index;
		int y1 = y_index-innerDist;
		int x2 = x_index-innerDist;
		int y2 = y_index+innerDist;
		int x3 = x_index;
		int y3 = y_index+innerDist/2;
		int x4 = x_index+innerDist;
		int y4 = y_index+innerDist;
		int x_array[] = {x1,x2,x3,x4};
		int y_array[] = {y1,y2,y3,y4};
		int p = y_array.length;
		g.setColor(Color.blue);
		g.fillPolygon(x_array, y_array, p);
		g.setColor(Color.BLACK);
		g.drawPolygon(x_array, y_array, p);
	}

	public void drawEnvInitial(int x, int y, Graphics2D g){
		// TODO Auto-generated method stub
		g.setColor(Color.green);
		g.fillRect(0, 0, 450, 50);
		g.fillRect(450, 0, 50, 450);
		g.fillRect(50, 450, 450, 50);
		g.fillRect(0, 50, 50, 450);
		
		g.fillRect(100, 100, 250, 250);
		g.fillRect(350, 150, 50, 200);
		g.fillRect(150, 350, 250, 50);
		g.fillRect(400, 50, 50, 50);
		g.fillRect(50, 400, 50, 50);
		
		Graphics gra = g.create(125, 125, 1, 1);
		System.out.println(gra.getColor());
		
		
		
		
		/*
		//Color color = r.getPixelColor(e.getXOnScreen(),e.getYOnScreen());
		Graphics gra = g.create(0, 0, 1, 1);
		BufferedImage ima = gra.
		int a = ima.getRGB(0, 0);
		Color color = new Color(a);
		System.out.println(color.getRed());
		System.out.println(color.getGreen());
		System.out.println(color.getBlue());
		System.out.println();
		*/
		/*
		Robot rb  = new Robot();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Rectangle rec = new Rectangle(0, 0, 1, 1);
		BufferedImage bi = rb.createScreenCapture(rec);
		bi
		int pixelColor = bi.getRGB(x, y);
		System.out.println(16777216 + pixelColor);
		*/
		
	}

}

