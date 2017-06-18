//Michael Cho
//CSC 236-64
//Lab 3.2

import java.util.Scanner;

public class StudentsDemo{

	public static void main(String[] args) throws Exception {

		//initialize size of student array and add students with intial class schedule
		Students studs=new Students(5);
		studs.addStudent("1111");
		studs.addStudent("1234");
		studs.addStudent("2357");

		//menu driven through number typed and passed through scanner
		System.out.println("What action would you like to implement?");
		System.out.println("1: Show all Students");
		System.out.println("2: Add a Course");
		System.out.println("3: Drop a Course");
		System.out.println("9: Quit");

		//must initialize variable that is going to be used in the menu
		int menuchoice=0;
		Scanner in = new Scanner(System.in);

		//menu won't quit until 9 is picked to get out of program, also default indicates an
		//invalid meu choice
			while (menuchoice!=9)
			{			  System.out.println();
						  System.out.println("What action would you like to implement?");
						  menuchoice=in.nextInt();
						  System.out.println();
		               	  switch (menuchoice) {
		                  case 1:
		                        System.out.println("Showing all students\n");
		                        System.out.println(studs);
		                        break;
		                  case 2:
		                        System.out.println("Adding a course" + "\n");
		                        System.out.println("Type in id for the student taking the course.");
		                        String inid2=in.next();
		                        System.out.println("Type in the name of the course student is adding.");
		                        String incoursename2=in.next();
		                        studs.addCourse(inid2, incoursename2);
		                        break;
		                  case 3:
		                        System.out.println("Dropping a course\n");
		                        System.out.println("Type in id for the student dropping the course.");
		                        String inid3=in.next();
		                        System.out.println("Type in the name of the course student is dropping.");
		                        String incoursename3=in.next();
		                        studs.dropCourse(inid3, incoursename3);
		                        break;
		                  case 9:
		                  		System.out.println("Exiting the program. Good bye!");
		                  		System.exit(0);
		                  		break;
		                  default:
		                  		System.out.print("Invalid menu selection. Please choose another option from menu");
		                  		break;
					  	  }
			}
	}
}