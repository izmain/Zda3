package net.den.zda3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.*;

public class UnlockStartService extends Service {
    public UnlockStartService() {
    }
	public void onCreate() {
		super.onCreate();
		
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		someTask();
		return super.onStartCommand(intent, flags, startId);
	}

	private void someTask()
	{
		IntentFilter filtrResiv=new IntentFilter (
		"android.intent.action.ACTION_USER_PRESENT");
		PlayerReciver unlockResiv= new PlayerReciver();
		registerReceiver(unlockResiv,filtrResiv);
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
