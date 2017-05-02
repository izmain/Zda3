package net.den.zda3;


import android.content.*;
import android.database.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.app.LoaderManager.*;
import android.support.v4.content.*;
import android.support.v4.widget.*;
import android.view.*;
import android.view.ContextMenu.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;

import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View.OnClickListener;

/*-------------------
TODO:
парсинг временной строки
чуждый таймпикер
полный выход
подправить тему
использовать прикосновения вместо клика
нужно ли возвращать интент из редактора
закрыть курсоры и дб
интернироуать, буилдировать и константировать
--------------------------*/


public class ListOperation   extends FragmentActivity implements LoaderCallbacks<Cursor>
	
{
	private static final int CM_DELETE_ID = 1;
	private static final int RESUL_TASK = 2;

	private ListView lvData;
	private Button btAdd;
	//private EditText et;
	
	private Intent intntEnter;
    private Intent intnEditor;
	DB db;
	SimpleCursorAdapter scAdapter;


	/** onCreate **/
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listop);
		//et=(EditText) findViewById(R.id.etOfList);

		btAdd=(Button) findViewById(R.id.bt_add);
		btAdd.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v)
				{onButtonClick(v);}});  //TODO : its is creasy
		lvData = (ListView) findViewById(R.id.lvData);
        initz();

	}

	private void initz() {
		
		intntEnter =getIntent();
		//et.setText(intntEnter.getStringExtra("time"));
		db = new DB(this);
		db.open();
		lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			    @Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				    intnEditor= new Intent(getApplicationContext(),BaseEdit.class);
                    intnEditor.putExtra("item", position);
					intnEditor.putExtra("id", id);
					intnEditor.putExtra("target", "edit");
					String[] editorExtra=db.getRecData(position);
					intnEditor.putExtra("name", editorExtra[0]);
					intnEditor.putExtra("termin", editorExtra[1]);
					intnEditor.putExtra("time", editorExtra[2]); // to do  very bad cod
					startActivityForResult(intnEditor, RESUL_TASK);
				}
		});

		// формируем столбцы сопоставления
		String[] from = new String[] { DB.COLUMN_IMG, DB.COLUMN_NAM };
		int[] to = new int[] { R.id.ivImg, R.id.tvText };

		// создаем адаптер и настраиваем список
		scAdapter = new SimpleCursorAdapter(this, R.layout.my_item, null, from, to, 0);
		lvData.setAdapter(scAdapter);
		registerForContextMenu(lvData);
		getSupportLoaderManager().initLoader(0, null, this);
	}

	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode==RESULT_OK){
		switch (requestCode) {
			case RESUL_TASK:
				String targ=data.getStringExtra("action");
				switch (targ){
					case "edit":{
						db.editRec(data.getStringExtra("time"),
									data.getStringExtra("name"),
									data.getStringExtra("determin"),
									R.drawable.ic_launcher,
									data.getLongExtra("id",-1));
					}break;
					case "add":{
						db.addRec(data.getStringExtra("time"),
						          data.getStringExtra("name"),
								  data.getStringExtra("determin"),
								  R.drawable.ic_launcher);
					}break;
					case "del":
						Long idI=data.getLongExtra("id",-1);
						db.delRec(idI);
					}break;	
				}
		getSupportLoaderManager().getLoader(0).forceLoad();
		  // super.onActivityResult(requestCode, resultCode, data);
		}}
		
	//-------------------------------
	//         НАЖАТИЯ
	//--------------------------------

	// нажатия кнопки
	public void onButtonClick(View v) {
		switch (v.getId() ){
			case R.id.bt_add:{
				intnEditor= new Intent(getApplicationContext(),BaseEdit.class);
				intnEditor.putExtra("target", "add");//ToDO add constantS
				startActivityForResult(intnEditor, RESUL_TASK);
			}break;
		}
	}

    // долгий тап
	public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
	}

    // контекст
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == CM_DELETE_ID) {
			// получаем из пункта контекстного меню данные по пункту списка
			AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
			// извлекаем id записи и удаляем соответствующую запись в БД
			db.delRec(acmi.id);
			// получаем новый курсор с данными
			getSupportLoaderManager().getLoader(0).forceLoad();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add("setting");
		return super.onCreateOptionsMenu(menu);
	}


	



	//-------------------------------
	//         DEBAG
	//--------------------------------

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
		return new MyCursorLoader(this, db);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		scAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}

	// class MyCursorLoader
	static class MyCursorLoader extends CursorLoader {

		DB db;

		public MyCursorLoader(Context context, DB db) {
			super(context);
			this.db = db;
		}

		@Override
		public Cursor loadInBackground() {
			Cursor cursor = db.getAllData();
			
			return cursor;
		}
	}
}

