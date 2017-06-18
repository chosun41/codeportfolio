//Michael Cho
//CSC 236-64
//Lab 7.1

import java.util.NoSuchElementException;

public class UnorderedLinkedList<T>
{
	private Node<T> firstNode;

	//default constructor
	public UnorderedLinkedList()
	{
		firstNode=null;
	}

	//check if empty
	public boolean isEmpty()
	{
		return firstNode==null;
	}

	//return fist node
	public Node getFirstNode()
	{
		return firstNode;
	}

	//set the first node
	public void setFirstNode(Node<T> node)
	{
		firstNode=node;
	}

	//add at the start
	public void addFirst(T o)
	{
		if(isEmpty())
			firstNode=new Node(o,null);
		else
			firstNode=new Node(o, firstNode);
	}

	//add at the end
	public void addLast(T o)
	{
		if(isEmpty())
			firstNode=new Node(o,null);
		else
		{
			Node<T> current=firstNode;
			while(current.getNext()!=null)
				current=current.getNext();

			current.setNext(new Node(o,null));
		}
	}

	//remove first node
	public T removeFirst()
	{
		if(isEmpty())
			throw new NoSuchElementException("Can't remove from empty list");
		T item=firstNode.getValue();
		firstNode=firstNode.getNext();
		return item;
	}

	//remove last node
	public T removeLast()
	{
		if(isEmpty())
			throw new NoSuchElementException("Can't remove from empty list");
		Node<T> current=firstNode;
		Node<T> follow=null;
		while(current.getNext()!=null)
		{
			follow=current;
			current=current.getNext();
		}

		if(follow==null)
			firstNode=null;
		else
			follow.setNext(null);
		return current.getValue();
	}

	//toString method
	public String toString()
	{
		if(isEmpty())
			return "empty.";
		else
		{
			String s="";
			Node current=firstNode;
			while(current!=null)
			{
				s=s+current.getValue() + " ";
				current=current.getNext();
			}
			return s;
		}
	}
}