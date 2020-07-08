package chess;

public class Main 
{
	public static void main (String args[]) throws CloneNotSupportedException
	{
		Game g = new Game ();
		g.b.g = g;
	}
}
