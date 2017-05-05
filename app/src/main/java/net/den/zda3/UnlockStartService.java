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

	public void onCreate() {
		super.onCreate();
		br = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				int itemCheckTime = checkTime();
				if(itemCheckTime!=MINUS_1){
					Toast.makeText(getApplicationContext(),"просрочено "+itemCheckTime,Toast.LENGTH_SHORT).show();
					

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
			if (Integer.parseInt(i)<getCurTime()){
				db.close();
				return Integer.parseInt(i);
			}
		}
		db.close();
		// TODO: Implement this method
		return -1;
	}
	
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
		}
		someTask();
		return super.onStartCommand(intent, flags, startId);
	}
	

	//получение текушего времени
	private int getCurTime()
	{
		Date sisData = new Date();		
		SimpleDateFormat sdf =new SimpleDateFormat("yyMMddHHmm");
		return Integer.parseInt(sdf.format(sisData));
	}

	private void someTask()
	{
		
	}

	public void onDestroy() {
		super.onDestroy();
		
	}
	

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
	
	
}
