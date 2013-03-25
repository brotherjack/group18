/**
 * COP4331 Group18 TankWars
 * @author Ryan Farneski
 * @author John Gilmore
 * @author Thomas Hellinger
 * @author Edward Jezisek
 * @author Brandon Ramsey
 * @author Andrew Watson
 * TankWars.java
 * @version 1.0
 * Mar 13, 2013
 * Description: Tank wars application activity. Creates/runs everything seen.
 */
package com.amphibian.tankwars;

import java.util.HashMap;

import com.amphibian.environment.Environment;
import com.amphibian.player.AI_Player;
import com.amphibian.player.HumanPlayer;
import com.amphibian.player.Player;
import com.amphibian.tank.Armament;
import com.amphibian.tank.DamageType;
import com.amphibian.tank.Tank;
import com.amphibian.tank.TankCondition;
import com.amphibian.tankwars.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TankWars extends Activity {
    private enum moveVal{moveLeft, moveRight, turrUp, turrDown, fire};
    //TODO private Tank activePlayer;
    private HumanPlayer playerOne; //TODO remove, when ready for multiplayer
    private HumanPlayer playerTwo;
    //TODO needs an array that stores players who are in the game, not necessarily in the round
    private Tank tankOne;
    private Tank tankTwo; 
    private boolean playerOneActive = true; //TODO remove, when ready for multiplayer
    private ImageView igEnvironment;
    private ImageView Directions[] = new ImageView[5];
    private boolean deleteThreadRunning = false;
    private boolean pause=false;
    private boolean cancelDeleteThread = false;
    private Handler handler = new Handler();
    private Environment theEnvironment;
    private TextView leftTankHeathTextView;
    private TextView rightTankHealthTextView;
    SoundPool soundPool;
    AudioManager audioManager;
    HashMap<Integer, Integer> soundPoolMap;
    int soundID = 1;
    
    /**
     * Creates the environment and sets it up with the android device.
     * March 6, 2013
     */
    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	//TODO Make intro menu, new game and buy item menus
        
        //Create the environment.
        theEnvironment = new Environment(this, 500, 300);
        playerOne = theEnvironment.getActiveHumanPlayers().get(0); //TODO don't hardcode this
        playerTwo = theEnvironment.getActiveHumanPlayers().get(1);
        
        tankOne = playerOne.get_controlled_tank();
        tankTwo = playerTwo.get_controlled_tank();
        
        //Create sound interface.
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(soundID, soundPool.load(this, R.raw.s1, 1));
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_game_main);
        
        //Set the environment to the imageview
        igEnvironment = (ImageView)findViewById(R.id.Environment);
        
        //Set the text views for health display 
        //includes setting size and color.
        leftTankHeathTextView = (TextView)findViewById
                (R.id.leftHealthTextView);
        rightTankHealthTextView = (TextView)findViewById
                (R.id.rightHealthTextView);
        leftTankHeathTextView.setTextSize(50);
        rightTankHealthTextView.setTextSize(50);
        leftTankHeathTextView.setTextColor(Color.RED);
        rightTankHealthTextView.setTextColor(Color.RED);
        leftTankHeathTextView.setText(Integer.toString(tankOne.armor.getHPLeft()));
        rightTankHealthTextView.setText(Integer.toString(tankTwo.armor.getHPLeft()));
        
        //Create all the directional image views.
        int ctr=0;
        Directions[ctr++] = (ImageView)findViewById(R.id.leftArrow);
        Directions[ctr++] = (ImageView)findViewById(R.id.rightArrow);
        Directions[ctr++] = (ImageView)findViewById(R.id.upArrow);
        Directions[ctr++] = (ImageView)findViewById(R.id.downArrow);
        Directions[ctr++] = (ImageView)findViewById(R.id.fire);
        ctr=0;
        
        //Set each to it's onTouch listener,
        //this allows it to receive touch events.
        Directions[ctr++].setOnTouchListener(move(moveVal.moveLeft));
        Directions[ctr++].setOnTouchListener(move(moveVal.moveRight));
        Directions[ctr++].setOnTouchListener(move(moveVal.turrUp));
        Directions[ctr++].setOnTouchListener(move(moveVal.turrDown));
        Directions[ctr++].setOnTouchListener(move(moveVal.fire));
        
        //Set the image bitmap.
        igEnvironment.setImageBitmap(theEnvironment.getBitmap());

        //Draw the environment
        drawEnvironment();
        //Update the screen.
        igEnvironment.invalidate();
    }

    /**
     * Sets up the layout with the device.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * Inflate the menu, this adds items to the action bar
         * if it is present.
         */
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * Moves the tank right.
     * @param v
     */
    public void moveTankRight(View v) {
        tankOne.move_tank(true, theEnvironment);
    }
    
    /**
     * This performs an action based on the action the user sent.
     * @param move
     * @return
     */
    private OnTouchListener move(final moveVal move) {
        OnTouchListener moveR = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v("this is Working", "nope");
                if(pause)
                    return false;
                switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    /**
                     * This starts the item, 
                     * move references the button they pressed.
                     */    
                    startMoving(move);
                    return true;
                case MotionEvent.ACTION_UP:
                    cancelDeleteThread = true;
                    return true;
                }
                return false;
            }
        };
        return moveR;
        
    }
    
    /**
     * This starts moving the item. 
     * It creates a new thread to allow the item to move.
     * @param move
     */
    private void startMoving(moveVal move) {
        if(!deleteThreadRunning && !pause) {
            if(playerOneActive)
                startMoveThread(move,tankOne, tankTwo); //TODO change what is passed to thread
            else
                startMoveThread(move,tankTwo, tankOne);
        }
        else if(!pause){
            deleteThreadRunning = false;
            cancelDeleteThread = false;
        }
    }

    /**
     * This draws the environment.
     */
    private void drawEnvironment() {
        theEnvironment.refreshEnvironment();
        for(HumanPlayer player : theEnvironment.getActiveHumanPlayers()){ 
	        theEnvironment.drawTank(player.get_controlled_tank()); 
        }
        if(theEnvironment.getActiveComputerPlayers() != null){
	        for(AI_Player player : theEnvironment.getActiveComputerPlayers()){ 
		        theEnvironment.drawTank(player.get_controlled_tank()); 
	        }
        }
        leftTankHeathTextView.setText(Integer.toString(tankOne.armor.getHPLeft())); //TODO softcode
        rightTankHealthTextView.setText(Integer.toString(tankTwo.armor.getHPLeft()));
//            theEnvironment.drawTankHitBox(playerOne);
//            theEnvironment.drawTankHitBox(playerTwo);
    }
    
    /**
     * This performs the action on the tank depending on what they pressed.
     * @param move
     * @param currPlayer
     */
    private void startMoveThread(final moveVal move, final Tank currPlayer,
            final Tank otherPlayer) {
    	//TODO Make for loop for checking player array for winner
    	//TODO Determine winner from last tank remaining
        // Check if a player has won
        if (tankOne.armor.getHPLeft() == 0) {
            Toast.makeText(getBaseContext(), "Player Two Wins",
                    Toast.LENGTH_LONG).show();
            new CountDownTimer(3000, 1) {
                public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                System.exit(0);
            }
            }.start();

        }
        if (tankTwo.armor.getHPLeft() == 0) {
            Toast.makeText(getBaseContext(), "Player One Wins",
                    Toast.LENGTH_LONG).show();
            new CountDownTimer(3000, 1) {
                public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                System.exit(0);
            }
            }.start();
        }
        switch(move){
        case fire:
            //Fires the bullet.
            fireBullet(currPlayer);
            //Switches the active player
            playerOneActive = (!playerOneActive);
            break;
        default:
            break;
        }
        
        //Starts a new thread to monitor tank movement.
        Thread r = new Thread() {
        @Override
        public void run() {
            try {
                deleteThreadRunning = true;
                while (!cancelDeleteThread) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            switch(move) {
                            //This moves the player right.
                            case moveRight:
                                //Check environment collisions
                                if (tankOne.getRect().intersect
                                        (tankTwo.getRect())) {
                                    tankOne.hasCollided = true;
                                    tankTwo.hasCollided = true;
                                }
                                else if (tankOne.getRect().intersect
                                        (theEnvironment.lScreenBorder)) {
                                    tankOne.hasCollided = true;
                                }
                                else if (tankTwo.getRect().intersect
                                        (theEnvironment.lScreenBorder)) {
                                    tankTwo.hasCollided = true;
                                }
                                else if (tankOne.getRect().intersect
                                        (theEnvironment.rScreenBorder)) {
                                    tankOne.hasCollided = true;
                                }
                                else if (tankTwo.getRect().intersect
                                        (theEnvironment.rScreenBorder)) {
                                    tankTwo.hasCollided = true;
                                }
                                else {
                                    tankOne.hasCollided = false;
                                    tankTwo.hasCollided = false;
                                }
                                currPlayer.move_tank(true, theEnvironment);
                                drawEnvironment();
                                igEnvironment.invalidate();
                                break;
                                //This moves the player left.
                            case moveLeft:
                              //Check environment collisions
                                if (tankOne.getRect().intersect
                                        (tankTwo.getRect())) {
                                    tankOne.hasCollided = true;
                                    tankTwo.hasCollided = true;
                                }
                                else if (tankOne.getRect().intersect
                                        (theEnvironment.lScreenBorder)) {
                                    tankOne.hasCollided = true;
                                }
                                else if (tankTwo.getRect().intersect
                                        (theEnvironment.lScreenBorder)) {
                                    tankTwo.hasCollided = true;
                                }
                                else if (tankOne.getRect().intersect
                                        (theEnvironment.rScreenBorder)) {
                                    tankOne.hasCollided = true;
                                }
                                else if (tankTwo.getRect().intersect
                                        (theEnvironment.rScreenBorder)) {
                                    tankTwo.hasCollided = true;
                                }
                                else {
                                    tankOne.hasCollided = false;
                                    tankTwo.hasCollided = false;
                                }
                                currPlayer.move_tank(false, theEnvironment);
                                drawEnvironment();
                                igEnvironment.invalidate();
                                break;
                                //This moves the turret down.
                            case turrDown:
                                currPlayer.turret.turret_move(false);
                                drawEnvironment();
                                igEnvironment.invalidate();
                                break;
                                //This moves the turret up.
                            case turrUp:
                                currPlayer.turret.turret_move(true);
                                drawEnvironment();
                                igEnvironment.invalidate();
                                break;
                                //This fires the turret.
                            case fire:
                                cancelDeleteThread = false;
                                break;
                            default:
                                break;	
                            }
                        }
                    });
                    
                    try {
                        //Sleep for 25 ms giving 40 fps (1000/40) = 25.
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(
                            "Could not wait between char delete.", e);
                    }
                }
            }
            finally
            {
                deleteThreadRunning = false;
                cancelDeleteThread = false;
            }
        }
    };
    r.start();
    }
    
    /**
     * This fires the bullet from players position.
     * @param Player
     */
    private void fireBullet(final Tank Player) {
    //TODO rename to "fire", move to turret, and remove arguments	
    //Ensure no other buttons can be pressed.
    pause = true;
    
    Environment.isShotRight = Player.getRotate();
    Environment.weaponInPlay = Player.turret.fire(Environment.isShotRight, Player.getPosX());
    
    //This starts a thread
    Thread thread =  new Thread() {
        @Override
        public void run() {
            try {
                Environment.timeInFlight = 0.0;
                
                while(Environment.weaponInPlay.getBulletY(Environment.timeInFlight, Environment.GRAVITY) < 150 - 1 / 7)
                {
                	final double fin = Environment.timeInFlight;
                	for(Player player : Environment.humanPlayers){ 
                		Tank otherTank = player.get_controlled_tank();
                		if(otherTank.equals(Player)) continue;
	                    // Check if shot hit other tank
	                    if (Environment.weaponInPlay.bulHitBox.
	                            intersect(otherTank.getRect())) {
	                        //Player hit, decrease health and play sound
	                        audioManager = (AudioManager)getSystemService
	                                (Context.AUDIO_SERVICE);
	                        soundPool.play(soundID, audioManager.getStreamVolume
	                                (AudioManager.STREAM_MUSIC)
	                                , audioManager.getStreamVolume
	                                (AudioManager.STREAM_MUSIC)
	                                , 1, 0, 1f);
	                        otherTank.wasShot = true;
	                        //TODO need to change next line for armament damage and tank destruction
	                        TankCondition otherTankStatus = 
	                        		otherTank.strikeTank(Environment.weaponInPlay);
	                        if(otherTankStatus == TankCondition.DESTROYED){
	                        	//TODO MAKE GRAPHFIX FOR SPLOSION!
	                        	//TODO MAKE BOOM BOOM SOUND maybe also BLOOD SPLATTER
	                        	theEnvironment.humanPlayers.remove(otherTank);
	                        }
	                        return;
	                    }
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            drawEnvironment();
                            float xPos = Environment.weaponInPlay.getBulletX(fin, Environment.isShotRight);
                            float yPos = Environment.weaponInPlay.getBulletY(fin, Environment.GRAVITY);
                        
                            theEnvironment.drawBullet(xPos, yPos);
                            igEnvironment.invalidate();
                        }
                    });
                    
                    try {
                        //Sleep for 25 ms giving 40 fps (1000/40) = 25.
                        Thread.sleep(15);
                        Environment.timeInFlight += 1;
                    }
                    catch (InterruptedException e) {
                    }
                }
            }
            finally {
                pause = false;
                cancelDeleteThread = true;
            }
        };
    };
    thread.start();
    }
}