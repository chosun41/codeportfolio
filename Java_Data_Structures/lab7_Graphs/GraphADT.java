//Michael Cho
//CSC 236-64
//Lab 7.1, 7.2

public interface GraphADT
{
	public boolean isEmpty();
	//Method to determine if the graph is empty
	//Postcondition: Returns true if the graph is empty;
	//otherwise, returns false.

	public void createGraph();
	//Method to create the graph.
	//Postcondition: The graph is created using the
	//adjacency list representation.

	public void clearGraph();
	//Method to clear the graph.
	//Postcondition: Each entry of the array graph is set
	//to null and gSize=0;

	public void printGraph();
	//Method to print the graph.
	//Postcondition: For each vertext, the vertex and the
	//vertices adjacent to the vertex are output.

	public String convert(int vertex);
	//Method to convert number vertex to String city

	public void depthFirstTraversal(int vertex);
	//Method to perform a depth first traversal from vertex
	//Postcondition: The vertices of the graph in the depth
	//first traversal order are output.

	public void breadthFirstTraversal(int vertex);
	//Method to perform a breadth first traversal from vertex
	//Postcondition: The vertices of the graph in the breadth
	//first traversal are output.
}