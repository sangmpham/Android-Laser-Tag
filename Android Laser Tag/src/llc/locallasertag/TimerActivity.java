package llc.locallasertag;

import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.content.*;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class TimerActivity extends Activity implements OnClickListener {	 	 
	 private CountDownTimer countDownTimer;
	 private boolean timerHasStarted = false;//start or not
	 private Button startB, changeB;//button 
	 public TextView text;//text box
	 public EditText newTimer;
	 //private final long seconds;//second to count down from
	 ///private final long startTime; //= seconds * 1000;
	 private final long interval = 1 * 1000;
	 public MediaPlayer myMedia;//play sound
	 public long playerTime;
	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_timer);
	  
	  addListenerOnButton();
	  
	  newTimer= (EditText)findViewById(R.id.newTime);
	  final long seconds = 5;//Long.parseLong(newTimer.getText().toString());
	  final long startTime = seconds*1000;
	  startB = (Button) this.findViewById(R.id.startB);//start button
	  startB.setOnClickListener(this);//listener for start button
	  text = (TextView) this.findViewById(R.id.timer);//timer display
	  countDownTimer = new MyCountDownTimer(startTime, interval);//instance of class 
	  text.setText(text.getText() + String.valueOf(startTime / 1000));//set initial time
	  myMedia = MediaPlayer.create(this, R.raw.count3);//to play sound countdown
	 }
	 //Button listener to change the timer
	 public void addListenerOnButton(){
		 changeB= (Button)findViewById(R.id.changeTime);//button to change time
		 changeB.setOnClickListener(new OnClickListener(){
			 public void onClick(View arg0){
				 
				 playerTime= Long.parseLong(newTimer.getText().toString());
				 final long startTime = playerTime*1000;
				 text.setText(String.valueOf(playerTime));//set initial time
				 countDownTimer = new MyCountDownTimer(startTime, interval);//instance of class
			 }
		 });
	 }
	 public void onClick(View v) {//do on click
	  if (!timerHasStarted) {//initial start button value
	   countDownTimer.start();//start the timer
	   timerHasStarted = true;
	   startB.setText("STOP");//change the button to stop
	  // if(startB.getText().toString() == "STOP")//sound file right now not sinc
		   //myMedia.pause();
	  } else {
	   countDownTimer.cancel();//if count down started then do if push again
	   timerHasStarted = false;//
	   startB.setText("RESTART");//button display restart
	  // if(startB.getText().toString() == "RESTART")
		  // myMedia.stop();
	  }
	 }
	 
	 public class MyCountDownTimer extends CountDownTimer {
	  public MyCountDownTimer(long startTime, long interval) {//constructor
	   super(startTime, interval);
	  }
	 
	  @Override
	  public void onFinish() {//when finish display
		  startActivity(new Intent(TimerActivity.this, OneForAll.class));//go to next page when 10 second left
	  }
	 
	  @Override
	  public void onTick(long millisUntilFinished) {//for each tick of second
		  if( millisUntilFinished / 1000 == 10){//when reach ten start sound
			  myMedia.start();//start sound
		  }
		  
		text.setText("" + millisUntilFinished / 1000);//display the countdown
		  
	  }
	 }
	 
	}