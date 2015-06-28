package llt.locallasertag.background;

import android.content.Context;

/*
 * Player Class
 * 
 * Used to create a player, which holds attributes for individual players such as long term stats
 * avatar, current score, user name, player id, IGN, and so forth
 */

public class Player {// implements ActionListener{

	   int avatarNum = 1; // for avatar integers 1-35 correspond to a preloaded image, integer 0 corresponds the user had uploaded his/her own image
	   private String IGN;
	   private int id; //used to identify player server side
	   private double health = 100; //is reset to 100 when game begins
	   private int wins, loses; //total wins and loses of the player
	   private int NUMBER_OF_AVATARS = 35;
	   private String team;
	   
	   private Context context;
	   
	   public Player() { // creates player from saved file OR a default player if file not found
		 // id =name;
	     loadPlayer(); //if load player fails, create default player
	     //this.context=context;
	   }

	   public Player(String IGN, int avatarNum) { // player when created in edit menu -- parameters should be IGN, icon, id?
		  //id = getUsername();
	      this.avatarNum = avatarNum;
	      this.IGN = IGN;
	      savePlayerData();
	   }

	   private boolean loadPlayer() {
	      //TODO download player info from server and place into local variables
		  // new LongOperation().execute("");
	      return false;
	   }

		
	   public boolean savePlayerData() {
	      //TODO upload player data to server

	      return true;
	   }

	  

	   public void loseHealth(int toLose) {
	      if (toLose > 0) //health to lose must be positive
	         health -= toLose;
	   }

	   public boolean status() {
	      return health > 0;
	   }
	   public String getTeam() {
		      return team;
		}
	   public void setTeam(String team) {
			  this.team =team;
		}
	   public String getIGN() {
	      return IGN;
	   }
	   
	   public int getId() {
	      return id;
	   }
	   public int getAvatar() {
	      return avatarNum;
	   }
	   public void setAvatar(int ava) {
	      avatarNum = ava;
	   }
	   public void setID(int id) {
		      this.id =id;
		}
	   public void setIGN(String IGN) {
		      this.IGN =IGN;
		}
	   public double getHealth() {
	      return health;
	   }
	   public void setHealth(double health) {
		      this.health =health;
		}

	   public int getWins() {
	      return wins;
	   }

	   public int getLoses() {
	      return loses;
	   }

	   public String getWinsOverTotal() {
	      int total = wins + loses;
	      return wins + "/" + total;
	   }

	   public int getWinPercentage() {
	      return (int) ((double) wins / (wins + loses)) * 100;
	   }
	}