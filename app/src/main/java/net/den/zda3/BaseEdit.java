package net.den.zda3;


import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;

public class BaseEdit extends Activity {
	
	private static final int RESULT_TIME = 1;
	String timePicStr ;
	
	TextView tvInfo,tvData,tvMinHor;
	Button btTimePic;
	EditText etTermin;
	
	Intent intnInit,intnTimePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_edit);
		tvInfo=(TextView) findViewById(R.id.tv_info);
		btTimePic=(Button) findViewById(R.id.bt_time_pick);
		tvData=(TextView) findViewById(R.id.tv_date);
		tvMinHor=(TextView) findViewById(R.id.tv_min_hor);
			
		initz();
	}

	private void initz()
	{
		intnInit=getIntent();
		tvInfo.setText("Редактирование задания #"+
		               (intnInit.getIntExtra("item", -1)+1));
		btTimePic.setOnClickListener(new OnClickListener(){

				

				@Override
				public void onClick(View v)
				{
					intnTimePic=new Intent(getApplicationContext(), MainActivity.class);
					startActivityForResult(intnTimePic,RESULT_TIME);
					
				}
				
			
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode) {
			case RESULT_TIME:
				tvData.setText(data.getStringExtra("time"));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
