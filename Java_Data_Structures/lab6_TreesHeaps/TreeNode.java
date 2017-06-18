//Michael Cho
//CSC 236-64
//Lab 6.1

public class TreeNode
{
	private Object value;
	private TreeNode left, right;

	//default constructor
	public TreeNode()
	{
		value=null;
		left=null;
		right=null;
	}

	//overloaded constructor
	public TreeNode(Object initValue)
	{
		value=initValue;
		left=null;
		right=null;
	}

	//overloaded constructor
	public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight)
	{
		value=initValue;
		left=initLeft;
		right=initRight;
	}

	//copy constructor
	public TreeNode(TreeNode a)
	{
		value=a.value;
		left=a.left;
		right=a.right;
	}

	//access value
	public Object getValue()
	{
		return value;
	}

	//access left node
	public TreeNode getLeft()
	{
		return left;
	}

	//access right node
	public TreeNode getRight()
	{
		return right;
	}

	//mutate value
	public void setValue(Object theNewValue)
	{
		value=theNewValue;
	}

	//mutate left
	public void setLeft(TreeNode theNewLeft)
	{
		left=theNewLeft;
	}

	//mutate right
	public void setRight(TreeNode theNewRight)
	{
		right=theNewRight;
	}

}