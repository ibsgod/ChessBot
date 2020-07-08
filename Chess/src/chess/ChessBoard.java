package chess;
import java.util.Scanner;
import java.util.ArrayList;

public class ChessBoard 
{
	public Piece[][] board;
	public ArrayList<Piece> wpieces = new ArrayList<Piece>();
	public ArrayList<Piece> bpieces = new ArrayList<Piece>();;
	King wking;
	King bking;
	Game g;
	public Move lastMove;
	public Piece killedPiece;
	public Piece promoted;
	public int whiteCastled = 0;
	public int blackCastled = 0;
	public ChessBoard(Game g, int height, int width)
	{
		this.g = g;
		board = new Piece[height][width];
		/*Scanner scanner = new Scanner(System.in);
		if (scanner.next().equals("load"))
		{
			for (int i = 0; i < board.length; i++)
			{
				for (int j = 0; j < board[i].length; j++)
				{
					String a = scanner.next();
					boolean white;
					if (!a.equals("___"))
					{
						if (a.charAt(0) == 'w')
						{
							white = true;
						}
						else
						{
							white = false;
						}
						switch (a.substring(1))
						{
							case "Pa":
								board[i][j] = new Pawn (this, i, j, white, false);
								break;
							case "Bi":
								board[i][j] = new Bishop (this, i, j, white, false);
								break;
							case "Kn":
								board[i][j] = new Knight (this, i, j, white, false);
								break;
							case "Ro":
								board[i][j] = new Rook (this, i, j, white, false);
								break;
							case "Qu":
								board[i][j] = new Queen (this, i, j, white, false);
								break;
							case "Ki":
								board[i][j] = new King (this, i, j, white, true);
								if (white)
								{
									wking = (King)board[i][j];
								}
								else
								{
									bking = (King)board[i][j];
								}
								break;
						}
					}
				}
			}
			return;
		}*/
		for (int i = 0; i < 8; i++)
		{
			board[1][i] = new Pawn (this, 1, i, false, false);
			board[6][i] = new Pawn (this, 6, i, true, false);
		}
		board[0][0] = new Rook (this, 0, 0, false, false);
		board[0][7] = new Rook (this, 0, 7, false, false);
		board[7][0] = new Rook (this, 7, 0, true, false);
		board[7][7] = new Rook (this, 7, 7, true, false);
		board[0][1] = new Knight (this, 0, 1, false, false);
		board[0][6] = new Knight (this, 0, 6, false, false);
		board[7][1] = new Knight (this, 7, 1, true, false);
		board[7][6] = new Knight (this, 7, 6, true, false);
		board[0][2] = new Bishop (this, 0, 2, false, false);
		board[0][5] = new Bishop (this, 0, 5, false, false);
		board[7][2] = new Bishop (this, 7, 2, true, false);
		board[7][5] = new Bishop (this, 7, 5, true, false);
		board[0][3] = new Queen (this, 0, 3, false, false);
		board[7][3] = new Queen (this, 7, 3, true, false);
		board[0][4] = new King (this, 0, 4, false, true);
		board[7][4] = new King (this, 7, 4, true, true);
		bking = (King)board[0][4];
		wking = (King)board[7][4];
		
	}
	public ChessBoard(Game g, int height, int width, ChessBoard cb) throws CloneNotSupportedException
	{
		//System.out.println(lastMove + " " + killedPiece);
		this. g = g;
		this.board = g.copyBoard(cb.board);
		wpieces = (ArrayList<Piece>) cb.wpieces.clone();
		for (int i = 0; i < wpieces.size(); i++)
		{
			//System.out.print(wpieces.get(i).getClass().getName().substring(6) + "(" + wpieces.get(i).y + " " + wpieces.get(i).x + ") ");
		}
		//System.out.println();
		bpieces = (ArrayList<Piece>) cb.bpieces.clone();
		for (int i = 0; i < bpieces.size(); i++)
		{
			//System.out.print(bpieces.get(i).getClass().getName().substring(6) + "(" + bpieces.get(i).y + " " + bpieces.get(i).x + ") ");
		}
		//System.out.println();
		wking = (King) cb.wking.clone();
		bking = (King) cb.bking.clone();
		blackCastled = cb.blackCastled;
		whiteCastled = cb.whiteCastled;
	}
	public ArrayList<ArrayList<Move>> getAllMoves (boolean white, boolean doCheck)
	{
		ArrayList<ArrayList<Move>> allmoves = new ArrayList<ArrayList<Move>>();
		if (white)
		{
			//g.printBoard(board);
			for (int i = 0; i < wpieces.size(); i++)
			{
				//System.out.println(i);
				//System.out.println(wpieces.get(i).getClass().getName().substring(6) + ": " + wpieces.get(i).y + " " + wpieces.get(i).x);
				//CHECK IF KILLEDPICE 
				allmoves.add(wpieces.get(i).getMoves(wpieces.get(i).y, wpieces.get(i).x, doCheck, this));
			}
		}
		else
		{
			for (int i = 0; i < bpieces.size(); i++)
			{ 
				//System.out.println(i);
				//System.out.println(bpieces.get(i).getClass().getName().substring(6) + ": " + bpieces.get(i).y + " " + bpieces.get(i).x);
				//System.out.println(bpieces.get(i));
				//System.out.println(bpieces.get(i).getMoves(bpieces.get(i).y, bpieces.get(i).x, doCheck, this));
				allmoves.add(bpieces.get(i).getMoves(bpieces.get(i).y, bpieces.get(i).x, doCheck, this));
			}
		}
		//System.out.println("GETALLMOVES");
		//g.printBoard(board);
		return allmoves;
	}
	public void makeMove(Move move)
	{
		//System.out.println(move.piece + " girth");
		//System.out.println(this + " " + "MOVED: " + move.piece.getClass().getName().substring(6) + " " + move.startY + " " + move.startX + " " + move.endY + " " + move.endX);
		try
		{
			killedPiece = null;
			if (move.type == 3 || move.type == 6)
			{
				if (!move.piece.white)
				{
					killedPiece = board[move.endY][move.endX];
					//System.out.println(killedPiece.white + " " + killedPiece.getClass().getName().substring(6) + " " + killedPiece.y + " " + killedPiece.x + " wded");
					for (int i = 0; i < wpieces.size(); i++)
					{
						if (wpieces.get(i).y == move.endY && wpieces.get(i).x == move.endX)
						{
							//System.out.println(wpieces.size());
							wpieces.remove(i);
							//System.out.println(wpieces.size());
							break;
						}
					}
				}
				else
				{
					killedPiece = board[move.endY][move.endX];
					//System.out.println(killedPiece.white + " " + killedPiece.getClass().getName().substring(6)+ " " + killedPiece.y + " " + killedPiece.x + " bded");
					for (int i = 0; i < bpieces.size(); i++)
					{
						if (bpieces.get(i).y == move.endY && bpieces.get(i).x == move.endX)
						{
							//System.out.println(bpieces.size());
							bpieces.remove(i);
							//System.out.println(bpieces.size());
							break;
						}
					}
				}
			}
			if (killedPiece != null && killedPiece.getClass() == King.class)
			{
				//g.b.displayMessage("congratz u broke my game");
			}
			if (move.type == 4)
			{
				//System.out.println("HOMO");
				//g.printBoard(board);
				if (move.endX == 2)
				{
					board[move.piece.y][3] = board[move.piece.y][0];
					board[move.piece.y][0] = null;
					((Rook)board[move.piece.y][3]).first = false;
					board[move.piece.y][3].y = move.piece.y;
					board[move.piece.y][3].x = 3;
					board[move.piece.y][2] = (Piece) board[move.piece.y][4].clone();
					board[move.piece.y][4] = null;
					((King)board[move.piece.y][2]).first = false;
					board[move.piece.y][2].y = move.piece.y;
					board[move.piece.y][2].x = 2;
					move.piece.x = 2;
				}
				else 
				{
					board[move.piece.y][5] = board[move.piece.y][7];
					board[move.piece.y][7] = null;
					((Rook)board[move.piece.y][5]).first = false;
					board[move.piece.y][5].y = move.piece.y;
					board[move.piece.y][5].x = 5;
					board[move.piece.y][6] = (Piece) board[move.piece.y][4].clone();
					board[move.piece.y][4] = null;
					((King)board[move.piece.y][6]).first = false;
					board[move.piece.y][6].y = move.piece.y;
					board[move.piece.y][6].x = 6;
					move.piece.x = 6;
					
				}
				//System.out.println("HOMOD");
				//g.printBoard(board);
			}
			if (move.type <= 4)
			{
				//System.out.println(move.piece + " girthy");
				board[move.endY][move.endX] = move.piece;
				board[move.startY][move.startX] = null;
				move.piece.y = move.endY;
				move.piece.x = move.endX;
			}
			else if (move.type > 4)
			{
				if (board[move.startY][move.startX].white)
				{
					for (int i = 0; i < wpieces.size(); i++)
					{
						if (wpieces.get(i).y == move.startY && wpieces.get(i).x == move.startX)
						{
							wpieces.remove(i);
							break;
						}
					}
				}
				else
				{
					for (int i = 0; i < bpieces.size(); i++)
					{
						if (bpieces.get(i).y == move.startY && bpieces.get(i).x == move.startX)
						{
							bpieces.remove(i);
							break;
						}
					}
				}	
				Queen newq = new Queen (this, move.endY, move.endX, board[move.startY][move.startX].white, false);
				promoted = (Piece) board[move.startY][move.startX];
				board[move.startY][move.startX] = null;
				board[move.endY][move.endX] = newq;
			}
			if (move.piece.getClass() == Pawn.class)
			{
				((Pawn)move.piece).first = false;
				//System.out.println(move.piece + " pee " + move.piece.y + " " + move.piece.x);
			}
			if (move.piece.getClass() == King.class)
			{
				((King)move.piece).first = false;
			}
			if (move.piece.getClass() == Rook.class)
			{
				((Rook)move.piece).first = false;
			}
		}
		catch(Exception e)
		{
			System.out.println("GAY" + move.startY + " " + move.startX + " " + move.endY + " " + move.endX);
			g.printPieces(this);
			g.printBoard(board);
			e.printStackTrace();
			g.b.displayMessage("congratz u broke my game");
			g.thread.stop();
			//System.exit(0);
		}
		lastMove = move;
		//MAKE IT SO THAT THE GAME CONSTANTLY UPDATES THE MAIN BOARD
		//MAKE THE CHECKCHECK WORK
	}
	public int checkCheckandMate (boolean white)
	{
		//g.printBoard(board);
		int check = 0;
		ArrayList<ArrayList<Move>> enemyMoves = getAllMoves(!white, false);
		//check if enemy pieces are attacking the king
		for (int i = 0; i < enemyMoves.size(); i++)
		{
			for (int j = 0; j < enemyMoves.get(i).size(); j++)
			{
				Move move = enemyMoves.get(i).get(j);
				if (move.type == 3 && board[move.endY][move.endX].isKing && board[move.endY][move.endX].white == white)
				{
					check = 1;
				}
			}
		}
		//if king is not in check, return 0 for no check
		if (check == 0)
		{
			if (white)
			{
				wking.inCheck = false;
			}
			else
			{
				bking.inCheck = false;
			}
			return 0;
		}
		//if check, check for checkmate
		else 
		{
			if (white)
			{
				wking.inCheck = true;
			}
			else
			{
				bking.inCheck = true;
			}
			//check if move can be made to get out of check
			ArrayList<ArrayList<Move>> myMoves = getAllMoves(white, true);
			for (int i = 0; i < myMoves.size(); i++)
			{
				if (myMoves.get(i).size() > 0)
				{
					//System.out.println(myMoves.get(i).get(0).piece.getClass().getName() + " " + myMoves.get(i).get(0).endY + " " + myMoves.get(i).get(0).endX);
					return 1;
				}
			}
			//if not returned yet, it must be checkmate
			return 2;
		}
	}
	
	public void undoMove() throws CloneNotSupportedException
	{
		try
		{
		if (lastMove != null)
		{
			if (lastMove.first == true)
			{
				//System.out.println(lastMove.piece + " poo " +  lastMove.piece.y + " " + lastMove.piece.x);
				if (lastMove.piece.getClass() == Pawn.class)
				{
					((Pawn)lastMove.piece).first = true;
					((Pawn)board[lastMove.endY][lastMove.endX]).first = true;
				}
				if (lastMove.piece.getClass() == King.class)
				{
					((King)lastMove.piece).first = true;
					((King)board[lastMove.endY][lastMove.endX]).first = true;
				}
				if (lastMove.piece.getClass() == Rook.class)
				{
					((Rook)lastMove.piece).first = true;
					((Rook)board[lastMove.endY][lastMove.endX]).first = true;
				}
			}
			else
			{
				//System.out.println(lastMove.piece + " poopy " +  lastMove.piece.y + " " + lastMove.piece.x);
			}
			if (killedPiece != null && lastMove.piece.white == killedPiece.white)
			{
				//System.out.println("TRUEEEeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeEEEEE " + killedPiece.getClass().getName().substring(6) + " " + killedPiece.y + " " + killedPiece.x+ " " + lastMove.piece.getClass().getName().substring(6) + lastMove.startY + " " + lastMove.startX);
			}
			//System.out.println(this + " " + "UNDO: " + board[lastMove.endY][lastMove.endX].getClass().getName().substring(6) + " " + lastMove.startY + " " + lastMove.startX + " " + lastMove.endY + " " + lastMove.endX);
			if (lastMove.type >= 5)
			{
				if (lastMove.piece.white)
				{
					for (int i = 0; i < wpieces.size(); i++)
					{
						if (wpieces.get(i).y == lastMove.endY && wpieces.get(i).x == lastMove.endX)
						{
							wpieces.remove(i);
							break;
						}
					}
					wpieces.add(promoted);
					if (lastMove.type == 6)
					{
						bpieces.add(killedPiece);
					}
					
				}
				else
				{
					for (int i = 0; i < wpieces.size(); i++)
					{
						if (bpieces.get(i).y == lastMove.endY && bpieces.get(i).x == lastMove.endX)
						{
							bpieces.remove(i);
							break;
						}
					}
					bpieces.add(promoted);
					if (lastMove.type == 6)
					{
						wpieces.add(killedPiece);
					}
					
				}
				if (lastMove.type == 6)
				{
					board[lastMove.endY][lastMove.endX] = killedPiece;
					killedPiece.y = lastMove.endY;
					killedPiece.x = lastMove.endX;
				}
				board[lastMove.startY][lastMove.startX] = promoted;
				
			}
			if (lastMove.type < 4)
			{
				board[lastMove.startY][lastMove.startX] = board[lastMove.endY][lastMove.endX];
				board[lastMove.startY][lastMove.startX].y = lastMove.startY;
				board[lastMove.startY][lastMove.startX].x = lastMove.startX;
				board[lastMove.endY][lastMove.endX] = null;
			}
			if (lastMove.type == 3 && lastMove.piece.white)
			{
				bpieces.add(killedPiece);
				board[lastMove.endY][lastMove.endX] = killedPiece;
				killedPiece.y = lastMove.endY;
				killedPiece.x = lastMove.endX;
				//System.out.println("brevived " + killedPiece.white + " " + killedPiece.getClass().getName().substring(6) + " " + killedPiece.y + " " + killedPiece.x);
			}
			else if (lastMove.type == 3)
			{
				wpieces.add(killedPiece);
				board[lastMove.endY][lastMove.endX] = killedPiece;
				killedPiece.y = lastMove.endY;
				killedPiece.x = lastMove.endX;
				//System.out.println("wrevived " + killedPiece.white + " " + killedPiece.getClass().getName().substring(6) + " " + killedPiece.y + " " + killedPiece.x);
			}
			else if (lastMove.type == 4)
			{
				lastMove.piece.x = 4;
				board[lastMove.endY][lastMove.endX] = null;
				board[lastMove.endY][4] = new King (this, lastMove.endY, 4, lastMove.piece.white, true);
				
				if (lastMove.endX == 2)
				{
					//board[lastMove.endY][3].x= 3;
					board[lastMove.endY][0] = new Rook (this, lastMove.endY, 0, lastMove.piece.white, false);
					board[lastMove.endY][3] = null;
				}
				else 
				{
					//board[lastMove.endY][5].x = 5;
					board[lastMove.endY][7] = new Rook (this, lastMove.endY, 7, lastMove.piece.white, false);
					board[lastMove.endY][5] = null;
					
				}//System.out.println("LESBIAN");
				//g.printBoard(board);
			}
		}
		lastMove = null;
		killedPiece = null;
		promoted = null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			g.printBoard(board);
		}
	}
	public int canCastle (boolean white)
	{
		int a = 0;
		int y = 0;
		King k = bking;
		if (white)
		{
			y = 7;
			k = wking;
			if (whiteCastled > 0)
			{
				return 0;
			}
		}
		else if (blackCastled > 0)
		{
			return 0;
		}
		boolean l1 = true;
		boolean l2 = true; 
		boolean r1 = true;
		boolean r2 = true;
		ArrayList<ArrayList<Move>> enemyMoves = getAllMoves(!white, false);
		for (int i = 0; i < enemyMoves.size(); i++)
		{
			 for (int j = 0; j < enemyMoves.get(i).size(); j++)
			 {
				 if (enemyMoves.get(i).get(j).endY == y && enemyMoves.get(i).get(j).endX == 3 || board[y][3] != null)
				 {
					 l1 = false;
				 }
				 if (enemyMoves.get(i).get(j).endY == y && enemyMoves.get(i).get(j).endX == 2 || board[y][2] != null || board[y][1] != null)
				 {
					 l2 = false;
				 }
				 if (enemyMoves.get(i).get(j).endY == y && enemyMoves.get(i).get(j).endX == 5 || board[y][5] != null)
				 {
					 r1 = false;
				 }
				 if (enemyMoves.get(i).get(j).endY == y && enemyMoves.get(i).get(j).endX == 6 || board[y][6] != null)
				 {
					 r2 = false;
				 }
			 }
		}
		
		try
		{
		if (l1 && l2 && k.first && !k.inCheck && board[y][0] != null && board[y][0].getClass()== Rook.class && ((Rook)board[y][0]).first && k.y == y && k.x == 4)
		{
			a += 1;
		}
		if (r1 && r2 && k.first && !k.inCheck && board[y][7] != null && board[y][7].getClass()== Rook.class && ((Rook)board[y][7]).first && k.y == y && k.x == 4)
		{
			a += 2;
		}
		}
		catch(Exception e)
		{
			//g.b.displayMessage("congratz u broke my game");
		}
		return a;
	}
}
