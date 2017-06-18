//Michael Cho
//CSC 236-64
//Lab 6.2

public class HeapDemo
{
	public static void main(String [] args)
	{
		//enqueue 1-10 in order
		HeapPriorityQueue heap1=new HeapPriorityQueue();
		heap1.enqueue(1);
		heap1.enqueue(2);
		heap1.enqueue(3);
		heap1.enqueue(4);
		heap1.enqueue(5);
		heap1.enqueue(6);
		heap1.enqueue(7);
		heap1.enqueue(8);
		heap1.enqueue(9);
		heap1.enqueue(10);

		//3 dequeues
		System.out.println("Heap after adding 1-10 \n" + heap1);
		heap1.dequeue();
		System.out.println("Heap after first dequeue \n" + heap1);
		heap1.dequeue();
		System.out.println("Heap after second dequeue \n" +heap1);
		heap1.dequeue();
		System.out.println("Heap after third dequeue \n" + heap1);
	}
}