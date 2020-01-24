package swing_turtorial;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class BorderLayoutDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setSize(400,200);
		frame.setLayout(new BorderLayout());
		
		JButton button1 = new JButton("up");
		JButton button2 = new JButton("left");
		JButton button3 = new JButton("center");
		JButton button4 = new JButton("right");
		JButton button5 = new JButton("down");
		
		frame.add(button1,BorderLayout.NORTH);
		frame.add(button2,BorderLayout.WEST);
		frame.add(button3,BorderLayout.CENTER);
		frame.add(button4,BorderLayout.EAST);
		frame.add(button5,BorderLayout.SOUTH);
		
		frame.setBounds(100,300,600,300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
