package llt.locallasertag.nongame;

import llt.locallasertag.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class BulletAnimation extends Activity {
	
	private ImageView bulletScreen;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bulletanimation);
		
		bulletScreen = (ImageView)findViewById(R.id.imbullet);
		bulletScreen.setBackgroundResource(R.drawable.animation);
		AnimationDrawable bulletAnimation =(AnimationDrawable) bulletScreen.getBackground();
		bulletScreen.setAlpha(.5f);
		bulletAnimation.start();
		Handler handler = new Handler();
		Runnable delayRunnable = new Runnable() {

		     @Override
		     public void run() {
		     //TODO Auto-generated method stub  

		   	  	finish();
		    }
		};      
		handler.postDelayed(delayRunnable, 700);
		
	}
}
