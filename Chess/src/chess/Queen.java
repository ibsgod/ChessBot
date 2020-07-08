package chess;

import java.util.ArrayList;

public class Queen extends Piece
{
	public Queen(ChessBoard g, int y, int x, boolean white, boolean isKing)
	{
		super (g, y, x, white, isKing);
		img = white ? "wqueen" : "bqueen";
	}
	public ArrayList<Move> getMoves(int y, int x, boolean doCheck, ChessBoard cb) 
	{
		ArrayList<Move> moves = new ArrayList<Move>();
		int i = 1;
		while (x + i < cb.board.length)
		{
			if (cb.board[y][x+i] == null)
			{
				moves.add(new Move(this, 2, y, x, y, x+i));
			}
			else if (cb.board[y][x+i].white != white)
			{
				moves.add(new Move(this, 3, y, x, y, x+i));
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
				moves.add(new Move(this, 2, y, x, y, x-i));
			}
			else if (cb.board[y][x-i].white != white)
			{
				moves.add(new Move(this, 3, y, x, y, x-i));;
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
				moves.add(new Move(this, 2, y, x, y+i, x));
			}
			else if (cb.board[y+i][x].white != white)
			{
				moves.add(new Move(this, 3, y, x, y+i, x));
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
				moves.add(new Move(this, 2, y, x, y-i, x));
			}
			else if (cb.board[y-i][x].white != white)
			{
				moves.add(new Move(this, 3, y, x, y-i, x));
				break;
			}
			else
			{
				break;
			}
			i ++;
		}
		i = 1;
		while (Math.max(y, x) + i < cb.board.length)
		{
			if (cb.board[y+i][x+i] == null)
			{
				moves.add(new Move(this, 2, y, x, y+i, x+i));
			}
			else if (cb.board[y+i][x+i].white != white)
			{
				moves.add(new Move(this, 3, y, x, y+i, x+i));
				break;
			}
			else
			{
				break;
			}
			i ++;
		}
		i = 1;
		while (Math.min(y, x) - i >= 0)
		{
			if (cb.board[y-i][x-i] == null)
			{
				moves.add(new Move(this, 2, y, x, y-i, x-i));
			}
			else if (cb.board[y-i][x-i].white != white)
			{
				moves.add(new Move(this, 3, y, x, y-i, x-i));
				break;
			}
			else
			{
				break;
			}
			i ++;
		}
		i = 1;
		while (x - i >= 0 && y + i < cb.board.length)
		{
			if (cb.board[y+i][x-i] == null)
			{
				moves.add(new Move(this, 2, y, x, y+i, x-i));
			}
			else if (cb.board[y+i][x-i].white != white)
			{
				moves.add(new Move(this, 3, y, x, y+i, x-i));
				break;
			}
			else
			{
				break;
			}
			i ++;
		}
		i = 1;
		while (y - i >= 0 && x + i < cb.board.length)
		{
			if (cb.board[y-i][x+i] == null)
			{
				moves.add(new Move(this, 2, y, x, y-i, x+i));
			}
			else if (cb.board[y-i][x+i].white != white)
			{
				moves.add(new Move(this, 3, y, x, y-i, x+i));
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