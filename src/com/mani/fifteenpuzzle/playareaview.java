package com.mani.fifteenpuzzle;





import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class playareaview extends View
{

		int positionX = 0;
		int positionY = 0;

		int cellWidth = 0;
		int cellHeight = 0;
		int numId = 0;
		Boolean imagemove = false;
		Boolean successfulMove = true;
		Boolean isRandom = false;
		Context mContext;
		Paint mPaint;
		Background mBackground;
		private numbers[] numbers = new numbers[16]; // array that holds the numbers

		private String timeText="00:00:00";
		private String movesText="0";
		private application mParent;
		private int movesCount=0;
		int mTouchMode;
		static final int TOUCH_MODE_TAP = 1;
		static final int TOUCH_MODE_DOWN = 2;
		static final int TOUCH_MODE_REST = 3;

		private int mTouchSlop;
		private boolean isForceSwap = false;
		MediaPlayer mpNumbers;
		MediaPlayer mpButtons;
		MediaPlayer mpGameFinished;
		public boolean isSoundON = true;
		int prevX,prevY,prevR,prevB;
		Rect prevRect;
		Typeface fontface;
		int position=0;
		playareaview(Context context,application app)
		{
			super(context);
			mContext = context;

			mPaint = new Paint();
			
			// set Color- blue
		    mPaint.setColor(0xFFFFFFFF);
		    mPaint.setTextSize(30);
		    fontface = Typeface.createFromAsset( context.getAssets(), "fonts/HNHeavy.ttf");
		    mPaint.setTypeface(fontface);
		    mParent = app;
		    mpNumbers = MediaPlayer.create(context, R.raw.button47);
		    mpButtons = MediaPlayer.create(context, R.raw.button);
		    mpGameFinished = MediaPlayer.create(context, R.raw.button8);
		    mBackground = new Background(context,mPaint,this,numbers);
		    mBackground.generateRandomNos();
		    
		    prevRect = new Rect();
		    for(int i=0;i<16;i++)
		    	numbers[i] =  new numbers(context,i);
			final ViewConfiguration configuration = ViewConfiguration.get(context);
			mTouchSlop = configuration.getScaledTouchSlop();

		    mParent.getNumberPositions(mBackground.randomNos);
		    mBackground.getBackgroundImage(false);
		    isSoundON = mParent.getSoundOnOFF();
		    timeText = mParent.getTimeText();
		    movesCount = mParent.getMovesCount();
		}
		public boolean isSoundEffectsEnabled ()
		{
			return true;
		}
		public void updateNumberPositions()
		{
			int index =0;
			System.out.println("numeber position size is "+mBackground.numbersPos.size());
		    for(int i=0;i<16;i++)
		    {
		    	for(int j=0;j<16;j++)
		    	{
		    		
				    	if(mBackground.numbersPos.get(i).x == numbers[j].getX() &&  mBackground.numbersPos.get(i).y == numbers[j].getY() )
				    	{
				    		mParent.updateNumberPositions(index,numbers[j].getID());
				    	}
	    	
		    	}
		    	index++;
		    }
		}
		public void getMovesCount()
		{
			mParent.updateMovesCount(movesCount);
		}
		public void getTime()
		{
			mParent.updateTime(timeText);
		}

		  @Override
		    public boolean onTouchEvent(MotionEvent event)
		    {
			  
              positionX = (int)event.getX();
	            positionY = (int)event.getY();
	          
	            switch(event.getAction())
	            {
		            case MotionEvent.ACTION_DOWN: {
		            	mTouchMode = TOUCH_MODE_DOWN;
		            	if(positionX > mBackground.getStartButtonX() && positionX < mBackground.getStartButtonX()+mBackground.getButtonWidth() && positionY >mBackground.getStartButtonY() && positionY <mBackground.getStartButtonY()+mBackground.getButtonHeight())
		            	{
		            		
		            		mBackground.generateRandomNos();
		            		isRandom = true;
		            		if(isSoundON)
		            		{
		            			if(mpButtons != null)
		            				mpButtons.start();
		            		}
		            		mParent.resetTimer();
		            		movesCount=0;
		            		invalidate();
		            	}
		            	else
		            	{
		            		
		            		if(checkForValidNumbers(positionX,positionY)){

		            			setNumId(positionX,positionY);
		            			prevRect.left = numbers[numId].getX();
		            			prevRect.top =  numbers[numId].getY();
		            			prevRect.right = numbers[numId].getRight();
		            			prevRect.bottom = numbers[numId].getBottom();
		            			prevX = positionX;
		            			prevY = positionY;
		            			cellWidth = numbers[15].getRight()-numbers[15].getX();
		            			cellHeight = numbers[15].getBottom()-numbers[15].getY();		            			
		            			imagemove = true;
		            		}
		            		else
		            		{
		            			position = 0;
		            			imagemove = false;
		            		}
		            	}
		            	break;
		            }
		            case MotionEvent.ACTION_MOVE: {
		            	
	            	if(imagemove == true)
	            	{
	            		// Check if we have moved far enough that it looks more like a
	    				// scroll than a tap
	    				final int distY = Math.abs(positionY - prevY);
	    				final int distX = Math.abs(positionX - prevX);

	    				if (distX > mTouchSlop || distY > mTouchSlop) {
	
 	            			if(checkIsWithinBounds(positionX,positionY))
		            			{
 	            					isForceSwap = true;
		            				invalidate();	
		            			}
	            				else
			       				{	
			            			//Swap
			            			numbers[numId].setX(numbers[15].getX());
				            		numbers[numId].setY(numbers[15].getY());
				            		numbers[numId].setRight(numbers[15].getRight());
				            		numbers[numId].setBottom(numbers[15].getBottom());
				            		
				            		swapPositions(prevRect);
				            		isForceSwap = false;
				            		invalidate();
				            		
				            		if(mBackground.isGameFinished())
				            		{
				            			if(mpGameFinished!= null)
				            				mpGameFinished.start();
				            			mParent.showSucessDialog();
				            			mBackground.generateRandomNos();
				            			invalidate();
				            		}
				            		imagemove = false;
			       			    }
	    				   }
		              }
	            	
		            }
		            break;
		            case MotionEvent.ACTION_UP: {
		            	if(isForceSwap)
		            	{
		            		//Swap
	            			numbers[numId].setX(numbers[15].getX());
		            		numbers[numId].setY(numbers[15].getY());
		            		numbers[numId].setRight(numbers[15].getRight());
		            		numbers[numId].setBottom(numbers[15].getBottom());
		            		
		            		swapPositions(prevRect);
		            		isForceSwap = false;
		            		
		            		invalidate();
		            		
		            		if(mBackground.isGameFinished())
		            		{
		            			mpGameFinished.start();
		            			mParent.showSucessDialog();
		            			mBackground.generateRandomNos();
		            			invalidate();
		            		}
		            		imagemove = false;		            	
		            	invalidate();
		            	}
		            //	invalidate();
		            }
		            break;
	            }
	            
	            
	            return true;
		  }
		  
		  public boolean setNumId(int x, int y)
		  {
			  boolean validMove = false;

			       	for (numbers number : numbers) {
			       		if( x > number.getX()  && x < number.getRight()  && y  > number.getY() && y < number.getBottom() )
			       		{
			       		 numId = number.getID(); 
			       		 validMove = true;
			       		 break;
			       		}
			  }
			       	
			return validMove;  
		  }
		 
		  public boolean checkIsWithinBounds(int x, int y)
		  {
			  boolean isCanMove = false;
			  int deltaX;
			  int deltaY;
			  switch(position)
			  {
			  case 1:
				 
				  if((x-cellWidth-3) > numbers[15].getX()  && (x-cellWidth-3) < numbers[15].getRight() )
				  {
					  	deltaX = Math.abs(prevX-positionX);
					  	numbers[numId].setX(numbers[numId].getX()-deltaX);
	            		numbers[numId].setY(numbers[15].getY());
	            		numbers[numId].setRight(numbers[numId].getX()+cellWidth);
	            		numbers[numId].setBottom((numbers[15].getBottom()));
	            		prevX = positionX;
	            		isCanMove = true;
                        return isCanMove;
				  }	
				  else
					  isCanMove = false;
				  break;
			  case 2:
				 
				  if((x+cellWidth+3) > numbers[15].getX()  && (x+cellWidth+3) <( numbers[15].getRight() ))
				  {
					  	deltaX = Math.abs(prevX-positionX);
					  	numbers[numId].setX(numbers[numId].getX()+deltaX);
	            		numbers[numId].setY(numbers[15].getY());
	            		numbers[numId].setRight(numbers[numId].getX()+cellWidth);
	            		numbers[numId].setBottom((numbers[15].getBottom()));
	            		prevX = positionX;
	            		isCanMove = true;
					    return isCanMove;
				  }				  
				  else
					  isCanMove = false;
				  break;
			  case 3:
				  if((y+cellHeight+3) > numbers[15].getY() && (y+cellHeight+3) < numbers[15].getBottom())
				  {
						
						deltaY = Math.abs(prevY-positionY);
	          			numbers[numId].setX(numbers[15].getX());
	            		numbers[numId].setY(numbers[numId].getY()+deltaY);
	            		numbers[numId].setRight(numbers[15].getRight());
	            		numbers[numId].setBottom(numbers[numId].getY()+cellHeight);
	            		prevY = positionY;
						isCanMove = true;
                        return isCanMove;
				  }	
				  else
					  isCanMove = false;
				  break;
			  case 4:
				  if((y-cellHeight-3) > numbers[15].getY() && (y-cellHeight-3) < numbers[15].getBottom())
				  {
						
						deltaY = Math.abs(prevY-positionY);
	          			numbers[numId].setX(numbers[15].getX());
	            		numbers[numId].setY(numbers[numId].getY()-deltaY);
	            		numbers[numId].setRight(numbers[15].getRight());
	            		numbers[numId].setBottom(numbers[numId].getY()+cellHeight);
	            		prevY = positionY;
						isCanMove = true;
                        return isCanMove;
                  }	
				  else
					  isCanMove = false;
				  break;
			  }
	
			  return isCanMove;
		  }
		  public boolean checkForValidNumbers(int x, int y)
		  {
			  boolean validMove = false;
			  int numberSquareX = numbers[15].getX();
			  int numberSquareY = numbers[15].getY();
			  cellWidth = numbers[15].getRight()-numbers[15].getX();
			  cellHeight = numbers[15].getBottom()-numbers[15].getY();
			  /* Check for right */
			  if(x > numberSquareX+cellWidth+3 && x< numberSquareX+3+cellWidth+cellWidth && y > numberSquareY && y < numberSquareY+cellHeight)
			  {
				  position =1;
				  validMove = true;
			  }
			  /* Check for left */
			  else if(x > numberSquareX -(3+cellWidth) && x< numberSquareX-3  && y > numberSquareY && y < numberSquareY+cellHeight)
			  {
				  position=2;
				  validMove = true;
			  }
			  /* Check for top */
			  else if(y > numberSquareY -(3+cellHeight) && y < numberSquareY- 3 && x > numberSquareX+3 && x < numberSquareX+cellWidth)
			  {
				  position =3;
				  validMove = true;
			  }
			  /* Check for bottom */
			  else if(y > numberSquareY+cellHeight+3 && y < numberSquareY+3+cellHeight+cellHeight && x > numberSquareX+3 && x < numberSquareX+cellWidth)
			  {
				  position=4;
				  validMove = true;
			  }
			  return validMove;

		  }
		  
		  public void swapPositions(Rect rect)
		  {
			  numbers[15].setX(rect.left);
			  numbers[15].setY(rect.top);
			  numbers[15].setRight(rect.right);
			  numbers[15].setBottom(rect.bottom);
			  if(isSoundON)
      			{
      				if(mpNumbers != null)
      					mpNumbers.start();
      			}
			  mParent.startTimer();
			  //mParent.showSucessDialog();
			  movesCount++;
			  mParent.updateMovesCount(movesCount);
		  }
		  
		  public void setTimeText(String text){
			  timeText = text;
			  invalidate();
		  }
		@Override
		public void onDraw(Canvas canvas)
		{
			Bitmap bitmap = mBackground.getBackgroundImage(isRandom);
			isRandom = false;
			canvas.drawBitmap(bitmap, 0,0, mPaint);
			mBackground.drawButtons(canvas);
			mBackground.drawTimeText(timeText,canvas);
			movesText=""+movesCount;
			mBackground.drawMovesText(movesText,canvas);
			bitmap.recycle();
		}

}
