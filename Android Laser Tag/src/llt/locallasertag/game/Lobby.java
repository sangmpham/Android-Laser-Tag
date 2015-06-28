package llt.locallasertag.game;

import java.util.ArrayList;
import java.util.List;

import llt.locallasertag.R;
import llt.locallasertag.util.JSONfunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Lobby extends Activity {

   private ListView lv;
   private JSONObject jObject;
   ArrayAdapter<String> arrayAdapter;
   private EditText sb;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lobby);

      lv = (ListView) findViewById(R.id.gameList);
      sb = (EditText) findViewById(R.id.searchbar);
      Button createGame = (Button) findViewById(R.id.btnCreateGame);

      sb.addTextChangedListener(new TextWatcher() {

         @Override
         public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            Lobby.this.arrayAdapter.getFilter().filter(cs); // When user changed the Text
         }

         @Override
         public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
         }

         @Override
         public void afterTextChanged(Editable arg0) {
         }
      });

      // Instanciating an array list (you don't need to do this, you already have yours).
      List<String> your_array_list = new ArrayList<String>();

      // This is the array adapter, it takes the context of the activity as a
      // first parameter, the type of list view as a second parameter and your
      // array as a third parameter.
      arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, your_array_list);

      new LongOperation().execute("");

      lv.setAdapter(arrayAdapter);

      lv.setOnItemClickListener(new OnItemClickListener() {
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            joinGame(view);
         }
      });

      createGame.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
            startActivity(new Intent(Lobby.this, CreateGame.class));
         }
      });
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == android.R.id.home)
         finish();

      return super.onOptionsItemSelected(item);
   }

   public void joinGame(View v) {
      Intent intent = new Intent(this, Room.class);
      startActivity(intent);
   }

   private class LongOperation extends AsyncTask<String, Void, String> {
      @Override
      protected String doInBackground(String... params) {
         jObject = JSONfunctions.getJSONfromURL("http://www.jonquybao.com/LLT/feedurls/view_games.php");
         return "Executed";
      }

      @Override
      protected void onPostExecute(String result) {
         try {
            Log.d("JSON", jObject.toString());
            JSONArray jArray;

            jArray = jObject.getJSONArray("games");

            for (int i = 0; i < jArray.length(); i++) {
               JSONObject object = jArray.getJSONObject(i);
               if (object.has("name"))
                  arrayAdapter.add(object.getString("name"));
            }
         } catch (JSONException e) {
            e.printStackTrace();
         }
         arrayAdapter.notifyDataSetChanged();
         System.out.println("onpostexecute");
      }

      @Override
      protected void onPreExecute() {
      }

      @Override
      protected void onProgressUpdate(Void... values) {
      }
   }
}
