//��ǹ��
package swing1;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Graphics2D;

import java.util.Random;

public class ShapeGum extends Shape{
	Random r = new Random();
	public ShapeGum(int x1, int y1, int x2, int y2,Color color,BasicStroke stroke) {
		super(x1, y1, x2, y2, color, stroke);
	}
	public void draw(Graphics2D g){
		g.setStroke(new BasicStroke(1));
		g.setColor(g.getColor());
		g.drawLine(getX2(),getY2 (),getX2(),getY2());
		for (int i = 0; i < 10; i++) {
			int p = r.nextInt(10);
			int q = r.nextInt(10); // ��������һ������0(����)��n(������)֮�������
									// nextInt(10)���ɵľ���0~10֮��������������0��10
			g.drawLine(getX2() + p, getY2() + q, getX2() + p, getY2() + q);
		}
	}

}