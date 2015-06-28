package llt.locallasertag.game;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import llt.locallasertag.R;
import llt.locallasertag.nongame.HowToPlay;
import llt.locallasertag.nongame.TimerActivity;
import llt.locallasertag.util.JSONfunctions;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
public class Room extends Activity {
	private ListView blueTeam;
	private ListView redTeam;
	private JSONObject jObject;
	ArrayAdapter<String> blueArrayAdapter;
	ArrayAdapter<String> redArrayAdapter;
	private Timer timer;
	//private MyTimerTask myTimerTask;
	private  List<String> blueList;
	private  List<String> redList;
	private  int player_id;
	private String team = "RED";
	private int creator_id;
	private Boolean firstTime =true;
	private Button start_button;
	private String start_game= "NO";
	private Activity current_activity;
	private Boolean started=false;
	private Intent intent;
	 private Handler handler = new Handler();
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.room);
      int value=99;
      Bundle extras = getIntent().getExtras();
      if (extras != null) {
           value = extras.getInt("position");
      }
      Log.d("position", value+"");
      current_activity=this;
      SharedPreferences settings=  PreferenceManager.getDefaultSharedPreferences(this);
       player_id = (int)settings.getInt("pid", 0); 
      blueTeam = (ListView) findViewById(R.id.blueTeam);
      redTeam = (ListView) findViewById(R.id.redTeam);
      blueList = new ArrayList<String>();
      blueList.add("Blue Slot 1");
      blueList.add("Blue Slot 2");
      blueList.add("Blue Slot 3");
      blueList.add("Blue Slot 4");
      redList = new ArrayList<String>();
      redList.add("RED Slot 1");
      redList.add("RED Slot 2");
      redList.add("RED Slot 3");
      redList.add("RED Slot 4");
      blueArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, blueList);
      blueTeam.setAdapter(blueArrayAdapter);
      redArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, redList);
      redTeam.setAdapter(redArrayAdapter);
      // TODO link players to the list for red and blue team
      start_button = (Button) findViewById(R.id.start);  
      Button blueButton = (Button) findViewById(R.id.joinblue);  
      blueButton.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
            team="BLUE";
           }
        });
      Button redButton = (Button) findViewById(R.id.joinred);  
      redButton.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
              team="RED";
             }
          });
      intent = new Intent(current_activity, TimerActivity.class);
            runnable.run();
   }
   private Runnable runnable = new Runnable() 
   {
       public void run() 
       {
            //
            // Do the stuff
            if(started==false){
			 if(start_game.equals("YES")){
				 Log.e("CREATE", "CREATE");
				  	new LongOperation().execute("");
            	      startActivity(intent);
            	      started=true;
            	    //  handler.removeCallbacks(runnable);
               }else{
            	   new LongOperation().execute("");
               }
            }
            handler.postDelayed(this, 500);
       }
   };
   @Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	/* timer = new Timer();
	  myTimerTask = new MyTimerTask();
	  timer.schedule(myTimerTask, 0, 100);
	  intent = new Intent(current_activity, TimerActivity.class);*/
}
	/*private class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
				Log.d("START", start_game);
				if(started==false){
					 if(start_game.equals("YES")){
						 Log.e("CREATE", "CREATE");
	            	      startActivity(intent);
		            	      timer.cancel();
		            	      myTimerTask.cancel();
		            	      started=true;
		               }else{
		            	   new LongOperation().execute("");
		               }
				}
				}
			});
		}
	}*/
   private class LongOperation extends AsyncTask<String, Void, String> {
	      @Override
	      protected String doInBackground(String... params) {
	         jObject = JSONfunctions.getJSONfromURL("http://www.jonquybao.com/LLT/feedurls/room_service.php?gid=1&pid="+player_id+"&team="+team+"&start="+start_game);
	         return "Executed";
	      }
	      @Override
	      protected void onPostExecute(String result) {
	    	  reset();
	         try {
	            Log.d("JSON", jObject.toString());
	            JSONArray jArray;
	            jArray = jObject.getJSONArray("room");
	               JSONObject object = jArray.getJSONObject(0);
	            	  JSONArray players;
	            	  int blueIndex=0;
	            	  int redIndex=0;
	            	  creator_id= object.getInt("creator_id");
	            	  if(object.getString("start").equals( "YES"))
	            	  start_game = object.getString("start");
	            	  Log.i("START GAME VAL", object.getString("start"));
	            	  players = object.getJSONArray("players");
	                  for (int i = 0; i < players.length(); i++) {
	                      JSONObject player = players.getJSONObject(i);
	                      Log.d("", player.getString("display_name"));
	                     if(player.getString("team").equals("RED"))
	                     {
	                    	 redList.set(redIndex, player.getString("display_name"));
	                    	 redIndex++;
	                     }else if(player.getString("team").equals("BLUE")){
	                    	 blueList.set(blueIndex, player.getString("display_name"));
	                    	 blueIndex++;
	                     }
	                   }
	                  if(firstTime && (creator_id!=player_id)){
	                	 start_button.setVisibility(View.GONE);
	                	  firstTime= false;
	                  }
	         } catch (JSONException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         }
	         redArrayAdapter.notifyDataSetChanged();
	         blueArrayAdapter.notifyDataSetChanged();
	         System.out.println("onpostexecute");
	      }
	      private void reset(){
	    	  for( int i =0; i<blueList.size(); i++){
	    		  blueList.set(i, "Blue Slot "+(i+1));
	    	  }
	    	  for(int j =0; j<redList.size(); j++){
	    		  redList.set(j, "Red Slot "+(j+1));
	    	  }
	      }
	      @Override
	      protected void onPreExecute() {
	      }
	      @Override
	      protected void onProgressUpdate(Void... values) {
	      }
   }
   public void StartGame(View v)
   {
	   start_game="YES";
   }
}