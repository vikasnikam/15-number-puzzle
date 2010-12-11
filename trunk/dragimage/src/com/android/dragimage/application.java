package com.android.dragimage;


import java.util.ArrayList;
import java.util.Random;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.preference.PreferenceManager;

public class application extends Application{
	dragimage activity;
	private Handler mHandler = new Handler();
	long mStartTime = 0L;
    public String stopWatchTime="00:00:00";
    private ArrayList <Integer> numberPositions ;
    public ArrayList <User > userList;
    private int movesCount=0;
    public boolean isTimerStarted = false;
    Random randomizer;
    int seconds;
    int minutes;
    int hours;
    congratulationsDialog customizeDialog;
    
    class User
    {
       	public String nameText;
    	public String timeText;
    	public int movesCount;
 
    	User(String name, String time, int count)
    	{
    		nameText = name;
    		timeText = time;
    		movesCount = count;
    	}
    }
	@Override
    public void onCreate() {
		
        super.onCreate();
        numberPositions = new ArrayList<Integer>();
        userList = new ArrayList<User>();
	    randomizer = new Random();
	    generateRandomNos();
	    loadSettings();
	}
	public void onTerminate()
	{
		super.onTerminate();
		
	}
	 public void generateRandomNos()
	 {
		 
         int number = Math.abs(randomizer.nextInt(15));
         if(numberPositions.size() > 0)
        	 numberPositions.clear();
		for(int i=0;i<16;i++)
		{
			 while(isalreadyAdded(number)==false){
				 number = Math.abs(randomizer.nextInt(16));
			 }
			 numberPositions.add(number);
		}
	 }	
	 public boolean isalreadyAdded(int number)
	 {
		 
		 boolean numberFound = true;
		 
         if(numberPositions.size() == 0)
         {
        	 return numberFound;
         }
         else
         {
		 for(int i =0;i<numberPositions.size();i++)
		 {
			 if(numberPositions.get(i)==number)
			 {
				numberFound = false;
				break;
			 }
		 }	 
	
         }
		 return numberFound;
	 }
  public void showSucessDialog()
  {
	  activity.showSucessDialog();
  }
  public void setActivity(dragimage act)
  {
	  activity = act;
  }
  public void updateNumberPositions(int index,int id)
  {
	  numberPositions.set(index, id);
  }
  public void getNumberPositions(ArrayList <Integer> numPosList)
  {
	  for(int i =0;i<numberPositions.size();i++)
		  numPosList.set(i, numberPositions.get(i));
  }
  public void updateMovesCount(int count)
  {
	  movesCount = count;
  }
  public String getTimeText()
  {
	  return stopWatchTime;
  }
  public int getMovesCount()
  {
	  return movesCount;
  }
  public void startTimer()
  {
	  if (mStartTime == 0L) {
		  System.out.println("Timer is started");
		  isTimerStarted = true;
          mStartTime = System.currentTimeMillis();
          mHandler.removeCallbacks(mUpdateTimeTask);
          mHandler.postDelayed(mUpdateTimeTask, 100);
     }     
  }
  private Runnable mUpdateTimeTask = new Runnable() {
	   public void run() {
		   System.out.println("Timmer running"+stopWatchTime+"seconds minues hrs"+seconds+":"+minutes+":"+hours);
		   String time=" ";
	       final long start = mStartTime;
	       long millis = System.currentTimeMillis()-start;
	        seconds = (int) (millis / 1000);
	        minutes = seconds / 60;
	        hours = minutes / 60;
	       seconds     = seconds % 60;

	       if (hours < 10) {
	         time=" 0"+hours;
	       }
	       else
	    	   time =time+hours;

	       if (minutes < 10) {
	         time=time+":"+"0"+minutes;
	       }
	       else
	    	   time=time+":"+minutes;

	       if (seconds < 10) {
	    	   time=time+":"+"0"+seconds;
	       }
	       else
	    	   time=time+":"+seconds;
	       
	       stopWatchTime = time;
	       activity.setTimeText(stopWatchTime);
	       mHandler.postDelayed(this,1000);
	   }
	};
	   public void stopTimer() {
		   mStartTime = 0L;
		   isTimerStarted = false;
		   stopWatchTime="00:00:00";
		   activity.setTimeText(stopWatchTime);
	       mHandler.removeCallbacks(mUpdateTimeTask);
	   }
	 public void pauseTimer()
	 {
		 mHandler.removeCallbacks(mUpdateTimeTask);
	 }

	 public void updateUserList(String name)
	 {
		 if(userList.size() >= 20)
		 {
			 
			 
		 }
		 else
		 {
		User user = new User(name,stopWatchTime,movesCount);
		userList.add(user);
		 }
		
	 }
	 public void saveSettings() {

		 	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	        Editor editor = null;;
	        editor = prefs.edit();
	        int count = userList.size();
	        editor.putInt("Count", count);
	       
	       for (int i = 0; i < count; i++) {
	        	User data = userList.get(i);
	        	
	        	if(count != 0) {
	        		// TODO:
	                editor.putString("Name" + i, data.nameText);
	                editor.putString("Time" + i, data.timeText);
	                editor.putInt("Movescount" + i, data.movesCount);
	        	}
	        } 
	        
	        editor.commit();
	        userList.clear();
	    }

	 	public boolean loadSettings()
		{
	    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	        Editor editor = null;;
	        int count = prefs.getInt("Count", 0);
	        if(count == 0) {
	        	System.out.println("Load settings FALSE");
	        	return false;
	        }
	        
	        for (int i = 0; i < count; i++) {
	        	String name = prefs.getString("Name" + i, "");
	        	String time = prefs.getString("Time" + i, "");
	        	int movescount = prefs.getInt("Movescount" + i, 0);
	        	
	        	User user = new User(name,time,movescount);
	        	userList.add(user);
	        	System.out.println("values are "+name+"::"+time+"::"+movescount);
			    
	        }
			return true;
		}
	 
}
