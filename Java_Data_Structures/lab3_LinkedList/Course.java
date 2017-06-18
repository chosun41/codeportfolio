//Michael Cho
//CSC 236-64
//Lab 3.2

public class Course {

	private String coursename;
	private int secnum, numcreds;
	private Course link;

	//default constructor
	public Course(){
	coursename=null;
	secnum=0;
	numcreds=0;
	link=null;
	}

	//overloaded constructor
	public Course(String incoursename, int insecnum, int innumcreds, Course inlink){
	this.coursename=incoursename;
	this.secnum=insecnum;
	this.numcreds=innumcreds;
	this.link=inlink;
	}

	//copy constructor
	public Course(Course a){
	coursename=a.coursename;
	secnum=a.secnum;
	numcreds=a.numcreds;
	link=a.link;
	}

	//set course name
	public void setCourseName(String incoursename){
		coursename=incoursename;
	}

	//set section number
	public void setSectionNumber(int insecnum){
		secnum=insecnum;
	}

	//set number of credits
	public void setNumberofCredits(int innumcreds){
		numcreds=innumcreds;
	}

	//set link
	public void setLink(Course inlink){
		link=inlink;
	}

	//get course name
	public String getCourseName(){
		return coursename;
	}

	//get section number
	public int getSectionNumber(){
		return secnum;
	}


	//get number of credit
	public int getNumberOfCredits(){
		return numcreds;
	}

	//get link
	public Course getLink(){
		return link;
	}

	//to String method
	public String toString(){
			String s="";
			s = s + "Course name : " + coursename + "\n"
			+ "Section number: " + secnum +"\n"
			+ "Number of credits: " + numcreds + "\n";
			return s;
	}

}

