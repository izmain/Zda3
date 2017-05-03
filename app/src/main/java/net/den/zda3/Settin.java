package net.den.zda3;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;

public class Settin extends Activity {


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
        chBoxChek.setChecked(preferSettin.getBoolean("check", false));    }



    private void saveSettin() {
        preferSettin = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = preferSettin.edit();
        ed.putBoolean("serv",chBoxServ.isChecked()).commit();
        ed.putBoolean("check",chBoxChek.isChecked()).commit();
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
