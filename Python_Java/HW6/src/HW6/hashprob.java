package HW6;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class hashprob {
	public static void main(String [] args) throws FileNotFoundException, IOException{
	        
		/**
		 * This is the main driver of the code that uses the functions below it.
		 * It asks the user to input 100 or 200 for size of the array list. While
		 * the user inputs a wrong value a message will appear prompting them to input
		 * the correct value of 100 or 200. Until this is done, the while loop will not be
		 * broken.
		 * Finally, the size of the array list is passed to functions that test the 3 hash
		 * code functions and write all the results to one file called results.txt.
		 * 
		 */	
		
	    int arylstsz=0;
	    System.out.println("Please enter 100 or 200 for the size of the array list");
		while(true)
		{
			try{
				Scanner inpt= new Scanner(System.in);
				arylstsz = inpt.nextInt();
				testException(arylstsz);
				inpt.close();
				break;
		    	}
			catch(NumberFormatException e){
				System.err.println(e.getMessage());
			}
		}
			
		FileWriter rslt = new FileWriter(new File("results.txt"));
		rslt.write("Results for hash function 1" + System.lineSeparator());
		rslt.write(tsthashfunc1(arylstsz) + System.lineSeparator());
		rslt.write("Results for hash function 2" + System.lineSeparator());
		rslt.write(tsthashfunc2(arylstsz) + System.lineSeparator());
		rslt.write("Results for hash function 3" + System.lineSeparator());
		rslt.write(tsthashfunc3(arylstsz) + System.lineSeparator());
		rslt.close();
	}
	
	/**
	 * This function takes in a string and sums the ASCII value mod arylstsz
	 * 
	 * @param string s, int size of array list
	 * @return int
	 */	
	public static int hashfunc1 (String s, int arylstsz){
		char ch[];
		ch = s.toCharArray();

		int sumasc=0;
		for (int i=0; i < s.length(); i++){
		   sumasc += ch[i];
		}
		return sumasc % arylstsz;
	}

	/**
	 * This function takes in a string, uses Java's hashCode() function,
	 * if negative convert to positive, and  mod arylstsz
	 * 
	 * @param string s, int size of array list
	 * @return int
	 */	
	public static int hashfunc2 (String s, int arylstsz){
		int finhash=s.hashCode();
		if (finhash<0)
			finhash=-finhash;
		return finhash % arylstsz;
	}
	
	/**
	 * This function takes in a string and uses Horner's rule
	 * https://courses.cs.washington.edu/courses/cse326/00wi/handouts/lecture15/sld012.htm
	 * 
	 * @param string s, int size of array list
	 * @return int
	 */	
	public static int hashfunc3 (String s, int arylstsz){
		char ch[];
		ch = s.toCharArray();
		
		int h=0;
		for(int i=s.length()-1;i>=0;i--){
			h=(ch[i]+128*h)% arylstsz;
		}
		return h;
	}
	
	/**
	 * This function takes in a size of a return list, creates a hash table which is an
	 * array list of linked list, which have String values.
	 * The default head of these linked list is  "EMPTY VALUE...".
	 * The function reads in input file, and fills the hash table in at the index
	 * indicated by hashfunc1.
	 * If the head of the linked list is "EMPTY VALUE...", it replaces it with a 
	 * new String from the input file, otherwise it adds the String to the end of the
	 * linkedlist.
	 * The function writes the hashtable to output1.txt, with the line number, which
	 * is the index of the arraylist + 1 because of zero indexing and only writing comma space
	 * if it's not the last element in the linkedlist.
	 * Finally, it loops through the hashtable to find the number of arraylist elements
	 * with an empty value, otherwise it finds the number of collisions which is
	 * the number of elements inside a linkedlist -1, since the first element is not a collision.
	 * The number of collisions and empty values are then added to the empty string, to be returned.
	 * 
	 * @param int size of array list
	 * @return string
	 */	
	public static String tsthashfunc1(int arylstsz) 
			throws FileNotFoundException, IOException{
		
		String str="";
		int collisions=0;
		int emptycnt=0;
		
		ArrayList<LinkedList<String>> hashtbl = new ArrayList<LinkedList<String>>(arylstsz);
		for (int x=0; x<arylstsz; x++){
			LinkedList<String> lnklst = new LinkedList<String>();
			lnklst.add("EMPTY VALUE...");
			hashtbl.add(x, lnklst);
		}

		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		FileWriter fw1 = new FileWriter(new File("output1.txt"));

		while (sc.hasNextLine()) {
		    String s = sc.next();
		    int arylstindx = hashfunc1(s,arylstsz);
		    LinkedList<String> lnklst=hashtbl.get(arylstindx);
		    if (lnklst.peekFirst().equals("EMPTY VALUE..."))
		    	lnklst.set(0,s);
			else
				lnklst.addLast(s);

		}
		sc.close();
		
		for (int k=0; k<hashtbl.size(); k++){
			String s=String.format("%d, ", k+1);
			LinkedList<String> lnklst=hashtbl.get(k);
			for (int m=0;m<lnklst.size();m++){
				if (m==lnklst.size()-1)
					s+=lnklst.get(m);
				else
					s+=lnklst.get(m) + ", ";
			}
			fw1.write(s + System.lineSeparator() + System.lineSeparator());
		}
		fw1.close();
		
		for (int n=0; n<hashtbl.size(); n++){
		    LinkedList<String> lnklst=hashtbl.get(n);
		    if (lnklst.peekFirst().equals("EMPTY VALUE..."))
		    	emptycnt+=1;
		    else
		    	collisions+=lnklst.size()-1;
		}
		
		str+=String.format("The number of collisions is: %d \n"
				+ "The number of empty indexes is %d\n", collisions, emptycnt);
		return str;
	}

	/**
	 * This function takes in a size of a return list, creates a hash table which is an
	 * array list of linked list, which have String values.
	 * The default head of these linked list is  "EMPTY VALUE...".
	 * The function reads in input file, and fills the hash table in at the index
	 * indicated by hashfunc2.
	 * If the head of the linked list is "EMPTY VALUE...", it replaces it with a 
	 * new String from the input file, otherwise it adds the String to the end of the
	 * linkedlist.
	 * The function writes the hashtable to output2.txt, with the line number, which
	 * is the index of the arraylist + 1 because of zero indexing and only writing comma space
	 * if it's not the last element in the linkedlist.
	 * Finally, it loops through the hashtable to find the number of arraylist elements
	 * with an empty value, otherwise it finds the number of collisions which is
	 * the number of elements inside a linkedlist -1, since the first element is not a collision.
	 * The number of collisions and empty values are then added to the empty string, to be returned.
	 * 
	 * @param int size of array list
	 * @return string
	 */	
	public static String tsthashfunc2(int arylstsz) throws FileNotFoundException, IOException{
		
		String str="";
		int collisions=0;
		int emptycnt=0;
		
		ArrayList<LinkedList<String>> hashtbl = new ArrayList<LinkedList<String>>(arylstsz);
		for (int x=0; x<arylstsz; x++){
			LinkedList<String> lnklst = new LinkedList<String>();
			lnklst.add("EMPTY VALUE...");
			hashtbl.add(x, lnklst);
		}

		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		FileWriter fw1 = new FileWriter(new File("output2.txt"));

		while (sc.hasNextLine()) {
		    String s = sc.next();
		    int arylstindx = hashfunc2(s,arylstsz);
		    LinkedList<String> lnklst=hashtbl.get(arylstindx);
		    if (lnklst.peekFirst().equals("EMPTY VALUE..."))
		    	lnklst.set(0,s);
			else
				lnklst.addLast(s);

		}
		sc.close();
		
		for (int k=0; k<hashtbl.size(); k++){
			String s=String.format("%d, ", k+1);
			LinkedList<String> lnklst=hashtbl.get(k);
			for (int m=0;m<lnklst.size();m++){
				if (m==lnklst.size()-1)
					s+=lnklst.get(m);
				else
					s+=lnklst.get(m) + ", ";
			}
			fw1.write(s + System.lineSeparator() + System.lineSeparator());
		}
		fw1.close();
		
		for (int n=0; n<hashtbl.size(); n++){
		    LinkedList<String> lnklst=hashtbl.get(n);
		    if (lnklst.peekFirst().equals("EMPTY VALUE..."))
		    	emptycnt+=1;
		    else
		    	collisions+=lnklst.size()-1;
		}
		
		str+=String.format("The number of collisions is: %d \n"
				+ "The number of empty indexes is %d\n", collisions, emptycnt);
		return str;
	}
	
	/**
	 * This function takes in a size of a return list, creates a hash table which is an
	 * array list of linked list, which have String values.
	 * The default head of these linked list is  "EMPTY VALUE...".
	 * The function reads in input file, and fills the hash table in at the index
	 * indicated by hashfunc3.
	 * If the head of the linked list is "EMPTY VALUE...", it replaces it with a 
	 * new String from the input file, otherwise it adds the String to the end of the
	 * linkedlist.
	 * The function writes the hashtable to output3.txt, with the line number, which
	 * is the index of the arraylist + 1 because of zero indexing and only writing comma space
	 * if it's not the last element in the linkedlist.
	 * Finally, it loops through the hashtable to find the number of arraylist elements
	 * with an empty value, otherwise it finds the number of collisions which is
	 * the number of elements inside a linkedlist -1, since the first element is not a collision.
	 * The number of collisions and empty values are then added to the empty string, to be returned.
	 * 
	 * @param int size of array list
	 * @return string
	 */	
	public static String tsthashfunc3(int arylstsz) throws FileNotFoundException, IOException{
		
		String str="";
		int collisions=0;
		int emptycnt=0;
		
		ArrayList<LinkedList<String>> hashtbl = new ArrayList<LinkedList<String>>(arylstsz);
		for (int x=0; x<arylstsz; x++){
			LinkedList<String> lnklst = new LinkedList<String>();
			lnklst.add("EMPTY VALUE...");
			hashtbl.add(x, lnklst);
		}

		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		FileWriter fw1 = new FileWriter(new File("output3.txt"));

		while (sc.hasNextLine()) {
		    String s = sc.next();
		    int arylstindx = hashfunc3(s,arylstsz);
		    LinkedList<String> lnklst=hashtbl.get(arylstindx);
		    if (lnklst.peekFirst().equals("EMPTY VALUE..."))
		    	lnklst.set(0,s);
			else
				lnklst.addLast(s);

		}
		sc.close();
		
		for (int k=0; k<hashtbl.size(); k++){
			String s=String.format("%d, ", k+1);
			LinkedList<String> lnklst=hashtbl.get(k);
			for (int m=0;m<lnklst.size();m++){
				if (m==lnklst.size()-1)
					s+=lnklst.get(m);
				else
					s+=lnklst.get(m) + ", ";
			}
			fw1.write(s + System.lineSeparator() + System.lineSeparator());
		}
		fw1.close();
		
		for (int n=0; n<hashtbl.size(); n++){
		    LinkedList<String> lnklst=hashtbl.get(n);
		    if (lnklst.peekFirst().equals("EMPTY VALUE..."))
		    	emptycnt+=1;
		    else
		    	collisions+=lnklst.size()-1;
		}
		
		str+=String.format("The number of collisions is: %d \n"
				+ "The number of empty indexes is %d\n", collisions, emptycnt);
		return str;
	}

	/**
	 * This exception function is used to indicate that the user didn't input 
	 * the right value.
	 * 
	 * @param int value
	 * @return none except a numberformatexception indicating that the user didn't put in 100
	 * or 200
	 */	
	public static void testException(int i) 
			throws NumberFormatException{
		if(i!=100 && i!=200){
			throw new NumberFormatException("Array list size is incorrect. Please reenter.");
		}
	}
	
}
