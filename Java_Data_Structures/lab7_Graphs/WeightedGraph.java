//Michael Cho
//CSC 236-64
//Lab 7.2

import java.io.*;
import java.util.*;

public class WeightedGraph extends Graphs
{
	protected double[][]weights; //weight matrix
	protected double[]smallestWeight; //smallest weight from source to vertices

	//default constructor
	public WeightedGraph()
	{
		super();
		weights=new double[maxSize][maxSize];
		smallestWeight=new double[maxSize];
	}

	//overloaded constructor
	public WeightedGraph(int size)
	{
		super(size);
		weights=new double[maxSize][maxSize];
		smallestWeight=new double[maxSize];
	}

	//create weighted graph
	public void createWeightedGraph()
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

		//gSize=infile.nextInt();//get number of vertices

        String s = infile.nextLine();
        gSize = Integer.parseInt(s); //get number of vertices

		//every line will have source, target, weight| source, target, weight
		//break down into tokens and read into the weights matrix
        while (infile.hasNext())
        {
            s = infile.nextLine();
            String[] triplet = s.split("[\\|]");
            for (int i = 0; i < triplet.length; i++)
            {
                String [] tokens= triplet[i].split("[,]");
                int source=Integer.parseInt(tokens[0]);
                int target=Integer.parseInt(tokens[1]);
                double weight=Double.parseDouble(tokens[2]);
                weights[source][target]=weight;
            }
	   }

		//since automatically initialized to 0 where value wasn't read in
		//from scanner, need to switch these to inifinity since there is no
		//direct edge between vertices where weight entries are 0
		//otherwise shortest path algorithm will fail when doing comparisons
		for(int i=0; i<weights.length; i++)
		{
			for(int j=0; j<weights.length; j++)
			{
				if(weights[i][j]==0)
					weights[i][j]=Double.POSITIVE_INFINITY;
			}
		}
	}

	//shortest path algorithm
	public void shortestPath(int vertex)
	{
		for(int i=0; i<gSize; i++)
			smallestWeight[i]=weights[vertex][i];

		boolean[] weightFound=new boolean[maxSize];

		for(int i=0; i<gSize; i++)
			weightFound[i]=false;

		weightFound[vertex]=true;
		smallestWeight[vertex]=0;

		for(int i=0; i<gSize-1; i++)
		{
			double minWeight=Integer.MAX_VALUE;
			int v=0;

			for(int j=0; j<gSize; j++)
				if(!weightFound[j])
					if(smallestWeight[j]<minWeight)
					{
						v=j;
						minWeight=smallestWeight[v];
					}

			weightFound[v]=true;

			for(int j=0; j<gSize; j++)
				if(!weightFound[j])
					if(minWeight+weights[v][j]<smallestWeight[j])
						smallestWeight[j]=minWeight + weights[v][j];
		}
	}

	//print shortest weight from source to other vertices in the graph
	public void printShortestDistance(int vertex)
	{
		System.out.println("Source Vertex: " + vertex);
		System.out.println("Shortest Distance from Source to each Vertext");
		System.out.println("Vertex Shortest_Distance");

		for(int j=0; j<gSize; j++)
		{
			System.out.print(" " + j + "\t\t");
			System.out.printf("%.2f \n", smallestWeight[j]);
		}

		System.out.println();
	}

}