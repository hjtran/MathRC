package com.example.mathrc;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathrc.Joystick.JoystickMovedListener;
import com.example.mathrc.Joystick.JoystickClickedListener;
import com.example.mathrc.Joystick.JoystickView;

public class DrivingActivity extends Activity {
	
	// Message types sent from the BluetoothRfcommClient Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
    // Key names received from the BluetoothRfcommClient Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
	
	// Layout View
	private static JoystickView joystick;
	private static SeekBar driveProgressBar;
	private static TextView statusTextView;
	private static BluetoothRfcommClient dRfcommClient;
	
	// Joystick Data
	private static int joystick_x = 0, joystick_y = 0;
	private static boolean center = true;
	
	// Timer
	private static Timer updateTimer;
	private static int timeoutCounter = 0;
	private static int maxTimeoutCount; // = count * updateperiod
	private static long updatePeriod;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driving);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.driving, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_driving,
					container, false);
			
			joystick = (JoystickView) rootView.findViewById(R.id.joystick);
			driveProgressBar = (SeekBar) rootView.findViewById(R.id.driveProgressBar);
			dRfcommClient = MainActivity.mRfcommClient;
			
			
			
			driveProgressBar.setMax(R.integer.maxProgressBar);
			driveProgressBar.setEnabled(false);
			joystick.setOnJostickMovedListener(joystickListener);
			
			updatePeriod = 175;
			maxTimeoutCount = 10;
			
			long delay = 2000;
			updateTimer = new Timer();
			updateTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					UpdateMethod();

				}
			}, delay , updatePeriod);
			
			
			//updateTimer = new Timer();
			
			return rootView;
		}
	}
	
	
	private static JoystickMovedListener joystickListener = new JoystickMovedListener() {
		
		public void OnMoved(int pan, int tilt) {
			joystick_x = pan;
			joystick_y = tilt;
			center = true;
		}
		
		public void OnReleased() {
			
		}
		
		public void OnReturnedToCenter() {
			joystick_x = joystick_y = 0;
			center = true;
		}
		
	};
	
	private static void UpdateMethod() {

		if(!center || (timeoutCounter >= maxTimeoutCount && maxTimeoutCount > -1)){
			

			String message = "";
			
			if(joystick_x < 0) message = message + "1";
			else if (joystick_x >= 0) message = message + "0";
			message = message + String.format("%02d",Math.abs(joystick_x));
			
			if(joystick_y == 0) joystick_y = 6;
			if(joystick_y > 0) message = message + "1";
			else if (joystick_y <= 0) message = message + "0";
			message = message + String.format("%02d",Math.abs(joystick_y));
			message = message + "255";
			
	    	byte[] msgBuffer = message.getBytes();
    		sendMessage( new String(msgBuffer) );
    		
	    	
	    	timeoutCounter = 0;
    	}
    	else{
    		if( maxTimeoutCount>-1 )
    			timeoutCounter++;
		}
		
	}
	
	private static void sendMessage(String message){
		if(dRfcommClient.getState() != BluetoothRfcommClient.STATE_CONNECTED) {
			return;
		}
		if (message.length() > 0) {
			byte[] send = message.getBytes();
			dRfcommClient.write(send);
		}
	}
	

}
