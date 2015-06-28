package llt.locallasertag.nongame;

import java.util.LinkedList;
import java.util.List;

import llt.locallasertag.R;
import llt.locallasertag.game.MainActivity;
import llt.locallasertag.util.JSONfunctions;

import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

public class Setting extends Activity {
   private JSONObject jObject;
   private Activity SettingActivity;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.app_settings);
      SettingActivity = this;

      GridView gView = (GridView) findViewById(R.id.avatar);

      gView.setOnItemClickListener(new OnItemClickListener() { //click listener for the avatars 
         @Override
         public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            EditText txtIGN = (EditText) findViewById(R.id.ingamename);
            String newIGN = txtIGN.getText().toString();
            new LongOperation().execute("" + position, newIGN);

            Intent intent = new Intent(SettingActivity, MainActivity.class);
            startActivity(intent);

         }
      });

      gView.setAdapter(new ImageAdapter(this));
   }

   private class LongOperation extends AsyncTask<String, Void, String> {

      @Override
      protected String doInBackground(String... params) {

         String temp;
         String name = getUsername();

         temp = "http://www.jonquybao.com/LLT/feedurls/editplayer.php?username=" + name; //TODO change to edituser.php
         temp += "&IGN=" + params[1];
         temp += "&avatar=" + (Integer.parseInt(params[0])+1);
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

   public class ImageAdapter extends BaseAdapter {
      private Context mContext;

      public int getCount() {
         return mThumbIds.length;
      }

      public Object getItem(int position) {
         return mThumbIds[position];
      }

      public long getItemId(int position) {
         return 0;
      }

      public ImageAdapter(Context c) {
         mContext = c;
      }

      public View getView(int position, View convertView, ViewGroup parent) {
         ImageView imageView;
         if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
         } else {
            imageView = (ImageView) convertView;
         }
         imageView.setImageResource(mThumbIds[position]);
         return imageView;
      }

      private Integer[] mThumbIds = { R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10,
            R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15, R.drawable.a16, R.drawable.a17, R.drawable.a18, R.drawable.a19, R.drawable.a20, R.drawable.a21,
            R.drawable.a22, R.drawable.a23, R.drawable.a24, R.drawable.a25, R.drawable.a26, R.drawable.a27, R.drawable.a28, R.drawable.a29, R.drawable.a29, R.drawable.a30, R.drawable.a31,
            R.drawable.a32, R.drawable.a33, R.drawable.a34, R.drawable.a35 };

   }

   public void settingReturn(View v) {
      Intent intent = new Intent(this, MainActivity.class);
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
}
