package chess;

import java.util.ArrayList;

public class Pawn extends Piece
{
	boolean first = true;
	public Pawn(ChessBoard g, int y, int x, boolean white, boolean isKing)
	{
		super (g, y, x, white, isKing);
		img = white ? "wpawn" : "bpawn";
	}
	public ArrayList<Move> getMoves(int y, int x, boolean doCheck, ChessBoard cb) 
	{
		ArrayList<Move> moves = new ArrayList<Move>();
		if (!white)
		{
			if (y+1 < cb.board.length && cb.board[y+1][x] == null)
			{
				Move m = new Move(this, 2, y, x, y+1, x);
				if (y + 1 == cb.board.length-1)
				{
					m.type += 3;
				}
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
				if (first && cb.board[y+2][x] == null)
				{
					m = new Move(this, 2, y, x, y+2, x);
					if (y + 1 == cb.board.length-1)
					{
						m.type += 3;
					}
					m.first = true;
					moves.add(m);
				}
			}
			if (y+1 < cb.board.length && x+1 < cb.board.length && cb.board[y+1][x+1] != null && cb.board[y+1][x+1].white != white)
			{
				Move m = new Move(this, 3, y, x, y+1, x+1);
				if (y + 1 == cb.board.length-1)
				{
					m.type += 3;
				}
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			if (y+1 < cb.board.length && x-1 >= 0 && cb.board[y+1][x-1] != null && cb.board[y+1][x-1].white != white)
			{
				Move m = new Move(this, 3, y, x, y+1, x-1);
				if (y + 1 == cb.board.length-1)
				{
					m.type += 3;
				}
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
		}
		if (white)
		{
			if (y-1 >= 0 && cb.board[y-1][x] == null)
			{
				Move m = new Move(this, 2, y, x, y-1, x);
				if (y - 1 == 0)
				{
					m.type += 3;
				}
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
				if (first && cb.board[y-2][x] == null)
				{
					m = new Move(this, 2, y, x, y-2, x);
					if (y - 1 == 0)
					{
						m.type += 3;
					}
					m.first = true;
					moves.add(m);
				}
			}
			if (y-1 >= 0 && x+1 < cb.board.length && cb.board[y-1][x+1] != null && cb.board[y-1][x+1].white != white)
			{
				Move m = new Move(this, 3, y, x, y-1, x+1);
				if (y - 1 == 0)
				{
					m.type += 3;
				}
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			if (y-1 >= 0 && x-1 >= 0 && cb.board[y-1][x-1] != null && cb.board[y-1][x-1].white != white)
			{
				Move m = new Move(this, 3, y, x, y-1, x-1);
				if (y - 1 == 0)
				{
					m.type += 3;
				}
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
		}
		
		if (doCheck)
		{
			//System.out.println("\n" + y + " " + x);
			moves = checkCheck(cb, white, moves);
		}
		return moves;	
		
	}

	
}
