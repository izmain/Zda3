package net.den.zda3;


import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;

public class BaseEdit extends Activity implements OnClickListener {
	
	private static final int RESULT_TIME = 1;
	String timePicStr ;
	
	TextView tvInfo,tvData,tvMinHor;
	Button btConfirm, btDel;
	EditText etTermin,etNameTask;
	
	Intent intnInit,intnTimePic;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_edit);
		tvInfo=(TextView) findViewById(R.id.tv_info);
        btConfirm=(Button) findViewById(R.id.bt_time_pick);
        btConfirm.setOnClickListener(this);
        btDel=(Button) findViewById(R.id.bt_del);
		tvData=(TextView) findViewById(R.id.tv_date);
		tvMinHor=(TextView) findViewById(R.id.tv_min_hor);
		etNameTask=(EditText) findViewById(R.id.et_name_task);
		etTermin=(EditText) findViewById(R.id.et_determinate);
		initz();
	}

	private void initz() {
        btConfirm.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					intnTimePic=new Intent(getApplicationContext(), TimPic.class);
					startActivityForResult(intnTimePic,RESULT_TIME);
				}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case RESULT_TIME:
				tvData.setText(data.getStringExtra("time"));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

    @Override
    public void onClick(View v) {
        intnInit=getIntent();
        Boolean isTargetEdit= "add". intnInit.getStringExtra("target");

        switch (v.getId()) {
            case R.id.bt_add: {

            }break;
            case R.id.bt_del: {
            }break;
        }
    }
}
