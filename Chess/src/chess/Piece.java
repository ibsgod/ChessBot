package chess;

import java.util.ArrayList;

public abstract class Piece implements Cloneable 
{
	int x;
	int y;
	ChessBoard b;
	boolean white;
	String img;
	boolean isKing;
	public Piece(ChessBoard b, int y, int x, boolean white, boolean isKing)
	{
		this.x = x;
		this.y = y;
		this.b = b;
		this.white = white;
		this.isKing = isKing;
		if (white)
		{
			b.wpieces.add(this);
		}
		else
		{
			b.bpieces.add(this);
		}
		
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {

	    return super.clone();
	    
	}
	public abstract ArrayList<Move> getMoves(int y, int x, boolean doCheck, ChessBoard cb);
	public ArrayList<Move> checkCheck (ChessBoard cb, boolean white, ArrayList<Move> moves)
	{
		//System.out.println(moves.size());
		ChessBoard board = null;
		try 
		{
			board = new ChessBoard(b.g, 8, 8, cb);
		} 
		catch (CloneNotSupportedException e) {}
		for (int i = 0; i < moves.size(); i++)
		{	
			Move newMove = new Move (moves.get(i).piece, moves.get(i).type, moves.get(i).startY, moves.get(i).startX, moves.get(i).endY, moves.get(i).endX);
			newMove.first = moves.get(i).first;
			//System.out.println(moves.get(i).piece.y + " " + moves.get(i).piece.x + " GAY");
			//System.out.println(newMove.piece + " GAY");
			//if (newMove.piece == null)
			//{
				//System.out.println(newMove.piece + " " + moves.get(0).piece + " GAYYYY");
			//}
			//System.out.println("GAYYYY " + newMove.piece + " " + moves.get(i).piece.white + " " + moves.get(i).piece + " " + moves.get(i).piece.y + " " + moves.get(i).piece.x + " " + newMove.endY + " " + newMove.endX);
			//b.g.printBoard(board.board);
			if (/*board.board[moves.get(i).startY][moves.get(i).startX] == null ||*/ moves.get(i).type == 4 && ((moves.get(i).piece.white && (moves.get(i).startY != 7 || moves.get(i).startX != 4)) || (!moves.get(i).piece.white && (moves.get(i).startY != 0 || moves.get(i).startX != 4))))
			{
				//System.out.println("gogoog " + moves.get(i).startY + " " + moves.get(i).startX);
				moves.remove(i);
				i--;
				continue;
			}
			board.makeMove(newMove);
			ArrayList<ArrayList<Move>> enemyMoves = board.getAllMoves(!white, false);
			
			//check if enemy pieces are attacking the king
			outer : for (int j = 0; j < enemyMoves.size(); j++)
			{
				for (int k = 0; k < enemyMoves.get(j).size(); k++)
				{
					Move move = enemyMoves.get(j).get(k);
					//System.out.println("Checking: " + moves.get(i).piece.getClass().getName() + " " + moves.get(i).endY + " " + moves.get(i).endX);
	
					if (move.type == 3 && board.board[move.endY][move.endX].isKing && board.board[move.endY][move.endX].white == white)
					{
						//System.out.println("Removed: " + moves.get(i).piece.getClass().getName() + " " + moves.get(i).endY + " " + moves.get(i).endX);
						moves.remove(i);
						i --;
						break outer;
					}
				}
			}
			try {
				board.undoMove();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//b.g.printBoard(board.board);
			//System.out.println(b.g.selectedPiece.y + " " + b.g.selectedPiece.x + " check");
		}
		return moves;
	}
	
}
