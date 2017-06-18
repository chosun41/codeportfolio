//Michael Cho
//CSC 236-64
//Lab 6.1

public abstract class BinaryTree implements BT
{
	private TreeNode root;

	//default constructor
	public BinaryTree()
	{
		root=null;
	}

	//access root
	public TreeNode getRoot()
	{
		return root;
	}

	//mutate roote
	public void setRoot(TreeNode theNewNode)
	{
		root=theNewNode;
	}

	//check if empty
	public boolean isEmpty()
	{
		return root==null;
	}

	//preorder
	public String preorder()
	{
		String s="Preorder traversal is: " + doPreorder(root) + "\n";
		return s;
	}

	private String doPreorder(TreeNode t)
	{
		String s="";

		if(t!=null)
		{
			s+=t.getValue() + " ";
			s+=doPreorder(t.getLeft());
			s+=doPreorder(t.getRight());
		}
		return s;
	}

	//inorder
	public String inorder()
	{
		String s="Inorder traversal is: " + doInorder(root) + "\n";
		return s;
	}

	private String doInorder(TreeNode t)
	{
		String s="";

		if(t!=null)
		{
			s+=doInorder(t.getLeft());
			s+=t.getValue() + " ";
			s+=doInorder(t.getRight());
		}
		return s;
	}

	//postorder
	public String postorder()
	{
		String s="Postorder traversal is: " + doPostorder(root) + "\n";
		return s;
	}

	private String doPostorder(TreeNode t)
	{
		String s="";

		if(t!=null)
		{
			s+=doPostorder(t.getLeft());
			s+=doPostorder(t.getRight());
			s+=t.getValue() + " ";
		}

		return s;
	}

	//to string method that combines pre, in, and post order
	public String toString()
	{
		String s="";
		s+=preorder()+inorder()+postorder();
		return s;

	}

	//count number of single children nodes
	public int singleParent()
	{
		return checkNode(root);
	}

	private int checkNode(TreeNode t)
	{
		int count=0;

		if(t!=null)
		{
			//only right child exists
			if(t.getLeft()==null && t.getRight()!=null)
			{
				count++;
			}
			//only left child exists
			else if(t.getRight()==null && t.getLeft()!=null)
			{
				count++;
			}

			//recursively add to count variable
			count+=checkNode(t.getLeft());
			count+=checkNode(t.getRight());
		}
		return count;
	}

	//swap subtrees
	public void swapSubtrees()
	{
		swapTrees(root);
	}

	private void swapTrees(TreeNode t)
	{
		if(t!=null)
		{
			swapTrees(t.getLeft());
			swapTrees(t.getRight());
			TreeNode temp=t.getRight();
			t.setRight(t.getLeft());
			t.setLeft(temp);
		}
	}

	//insert1 for complete tree and insert2 for bst defined in BinarySearchTree
	public abstract void insert1(TreeNode t);
	public abstract void insert2(Comparable item);

}