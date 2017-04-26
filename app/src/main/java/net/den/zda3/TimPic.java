package net.den.zda3;


import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.text.*;
import java.util.*;

public class TimPic extends Activity

	

{
	EditText editYear ;
	EditText editMonth ;
	EditText editDay ;
	EditText editHore ;
	EditText editMin;
	TextView debugConsol;
	
	Intent intnList;

	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_pic);
		// доступ к полям
		 editYear = (EditText) findViewById(R.id.mainEditYear);
		 editMonth = (EditText) findViewById(R.id.mainEditMonth);
		 editDay = (EditText) findViewById(R.id.mainEditDay);
		 editHore = (EditText) findViewById(R.id.mainEditHore);
		 editMin = (EditText) findViewById(R.id.mainEditMin);
		 debugConsol =(TextView) findViewById(R.id.mainDebugConsole);
		 // установка слушателей полей , to do
		inicial();
    }

	private void inicial()
	{
		
		
		intnList=new Intent(this, ListOperation.class);
		
	}
	
	//---------------------------
	//     ОБРАБОТЧИКИ НАЖАТИЙ
	//---------------------------
	public void onclButt(View v){
		switch (v.getId()){
			case R.id.bt_get_time:
				getTime();break;
			case R.id.bt_set_time:
				sendTime();break;
			
			case R.id.bt_start_serv:
				startHuyService();break;
			case R.id.bt_stop_serv:
				stopHuyServ();break;
			case R.id.bt_check_serv:
				checkHuyServ();break;
		}
	}

	private void checkHuyServ()
	{
		// TODO: Implement this method
	}

	private void stopHuyServ(){
		stopService(new Intent(this, UnlockStartService.class));
	}

	private void startHuyService(){                           
			startService(new Intent(this, UnlockStartService.class));
	}                              
	
	public void sendTime(){
		intnList.putExtra("time",getDataToFields());
		setResult(RESULT_OK, intnList);
		Toast.makeText(this,"time",Toast.LENGTH_SHORT).show();
		finish();
	}
	//нажатие получение ячейки времени
	public void clcReed(View v)
	{}
	
	//нажатие на текущее время
	public void getTime(){
		DecimalFormat df= new DecimalFormat("00");
		editYear.setText(df.format(getTimeOfFormat("yy")));
		editMonth.setText(df.format(getTimeOfFormat("MM")));
		editDay.setText(df.format(getTimeOfFormat("dd")));
		editHore.setText(df.format(getTimeOfFormat("HH")));
		editMin.setText(df.format(getTimeOfFormat("mm")));
		
	}

	
	
	//нажатие на list
	public void clcViewList(View v)
	{		
		showList();
	}

	
	
    //нажатие на сохранение времени
	public void onclSaveTime(View v)
	{}
	
	//-------------------------------
	//         МОИ ФУНКЦИИ
	//--------------------------------
	
	//извлечение даты строкой из полей
	private String getDataToFields()
	{	
		String mDate="";
		mDate= addFieldToData(editYear,mDate);
		mDate= addFieldToData(editMonth,mDate);
		mDate= addFieldToData(editDay,mDate);
		mDate= addFieldToData(editHore,mDate);
		mDate= addFieldToData(editMin,mDate);
		return mDate;
	}
	
	
	
	// добавление к строке двух цифр из поля
	public String addFieldToData (EditText et, String s){
		DecimalFormat df=new DecimalFormat("00");
		df.setMaximumIntegerDigits(2);
		s += df.format(Integer.parseInt("00"+et.getText().toString()));
		return s;
	}
	
	//добавление к дебагу
	public void addDbg(String s)
	{
		s+=";;"+'\n'+debugConsol.getText().toString();
		debugConsol.setText(s);
	}
	
	
	
	
	
	// нужная секция даты_времени по формату
	public int getTimeOfFormat (String timeFormat)
	{
		SimpleDateFormat datFormat =new SimpleDateFormat(timeFormat);
		Calendar sisData = Calendar.getInstance(); 
		return Integer.parseInt(datFormat.format(sisData.getTime()));

	}
	
	//сверка времени по строке
	public String checkTime(String dataString){
		String checkStatus= "now";
		Date sisData = new Date();		
		SimpleDateFormat sdf =new SimpleDateFormat("yyMMddHHmm");
		int sisDataInt = Integer.parseInt(sdf.format(sisData));
		int myDataInt=Integer.parseInt(dataString);
		if (myDataInt==sisDataInt) 
		{return checkStatus;}
		if  (myDataInt<sisDataInt) {
			 checkStatus="after";
		}else { checkStatus="befor";}
		return checkStatus;
	}
	
	
	private void showList()
	{

		
		intnList.putExtra("time", getDataToFields());
		addDbg( intnList.getStringExtra("time"));
		startActivity(intnList);
		
	}
}
