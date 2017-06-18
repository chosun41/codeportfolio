//Michael Cho
//CSC 236-64
//Lab 4.1

public interface ArrayStackADT<T>
{
	//initialize the stack
	public void initializeStack();

	//checks if stack empty
	public boolean isEmptyStack();

	//checks if stack full
	public boolean isFullStack();

	//push element onto top of stack
	//Precondition: make sure stack is not full
	public void push(T newitem) throws StackOverflowException;

	//looks at top of stock but doesn't modify
	//Precondition: make sure stack is not empty
	public T peek() throws StackUnderflowException;

	//removes top of stack, doesn't return
	//Precondition: make sure stack is not empty
	public void pop() throws StackUnderflowException;

}