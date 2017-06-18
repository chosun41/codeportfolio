//Michael Cho
//CSC 236-64
//Lab 6.1

public class TreeDemo
{
	//creates two trees, prints number of nodes with only 1 child, swaps subtrees
	public static void main(String [] args)
	{
		//first tree with overloaded variable length parameter
		BinarySearchTree tree1=new BinarySearchTree(1,2,3,4,5,6);
		System.out.println("Tree 1 prior to swapping subtrees" + "\n");
		makeTree(tree1);
		System.out.println("Number of nodes with one child is: " + tree1.singleParent() + "\n");
		printTree(tree1);
		tree1.swapSubtrees();
		System.out.println("Tree 1 after swapping subtrees" + "\n");
		System.out.println("Number of nodes with one child is: " + tree1.singleParent() + "\n");
		printTree(tree1);

		//second tree with default constructor
		BinarySearchTree tree2=new BinarySearchTree();
		tree2.insert2(14);
		tree2.insert2(4);
		tree2.insert2(15);
		tree2.insert2(3);
		tree2.insert2(9);
		tree2.insert2(18);
		tree2.insert2(7);
		tree2.insert2(16);
		tree2.insert2(20);
		tree2.insert2(5);
		tree2.insert2(17);
		System.out.println("Tree 2 prior to swapping subtrees" + "\n");
		System.out.println("Number of nodes with one child is: " + tree2.singleParent() + "\n");
		printTree(tree2);
		System.out.println("Tree 2 after swapping subtrees" + "\n");
		tree2.swapSubtrees();
		System.out.println("Number of nodes with one child is: " + tree2.singleParent() + "\n");
		printTree(tree2);

	}

	//used exclusively with the complete tree
	public static void makeTree(BinarySearchTree inTree)
	{
		//set first array element to root and then call recuseInsert
		//using the root, tree, starting index, length of array
		inTree.setRoot(inTree.getArray()[0]);
		TreeNode t=inTree.getRoot();
		recurseInsert(t, inTree, 0, inTree.getArray().length);
	}

	public static TreeNode recurseInsert(TreeNode t, BinarySearchTree inTree, int index, int arraySize)
	{
		//index for left and right children
		int leftIndex = 2*index+1;
		int rightIndex = 2*index+2;

		//need to place this, so we won't get indexoutofbounds exception
		if(leftIndex>arraySize || rightIndex>arraySize)
			return t;

		//base case
		if(t==null)
			return new TreeNode(inTree.getArray()[index]);

		//attach left and right children from array
		if(t.getLeft()==null && t.getRight()==null)
		{
			if(leftIndex<arraySize)
				t.setLeft(inTree.getArray()[leftIndex]);
			if(rightIndex<arraySize)
				t.setRight(inTree.getArray()[rightIndex]);
		}

		//recursively attach children to existing tree
		recurseInsert(t.getLeft(),inTree, leftIndex, arraySize);
		recurseInsert(t.getRight(),inTree, rightIndex, arraySize);

		return t;
	}

	//print tree method does pre, in, and post traversal
	public static void printTree(BinarySearchTree inTree)
	{
		System.out.println(inTree);
	}
}