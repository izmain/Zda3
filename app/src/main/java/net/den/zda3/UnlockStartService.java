package net.den.zda3;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class UnlockStartService extends Service {
    public UnlockStartService() {}

	BroadcastReceiver br;
	int MINUS_1=-1;
	String nextCheckTime="9999999999";
	
	// onCreate
	public void onCreate() {
		super.onCreate();
		// ресивер
		br = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				if (getCurTime().compareTo(nextCheckTime)>0){
				
				  Intent intnDial=new Intent(getApplicationContext(),Dial.class);
						intnDial.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
								.putExtra("message",nextCheckTime);
				  		startActivity(intnDial);
				}
				
			}

			
	    };
		IntentFilter filtrResiv=new IntentFilter ("android.intent.action.USER_PRESENT");
		filtrResiv.addCategory("android.intent.category.DEFAULT");
		registerReceiver(br, filtrResiv);
	}

	// проверка на время
	private int checkTime()
	{
		DB db = new DB(getApplicationContext());
		db.open();
		String[] s=db.getTimes();
		for (String i:s){
			if (i.compareTo(getCurTime())<0){
				db.close();
				return Integer.parseInt(i);
			}
		}
		db.close();
		return -1;
	}
	
	

	//получение текушего времени
	private String getCurTime()
	{
		Date sisData = new Date();		
		SimpleDateFormat sdf =new SimpleDateFormat("yyMMddHHmm");
		return sdf.format(sisData);
	}
	

	// onStart
	public int onStartCommand(Intent intent, int flags, int startId) {
		String actionOfIntn=intent.getStringExtra("action");
		//to do go to getaction
		switch (actionOfIntn){

			case "start":
				Intent intn =new Intent(getApplicationContext(), ListOperation.class);
				intn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intn);break;
			case "check time":
				//to do устроить сортировку и облегчить цикл
				int itemCheckTime = checkTime();
				if(itemCheckTime!=MINUS_1){
					Toast.makeText(this,"просрочено "+itemCheckTime,Toast.LENGTH_SHORT).show();
				}
				break;
			case "check next":
				if (nextCheckTime.compareTo(getCurTime())<0){
					Toast.makeText(getApplicationContext(),"next ",Toast.LENGTH_SHORT).show();
				}
				break;

			case "check stat":	
				DB db = new DB(getApplicationContext());
				db.open();
				nextCheckTime = db.checkStat(getCurTime());
				db.close();
				Toast.makeText(getApplicationContext(),
							   "next "+ nextCheckTime,
							   Toast.LENGTH_SHORT).show();
				break;

		}

		return super.onStartCommand(intent, flags, startId);
	}

	//::::::::::::::::::::::::::::::::
	public void onDestroy() {
		super.onDestroy();
		
	}
	

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
	
	
}
