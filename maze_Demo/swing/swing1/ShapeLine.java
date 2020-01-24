/**
 * 定义ShapeLine类，该类是用来绘制直线的类
 */
package swing1;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class ShapeLine extends Shape {

	public ShapeLine(int x1, int y1, int x2, int y2, Color color,
			BasicStroke stroke) {
		super(x1, y1, x2, y2, color, stroke);
	}

	/**
	 * 绘制图形的方法
	 * 
	 * @param g画笔对象
	 */
	public void draw(Graphics2D g) {
		g.setStroke(getStroke());// 设置画笔的粗细
		g.setColor(getColor());// 设置画笔的颜色
		g.drawLine(getX1(), getY1(), getX2(), getY2());// 绘制直线
	}
}
