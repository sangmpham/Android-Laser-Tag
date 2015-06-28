package llc.locallasertag;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
//import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayingPage extends Activity {
	
	private ArrayList<Integer> id = new ArrayList<Integer>();//id number for identification
	private ArrayList<Integer> playerDeath = new ArrayList<Integer>();
	TextView name1, name2, name3, name4, name5, name6 , teamName, win, loss;//to change the players name
	TextView death1, death2, death3, death4, death5, death6;//to change the number of death
	Button health1, health2, health3, health4, health5, health6;//to change the health Bar
	LinearLayout l1, l2, l3, l4, l5, l6;
	TextView player1Image, player2Image, player3Image ,player4Image, player5Image, player6Image;//to change the image
	ArrayList<Boolean> playerDead = new ArrayList<Boolean>();
	boolean light=false;
	float lightAmount = 300f;
	int numberPlayer=6, teamNumber=2;
	private Integer numWin=9, numLoss=4;
	Double subHealth =0.01;//how much to subtract the health by. Player start with 1
	public ArrayList<Double>playerHealth= new ArrayList<Double>();
	//Vibrator v =(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
	String teamNames="Dragon Lords";
	Uri notification;
	Ringtone r;
	double value;

	
	 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playingpage);
		SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
	    Sensor LightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
	     if(LightSensor != null){
	        	mySensorManager.registerListener(
	            LightSensorListener, 
	            LightSensor, 
	            SensorManager.SENSOR_DELAY_NORMAL);
	     }
	     try {
			 notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			 r = RingtoneManager.getRingtone(getApplicationContext(), notification);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		for(int i=0;i<6; i++){
			id.add(i);
			playerDeath.add(i);
			playerHealth.add(1.0);
			playerDead.add(false);
			
		}
		
		
		//passHealth1=1;//set the player health to full
		
		assignView(); //assign view 
		createPlayer(); // put the player on the screen
		setImageAvatar();
		setWin(numWin);
		setLoss(numLoss);
		setName();
		setDeath();
		for(int i=0; i<playerHealth.size(); i++)
			setPlayerHealth(playerHealth.get(i), id.get(i));
		setTeamName(teamNames);
		 
    
		
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
			    	 subtractLife(0);
			    	 r.play();

			     }
			    	
			     //else mWakeLock.release();
			    }
			   }
			     
			    };

	//subtract life from player health
	public void subtractLife(Integer id){
		playerHealth.set((int)id, playerHealth.get(id)-subHealth);
		setPlayerHealth(playerHealth.get(id), id);
	}
	public void setPlayerHealth(Double lessHealth, Integer playerNum){ //method change the player health must be pass number<=1
		DecimalFormat healthFormat = new DecimalFormat("######");
		
	if(playerNum == id.get(0)){//check which player health to change
		
		if(playerDead.get(0)==false){//if player is still alive then change health if not do nothing
			if(lessHealth< 0.0) lessHealth = 0.0;//change change bar to under 0
			health1.setScaleX(Float.parseFloat(lessHealth.toString()));//change health bar
			health1.setText(healthFormat.format(lessHealth*100).toString() + "%");//type percentage health on bar
			if(lessHealth<= 0.0){//if player die then set to true
				playerDead.set(0, true);
				player1Image.setText(" X");	//put an x on player pic
				playerDeath.set(0, playerDeath.get(0)+1);// add to death count
				death1.setText(playerDeath.get(0).toString());//set number of death to screen
			//v.vibrate(300);
			
			}
		}
	}
	
	if(playerNum == id.get(1)){
		if(playerDead.get(1)==false){
			if(lessHealth<=0.0) lessHealth = 0.0;
			health2.setScaleX(Float.parseFloat(lessHealth.toString()));
			health2.setText(healthFormat.format(lessHealth*100).toString() + "%");
			if(lessHealth==0.0){
				playerDead.set(1, true);
				player2Image.setText(" X");	
				playerDeath.set(1, playerDeath.get(1)+1);
				death2.setText(playerDeath.get(1).toString());
			}
		}
	}
	
	
	if(playerNum == id.get(2)){
		
		if(playerDead.get(2)==false){
			if(lessHealth<0.0) lessHealth = 0.0;
			health3.setScaleX(Float.parseFloat(lessHealth.toString()));
			health3.setText(healthFormat.format(lessHealth*100).toString() + "%");
			if(lessHealth==0.0){
				playerDead.set(2, true);
				player3Image.setText(" X");	
				playerDeath.set(2, playerDeath.get(2)+1);
				death3.setText(playerDeath.get(2).toString());
			}
		}
	}
	
	
	if(playerNum == id.get(3)){
		if(playerDead.get(3)==false){
			
			if(lessHealth<0.0) lessHealth = 0.0;
			health4.setScaleX(Float.parseFloat(lessHealth.toString()));
			health4.setText(healthFormat.format(lessHealth*100).toString() + "%");
			if(lessHealth==0.0){
				playerDead.set(3, true);
				player4Image.setText(" X");	
				playerDeath.set(3, playerDeath.get(3)+1);
				death4.setText(playerDeath.get(3).toString());
			}
		}
	}
	if(playerNum == id.get(4)){
		if(playerDead.get(4)==false){
			if(lessHealth<0.0) lessHealth = 0.0;
			health5.setScaleX(Float.parseFloat(lessHealth.toString()));
			//percentHealth = lessHealth*100;
			health5.setText(healthFormat.format(lessHealth*100).toString() + "%");
			if(lessHealth==0.0){
				playerDead.set(4, true);
				player5Image.setText(" X");	
				playerDeath.set(4, playerDeath.get(4)+1);
				death5.setText(playerDeath.get(4).toString());
			}
		}
	}
	
	
	if(playerNum == id.get(5)){
		if(playerDead.get(5)==false){
			if(lessHealth<0.0) lessHealth = 0.0;
			health6.setScaleX(Float.parseFloat(lessHealth.toString()));
			health6.setText(healthFormat.format(lessHealth*100).toString() + "%");
			if(lessHealth==0.0){
				playerDead.set(5, true);
				player6Image.setText(" X");	
				playerDeath.set(5, playerDeath.get(5)+1);
				death6.setText(playerDeath.get(5).toString());
			}
		}
	}
	
	
}
public void setWin(Integer w){
	win.setText(w.toString());
}
public void setLoss(Integer l){
	loss.setText(l.toString());
}
public void setImageAvatar(){//method changes the player avatar
	player1Image.setBackgroundResource(R.drawable.pic8);
	player2Image.setBackgroundResource(R.drawable.avatar);
	player3Image.setBackgroundResource(R.drawable.pic2);
	player4Image.setBackgroundResource(R.drawable.pic3);
	player5Image.setBackgroundResource(R.drawable.pic4);
	player6Image.setBackgroundResource(R.drawable.pic5);
}

//set the number of death
public void setDeath(){ 
	Integer playerNum=0;
	if(playerNum == id.get(0)){
		
	}
	if(playerNum == id.get(1)){
		
	}
	if(playerNum == id.get(2)){
		
	}
	if(playerNum == id.get(3)){
		
	}
	if(playerNum == id.get(4)){
	
	}
	if(playerNum == id.get(5)){
	
	}
	death1.setText(playerDeath.get(0).toString());
	death2.setText(playerDeath.get(1).toString());
	death3.setText(playerDeath.get(2).toString());
	death4.setText(playerDeath.get(3).toString());
	death5.setText(playerDeath.get(4).toString());
	death6.setText(playerDeath.get(5).toString());
}
//set the player name
public void setName(){
	name2.setText("Paul");
	name1.setText("Sang");
	name3.setText("Micheal");
	name4.setText("John");
	name5.setText("Cool Guy");
	name6.setText("Magic Man");
}
//set the team name
public void setTeamName(String name){
	teamName.setText(name);
}

//assign all the view to the respected controller
public void assignView(){
	teamName= (TextView)findViewById(R.id.txtTeamName1);
	
	name1 = (TextView)findViewById(R.id.txtPlayerName1);
	name2 = (TextView)findViewById(R.id.txtPlayerName2);
	name3 = (TextView)findViewById(R.id.txtPlayerName3);
	name4 = (TextView)findViewById(R.id.txtPlayerName4);
	name5 = (TextView)findViewById(R.id.txtPlayerName5);
	name6 = (TextView)findViewById(R.id.txtPlayerName6);
	
	death1 = (TextView)findViewById(R.id.death1);
	death2 = (TextView)findViewById(R.id.death2);
	death3 = (TextView)findViewById(R.id.death3);
	death4 = (TextView)findViewById(R.id.death4);
	death5 = (TextView)findViewById(R.id.death5);
	death6 = (TextView)findViewById(R.id.death6);
	
	//assign to actual xml 
	health1 = (Button)findViewById(R.id.healthBar1);
	health2 = (Button)findViewById(R.id.bar2);
	health3 = (Button)findViewById(R.id.bar3);
	health4 = (Button)findViewById(R.id.bar4);
	health5 = (Button)findViewById(R.id.bar5);
	health6 = (Button)findViewById(R.id.bar6);
	
	
	//set the health bar to the right of pic
	health1.setPivotX(1);
	health2.setPivotX(1);
	health3.setPivotX(1);
	health4.setPivotX(1);
	health5.setPivotX(1);
	health6.setPivotX(1);
	
	health1.setLeft(1);
	health2.setLeft(1);
	health3.setLeft(1);
	health4.setLeft(1);
	health5.setLeft(1);
	health6.setLeft(1);
	
	
	//create the  image
	player1Image = (TextView)findViewById(R.id.avatar1);
	player2Image = (TextView)findViewById(R.id.avatar2);
	player3Image = (TextView)findViewById(R.id.avatar3);
	player4Image = (TextView)findViewById(R.id.avatar4);
	player5Image = (TextView)findViewById(R.id.avatar5);
	player6Image = (TextView)findViewById(R.id.avatar6);
	
	//set color of healthBar
		if(teamNumber == 1){
			health1.setBackgroundColor(Color.RED);
			health2.setBackgroundColor(Color.RED);
			health3.setBackgroundColor(Color.RED);
			health4.setBackgroundColor(Color.RED);
			health5.setBackgroundColor(Color.RED);
			health6.setBackgroundColor(Color.RED);
			
			//set the x when dead to red
			player1Image.setTextColor(Color.RED);
			player2Image.setTextColor(Color.RED);
			player3Image.setTextColor(Color.RED);
			player4Image.setTextColor(Color.RED);
			player5Image.setTextColor(Color.RED);
			player6Image.setTextColor(Color.RED);
		}
		else{
			health1.setBackgroundColor(Color.BLUE);
			health2.setBackgroundColor(Color.BLUE);
			health3.setBackgroundColor(Color.BLUE);
			health4.setBackgroundColor(Color.BLUE);
			health5.setBackgroundColor(Color.BLUE);
			health6.setBackgroundColor(Color.BLUE);
			
			//set the x when dead to blue
			player1Image.setTextColor(Color.BLUE);
			player2Image.setTextColor(Color.BLUE);
			player3Image.setTextColor(Color.BLUE);
			player4Image.setTextColor(Color.BLUE);
			player5Image.setTextColor(Color.BLUE);
			player6Image.setTextColor(Color.BLUE);
		}
	//match up win and loss xlm
	win = (TextView)findViewById(R.id.txtWin);
	loss = (TextView)findViewById(R.id.txtLoss);
	//match up LinearLayout
	l1 = (LinearLayout)findViewById(R.id.L1);
	l2 = (LinearLayout)findViewById(R.id.L2);
	l3 = (LinearLayout)findViewById(R.id.L3);
	l4 = (LinearLayout)findViewById(R.id.L4);
	l5 = (LinearLayout)findViewById(R.id.L5);
	l6 = (LinearLayout)findViewById(R.id.L6);
	
}

//make each field visible depending on the number of player
public void createPlayer(){
	
	if(numberPlayer>=2){	
		l2.setVisibility(View.VISIBLE);
	}
	if(numberPlayer>=3){	
		l3.setVisibility(View.VISIBLE);
	}
	if(numberPlayer>=4){	
		l4.setVisibility(View.VISIBLE);
	}
	if(numberPlayer>=5){	
		l5.setVisibility(View.VISIBLE);
	}
	if(numberPlayer>=6){	
		l6.setVisibility(View.VISIBLE);
	}
	
	
}

		
		
		
}
