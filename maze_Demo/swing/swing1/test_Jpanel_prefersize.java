package swing1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class test_Jpanel_prefersize {
  public static void main(String args[]) {
    JFrame f = new JFrame("Label Demo");
    f.setLayout(new FlowLayout());
    f.setSize(200, 360);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    JLabel label= new JLabel("asdf");
    Border border = BorderFactory.createLineBorder(Color.BLACK);
    label.setBorder(border);
    label.setPreferredSize(new Dimension(200, 100));
    f.add(label);

    f.setVisible(true);
  }
}