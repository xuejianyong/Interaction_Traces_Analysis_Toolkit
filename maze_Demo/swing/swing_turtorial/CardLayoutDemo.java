package swing_turtorial;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;

public class CardLayoutDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("the card layout demo");
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel cards = new JPanel(new CardLayout());
		p1.add(new JButton("login"));
		p1.add(new JButton("register"));
		p1.add(new JButton("find back the password"));
		p2.add(new JTextField("user name text",20));
		p2.add(new JTextField("password",20));
		p2.add(new JTextField("user name text",20));
		cards.add(p1,"card1");
		cards.add(p2,"card2");
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, "card2");
		frame.add(cards);
		frame.setBounds(300,200,400,200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		

	}

}
