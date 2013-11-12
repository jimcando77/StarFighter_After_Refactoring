import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OuterSpace extends Canvas implements KeyListener, Runnable
{
	private Ship ship;
	private int ammosupply;
	private double Threadnum;
	private boolean start;
	private Menu m;
	private int hitnum = 0;
	private int xlimit = 510;
	private String screen;
    private ArrayList<Alien> aliens;
	private ArrayList<Ammo> shots;
    private boolean[] keys;
	private BufferedImage back;

	public OuterSpace()
	{
		m = new Menu(this);
		screen = "gamepage";
		setupgamepage();
		setVisible(true);
	}

   public void update(Graphics window)
   {
   	   if (screen.equals("menu"))
   	   {

   	   }

   	   else if (screen.equals("gamepage"))
   	   {
	   	   if (start)
	   	   {
	   	   	   paint(window);

	   	   }
   	   }


   }

	public void paint( Graphics window )
	{
			if (screen.equals("menu"))
			{

			}

				else if (screen.equals("gamepage"))
				{
				//set up the double buffering to make the game animation nice and smooth
				Graphics2D twoDGraph = (Graphics2D)window;
                if(back==null)
				   back = (BufferedImage)(createImage(getWidth(),getHeight()));
				Graphics graphToBack = back.createGraphics();
                graphToBack.setColor(Color.BLUE);
				graphToBack.drawString("StarFighter ", 25, 50 );
				graphToBack.setColor(Color.BLACK);
				graphToBack.fillRect(0,0,800,600);
				m.drawgamescreen(graphToBack);

				if (keys[0] == true && ship.getPosX()>0-20)
				{
					ship.move("LEFT");
				}
				if (keys[1] == true && ship.getPosX()<800-80)
				{
					ship.move("RIGHT");
				}

				if (keys[2] == true && ship.getPosY()>0-10)
				{
					ship.move("UP");
				}

				if (keys[3] == true && ship.getPosY()<600-100)
				{
					ship.move("DOWN");
				}

				if (keys[4] == true)
				{
					shots.add(new Ammo(ship.getPosX()+40,ship.getPosY()-80,1)); //orig speed 25
					keys[4] = false;
				}
                //add code to move stuff

				// update ship movement
				ship.draw(graphToBack);

				//update alien movement
				for (Alien a : aliens)
				{
					a.draw(graphToBack);
				}

				//update Ammo movement
				for (Ammo ammo : shots)
				{
					if (ammo.isalive())
					{
					  ammo.draw(graphToBack);
					}

				}

				if (Threadnum%100==0)
				{
					//if (canshoot)
					keys[4] = true;
					//canshoot = !canshoot;

				}


				if (Threadnum%1==0)
				{
					//if (canshoot)
					{
						for (Ammo ammo : shots)
						{
							if (ammo.isalive())
							ammo.setPosY(ammo.getPosY()-10);
						}
					}

					//canshoot = !canshoot;

				}

				if (Threadnum%((random(1,6)*300))==0) 
				{
					addmorealiens();
				}

				if (Threadnum%10==0)  
				{
					for (Alien alien : aliens)
					{

						if (alien.getresistance()!=0)
						{
							alien.scootback(2);
							alien.setPosY(alien.getPosY()-2);
						}
						else
						{
							alien.setPosY(alien.getPosY() + (random(0,2)*1));

							int randx = (random(0,3));
							int dir = random (1,2);

							if (dir==1 && alien.getPosX()-randx>0)
							{
								alien.setPosX(alien.getPosX() - randx);
								
							}

							if (dir==2 && alien.getPosX()+randx<xlimit)
							{
								alien.setPosX(alien.getPosX() + randx);
							}

						}

					}
				}
				if (aliens.size()==0)
				{
				//	addmorealiens();
				}

				//add in collision detection
				for (Ammo am : shots) //remove dead aliens
				{
					if (am.isdead())
					{
						shots.remove(am);
					}
				}

				for (Alien a : aliens) //remove dead aliens
				{
					if (a.isdead())
					{
						aliens.remove(a);
					}
				}


				for (Alien a : aliens) //remove past aliens (collsion detection between aliens and wall)
				{
					if (a.getPosY()>600)
					{

						a.setPosY(100);
					//	aliens.remove(a);
					}
				}
				for (Alien alien : aliens) 
				{
					for (Ammo ammo : shots)
					{
                        if (Math.abs(ammo.getPosX()-alien.getPosX())<80 && Math.abs(ammo.getPosY()-alien.getPosY())<80 && ammo.isalive())
		               {
                        ammo.kill();
		               	hitnum++;
		               	ammosupply--;
		               	alien.movebackmore();
		               	alien.kill();
		               	System.out.println("alien hit! " + gethitnum());
                       }
		               //removedead();
					}
				}

				if (aliens.size()==0)
				{
				//	addmorealiens();
				}

				twoDGraph.drawImage(back, null, 0, 0);
				}
	}


	public void keyPressed(KeyEvent e)
	{
		if (screen.equals("menu"))
		{

		}

		else if (screen.equals("gamepage"))
		{

			if (!start && e.getKeyCode() == KeyEvent.VK_S)
			{
				start = true;
		    }


			else if (start)
			{
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
				{keys[0] = true;}

				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				{keys[1] = true;}

				if (e.getKeyCode() == KeyEvent.VK_UP)
				{keys[2] = true;}

				if (e.getKeyCode() == KeyEvent.VK_DOWN)
				{keys[3] = true;}

				if (e.getKeyCode() == KeyEvent.VK_SPACE)
				{

				//	keys[4] = true;

				}

				if (e.getKeyCode() == KeyEvent.VK_S) //S = "Stats" -ship info, alien info, ammo info
				{printstats();}

				if (e.getKeyCode() == KeyEvent.VK_P)
				{
					start = false;
					Graphics g = getGraphics();
					m.drawMenu(g);
				}

				repaint();
			}
		}
}
	

	public void keyReleased(KeyEvent e)
	{
		if (screen.equals("menu"))
		{

		}

		else if (screen.equals("gamepage"))
		{
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				keys[0] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				keys[1] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				keys[2] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				keys[3] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				keys[4] = true; // key[4]  = false;
			}
		}

		repaint();
	}

	public void keyTyped(KeyEvent e)
	{
		if (screen.equals("menu"))
		{

		}

		else if (screen.equals("gamepage"))
		{
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				keys[4] = true;
			}
			repaint();
		}
}

   public void run()
   {
   	try
   	{
   		while(true)
   		{
   		   Thread.currentThread();
		Thread.sleep(5);
   		   Threadnum += 5; //
            repaint();
         }
      }catch(Exception e)
      {
      }
   }

   public void setupgamepage ()
   {
		Graphics g = getGraphics();
		Threadnum = 0;
		setBackground(Color.black);

		keys = new boolean[5];
		ship = new Ship(250,400,10);
		aliens = new ArrayList<Alien>();
		shots = new ArrayList<Ammo>();
		ammosupply = 1000;
		start = false;
		addmorealiens();
		this.addKeyListener(this);
		new Thread(this).start();
   }
   public void printstats()
   {
   	 System.out.println("ship: " + ship);
   	 System.out.println("aliens: " + aliens.size());
   	 System.out.println("ammo: " + ammosupply);

   	 System.out.println();
   }


   public void reload(int newammo)//
   {
	   ammosupply = newammo;
   }

   public void addmorealiens() //
   {
		for (int x = 0; x<=5;  x++)
		{
			aliens.add(new Alien((100*x),0,350));
		}
   }


      public int gethitnum ()
   {
		return hitnum;
   }


   public double random(double x, double y) //
   {
   	return (Math.random()*(y-x+1))+1;
   }

   public int random(int x, int y) //
   {
   	return (int)(Math.random()*(y-x+1))+1;
   }
}