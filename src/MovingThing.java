//© A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class -
//Lab  -

import java.awt.Color;
import java.awt.Graphics;

public abstract class MovingThing implements Locatable
{
	private int xPos;
	private int yPos;
	public int xlimit = 0; //
	private Thread thread;
	public MovingThing()
	{
		//add more code
		this(0,0);
	}

	public MovingThing(int x, int y)
	{
		//add more code
		setPos(x,y);
	}

	public void setPos( int x, int y)
	{
		//add more code
		setPosX(x);
		setPosY(y);
	}


	public void setPosX(int x)
	{
		//add more code
		xPos = x;
	}


	public void setPosY(int y)
	{
		//add more code
		yPos = y;
	}

	public int getPosX()
	{
		return xPos;
	}


	public int getPosY()
	{
		return yPos;
	}

	public void setThread(String s) //
	{
		//thread = new Thread(s);
	}

	public Thread getThread() //
	{
		return thread;
	}


	public abstract void setSpeed( int s );
	public abstract int getSpeed();

	public abstract void draw(Graphics window);

	public void move(String direction)
	{
		if(direction.equals("LEFT"))
		{
			setPosX(getPosX()-getSpeed());
		}


      //add more code to complete the move method
      	if(direction.equals("RIGHT"))
      	{
      		setPosX(getPosX()+getSpeed());
      	}

	    if(direction.equals("UP"))
	    {
	    	setPosY(getPosY()-getSpeed());
	    }


	    if(direction.equals("DOWN"))
	    {
	    	setPosY(getPosY()+getSpeed());
	    }


	}

	public String toString()
	{
		return "xPos: " + xPos + " yPos: " + yPos;
	}
}