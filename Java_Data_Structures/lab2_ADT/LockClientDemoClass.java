import java.util.Scanner;

public class LockClientDemoClass{
	//throws exception because of potential use of set and alter methods
	public static void main(String[] args) throws Exception{

		//menu driven through number typed and passed through scanner variable int
		LockDataStructureClass lock=new LockDataStructureClass();
		System.out.println("1: set lock combination");
		System.out.println("2: close lock");
		System.out.println("3: check status");
		System.out.println("4: attempt to open lock");
		System.out.println("5: check what the dial is currently pointing at");
		System.out.println("6: exit the program");

		//must initialize variable that is going to be used in the menu
		int menuchoice=0;
		Scanner in = new Scanner(System.in);

		//menu won't quit until 6 is picked to get out of program, also default indicates an
		//invalid meu choice
			while (menuchoice!=6)
			{			  System.out.println();
						  System.out.println();
						  System.out.println("What do you want to do with the lock? Please choose from the menu.");
						  menuchoice=in.nextInt();
		               	  switch (menuchoice) {
		                  case 1:
		                        System.out.println("Setting lock combination");
		                        lock.alter();
		                        break;
		                  case 2:
		                        System.out.println("Closing the lock");
		                        lock.close();
		                        break;
		                  case 3:
		                        System.out.println("Checking whether the lock is closed");
		                        lock.inquire();
		                        break;
		                  case 4:
		                        System.out.println("Attempting to open lock");
		                        lock.attempt();
		                        break;
		                  case 5:
		                        System.out.println("Checking what position the dial is at");
		                        System.out.println(lock.current());
		                        break;
		                  case 6:
		                  		System.out.println("Exiting the program. Good bye!");
		                  		System.exit(0);
		                  		break;
		                  default:
		                  		System.out.print("Invalid menu selection. Please choose another option from 1-6");
		                  		break;
					  	  }
			}
	}
}