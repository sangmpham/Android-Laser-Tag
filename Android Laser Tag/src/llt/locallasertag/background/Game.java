package llt.locallasertag.background;

public class Game {
   int type = 0; //type 0 = tdm, 1 = ffa
   boolean started = false;
   int lengthInSec = 600; // game ends if time runs out
   String name, typeAsString;
   Player creator;

   public Game() {
      lengthInSec = 600;
      type = 0;
   }

   public Game(String name, int t, int minutes, Player host) {
      type = t;
      switch (type) {
         case 0:
            typeAsString = "Team Death Match";
         case 1:
            typeAsString = "Free For All";
      }
      creator = host;
      lengthInSec = minutes * 60;
   }

   public void setType(int n) {
      type = n;
   }

   public String type() {
      return typeAsString;
   }

   public void start() {
      //send signal to other phones and server that match has begun

      started = true;
   }

   public void endGame() {
      //goes to match setup area for all players
      //send signal to other phones and server that match has begun

   }

   public boolean started() {
      return started;
   }

}
