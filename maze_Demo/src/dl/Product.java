package dl;

import java.util.Date;  

public class Product implements Comparable<Product>  {//���ڱȽ�   
    private String name;   
    private Date date;   
    private int price;   
    
    public double proclivity;
    public int interactionResult; 
    
    
    public Product(String sname,int  sinteractionResult) {
    	this.name = sname;
		this.interactionResult = sinteractionResult;
	}
    
    
    
    public Double getProclivity() {
		return proclivity;
	}
	public void setProclivity(double proclivity) {
		this.proclivity = proclivity;
	}
	public int getPrice() {   
        return price;   
    }   
    public void setPrice(int price) {   
        this.price = price;   
    }   
    public String getName() {   
        return name;   
    }   
    public void setName(String name) {   
        this.name = name;   
    }   
    public Date getDate() {   
        return date;   
    }   
    public void setDate(Date date) {   
        this.date = date;   
    }   
    
    //�������ڱȽ�
    @Override//��дcompareTo(Object o)����   
    public int compareTo(Product o) {
       return o.getProclivity().compareTo(this.getProclivity());
       }



	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.getName() == ((Product)obj).getName();
	}
     
     
	
} 