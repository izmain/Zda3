package net.den.zda3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {

	private static final String DB_NAME = "mydb";
	private static final int DB_VERSION = 1;
	private static final String DB_TABLE = "mytab";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_IMG = "img";
	public static final String COLUMN_TIM = "time";
	public static final String COLUMN_NAM = "name";
	public static final String COLUMN_TER = "termin";

	private static final String DB_CREATE = 
    "create table " + DB_TABLE + "(" +
	COLUMN_ID + " integer primary key autoincrement, " +
	COLUMN_IMG + " integer, " +
	COLUMN_TIM + " text, " +
	COLUMN_NAM + " text, " +
	COLUMN_TER + " text " +
    ");";

	private final Context mCtx;


	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;

	public DB(Context ctx) {
		mCtx = ctx;
	}

	// открыть подключение
	public void open() {
		mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
		mDB = mDBHelper.getWritableDatabase();
	}

	// закрыть подключение
	public void close() {
		if (mDBHelper!=null) mDBHelper.close();
	}

	// получить все данные из таблицы DB_TABLE
	public Cursor getAllData() {
		return mDB.query(DB_TABLE, null, null, null, null, null, null);
		
	}
	
	public String[] getTimes(){
		String[] s=new String[]{DB.COLUMN_TIM};
		Cursor c= mDB.query (DB_TABLE, s, null, null, null, null, null); // clear
		c.moveToFirst();
		String[] times=new String[c.getCount()] ;
		for (int i=0;i<c.getCount();i++){
			times[i]=c.getString(0);
			c.moveToNext();
		}
		return times;
	}
	
	public String[] getRecData(int iNo){
		String[] recData= new String[3];
		String[] columnRec= {COLUMN_NAM, COLUMN_TER, COLUMN_TIM};
		Cursor c= mDB.query(DB_TABLE,  columnRec, null, null, null, null, null); // clear
		c.moveToPosition(iNo);
		int i=c.getColumnIndex(COLUMN_NAM);
		String s=c.getString(i);
		recData[0]= s;
		
		recData[1]= c.getString(c.getColumnIndex(COLUMN_TER));
		recData[2]= c.getString(c.getColumnIndex(COLUMN_TIM));
		return recData;
		

	}

	// добавить запись в DB_TABLE
	public void addRec(String time, String name, String determin,  int img) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_TIM, time);
		cv.put(COLUMN_NAM, name);
		cv.put(COLUMN_TER, determin);
		cv.put(COLUMN_IMG, img);
		mDB.insert(DB_TABLE, null, cv);
	}

	// удалить запись из DB_TABLE
	public void delRec(long id) {
		mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
	}

	// edit record in DB
	public void editRec(String time, String name, String determin,  int img, long id) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_TIM, time);
		cv.put(COLUMN_NAM, name);
		cv.put(COLUMN_TER, determin);
		cv.put(COLUMN_IMG, img);
		mDB.update(DB_TABLE, cv, COLUMN_ID + " = " + id, null);
	}


	// класс по созданию и управлению БД
	private class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context, String name, CursorFactory factory,
						int version) {
			super(context, name, factory, version);
		}

		// создаем и заполняем БД
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CREATE);

			ContentValues cv = new ContentValues();
			cv.put(COLUMN_IMG, R.drawable.ic_launcher);
			cv.put(COLUMN_TIM, "0000000000000");
			cv.put(COLUMN_NAM, "addnewtask");
			cv.put(COLUMN_TER, "");
			
			db.insert(DB_TABLE, null, cv);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
}
