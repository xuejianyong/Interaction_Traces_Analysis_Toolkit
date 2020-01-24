/**
 * ����ShapeRoundRect�࣬��������������Բ�Ǿ��ε���
 */
package swing1;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * ����ShapeRoundRect�࣬��������������Բ�Ǿ��ε���
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
	 * ����ͼ�εķ���
	 * 
	 * @param g���ʶ���
	 */
	public void draw(Graphics2D g) {
		g.setStroke(getStroke());// ���û��ʵĴ�ϸ
		g.setColor(getColor());// ���û��ʵ���ɫ
		g.drawRoundRect(getX1(), getY1(), getX2(), getY2(), arcW, arcH);// ���ƾ���
	}
}