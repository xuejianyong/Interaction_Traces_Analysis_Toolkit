package swing1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class test_Layout_Flowlayout {

	private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;

    public static void main(String[] args) {
    	test_Layout_Flowlayout swingLayoutDemo = new test_Layout_Flowlayout();
        swingLayoutDemo.showFlowLayoutDemo();
    }
    
    public test_Layout_Flowlayout() {
        prepareGUI();
    }
    
    private void prepareGUI(){
        mainFrame = new JFrame("Java SWING FlowLayout example)");
        mainFrame.setSize(600,600);
        mainFrame.setLayout(new GridLayout(3, 1));

        headerLabel = new JLabel("",JLabel.CENTER );
        //headerLabel.setBackground(Color.white);
        statusLabel = new JLabel("",JLabel.CENTER);        
        statusLabel.setSize(350,100);
        //statusLabel.setBackground(Color.gray);

        mainFrame.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent windowEvent){
              System.exit(0);
           }        
        });
        
        
        controlPanel = new JPanel();
        controlPanel.setSize(300,300);
        controlPanel.setBackground(Color.green);
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);  
     }

      private void showFlowLayoutDemo() {
          headerLabel.setText("Layout in action: FlowLayout");
          statusLabel.setText("status: liable");

          JPanel panel = new JPanel();
          panel.setBackground(Color.blue);
          panel.setSize(200, 200);
          FlowLayout layout = new FlowLayout();
          layout.setHgap(40);
          layout.setVgap(40);
          
          GridLayout glayout = new GridLayout(0,3);
          glayout.setHgap(20);
          glayout.setVgap(20);
          
          
          
          

          panel.setLayout(glayout);
          panel.add(new JButton("按钮-A"));
          panel.add(new JButton("按钮-B"));
          panel.add(new JButton("按钮-C"));
          panel.add(new JButton("按钮-D"));
          panel.add(new JButton("按钮-E"));
          controlPanel.add(panel);
          mainFrame.setVisible(true);
      }
      
	
}
