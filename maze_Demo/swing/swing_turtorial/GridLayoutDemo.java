 package swing_turtorial;
 import javax.swing.JButton;
 import javax.swing.JFrame;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.JTextField;
 import java.awt.*;
 
public class GridLayoutDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("grid layout caculator");
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(4,4,5,5));
		panel.add(new JButton("7"));
		panel.add(new JButton("8"));
		panel.add(new JButton("9"));
		panel.add(new JButton("/"));
		panel.add(new JButton("4"));
		panel.add(new JButton("5"));
		panel.add(new JButton("6"));
		panel.add(new JButton("*"));
		panel.add(new JButton("1"));
		panel.add(new JButton("2"));
		panel.add(new JButton("3"));
		panel.add(new JButton("-"));
		panel.add(new JButton("0"));
		panel.add(new JButton("."));
		panel.add(new JButton("="));
		panel.add(new JButton("+"));
		frame.add(panel);
		frame.setBounds(300,200,200,150);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	}

}
