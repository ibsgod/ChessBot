package chess;

import java.util.ArrayList;

public class King extends Piece
{
	public boolean castled = false;
	public boolean inCheck = false;
	public boolean first = true;
	public King(ChessBoard g, int y, int x, boolean white, boolean isKing)
	{
		super (g, y, x, white, isKing);
		img = white ? "wking" : "bking";
	}
	public ArrayList<Move> getMoves(int y, int x, boolean doCheck, ChessBoard cb) 
	{
		ArrayList<Move> moves = new ArrayList<Move>();
			
		if (y + 1 < cb.board.length)
		{
			if (cb.board[y+1][x] == null)
			{
				Move m = new Move(this, 2, y, x, y+1, x);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			else if (cb.board[y+1][x].white != white)
			{
				Move m = new Move(this, 3, y, x, y+1, x);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			if (x + 1 < cb.board.length)
			{
				if (cb.board[y+1][x+1] == null)
				{
					Move m = new Move(this, 2, y, x, y+1, x+1);
					if (first)
					{
						m.first = true;
					}
					moves.add(m);
				}
				else if (cb.board[y+1][x+1].white != white)
				{
					Move m = new Move(this, 3, y, x, y+1, x+1);
					if (first)
					{
						m.first = true;
					}
					moves.add(m);
				}
			}
			if (x - 1 >= 0)
			{
				if (cb.board[y+1][x-1] == null)
				{
					Move m = new Move(this, 2, y, x, y+1, x-1);
					if (first)
					{
						m.first = true;
					}
					moves.add(m);
				}
				else if (cb.board[y+1][x-1].white != white)
				{
					Move m = new Move(this, 3, y, x, y+1, x-1);
					if (first)
					{
						m.first = true;
					}
					moves.add(m);
				}
			}
		}
		if (y - 1 >= 0)
		{
			if (cb.board[y-1][x] == null)
			{
				Move m = new Move(this, 2, y, x, y-1, x);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			else if (cb.board[y-1][x].white != white)
			{
				Move m = new Move(this, 3, y, x, y-1, x);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			if (x + 1 < cb.board.length)
			{
				if (cb.board[y-1][x+1] == null)
				{
					Move m = new Move(this, 2, y, x, y-1, x+1);
					if (first)
					{
						m.first = true;
					}
					moves.add(m);
				}
				else if (cb.board[y-1][x+1].white != white)
				{
					Move m = new Move(this, 3, y, x, y-1, x+1);
					if (first)
					{
						m.first = true;
					}
					moves.add(m);
				}
			}
			if (x - 1 >= 0)
			{
				if (cb.board[y-1][x-1] == null)
				{
					Move m = new Move(this, 2, y, x, y-1, x-1);
					if (first)
					{
						m.first = true;
					}
					moves.add(m);
				}
				else if (cb.board[y-1][x-1].white != white)
				{
					Move m = new Move(this, 3, y, x, y-1, x-1);
					if (first)
					{
						m.first = true;
					}
					moves.add(m);
				}
			}
		}
		if (x + 1 < cb.board.length)
		{
			if (cb.board[y][x+1] == null)
			{
				Move m = new Move(this, 2, y, x, y, x+1);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			else if (cb.board[y][x+1].white != white)
			{
				Move m = new Move(this, 3, y, x, y, x+1);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
		}
		if (x - 1 >= 0)
		{
			if (cb.board[y][x-1] == null)
			{
				Move m = new Move(this, 2, y, x, y, x-1);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			else if (cb.board[y][x-1].white != white)
			{
				Move m = new Move(this, 3, y, x, y, x-1);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
		}	
		
		if (doCheck)
		{
			int r = 0;
			if (white)
			{
				r = 7;
			}
			if (cb.canCastle(white) % 2 == 1 && y == r && x == 4)
			{
				Move m = new Move(this, 4, y, x, y, x-2);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			if (cb.canCastle(white) > 1 && y == r && x == 4)
			{
				Move m = new Move(this, 4, y, x, y, x+2);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			moves = checkCheck(cb, white, moves);
		}
		return moves;	
		
	}
}
