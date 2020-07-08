package chess;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class myThread extends Thread 
{
	public Game g;
	boolean thinking = false;
	public void run()
	{
		try 
		{
			thinking = true;
			g.m.makeMove();
		} 
		catch (CloneNotSupportedException e) 
		{}
		thinking = false;
		try 
		  { 
			  URL sound = getClass().getResource("wood2.wav");
			  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound); 
			  Clip clip = AudioSystem.getClip(); 
			  clip.open(audioInputStream);
			  clip.start(); 
		  } 
		  catch (Exception e) 
		  {
			  e.printStackTrace(); 
		  }
		g.bstarted = true;
		g.turn = !g.turn;
		g.checkeroo();
		//System.out.println("MAIN");
		//g.printPieces(g.board);
	}
}
