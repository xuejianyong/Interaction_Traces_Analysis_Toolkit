package swing_turtorial;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class JFrameDemo extends JFrame{

	public JFrameDemo() {
		setTitle("the first java gui programme");
		setSize(400,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel  jl = new JLabel("the JFrame window by using swing");
		Container c = getContentPane();
		c.add(jl);
		setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new JFrameDemo();

	}

}
