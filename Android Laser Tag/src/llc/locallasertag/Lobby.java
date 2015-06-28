package llc.locallasertag;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class Lobby extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lobby);

      //getActionBar().setDisplayHomeAsUpEnabled(true);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // TODO Auto-generated method stub
      if (item.getItemId() == android.R.id.home) {
         finish();
      }
      return super.onOptionsItemSelected(item);
   }
}
