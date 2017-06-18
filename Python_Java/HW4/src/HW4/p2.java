package HW4;
import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JComponent;

@SuppressWarnings("serial")

public class p2
{  
	/**
	 * This function reads in dimensions and draw circles with the right color 
	 * in the appropriate quadrant. The grid dimension input is put into a try
	 * catch block inside of while loop, where the only way to break out is 
	 * to put in the correct input. 3/4 exceptions in testException class.
	 * InputMismatchException is for non numbers.
	 * 
	 * @param Graphics class g
	 * @return none, just reads in user input for dimension and starts calling functions
	 * to fill in the other quadrants
	 */	
	public static void draw(Graphics g)
	{  
		int GridDimension = 6;

		while(true){
			try{
				Scanner in = new Scanner(System.in);
				System.out.println("Enter Grid Dimension:");
				GridDimension = in.nextInt();
				testException(GridDimension);
				break;
			}
			catch(FileNotFoundException e){
				System.err.println(e.getMessage());
			}
			catch(NumberFormatException e){
				System.err.println(e.getMessage());
			}
			catch(IOException e){
				System.err.println(e.getMessage());
			}
			catch(InputMismatchException e){ 
				System.err.println("Not a number.Please reenter.");
			}
		}

		/*indicates index where divide rows and columns into different quadrants*/
		int half=GridDimension/2;
		
		upperleft(half, g);
		upperright(half, GridDimension, g);
		lowerleft(half, GridDimension, g);
		lowerright(half, GridDimension, g);

	}
	
	/**
	 * This function lists 3/4 exceptions we want to capture in a try catch block.
	 * The dimension can't be negative, equal to zero, or not even.
	 * 
	 * @param int i
	 * @return none, just throws exceptions to be caught
	 */	
	public static void testException(int i) 
			throws FileNotFoundException,NumberFormatException,IOException{
		if(i < 0){
			throw new FileNotFoundException("Negative Integer. Please reenter.");
		}
		else if(i == 0){
			throw new NumberFormatException("Number can't be zero. Please reenter.");
		}
		else if(i%2 !=0){
			throw new IOException("Not an even number.Please reenter.");
		}
	}
	
	/**
	 * This function fills in the upper left quadrant with green circles.
	 * 
	 * @param integer for halfway point, input graph of class Graphics
	 * @return none, fills in upper left quadrant with green circles
	 */
	public static void upperleft(int halflength, Graphics inptgraph){
		for (int row = 0;row<halflength;row++)
		{
			for (int column = 0;column<halflength;column++)
			{
				inptgraph.setColor(Color.GREEN);
				inptgraph.fillOval(row*60 + 50,column*60 + 50, 50,50);	
			}
		}
	}

	/**
	 * This function fills in the upper right quadrant with black circles.
	 * 
	 * @param integer for halfway point, integer for fulllength, input graph of class Graphics
	 * @return none, fills in the upper right quadrant with black circles
	 */
	public static void upperright(int halflength, int fulllength, Graphics inptgraph){
		for (int row = 0;row<halflength;row++)
		{
			for (int column = halflength;column<fulllength;column++)
			{
				inptgraph.setColor(Color.BLACK);
				inptgraph.fillOval(row*60 + 50,column*60 + 50, 50,50);	
			}
		}
	}
	
	/**
	 * This function fills in the lower left quadrant with black circles.
	 * 
	 * @param integer for halfway point, integer for fulllength, input graph of class Graphics
	 * @return none, fills in the lower left quadrant with black circles
	 */
	public static void lowerleft(int halflength, int fulllength, Graphics inptgraph){
		for (int row = halflength;row<fulllength;row++)
		{
			for (int column = 0;column<fulllength;column++)
			{
				inptgraph.setColor(Color.BLACK);
				inptgraph.fillOval(row*60 + 50,column*60 + 50, 50,50);	
			}
		}
	}
	
	/**
	 * This function fills in the lower right quadrant with red circles.
	 * 
	 * @param integer for halfway point, input graph of class Graphics
	 * @return none,  fills in the lower right quadrant with red circles.
	 */
	public static void lowerright(int halflength, int fulllength, Graphics inptgraph){
		for (int row = halflength;row<fulllength;row++)
		{
			for (int column = halflength;column<fulllength;column++)
			{
				inptgraph.setColor(Color.RED);
				inptgraph.fillOval(row*60 + 50,column*60 + 50, 50,50);	
			}
		}
	}

	/*parameters for drawing and frame dimensions*/
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();

		final int FRAME_WIDTH = 800;
		final int FRAME_HEIGHT = 800;

		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JComponent component = new JComponent()
		{
			@Override
			public void paintComponent(Graphics graph)
			{
				draw(graph);
			}
		};     
		frame.add(component);
		frame.setVisible(true);
   }   
}