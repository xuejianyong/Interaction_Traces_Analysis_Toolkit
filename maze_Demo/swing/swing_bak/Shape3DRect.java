//3d矩形类
package swing_bak;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Shape3DRect  extends Shape{

	public Shape3DRect(int x1, int y1, int x2,int y2, Color color, BasicStroke s) {
		super(x1, y1, x2, y2, color,s);
		
	}
	public void draw(Graphics2D g){
		g.setStroke(g.getStroke());
		g.setColor(getColor());// 设置画笔颜色
		g.fill3DRect(getX1(), getY1(), getX2() - getX1(), getY2() - getY1(), true);
	}
}