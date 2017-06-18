//Michael Cho
//CSC 236-64
//Lab 3.1

public class PolynomialDataStrucClass implements PolynomialADT{

	private PolyNodeClass firstnode;

	//default constructor
	public PolynomialDataStrucClass(){
	firstnode=null;
	}

	//overloaded constructor
	public PolynomialDataStrucClass(PolyNodeClass node){
	firstnode=node;
	}

	//copy constructor
	public PolynomialDataStrucClass(PolynomialDataStrucClass a){
	firstnode=a.firstnode;
	}

	//tests whether node is empty
	public boolean isEmpty(){
	return firstnode == null;
	}

	//set first node to some value
	public void setFirstNode(PolyNodeClass node){
	firstnode=node;
	}

	//return first node
	public PolyNodeClass getFirstNode(){
	return firstnode;
	}

	//polynode is created and set to beginning of polynomial
	public void addPolyNodeFirst(int incoef, int inexp){
		if (isEmpty())
			firstnode=new PolyNodeClass(incoef, inexp, null);
		else
			firstnode=new PolyNodeClass(incoef, inexp, firstnode);
	}

	//polynode is created and set to end of polynomial
	public void addPolyNodeLast(int incoef, int inexp){
		if(isEmpty())
			firstnode=new PolyNodeClass(incoef, inexp, null);
		else
			{
				PolyNodeClass current=firstnode;
				while (current.getNext() != null)
					current=current.getNext();

				current.setNext(new PolyNodeClass(incoef, inexp, null));
			}
	}

	//take in polynode and add it at end, utilizes copy constructor here
	//method used by addPolynomials method later
	public void addPolyNode(PolyNodeClass a){
		if(isEmpty())
			firstnode=new PolyNodeClass(a);
		else
			{
				PolyNodeClass current=firstnode;
				while (current.getNext() != null)
					current=current.getNext();

				current.setNext(new PolyNodeClass(a));
			}
	}

	//takes in two parameters: the 2nd list you are adding to 1st list and a boolean to test
	//whether both lists are descending or ascending in terms of order of exponents
	//both lists have to be instantiated. supposed there are lists a and b
	// a.addPolynomials(b, true) would be equivalent to b.addPolynomials (a,true)
	public PolynomialDataStrucClass addPolynomials(PolynomialDataStrucClass list2, boolean descending){

		PolynomialDataStrucClass list1=this;
		//new list that contains results of add polyniaml
		PolynomialDataStrucClass sumlist= new PolynomialDataStrucClass();
		//x is 1st list 1st node
		PolyNodeClass x=list1.getFirstNode();
		//y is 2nd list 1st node
		PolyNodeClass y=list2.getFirstNode();

		//case for when both lists descending
		if(descending==true){

		//traverse both lists, reference changes for next are made in conditionals
		while(x!= null || y != null){

			//empty node to hold results to be added into sumlist
			PolyNodeClass z=null;

			//when 1st list runs out, extract 2nd list coefficient, exponent into Polynode z
			//and traverse to next in 2nd list
			if (x == null)
				{z = new PolyNodeClass(y.getCoefficient(), y.getExponent(), null);
				y = y.getNext();
				}

			//when 2nd list runs out, extract 1st list coefficient, exponent into Polynode z
			//and traverse to next in 1st list
			else if (y == null)
				{z = new PolyNodeClass(x.getCoefficient(), x.getExponent(), null);
				x = x.getNext();
				}

			//if 1st list exponent>2nd list exponent, extract 1st list coefficient, exponent
			//into Polynode z and traverse to next in 1st list
			else if (x.getExponent()>y.getExponent())
				{z = new PolyNodeClass(x.getCoefficient(), x.getExponent(), null);
				x = x.getNext();
				}

			//if 1st list exponent<2nd list exponent, extract 2nd list coefficent, exponent
			//into Polynode z and traverse to next in 2nd list
			else if (x.getExponent()<y.getExponent())
				{z = new PolyNodeClass(y.getCoefficient(),y.getExponent(), null);
				y = y.getNext();
				}

			//if 1st list exponent = 2nd list expoent, add coefficient together and
			//place both new coeffient and exponent into z
			//traverse to next in both list
			else
				{int sumcoef = x.getCoefficient() + y.getCoefficient();
				int sumexp = x.getExponent();
				z = new PolyNodeClass(sumcoef, sumexp, null);
				x = x.getNext();
				y = y.getNext();
				}

			//add z to sumlist with each iteration in while loop
			sumlist.addPolyNode(z);
		}
	}

		//case for when both lists ascending
		if(descending==false){

		//traverse both lists, reference changes for next are made in conditionals
		while(x!= null || y != null){

			//empty node to hold results to be added into sumlist
			PolyNodeClass z=null;

			//when 1st list runs out, extract 2nd list coefficient, exponent into Polynode z
			//and traverse to next in 2nd list
			if (x == null)
				{z = new PolyNodeClass(y.getCoefficient(), y.getExponent(), null);
				y = y.getNext();
				}

			//when 2nd list runs out, extract 1st list coefficient, exponent into Polynode z
			//and traverse to next in 1st list
			else if (y == null)
				{z = new PolyNodeClass(x.getCoefficient(), x.getExponent(), null);
				x = x.getNext();
				}

			//if 1st list exponent>2nd list exponent, extract 2nd list coefficient, exponent
			//into Polynode z and traverse to next in 2nd list
			else if (x.getExponent()>y.getExponent())
				{z = new PolyNodeClass(y.getCoefficient(), y.getExponent(), null);
				y = y.getNext();
				}

			//if 1st list exponent<2nd list exponent, extract 1st list coefficent, exponent
			//into Polynode z and traverse to next in 1st list
			else if (x.getExponent()<y.getExponent())
				{z = new PolyNodeClass(x.getCoefficient(),x.getExponent(), null);
				x = x.getNext();
				}

			//if 1st list exponent = 2nd list expoent, add coefficient together and
			//place both new coeffient and exponent into z
			//traverse to next in both list
			else
				{int sumcoef = x.getCoefficient() + y.getCoefficient();
				int sumexp = x.getExponent();
				z = new PolyNodeClass(sumcoef, sumexp, null);
				x = x.getNext();
				y = y.getNext();
				}

			//add z to sumlist with each iteration in while loop
			sumlist.addPolyNode(z);
		}
	}

			return sumlist;
	}

	//string return
	public String toString(){
		if (isEmpty())
			return "empty.";
		else
		{
			String s="";
			PolyNodeClass current=firstnode;

			//1st polynomial term treated differently than rest of terms as it
			//will not have a + or - sign in front of it and it takes care of special
			//case where only 1 polynomial term in each of the lists
			//exponent of 0 will not print the exponent, only the coefficient

			if(current.getExponent()==0)
				s = s + current.getCoefficient();
			else if(current.getExponent()==1)
				s = s +current.getCoefficient() + "x";
			else
				s = s + current.getCoefficient() + "x^" + current.getExponent();

			current=current.getNext();

			while(current!=null)
			{
				//if coefficient=0, polynomial term shouldn't even display
				// + for positive coefficent, - for negative coefficents
				//exponent of 0 will not print the exponent, only the coefficient
				//exponent of 1 will print x not x^1
				//coefficient of 1 will not display 1 before exponent

				if (current.getCoefficient()>0)
					{if(current.getCoefficient()==1)
						{if(current.getExponent()==0)
							s= s + " + 1";
						else if(current.getExponent()==1)
							s= s + " + x";
						else
							s= s + " + " + "x^" + current.getExponent();
						}
					else
						{if(current.getExponent()==0)
							s= s + " + " + current.getCoefficient();
						else if(current.getExponent()==1)
							s= s + " + " + current.getCoefficient() + "x";
						else
							s= s + " + " + current.getCoefficient() + "x^" + current.getExponent();
						}
					}
				else if (current.getCoefficient() < 0)
					{if(current.getCoefficient()==-1)
						{if(current.getExponent()==0)
							s= s + " - 1";
						else if(current.getExponent()==1)
							s= s + " - x";
						else
							s= s + " - " + "x^" + current.getExponent();
						}
					else
						{if(current.getExponent()==0)
							s= s + " - " + (-current.getCoefficient());
						else if(current.getExponent()==1)
							s= s + " - " + (-current.getCoefficient()) + "x";
						else
							s= s + " - " + (-current.getCoefficient())+ "x^" + current.getExponent();
						}
					}
				current = current.getNext();

			}
			return s;
		}
	}

}