package dl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class TestClass {
	public static void main(String[] args) {
		Interaction in1 = new Interaction("e01");
		Interaction in2 = new Interaction("e00");
		Interaction in3 = new Interaction("e11");
		
		Node n1 = new Node(in1);
		n1.setLeftNode(null);
		n1.setRightNode(null);
		
		if(n1.getLeftNode() == null) {
			System.out.println("the left node is null");
		}
		
	}
}
