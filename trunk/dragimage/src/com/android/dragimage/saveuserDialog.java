package com.android.dragimage;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/** Class Must extends with Dialog */
/** Implement onClickListener to dismiss dialog when OK Button is pressed */
public class saveuserDialog extends Dialog implements OnClickListener {
	
	Button saveButton;
	application app;
	EditText nameEditor; 
	Context mContext;
	Dialog dialog;
	Typeface fontface;
	
	public saveuserDialog(Context context,application app1) {
		super(context);
		mContext = context;
		/** Design the dialog in main.xml file */
		mContext = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.savedialog);
		app = app1;
		nameEditor = (EditText)findViewById(R.id.nametextbox);
		fontface = Typeface.createFromAsset( context.getAssets(), "fonts/HNHeavy.ttf");
		saveButton = (Button) findViewById(R.id.Save);
		saveButton.setTypeface(fontface);
		saveButton.setOnClickListener(this);
	}


	public void onClick(View v) {
		/** When OK Button is clicked, dismiss the dialog */
		if (v == saveButton)
		{
			String name = nameEditor.getText().toString();
			if(name.length() ==0 )
			{
				AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
				alertDialog.setTitle("Error");
				alertDialog.setMessage("Please enter a valid name");
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int which) {
				      // here you can add functions
					   dialog.dismiss();
				   }
				});
				alertDialog.setIcon(R.drawable.icon);
				alertDialog.show();			}
			else
			{
			app.updateUserList(name);
			System.out.println("Name is "+name);
			dismiss();
			}
		}

		
	}

}
