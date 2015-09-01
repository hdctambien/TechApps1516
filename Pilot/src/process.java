
import java.util.Scanner;
public class process {
	int xcord,ycord,zcord,angle;
	Scanner input = new Scanner( System.in );
	boolean done = false, finished = false;
	while(done == false)
	{
		System.out.println("What would you like to do?");
		
		String in = input.nextLine();
		if(in.equals("Change"))
		{
			
		}
		if(in.equals("Display"))
		{
			while(finished == false)
			{
				System.out.println("What would you like to do?");
				String choice = input.nextLine();				
				if(choice.equals("exit"))
				{
					break;
				}
			}
		}
		if(in.equals("exit"))
		{
			done = true;
		}
	}
}
}
class Change
{
	public void changeAngle()
	{
		//send to server
	}
	public void goRight()
	{
		
	}
	public void goLeft()
	{
		
	}
	public void goUp()
	{
		
	}
	public void goDown()
	{
		
	}
	
}
