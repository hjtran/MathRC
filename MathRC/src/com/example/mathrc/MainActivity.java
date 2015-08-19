package com.example.mathrc;


import com.example.mathrc.DeviceListActivity;
import com.example.mathrc.Joystick.JoystickView;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	// Message types sent from the BluetoothRfcommClient Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
    // Key names received from the BluetoothRfcommClient Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
	
	// Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
	
    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the RFCOMM services
    public static BluetoothRfcommClient mRfcommClient = null;
	
	// Layout View
	private static TextView BTStatus;
	private static Button BTConnectButton;
	private static Button reinforcementButton;
	private static Button learningButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
	       // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
        // If BT is not on, request that it be enabled.
    	if (!mBluetoothAdapter.isEnabled()){
    		Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    		startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    	}
    	
    	// Initialize the BluetoothRfcommClient to perform bluetooth connections
        mRfcommClient = new BluetoothRfcommClient(this, mHandler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
		.setTitle("Math Racer")
		.setMessage("Close the game?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
	
	public void reinforcementModeButtonListener(View view) {
		Intent intent = new Intent(this, TransitionActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void learningModeButtonListener(View view) {
		Intent intent = new Intent(this, LearningActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void connectButtonListener(View view) {
		Intent intent = new Intent(this, DeviceListActivity.class);
		startActivityForResult(intent,REQUEST_CONNECT_DEVICE);
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			// Initialize components for manipulation
			BTStatus = (TextView) rootView.findViewById(R.id.BTStatus);
			BTConnectButton = (Button) rootView.findViewById(R.id.BTConnectButton);
			reinforcementButton = (Button) rootView.findViewById(R.id.reinforcementButton);
			learningButton = (Button) rootView.findViewById(R.id.learningButton);
			
			// Disable buttons
			/**reinforcementButton.setEnabled(false);
			learningButton.setEnabled(false);
			**/
			return rootView;
		}
	}
	
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode){
    	case REQUEST_CONNECT_DEVICE:
    		// When DeviceListActivity returns with a device to connect
    		if (resultCode == Activity.RESULT_OK) {
    			// Get the device MAC address
    			String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
    			// Get the BLuetoothDevice object
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                mRfcommClient.connect(device);
    		}
    		break;
    	case REQUEST_ENABLE_BT:
    		// When the request to enable Bluetooth returns
    		if (resultCode != Activity.RESULT_OK) {
            	// User did not enable Bluetooth or an error occurred
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
    		break;
    	}
    }
	
	   private final Handler mHandler = new Handler() {
	    	@Override
	        public void handleMessage(Message msg) {

	    		switch (msg.what) {
	    		case MESSAGE_STATE_CHANGE:
	    			switch (msg.arg1) {
	    			case BluetoothRfcommClient.STATE_CONNECTED:
	    				BTStatus.setText(R.string.title_connected_to);
	    				BTStatus.append(" " + mConnectedDeviceName);
	    				reinforcementButton.setEnabled(true);
	    				learningButton.setEnabled(true);
	    				break;
	    			case BluetoothRfcommClient.STATE_CONNECTING:
	    				BTStatus.setText(R.string.title_connecting);
	    				break;
	    			case BluetoothRfcommClient.STATE_NONE:
	    				BTStatus.setText(R.string.title_not_connected);
	    				break;
	    			}
	    			break;
	    		case MESSAGE_READ:
	    			// byte[] readBuf = (byte[]) msg.obj;
	    			// int data_length = msg.arg1;
	    			break;
	    		case MESSAGE_DEVICE_NAME:
	    			// save the connected device's name
	                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
	                Toast.makeText(getApplicationContext(), "Connected to "
	                        + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
	    			break;
	    		case MESSAGE_TOAST:
	    			Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
	                        Toast.LENGTH_SHORT).show();
	    			break;
	    		}
	    	}
	    };
}
