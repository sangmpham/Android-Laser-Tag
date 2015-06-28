package llt.locallasertag.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import llt.locallasertag.R;
import llt.locallasertag.background.DownloadInfo;
import llt.locallasertag.background.DownloadInfoArrayAdapter;
import llt.locallasertag.background.Player;
import llt.locallasertag.nongame.BulletAnimation;
import llt.locallasertag.util.JSONfunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayingPage extends Activity {

	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Player> enemyPlayers = new ArrayList<Player>();
	private ArrayList<Player> AllPlayers = new ArrayList<Player>();
	private RelativeLayout screen;
	private TextView playerText, teamText, scoreText;
	public MediaPlayer gunSound;//play sound
	private JSONObject jObject;
	private Timer timer;
	private MyTimerTask myTimerTask;
	boolean firstTime = true;
	private DownloadInfo currentInfo;
	private List<DownloadInfo> downloadInfo;
	private List<DownloadInfo> downloadInfo2;
	private List<DownloadInfo> downloadInfo3;
	Vibrator V;
	DownloadInfoArrayAdapter firstArrayAdapter;
	DownloadInfoArrayAdapter secondArrayAdapter;
	DownloadInfoArrayAdapter enemyArrayAdapter;
	Uri notification;
	Ringtone r;
	float lightAmount = 300f;
	Double subHealth = 1.0;
	Player myplayer;
	int player_id;
	String player_team;
	ImageView imView;
	int red_score=0, blue_score=0, backgroundColor;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.playingpage2);
		//screen = (RelativeLayout)findViewById(R.id.txtscreen);
		V=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		gunSound = MediaPlayer.create(this, R.raw.gunshot);//to play sound countdown
		gunSound.setLooping(true);
		
		try {
			gunSound.prepare();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		gunSound.start();
		gunSound.pause();
	
		teamText =(TextView)findViewById(R.id.Team);
		scoreText=(TextView)findViewById(R.id.Score);
		//backgroundColor = scoreText.g
		SensorManager mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		Sensor LightSensor = mySensorManager
				.getDefaultSensor(Sensor.TYPE_LIGHT);
		if (LightSensor != null) {
			mySensorManager.registerListener(LightSensorListener, LightSensor,
					SensorManager.SENSOR_DELAY_NORMAL);
		}
		try {
			notification = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			r = RingtoneManager.getRingtone(getApplicationContext(),
					notification);
		} catch (Exception e) {
			e.printStackTrace();
		}
		myplayer = new Player();
		currentInfo = new DownloadInfo("ME", 100,1);
		ListView listView = (ListView) findViewById(R.id.downloadListView);

		downloadInfo = new ArrayList<DownloadInfo>();
		downloadInfo.add(currentInfo);
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		player_id = (int)settings.getInt("pid", 0); 
		player_team =settings.getString("team", "n/a"); 
		
		imView = (ImageView) findViewById(R.id.headStatus);

		RelativeLayout rl = (RelativeLayout) findViewById(R.id.txtscreen);
		if(player_team.equals("RED")){
			 rl.setBackgroundColor(Color.parseColor("#800000"));
		}
		else	
			 rl.setBackgroundColor(Color.parseColor("#1a2a5a"));
		/*
		 * 
		 * for(int i = 0; i < 1; ++i) { downloadInfo.add(currentInfo); }
		 */
		
		// TODO set bg color to red if on red team

		firstArrayAdapter = new DownloadInfoArrayAdapter(
				getApplicationContext(), R.id.downloadListView, downloadInfo);
		listView.setAdapter(firstArrayAdapter);

		ListView listView2 = (ListView) findViewById(R.id.downloadListView2);
		ListView enemyList = (ListView) findViewById(R.id.enemyTeam);
		
		downloadInfo2 = new ArrayList<DownloadInfo>();
		downloadInfo3 = new ArrayList<DownloadInfo>();
		/*
		 * for(int i = 0; i < 4; ++i) { downloadInfo2.add(new
		 * DownloadInfo("File " + i, 100)); }
		 */

		secondArrayAdapter = new DownloadInfoArrayAdapter(
				getApplicationContext(), R.id.downloadListView, downloadInfo2);
		listView2.setAdapter(secondArrayAdapter);
		
		enemyArrayAdapter = new DownloadInfoArrayAdapter(
				getApplicationContext(), R.id.downloadListView, downloadInfo3);
		enemyList.setAdapter(enemyArrayAdapter);

	
		timer = new Timer();
		myTimerTask = new MyTimerTask();

		timer.schedule(myTimerTask, 0, 100);

		
	}

	private class MyTimerTask extends TimerTask {

		@Override
		public void run() {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					
				

					new LongOperation().execute("");
				}
			});
		}

	}

	// TALK TO SERVER START
	private class LongOperation extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String temp;
			//temp = "http://www.jonquybao.com/LLT/feedurls/all_players.php?pid=1&health=100";

			if (firstTime) {
				temp = "http://www.jonquybao.com/LLT/feedurls/all_players.php?pid="+player_id+"&health=100";
				
			} else
				temp = "http://www.jonquybao.com/LLT/feedurls/all_players.php?pid="+player_id+"&health="+ myplayer.getHealth();
			// +Integer.parseInt((playerHealth.get(0)*100)+"")

		//	Log.d("LOOK", temp);
			jObject = JSONfunctions.getJSONfromURL(temp);

			return "Executed";
		}

		@Override
		protected void onPostExecute(String result) {

		//	Log.d("JSON", jObject.toString());
			try {
			//	Log.d("JSON", jObject.toString());
				JSONArray jArray;

				jArray = jObject.getJSONArray("players");

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject object = jArray.getJSONObject(i);
					Player p = new Player();

					if (object.has("pid")) {
						p.setID(Integer.parseInt(object.getString("pid")));

					}

					if (object.has("display_name")) {
						p.setIGN(object.getString("display_name"));

					}
					if (object.has("health")) {
						p.setHealth(Double.parseDouble(object
								.getString("health")));

					}
					if (object.has("team")) {
						p.setTeam(object
								.getString("team"));

					}
					if(object.has("avatar"))
					{
						p.setAvatar(Integer.parseInt(object.getString("avatar")));
					}

					if (firstTime) {
						AllPlayers.add(p);
						
						if (object.getString("username").equals(getUsername())) {
							Log.d("EPIC", object.getString("username"));
							myplayer.setID(p.getId());
							myplayer.setIGN(p.getIGN());
							myplayer.setAvatar(p.getAvatar());

						} else {
							if( player_team.equals(object.getString("team")))
								players.add(p);
							else
								enemyPlayers.add(p);
						}
						
						
					} else {
						AllPlayers.get(i).setHealth(p.getHealth());
						if (!object.getString("username").equals(getUsername()) && i < players.size() ) {
							players.get(getIndexofPID(p.getId())).setHealth(p.getHealth());
						}
					}

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			if(firstTime){
				Log.d("NAME", "NAME");
				downloadInfo.get(0).setFilename(myplayer.getIGN());
				downloadInfo.get(0).setAvatar(myplayer.getAvatar());
				firstArrayAdapter.notifyDataSetChanged();
			}
			
			if(firstTime){
				//("FIRST TIME", "FIRSTTIME");
				for (int i = 0; i < players.size(); i++) {
					DownloadInfo currentInfo = new DownloadInfo(players.get(i)
							.getIGN(), 100, players.get(i).getAvatar());
					currentInfo.setProgress((int) players.get(i).getHealth());
					downloadInfo2.add(currentInfo);
				}
				secondArrayAdapter.notifyDataSetChanged();
				
				for (int i = 0; i < enemyPlayers.size(); i++) {
					DownloadInfo currentInfo = new DownloadInfo(enemyPlayers.get(i)
							.getIGN(), 100, enemyPlayers.get(i).getAvatar());
					currentInfo.setProgress((int) enemyPlayers.get(i).getHealth());
					downloadInfo3.add(currentInfo);
				}
				enemyArrayAdapter.notifyDataSetChanged();
			}else{


				//Log.d("NOT", players.size()+"");
				for (int i = 0; i < players.size(); i++) {
					downloadInfo2.get(i).setProgress((int)players.get(i).getHealth());					
					secondArrayAdapter.notifyDataSetChanged();										
				}
				for (int i = 0; i < enemyPlayers.size(); i++) {
					if(downloadInfo3.get(i).getProgress() == 0)
					downloadInfo3.get(i).setProgress((int)enemyPlayers.get(i).getHealth());					
				enemyArrayAdapter.notifyDataSetChanged();										
				}
			}
			firstTime = false;
			updateScore();
			

		}
		private void updateScore(){
			red_score=0;
			blue_score=0;
			for (int i = 0; i < AllPlayers.size(); i++) {
				Log.d("HEALTH", ((int)AllPlayers.get(i).getHealth())+"");
				
				if(myplayer.getHealth() == 0 || myplayer.getHealth() < 0)
					imView.setImageResource(R.drawable.dead);
				
				if( AllPlayers.get(i).getTeam().equals("RED") && ((int)AllPlayers.get(i).getHealth())==0 ){
					blue_score=blue_score+1;
				}else if(AllPlayers.get(i).getTeam().equals("BLUE") && ((int)AllPlayers.get(i).getHealth())==0){
					red_score= red_score+1;
				}
			}
			Log.d("------", "---");
			TextView red = (TextView) findViewById(R.id.redscore);
			red.setText(red_score+"");
			TextView blue = (TextView) findViewById(R.id.bluescore);
			blue.setText(blue_score+"");
			
		}
		private int getIndexofPID(int pid){
			int val=0;
				for(int i =0; i<players.size(); i++){
					if(players.get(i).getId()==pid){
						val =i;
					}
				}
			
			return val;
		}
		
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

	// TALK TO SERVER END

	public String getUsername() {
		AccountManager manager = AccountManager.get(this);
		Account[] accounts = manager.getAccountsByType("com.google");
		List<String> possibleEmails = new LinkedList<String>();

		for (Account account : accounts) {
			// TODO: Check possibleEmail against an email regex or treat
			// account.name as an email address only for certain account.type
			// values.
			possibleEmails.add(account.name);
		}

		if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
			String email = possibleEmails.get(0);
			String[] parts = email.split("@");
			if (parts.length > 0 && parts[0] != null)
				return parts[0];
			else
				return null;
		} else
			return null;
	}

	// light sensor listener
	private final SensorEventListener LightSensorListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

		@SuppressWarnings("deprecation")
		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

				if (event.values[0] > lightAmount && myplayer.getHealth()>0) {
					Intent intent = new Intent(getBaseContext(), BulletAnimation.class);
					startActivity(intent);
					V.vibrate(2000);
					if(myplayer.getHealth() > 88 && myplayer.getHealth() < 100)
						imView.setImageResource(R.drawable.face2);
					else if(myplayer.getHealth() > 76)
						imView.setImageResource(R.drawable.face3);
					else if(myplayer.getHealth() > 64)
						imView.setImageResource(R.drawable.face4);
					else if(myplayer.getHealth() > 50)
						imView.setImageResource(R.drawable.face5);
					else if(myplayer.getHealth() > 40)
						imView.setImageResource(R.drawable.face6);
					else if(myplayer.getHealth() > 20)
						imView.setImageResource(R.drawable.face7);
					else if(myplayer.getHealth() < 20 && myplayer.getHealth() > 0)
						imView.setImageResource(R.drawable.face8);				
						
					
					
					myplayer.setHealth(myplayer.getHealth() - subHealth);
					if(!gunSound.isPlaying())
						gunSound.start();
					downloadInfo.get(0).setProgress((int) myplayer.getHealth());
					firstArrayAdapter.notifyDataSetChanged();

				}
				else{
					if(gunSound.isPlaying())
						gunSound.pause();					
				}

			}

		}

	};

	public void onBackPressed() {
		 final Intent startMain = new Intent(this, MainActivity.class);
		new AlertDialog.Builder(this)
	    .setTitle("Exit Game")
	    .setMessage("Are you sure you want to quit?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete	   	    	      	  
		        startActivity(startMain);
		        finish();
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();

	}
}
