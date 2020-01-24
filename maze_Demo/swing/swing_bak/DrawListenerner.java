/**
 * 1.����DrawListener�¼������࣬����Ҫʵ��ActionLIstener�����¼��ӿڡ�
 * MouseListener����¼��ӿں�MouseMotionListener����ƶ��¼��ӿڣ�
 */
package swing_bak;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;


public class DrawListenerner implements ActionListener, MouseListener,
		MouseMotionListener {

	private String type = "Ǧ��";// ����ͼ�����ԣ������洢�û�ѡ���ͼ��
	private Color color = Color.black;// ������ɫ���ԣ������洢�û�ѡ�����ɫ
	private Graphics g;// ����Graphics������Ķ���������
	private int t1, t2, t3, t4, count = 0;
	private int x1, y1, x2, y2;// ����
	private Shape shape;// ����ͼ�ζ�����
	private Shape[] shapeArray;// �����洢ͼ�ζ�����������������
	private int number = 0;// ��¼������������¼�Ѿ��洢��ͼ�θ�����
	private JPanel panel4;
	Random r = new Random();

	public BasicStroke s1;// ���ʴ�С
	BasicStroke s = new BasicStroke();

	/**
	 * 1 ���췽��
	 * 
	 * @param g�Ǵ�DrawMain��Ĵ����ϴ��ݹ����Ļ��ʶ���
	 * @param shapeArray�Ǵ�DrawMain�ഫ�ݹ����Ĵ洢ͼ�ε��������
	 */
	public DrawListenerner(JPanel panel4, Shape[] shapeArray) {

		this.panel4 = panel4;
		this.shapeArray = shapeArray;
	}

	public void setG(Graphics g) {
		this.g = g;
	}

	/**
	 * ��дActionListener�ӿ��е�actionPerformed���󷽷���
	 */
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();// ��ȡ�¼�Դ����
		String text = button.getText();// ��ȡ��ť�ϵ�������Ϣ
		if (text == null || text.equals("")) {// �ж��Ƿ���������ɫ��ť
			color = button.getBackground();// ��ȡ��ť�ı�����ɫ
			System.out.println("color = " + color);
		} else {
			type = button.getText();// ��ȡ��ť�ϵ�������Ϣ
			System.out.println("text = " + text);
		}
	}

	/**
	 * �������¼�Դ�����Ϸ�����갴�����¶���ʱִ�д˷���
	 */
	public void mousePressed(MouseEvent e) {
		// 2.����д�İ��¶������ͷŶ������¼��������У��ڰ��·����л�ȡ��������ֵ
		x1 = e.getX();
		y1 = e.getY();

		// //���û��ʵ���ɫ
		// g.setColor(color);
		if (type.equals("����")) {
			try {
				x1 = e.getXOnScreen();
				y1 = e.getYOnScreen();
				// ����һ�������˶���
				Robot jiqiren = new Robot();
				// 2.����һ����������������������Ҫ��ȡ��ɫ������
				Rectangle rec = new Rectangle(x1, y1, 1, 1);
				BufferedImage ima = jiqiren.createScreenCapture(rec);
				// 3.��ȡͼƬ�ı������ص���ɫ:��ԭɫred blue green,��ȡͼƬָ�����ص���ɫ
				int a = ima.getRGB(0, 0);
				color = new Color(a);

				// 4.����ɫ���õ�������
			} catch (AWTException e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * �������¼�Դ�����Ϸ�����갴���ͷŶ���ʱִ�д˷���
	 */
	public void mouseReleased(MouseEvent e) {
		// 2.����д�İ��¶������ͷŶ������¼��������У����ͷŷ����л�ȡ�ͷŵ�����ֵ��
		x2 = e.getX();
		y2 = e.getY();
		// 2.֮����ݰ��º��ͷŵ�����ֵ���Լ�ͼ�κ���ɫ��ʹ��Graphics�����ƶ�Ӧ��ͼ�Ρ�
		if (type.equals("ֱ��")) {
			// // System.out.println(">>>>>" + g);
			// g.drawLine(x1, y1, x2, y2);// ���û���ֱ�ߵķ���
			// ����������ʵ����ͼ�ζ���
			shape = new ShapeLine(x1, y1, x2, y2, color, new BasicStroke(1));
			// ����ͼ�εĻ�ͼ����
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// ��ͼ�ζ�����뵽������
				shapeArray[number++] = shape;
			}

		} else if (type.equals("��������")) {

			if (count == 0) {
				shape = new ShapeLine(x1, y1, x2, y2, color, new BasicStroke(1));
				// ����ͼ�εĻ�ͼ����
				shape.draw((Graphics2D) g);
				count++;
				t1 = x1;
				t2 = y1;
			}

			else if (count != 0) {
				shape = new ShapeLine(t3, t4, x2, y2, color, new BasicStroke(1));// ������õ���ǰһ��������
				// ����ͼ�εĻ�ͼ����
				shape.draw((Graphics2D) g);
				if (e.getClickCount() == 2) {// ˫�������պ��߶Σ������µĵ�������ĵ������ӣ��պ�ͼ��
					shape = new ShapeLine(t1, t2, x2, y2, color, new BasicStroke(1));
					shape.draw((Graphics2D) g);
					count = 0;
				}
				// ��¼Ŀǰ�õ��λ��
			}
			t3 = x2;
			t4 = y2;

				if (number < shapeArray.length) {
					shapeArray[number++] = shape;

				}

			} else if (type.equals("Բ�Ǿ���")) {
				shape = new ShapeRoundRect(Math.min(x1, x2), Math.min(y1, y2),
						Math.abs(x1 - x2), Math.abs(y1 - y2), color,
						new BasicStroke(), 30, 30);
				shape.draw((Graphics2D) g);

				if (number < shapeArray.length) {
					// ��ͼ�ζ�����뵽������
					shapeArray[number++] = shape;
				}
			} else if (type.equals("���Բ")) {
				shape = new ShapeCircle(x1, y1, x2, y2, color, s);
				shape.draw((Graphics2D) g);
				if (number < shapeArray.length) {
					// ��ͼ�ζ�����뵽������
					shapeArray[number++] = shape;
				}
			} else if (type.equals("�����")) {
				shape = new ShapeStar(x1, y1, x2, y2, color, s);
				shape.draw((Graphics2D) g);
				System.out.println(">>>>>>>>>"+x1+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+y1);
				if (number < shapeArray.length) {
					// ��ͼ�ζ�����뵽������
					shapeArray[number++] = shape;
					
				}

			}
		}
	

	/**
	 * ������ڴ����ϰ��°��������϶�ʱִ�д˷���
	 */
	public void mouseDragged(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		Graphics2D g2d = (Graphics2D) g;
		if (type.equals("Ǧ��")) {

			// ����������ʵ����ͼ�ζ���
			shape = new ShapeLine(x1, y1, x2, y2, color, new BasicStroke(1));
			// ����ͼ�εĻ�ͼ����
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// ��ͼ�ζ�����뵽������
				shapeArray[number++] = shape;
			}
			x1 = x2;
			y1 = y2;
		} else if (type.equals("ˢ��")) {

			// ����������ʵ����ͼ�ζ���
			shape = new ShapeLine(x1, y1, x2, y2, color, new BasicStroke(10));
			// ����ͼ�εĻ�ͼ����
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// ��ͼ�ζ�����뵽������
				shapeArray[number++] = shape;
			}
			x1 = x2;
			y1 = y2;
		} else if (type.equals("��ǹ")) {
			g2d.setColor(color);
			shape = new ShapeGum(x1, y1, x2, y2, color, s);
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// ��ͼ�ζ�����뵽������
				shapeArray[number++] = shape;
			}
			x1 = x2;
			y1 = y2;
		} else if (type.equals("��Ƥ")) {
			shape = new ShapeEraser(x1, y1, x2, y2, color, s);
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// ��ͼ�ζ�����뵽������
				shapeArray[number++] = shape;
			}

			x1 = x2;
			y1 = y2;
		} else if (type.equals("ͼƬ")) {
			shape = new ShapeImage(color, x1, y1, x2, y2);
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// ��ͼ�ζ�����뵽������
				shapeArray[number++] = shape;
			}
		} else if (type.equals("������")) {
			shape = new ShapeFillRect(Math.min(x1, x2), Math.min(y1, y2),
					Math.abs(x1 - x2), Math.abs(y1 - y2), color,
					new BasicStroke(1));
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// ��ͼ�ζ�����뵽������
				shapeArray[number++] = shape;
			}
		} else if (type.equals("����")) {
			shape = new ShapeString(x1, y1, x2, y2, color, s);
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// ��ͼ�ζ�����뵽������
				shapeArray[number++] = shape;
			}
		} else if (type.equals("3d����")) {// 7.3d����
			shape = new Shape3DRect(x1, y1, x2, y2, color, s);
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// ��ͼ�ζ�����뵽������
				shapeArray[number++] = shape;
			}
		}

	}

	/**
	 * ������ڴ������ƶ����ʱִ�д˷���
	 */
	public void mouseMoved(MouseEvent e) {

	}

	/**
	 * �������¼�Դ�����Ϸ��������(���º��ͷŵĶ���������ͬһ��λ����)����ʱִ�д˷���
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * ��������������¼�Դ������ʱִ�д˷���
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * �����������뿪�¼�Դ������ʱִ�д˷���
	 */
	public void mouseExited(MouseEvent e) {
	}
}