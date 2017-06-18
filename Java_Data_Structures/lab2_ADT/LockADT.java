public interface LockADT
{
	public void setx(int newx) throws Exception;
	//sets 1st number of lock
	//Precondition: check newx is between 0-39 inclusive

	public void sety(int newy) throws Exception;
	//sets 2nd number of lock
	//Precondition: check newy is between 0-39 inclusive

	public void setz(int newz)throws Exception;
	//sets 3rd number of lock
	//Precondition: check newy is between 0-39 inclusive

	public void alter() throws Exception;
	//alter combo of lock

	public int turn(int turnnumber, int turnto) throws Exception;
	//turn dial and print current dial number
	//Precondition: turnto has to be between 0-39 inclusive

	public void close();
	//close the lock

	public void attempt() throws Exception;
	//attempt to open the lock

	public void inquire();
	//check whether lock is close

	public String current();
	//check what the number the dial is at currently

	public String toString();
	//check what is the current string combo
}