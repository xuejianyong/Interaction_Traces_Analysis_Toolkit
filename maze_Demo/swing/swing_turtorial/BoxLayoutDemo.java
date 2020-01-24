package swing_turtorial;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
public class BoxLayoutDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("box layout");
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createVerticalBox();
		frame.add(b1);
		b1.add(Box.createVerticalStrut(200));
		b1.add(new JButton("west"));
		

	}

}
