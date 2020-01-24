//ÏðÆ¤Àà
package swing1;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class ShapeEraser extends Shape{
    
	public ShapeEraser(int x1, int y1, int x2, int y2, Color color, BasicStroke s) {
		super(x1, y1, x2, y2,color,s);
		
	}
    public void draw(Graphics2D g){
    	g.setStroke(new BasicStroke(20));// ÏðÆ¤´óÐ¡
		JFrame frame = new JFrame();
		g.setColor(frame.getBackground());
		g.drawLine(getX1(),getY1(),getX2(),getY2());
    	
    }

}
