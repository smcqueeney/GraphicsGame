package finalproject;

import processing.core.PApplet;

/**
 * 
 * @author Sean McQueeney
 * This class will be in charge of the projectiles the Player will
 * shoot to kill the enemies
 *
 */
public class Bullet  implements ApplicationConstants{
	
	private static PApplet app_; 
	
	private static final float V = .1f;
	private float x_ = 0f;
	private float y_ = 0f;
	private float vx_;
	
	private int hitCounter_ = 0;
	public static final int MAX_HITS = 3;
	
	//private Enemies enemy_;
	
	
	
	/**
	 * Constructor for Bullets to shoot left or right
	 * @param inApplet
	 * @param x
	 * @param y
	 * @param goingLeft
	 */
	public Bullet( float x, float y, boolean goingLeft){
		
		x_ = x;
		y_ = y;
		vx_ = goingLeft ? -V : V;
		
	}
	
	
	/**
	 * Draws Bullets
	 */
	public void draw() {
		
		app_.pushMatrix();
		app_.fill(255);
		app_.ellipse(x_, y_, 10, 10);
		app_.popMatrix();		
	}
	
	/**
	 * Returns Bullet x value
	 * @return
	 */
	public float getX(){
		return x_;
	}
	
	/**
	 * Moves the bullet
	 * @param dt
	 */
	public boolean update(float dt) {
		
		x_ += vx_ *dt;
		//draw();
		return (x_ >= 0 && (x_ <= app_.width));
	}
	
	public boolean isInside(Enemies enemy)
	{	
		boolean hit  =false;
		
		float enX = enemy.getX(), enY = enemy.getY();
		
		//	center of torso
		float dx = x_ - enX, 
			  dy = y_ - (enY + enemy.snowmanTorso_Offset);
		float distTorso = app_.sqrt(dx*dx + dy*dy);
		hit  = distTorso < enemy.snowmanTorso;
		
		if (!hit) {
			dy = y_ - (enY + enemy.snowmanHead_yOffSet);
			float distHead = app_.sqrt(dx*dx + dy*dy);
			hit  = distHead < enemy.snowmanHead;
		}
		
		return hit;
		
	}
	
//	public boolean hit() {
//		hitCounter_++;
//		if (hitCounter_ >= MAX_HITS) 
//			return true;
//		else
//			return false;
//		
//	}
	
	protected static void setup(PApplet theApp)
	{
			app_ = theApp;

	}

}
