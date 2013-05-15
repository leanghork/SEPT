import model.*;
import view.*;
import controller.*;

public class Driver 
{
	public static void main (String[] args)
	{
		Model svg = new Model(args);
		View gui = new View(svg);
	}
}
