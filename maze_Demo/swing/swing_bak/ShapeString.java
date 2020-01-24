//文字类
package swing_bak;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Graphics2D;

public class ShapeString extends Shape{
    private String str="弹个吉他";
	public ShapeString(int x1, int y1, int x2, int y2, Color color, BasicStroke s) {
		super(x1, y1, x2, y2,color,s);
		
	}
	public void draw(Graphics2D g){
		g.setStroke(new BasicStroke());// 画笔大小
		g.setColor(getColor());//设置画笔颜色
		
		g.drawString(str, getX1(), getY1());// 5.文字
	}

}