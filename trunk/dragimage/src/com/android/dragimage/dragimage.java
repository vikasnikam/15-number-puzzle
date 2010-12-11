package com.android.dragimage;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class dragimage extends Activity {
	
   private playareaview playView;
   private Context mContext;
   private application app;
   public boolean soundONOFF = true;
   congratulationsDialog customizeDialog;
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        app = (application)getApplication();
        app.setActivity(this);
        customizeDialog = new congratulationsDialog(this,app);
        playView = new playareaview(this,app);
        setTitleBar();
        
        setContentView(playView);
        
    }
   public void setTitleBar()
   {
		Display display = ((WindowManager)mContext.getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay();  
		  
		int width = display.getWidth();  
		int height = display.getHeight(); 
		
		
		if(width > height)
		{
			   requestWindowFeature(Window.FEATURE_NO_TITLE);	
		}

   }
	public void onPause()
	{
		super.onPause();
		app.updateMovesCount(0);
		app.generateRandomNos();
		//app.stopTimer();
	}
	public void onStop()
	{
		super.onStop();
		System.out.println("On stop");
	}
	public void onResume()
	{
		super.onResume();
		app.generateRandomNos();
		System.out.println("On resume");
	}
   public void setTimeText(String text)
   {
	   playView.setTimeText(text);
   }
   public String getTimeText()
   {
	   
	   return app.getTimeText();
   }
   public void getNumberPositions(ArrayList <Integer> numPosList)
   {
	   app.getNumberPositions(numPosList);
   }
   public void updateNumberPositions(int index,int id)
   {
	   app.updateNumberPositions(index,id);
   }
   public void updateMovesCount(int count)
   {
 	  app.updateMovesCount(count);
   }
   public int getMovesCount()
   {
	   return app.getMovesCount();
   }
   public void showSucessDialog()
   {
	   customizeDialog.showDialog();
   }
   	public boolean onCreateOptionsMenu(Menu menu){

   		MenuInflater inflater = getMenuInflater();
   		inflater.inflate(R.menu.menu, menu);
   		return true;
	   }
   
   	public boolean onOptionsItemSelected (MenuItem item){
   		switch (item.getItemId()) {
   		
   		case R.id.sound:
   			if(soundONOFF == true)
   			{
   				soundONOFF = false;
   				item.setIcon(R.drawable.sound_off);
   			}
   			else
   			{
   				soundONOFF = true;
   				item.setIcon(R.drawable.sound_on);
   			}
   			playView.isSoundON = soundONOFF;
   			break;

   		case R.id.scores:
   			Intent intent = new Intent();
            intent.setClass(getApplicationContext(), scoresListview.class);
            startActivity(intent);
   			break;   			
   		
   		case R.id.exit:
   			//this.finish();
   			app.saveSettings();
   			System.exit(0);
   			break;
   		}
   		return true;
   		}



   	
   @Override
   public void onDestroy() {
	   super.onDestroy();
	   playView.updateNumberPositions();
	   playView.unregisterReceiver();
	   playView.getMovesCount();
      	System.gc();
    	System.runFinalization();
    	System.gc();
   }
}