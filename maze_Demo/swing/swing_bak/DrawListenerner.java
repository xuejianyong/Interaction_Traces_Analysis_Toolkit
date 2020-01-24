/**
 * 1.定义DrawListener事件处理类，该类要实现ActionLIstener动作事件接口、
 * MouseListener鼠标事件接口和MouseMotionListener鼠标移动事件接口，
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

	private String type = "铅笔";// 声明图形属性，用来存储用户选择的图形
	private Color color = Color.black;// 声明颜色属性，用来存储用户选择的颜色
	private Graphics g;// 声明Graphics画笔类的对象名属性
	private int t1, t2, t3, t4, count = 0;
	private int x1, y1, x2, y2;// 坐标
	private Shape shape;// 声明图形对象名
	private Shape[] shapeArray;// 声明存储图形对象的数组对象名属性
	private int number = 0;// 记录数器，用来记录已经存储的图形个数。
	private JPanel panel4;
	Random r = new Random();

	public BasicStroke s1;// 画笔大小
	BasicStroke s = new BasicStroke();

	/**
	 * 1 构造方法
	 * 
	 * @param g是从DrawMain类的窗体上传递过来的画笔对象
	 * @param shapeArray是从DrawMain类传递过来的存储图形的数组对象
	 */
	public DrawListenerner(JPanel panel4, Shape[] shapeArray) {

		this.panel4 = panel4;
		this.shapeArray = shapeArray;
	}

	public void setG(Graphics g) {
		this.g = g;
	}

	/**
	 * 重写ActionListener接口中的actionPerformed抽象方法。
	 */
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();// 获取事件源对象
		String text = button.getText();// 获取按钮上的文字信息
		if (text == null || text.equals("")) {// 判断是否点击的是颜色按钮
			color = button.getBackground();// 获取按钮的背景颜色
			System.out.println("color = " + color);
		} else {
			type = button.getText();// 获取按钮上的文字信息
			System.out.println("text = " + text);
		}
	}

	/**
	 * 当你在事件源对象上发生鼠标按键按下动作时执行此方法
	 */
	public void mousePressed(MouseEvent e) {
		// 2.在重写的按下动作和释放动作的事件处理方法中，在按下方法中获取按下坐标值
		x1 = e.getX();
		y1 = e.getY();

		// //设置画笔的颜色
		// g.setColor(color);
		if (type.equals("吸管")) {
			try {
				x1 = e.getXOnScreen();
				y1 = e.getYOnScreen();
				// 创建一个机器人对象
				Robot jiqiren = new Robot();
				// 2.构造一个矩形区域，这个区域就是你要获取颜色的区域
				Rectangle rec = new Rectangle(x1, y1, 1, 1);
				BufferedImage ima = jiqiren.createScreenCapture(rec);
				// 3.获取图片的背景像素点颜色:三原色red blue green,获取图片指定像素点颜色
				int a = ima.getRGB(0, 0);
				color = new Color(a);

				// 4.把颜色设置到画笔上
			} catch (AWTException e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * 当你在事件源对象上发生鼠标按键释放动作时执行此方法
	 */
	public void mouseReleased(MouseEvent e) {
		// 2.在重写的按下动作和释放动作的事件处理方法中，在释放方法中获取释放的坐标值；
		x2 = e.getX();
		y2 = e.getY();
		// 2.之后根据按下和释放的坐标值，以及图形和颜色，使用Graphics来绘制对应的图形。
		if (type.equals("直线")) {
			// // System.out.println(">>>>>" + g);
			// g.drawLine(x1, y1, x2, y2);// 调用绘制直线的方法
			// 根据数据来实例化图形对象
			shape = new ShapeLine(x1, y1, x2, y2, color, new BasicStroke(1));
			// 调用图形的绘图方法
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// 将图形对象存入到数组中
				shapeArray[number++] = shape;
			}

		} else if (type.equals("任意多边形")) {

			if (count == 0) {
				shape = new ShapeLine(x1, y1, x2, y2, color, new BasicStroke(1));
				// 调用图形的绘图方法
				shape.draw((Graphics2D) g);
				count++;
				t1 = x1;
				t2 = y1;
			}

			else if (count != 0) {
				shape = new ShapeLine(t3, t4, x2, y2, color, new BasicStroke(1));// 点击鼠标该点与前一个点相连
				// 调用图形的绘图方法
				shape.draw((Graphics2D) g);
				if (e.getClickCount() == 2) {// 双击鼠标则闭合线段，该最新的点与最早的点相连接，闭合图形
					shape = new ShapeLine(t1, t2, x2, y2, color, new BasicStroke(1));
					shape.draw((Graphics2D) g);
					count = 0;
				}
				// 记录目前该点的位置
			}
			t3 = x2;
			t4 = y2;

				if (number < shapeArray.length) {
					shapeArray[number++] = shape;

				}

			} else if (type.equals("圆角矩形")) {
				shape = new ShapeRoundRect(Math.min(x1, x2), Math.min(y1, y2),
						Math.abs(x1 - x2), Math.abs(y1 - y2), color,
						new BasicStroke(), 30, 30);
				shape.draw((Graphics2D) g);

				if (number < shapeArray.length) {
					// 将图形对象存入到数组中
					shapeArray[number++] = shape;
				}
			} else if (type.equals("填充圆")) {
				shape = new ShapeCircle(x1, y1, x2, y2, color, s);
				shape.draw((Graphics2D) g);
				if (number < shapeArray.length) {
					// 将图形对象存入到数组中
					shapeArray[number++] = shape;
				}
			} else if (type.equals("五角星")) {
				shape = new ShapeStar(x1, y1, x2, y2, color, s);
				shape.draw((Graphics2D) g);
				System.out.println(">>>>>>>>>"+x1+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+y1);
				if (number < shapeArray.length) {
					// 将图形对象存入到数组中
					shapeArray[number++] = shape;
					
				}

			}
		}
	

	/**
	 * 当鼠标在窗体上按下按键并且拖动时执行此方法
	 */
	public void mouseDragged(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		Graphics2D g2d = (Graphics2D) g;
		if (type.equals("铅笔")) {

			// 根据数据来实例化图形对象
			shape = new ShapeLine(x1, y1, x2, y2, color, new BasicStroke(1));
			// 调用图形的绘图方法
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// 将图形对象存入到数组中
				shapeArray[number++] = shape;
			}
			x1 = x2;
			y1 = y2;
		} else if (type.equals("刷子")) {

			// 根据数据来实例化图形对象
			shape = new ShapeLine(x1, y1, x2, y2, color, new BasicStroke(10));
			// 调用图形的绘图方法
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// 将图形对象存入到数组中
				shapeArray[number++] = shape;
			}
			x1 = x2;
			y1 = y2;
		} else if (type.equals("喷枪")) {
			g2d.setColor(color);
			shape = new ShapeGum(x1, y1, x2, y2, color, s);
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// 将图形对象存入到数组中
				shapeArray[number++] = shape;
			}
			x1 = x2;
			y1 = y2;
		} else if (type.equals("橡皮")) {
			shape = new ShapeEraser(x1, y1, x2, y2, color, s);
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// 将图形对象存入到数组中
				shapeArray[number++] = shape;
			}

			x1 = x2;
			y1 = y2;
		} else if (type.equals("图片")) {
			shape = new ShapeImage(color, x1, y1, x2, y2);
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// 将图形对象存入到数组中
				shapeArray[number++] = shape;
			}
		} else if (type.equals("填充矩形")) {
			shape = new ShapeFillRect(Math.min(x1, x2), Math.min(y1, y2),
					Math.abs(x1 - x2), Math.abs(y1 - y2), color,
					new BasicStroke(1));
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// 将图形对象存入到数组中
				shapeArray[number++] = shape;
			}
		} else if (type.equals("文字")) {
			shape = new ShapeString(x1, y1, x2, y2, color, s);
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// 将图形对象存入到数组中
				shapeArray[number++] = shape;
			}
		} else if (type.equals("3d矩形")) {// 7.3d矩形
			shape = new Shape3DRect(x1, y1, x2, y2, color, s);
			shape.draw((Graphics2D) g);

			if (number < shapeArray.length) {
				// 将图形对象存入到数组中
				shapeArray[number++] = shape;
			}
		}

	}

	/**
	 * 当鼠标在窗体上移动鼠标时执行此方法
	 */
	public void mouseMoved(MouseEvent e) {

	}

	/**
	 * 当你在事件源对象上发生鼠标点击(按下和释放的动作必须在同一个位置上)动作时执行此方法
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * 当你的鼠标光标进入事件源对象上时执行此方法
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * 当你的鼠标光标离开事件源对象上时执行此方法
	 */
	public void mouseExited(MouseEvent e) {
	}
}