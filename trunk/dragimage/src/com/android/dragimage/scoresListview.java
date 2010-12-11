package com.android.dragimage;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class scoresListview extends ListActivity{

	application app;
	Typeface fontface;
	public void onCreate(Bundle savedInstanceState) {
	      
		super.onCreate(savedInstanceState);
        setContentView(R.layout.scores);
        app = (application)getApplication();
        for(int i =0;i< app.userList.size();i++)
        	System.out.println("name time count "+app.userList.get(i).nameText+":"+app.userList.get(i).timeText);
		fontface = Typeface.createFromAsset( getApplicationContext().getAssets(), "fonts/HNHeavy.ttf");
		TextView title = (TextView) findViewById(R.id.title);
		title.setTypeface(fontface);
        setListAdapter(new EfficientAdapter(this));
        
	}
    private  class EfficientAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Bitmap mIcon1;
        private Bitmap mIcon2;

        public EfficientAdapter(Context context) {
            // Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);

        }

        public int getCount() {
            return app.userList.size()+1;
        	//return DATA.length;
        }
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
            	AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, 80);
                convertView = mInflater.inflate(R.layout.scoreslistview, null);
                convertView.setLayoutParams(layoutParams);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.moves = (TextView) convertView.findViewById(R.id.moves);
                if(position == 0 )
                {
                holder.name.setTextSize(28);                	
                holder.name.setText("Name ");
                holder.name.setTypeface(fontface);
                
                holder.time.setTextSize(28);
                holder.time.setText("Time ");
                holder.time.setTypeface(fontface);
              
                holder.moves.setTextSize(28);
                holder.moves.setText("Moves ");
                holder.moves.setTypeface(fontface);
                }
                else
                {
                    holder.name.setText(app.userList.get(position-1).nameText);
                    holder.name.setTextSize(23);
                    holder.name.setTypeface(fontface);
                    
                    holder.time.setText(app.userList.get(position-1).timeText);
                    holder.time.setTextSize(23);
                    holder.time.setTypeface(fontface);
                    
                    holder.moves.setText(""+app.userList.get(position-1).movesCount);
                    holder.moves.setTextSize(23);
                    holder.moves.setTypeface(fontface);
                }
                
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            
            return convertView;
        }
         class ViewHolder {
            TextView name;
            TextView time;
            TextView moves;
        }
    }

}
