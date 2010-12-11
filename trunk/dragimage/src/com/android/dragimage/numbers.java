package com.android.dragimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class numbers {

	 public Bitmap img; // the image of the numbers
	 private int coordX = 0; // the x coordinate at the canvas
	 private int coordY = 0; // the y coordinate at the canvas
	 private int bottom;
	 private int right;
	 private int id; // gives every number a unique id
	 private static int count = 1;
	 private boolean goRight = true;
	 private boolean goDown = true;

		public numbers(Context context, int drawable) {
		
			BitmapFactory.Options opts = new BitmapFactory.Options();
	        opts.inJustDecodeBounds = true;
	        
	        switch(drawable)
	        {
	        case 0:
				img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number1);
				id = drawable;
				break;
	        case 1:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number2);
		        id = drawable;
		        break;
	        case 2:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number3);
		        id = drawable;
		        break;
	        case 3:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number4);
		        id = drawable;
		        break;
	        case 4:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number5);
		        id = drawable;
		        break;
	        case 5:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number6);
		        id = drawable;
		        break;
	        case 6:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number7);
		        id = drawable;
		        break;
	        case 7:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number8);
		        id = drawable;
		        break;
	        case 8:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number9);
		        id = drawable;
		        break;
	        case 9:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number10);
		        id = drawable;
		        break;
	        case 10:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number11);
		        id = drawable;
		        break;
	        case 11:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number12);
		        id = drawable;
		        break;
	        case 12:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number13);
		        id = drawable;
		        break;
	        case 13:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number14);
		        id = drawable;
		        break;
	        case 14:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.number15);
		        id = drawable;
		        break;
	        case 15:
		        img = BitmapFactory.decodeResource(context.getResources(), com.android.dragimage.R.drawable.square);
		        id = drawable;
		        break;		        
	        }
	        
	      //  id=count;
		//	count++;

		}
		
		public static int getCount() {
			return count;
		}
		void setX(int newValue) {
	        coordX = newValue;
	    }
		
		public int getX() {
			return coordX;
		}

		void setY(int newValue) {
	        coordY = newValue;
	   }
		
		public int getY() {
			return coordY;
		}
		
		public int getID() {
			return id;
		}
		void setRight(int w)
		{
			right = w;
		}
		int getRight()
		{
			return right;
		}
		void setBottom(int h)
		{
			bottom = h;
		}
		int getBottom()
		{
			return bottom;
		}
		public Bitmap getBitmap() {
			return img;
		}
		
		public void moveBall(int goX, int goY) {
			// check the borders, and set the direction if a border has reached
			if (coordX > 270){
				goRight = false;
			}
			if (coordX < 0){
				goRight = true;
			}
			if (coordY > 400){
				goDown = false;
			}
			if (coordY < 0){
				goDown = true;
			}
			// move the x and y 
			if (goRight){
				coordX += goX;
			}else
			{
				coordX -= goX;
			}
			if (goDown){
				coordY += goY;
			}else
			{
				coordY -= goY;
			}
			
		}

}
