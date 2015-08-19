package com.example.mathrc;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class PhysicsActivity extends Activity {

	
	private static int slideCount = 1;
	private static BluetoothRfcommClient pRfcommClient;
	private static TextView textView_physics;
	private static Button nextButton_physics;
	private static Button backButton_physics;
	private static SeekBar torquebar_physics;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physics);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.physics, menu);
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

	public void nextButton_physicsListener(View view) {
		if(slideCount == 1) {textView_physics.setText(R.string.slide2_physics); slideCount++;}
		else if(slideCount == 2) {textView_physics.setText(R.string.slide3_physics); slideCount++;}
		else if(slideCount == 3) {textView_physics.setText(R.string.slide4_physics); slideCount++;}
		else if(slideCount == 4) {textView_physics.setText(R.string.slide5_physics); slideCount++;}
		else if(slideCount == 5) {textView_physics.setText(R.string.slide6_physics); slideCount++;}
		else if(slideCount == 6) {textView_physics.setText(R.string.slide7_physics); slideCount++;}
		else if(slideCount == 7) {textView_physics.setText(R.string.slide8_physics); slideCount++;}
		else if(slideCount == 8) {textView_physics.setText(R.string.slide9_physics); slideCount++;}
		else if(slideCount == 9) {textView_physics.setText(R.string.slide10_physics); slideCount++;}
		else if(slideCount == 10) {textView_physics.setText(R.string.slide11_physics); slideCount++;}
	}
	
	public void backButton_physicsListener(View view) {
		if(slideCount == 11) {textView_physics.setText(R.string.slide10_physics); slideCount--;}
		else if(slideCount == 10) {textView_physics.setText(R.string.slide9_physics); slideCount--;}
		else if(slideCount == 9) {textView_physics.setText(R.string.slide8_physics); slideCount--;}
		else if(slideCount == 8) {textView_physics.setText(R.string.slide7_physics); slideCount--;}
		else if(slideCount == 7) {textView_physics.setText(R.string.slide6_physics); slideCount--;}
		else if(slideCount == 6) {textView_physics.setText(R.string.slide5_physics); slideCount--;}
		else if(slideCount == 5) {textView_physics.setText(R.string.slide4_physics); slideCount--;}
		else if(slideCount == 4) {textView_physics.setText(R.string.slide3_physics); slideCount--;}
		else if(slideCount == 3) {textView_physics.setText(R.string.slide2_physics); slideCount--;}
		else if(slideCount == 2) {textView_physics.setText(R.string.slide1_physics); slideCount--;}
	}
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_physics,
					container, false);
			textView_physics = (TextView) rootView.findViewById(R.id.textView_physics);
			return rootView;
		}
	}
}
