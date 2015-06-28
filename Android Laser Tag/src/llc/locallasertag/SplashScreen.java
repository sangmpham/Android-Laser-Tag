package llc.locallasertag;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		
		TimerTask pause = new TimerTask(){
			public void run(){
				finish();
				startActivity(new Intent(SplashScreen.this, MainActivity.class));//go to next page 
			}
		};
		Timer splash = new Timer();
		splash.schedule(pause, 1000);
		
		
	}
	
	
}
