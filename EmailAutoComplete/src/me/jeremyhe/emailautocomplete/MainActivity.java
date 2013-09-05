package me.jeremyhe.emailautocomplete;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EmailAutoCompleteTextView textView = (EmailAutoCompleteTextView)findViewById(R.id.emailAutoCompleteTextView1);
		textView.setDropDownBackgroundResource(R.drawable.mail_login_email_dropdownlist_bg_no_lt_radius);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
