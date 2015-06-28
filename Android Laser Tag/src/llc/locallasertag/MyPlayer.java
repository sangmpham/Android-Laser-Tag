package llc.locallasertag;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/*
 * Player Class
 * 
 * Used to create a player, which holds attributes for the player logged onto the phone running the app
 */

public class MyPlayer extends Player {// implements ActionListener{

   int avatarNum = 1; // for avatar integers 1-35 correspond to a preloaded image, integer 0 corresponds the user had uploaded his/her own image
   //Image avatar = new IconImage();
   private String IGN;
   private String id; //used to identify player server side
   private int health = 100; //is reset to 100 when game begins
   private int wins, loses; //total wins and loses of the player
   String FILENAME = "player_data";
   int MILLI_FOR_DAMAGE = 250; //number of milliseconds needed of light above MINIMUM_LIGHT to decrease health by 1% 
   int MINIMUM_LIGHT = 400; //amount of light needed to register a hit
   Long start = 0L; //start = system nano time when player sensor went above minimum light

   public MyPlayer() { // creates player from saved file OR a default player if file not found
      if (!loadPlayer()) { //if load player fails, create default player
         //id = random string?
         IGN = "NEWBIE";
         avatarNum = (int) (Math.random() * (NUMBER_OF_AVATARS - 1)) + 1; //randomizes player an avatar from the preloaded ones
         wins = 0;
         loses = 0;
         savePlayerData();
         start = 0L;
      }
   }

   public MyPlayer(String IGN, int avatarNum) { // player when created in edit menu -- parameters should be IGN, icon, id?
      this.avatarNum = avatarNum;
      this.IGN = IGN;
      //used to create a player from EDIT menu

      //if ()

      savePlayerData();
   }

   public boolean loadPlayer() {
      //loads player information from file if stored previously //OR LOADS FROM SERVER?
      //returns true if data was loaded from file into local variables
      FileInputStream fis;

      try {
         fis = openFileInput(FILENAME);
      } catch (FileNotFoundException e) {
         return false;
      }

      //TODO get data from file and store into local variables
      // ei: IGN = fis.read(....

      return true;
   }

   public boolean savePlayerData() {
      //returns true if save was successful

      FileOutputStream fos;
      String str = "" + id + " " + IGN + " " + wins + " " + loses;
      try {
         fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
         //TODO how to save data?
         fos.write(str.getBytes());
         fos.close();
      } catch (FileNotFoundException e) {
         // TODO create file, then save data?
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }

      //http://developer.android.com/guide/topics/data/data-storage.html#filesInternal

      return true;
   }

   public boolean setHealth(int h) {
      //returns true if setHealth worked
      if (h > 100 || h < 0) //health to set must be 0<h<100
         return false;

      health = h;

      return true;
   }

   public void loseHealth(int toLose) {
      if (toLose > 0) //health to lose must be positive
         health -= toLose;

      if (health < 0) {
         health = 0;
         //call death function?
      }
   }

   private boolean avatarExists() {
      //returns true if avatar file exists in file system

      if (avatarNum >= 1 && avatarNum <= NUMBER_OF_AVATARS) // 
         return true;
      else {
         // TODO return true if uploaded avatar file exists
      }

      return true;
   }

   /* TODO
   public image/drawable getAvatar(){
      if (usingPreLoadedAvatar()){ //returns preploaded image
         return 
      } else //returns user uplaoded image
       return 
         
   }
   */

   private final SensorEventListener LightSensorListener = new SensorEventListener() {
      @Override
      public void onAccuracyChanged(Sensor sensor, int accuracy) {
         // TODO Auto-generated method stub
      }

      @Override
      public void onSensorChanged(SensorEvent event) {
         //if (event.sensor.getType() == Sensor.TYPE_LIGHT)
         //   timer.start();

         if (event.values[0] > MINIMUM_LIGHT) { //player is assumed to be being hit with light
            if (start == 0L) //if time hasnt started counting
               start = System.nanoTime(); //start timer
         } else {
            if (start != 0L) { //if timer has started then...
               Long elasped = start - System.nanoTime();
               loseHealth((int) (elasped / 1000) / MILLI_FOR_DAMAGE); //have player lose health compared to how long light was above threshold
               start = 0L; //reset start time
            }
         }
      }
   };

}
