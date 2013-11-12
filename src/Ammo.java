import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Ammo extends MovingThing
{
	private int speed;
	private Image image;
	private boolean alive;

	public Ammo()
	{
		this(0,0,0);
	}

	public Ammo(int x, int y)
	{
				this(x,y,0);
	}

	public Ammo(int x, int y, int s)
	{

		super(x,y);
		setSpeed(s);
		alive = true;
		try
		{
			image = ImageIO.read(new File("ammo.jpg"));
		}
		catch(Exception e)
		{
			//feel free to do something here
		}
		
	}

	public void setSpeed(int s)
	{
	   		   	speed = s;
	}

	public int getSpeed()
	{
	   	return speed;
	}

	public void draw( Graphics window )
	{
		//add code to draw the ammo
		window.drawImage(image,getPosX(),getPosY(),5,40,null);
	}

	public boolean isdead ()
	{
		return !alive;
	}

	public boolean isalive ()
	{
		return alive;
	}

	public void kill ()
	{
		alive = false;
	}

	public String toString()
	{
		return super.toString() + " speed: " + speed;
	}
}
