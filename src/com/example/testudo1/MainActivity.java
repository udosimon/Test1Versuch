package com.example.testudo1;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.List;

import com.example.sensor.AccelerometerListener;
import com.example.sensor.AccelerometerManager;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AccelerometerListener {
	
	private final static float MOVE_FAKTOR =9.0F;
	
	private Button buttonAction1;
	private Button buttonMove;
	private TextView textView1;
	private TextView textViewX;
	private TextView textViewY;
	private TextView textViewZ;
	private TextView textViewShake;
	
	private boolean isShaked = true;
	private boolean isStartMoveButton = false;
	
	int displayHeight = 0;
	int displayWidth = 0;
	int movedHeight = 0;
	int movedWidth = 0;

	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		Log.i("Output", "onPostCreate "+buttonMove.getWidth()+"  "+buttonMove.getX());

		float x = buttonMove.getX();
		
		movedHeight = buttonMove.getHeight();
		movedWidth = buttonMove.getWidth();

		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		buttonAction1 = (Button) findViewById(R.id.buttonA1);
		buttonMove = (Button) findViewById(R.id.buttonMove);
		textView1 = (TextView) findViewById(R.id.textView1);
		textViewX = (TextView) findViewById(R.id.textViewX);
		textViewY = (TextView) findViewById(R.id.textViewY);
		textViewZ = (TextView) findViewById(R.id.textViewZ);
		textViewShake = (TextView) findViewById(R.id.textViewShake);

		
		WindowManager w = this.getWindowManager();
		Display d = w.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		d.getMetrics(metrics);

		displayHeight = metrics.heightPixels;
		displayWidth = metrics.widthPixels;
		
		if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
			try {
				displayWidth = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
				displayHeight = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
			} catch (Exception ignored) {
			}
			// includes window decorations (statusbar bar/menu bar)
			if (Build.VERSION.SDK_INT >= 17)
			try {
			    Point realSize = new Point();
			    Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
			    displayWidth = realSize.x;
			    displayHeight = realSize.y;
			} catch (Exception ignored) {
			}
		}
		
//		BitmapDrawable bd = (BitmapDrawable) this.getResources().getDisplayMetrics()
//		int width = bd.getBitmap().getWidth();
//		int height = bd.getBitmap().getHeight();
//		float ww = d.getHeight();
//		float qq = d.getWidth();
		
		// Clicklistener für den Move-button
		buttonMove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
				Log.i("Output", "Move-Button Click");
				handleMoveBotton();
			}
		});
		
		// Clicklistener für den button
		buttonAction1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View arg0) {
		    	
		    	String text = "";
				text = text + "Display:"+displayWidth+"/"+displayHeight
						+"\n Button:"+movedWidth+"/"+movedHeight
						+"\n Button2:"+buttonMove.getWidth()+"/"+buttonMove.getHeight();
				Log.i("Output", "Button Click "+text);
				
				SensorManager sMgr = (SensorManager)MainActivity.this.getSystemService(SENSOR_SERVICE);
				List<Sensor> list = sMgr.getSensorList(Sensor.TYPE_LIGHT);
				String st = "";
				for(Sensor sensor: list){
					Log.i("Activity","Sensor:"+sensor.getName());
					st += sensor.getName()+"\n";
   				}
		        Toast.makeText(getBaseContext(), st, 
	                    Toast.LENGTH_LONG).show();
				Log.i("Output", "Button Click3 "+st);

				textView1.setText(text);
				
			}

		});

		Log.i("Output", "onCreate "+buttonMove.getWidth()+"  "+buttonMove.getX());

	}
	
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Output", "onResume"+buttonMove.getWidth()+"  "+buttonMove.getX());
        
        //Check device supported Accelerometer sensor or not
        if (AccelerometerManager.isSupported(this)) {
             
            //Start Accelerometer Listening
            AccelerometerManager.startListening(this);
            Log.i("Activity", "onResume -> support");
        }
		movedHeight = buttonMove.getHeight();
		movedWidth = buttonMove.getWidth();

    }

    @Override
	protected void onStart() {
    	Log.i("Output", "onStart"+buttonMove.getWidth()+"  "+buttonMove.getX());
		super.onStart();
	}


	@Override
	protected void onRestart() {
        Log.i("Output", "onRestart"+buttonMove.getWidth()+"  "+buttonMove.getX());
		super.onRestart();
        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {
             
            //Start Accelerometer Listening
            AccelerometerManager.stopListening();
        }

	}

    
    @Override
	protected void onStop() {
		super.onStop();
    	Log.i("Output", "onStop");
	}

	@Override
    protected void onDestroy() {
    	super.onDestroy();
    	Log.i("Output", "onDestroy");
    
    	// If we no longer need it, kill the service
        if (AccelerometerManager.isListening()) {
            
            //Stop Accelerometer Listening
            AccelerometerManager.stopListening();
        }   		

    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        Log.i("Output", "onCreateOptionsMenu");
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
        Log.i("Output", "onCreateOptionsMenu id:"+id);
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAccelerationChanged(float x, float y, float z) {
		// auf 1 Stelle hinter dem Komma kürzen
		float rundX = (float)(((int)(x*10))/10.0);
		float rundY = (float)(((int)(y*10))/10.0);
		float rundZ = (float)(((int)(z*10))/10.0);
		
		textViewX.setText("" + rundX);
		textViewY.setText("" + rundY);
		textViewZ.setText("" + rundZ);
		
		doMoveButton(rundX, rundY);
	}

	@Override
	public void onShake(float force) {
		if (isShaked) {
			textViewShake.setText("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			isShaked = false;
		} else {
			textViewShake.setText("-----------------------------");
			isShaked = true;
		}
		
	}
	

	private void handleMoveBotton() {
		if (isStartMoveButton) {
			isStartMoveButton = false;
			buttonMove.setBackgroundColor(Color.LTGRAY);
		} else {
			isStartMoveButton = true;
			buttonMove.setBackgroundColor(Color.BLUE);
		}
//		if (movedWidth == 0) {
			movedWidth = buttonMove.getWidth()+buttonMove.getPaddingLeft()+buttonMove.getPaddingRight();
			movedHeight = buttonMove.getHeight()+buttonMove.getPaddingTop()+buttonMove.getPaddingBottom();
			Rect rec = new Rect();
			Window win = getWindow();
			win.getDecorView().getWindowVisibleDisplayFrame(rec);
			
			View vi = win.findViewById(Window.ID_ANDROID_CONTENT);
			ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) vi.getLayoutParams();
			
			int statusBarHeight = rec.top;
			int contentViewTop = win.findViewById(Window.ID_ANDROID_CONTENT).getTop();
			
			Rect recDr = new Rect();
//			win.findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(recDr);
			win.findViewById(Window.ID_ANDROID_CONTENT).getLocalVisibleRect(recDr);
			
			int xx = win.findViewById(Window.ID_ANDROID_CONTENT).getWidth();
			int yy = win.findViewById(Window.ID_ANDROID_CONTENT).getHeight();
			int xxM = win.findViewById(Window.ID_ANDROID_CONTENT).getMeasuredWidth();
			int yyM = win.findViewById(Window.ID_ANDROID_CONTENT).getMeasuredHeight();
			
			int pL = win.findViewById(Window.ID_ANDROID_CONTENT).getPaddingStart();
			
			String st = "StatusBarHeight : "+statusBarHeight+"\n"
					  + "ContenViewTop   : "+contentViewTop+"\n"
					  + "DisplayHeight   : "+displayHeight+"\n"
					  + "  xx/yy:  "+xx+"/"+yy+"\n"
					  + "  Mxx/yy:  "+xxM+"/"+yyM+"\n"
					  + "  Amrgin B :"+lp.leftMargin+"/"+lp.rightMargin+"\n"
					  + "Draw:"+recDr.left+"/"+recDr.right+"\n\n"
					  +"    "+buttonMove.getWidth()+"/"+buttonMove.getPaddingTop()+"/"+buttonMove.getPaddingLeft()
					  +"/"+buttonMove.getPaddingRight();
			
	        Log.i("Output", "handleMoveBotton id:"+statusBarHeight+"  "+contentViewTop);
	        Log.i("Output", "                   :"+displayHeight);
	        Log.i("Output", "                   :"+xx+"  "+yy);

	        displayHeight = yy;
	        
	        Toast.makeText(getBaseContext(), st, 
                    Toast.LENGTH_LONG).show();

//		}
	}
	
	private void doMoveButton(float rundX, float rundY) {
		if (! isStartMoveButton) {
			// nix zu tun
			return;
		}
		
		rundX = rundX * -1;
		float x = buttonMove.getX() + rundX*MOVE_FAKTOR;
		float y = buttonMove.getY() + rundY*MOVE_FAKTOR;
		
		boolean isRed = false;

		if (x < buttonMove.getPaddingLeft()+22) {
			x = buttonMove.getPaddingLeft()+22;
			isRed = true;
		} else if (x > (displayWidth - movedWidth)) {
			x = displayWidth - movedWidth;
			isRed = true;
		}
		
		if (y < buttonMove.getPaddingTop()+22) {
			y = buttonMove.getPaddingTop()+22;
			isRed = true;
		} if (y > (displayHeight - movedHeight)) {
			y = displayHeight - movedHeight;
			isRed = true;
		}
		
		if (isRed) {
			buttonMove.setBackgroundColor(Color.RED);
		} else {
			buttonMove.setBackgroundColor(Color.BLUE);
		}
		
		buttonMove.setX(x);
		buttonMove.setY(y);
		
		
	}

}
