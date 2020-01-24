//五角星类
package swing_bak;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Graphics2D;


public class ShapeStar extends Shape {
    private int x,y;
    private int a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, b1, b2, b3, b4, b5,
	b6, b7, b8, b9, b10;
	public ShapeStar(int x1, int y1, int x2, int y2, Color color, BasicStroke s) {
		super(x1, y1, x2, y2,color,s);
		
	}
	public void draw(Graphics2D g){
		g.setStroke(new BasicStroke());// 画笔大小
		g.setColor(getColor());//设置画笔颜色
		
		
		x = (Math.abs(getX2()-getX1())) / 8;
		y = (Math.abs(getY2()-getY1())) / 8;
		a1 = getX1() + 4 * x;
		b1 = getY1() + y;
		a2 = getX1();
		b2 = getY1() + 3 * y;
		a3 = getX1() + 8 * x * 2 / 6;
		b3 = getY1() + 3 * y;
		a4 = getX1() + 8 * x * 4 / 6;
		b4 = getY1() + 3 * y;
		a5 = getX2();
		b5 = getY1()+ 3 * y;
		a6 = getX1() + 4 * x / 2;
		b6 = getY1() + 4 * y;
		a7 = getX1() + 6 * x;
		b7 = getY1() + 4 * y;
		a8 = getX1() + 4 * x;
		b8 = getY1() + 8 * y * 2 / 3;
		a9 = getX1();
		b9 = getY2();
		a10 = getX2();
		b10 = getY2();
		g.drawLine(a1, b1, a3, b3);
		g.drawLine(a1, b1, a4, b4);
		g.drawLine(a2, b2, a3, b3);
		g.drawLine(a2, b2, a6, b6);
		// g.drawLine(a3, b3, a4, b4);
		// g.drawLine(a3, b3, a6, b6);
		g.drawLine(a4, b4, a5, b5);
		// g.drawLine(a4, b4, a7, b7);
		g.drawLine(a5, b5, a7, b7);
		// g.drawLine(a6, b6, a8, b8);
		g.drawLine(a6, b6, a9, b9);
		g.drawLine(a7, b7, a10, b10);
		// g.drawLine(a7, b7, a8, b8);
		g.drawLine(a8, b8, a10, b10);
		g.drawLine(a8, b8, a9, b9);

	}
        
}