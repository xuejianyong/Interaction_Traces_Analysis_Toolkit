package dl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import dl.DlServlet.Mood;

public class Test1 {
	public Map<String ,Integer> EXPERIENCES = new HashMap<String ,Integer>();
	public String testString;

	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}


	public Test1() {
		
		String test1 = "the test1";
		String test2 = "the test1";
		
		if(test1 == test2) {
			System.out.println("the test1 equals with the test2");
		}
		
		if(test1.equals(test2)) {
			System.out.println("the test1 equals with the test2");
		}
			
		
		
	}
	
	
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test1 tes1 = new Test1();
	}
}
