package chess;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Game extends JFrame {

	Board b;
	ChessBoard board;
	int[][] displays;
	public boolean selecting;
	public Piece selectedPiece;
	public MiniMax m;
	public myThread thread = null;
	public boolean over = false;
	double wseconds = 180;
	double bseconds = 180;
	boolean wstarted;
	boolean bstarted;
	boolean turn = true;
	int timelose = 0;
	public myThread2 timerThread = null;
	
	/**
	 * Create the frame.
	 * @throws CloneNotSupportedException 
	 */
	public Game() throws CloneNotSupportedException 
	{
		b = new Board(8, 8);
		b.wtime = wseconds;
		b.btime = bseconds;
		play();
	}
	public void reset() throws CloneNotSupportedException
	{
		turn = true;
		wstarted = false;
		bstarted = false;
		wseconds = 180;
		bseconds = 180;
		thread.thinking = false;
		thread.stop();
		timerThread.stop();
		over = false;
		play();
	}
	public void checkeroo()
	{
		if (timelose == 1)
		{
			b.displayMessage("you 2 slow n00b");
			over = true;
		}
		else if (timelose == 2)
		{
			b.displayMessage("gg java is slow and dumb not my fault");
			over = true;
		}
		if (board.checkCheckandMate(true) > 0) 
		{
			board.wking.inCheck = true;
			b.displayMessage("white in check");
			board.wking.img = "wkingo";
			paintBoard();
			try 
			  { 
				  URL sound = getClass().getResource("oof.wav");
				  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound); 
				  Clip clip = AudioSystem.getClip(); 
				  clip.open(audioInputStream);
				  clip.start(); 
			  } 
			  catch (Exception e) 
			  {
				  e.printStackTrace(); 
			  }
			if (board.checkCheckandMate(true) > 1) 
			{
				b.displayMessage("white in checkmate");
				over = true;
			}
		} 
		else 
		{
			board.wking.inCheck = false;
			board.wking.img = "wking";
			paintBoard();
			int d = 0;
			ArrayList<ArrayList<Move>> moves = board.getAllMoves(true, true);
			for (int i = 0; i < moves.size(); i++)
			{
				for (int j = 0; j < moves.get(i).size(); j++)
				{
					d ++;
				}
			}
			if (d == 0)
			{
				thread.stop();
				b.displayMessage("stalemate");
				over = true;
			}
		}
		if (board.checkCheckandMate(false) > 0) 
		{
			board.bking.inCheck = true;
			board.bking.img = "bkingo";
			paintBoard();
			try 
			  { 
				  URL sound = getClass().getResource("oof.wav");
				  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound); 
				  Clip clip = AudioSystem.getClip(); 
				  clip.open(audioInputStream);
				  clip.start(); 
			  } 
			  catch (Exception e) 
			  {
				  e.printStackTrace(); 
			  }
			b.displayMessage("black in check");
			if (board.checkCheckandMate(false) > 1) 
			{
				b.displayMessage("black in checkmate");
				thread.stop();
				
				over = true;
			}
		} 
		else 
		{
			board.bking.inCheck = false;
			board.bking.img = "bking";
			paintBoard();
			if (m.stale)
			{
				thread.stop();
				b.displayMessage("stalemate");
				over = true;
			}
		}
	}
	public void mouseClicked(int y, int x, boolean dragged) 
	{
		if (over || thread != null && thread.thinking) 
		{
			return;
		}
		// printBoard(board);
		if (!selecting && board.board[y][x] != null && board.board[y][x].white) 
		{
			clearDisplays();
			selecting = true;
			selectedPiece = board.board[y][x];
			showDisplay();
			b.repaint();
		} 
		else if (selectedPiece != null && board.board[y][x] != null && board.board[y][x].white == selectedPiece.white) 
		{
			selecting = false;
			mouseClicked(y, x, true);
		} 
		else if (selectedPiece != null && displays[y][x] > 1 && !dragged) 
		{
			// printBoard(board.board);
			selecting = false;
			ArrayList<Move> moves = selectedPiece.getMoves(selectedPiece.y, selectedPiece.x, true, board);
			for (int i = 0; i < moves.size(); i++) 
			{
				// System.out.println(y + " " + x + " " + moves.get(i).endY + "
				// " + moves.get(i).endX);
				if (y == moves.get(i).endY && x == moves.get(i).endX) 
				{
					board.makeMove(moves.get(i));
					wstarted = true;
					turn = !turn;
					try 
					  { 
							  URL sound = getClass().getResource("wood1.wav");
							  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound); 
							  Clip clip = AudioSystem.getClip(); 
							  clip.open(audioInputStream);
							  clip.start(); 
					  } 
					  catch (Exception e) 
					  {
						  e.printStackTrace(); 
					  }
					checkeroo();
					break;
					
					//printPieces(board);
				}
			}
			selectedPiece = null;
			// printBoard(board.board);
			checkeroo();
			//System.out.println();
			paintBoard();
			clearDisplays();
			thread = new myThread();
			thread.g = this;
			thread.start();

			 b.displayMessage("Thinking");
			// System.out.println("scorre: " + m.evaluateBoard(board));
		}

		// System.out.println(y + " " + x + " ");
		/*
		 * System.out.print("Black remaining: "); for (int i = 0; i <
		 * board.bpieces.size(); i++) {
		 * System.out.print(board.bpieces.get(i).getClass().getName().substring(
		 * 6) + " "); } System.out.println();
		 */
	}

	public void clearDisplays() {
		for (int i = 0; i < board.board.length; i++) {
			for (int j = 0; j < board.board[i].length; j++) {
				displays[i][j] = 0;
			}
		}
	}

	public void play() throws CloneNotSupportedException 
	{
		board = null;
		displays = null;
		m = null;
		board = new ChessBoard(this, b.rows, b.columns);
		displays = new int[b.rows][b.columns];
		// printBoard(board.board);
		paintBoard();
		m = new MiniMax (this);
		timerThread = new myThread2();
		timerThread.g = this;
		timerThread.start();
	}

	public void printBoard(Piece[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != null) {
					System.out.print(
							(board[i][j].white ? "w" : "b") + board[i][j].getClass().getName().substring(6, 8) + " ");
				} else {
					System.out.print("___ ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public Piece[][] copyBoard(Piece[][] board) throws CloneNotSupportedException {
		{
			Piece[][] boardy = new Piece[board.length][board[0].length];
			for (int s = 0; s < board.length; s++) {
				for (int t = 0; t < board[0].length; t++) {
					if (board[s][t] != null) {
						boardy[s][t] = (Piece) board[s][t].clone();
					}
				}
			}
			return boardy;
		}
	}

	public Piece[][] copyBoard(Piece[][] board, Move move) throws CloneNotSupportedException {
		Piece[][] boardy = new Piece[board.length][board[0].length];
		for (int s = 0; s < board.length; s++) {
			for (int t = 0; t < board[0].length; t++) {
				if (board[s][t] != null) {
					boardy[s][t] = (Piece) board[s][t].clone();
				}
			}
		}
		boardy[move.endY][move.endX] = (Piece) boardy[move.startY][move.startX].clone();
		boardy[move.startY][move.startX] = null;
		// g.printBoard(board);
		return boardy;
	}

	public void showDisplay() {
		// System.out.println(selectedPiece);
		displays[selectedPiece.y][selectedPiece.x] = 1;
		ArrayList<Move> moves = selectedPiece.getMoves(selectedPiece.y, selectedPiece.x, true, board);
		for (int i = 0; i < moves.size(); i++) {
			displays[moves.get(i).endY][moves.get(i).endX] = moves.get(i).type;
		}
	}

	public void paintBoard() {
		for (int i = 0; i < board.board.length; i++) {
			for (int j = 0; j < board.board[i].length; j++) {
				if (board.board[i][j] != null) {
					b.putPeg(board.board[i][j].img, i, j);
				} else {
					b.removePeg(i, j);
				}
				b.repaint();
			}
		}
	}
	
	public void printPieces(ChessBoard cb)
	{
		for (int i1 = 0; i1 < cb.wpieces.size(); i1++)
		{
			System.out.print(cb.wpieces.get(i1).getClass().getName().substring(6) + "(" + cb.wpieces.get(i1).y + " " + cb.wpieces.get(i1).x + ") ");
		}
		System.out.println();
		
		for (int i1 = 0; i1 < cb.bpieces.size(); i1++)
		{
			System.out.print(cb.bpieces.get(i1).getClass().getName().substring(6) + "(" + cb.bpieces.get(i1).y + " " + cb.bpieces.get(i1).x + ") ");
		}
		System.out.println();
	}

}
