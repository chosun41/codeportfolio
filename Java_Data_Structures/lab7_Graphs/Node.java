public class Node<T>
{
	private T value;
	private Node next;

	//overloaded constructor
	public Node(T initValue, Node<T> initNext)
	{
		value=initValue;
		next=initNext;
	}

	//get value
	public T getValue()
	{
		return value;
	}

	//get next node
	public Node getNext()
	{
		return next;
	}

	//set value
	public void setValue(T theNewValue)
	{
		value=theNewValue;
	}

	//set next node
	public void setNext(Node<T> theNewNext)
	{
		next=theNewNext;
	}

}