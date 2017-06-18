//Michael Cho
//CSC 236-64
//Lab 6.1

public interface BT
{

	//mutate roote
	public void setRoot(TreeNode theNewNode);

	//check if empty
	public boolean isEmpty();

	//preorder
	public String preorder();

	//inorder
	public String inorder();

	//postorder
	public String postorder();

	//to string method
	public String toString();

	//counts # of single child nodes
	public int singleParent();

	//swap subtrees
	public void swapSubtrees();

	//insert node into complete tree
	public abstract void insert1(TreeNode t);

	//insert node into bst
	public abstract void insert2(Comparable item);

}