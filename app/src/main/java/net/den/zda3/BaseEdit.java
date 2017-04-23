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
	Boolean isTargetEdit;
	
	TextView tvInfo,tvData,tvMinHor;
	Button btTimPic;
	ImageButton btDel,btAdd;
	EditText etTermin,etNameTask;
	
	Intent intnInit,intnTimePic;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_edit);
		tvInfo=(TextView) findViewById(R.id.tv_info);
        btTimPic=(Button) findViewById(R.id.bt_time_pick);
        btTimPic.setOnClickListener(this);
        btDel=(ImageButton) findViewById(R.id.bt_del);
		btDel.setOnClickListener(this);
		btAdd=(ImageButton) findViewById(R.id.bt_add);
		btAdd.setOnClickListener(this);
		tvData=(TextView) findViewById(R.id.tv_date);
		tvMinHor=(TextView) findViewById(R.id.tv_min_hor);
		etNameTask=(EditText) findViewById(R.id.et_name_task);
		etTermin=(EditText) findViewById(R.id.et_determinate);
		initz();
	}

	private void initz() {
        intnInit=getIntent();
        etNameTask.setText(intnInit.getStringExtra("name"));
		etTermin.setText(intnInit.getStringExtra("termin"));
		
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
        int vId=v.getId();
		if (vId==R.id.bt_time_pick){
			intnTimePic=new Intent(getApplicationContext(), TimPic.class);
			startActivityForResult(intnTimePic,RESULT_TIME);			
		}else{
		  isTargetEdit="edit".equals(intnInit.getStringExtra("target"));
          switch (vId) {		
            case R.id.bt_add: {
				intnInit.putExtra("name",etNameTask.getText().toString());
				intnInit.putExtra("determin",etTermin.getText().toString());
				if (isTargetEdit){
					intnInit.putExtra("action","edit");
				}else intnInit.putExtra("action","add");
				
            }break;
			
            case R.id.bt_del: {
					if (isTargetEdit){
						intnInit.putExtra("action","del");
					}
            }break;
          }setResult(RESULT_OK, intnInit);
		  finish();
	    }
    }
}
