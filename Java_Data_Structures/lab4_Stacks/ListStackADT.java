//Michael Cho
//CSC 236-64
//Lab 4.2

public interface ListStackADT<T>
{
	//checks if list stack is empty
	public boolean isEmpty();

	//throws exception if stack empty
	public void ifEmpty() throws EmptyStackException;

	//add to top
	public void push(T newelement);

	//look at top, doesn't modify list stack
	//Precondition:stack is not empty
	public T peek() throws EmptyStackException;

	//remove from top
	//Precondition:stack is not empty
	public void pop() throws EmptyStackException;

	//tostring method
	public String toString();

}