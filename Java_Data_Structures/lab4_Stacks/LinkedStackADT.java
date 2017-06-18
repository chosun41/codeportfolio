//Michael Cho
//CSC 236-64
//Lab 4.3

public interface LinkedStackADT<T>
{
	//checks if list stack is empty
	public boolean isEmptyStack();

	//add to top
	public void push(T newelement);

	//look at top, doesn't modify list stack
	public T peek() throws StackUnderflowException;

	//remove from top
	public void pop() throws StackUnderflowException;

}