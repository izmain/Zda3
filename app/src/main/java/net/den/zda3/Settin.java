package net.den.zda3;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;

public class Settin extends AppCompatActivity {


    CheckBox  chBoxServ, chBoxChek;

    SharedPreferences preferSettin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settin);
        chBoxServ = (CheckBox) findViewById(R.id.chbx_serv);
        chBoxChek = (CheckBox) findViewById(R.id.chbx_check);
        loadSettin();
    }

    private void loadSettin() {
        preferSettin = getPreferences(MODE_PRIVATE);
        chBoxServ.setChecked(preferSettin.getBoolean("serv", false));
        chBoxChek.setChecked(preferSettin.getBoolean("check", false));
		checkCheck();
		}


    private void saveSettin() {
        preferSettin = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = preferSettin.edit();
        ed.putBoolean("serv",chBoxServ.isChecked()).apply();
        ed.putBoolean("check",chBoxChek.isChecked()).commit();
    }

    public void onCheckboxClicked(View view) {
		
        

        switch (view.getId()) {
			
            case R.id.chbx_serv:
				checkCheck();
				
                break;
        }
    }
	
	// проверка галочек
	private void checkCheck()
	{
		
		boolean checked = chBoxServ.isChecked();
		if (checked){
			Intent intnCheckTime = new Intent(getApplicationContext(),UnlockStartService.class);
			intnCheckTime.putExtra("action", "check time");
			startService(intnCheckTime);
		}else{
			stopService(new Intent(this, UnlockStartService.class));
			}
	}

    @Override
    protected void onPause() {
        saveSettin();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        saveSettin();
        super.onDestroy();
    }
}
