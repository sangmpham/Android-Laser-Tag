package llc.locallasertag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;


public class CreateGame extends Activity {
	private Spinner gameType, numPlayer;
	private EditText getGameName;
	private Integer numberPlayer;
	private String gameChoice, gameName;
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.creategame);
		  getGameName=(EditText)findViewById(R.id.txtGameName);
		  gameType = (Spinner)findViewById(R.id.chooseGame);
		  numPlayer = (Spinner)findViewById(R.id.spinNumberPlayer);
		  
		  
		  
		  gameName = getGameName.getText().toString();
		  gameChoice = gameType.getSelectedItem().toString();
		  
		
		
			  gameType.setVisibility(View.VISIBLE);
			  numberPlayer = Integer.parseInt(numPlayer.getSelectedItem().toString());
		
			  if(gameChoice == "Free For All")
				  numberPlayer = 1;
		 
		 
	}

}
