package com.android.dragimage;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/** Class Must extends with Dialog */
/** Implement onClickListener to dismiss dialog when OK Button is pressed */
public class congratulationsDialog extends Dialog implements OnClickListener {
	Button okButton;
	Button saveButton;
	Button save;
	application app;
	TextView text;
	Context mContext;
	Dialog dialog;
	Typeface fontface;
	public congratulationsDialog(Context context,application app1) {
		super(context);
		/** Design the dialog in main.xml file */
		mContext = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog);
		app = app1;
		fontface = Typeface.createFromAsset( context.getAssets(), "fonts/HNHeavy.ttf");
		text= (TextView)findViewById(R.id.message);
		text.setTypeface(fontface);
		okButton = (Button) findViewById(R.id.OkButton);
		okButton.setOnClickListener(this);
		okButton.setTypeface(fontface);
		saveButton = (Button) findViewById(R.id.SaveButton);
		saveButton.setTypeface(fontface);
		saveButton.setOnClickListener(this);
	}

	public void showDialog()
	{
		   
		   String message="";
		   message+="You have completed the game in ";
		   message+=app.getTimeText();
		   message+=" and ";
		   message+=app.getMovesCount();
		   message+=" moves";
		   text.setText(message);

		   this.show();
	}
	
	public void onClick(View v) {
		/** When OK Button is clicked, dismiss the dialog */
		if (v == okButton)
			dismiss();
		else if (v == saveButton)
		{
			dialog = new saveuserDialog(mContext,app);
			dialog.show();
			this.dismiss();
		}
		
	}

}
