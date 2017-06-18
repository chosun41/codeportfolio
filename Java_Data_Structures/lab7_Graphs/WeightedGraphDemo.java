//Michael Cho
//CSC 236-64
//Lab 7.2

public class WeightedGraphDemo
{
	public static void main(String[] args)
	{
		WeightedGraph wg1=new WeightedGraph();
		wg1.createWeightedGraph();
		wg1.shortestPath(0);
		wg1.printShortestDistance(0);
	}
}

//txt file = C:\Users\Michael\Desktop\Shortestpath.txt