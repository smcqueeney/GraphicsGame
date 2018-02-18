package finalproject;
import processing.core.PApplet;

public class Player implements ApplicationConstants{

	private static PApplet app_; 
	
	//===========================================================================
	//Player Attributes
	//===========================================================================
	private int mouthX_OffSet = 10;
	private int mouthY_OffSet = -90;
	private int headY_OffSet = -100;
	private int torso_Y1 = -35;
	private int torso_Y2 = -75;
	private int eyeX_OffSet = 10;
	private int eyeY_OffSet = -110;
	
	private int armX1 = 20;
	private int armY = -60;
	
	private int legX1 = 15;
	private int legY2 = -35;
	
	private int gunLeftX_OffSet = -40;
	private int gunY_OffSet = -70;
	private int gunRightX_OffSet = 25;
	//===========================================================================

	float x_;
	private float y_;
	
	private boolean isJump = false;
	private boolean isUp = false;
	
	
	
	
	private float Vx_ = .2f;
	private int _bulletCount = 0;
	//=============================================================================
	//Jumping
	//=============================================================================
	private float startJump;
	private static final float ASCENT_TIME = 0.5f; //How slow he Ascends in air
	private static final float JUMP_HEIGHT = 300; //Jump Height
	private static final float ACCEL = -2*JUMP_HEIGHT/(ASCENT_TIME*ASCENT_TIME); //Acceleration
	//==============================================================================
	


	/**
	 * Sets player origin
	 */
	public Player() {

		x_ = CENTER_X;
		y_ = GROUND_Y;
	}

	/**
	 * Moves and draws player
	 * calls drawPlayer method
	 */
	public void draw() {
		app_.pushMatrix();
		
		app_.translate(x_,  y_);
		drawPlayer();
		app_.popMatrix();
		

	}

	
	/**
	 * Draws the player from the origin
	 */
	public void drawPlayer() {
		app_.pushMatrix();
		app_.stroke(0);
		//Head;		
		app_.fill(247, 235, 175);
		app_.ellipse(0, headY_OffSet,PLAYER_HEAD_RAD, PLAYER_HEAD_RAD);

		//EYES
		app_.ellipse( - eyeX_OffSet, eyeY_OffSet ,PLAYER_EYE_RAD, PLAYER_EYE_RAD);
		app_.ellipse( eyeX_OffSet, eyeY_OffSet,PLAYER_EYE_RAD, PLAYER_EYE_RAD);
		
		//Mouth
		app_.line( mouthX_OffSet, mouthY_OffSet,  -mouthX_OffSet, mouthY_OffSet);
		
		//Torso
		app_.strokeWeight(3);
		app_.line(0, torso_Y1, 0, torso_Y2 );
		
		//Arms
		app_.line(-armX1,  armY , 0, armY  ); // Left
		app_.line(armX1, armY , 0, armY  ); // Right
		
		
		
		//Legs
		app_.strokeWeight(3);
		app_.line(- legX1, 0, 0, legY2  ); // Left
		app_.line( legX1, 0, 0, legY2  ); // Right
		
		//Draw Gun
		app_.fill(0);
		//Left
		app_.rect(gunLeftX_OffSet, gunY_OffSet, GUN_WIDTH_TOP, GUN_HEIGHT_TOP);
		app_.rect(gunLeftX_OffSet/2, gunY_OffSet, GUN_MAG_WIDTH ,GUN_MAG_HEIGHT);
		//Right
		app_.rect(gunRightX_OffSet, gunY_OffSet, GUN_WIDTH_TOP, GUN_HEIGHT_TOP);
		app_.rect(gunRightX_OffSet - 5, gunY_OffSet, GUN_MAG_WIDTH ,GUN_MAG_HEIGHT);

	
		app_.popMatrix();
	}

	

	/**
	 * Makes player move on X-axis
	 * @param dt
	 */
	public void move(float dt) {
			x_ += Vx_ * dt;
	}
	
	/**
	 * Jump Method that sets booleans to true 
	 */
	public void jump() {
		
		startJump = (app_.millis() * 0.001f);
		
		isJump = true;
		isUp = true;
		
	}	
		
	/**
	 * Does the animation of player jumping
	 * Moves the player in y-axis
	 * @return
	 */
	public boolean animateJump() {
		float jumpTime = app_.millis()*0.001f - startJump;
		 if (jumpTime < ASCENT_TIME) {
			 //	 still going up
			 y_ = GROUND_Y + 0.5f *  ACCEL *jumpTime*jumpTime;
		 }
		 else if (jumpTime < 2*ASCENT_TIME) {
			 isUp = false;
			 float dt = jumpTime - ASCENT_TIME;
			 y_ = GROUND_Y -JUMP_HEIGHT - 0.5f * ACCEL * dt*dt;
		 }
		 else {
			 y_ = GROUND_Y;
			 isJump = false;
			 // jump is over
		 }
		 
		 return isJump;
	}

	/**
	 * Gets Player x_
	 * @return
	 */
	public float getX() {
		return x_;
	}
	
	/**
	 * Checks if Player is Jumping
	 * @return
	 */
	public boolean isJumping() {
		return isJump;
	}
	
	/**
	 * Checks if player is falling
	 * @return
	 */
	public boolean isGoingDown() {
		return !isUp;
	}
	
	/**
	 * Hadles the firing of bullets
	 * sets the cordinates to the tip of 
	 * the player gun
	 * @return
	 */
	public Bullet fire() {
		
		Bullet b;
		//Shoot Left
		if (_bulletCount %2 == 0) {
			b = new Bullet(x_ - 40, y_ - 70 + GUN_HEIGHT_TOP/2, true);
		}
		//Shoot Right
		else {
			b = new Bullet(x_ + 25 + GUN_WIDTH_TOP, y_ - 70 + GUN_HEIGHT_TOP/2, false);			
		}
		_bulletCount++;
		return b;
	}


	protected static void setup(PApplet theApp)
	{
		app_ = theApp;
	}






}
