package llt.locallasertag.nongame;

import llt.locallasertag.R;
import llt.locallasertag.game.PlayingPage;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TimerActivity extends Activity implements OnClickListener {	 	 
	 private CountDownTimer countDownTimer;
	 private boolean timerHasStarted = false;//start or not
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
	  	  
	  final long seconds = 6;//Long.parseLong(newTimer.getText().toString());
	  final long startTime = seconds*1000;
	  text = (TextView) this.findViewById(R.id.timer);//timer display
	  countDownTimer = new MyCountDownTimer(startTime, interval);//instance of class 
	  text.setText(text.getText() + String.valueOf(startTime / 1000));//set initial time
	  myMedia = MediaPlayer.create(this, R.raw.count);//to play sound countdown
	  playerTime= Long.parseLong("6");
	  text.setText(String.valueOf(playerTime));//set initial time
	  countDownTimer.start();//start the timer
	  timerHasStarted = true;
	 }
	 //Button listener to change the timer
	
 
	 public class MyCountDownTimer extends CountDownTimer {
	  public MyCountDownTimer(long startTime, long interval) {//constructor
	   super(startTime, interval);
	  }
	 
	  @Override
	  public void onFinish() {//when finish display
		  startActivity(new Intent(TimerActivity.this, PlayingPage.class));//go to next page when 10 second left
	  }
	 
	  @Override
	  public void onTick(long millisUntilFinished) {//for each tick of second
			  myMedia.start();//start sound  
			  text.setText("" + millisUntilFinished / 1000);//display the countdown	  
	  }
	 }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	public void onBackPressed() {
	}
	}