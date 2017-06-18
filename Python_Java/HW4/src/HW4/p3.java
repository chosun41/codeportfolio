package HW4;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.*;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.io.FileWriter;

public class p3 {
	/**
	 * This is the main driver function where we first establish that we are reading from the 
	 * employees.txt file and create 3 arraylists for name, age, and salary.
	 * The end output and writing to the text file relies on functions defined in 
	 * other public classes further below in the code.
	 * 
	 * @param console arguments (empty unless specified)
	 * @return none
	 */
	public static void main(String[] args)throws IOException{
		File f = new File("employees.txt");
		ArrayList<String>names = scanFileString(f,0);
		ArrayList<Integer>age = scanFileLocalDate(f,1);		
		ArrayList<Integer>salary = scanFileInt(f,2);
		
		System.out.println(noEmployees(names));
		System.out.println(whomaxSal(salary, names));
		System.out.println(avgSal(salary));
		System.out.println(cntabvavgSal(salary));
		System.out.println(avgAge(age));
		nameSortHL(salary,names);
	}
	/**
	 * This function takes in names array and prints the number of employees from the
	 * array list size.
	 * 
	 * @param arraylist
	 * @return string with message and number of employees
	 */
	public static String noEmployees(ArrayList<String> inptarray) {
		String s="The number of employees is " + inptarray.size() + "\r\n";
		return s;
	}

	/**
	 * This function uses Collections to find max of salary arraylist and finds the 
	 * corresponding index. It finds the value in the corresponding index of the names
	 * arraylist and adds this to the String s to be returned.
	 * 
	 * @param arraylist of integers, arraylist of strings
	 * @return string with message and max salary
	 */
	public static String whomaxSal(ArrayList<Integer> inptarray1, ArrayList<String> inptarray2) {
		int maxsal=Collections.max(inptarray1);
		int indexmaxsal=inptarray1.indexOf(maxsal);
		String s="The person with the max salary is " + inptarray2.get(indexmaxsal) + "\r\n";
		return s;
	}
	
	/**
	 * This function takes in salary arraylist, sums up all the values inside
	 * and divides it by the size of the arraylist to get the average. The average
	 * is added to the string and formatted to 2 decimal places. 
	 * 
	 * @param arraylist of integers
	 * @return string with message and formatted average salary
	 */
	public static String avgSal(ArrayList<Integer> inptarray) {
		float sumsal=0;
		for (int i = 0; i < inptarray.size(); i++){
		         sumsal += inptarray.get(i) ; 
		}
		float avgsal=sumsal/inptarray.size();
		String s=String.format("The average salary is $%,.2f%n",avgsal);
		return s;
	}

	/**
	 * This function takes in salary arraylist, computes average similar to the previous function.
	 * Then it increments count for those who have salary above the average salary and 
	 * adds to to a string format to be returned.
	 * 
	 * @param arraylist of integers
	 * @return string with message and formatted count of those above average salary.
	 */
	public static String cntabvavgSal(ArrayList<Integer> inptarray) {
		float sumsal=0;
		for (int i = 0; i < inptarray.size(); i++){
		         sumsal += inptarray.get(i) ; 
		}
		float avgsal=sumsal/inptarray.size();
		
		int cntabvavgsal=0;
		for (int i = 0; i < inptarray.size(); i++){
			if(inptarray.get(i)>avgsal)
				cntabvavgsal +=1 ; 
		}
		String s=String.format("%d people earn above the average salary\n",cntabvavgsal);
		return s;
	}
	
	/**
	 * This function takes in the age arraylist, adds up all the values 
	 * and divides by arraylist size to get the average. This average is then
	 * adds to to a string format to be returned.
	 * 
	 * @param arraylist of integers
	 * @return string with message and formatted string of average age.
	 */
	public static String avgAge(ArrayList<Integer> inptarray) {
		float sumage=0;
		for (int i = 0; i < inptarray.size(); i++){
		         sumage += inptarray.get(i) ; 
		}
		float avgage=sumage/inptarray.size();
		String s=String.format("The average age is %.2f years\n",avgage);
		return s;
	}

	/**
	 * This function takes in the salary and name arraylists.
	 * A newsalary arraylist is created from a copy of the salary arraylist, which
	 * is then sorted in reverse order through collections.
	 * We also created an empty sorted names arraylist which is filled in later and we open
	 * a filewriter to the empty text file.
	 * We go through the newsalary arraylist and get the value, which we can find the index
	 * for in the original salary arraylist and the corresponding original name.
	 * Three test cases:
	 * 1. If sortednames is empty, add the corresponding name
	 * 2. If the name is already in sortednames (because get(indexorigsal) finds the first occurence
	 * of the salary when there might be multiple people with the same salary, we continue onto
	 * the next salary index until the end searching for another match on salary amount.
	 * Once we get a match, we place the name into sortednames.
	 * 3. In the else case, iff the sortednamed doesn't include the current name under, 
	 * we just add it to sortednames. 
	 * 
	 * @param arraylist of integers, arraylist of string
	 * @return nothing, prints and writes names to be added to the text file
	 */
	public static void nameSortHL(ArrayList<Integer>inptarray1,ArrayList<String>inptarray2) 
			throws IOException{
		ArrayList<Integer> newsalary = new ArrayList<>(inptarray1);
		Collections.sort(newsalary, Collections.reverseOrder());
		ArrayList<String> sortednames = new ArrayList<>();
		FileWriter fw = new FileWriter("namessalhightolow.txt");
	
		for (int i = 0; i < newsalary.size(); i++){
			int newsalvalue=newsalary.get(i);
			int indexorigsal=inptarray1.indexOf(newsalvalue);
			String origname=inptarray2.get(indexorigsal);
				
			if (sortednames.isEmpty()) 
				sortednames.add(origname);
			else if (sortednames.contains(origname))
				for(int j=indexorigsal+1; j<inptarray2.size(); j++){
					if(inptarray1.get(j).equals(newsalvalue)){
						origname=inptarray2.get(j);
						sortednames.add(origname);
					}	
				}
			else
				sortednames.add(origname);

			}
		System.out.println("The following name are being placed into the text file");
		for (String indvnames : sortednames) {
			System.out.println(indvnames);
			fw.write(indvnames + System.getProperty("line.separator"));
		}
	fw.close();
	}
	
	/**
	 * This function takes in a File object f, int index, and adds the portion of
	 * processed lines in arraylist of strings.
	 * 
	 * @param f, index
	 * @return result, processed line portion by index in File f.
	 */
	public static ArrayList<String> scanFileString(File f, int index) {
		ArrayList<String> result = new ArrayList<String>();
		Scanner scan = null;
		try {
			scan = new Scanner(f);
			while (scan.hasNextLine()) {
				String tempLine = scan.nextLine();
				result.add(processLine(tempLine, index));
			}
		} catch (FileNotFoundException ex) {
			System.out.println("No file found at: " + f.getAbsolutePath());
		} finally {
			// clean up the scanner
			if (scan != null) {
				scan.close();
			}
		}
		return result;
	}

	/**
	 * This function takes in a File object f, int index, transforms input into a LocalDate
	 * object, computes difference in years with 2/17/2017 to get age, and adds this to
	 * an arraylist of integers
	 * 
	 * @param f, index
	 * @return result, processed line portion by index in File f.
	 */
	public static ArrayList<Integer> scanFileLocalDate(File f, int index){
		ArrayList<Integer> result = new ArrayList<Integer>();
		Scanner scan = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy").
				withLocale(Locale.US);
		LocalDate today=LocalDate.parse("02/17/2017",formatter);
		try {
			scan = new Scanner(f);
			while (scan.hasNextLine()) {
				String tempLine = scan.nextLine();
				LocalDate indvdob= LocalDate.parse(processLine(tempLine, index),formatter);
				Integer age = (int) Period.between(indvdob, today).getYears();
				result.add(age);
			}
		} catch (FileNotFoundException ex) {
			System.out.println("No file found at: " + f.getAbsolutePath());
		} finally {
			// clean up the scanner
			if (scan != null) {
				scan.close();
			}
		}
		return result;
	}

	/**
	 * This function takes in a File object f, int index, and adds the portion of
	 * processed lines in arraylist of integers.
	 * 
	 * @param f, index
	 * @return result, processed line portion by index in File f.
	 */
	public static ArrayList<Integer> scanFileInt(File f, int index) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		Scanner scan = null;
		try {
			scan = new Scanner(f);
			while (scan.hasNextLine()) {
				String tempLine =scan.nextLine();
				result.add(Integer.parseInt(processLine(tempLine, index)));
			}
		} catch (FileNotFoundException ex) {
			System.out.println("No file found at: " + f.getAbsolutePath());
		} finally {
			// clean up the scanner
			if (scan != null) {
				scan.close();
			}
		}
		return result;
	}

	/**
	 * Function creates empty string, splits on commas, and returns a portion of the line
	 * by index (0 for names, 1 for age, and 2 for salary).
	 * 
	 * @param line, index of line
	 * @return portion of line by split index
	 */
	public static String processLine(String line, int index) {
		String result = "";
		result = line.split(",")[index];
		return result;
	}
}