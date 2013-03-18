/**
 * COP4331 Group18 TankWars
 * @author Ryan Farneski
 * @author John Gilmore
 * @author Thomas Hellinger
 * @author Edward Jezisek
 * @author Brandon Ramsey
 * @author Andrew Watson
 * Tank.java
 * @version 1.0
 * Mar 13, 2013
 * Description: Environment which draws all the tanks, the bullets, etc..
 * In Progress: Need to implement collision detection.
 */
package com.example.tankwars;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;

public class Environment {
    Bitmap bitmap;
    Paint paint;
    final int tankWidth = 50;
    final int tankHeight = 25;
    final Bitmap.Config conf = Bitmap.Config.ARGB_8888;
    public RectF lScreenBorder = new RectF(0, 0, 0, 0);
    public RectF rScreenBorder = new RectF(0, 0, 0, 0);
    public RectF bulHitBox = new RectF(0, 0, 0, 0);
    public boolean isLeftTankHit = false;
    public boolean isRightTankHit = false;
    private Bitmap curr;
    private Bitmap tankRight;
    private Bitmap tankLeft;
    private Bitmap turretLeft;
    private Bitmap turretRight;
    private Bitmap bullet;
    private Canvas canvas;
    private Context context;
    
    
    /**
     * @param context
     * @param screenWidth
     * @param screenHeight
     */
    public Environment(Context context, int screenWidth, int screenHeight) {
            this.context = context;
            paint = new Paint();
            paint.setAntiAlias(true);
            curr = Bitmap.createBitmap(screenWidth, screenHeight, conf);
            canvas = new Canvas(curr);
            createTankBitmap();
            refreshEnvironment();
            lScreenBorder = new RectF(0, 0, 1, 300);
            rScreenBorder = new RectF(499,0,500,300);
        }
    
    public void createTankBitmap() {
        //This creates the right tank bitmap.
        tankRight = BitmapFactory.decodeResource(context.getResources(), 
                                                    R.drawable.tank);
        tankRight = Bitmaps.resizeBitmap(tankRight, tankWidth, tankHeight);
        
        //This creates  the right turret.
        turretRight = BitmapFactory.decodeResource(context.getResources(),
                                                    R.drawable.turret);
        turretRight = Bitmaps.resizeBitmap
                                (turretRight, tankWidth / 2, tankHeight / 5);
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        
        //This rotates the right tank bitmap and makes it the left tank.
        tankLeft = Bitmap.createBitmap(tankRight, 0, 0, 
                tankRight.getWidth(), tankRight.getHeight(),m, false);
        tankLeft.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        
        //This rotates the right turret bitmap and makes the left turret.
        turretLeft = Bitmap.createBitmap(turretRight, 0, 0, 
                turretRight.getWidth(), turretRight.getHeight(),m, false);
        turretLeft.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        
        bullet = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.bullet);
        bullet = Bitmaps.resizeBitmap(bullet, bullet.getHeight() / 7,
                            bullet.getWidth() / 7);
    }
    
    public void refreshEnvironment() {
        paint.setColor(Color.CYAN);
        canvas.drawRect(0, 150, 500, 0, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(0, 300, 500, 150, paint);
    }
    
    public void drawTank(Tank aTank) {
        if(aTank.getRotate()) {
            canvas.drawBitmap(tankLeft, aTank.getX(), 150 - tankHeight, paint);
        }
        else {
            canvas.drawBitmap(tankRight,aTank.getX(), 150 - tankHeight, paint);
        }
        drawTurret(aTank);
    }
    
    public void drawTurret(Tank aTank) {
        Matrix matrix = new Matrix();
    
        if(aTank.getRotate()) {
            float xPos = aTank.getX() + tankLeft.getWidth() - 20;
            float yPos = (float) (150 - .75 * tankHeight);
            matrix.setRotate(-aTank.getDegrees(), 0, 0);
            matrix.postTranslate(xPos, yPos);
            canvas.drawBitmap(turretLeft, matrix, paint);
        }
        else {
            float xPos = aTank.getX() - 5;
            float yPos = (float) (150 - .75 *tankHeight);
            matrix.setRotate(aTank.getDegrees(), turretRight.getWidth(), 0);
            matrix.postTranslate(xPos, yPos);
            canvas.drawBitmap(turretRight, matrix, paint);
        }
    }
    
    public void drawTankHitBox(Tank aTank) {
        paint.setColor(Color.RED);
        canvas.drawRect(aTank.rect, paint);
    }
    
    public void drawBulletHitBox() {
        paint.setColor(Color.RED);
        canvas.drawRect(bulHitBox, paint);
    }
    
    public Bitmap getBitmap() {
        return curr;
    }
    
    public void drawBullet(float xPos, float yPos) {
        bulHitBox.set(xPos - 5, yPos + 5, xPos + 5, yPos - 5);
//        canvas.drawBitmap(bullet, xPos, yPos, paint);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(xPos, yPos, 5, paint);
//        drawBulletHitBox();
    }

}
