package llc.locallasertag;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OneForAll extends Activity{
	Button health ;
	Float healthAmount =1f, amountToSub=10f;
	int screenSize ;
	RelativeLayout oneScreen; 
	int lightAmount = 250;
	private TextView name;
	
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.oneforall);
		  name = (TextView)findViewById(R.id.playername);
		  health = (Button)findViewById(R.id.startButton);
		  oneScreen = (RelativeLayout)findViewById(R.id.RelativeLayout1);
		 screenSize = oneScreen.getHeight();
		// amountToSub = Float.parseFloat((screenSize/30)+"");
		 SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		    Sensor LightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		     if(LightSensor != null){
		        	mySensorManager.registerListener(
		            LightSensorListener, 
		            LightSensor, 
		            SensorManager.SENSOR_DELAY_NORMAL);
		     }
		     try {
				// notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				// r = RingtoneManager.getRingtone(getApplicationContext(), notification);
			} catch (Exception e) {
			    e.printStackTrace();
			}

	 }
	 
	public void setName(String A){
		name.setText(A);
	}
	//light sensor listener
		private final SensorEventListener LightSensorListener = new SensorEventListener(){

			 @Override
			   public void onAccuracyChanged(Sensor sensor, int accuracy) {
			    // TODO Auto-generated method stub
			    
			   }
			 @SuppressWarnings("deprecation")
			 @Override
			   public void onSensorChanged(SensorEvent event) {
				    if(event.sensor.getType() == Sensor.TYPE_LIGHT){
				   
				     if (event.values[0]>lightAmount) {
				    	 subtractLife();
				    	// r.play();

				     }
				    	
				     //else mWakeLock.release();
				    }
				   }
				     
				    };
	private void subtractLife(){
		healthAmount = healthAmount - amountToSub;
		health.setScaleY(healthAmount);
	}
		//subtract life from player health
}
