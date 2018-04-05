package com.example.testudo1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MyView extends View {
    Paint paint = null;
    Button buttonExit = null;
    int radius = 70;
    int xCm; 
    int yCm;
    boolean isMoving = false;
    int colorChange = 0;
    
    int oldX;
    int oldY;
    
    public MyView(Context context) 
    {
         super(context);
         xCm = 300;
         yCm = 300;
         Log.e("Output", "Constructor: "+xCm+"/"+yCm);
         paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) 
    {
       super.onDraw(canvas);
       paint.setStyle(Paint.Style.FILL);
       paint.setColor(Color.TRANSPARENT);
       canvas.drawPaint(paint);
       // Use Color.parseColor to define HTML colors
       paint.setColor(getColorOfCircle(false));
       canvas.drawCircle(xCm , yCm , radius, paint);
    }

    @Override
	public boolean performClick() {
    	super.performClick();
    	return true;
    }

    public void setRadius(int rad) {
    	radius = rad;
    }
    
    public void incRadius() {
    	radius = radius + 10;
    	invalidate();
    }
    public void decRadius() {
    	radius = radius - 10;
    	invalidate();
    }
    
    public void doTouchDown(int x, int y) {
    	if (isInCircle(x, y)) {
    		isMoving = true;
    		oldX = xCm;
    		oldY = yCm;
    	}
    }
    public void doTouchUp() {
    	isMoving = false;
    	if (oldX == xCm && oldY == yCm) {
    		// change color of circle
   	       paint.setColor(getColorOfCircle(true));
   	       invalidate();
    	}
    }
    public void doMove(int x, int y) {
    	if (isMoving) {
    		xCm = x;
    		yCm = y;
    		invalidate();
    	}
    }
    public void setCircle() {
//        xCm = getWidth() / 2;
//        yCm = getHeight() / 2;
//		invalidate();
    }
    
    private int getColorOfCircle(boolean doChange) {
        
    	int retColor = Color.BLACK;
		if (doChange) {
			colorChange++;
			if (colorChange > 3) {
				colorChange = 0;
			}
		}
    	switch (colorChange) {
		case 0:
			retColor = Color.BLUE;
			break;
		case 1:
			retColor = Color.CYAN;
			break;
		case 2:
			retColor = Color.RED;
			break;
		case 3:
			retColor = Color.GREEN;
			break;
		default:
			colorChange = 0;
			break;
		}
    	
    	return retColor;
    }

    
    private boolean isInCircle(int x, int y) {
    	if (x > (xCm + radius/2)) {
    		return false;
    	}
    	if (x < (xCm - radius/2)) {
    		return false;
    	}
    	if (y > (yCm + radius/2)) {
    		return false;
    	}
    	if (y < (yCm - radius/2)) {
    		return false;
    	}
    	return true;
    }

}
