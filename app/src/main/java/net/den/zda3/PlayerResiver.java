package net.den.zda3;

import android.content.*;
import net.den.zda3.*;
import android.widget.*;

 class PlayerReciver extends BroadcastReceiver
{ 
	private static final String TYPE = "type"; 
	private static final int ID_ACTION_PLAY = 0; 
	private static final int ID_ACTION_STOP = 1; 

	@Override 
	public void onReceive(Context context, Intent intent) 
	{ 
	// Toast.makeText();
		
	int type = intent.getIntExtra(TYPE, ID_ACTION_STOP); 
		switch (type) 
		{
			case ID_ACTION_PLAY: 
				// выполнение полученного намерения 
				context.startActivity(new Intent(context, ListOperation.class)); 
				break;
		} 
	}
}
