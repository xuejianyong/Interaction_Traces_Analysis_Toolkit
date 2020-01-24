//��������
package swing_bak;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * ����ShapeRect�࣬�������������ƾ��ε���
 */
public class ShapeFillRect extends Shape {

	public ShapeFillRect(int x1, int y1, int x2, int y2, Color color,
			BasicStroke s) {
		super(x1, y1, x2, y2, color, s);
	}

	/**
	 * ����ͼ�εķ���
	 * 
	 * @param g���ʶ���
	 */
	public void draw(Graphics2D g) {
		g.setStroke(getStroke());// ���û��ʵĴ�ϸ
		g.setColor(getColor());// ���û��ʵ���ɫ
		g.fillRect(getX1(), getY1(), getX2(), getY2());// ���ƾ���
	}
}

