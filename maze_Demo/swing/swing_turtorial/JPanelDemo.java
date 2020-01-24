package swing_turtorial;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class JPanelDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame jf = new JFrame();
		jf.setBounds(300,300,400,200);
		JPanel jp = new JPanel();
		JLabel jl = new JLabel("this is the label put ont the JPanel");
		jp.setBackground(Color.green);
		jp.add(jl);
		jf.add(jp);
		jf.setVisible(true);

	}

}
