package com.example.mathrc;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProgrammingActivity extends Activity {

	private static final String DRIVEFAST = "000010255";
	private static final String DRIVEMEDIUM = "000010150";	
	private static final String DRIVESLOW = "00001070";	
	private static final String TURNLEFT = "110000255";	
	private static final String TURNRIGHT = "010000255";
	private static final String TURNCENTER = "000000255";	
	private static final String STOP = "00000000";	
	private static final String REVERSEFAST = "000110255";	
	private static final String REVERSEMEDIUM = "000110150";	
	private static final String REVERSESLOW = "000110070";	
	
	private static int slideCount = 1;
	private static BluetoothRfcommClient pRfcommClient;
	private static TextView textView_programming;
	private static TextView console_programming;
	private static Button nextButton_programming;
	private static Button backButton_programming;
	private static Button enterButton_programming;
	private static String enteredCommand;
	private static EditText codeField;
	private static String message = "000000000";
	
	// Timer
	private static Timer updateTimer;
	private static int timeoutCounter = 0;
	private static int maxTimeoutCount; // = count * updateperiod
	private static long updatePeriod;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_programming);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.programming, menu);
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
	
	// Begin Listeners
	
	public void nextButton_programmingListener(View view) {
		if(slideCount == 1) {textView_programming.setText(R.string.slide2_programming); slideCount++;}
		else if(slideCount == 2) {textView_programming.setText(R.string.slide3_programming); slideCount++;}
		else if(slideCount == 3) {textView_programming.setText(R.string.slide4_programming); slideCount++;}
		else if(slideCount == 4) {textView_programming.setText(R.string.slide5_programming); slideCount++;}
		else if(slideCount == 5) {textView_programming.setText(R.string.slide6_programming); slideCount++;}
	}
	
	public void backButton_programmingListener(View view) {
		if(slideCount == 6) {textView_programming.setText(R.string.slide5_programming); slideCount--;}
		else if(slideCount == 5) {textView_programming.setText(R.string.slide4_programming); slideCount--;}
		else if(slideCount == 4) {textView_programming.setText(R.string.slide3_programming); slideCount--;}
		else if(slideCount == 3) {textView_programming.setText(R.string.slide2_programming); slideCount--;}
		else if(slideCount == 2) {textView_programming.setText(R.string.slide1_programming); slideCount--;}
	}
	
	public void enterButton_programmingListener(View view) {
		message = "";
		enteredCommand = codeField.getText().toString();
		switch(enteredCommand){
		case("RCCar.drive(fast);"): 
			message = DRIVEFAST;
			console_programming.setText(message);
		break;
		case("RCCar.drive(medium);"):
			message = DRIVEMEDIUM;
			console_programming.setText(message);
		break;
		case("RCCar.drive(slow);"):
			message = DRIVESLOW;
			console_programming.setText(message);
		break;
		case("RCCar.turn(left);"):
			message = TURNLEFT;
			console_programming.setText(message);
		break;
		case("RCCAr.turn(right);"):
			message = TURNRIGHT;
			console_programming.setText(message);
		break;
		case("RCCar.turn(center);"):
			message = TURNCENTER;
			console_programming.setText(message);
		break;
		case("RCCar.stop();"):
			message = STOP;
			console_programming.setText(message);
		break;
		case("RCCar.reverse(fast);"):
			message = REVERSEFAST;
			console_programming.setText(message);
		break;
		case("RCDCar.reverse(medium);"):
			message = REVERSEMEDIUM;
			console_programming.setText(message);
		break;
		case("RCCAr.reverse(slow);"):
			message = REVERSESLOW;
			console_programming.setText(message);
		break;
		case("RCCar.drive();"):
			message = DRIVEMEDIUM;
			console_programming.setText(message);
		break;
		default: console_programming.setText("Command not understood");
		
		}
	}
	/**    <string name="slide6_programming">Great! Now you understand a little bit of the basics of programming! Feel free to just play around now! 
    Here are all of the commands you can try. 
    RCCar.drive(fast);
	RCCar.drive(medium);
	RCCar.drive(slow);
	RCCar.turn(left);
	RCCar.turn(right);
	RCCar.turn(straight);
	RCCar.stop();
	RCCar.reverse(fast);
	RCCar.reverse(medium);
	RCCar.reverse(slow);
</string>**/
	
	public static void UpdateMethod() {
		if((timeoutCounter >= maxTimeoutCount && maxTimeoutCount > -1)){
			
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
		if(pRfcommClient.getState() != BluetoothRfcommClient.STATE_CONNECTED) {
			return;
		}
		if (message.length() > 0) {
			byte[] send = message.getBytes();
			pRfcommClient.write(send);
		}
	}
	/**    <string name="slide6_programming">Great! Now you understand a little bit of the basics of programming! Feel free to just play around now! 
        Here are all of the commands you can try. 
        RCCar.drive(fast);
		RCCar.drive(medium);
		RCCar.drive(slow);
		RCCar.turn(left);
		RCCar.turn(right);
		RCCar.turn(straight);
		RCCar.stop();
		RCCar.reverse(fast);
		RCCar.reverse(medium);
		RCCar.reverse(slow);
	</string>**/
	
	// End Listeners
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_programming,
					container, false);
			enteredCommand = "";
			pRfcommClient = MainActivity.mRfcommClient;
			codeField = (EditText) rootView.findViewById(R.id.codeField);
			textView_programming = (TextView) rootView.findViewById(R.id.textView_programming);
			console_programming = (TextView) rootView.findViewById(R.id.console_programming);
			nextButton_programming = (Button) rootView.findViewById(R.id.nextButton_programming);
			backButton_programming = (Button) rootView.findViewById(R.id.backButton_programming);				
			
			textView_programming.setMovementMethod(new ScrollingMovementMethod());
			
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
			
			return rootView;
		}
	}
}
