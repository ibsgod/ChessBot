package chess;

import java.util.ArrayList;

public class Knight extends Piece
{
	public Knight(ChessBoard g, int y, int x, boolean white, boolean isKing)
	{
		super (g, y, x, white, isKing);
		img = white ? "wknight" : "bknight";
	}
	public ArrayList<Move> getMoves(int y, int x, boolean doCheck, ChessBoard cb)
	{
		ArrayList<Move> moves = new ArrayList<Move>();
		if (y + 2 < cb.board.length)
		{
			if (x + 1 < cb.board.length)
			{
				if (cb.board[y+2][x+1] == null)
				{
					moves.add(new Move(this, 2, y, x, y+2, x+1));
				}
				else if (cb.board[y+2][x+1].white != white)
				{
					moves.add(new Move(this, 3, y, x, y+2, x+1));
				}
			}
			if (x - 1 >= 0)
			{
				if (cb.board[y+2][x-1] == null)
				{
					moves.add(new Move(this, 2, y, x, y+2, x-1));
				}
				else if (cb.board[y+2][x-1].white != white)
				{
					moves.add(new Move(this, 3, y, x, y+2, x-1));
				}
			}
		}
		if (y - 2 >= 0)
		{
			if (x + 1 < cb.board.length)
			{
				if (cb.board[y-2][x+1] == null)
				{
					moves.add(new Move(this, 2, y, x, y-2, x+1));
				}
				else if (cb.board[y-2][x+1].white != white)
				{
					moves.add(new Move(this, 3, y, x, y-2, x+1));
				}
			}
			if (x - 1 >= 0)
			{
				if (cb.board[y-2][x-1] == null)
				{
					moves.add(new Move(this, 2, y, x, y-2, x-1));
				}
				else if (cb.board[y-2][x-1].white != white)
				{
					moves.add(new Move(this, 3, y, x, y-2, x-1));
				}
			}
		}
		if (x + 2 < cb.board.length)
		{
			if (y + 1 < cb.board.length)
			{
				if (cb.board[y+1][x+2] == null)
				{
					moves.add(new Move(this, 2, y, x, y+1, x+2));
				}
				else if (cb.board[y+1][x+2].white != white)
				{
					moves.add(new Move(this, 3, y, x, y+1, x+2));
				}
			}
			if (y - 1 >= 0)
			{
				if (cb.board[y-1][x+2] == null)
				{
					moves.add(new Move(this, 2, y, x, y-1, x+2));
				}
				else if (cb.board[y-1][x+2].white != white)
				{
					moves.add(new Move(this, 3, y, x, y-1, x+2));
				}
			}
		}
		if (x - 2 >= 0)
		{
			if (y + 1 < cb.board.length)
			{
				if (cb.board[y+1][x-2] == null)
				{
					moves.add(new Move(this, 2, y, x, y+1, x-2));
				}
				else if (cb.board[y+1][x-2].white != white)
				{
					moves.add(new Move(this, 3, y, x, y+1, x-2));
				}
			}
			if (y - 1 >= 0)
			{
				if (cb.board[y-1][x-2] == null)
				{
					moves.add(new Move(this, 2, y, x, y-1, x-2));
				}
				else if (cb.board[y-1][x-2].white != white)
				{
					moves.add(new Move(this, 3, y, x, y-1, x-2));
				}
			}
		}
		if (doCheck)
		{
			moves = checkCheck(cb, white, moves);
		}
		return moves;	
	}
}
