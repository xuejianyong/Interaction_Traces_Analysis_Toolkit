package swing1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class test_Layout_GroupLayout {
	private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JLabel statusLabel;
	   private JPanel controlPanel;
	   private JLabel msglabel;

	   public test_Layout_GroupLayout(){
	      prepareGUI();
	   }
	   public static void main(String[] args){
		   test_Layout_GroupLayout swingLayoutDemo = new test_Layout_GroupLayout();  
	      swingLayoutDemo.showGroupLayoutDemo();       
	   }
	   private void prepareGUI(){
	      mainFrame = new JFrame("Java SWING GroupLayoutDemo(yiibai.com)");
	      mainFrame.setSize(400,400);
	      mainFrame.setLayout(new GridLayout(3, 1));
	      mainFrame.getContentPane().setBackground(Color.white);
	      
	      headerLabel = new JLabel("",JLabel.CENTER );
	      statusLabel = new JLabel("States",JLabel.CENTER);        
	      statusLabel.setSize(350,100);

	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });
	      
	      controlPanel = new JPanel();
	      controlPanel.setBackground(Color.green);
	      controlPanel.setLayout(new FlowLayout());

	      mainFrame.add(headerLabel);
	      mainFrame.add(controlPanel);
	      mainFrame.add(statusLabel);
	      mainFrame.setVisible(true);
	   }
	   
	   
	   private void showGroupLayoutDemo(){
	      headerLabel.setText("Layout in action: GroupLayout"); 
	      statusLabel.setText("The current status");
	      
	      JPanel panel = new JPanel();
	      panel.setBackground(Color.darkGray);
	      panel.setSize(200,200);
	      GroupLayout layout = new GroupLayout(panel);
	      layout.setAutoCreateGaps(true);
	      layout.setAutoCreateContainerGaps(true);

	      JButton btn1 = new JButton("Button - A");
	      JButton btn2 = new JButton("Button - B");
	      JButton btn3 = new JButton("Button - C");

	      layout.setHorizontalGroup(layout.createSequentialGroup()
	         .addComponent(btn1)
	         .addGroup(layout.createSequentialGroup()
	         .addGroup(layout.createParallelGroup(
	         GroupLayout.Alignment.LEADING)
	         .addComponent(btn2)
	         .addComponent(btn3))));

	      layout.setVerticalGroup(layout.createSequentialGroup()
	         .addComponent(btn1)
	         .addComponent(btn2)
	         .addComponent(btn3));

	      panel.setLayout(layout);        
	      controlPanel.add(panel);
	      mainFrame.setVisible(true);  
	   }
}
