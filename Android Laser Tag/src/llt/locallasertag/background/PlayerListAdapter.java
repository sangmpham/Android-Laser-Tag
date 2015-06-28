package llt.locallasertag.background;

import java.util.ArrayList;

import llt.locallasertag.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerListAdapter extends BaseAdapter{

   private ArrayList<PlayerList> _data;     
   Context _c;          
   PlayerListAdapter (ArrayList<PlayerList> data, Context c){         
   	_data = data;        
   	_c = c;    
   	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
      View v = convertView;          
      if (v == null)          
      {            
      	LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);            
      	v = vi.inflate(R.layout.player, null);          }             
         ImageView image = (ImageView) v.findViewById(R.id.imbullet);            
         TextView fromView = (TextView)v.findViewById(R.id.textView1);                       
         PlayerList msg = _data.get(position);            
         image.setImageResource(msg.icon);            
         fromView.setText(msg.playerName);                                                                        
         return v; 
	} 
   
   
}
