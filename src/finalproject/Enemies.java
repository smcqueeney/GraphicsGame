package finalproject;

import processing.core.PApplet;

/**
 * Class that creates enemies and makes them move
 * @author Sean McQueeney
 *
 */
public class Enemies implements ApplicationConstants{
	//==================================================================
	//Enemy or SnowMan Dimensions
	//==================================================================
	public static final float snowmanHead = 40;
	public static final float snowmanTorso = snowmanHead * 1.5f;
	public static final float snowmanBottom = snowmanHead *2;
	public static final float snowmanHead_yOffSet = -snowmanBottom/4-95;
	public static final float snowmanNose_yOffSet = -snowmanBottom/4-90;
	public static final float snowmanButton_yOffset = -snowmanBottom/4-30;
	public static final float snowmanEye_xOffset = 7;
	public static final float snowmanNose_xOffset = 5;
	public static final float snowmanTorso_Offset = -snowmanBottom/4-50;
	public static final float snowmanBottom_Offset = -snowmanBottom/4;
	
	//==================================================================
	public static final int MAX_HITS = 1;
	
	private static PApplet app_; 
	private static Player player_; 

	
	private float spawnMin = app_.width/5;
	private float spawnMax = app_.width;
	
	private float Vx_ = 0.1f;
	private int hitCounter_ = 0;
	//Snowmans X value
	private float x_;
	private float y_;



	/**
	 * Shifts origin to centerX and the ground
	 */
	public Enemies() {

		y_ = GROUND_Y;
		float d = app_.random(spawnMin, spawnMax);
		//Avoid Player spawn
		if(app_.random(1)<0.5) {
			x_ = -d;
		}
		else {
			x_ = app_.width + d;
		}

		

	}


	/**
	 * Draws the Snowman or enemy
	 */
	public void draw() {

		app_.pushMatrix();

		app_.translate(x_,  y_);
		app_.fill(255);
		app_.noStroke();
		
		//Snowman Bottom
		app_.ellipse(0, snowmanBottom_Offset, snowmanBottom, snowmanBottom);
		
		//Snowman Torso
		app_.ellipse(0,  snowmanTorso_Offset, snowmanTorso, snowmanTorso);
		app_.fill(0);
		
		//SnowMan Buttons
		app_.ellipse(0,snowmanButton_yOffset, snowmanTorso/6, snowmanTorso/6);
		app_.ellipse(0,snowmanButton_yOffset - 15, snowmanTorso/6, snowmanTorso/6);
		app_.ellipse(0,snowmanButton_yOffset - 30, snowmanTorso/6, snowmanTorso/6);

		//snowman Head
		app_.fill(255);
		app_.ellipse(0, snowmanHead_yOffSet, snowmanHead, snowmanHead);
		app_.fill(0);
		//Eyes
		app_.ellipse(snowmanEye_xOffset, snowmanHead_yOffSet,snowmanHead/5, snowmanHead/5);
		app_.ellipse(-snowmanEye_xOffset, snowmanHead_yOffSet,snowmanHead/5, snowmanHead/5);
		app_.fill(255,157,0);
		
		//Nose
		app_.noStroke();
		app_.triangle(-snowmanNose_xOffset, snowmanNose_yOffSet, snowmanNose_xOffset, snowmanNose_yOffSet, 0, snowmanNose_yOffSet + 10);

		app_.popMatrix();

	}


	/**
	 * Moves the Enemy where the player is
	 * Stops if player is Jumping or in Air
	 */
	public void update(float dt) {
		if (!player_.isJumping() || !player_.isGoingDown()) {
			float direction = player_.getX() < x_ ? -1.f : 1.f;
			x_ += direction*Vx_* dt;
		}

	}
	
	public float getX() {
		return x_;
	}
	public float getY() {
		return y_;
	}
	
//	public boolean isInside()
//	{		
//		if(player_.getX() == x_) {
//			player_.remove();
//			
//		}
//		return ;
//		
//	}

	public boolean hit() {
		hitCounter_++;
		if (hitCounter_ >= MAX_HITS)
			return true;
			
		else
			return false;
		
	}
	protected static void setup(PApplet theApp, Player thePlayer)
	{
			app_ = theApp;
			player_ = thePlayer;
	}

}
