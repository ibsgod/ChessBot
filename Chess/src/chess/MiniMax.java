package chess;

import java.util.ArrayList;

public class MiniMax 
{
	boolean stale = false;
	int cc = 0;
	ChessBoard bestboard;
	Game g;
	int count = 0;
	ChessBoard cb;
	int initdepth = 3;
	public MiniMax(Game g) throws CloneNotSupportedException
	{
		this.g = g;
		cb = new ChessBoard(g, 8, 8, g.board);
	}
	public int evaluateBoard (ChessBoard board, int depth)
	{
		depth ++;
		count ++;
		int score = 0;
		for (int i = 0; i < board.board.length; i++)
		{
			for (int j = 0; j < board.board[i].length; j++)
			{
				int value = 0;
				if (board.board[i][j] != null)
				{
					String name = board.board[i][j].getClass().getName().substring(6);
					switch (name)
					{
					case "Pawn":
						value = 10;
						break;
					case "Bishop":
						value = 70;
						break;
					case "Knight":
						value = 70;
						break;
					case "Rook":
						value = 140;
						break;
					case "Queen":
						value = 250;
						break;
					case "King":
						value = 100000;
						break;
					}
					if (board.board[i][j].white)
					{
						score += value;
					}
					else
					{
						score -= value;
					}
				}
			}
		}
		score += board.whiteCastled * 125;
		score -= board.blackCastled * 125;
		if (board.canCastle(false) > 0)
		{
			//cc ++;
		}
		ChessBoard gboard = null;
		try {
			gboard = new ChessBoard (g, 8, 8, board);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (gboard.checkCheckandMate(false) >= 1)
		{
			score += 1;
			if (gboard.checkCheckandMate(false) == 2)
			{
				score += 100000 * depth;
			}
		}
		if (gboard.checkCheckandMate(true) >= 1)
		{
			score -= 1;
			if (gboard.checkCheckandMate(true) == 2)
			{
				score -= 100000 * depth;
			}
		}
		/*ArrayList<Move> wmoves = listPaths(board, true);
		ArrayList<Move> bmoves = listPaths(board, false);
		for (int i = 0; i < wmoves.size(); i++)
		{
			String kill = wmoves.get(i).canKill;
			int value;
			if (kill != null)
			{
				switch (kill)
				{
				case "Pawn":
					value = 10/2;
					break;
				case "Bishop":
					value = 70/2;
					break;
				case "Knight":
					value = 70/2;
					break;
				case "Rook":
					value = 140/2;
					break;
				case "Queen":
					value = 250/2;
					break;
				default:
					value = 0;
				}
				score += value;
			}
		}
		for (int i = 0; i < bmoves.size(); i++)
		{
			String kill = bmoves.get(i).canKill;
			int value;
			if (kill != null)
			{
				switch (kill)
				{
				case "Pawn":
					value = 10/2;
					break;
				case "Bishop":
					value = 70/2;
					break;
				case "Knight":
					value = 70/2;
					break;
				case "Rook":
					value = 140/2;
					break;
				case "Queen":
					value = 250/2;
					break;
				default:
					value = 0;
				}
				score -= value;
			}
		}*/
		return score;
	}
	
	public void makeMove() throws CloneNotSupportedException
	{
		boolean looped = false;
		cc = 0;
		cb = new ChessBoard(g, 8, 8, g.board);
		double countt = 0;
		count = 0;
		int min = Integer.MAX_VALUE;
		Move themove = null;
		//loop through game board
		ArrayList<ArrayList<Move>> moves = cb.getAllMoves(false, true);
		for (int z = 0; z < 2; z++)
		{
			for (int i = 0; i < moves.size(); i++)
			{
				for (int j = 0; j < moves.get(i).size(); j++)
				{
					if (moves.get(i).get(j).type == 2 && !looped || moves.get(i).get(j).type != 2 && looped)
					{
						continue;
					}
					cb.makeMove(moves.get(i).get(j));
					if (moves.get(i).get(j).type == 4)
					{
						cb.blackCastled = initdepth+1;
					}
					int mini = max(cb, Integer.MIN_VALUE, Integer.MAX_VALUE, initdepth);
					//save min value and move if lower than current min
					if (mini < min || (mini == min && Math.random() > 0.8))
					{
						min = mini;
						themove = moves.get(i).get(j);
					}
					//g.printBoard(cb.board);
					cb.undoMove();
				}
				countt ++;
				g.b.displayMessage(g.b.message + ".");
			}
			looped = true;
		}		
		//make minimum move 
		if (themove != null)
		{
			stale = false;
			themove.piece = g.board.board[themove.piece.y][themove.piece.x];
			g.board.makeMove(themove);
			g.paintBoard();
			//g.printBoard(g.board.board);
			//System.out.println("googa: " + evaluateBoard(g.board, 0));
			g.b.displayMessage(count + " outcomes analyzed");
			//System.out.println(min);
		}
		else
		{
			stale = true;
		}
		ChessBoard gboard = new ChessBoard (g, 8, 8, g.board);
		//System.out.println();
		//System.out.println(min);
		//System.out.println(g.board.wking.y + " " + g.board.wking.x + " " + g.board.bking.y + " " + g.board.bking.x);
		//g.printBoard(bestboard);
	}
	
	public int max (ChessBoard board, int alpha, int beta, int depth ) throws CloneNotSupportedException
	{
		boolean looped = false;
		board = new ChessBoard(g, 8, 8, board);
		int max = Integer.MIN_VALUE;
		if (depth == 0 || board.checkCheckandMate(true) == 2)
		{
			//System.out.println("DEPTH " + depth + "--------------------"  + evaluateBoard(gboard));
			//g.printBoard(gboard);
			return evaluateBoard(board, depth);
		}
		ArrayList<ArrayList<Move>> moves = board.getAllMoves(true, true);
		for (int z = 0; z < 2; z++)
		{
			for (int i = 0; i < moves.size(); i++)
			{
				for (int j = 0; j < moves.get(i).size(); j++)
				{
					if (moves.get(i).get(j).type == 2 && !looped || moves.get(i).get(j).type != 2 && looped)
					{
						continue;
					}
					board.makeMove(moves.get(i).get(j));
					if (moves.get(i).get(j).type == 4)
					{
						board.whiteCastled = depth;
					}
					int maxi = min(board, alpha, beta, depth-1);			
					//save max value if higher than current max
					if (maxi > max || (maxi == max && Math.random() > 0.8))
					{
						max = maxi;
						
						alpha = Math.max(alpha, max);
						if (beta <= alpha)
						{
							//bestboard = g.copyBoard(gboard, pieceY, pieceX, moveY, moveX);
							//System.out.println("DEPTH " + depth + "------------- maxSCORE " + max);
							//g.printBoard(g.copyBoard(gboard, moves.get(i).startY, moves.get(i).startX, moves.get(i).endY, moves.get(i).endX));
							board.undoMove();
							return max;
						}
					}
					//System.out.println("DEPTH " + depth + "-------------");
					//g.printBoard(board.board);
					board.undoMove();
					//g.printBoard(board.board);
				}
			}
			looped = true;
		}
		//System.out.println("DEPTH " + depth + "------------- maxSCORE " + max);
			//g.printBoard(g.copyBoard(gboard, pieceY, pieceX, moveY, moveX));
			//bestboard = g.copyBoard(gboard, pieceY, pieceX, moveY, moveX);
		return max;
	}
	public int min (ChessBoard board, int alpha, int beta, int depth ) throws CloneNotSupportedException
	{
		boolean looped = false;
		board = new ChessBoard(g, 8, 8, board);
		int min = Integer.MAX_VALUE;
		if (depth == 0 || board.checkCheckandMate(false) == 2)
		{
			//System.out.println("DEPTH " + depth + "--------------------"  + evaluateBoard(gboard));
			//g.printBoard(gboard);
			return evaluateBoard(board, depth);
		}
		ArrayList<ArrayList<Move>> moves = board.getAllMoves(false, true);
		for (int z = 0; z < 2; z++)
		{
			for (int i = 0; i < moves.size(); i++)
			{
				for (int j = 0; j < moves.get(i).size(); j++)
				{
					if (moves.get(i).get(j).type == 2 && !looped || moves.get(i).get(j).type != 2 && looped)
					{
						continue;
					}
					board.makeMove(moves.get(i).get(j));
					if (moves.get(i).get(j).type == 4)
					{
						board.blackCastled = depth;
					}
					int mini = max(board, alpha, beta, depth-1);	
				
					//save min value if higher than current min
					if (mini < min || (mini == min && Math.random() > 0.8))
					{
						min = mini;
						
						beta = Math.min(beta, min);
						if (beta <= alpha)
						{
							//System.out.println("DEPTH " + depth + "------------- minSCORE " + min);
							//g.printBoard(g.copyBoard(gboard, moves.get(i).startY, moves.get(i).startX, moves.get(i).endY, moves.get(i).endX));
							//bestboard = g.copyBoard(gboard, pieceY, pieceX, moveY, moveX);
							board.undoMove();
							return min;
						}
					}
					//System.out.println("DEPTH " + depth + "-------------");
					//g.printBoard(board.board);
					board.undoMove();
				}
			}
			looped = true;
		}
						
		//System.out.println("DEPTH " + depth + "------------- minSCORE " + min);
		//g.printBoard(g.copyBoard(gboard, pieceY, pieceX, moveY, moveX));
		//bestboard = g.copyBoard(gboard, pieceY, pieceX, moveY, moveX);
		return min;
	}
	

	
}
