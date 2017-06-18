//Michael Cho
//CSC 236-64
//Lab 7.1

public class GraphSearchDemo
{
	public static void main(String[] args)
	{
		Graphs g2 = new Graphs();
		g2.createGraph();
		System.out.println("Breadth First Traversal: ");
		g2.breadthFirstTraversal(0);
		System.out.println("Depth First Traversal: ");
		g2.depthFirstTraversal(0);
	}
}

//txt.file = C:\\Users\\Michael\\Desktop\\Australia.txt