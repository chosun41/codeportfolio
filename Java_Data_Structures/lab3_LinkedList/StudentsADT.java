//Michael Cho
//CSC 236-64
//Lab 3.2

public interface StudentsADT{

	//sets max num of students
	//Precondition: exception thrown when paramater inmaxstuds lower than current max
	public void setMaxNumberOfStudents(int inmaxstuds) throws Exception;

	//add a student
	//Precondition: except thrown when you add 1 more than current max
	public void addStudent(String inid) throws Exception;

	//add course
	//Precondition: exception thrown when adding course for student who
	//don't exist, when course name doesn't have 3 letters followed
	//by 3 numbers, or when length of incourse not 6 characters
	public void addCourse(String inid,String incourse) throws Exception;

	//drop course
	//Precondition: exception thrown when dropping course for student who
	//doesn't exist or when incourse string doesn't have
	//3 letters followed by 3 numbers
	public void dropCourse(String inid, String incourse) throws Exception ;

	//returns student id and courses for Students class
	public String toString();

}