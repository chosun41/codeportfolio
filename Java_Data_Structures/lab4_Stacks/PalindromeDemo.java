//Michael Cho
//CSC 236-64
//Lab 4.3

//using swing for input and message dialogs
import javax.swing.*;

public class PalindromeDemo
{
	public static void main(String [] args)
	{
		//instantiate a LinkedStackDS<Character> object
		LinkedStackDS<Character> palstack=new LinkedStackDS<Character>();

		//string used in the input dialog and exit used to exit program
		String inputstring;

		int exit;

		//continues taking inputs until sentinel value of bye
		do
		{
			JFrame frame = new JFrame("InputDialog Example #1");

			//input dialog box for Palindrome Test
			inputstring = JOptionPane.showInputDialog(frame, "Input a String for Palindrome Test");

			//replace all special characters that are not letters with blank using regular expression
			String newstring = inputstring.replaceAll("[^a-zA-Z]","");

			//push each letter of newstring onto palstack
			for(int i=0; i<newstring.length();i++)
			{
				char c = newstring.charAt(i);
				palstack.push(newstring.charAt(i));
			}

			//peek, add to reversedstring the character reversedchar, and pop
			//reversedstring should be newstring reversed
			String reversedstring="";

			while(!palstack.isEmptyStack())
			{
				Character reversedchar=palstack.peek();
				reversedstring+=reversedchar;
				palstack.pop();
			}

			//comparetoignorecase method will compare newstring and reversedstring for equality ignoring case
			//if the method returns 0, it means both strings are equal and thus are palindromes,
			//which is outputted by a messagedialog
			if(newstring.compareToIgnoreCase(reversedstring)==0)
				JOptionPane.showMessageDialog ( null, "String " + inputstring + " is a palindrome" );
			else
				JOptionPane.showMessageDialog ( null, "String " + inputstring + " is not a palindrome" );

			//yes no option, program continues is you keep pressing yes which has an int value of 0
			exit = JOptionPane.showConfirmDialog(null, "Test another palindrome?",
    		"Test another palindrome?", JOptionPane.YES_NO_OPTION);

    		if (exit == 1)
				JOptionPane.showMessageDialog(null, "Thanks for using this program!",
			     "Thanks for using this program!", JOptionPane.INFORMATION_MESSAGE);
		}
		while (exit==0);
	}
}










