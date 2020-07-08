package chess;

import java.util.Date;

public class myThread2 extends Thread 
{
	public Game g;
	public void run()
	{
		while (!g.wstarted || !g.bstarted)
		{
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(g.turn + " " + g.wstarted + " " + g.over);
			pee();
	}
	public void pee()
	{
		long startTime = System.currentTimeMillis();
		double currentTime = g.wseconds;
		while (g.turn && !g.over)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.wseconds = currentTime - ((new Date()).getTime() - startTime)/1000.0;
			g.b.displayTime(true, g.wseconds);
			if (g.wseconds <= 0)
			{
				g.b.displayTime(true, 0);
				g.timelose = 1;
				g.checkeroo();
				return;
			}
		}
		poo();
	}
	public void poo()
	{
		long startTime = System.currentTimeMillis();
		double currentTime = g.bseconds;
		while (!g.turn && !g.over)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.bseconds = currentTime - ((new Date()).getTime() - startTime)/1000.0;
			g.b.displayTime(false, g.bseconds);
			if (g.bseconds <= 0)
			{
				g.b.displayTime(false, 0);
				g.timelose = 2;
				g.thread.stop();
				g.checkeroo();
				return;
			}
		}
		pee();
	}
}
