package finalproject;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;



/**
 * 
 * @author Sean McQueeney
 * This class is The main class where it controls the game
 * It calls Player, Bullets, and Enemies class
 * As well As sets up the window to fullscreen
 *
 */
public class Game extends PApplet implements ApplicationConstants {


	/**
	 * The Background
	 */
	PImage _myBackground;

	//Reference to Player class
	private Player player_;
	
	//Array of Bullets class
	private ArrayList<Bullet> bullets_;
	
	private ArrayList<Enemies> enemy_;
	
	private int lastBullet_;
	private static final int TIME_BETWEENbullets_ = 500;	//	msec
	
	private long frameIndex_ = 0L;
	/**
	 * Boolean to animate or not
	 */
	private boolean animate_ = true;

	/**
	 * Has the player on the ground true if 
	 * the Up Key is pressed. Referred to the Player
	 */
	private boolean jump_ = false;
	
	
	private int enemyCount = 3;

	/**
	 * Settings for the game
	 * Only makes it full screen
	 */
	public void settings() {

		//Makes window Full screen
		//fullScreen(P2D);

		//Change to other screen
		//scaling might be off
		size(1200, 800,P2D);
	}

	/**
	 * Sets up the Game
	 * Loads Background
	 * and creates the other classes
	 */
	public void setup() {
		frameRate(500);

		textureMode(NORMAL);
		_myBackground=loadImage("snow.png");
		player_ = new Player();

		graphicClassSetup();
		
		bullets_ = new ArrayList<Bullet>();
		enemy_ = new ArrayList<Enemies>();
		for(int i =0; i<=enemyCount; i++) {
			enemy_.add(new Enemies());
			
		}
		
		

	}

	/**
	 * Draws the game and calls the other classes draw function
	 * Handles all the controls and handles the bullets 
	 */
	public void draw() {
		//Text Size of Kills
		textSize(32);
		fill(0);
		text("Kills: ", 50,50);
		//text("10", 125,50); //enemies killed counter
		
		frameIndex_++;
		if (frameIndex_ % 5 == 0) {

		
			//Background world setup - etc
			background(100);
			drawBackground();

			//draws player
			player_.draw();
			//Draws enemy
			for (Enemies e : enemy_)
				e.draw();
			
			
			//draws bullets only if not null
			for (Bullet b: bullets_) {
				if (b != null)
					b.draw();
			}


		}

		float dt = 10f; 

		int t = millis();

		if (animate_) {
			
			for (Enemies e : enemy_)
				e.update(dt);
			
			/**
			 * Controls for the Game
			 */
			if (keyPressed) {
				if (key == CODED) {
					//Moves Player Left
					if (keyCode == LEFT) {
						player_.move(-dt);

					}
					//Move Player Right
					else if(keyCode == RIGHT) {
							player_.move(dt);
						
					}
					//Jumps
					else if(keyCode == UP) {
						jump_ = true;
						player_.jump();
					}
				}
				switch(key) {        

				//Fires the Bullets with SpaceBar
				case' ':

					if (t - lastBullet_ > TIME_BETWEENbullets_) {
						bullets_.add(player_.fire());
						lastBullet_ = t;
					}
					break;

				}

			} 
			//Calls the player jump method
			if (jump_) {
				jump_  = player_.animateJump();
			}
			for (int k= enemy_.size()-1; k>=0; k--) {
				Enemies e = enemy_.get(k);
				for (Bullet b : bullets_) {
					if (b.isInside(e)) {
						//bullets_.remove(b);
						if (e.hit())
							enemy_.remove(e);

						
					}
				}
				
				
				
			}

			//Loops to create bullets and removes them when off screen
			for (int k=bullets_.size()-1; k >=0; k--) {
				Bullet b = bullets_.get(k);
				if (b != null) {
					boolean bulletStillFlying = b.update(dt);
					if (!bulletStillFlying) {
						bullets_.remove(b);
					}
					
				}
				for (Enemies e : enemy_) {
					if (b.isInside(e)) {
						bullets_.remove( b);
									
						
					}
				}
			}
			
			
  	  }

	}

	/**
	 * Draws the background 
	 */
	public void drawBackground(){

		beginShape();

		texture(_myBackground);
		vertex(0,0,0,0);
		vertex(0,height,0,1);
		vertex(width,height,1,1);
		vertex(width,0,1,0);

		endShape();

	}

	/**
	 * Graphics class for class constructors
	 */
	private void graphicClassSetup() {
		Player.setup(this);
		Enemies.setup(this, player_);
		Bullet.setup(this);
	}



	/**
	 * Main
	 * @param _args
	 */
	public static void main(String _args[]) {
		PApplet.main(new String[] { Game.class.getName() });
	}

}
