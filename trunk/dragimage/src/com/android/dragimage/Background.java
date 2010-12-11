package com.android.dragimage;

import java.util.ArrayList;
import java.util.Random;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.view.Display;
import android.view.WindowManager;

public class Background {
	 private Bitmap img; // the image of the numbers
	 private int width = 0; 
	 private int height = 0;
	 private int ROWS = 4; 
	 private int COLUMNS = 4; 
	 private int xoffsetLandscape = 0;
	 private int xoffsetportrait = 10;
	 public int cellSizeWidth = 0;
	 public int cellSizeHeight = 0;
	 private int reqPlaywidth = 0;
	 private boolean isPortrait;
	 private boolean isUpdateNumberCellsPositions;
	 private Context mContext;
	 private Paint mPaint;
	 private playareaview mParent;
	 private int lineGap = 3;
	 private numbers[] numbers;
	 
	 private int timeTextX=0;
	 private int timeTextY=0;
	 private int movesTextX=0;
	 private int movesTextY=0;
	 
	private	int startingX;
	private	int startingY;
	private int  buttonWidth;
	private int buttonHeight;
	
	public ArrayList <Integer> randomNos ;
	private Random randomizer;
	Rect destination = new Rect();

	 public class numbersCellPosition
		{
			public int x;
			public int y;
		}
	 public ArrayList<numbersCellPosition > numbersPos = new ArrayList<numbersCellPosition>();
		
	 private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
	        public void onReceive(Context context, Intent intent) {
	            String action = intent.getAction();
	            if (Intent.ACTION_CONFIGURATION_CHANGED.equals(action)) {
	            	updateBackground();
	            }
	        }
	    };
	    
	 Background(Context context,Paint paint,playareaview parent,numbers[] aNum)
	 {
		 mContext = context;
		 mPaint = paint;
		 mParent = parent;
		 numbers = aNum;
		randomizer = new Random();
		randomNos = new ArrayList<Integer>();
        
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
		context.registerReceiver(mBroadcastReceiver, filter);
		updateBackground();
		
	 }
	 public void unregisterReceiver()
	 {
		 mContext.unregisterReceiver(mBroadcastReceiver); 
	 }
	 public void generateRandomNos()
	 {
         int number = Math.abs(randomizer.nextInt(15));
         if(randomNos.size() > 0)
        	 randomNos.clear();

		for(int i=0;i<16;i++)
		{
			 while(isalreadyAdded(number)==false){
				 number = Math.abs(randomizer.nextInt(16));
			 }
			randomNos.add(number);
		}
	 }
	 
	 public boolean isalreadyAdded(int number)
	 {
		 boolean numberFound = true;
         if(randomNos.size() == 0)
         {
        	 return numberFound;
         }
         else
         {
		 for(int i =0;i<randomNos.size();i++)
		 {
			 if(randomNos.get(i)==number)
			 {
				numberFound = false;
				break;
			 }
		 }	 
	
         }
		 return numberFound;
	 }
	 public void calculateTimeandMovesTextXY(int width,int height)
	 {
			if(width > height)
			{
				timeTextY = (height *72)/100;
				movesTextY= (height *85)/100;
				timeTextX = (width *77)/100;
				movesTextX = (width *77)/100;
			}
			else
			{
				timeTextY = (height *85)/100;
				movesTextY= (height *85)/100;
				timeTextX = (width *5)/100;
				movesTextX = (width *60)/100;
		}
		 
	 }
	 public void updateBackground()
	 {
			Display display = ((WindowManager)mContext.getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay();  
			  
			width = display.getWidth();  
			height = display.getHeight(); 
			reqPlaywidth = display.getWidth();
			
			if(width > height)
			{
				 reqPlaywidth = display.getHeight();
				 xoffsetLandscape =  (width * 10 )/100;
				 isPortrait = false;
			}
			else
			{
				 isPortrait = true;;
			}
			calculateTimeandMovesTextXY(width,height);
			mParent.invalidate();
			isUpdateNumberCellsPositions = true;
	    }

	public Bitmap getBackgroundImage(boolean isRandom)
	{
			Bitmap bitmap;
			Bitmap mbitmap;
			bitmap = Bitmap.createBitmap(width,height, Config.ARGB_8888);
			Canvas drawcanvas = new Canvas(bitmap);
	
			if(isPortrait)
			mbitmap = BitmapFactory.decodeResource(mContext.getResources(), com.android.dragimage.R.drawable.background);
			else
			mbitmap = BitmapFactory.decodeResource(mContext.getResources(), com.android.dragimage.R.drawable.landscape);
            drawcanvas.drawBitmap(mbitmap,0,0,mPaint);
            
            mbitmap = BitmapFactory.decodeResource(mContext.getResources(), com.android.dragimage.R.drawable.square);
            
			int yOffset;
			int xOffset ;
			
			if(isPortrait)
			{
			cellSizeWidth = (reqPlaywidth - ((3*lineGap)+xoffsetportrait ))/ 4;
			cellSizeHeight = (reqPlaywidth - ((3*lineGap)+xoffsetportrait ))/ 4;
			yOffset = ((height * 21 )/100);
			xOffset = xoffsetportrait;
			}
			else
			{
			cellSizeWidth = (reqPlaywidth - ((3*lineGap) ))/ 4;				
			
			yOffset = ((height * 2 )/100);
			
			cellSizeHeight = (height -((3*lineGap)+yOffset +25))/ 4;
			System.out.println("total height  "+height +":"+yOffset+":"+cellSizeHeight);
			//xoffsetLandscape = ((width *5)/100);
			xOffset= xoffsetLandscape;
			}
			
			int rightOffset=cellSizeWidth+xoffsetLandscape;
			int bottomOffset=yOffset+cellSizeHeight;
		
			for( int i=0;i<ROWS;i++)
			{
				for(int j=0;j<COLUMNS;j++)
				{
				destination.top = yOffset;
				destination.left = xOffset;
				destination.right = rightOffset;
				destination.bottom = bottomOffset;

				if(	isUpdateNumberCellsPositions)
				{
					numbersCellPosition obj = new numbersCellPosition();
					obj.x = destination.left;
					obj.y = destination.top;
					numbersPos.add(obj);
				}
				
				drawcanvas.drawBitmap(mbitmap,null,destination,mPaint);
				xOffset = xOffset + cellSizeWidth+lineGap;
				rightOffset = rightOffset+cellSizeWidth+lineGap;
				}

				if(isPortrait)
					 xOffset = xoffsetportrait;
				 else
				 xOffset=xoffsetLandscape;
				 		
				rightOffset = cellSizeWidth+xoffsetLandscape;
				yOffset = yOffset + cellSizeHeight+lineGap;
				bottomOffset = bottomOffset + cellSizeHeight;

			}
			isUpdateNumberCellsPositions = false;
			
			 
				if(isPortrait)
				{
				yOffset = ((height * 21 )/100);
				xOffset =xoffsetportrait;
				}
				else
				{
				yOffset = ((height * 2 )/100);
			//	xoffsetLandscape = ((width *5)/100);
				xOffset=xoffsetLandscape;
				}
				
			 rightOffset=cellSizeWidth+xoffsetLandscape;
			 bottomOffset=yOffset+cellSizeHeight;
			 int numbersCount=0;

			for( int i=0;i<ROWS;i++)
				{
				 for(int j=0;j<COLUMNS;j++)
					{
						if(numbersCount < 16)
						{
							if(isRandom || numbers[randomNos.get(numbersCount)].getX() == 0)
							{
							destination.top = yOffset;
							destination.left = xOffset;
							destination.right = rightOffset;
							destination.bottom = bottomOffset;
							numbers[randomNos.get(numbersCount)].setX(xOffset);
							numbers[randomNos.get(numbersCount)].setY(yOffset);
							numbers[randomNos.get(numbersCount)].setRight(rightOffset );
							numbers[randomNos.get(numbersCount)].setBottom(bottomOffset );
							
							}
							else
							{
								
							destination.top = numbers[randomNos.get(numbersCount)].getY();
							destination.left = numbers[randomNos.get(numbersCount)].getX();
							destination.right = numbers[randomNos.get(numbersCount)].getRight();
							destination.bottom = numbers[randomNos.get(numbersCount)].getBottom();
								
							} 
							if(randomNos.get(numbersCount) !=15)
							drawcanvas.drawBitmap(numbers[randomNos.get(numbersCount)].getBitmap(),null,destination,mPaint);
							numbersCount++;
						}
						

						xOffset = xOffset + cellSizeWidth+lineGap;
						rightOffset = rightOffset+cellSizeWidth+lineGap;
					}

				 if(isPortrait)
				 xOffset = xoffsetportrait;
				 else
				 {
				 xOffset=xoffsetLandscape;

				 }		
				rightOffset = cellSizeWidth+xoffsetLandscape;
				yOffset = yOffset + cellSizeHeight+lineGap;
				bottomOffset = bottomOffset + cellSizeHeight;
			}
			
			return bitmap;
		}

	public void drawTimeText(String text,Canvas canvas)
	{
		if(isPortrait)
		canvas.drawText("Time : "+text, timeTextX, timeTextY, mPaint);
		else
		{
		canvas.drawText("Time : ", timeTextX, timeTextY, mPaint);			
		canvas.drawText(text, timeTextX, timeTextY+28, mPaint);
		}
	
	}
	public void drawMovesText(String text,Canvas canvas)
	{
	//	if(isPortrait)
		canvas.drawText("Moves : "+text, movesTextX, movesTextY, mPaint);
		/*else
		{
		canvas.drawText("Moves : ", movesTextX, movesTextY, mPaint);
		canvas.drawText(text, movesTextX, movesTextY+25, mPaint);
		} */
	}	
	public int getStartButtonX()
	{
		return 	startingX;
		
	}
	public int getStartButtonY()
	{
		return 	startingY;
	}

	public int getButtonWidth()
	{
		return buttonWidth;
	}
	public int getButtonHeight()
	{
		return buttonHeight;
	}

	public void	drawButtons(Canvas canvas)
	{
    	
    	Bitmap bitmap;
		Bitmap mbitmap;
		int width1;
		int height1;
		
		if(isPortrait)
		{
		width1 = ( width * 80 )/100;
		height1 = ( height * 10) / 100;
		}
		else
		{
		width1 = ( width * 20 )/100;
		height1 = ( height * 40) / 100;
		}
		bitmap = Bitmap.createBitmap(width1,height1, Config.ARGB_8888);
		Canvas drawcanvas = new Canvas(bitmap);
        

		int xOffset;
		int yOffset;

		if(isPortrait)
		{
			startingX = ( width * 10) /100;
			startingY = ( height * 5) / 100;
			buttonWidth = width1;
			buttonHeight = height1;
				
			mbitmap = BitmapFactory.decodeResource(mContext.getResources(), com.android.dragimage.R.drawable.new_portrait);
			xOffset = 0;
			yOffset = 0;
			destination.top = yOffset;
			destination.left = xOffset;
			destination.right = xOffset + buttonWidth;
			destination.bottom = yOffset+buttonHeight;
			
			drawcanvas.drawBitmap(mbitmap,null,destination,mPaint);
	
		}
		else
		{
			startingX =  ( width * 75) /100;
			startingY = ( height * 7) / 100;
			xOffset = 0;
			yOffset = 0;
			buttonWidth = width1;
			buttonHeight = height1 ;
			mbitmap = BitmapFactory.decodeResource(mContext.getResources(), com.android.dragimage.R.drawable.new_landscape);

			destination.top = yOffset;
			destination.left = xOffset;
			destination.right = xOffset + buttonWidth;
			destination.bottom = yOffset+buttonHeight;
			drawcanvas.drawBitmap(mbitmap,null,destination,mPaint);
			}
		canvas.drawBitmap(bitmap, startingX,startingY, mPaint);
	}
	
    public boolean isGameFinished()
	{
			boolean isGameFinish = true;
			for(int i =0;i<numbers.length;i++)
			{
				if(numbers[i].getX() == numbersPos.get(i).x && numbers[i].getY() == numbersPos.get(i).y)
				{
					System.out.println("NUmbers match"+numbers[i].getX()+":"+numbers[i].getY()+":"+numbersPos.get(i).x+":"+numbersPos.get(i).y);
				}
				else
				{
					System.out.println("NUmbers miss match match"+numbers[i].getX()+":"+numbers[i].getY()+":"+numbersPos.get(i).x+":"+numbersPos.get(i).y);
					isGameFinish = false;
					break;
				}
			} 
			return isGameFinish;
		}
}
