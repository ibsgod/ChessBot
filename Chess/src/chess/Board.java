package chess;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputAdapter;

/**  Board GUI for implementation with various games	
 *   Author: Kirill Levin, Troy Vasiga, Chris Ingram
 */

public class Board extends JPanel
{
	  public static JFrame f;
	  private static final int X_DIM = 60;
	  private static final int Y_DIM = 60;
	  private static final int X_OFFSET = 30;
	  private static final int Y_OFFSET = 30;
	  private static final double MIN_SCALE = 0.25;
	  private static final int GAP = 10;
	  private static final int FONT_SIZE = 16;
	  //JLabel wtime;
	  //JLabel btime;
	  JLabel lblDifficultyy;
	  JTextField lblDifficulty;
	  JButton retry;
	   
	  // String used to indicate each image
	  private static final String[] PIECE_NAMES = {"wpawn", "bpawn", "wbishop", "bbishop", "wknight", "bknight", "wrook", "brook", "wqueen", "bqueen", "wking", "bking", "wkingo", "bkingo"};
	  private static final ImageIcon[] PIECES = new ImageIcon[PIECE_NAMES.length];  
  // Grid colours
	  private static final Color GRID_COLOR_A = new Color(84,137,139);
	  private static final Color GRID_COLOR_B = new Color(103,156,158);
	  public Game g;
	  
	  // Colour to use if a match is not found
	  
	  public ImageIcon[][] grid;
	  public Coordinate lastClick;  // How the mouse handling thread communicates 
	                                 // to the board where the last click occurred
	  public String message = "";
	  public double wtime;
	  public double btime;
	  private int numLines = 0;
	  private int[][] line = new int[4][100];  // maximum number of lines is 100
	  int columns;
	  int rows;
	  
	  private boolean first = true;
	  private int originalWidth;
	  private int originalHeight;
	  int wid = 540;
	  int hei = 260;
	  private double scale;
	  public static Rectangle[][] boxes;

	  
	  /** A constructor to build a 2D board.
	   */
	  public Board (int rows, int cols)
	  {
	    super( true );
	    f = new JFrame( "Board game" );
	    this.columns = cols;
	    this.rows = rows;
	    originalWidth = 2*X_OFFSET+X_DIM*cols;
	    originalHeight = 2*Y_OFFSET+Y_DIM*rows+GAP+FONT_SIZE;
	    this.setPreferredSize( new Dimension( originalWidth + 140, originalHeight) );
	    f.setResizable(false);
	  	setFocusable(true);
	  	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setContentPane( this );
	    f.pack();
	    f.setVisible(true);
	    this.grid = new ImageIcon [cols][rows];
	    boxes = new Rectangle[rows][columns];
	    this.addMouseListener(
	    		new MouseInputAdapter()
	    		{
	    			public void mouseClicked(MouseEvent e)
	    			{
	    				select(e, false);
	    			}
	    		}
	    		);
	    this.addMouseMotionListener(
	      new MouseInputAdapter() 
	      {
	        /** A method that is called when the mouse is clicked
	         */
	        public void mouseDragged(MouseEvent e) 
	        { 
	        	select(e, true);
	        } 
	      } 
	    );

	    this.grid = new ImageIcon[cols][rows];
	    
		
	    // PUT ALL OF THE IMAGES OF THE PIECES INTO AN ARRAY ===========================
	    for (int i = 0; i < PIECE_NAMES.length; i++)
	    {
	    	PIECES [i] = new ImageIcon(getClass().getResource(PIECE_NAMES[i] + ".png"));
	    }
	    lblDifficulty = new JTextField("3");
	    lblDifficulty.addKeyListener(new KeyAdapter() {
	    	@Override
	    	public void keyReleased(KeyEvent arg0) {
	    		try {
					g.m.initdepth = Integer.parseInt(lblDifficulty.getText());
				} catch (Exception e) {
				}
	    	}
	    });
	    lblDifficulty.setBounds(wid + 70, hei/2 + 100, 30, 25 );
	    lblDifficulty.setFont(new Font("Arial", Font.PLAIN, 25));
	    //add(lblDifficulty);
	    
	    lblDifficultyy = new JLabel("Level");
	    lblDifficultyy.setBounds(wid - 5, hei/2 + 100, 70, 20 );
	    lblDifficultyy.setFont(new Font("Arial", Font.PLAIN, 25));
	    //add(lblDifficultyy);
	    
	    /*wtime = new JLabel("210");
	    wtime.setBounds(wid - 5, hei/2 , 70, 20 );
		wtime.setFont(new Font("Arial", Font.PLAIN, 25));
		//add(wtime);*/
		 
		retry = new JButton("Reset");
		retry.setFont(new Font("Arial", Font.PLAIN, 25));
		retry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try 
				{
					g.reset();
					lblDifficulty.setText("3");
					
				} catch (Exception e) {}
			}
		});
		retry.setBounds(wid - 5, hei/2 + 150, 100, 50 );
		//add(retry);
		 try {
				add(retry);
				add(lblDifficulty);
				add(lblDifficultyy);
				//add(wtime);
				//add(btime);
			} catch (Exception e) {
			}
		
	  }
	  
	  /** A constructor to build a 1D board.
	   */
	 
	  private void paintText(Graphics g)
	  {
	    g.setColor( this.getBackground() );
	    g.setFont(new Font(g.getFont().getFontName(), Font.ITALIC+Font.BOLD, (int)(Math.round(FONT_SIZE*scale))));
	    
	    int x = (int)Math.round(X_OFFSET*scale);
	    int y = (int)Math.round((Y_OFFSET+Y_DIM*grid[0].length)*scale + GAP  ) ;
	    
	    g.fillRect(x,y, this.getSize().width, (int)Math.round(GAP+FONT_SIZE*scale) );
	    g.setColor( Color.black );
	    g.drawString(message, x, y + (int)Math.round(FONT_SIZE*scale));
	  }
	  public void displayTime(boolean white, double theTime)
	  {
		  if (white)
		  {
			  wtime = theTime;
		  }
		  else
		  {
			  btime = theTime;
		  }
		  this.repaint();
	  }
	  private void paintTime (Graphics g)
	  {
		  g.setColor( this.getBackground() );
		    g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 30));
		    
		    int x = wid - 5;
		    int y = hei/2;
		    
		    g.fillRect(wid - 15, hei/2 - 100 , 150, 600 );
		    g.setColor( Color.black );
		    DecimalFormat format = new DecimalFormat("00.###");
		    g.drawString((int)(wtime / 60)  + " : " + format.format(wtime % 60), x, y + (int)Math.round(FONT_SIZE*scale) + 320);
		    g.drawString((int)(btime / 60)  + " : " + format.format(btime % 60), x, y + (int)Math.round(FONT_SIZE*scale)-50);
	  }
	  
	  private void paintGrid(Graphics g)
	  {
		  g.setColor(this.getBackground());
		  g.fillRect(0, 0, 300, 300);
	    for (int i = 0; i < this.grid.length; i++)
	    {
	      for (int j = 0; j < this.grid[i].length; j++)
	      {    
	        if ((i%2 == 0 && j%2 != 0) || (i%2 != 0 && j%2 == 0))
	          g.setColor(GRID_COLOR_A);
	        else
	          g.setColor(GRID_COLOR_B);
	        int curX = (int)Math.round((X_OFFSET+X_DIM*i)*scale);
	        int curY = (int)Math.round((Y_OFFSET+Y_DIM*j)*scale);
	        int nextX = (int)Math.round((X_OFFSET+X_DIM*(i+1))*scale);
	        int nextY = (int)Math.round((Y_OFFSET+Y_DIM*(j+1))*scale);
	        int deltaX = nextX-curX; 
	        int deltaY = nextY-curY;
	                                
	        boxes[i][j] = new Rectangle (curX, curY, deltaX, deltaY );
	        g.fillRect(boxes[i][j].x, boxes[i][j].y, boxes[i][j].width, boxes[i][j].height);
	        ImageIcon curImage = this.grid[i][j];
	        if (curImage != null) // Draw pegs if they exist
	        {
	          curImage.paintIcon(this, g, curX, curY);         
	        }
	      }
	    }
	    ((Graphics2D) g).setStroke( new BasicStroke(0.5f) );
	    g.setColor(Color.BLACK);
	    int curX = (int)Math.round(X_OFFSET*scale);
	    int curY = (int)Math.round(Y_OFFSET*scale);
	    int nextX = (int)Math.round((X_OFFSET+X_DIM*grid.length)*scale);
	    int nextY = (int)Math.round((Y_OFFSET+Y_DIM*grid[0].length)*scale);
	    g.drawRect(curX, curY, nextX-curX, nextY-curY);
	  }
	  
	  private void drawLine(Graphics g)
	  {
	    for (int i =0; i < numLines; i++ ) 
	    {
	      ((Graphics2D) g).setStroke( new BasicStroke( 5.0f*(float)scale) );
	      g.drawLine( (int)Math.round((X_OFFSET+X_DIM/2.0+line[0][i]*X_DIM)*scale), 
	                  (int)Math.round((Y_OFFSET+Y_DIM/2.0+line[1][i]*Y_DIM)*scale), 
	                  (int)Math.round((X_OFFSET+X_DIM/2.0+line[2][i]*X_DIM)*scale), 
	                  (int)Math.round((Y_OFFSET+Y_DIM/2.0+line[3][i]*Y_DIM)*scale) );
	    }
	  }
	  
	  /**
	   * Convert a String to the corresponding Color defaulting to Black 
	   * with an invald input
	   */
	  private ImageIcon convertImageIcon(String thePiece )
	  {
	    for( int i=0; i<PIECE_NAMES.length; i++ )
	    {
	      if( PIECE_NAMES[i].equalsIgnoreCase( thePiece ) )
	        return PIECES[i];
	    }
	    return null;
	  }
	 
	  
	  /** The method that draws everything
	   */
	  public void paintComponent( Graphics g ) 
	  {
	    this.setScale();
	    this.paintGrid(g);
	    this.drawLine(g);
	    this.paintText(g);
	    for (int i = 0; i < rows; i++)
	    {
	    	for (int j = 0; j < columns; j++)
	    	{
	    		if (this.g != null)
	    		{
	    			if (this.g.displays[i][j] == 1)
	    			{
	    				paintSquare(g, Color.green, i, j);
	    			}
	    			else if (this.g.displays[i][j] == 2 || this.g.displays[i][j] == 5)
	    			{
	    				paintSquare(g, Color.blue, i, j);
	    			}
	    			else if (this.g.displays[i][j] == 3 || this.g.displays[i][j] == 6)
	    			{
	    				paintSquare(g, Color.red, i, j);
	    			}
	    			else if (this.g.displays[i][j] == 4)
	    			{
	    				paintSquare(g, Color.pink, i, j);
	    			}
	    		}
	    	}
	    }
	    this.paintTime(g);
	  }
	  
	  public void setScale()
	  {
	    double width = (0.0+this.getSize().width) / this.originalWidth;
	    double height = (0.0+this.getSize().height) / this.originalHeight;
	    this.scale = Math.max( Math.min(width,height), MIN_SCALE ); 
	  }
	  
	  /** Sets the message to be displayed under the board
	   */
	  public void displayMessage(String theMessage)
	  {
	    message = theMessage;
	    this.repaint();
	  }
	  
	  
	  /** This method will save the value of the colour of the peg in a specific 
	    * spot.  theColour is restricted to 
	    *   "yellow", "blue", "cyan", "green", "pink", "white", "red", "orange"  
	    * Otherwise the colour black will be used. 
	    */
	  public void putPeg(String theImage, int row, int col)
	  {
	    this.grid[col][row] = this.convertImageIcon(theImage );
	    this.repaint();
	  }
	  
	  /** Remove a peg from the gameboard.
	   */
	  public void removePeg(int row, int col)
	  {
	    this.grid[col][row] = null;
	    repaint();
	  }
	  
	   
	  /** Draws a line on the board using the given co-ordinates as endpoints
	   */
	  public void drawLine(int row1, int col1, int row2, int col2)
	  {
	    this.line[0][numLines]=col1;
	    this.line[1][numLines]=row1;
	    this.line[2][numLines]=col2;
	    this.line[3][numLines]=row2;
	    this.numLines++;
	    repaint();
	  }
	
	  /** Removes one line from a board given the co-ordinates as endpoints
	   * If there is no such line, nothing happens
	   * If multiple lines, all copies are removed
	   */
	  
	  public void removeLine(int row1, int col1, int row2, int col2) 
	  {
	    int curLine = 0;
	    while (curLine < this.numLines) 
	    {
	      // Check for either endpoint being specified first in our line table
	      if ( (line[0][curLine] == col1 && line[1][curLine] == row1 &&
	            line[2][curLine] == col2 && line[3][curLine] == row2)   || 
	           (line[2][curLine] == col1 && line[3][curLine] == row1 &&
	            line[0][curLine] == col2 && line[1][curLine] == row2) )
	      {
	        // found a matching line: overwrite with the last one
	        numLines--;
	        line[0][curLine] = line[0][numLines];
	        line[1][curLine] = line[1][numLines];
	        line[2][curLine] = line[2][numLines];
	        line[3][curLine] = line[3][numLines];
	        curLine--; // perhaps the one we copied is also a match
	      }
	      curLine++;
	       
	    }
	    repaint();
	  }
	  
	  /** Waits for user to click somewhere and then returns the click.
	   */
	  /*
	  public Coordinate getClick()
	  {
	      Coordinate returnedClick = null;
	      synchronized(this) {
	          lastClick = null;
	          while (lastClick == null)
	          {
	              try {
	                  this.wait();
	              } catch(Exception e) {
	                  // We'll never call Thread.interrupt(), so just consider
	                  // this an error.
	                  e.printStackTrace();
	                  System.exit(-1) ;
	              } 
	          }
	    
	          int col = (int)Math.floor((lastClick.getCol()-X_OFFSET*scale)/X_DIM/scale);
	          int row = (int)Math.floor((lastClick.getRow()-Y_OFFSET*scale)/Y_DIM/scale);
	          
	          // Put this into a new object to avoid a possible race.
	          returnedClick = new Coordinate(row,col);
	      }
	      return returnedClick;
	  }*/
	  public void paintSquare(Graphics g, Color col, int y, int x)
	  {
		  // X AND Y ARE REVERSED BRO WATCH OUT
		  g.setColor(col);
		  ImageIcon image = grid[x][y];
		  if (col != null)
		  {
			  g.fillRect(boxes[x][y].x, boxes[x][y].y, boxes[x][y].width, boxes[x][y].height);
		  }

		  try
		  {
			  image.paintIcon(this, g, boxes[x][y].x, boxes[x][y].y);
		  }
		  catch (Exception e)
		  {
			  
		  }
		  
	  }
	  
	  public void select (MouseEvent e, boolean dragged)
	  {
		  int x = (int)e.getPoint().getX();
          int y = (int)e.getPoint().getY();
          
          // We need to by synchronized to the parent class so we can wake
          // up any threads that might be waiting for us
          synchronized(Board.this) 
          {
            int curX = (int)Math.round(X_OFFSET*scale);
            int curY = (int)Math.round(Y_OFFSET*scale);
            int nextX = (int)Math.round((X_OFFSET+X_DIM*grid.length)*scale);
            int nextY = (int)Math.round((Y_OFFSET+Y_DIM*grid[0].length)*scale);
            // Subtract one from high end so clicks on the black edge
            // don't yield a row or column outside of board because of
            // the way the coordinate is calculated.
            if (x >= curX && y >= curY && x < nextX && y < nextY)
            {
              lastClick = new Coordinate(y,x);
              // Notify any threads that would be waiting for a mouse click
              Board.this.notifyAll() ;
            } /* if */
            for (int i = 0; i < rows; i++)
            {
            	for (int j = 0; j < columns; j++)
            	{
            		if (g != null && lastClick != null && boxes[i][j].contains(new Point(lastClick.getRow(), lastClick.getCol())) && x < nextX && y < nextY && x >= curX && y >= curY)
            		{
            			g.mouseClicked(i, j, dragged);
            			//System.out.println(i + " " + j);
            		}
            	}
            }
          } 
        
	  }
}
	  