package chess;

import java.util.ArrayList;

public class Rook extends Piece
{
	public boolean first = true;
	public Rook(ChessBoard g, int y, int x, boolean white, boolean isKing)
	{
		super (g, y, x, white, isKing);
		img = white ? "wrook" : "brook";
	}
	public ArrayList<Move> getMoves(int y, int x, boolean doCheck, ChessBoard cb) 
	{
		ArrayList<Move> moves = new ArrayList<Move>();
		int i = 1;
		while (x + i < cb.board.length)
		{
			if (cb.board[y][x+i] == null)
			{
				Move m = new Move(this, 2, y, x, y, x+i);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			else if (cb.board[y][x+i].white != white)
			{
				Move m = new Move(this, 3, y, x, y, x+i);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
				break;
			}
			else
			{
				break;
			}
			i ++;
		}
		i = 1;
		while (x - i >= 0)
		{
			if (cb.board[y][x-i] == null)
			{
				Move m = new Move(this, 2, y, x, y, x-i);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			else if (cb.board[y][x-i].white != white)
			{
				Move m = new Move(this, 3, y, x, y, x-i);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
				break;
			}
			else
			{
				break;
			}
			i ++;
		}
		i = 1;
		while (y + i < cb.board.length)
		{
			if (cb.board[y+i][x] == null)
			{
				Move m = new Move(this, 2, y, x, y+i, x);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			else if (cb.board[y+i][x].white != white)
			{
				Move m = new Move(this, 3, y, x, y+i, x);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
				break;
			}
			else
			{
				break;
			}
			i ++;
		}
		i = 1;
		while (y - i >= 0)
		{
			if (cb.board[y-i][x] == null)
			{
				Move m = new Move(this, 2, y, x, y-i, x);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
			}
			else if (cb.board[y-i][x].white != white)
			{
				Move m = new Move(this, 3, y, x, y-i, x);
				if (first)
				{
					m.first = true;
				}
				moves.add(m);
				break;
			}
			else
			{
				break;
			}
			i ++;
		}
		if (doCheck)
		{
			moves = checkCheck(cb, white, moves);
		}
		return moves;
	}
}
