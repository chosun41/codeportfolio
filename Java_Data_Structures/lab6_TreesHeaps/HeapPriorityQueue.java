//Michael Cho
//CSC 236-64
//Lab 6.2

public class HeapPriorityQueue<T> implements HeapPQ<T>
{
	private int maxqueuesize, count, queuefront, queuerear;
	private T[] list;

	//default constructor
	public HeapPriorityQueue()
	{
		maxqueuesize=250;
		queuefront=0;
		queuerear=maxqueuesize-1;
		count=0;
		list=(T[]) new Object[maxqueuesize];
	}

	//overloaded constructor
	public HeapPriorityQueue(int queuesize)
	{
		if(queuesize<=0)
		{
			System.err.println("The size of the array to implement the queue must be positive.");
			System.err.println("Creating an array of the size 250.");
			maxqueuesize = 250;
		}
		else
			maxqueuesize=queuesize;

		queuefront=0;
		queuerear=maxqueuesize-1;
		count=0;
		list=(T[]) new Object[maxqueuesize];
	}

	//initialize queue
	public void initializequeue()
	{
		for(int i=queuefront; i<queuerear; i=(i+1)% maxqueuesize)
		list[i]=null;

		queuefront=0;
		queuerear=maxqueuesize-1;
		count=0;
	}

	//count length of queue
	public double count()
	{
		return count ;
	}

	//check if queue is empty
	public boolean isEmpty()
	{
		return (count==0);
	}

	//check if queue is full
	public boolean isFull()
	{
		return(count==maxqueuesize);
	}

	//check beginning of queue
	public T front() throws HeapUnderflowException
	{
		if(isEmpty())
			throw new HeapUnderflowException();

		return (T) list[queuefront];
	}

	//check end of queue
	public T back() throws HeapUnderflowException
	{
		if(isEmpty())
			throw new HeapUnderflowException();

		return (T) list[queuerear];
	}

	//add element to queue at end
	public void enqueue(T queueelement) throws HeapOverflowException
	{
		if(isFull())
			throw new HeapOverflowException();

		queuerear=(queuerear+1)%maxqueuesize;
		count++;
		list[queuerear]=queueelement;

		reheapifyUpward(queuerear);
	}

	//reheapify up (recursive with array index)
	//won't produce a null pointer exception b/c we are going from leaves on up
	private void reheapifyUpward(int index)
	{
		//explicit integer cast to perform numeric comparisons and recursive swaps
		//recursion stops when we hit the base case where parent >= child priority
		if((Integer)list[index]>(Integer)list[(index-1)/2])
		{
			T temp=list[(index-1)/2];
			list[(index-1)/2]=list[index];
			list[index]=temp;
			reheapifyUpward((index-1)/2);
		}
	}

	//delete element at front of queue
	//doesn't move queuefront up one index value, but replaces front with back and deletes at end
	public void dequeue() throws HeapUnderflowException
	{
		if(isEmpty())
			throw new HeapUnderflowException();

		count--;
		list[queuefront]=list[queuerear];

		list[queuerear]=null;

		queuerear=(queuerear-1)%maxqueuesize;

		reheapifyDownward(queuefront);
	}

	//reheapify down (recursive with array index)
	//different cases to avoid null pointer exception
	private void reheapifyDownward(int index)
	{
		//both children not null
		if(list[2*index+1]!=null && list[2*index+2]!=null)
		{
			T temp1=list[2*index+1];
			T temp2=list[2*index+2];


			//swap with the child with the larger priority
			if((Integer) temp1 > (Integer) temp2)
			{
				list[2*index+1]=list[index];
				list[index]=temp1;
				reheapifyDownward(2*index+1);
			}
			else
			{
				list[2*index+2]=list[index];
				list[index]=temp2;
				reheapifyDownward(2*index+2);
			}
		}

		//left child not null while right child null
		else if(list[2*index+1]!=null && list[2*index+2]==null)
		{
			T temp=list[2*index+1];

			//swap if left larger than parent
			if((Integer) temp > (Integer) list[index])
			{
				list[2*index+1]=list[index];
				list[index]=temp;
			}
		}

		//right child not null while left child null
		else if(list[2*index+1]==null && list[2*index+2]!=null)
		{
			T temp=list[2*index+2];

			//swap if right larger than parent
			if((Integer) temp > (Integer) list[index])
			{
				list[2*index+2]=list[index];
				list[index]=temp;
			}
		}

	}

	//preorder
	public String preorder()
	{
		String s="Preorder traversal is: " + doPreorder(0) + "\n";
		return s;
	}

	private String doPreorder(int index)
	{
		String s="";

		if(list[index]!=null)
		{
			s+=list[index] + " ";
			s+=doPreorder(2*index+1);
			s+=doPreorder(2*index+2);
		}
		return s;
	}

	//inorder
	public String inorder()
	{
		String s="Inorder traversal is: " + doInorder(0) + "\n";
		return s;
	}

	private String doInorder(int index)
	{
		String s="";

		if(list[index]!=null)
		{
			s+=doInorder(2*index+1);
			s+=list[index] + " ";
			s+=doInorder(2*index+2);
		}
		return s;
	}

	//postorder
	public String postorder()
	{
		String s="Postorder traversal is: " + doPostorder(0) + "\n";
		return s;
	}

	private String doPostorder(int index)
	{
		String s="";

		if(list[index]!=null)
		{
			s+=doPostorder(2*index+1);
			s+=doPostorder(2*index+2);
			s+=list[index] + " ";
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

}