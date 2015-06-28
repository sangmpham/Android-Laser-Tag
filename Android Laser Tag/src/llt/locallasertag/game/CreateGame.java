package llt.locallasertag.game;

import java.util.LinkedList;
import java.util.List;

import llt.locallasertag.R;
import llt.locallasertag.util.JSONfunctions;

import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateGame extends Activity {
   private Spinner gameType, numPlayer;
   private EditText txtGameName;
   private int gameIndex;
   private JSONObject jObject;
   private Button done;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.creategame);

      gameType = (Spinner) findViewById(R.id.chooseGame);
      numPlayer = (Spinner) findViewById(R.id.spinNumberPlayer);
      txtGameName = (EditText) findViewById(R.id.txtGameName);
      done = (Button) findViewById(R.id.txtDone);
      done.setEnabled(false);

      txtGameName.addTextChangedListener(new TextWatcher() {
         @Override
         public void afterTextChanged(Editable arg0) {
            enableSubmitIfReady();
         }

         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
         }
      });
   }

   public void enableSubmitIfReady() { // disables create game button unless game name is longer then 2 characters

      boolean isReady = txtGameName.getText().toString().length() > 2;

      if (isReady) {
         done.setEnabled(true);
      } else {
         done.setEnabled(false);
      }
   }

   public void GameCreate(View v) {
      new LongOperation().execute("");
      Intent intent = new Intent(this, Room.class);
      startActivity(intent);
   }

   public String getUsername() {
      AccountManager manager = AccountManager.get(this);
      Account[] accounts = manager.getAccountsByType("com.google");
      List<String> possibleEmails = new LinkedList<String>();

      for (Account account : accounts) {
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

   private class LongOperation extends AsyncTask<String, Void, String> {

      @Override
      protected String doInBackground(String... params) {

         String temp;
         String name = getUsername();
         String gameName = txtGameName.getText().toString();
         gameName = gameName.replaceAll(" ", "_"); //because php cannot handle spaces

         temp = "http://www.jonquybao.com/LLT/feedurls/create_game.php?name=" + gameName;
         temp += "&type=" + gameIndex;
         temp += "&creator=" + name;
         jObject = JSONfunctions.getJSONfromURL(temp);
         return "Executed";
      }

      @Override
      protected void onPostExecute(String result) {
         /*
         try {
            JSONArray jArray;
            jArray = jObject.getJSONArray("player");

            for (int i = 0; i < jArray.length(); i++) {
               JSONObject object = jArray.getJSONObject(i);

               if (object.has("pid")) {
                  SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(SettingActivity);
                  Editor edit = settings.edit();
                  edit.putInt("pid", Integer.parseInt(object.getString("pid")));
                  edit.putString("team", object.getString("team"));
                  edit.apply();

               }
            }
         } catch (JSONException e) {
            e.printStackTrace();
         }
         */
      }

      @Override
      protected void onPreExecute() {
      }

      @Override
      protected void onProgressUpdate(Void... values) {
      }
   }
}
