//Michael Cho
//CSC 236-64
//Lab 6.1

public class BinarySearchTree extends BinaryTree
{
	//used exclusively for the complete tree not the bst
	private TreeNode [] treeArray;

	//default constructor
	public BinarySearchTree()
	{
		super();
	}

	//overloaded constructor (exclusively used by the complete tree
	public BinarySearchTree(int... values)
	{
		treeArray = new TreeNode[6];

		//insert Tree with int value (primitive int is autoboxed)
		//as TreeNode(value) actually takes Object parameter
		for(int value:values)
		{
			insert1(new TreeNode(value));
		}

	}

	//access the queue in demo
	public TreeNode[] getArray()
	{
		return treeArray;
	}

	//insert for complete tree into TreeNode array
	//breaks so it only adds at the end of existing values
	//recursive logic in main
	public void insert1(TreeNode t)
	{
		for(int i=0; i<getArray().length; i++)
		{
			if (treeArray[i]==null)
			{
				treeArray[i]= t;
				break;
			}
		}
	}


	//insert for binary search tree
	public void insert2(Comparable item)
	{
			setRoot(recurInsert(getRoot(),item));
	}

	//only the bst uses the recursive method with comparisons
	private TreeNode recurInsert(TreeNode t, Comparable item)
	{
		if(t==null)
			return new TreeNode(item);
		else if(item.compareTo(t.getValue())<0)
			t.setLeft(recurInsert(t.getLeft(),item));
		else
			t.setRight(recurInsert(t.getRight(),item));
		return t;
	}
}