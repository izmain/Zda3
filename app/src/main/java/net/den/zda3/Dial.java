package net.den.zda3;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.content.*;

public class Dial extends Activity
{
	

	/** onCreate **/
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog1);
		TextView tvDial = (TextView) findViewById(R.id.tv_dial);
		Intent intnDial = getIntent();
		tvDial.setText(" nopa 6∆¶ " + intnDial.getStringExtra("message"));
	}
		
}
