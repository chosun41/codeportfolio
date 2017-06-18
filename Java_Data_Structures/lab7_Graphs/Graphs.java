//Michael Cho
//CSC 236-64
//Lab 7.1, 7.2

import java.io.*;
import java.util.*;

public class Graphs implements GraphADT
{
	protected int maxSize; //maximum number of vertices
	protected int gSize; //current number of vertices
	protected UnorderedLinkedList [] graph; //array of references to create adjacency lists

	//default constructor
	public Graphs()
	{
		maxSize=100;
		gSize=0;
		graph=new UnorderedLinkedList[maxSize];

		//adjacency list
		for(int i=0; i<maxSize; i++)
			graph[i]=new UnorderedLinkedList<Integer>();
	}

	//overloaded constructor
	public Graphs(int size)
	{
		maxSize=size;
		gSize=0;
		graph=new UnorderedLinkedList[maxSize];

		//adjacency list
		for(int i=0; i<maxSize; i++)
			graph[i]=new UnorderedLinkedList<Integer>();
	}

	//checks if graph empty
	public boolean isEmpty()
	{
		return (gSize==0); //number of vertices is 0
	}

	//creates a graph
	public void createGraph()
	{
		Scanner console=new Scanner(System.in);

		String fileName;

		if(gSize!=0)
			clearGraph();

		Scanner infile=null;

		try
		{
			System.out.print("Enter input file name: ");
			fileName=console.nextLine();
			System.out.println();
			infile=new Scanner(new FileReader(fileName));
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println(fnfe.toString());
			System.exit(0);
		}

		gSize=infile.nextInt();//get number of vertices

		for(int index=0; index<gSize; index++)
		{
			int vertex=infile.nextInt();
			int adjacentVertex = infile.nextInt();

			while(adjacentVertex!=-999)
			{
				graph[vertex].addLast(adjacentVertex);
				adjacentVertex=infile.nextInt();
			}
		}
	}

	//makes graph empty
	public void clearGraph()
	{
		for(int index=0; index<gSize; index++)
			graph[index]=null;

		gSize=0;
	}

	//prints graph
	public void printGraph()
	{
		for(int index=0; index<gSize; index++)
		{
			System.out.print(index + " " + graph[index]);
			System.out.println();
		}

		System.out.println();
	}

	//get size of graph
	public int getSize()
	{
		return gSize;
	}

	//decided to use a switch here for displaying the vertices, because
	//if I used String vertices with actual city names then I couldn't
	//easily match up to the visited array index which is a number.
	//i would have to do either one of the following as a workaround:
	//1. key map values to relate cities to number vertex either in
	//text file or separate class
	//2. switch statement that relates number vertex to cities
	public String convert(int vertex)
	{
		String city="";
        switch (vertex)
        {
            case 0:  city = "Sydney";
                     break;
            case 1:  city = "Canberra";
                     break;
            case 2:  city = "Brisbane";
                     break;
            case 3:  city = "Melbourne";
                     break;
            case 4:  city = "Hobart";
                     break;
            case 5:  city = "Adelaide";
                     break;
            case 6:  city = "Perth";
                     break;
            case 7:  city = "Black Stump";
                     break;
            case 8:  city = "Darwin";
					 break;
		}
		return city;
	}

	//breadth first traversal
	public void breadthFirstTraversal(int vertex)
	{
		boolean[] visited;

		visited=new boolean[getSize()];

		for(int i=0; i<getSize(); i++)
			visited[i]=false;

		ArrayQueue<Integer> q=new ArrayQueue<Integer>(getSize());

		visited[vertex]=true;
		q.enqueue(vertex);

		while(!q.isemptyqueue())
		{
			int v=q.dequeue();
			System.out.print(convert(v) + " ");

			Node current=graph[v].getFirstNode();

			while (current!=null)
			{

				int w= (Integer) current.getValue();

				if (visited[w]==false)
				{
					visited[w]=true;
					q.enqueue(w);
				}
				current=current.getNext();
			}
		}
		System.out.println("\n");

	}

	//depth first traversal
	//not using a stack here, but using a public method that calls on
	//private recursive method dfs
	//the trickiest part of this method is the boolean array for visited vertices
	//b/c of recursion, this array would have to be either recreated and passed
	//into recursive method, exist in a separate depthfirstsearch class, or
	//exist in the graph class
	public void depthFirstTraversal(int vertex)
	{
		boolean[] visited;

		visited=new boolean[getSize()];

		for(int i=0; i<getSize(); i++)
			visited[i]=false;

		visited[vertex]=true;

		System.out.print(convert(vertex) + " ");

		dfs(vertex, visited);

		System.out.println("\n");

	}

	private void dfs(int vertex, boolean [] invisited)
	{
		boolean [] dfsvisited = invisited;

		Node current=graph[vertex].getFirstNode();

		while(current!=null)
		{
			int w = (Integer) current.getValue();

			if(dfsvisited[w]==false)
			{
				dfsvisited[w]=true;
				System.out.print(convert(w) + " ");
				dfs(w, dfsvisited);
			}
			current=current.getNext();
		}
	}
}