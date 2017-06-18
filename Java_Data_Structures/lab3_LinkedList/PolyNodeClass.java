//Michael Cho
//CSC 236-64
//Lab 3.1

public class PolyNodeClass {

	private int coef, exp;
	private PolyNodeClass next;

	//default constructor
	public PolyNodeClass(){
	coef=0;
	exp=0;
	next=null;
	}

	//overloaded constructor
	public PolyNodeClass(int incoef, int inexp, PolyNodeClass innext){
	this.coef=incoef;
	this.exp=inexp;
	this.next=innext;
	}

	//copy constructor
	public PolyNodeClass(PolyNodeClass a){
	coef=a.coef;
	exp=a.exp;
	next=a.next;
	}

	//set coefficient to 1st element of node
	public void setCoefficient(int newcoef){
	coef=newcoef;
	}

	//set exponent to 2nd element of node
	public void setExponent(int newexp){
	exp=newexp;
	}

	//set next to next polynomial node
	public void setNext(PolyNodeClass newnext){
	next=newnext;
	}

	//return 1st elemenent of node
	public int getCoefficient(){
	return coef;
	}

	//return 2nd element of node
	public int getExponent(){
	return exp;
	}

	//return link to next node
	public PolyNodeClass getNext(){
	return next;
	}

}













