package net.den.zda3;


import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import java.text.*;
import java.util.*;

public class TimPic extends AppCompatActivity

	

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
				String putData = getDataToFields();
				intnList.putExtra("time",putData)
					.putExtra("status",
							  checkTime(putData));
				setResult(RESULT_OK, intnList);
				finish();				
		}
	}
	
	
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
		intnList.putExtra("time", getDataToFields());
		startActivity(intnList);	
	}

	
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
	
	// нужная секция даты_времени по формату
	public int getTimeOfFormat (String timeFormat)
	{
		SimpleDateFormat datFormat =new SimpleDateFormat(timeFormat);
		Calendar sisData = Calendar.getInstance(); 
		return Integer.parseInt(datFormat.format(sisData.getTime()));

	}
	
	//сверка времени по строке
	public String checkTime(String dataString){
		String checkStatus= "befor";
		Date sisData = new Date();		
		SimpleDateFormat sdf =new SimpleDateFormat("yyMMddHHmm");
		int sisDataInt = Integer.parseInt(sdf.format(sisData));
		int myDataInt=Integer.parseInt(dataString);
		if (myDataInt==sisDataInt) 
		{return checkStatus;}
		if  (myDataInt<sisDataInt) {
			 checkStatus="after";
		}
		return checkStatus;
	}
}
