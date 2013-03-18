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
package com.example.tankwars;

import java.util.HashMap;

import com.amphibian.environment.Environment;
import com.amphibian.tank.Tank;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
    private Tank playerOne;
    private Tank playerTwo;
    private boolean playerOneActive = true;
    private ImageView Environment;
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
        //Create the first tank
        playerOne = new Tank(100, true);
        //Create the second tank
        playerTwo = new Tank(400, false);
        //Create the environment.
        theEnvironment = new Environment(this, 500, 300);
        //Create sound interface.
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(soundID, soundPool.load(this, R.raw.s1, 1));
        
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_game_main);
        
        //Set the environment to the imageview
        Environment = (ImageView)findViewById(R.id.Environment);
        
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
        leftTankHeathTextView.setText(Integer.toString(playerOne.getHealth()));
        rightTankHealthTextView.setText(Integer.toString(playerTwo.getHealth()));
        
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
        Environment.setImageBitmap(theEnvironment.getBitmap());

        //Draw the environment
        drawEnvironment();
        //Update the screen.
        Environment.invalidate();
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
        playerOne.move(true, playerTwo);
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
                startMoveThread(move,playerOne, playerTwo);
            else
                startMoveThread(move,playerTwo, playerOne);
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
        theEnvironment.drawTank(playerOne);
        theEnvironment.drawTank(playerTwo);
        leftTankHeathTextView.setText(Integer.toString(playerOne.getHealth()));
        rightTankHealthTextView.setText(Integer.toString(playerTwo.getHealth()));
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
        // Check if a player has won
        if (playerOne.health == 0) {
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
        if (playerTwo.health == 0) {
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
            fireBullet(currPlayer, otherPlayer);
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
                                if (playerOne.getRect().intersect
                                        (playerTwo.getRect())) {
                                    playerOne.hasCollided = true;
                                    playerTwo.hasCollided = true;
                                }
                                else if (playerOne.getRect().intersect
                                        (theEnvironment.lScreenBorder)) {
                                    playerOne.hasCollided = true;
                                }
                                else if (playerTwo.getRect().intersect
                                        (theEnvironment.lScreenBorder)) {
                                    playerTwo.hasCollided = true;
                                }
                                else if (playerOne.getRect().intersect
                                        (theEnvironment.rScreenBorder)) {
                                    playerOne.hasCollided = true;
                                }
                                else if (playerTwo.getRect().intersect
                                        (theEnvironment.rScreenBorder)) {
                                    playerTwo.hasCollided = true;
                                }
                                else {
                                    playerOne.hasCollided = false;
                                    playerTwo.hasCollided = false;
                                }
                                currPlayer.move(true, otherPlayer);
                                drawEnvironment();
                                Environment.invalidate();
                                break;
                                //This moves the player left.
                            case moveLeft:
                              //Check environment collisions
                                if (playerOne.getRect().intersect
                                        (playerTwo.getRect())) {
                                    playerOne.hasCollided = true;
                                    playerTwo.hasCollided = true;
                                }
                                else if (playerOne.getRect().intersect
                                        (theEnvironment.lScreenBorder)) {
                                    playerOne.hasCollided = true;
                                }
                                else if (playerTwo.getRect().intersect
                                        (theEnvironment.lScreenBorder)) {
                                    playerTwo.hasCollided = true;
                                }
                                else if (playerOne.getRect().intersect
                                        (theEnvironment.rScreenBorder)) {
                                    playerOne.hasCollided = true;
                                }
                                else if (playerTwo.getRect().intersect
                                        (theEnvironment.rScreenBorder)) {
                                    playerTwo.hasCollided = true;
                                }
                                else {
                                    playerOne.hasCollided = false;
                                    playerTwo.hasCollided = false;
                                }
                                currPlayer.move(false, otherPlayer);
                                drawEnvironment();
                                Environment.invalidate();
                                break;
                                //This moves the turret down.
                            case turrDown:
                                currPlayer.turret_move(false);
                                drawEnvironment();
                                Environment.invalidate();
                                break;
                                //This moves the turret up.
                            case turrUp:
                                currPlayer.turret_move(true);
                                drawEnvironment();
                                Environment.invalidate();
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
    private void fireBullet(final Tank Player, final Tank otherPlayer) {
    //Ensure no other buttons can be pressed.
    pause = true;
    
    //Gets the bullets position.
    if(Player.getRotate())
        Player.setBulletPos(
                (float) (Player.getX() + 28 + 28 * Math.cos(
                            Math.toRadians(Player.getDegrees()))), 
                (float) (130 + 28 * Math.sin(
                            Math.toRadians(-Player.getDegrees()))));
    else
        Player.setBulletPos(
                (float) (Player.getX() + 16 - 28 * Math.cos(
                        Math.toRadians(Player.getDegrees()))),
                (float) (130 + 28 * Math.sin(
                        Math.toRadians(-Player.getDegrees()))));

    //This starts a thread
    Thread thread =  new Thread() {
        @Override
        public void run() {
            try {
                double t = 0;
                while(Player.getBulletY(t) < 150 - 1 / 7)
                {
                    final double fin = t;
                    // Check if shot hit other tank
                    if (theEnvironment.bulHitBox.
                            intersect(otherPlayer.getRect())) {
                        //Player hit, decrease health and play sound
                        audioManager = (AudioManager)getSystemService
                                (Context.AUDIO_SERVICE);
                        soundPool.play(soundID, audioManager.getStreamVolume
                                (AudioManager.STREAM_MUSIC)
                                , audioManager.getStreamVolume
                                (AudioManager.STREAM_MUSIC)
                                , 1, 0, 1f);
                        otherPlayer.wasShot = true;
                        otherPlayer.health -= 10;
                        break;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            drawEnvironment();
                            theEnvironment.drawBullet(Player.getBulletX(fin),
                                                      Player.getBulletY(fin));
                            Environment.invalidate();
                        }
                    });
                    
                    try {
                        //Sleep for 25 ms giving 40 fps (1000/40) = 25.
                        Thread.sleep(15);
                        t += 1;
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