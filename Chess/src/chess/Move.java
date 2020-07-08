package chess;

public class Move 
{
	int startY;
	int startX;
	int endY;
	int endX;
	Piece piece;
	String canKill;
	int type;
	boolean first;
	@Override
	protected Object clone() throws CloneNotSupportedException {

	    return super.clone();
	}
	public Move(Piece piece, int type, int startY, int startX, int endY, int endX)
	{
		this.type = type;
		this.piece = piece;
		this.startY = startY;
		this.startX = startX;
		this.endY = endY;
		this.endX = endX;
	}

}
