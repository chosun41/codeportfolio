//Michael Cho
//CSC 236-64
//Lab 3.2

import java.util.Scanner;
import java.lang.Character;

public class Students implements StudentsADT {

	//note these variables don't have corresponding accessor methods
	private int maxstuds;
	private Student [] studarray;

	//default constructor, intialize max to 0 and create a new array.
	//although it doesn't make sense logically, allowable under java
	//empty array until users utilizes setMaxStudents method to change max.
	//populated with new students
	public Students(){
		maxstuds=0;
		studarray=new Student[maxstuds];
		for(int i=0; i<studarray.length; i++)
			studarray[i]=new Student();
	}

	//overloaded constructor, inmaxstuds defines the size of
	//maxstuds and size of student array.
	//populated with new students
	public Students(int inmaxstuds){
		this.maxstuds=inmaxstuds;
		this.studarray=new Student[this.maxstuds];
		for(int i=0; i<studarray.length; i++)
			this.studarray[i]=new Student();
	}

	//copy constructor
	public Students(Students a){
		maxstuds=a.maxstuds;
		studarray=a.studarray;
	}

	//set max num of students, since we can't remove students
	//in this program, we can't set inmaxstuds lower than current
	//max, in which case exception thrown
	public void setMaxNumberOfStudents(int inmaxstuds) throws Exception {
			if (inmaxstuds<maxstuds)
				throw new Exception ("Can't set new max lower than current.");
			else
				{maxstuds=inmaxstuds;
				studarray=new Student [maxstuds];
				}
	}

	//add a student, if try to add more than current max
	//exception thrown
	public void addStudent(String inid) throws Exception{

			//variable numstuds used to count number of students
			int numstuds=0;
			for(int i=0; i<studarray.length;i++)
				{if(studarray[i].getID()!=null)
					numstuds++;
				}
			if (numstuds+1>maxstuds)
				throw new Exception ("Can't add anymore students, max is reached.");
			else
				studarray[numstuds].setID(inid);
	}

	//add course, exception thrown when adding course for student who
	//don't exist, when course name doesn't have 3 letters followed
	//by 3 numbers,or when length of incourse not 6 characters
	public void addCourse(String inid,String incourse) throws Exception {

		//verifying if student exists
		boolean studfound=false;
		for(int i=0;i<maxstuds;i++)
			{if(studarray[i].getID()!=null && studarray[i].getID().equals(inid))
				studfound=true;
			}
		if(studfound==false)
			throw new Exception("Student not found. You entered a wrong id number.");

		//verifying if course name correct
		else if(incourse.length()!=6 ||
			!Character.isLetter(incourse.charAt(0))||
			!Character.isLetter(incourse.charAt(1))||
			!Character.isLetter(incourse.charAt(2))||
			!Character.isDigit(incourse.charAt(3))||
			!Character.isDigit(incourse.charAt(4))||
			!Character.isDigit(incourse.charAt(5)))
			throw new Exception ("Invalid course name. Please enter 3 letters followed by 3 numbers");

		//adding course for corresponding student
		//call Student addcourse method
		else
			{for(int i=0;i<maxstuds;i++)
				{if(studarray[i].getID().equals(inid))
					{studarray[i].addCourse(incourse);
					break;
					}
				}
			}
	}

	//drop course, exception thrown when dropping course for student who
	//doesn't exist or when incourse string doesn't have
	//3 letters followed by 3 numbers
	public void dropCourse(String inid, String incourse) throws Exception {

		//verifying if student exists
		boolean studfound=false;
		for(int i=0;i<maxstuds;i++)
			{if(studarray[i].getID()!=null && studarray[i].getID().equals(inid))
				studfound=true;
			}
		if(studfound==false)
			throw new Exception("Student not found. You entered a wrong id number.");

		//verifying if course name correct
		else if(incourse.length()!=6 ||
			!Character.isLetter(incourse.charAt(0))||
			!Character.isLetter(incourse.charAt(1))||
			!Character.isLetter(incourse.charAt(2))||
			!Character.isDigit(incourse.charAt(3))||
			!Character.isDigit(incourse.charAt(4))||
			!Character.isDigit(incourse.charAt(5)))
			throw new Exception ("Invalid course name. Please enter 3 letters followed by 3 numbers");

		//dropping course for corresponding student
		//call Student dropcourse method
		else
			{for(int i=0;i<maxstuds;i++)
				{if(studarray[i].getID().equals(inid))
					{studarray[i].dropCourse(incourse);
					break;
					}
				}
			}
	}

	//tostring method, utilized by menu option 1 in main with student ids
	public String toString(){
		String s="";
		for(int i=0; i<maxstuds; i++)
			{if (studarray[i].getID()!=null)
				s = s + "Student" + (i+1) + ": " + studarray[i].getID() + "\n" + studarray[i] + "\n";
			}
		return s;
	}

	private class Student{

		//please note that are other Course instances besides "head"
		//but because of traversing, these instances are local to their
		//respective student methods
		private String id;
		private Course head;

		//default constuctor
		public Student(){
		id=null;
		head=null;
		}

		//overloaded constructor
		public Student(String inid, Course inhead){
		this.id=inid;
		this.head=inhead;
		}

		//set id
		public void setID(String inid){
		id=inid;
		}

		//set courses
		public void setCourses(Course inhead){
		head=inhead;
		}

		//get id
		public String getID(){
			return id;
		}

		//get courses as string
		public String getCourses(){
			String s="";

			//note we can only use current to reference head
			//after head is modified in previous methods
			Course current=head;
			s = s + "The current course schedule for student id " + getID() + " is: " + "\n\n";

			//need if else, because if we print current when its null it will
			//cause a null pointer exception
			if (current==null)
				s = s + "The student currently has no classes" + "\n\n" ;
			else
				{int i=1;
				while(current.getLink()!=null)
					{s = s +"Class " + i + ":\n" + current + "\n\n";
					i++;
					current=current.getLink();
					}

			//passes course info for last course in list into String s
				s = s + "Class " + i + ":\n" + current;
				}
			return s;
		}

		//add course, exception thrown when section number<1,
		//credits are <1 or >4
		public void addCourse(String incoursename) throws Exception {

			//asks for section number
			System.out.println("What is the section number?");
			Scanner keyboard=new Scanner(System.in);
			int insecnum=keyboard.nextInt();
			if (insecnum<1)
				throw new Exception ("Section number can't be less than 1");

			//asks for number of credits
			System.out.println("What is the number of credits?");
			int innumcreds=keyboard.nextInt();
			System.out.println();
			if(innumcreds<1 || innumcreds>4)
				throw new Exception ("Number of credits has to be between 1 and 4");

			//here we actually adds the course
			//case of only 1 node

			if (head==null)
				{head = new Course(incoursename, insecnum, innumcreds, null);
				System.out.println(toString());
				}
			//case of more than 1 node with new node added at front
			else
				{head = new Course(incoursename, insecnum, innumcreds, head);

				System.out.println(toString());
				}
		}

		//drop course, exception thrown when you can't drop class with empty schedule,
		//two cases, where only one course before drop and multiple courses before drop
		public void dropCourse(String incoursename) throws Exception{

			//local variable for dropCourse method to see if corresponding course is found
			//after conditionals run trying to find the course to drop
			boolean coursefound=false;

			//verifying if student has empty schedule
			if(head==null)
				throw new Exception("Can't drop class as there are no classes to drop");

			//case where head node has the course we are seeking to drop
			if(head.getCourseName().equals(incoursename))
			{	System.out.println("The current course schedule for student id " + getID()
				+ " is: " + "\n");
				if(head.getLink()==null)
					{head=head.getLink();
					coursefound=true;
					System.out.println("The student currently has no classes");
					}
				else
					{head=head.getLink();
					coursefound=true;
					System.out.println(toString());
					}
			}

			//if head node doesn't have course, we start traversing.
			//if the course name of the current2's link (one node over) equals the course
			//we are seeking to drop, we skip current2 to the link of its link leaving a
			//dangling node of the node we are seeking to drop, which will eventually
			//get picked up by garbage collection eventually
			else
				{	Course current2=head;
					while(current2.getLink()!=null)
					{	if(current2.getLink().getCourseName().equals(incoursename))
							{current2.setLink(current2.getLink().getLink());

							System.out.println(toString());

							coursefound=true;
							break;
							}
						current2=current2.getLink();
					}
				}
			//if all else fails we throw an exception, saying we couldn't find the course
			//throught the boolean coursefound which should turn true if there was a match
			if (coursefound==false)
				throw new Exception("Corresponding course was not found for student");
		}

		//tostring method, utilized by Students above and calls getCourses() to
		//print full course list for student
		public String toString(){

			String s="";
			s = s + getCourses();
			return s;
		}
	}

}

