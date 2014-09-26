//     Copyright (C) 2014 Noah Shillington
//	   Full notice in MainActivity.java

/*
 * This activity is where the help information is stored. It was also going to serve as a way of clearing everything in
 * the lists, but I didn't have enough time.
 */

package ca.ualberta.cs.nshillin.todolist;

import ca.ualberta.cs.todolist.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
    	TextView textView = (TextView) findViewById( R.id.help_textView);
    	textView.setTextSize(18);
		textView.setText("\nTo add items to your To Do list, type into the text field at the bottom of the screen. \n\n" +
				"Long tap on an item that you have added to your list to Archive/Email/Delete that item. \n\n" +
				"To see email options, go to the menu and select Email. This will allow you to Email All or Selected Items from your list. \n\n" +
				"For emailing selected items, tap on any item that you wish to email, then tap the email button.");
	}
}
