package llt.locallasertag.nongame;

import llt.locallasertag.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class HowToPlay extends Activity {

   TextView tv1;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.howtoplay);

      tv1 = (TextView) findViewById(R.id.tv1);

      // I know this is horrible, but its last minute.... 
      String total = "What you'll need: \n"
            + "In order to play Local Laser Tag, you will need an android phone with a light sensor, an internet connection, a flash light, a dimly lit area, and a chest harness to hold your phone (screen facing out). "
            + "\n\nHosting a game:\n"
            + "If you choose to host a game, you can create one from the lobby page. Friends will be able to join your game by searching the name of your game, so choose wisely. Once all players have entered your game room and have chosen their respective team, click start to being the game. "
            + "\n\nJoining a game:\n"
            + "If you are trying to join a friend�s game, click the start button from the home screen, and search the name of the host's game. Once inside the room, you will be able to choose a team while waiting for the host to start the game."
            + "\n\nChoosing an IGN and avatar:\n"
            + "Visit the settings page to choose an In Game Name and avatar which will be displayed to other players in game."
            + "\n\nGame play:\n"
            + "Once you're inside a room and the game has begun, you'll notice a few things. Firstly your health bar and avatar will be displayed prominently at the top of the screen, above that of your teammates.  The object of the game is to deplete the enemy�s health bars by shining your flashlight at the enemy�s phones (simulating laser tag).  Once a players health bar is depleted, that player is out for the rest of the game, and must wait for the host to start another round of Local Laser Tag. The last team to still have \"alive\" players wins. In the event that the time runs out before a team has won, the team with the least number of deaths will be the winner and the round will be over."
            + "\n\nWARNING:\n"
            + "Do not shine lights at individual�s faces per damage to retina.   Yes, team damage is on, so careful where you point that thing. The creators of Local Laser Tag are not responsible for any death, injury, loss of vision, broken property or any other mishap what so ever that may occur while playing Local Laser Tag. ";

      /*

      // first open the file and read it into a StringBuilder
      String cardPath = Environment.getExternalStorageDirectory().toString();
      BufferedReader r = null;
      try {
         r = new BufferedReader(new FileReader("/helptext.txt"));
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      StringBuilder total = new StringBuilder();
      String line;
      try {
         while ((line = r.readLine()) != null) {
            total.append(line);
         }
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      // then get the TextView and set its text
      //TextView txtView = (TextView)findViewById(R.id.txt_view_id);
       * 
       */
      tv1.setText(total.toString());
      tv1.setMovementMethod(new ScrollingMovementMethod());
      //      test.start();
      //    loadDoc();
   }
   /*
   private void loadDoc() {

      String s;

      try {
         s = readFile("src/helptext.txt");
      } catch (IOException e) {
         s = "CANNOT LOAD FILE";
      }

      for (int x = 0; x <= 100; x++) {
         s += "Line: " + String.valueOf(x) + "\n";
      }

      tv1.setMovementMethod(new ScrollingMovementMethod());

      tv1.setText(s);

   }

   private String readFile(String pathname) throws IOException {

      File file = new File(pathname);
      StringBuilder fileContents = new StringBuilder((int) file.length());
      Scanner scanner = new Scanner((Readable) new BufferedReader(new FileReader(file)));
      String lineSeparator = System.getProperty("line.separator");

      try {
         while (scanner.hasNextLine()) {
            fileContents.append(scanner.nextLine() + lineSeparator);
         }
         return fileContents.toString();
      } finally {
         scanner.close();
      }
   }

   Thread test = new Thread() {
      public void run() {
         File sfile = new File(extras.getString("sfile"));
         try {
            StringBuilder text = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(sfile));
            String line1;
            while (null != (line1 = br.readLine())) {
               text.append(line1);
               text.append("\n");
            }
            subtitletv.setText(text.toString());

         } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
   };

   */
}
