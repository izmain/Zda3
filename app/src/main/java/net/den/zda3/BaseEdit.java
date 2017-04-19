package net.den.zda3;


import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;

public class BaseEdit extends Activity {
	
	TextView tvInfo;
	
	Intent intnInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_edit);
		tvInfo=(TextView) findViewById(R.id.tv_info);
		intnInit=getIntent();
		tvInfo.setText("Редактирование задания #"+
		               (intnInit.getIntExtra("item", -1)+1));
    }
}
