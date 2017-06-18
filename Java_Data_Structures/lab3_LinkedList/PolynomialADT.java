//Michael Cho
//CSC 236-64
//Lab 3.1

public interface PolynomialADT {

	//tests whether node is empty
	public boolean isEmpty();

	//set first node to some value
	public void setFirstNode(PolyNodeClass node);

	//return first node
	public PolyNodeClass getFirstNode();

	//polynode is created and set to beginning of polynomial
	public void addPolyNodeFirst(int incoef, int inexp);

	//polynode is created and set to end of polynomial
	public void addPolyNodeLast(int incoef, int inexp);

	//take in polynode and add it at end
	public void addPolyNode(PolyNodeClass a);

	//adds two polynodes together
	public PolynomialDataStrucClass addPolynomials(PolynomialDataStrucClass list2, boolean descending);

	//string return
	public String toString();

}