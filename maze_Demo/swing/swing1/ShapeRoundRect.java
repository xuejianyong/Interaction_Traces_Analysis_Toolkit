/**
 * 定义ShapeRoundRect类，该类是用来绘制圆角矩形的类
 */
package swing1;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 定义ShapeRoundRect类，该类是用来绘制圆角矩形的类
 */
public class ShapeRoundRect extends Shape {

	private int arcH, arcW;

	public ShapeRoundRect(int x1, int y1, int x2, int y2, Color color,
			BasicStroke s, int arcH, int arcW) {
		super(x1, y1, x2, y2, color, s);
		this.arcH = arcH;
		this.arcW = arcW;
	}

	/**
	 * 绘制图形的方法
	 * 
	 * @param g画笔对象
	 */
	public void draw(Graphics2D g) {
		g.setStroke(getStroke());// 设置画笔的粗细
		g.setColor(getColor());// 设置画笔的颜色
		g.drawRoundRect(getX1(), getY1(), getX2(), getY2(), arcW, arcH);// 绘制矩形
	}
}