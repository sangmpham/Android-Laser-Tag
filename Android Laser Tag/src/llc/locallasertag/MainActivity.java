package llc.locallasertag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button startButton, how;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      startButton = (Button)findViewById(R.id.startButton);
      how = (Button)findViewById(R.id.butHow);
      
      how.setOnClickListener(new OnClickListener() {
    	  public void onClick(View v){
    		  startActivity(new Intent(MainActivity.this, HowToPlay.class));
    	  }
      });
      startButton.setOnClickListener(new OnClickListener() {
    	  public void onClick(View v){
    		  startActivity(new Intent(MainActivity.this, GamePage.class));
    	  }
      });
   }
   
  
  
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {

      // Inflate the menu; this adds items to the action bar if it is present.
      //TODO getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();
      //TODO if (id == R.id.action_settings) {
      //TODO   return true;
      //TODO }
      return super.onOptionsItemSelected(item);
   }

   public void gameStart(View v) {
      Intent intent = new Intent(this, TimerActivity.class);
      startActivity(intent);
   }

   public void gameSettings(View v) {
      Intent intent = new Intent(this, App_Settings.class);
      startActivity(intent);
   }
}
