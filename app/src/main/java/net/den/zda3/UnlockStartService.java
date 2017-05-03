package net.den.zda3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.*;

public class UnlockStartService extends Service {
    public UnlockStartService() {}

	BroadcastReceiver br;

	public void onCreate() {
		super.onCreate();
		br = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				Intent intn =new Intent(getApplicationContext(), ListOperation.class);
				intn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intn);

			}
		};
		IntentFilter filtrResiv=new IntentFilter (
				"android.intent.action.USER_PRESENT");
		filtrResiv.addCategory("android.intent.category.DEFAULT");

		registerReceiver(br, filtrResiv);
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		someTask();
		return super.onStartCommand(intent, flags, startId);
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
