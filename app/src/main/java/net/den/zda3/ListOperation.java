package net.den.zda3;


import android.content.*;
import android.database.*;
import android.os.*;
import android.support.v4.app.LoaderManager.*;
import android.support.v4.content.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
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
чуждый таймпикер
полный выход
подправить тему
использовать прикосновения вместо клика
нужно ли возвращать интент из редактора
закрыть курсоры и дб
интернироуать, буилдировать и константировать
предложить удалять поврежденную базу
сделать службу неубивашкой
 ThemeOverlay.Material.Dark
 уточнить импорты

--------------------------*/

// главное окно , список событий
public class ListOperation   extends AppCompatActivity implements LoaderCallbacks<Cursor>
	
{
	private static final int CM_DELETE_ID = 1;
	private static final int RESUL_TASK = 2;

	private ListView lvData;
	private Button btAdd,btMenu;
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
		btMenu=(Button) findViewById(R.id.bt_menu);
		btAdd=(Button) findViewById(R.id.bt_add);
		
		lvData = (ListView) findViewById(R.id.lvData);
        initz();

	}

	private void initz() {
		
		intntEnter =getIntent();
		
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
									data.getStringExtra("status"),
									data.getStringExtra("name"),
									data.getStringExtra("determin"),
									R.drawable.ic_launcher,
									data.getLongExtra("id",-1));
					}break;
					case "add":{
						db.addRec(data.getStringExtra("time"),
								  data.getStringExtra("status"),
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
			case R.id.bt_menu:{
				openOptionsMenu();
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
	
	// меню
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add("setting");
		menu.add("check time");
		menu.add("check stat");
		menu.add("show dialAct");
		return true;
		//return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getTitle().toString()){
		   case "setting":
		   	Intent intnSetng = new Intent(getApplicationContext(),Settin.class);
			startActivity(intnSetng);break;
		   case "check time":
			   // to do: delete debug code in unl.service in startServ
			   Intent intnCheckTime = new Intent(getApplicationContext(),UnlockStartService.class);
			   intnCheckTime.putExtra("action", "check time");
			   startService(intnCheckTime);break;
		   case "check stat":
			   Intent intnAllTime = new Intent(getApplicationContext(),UnlockStartService.class);
			   intnAllTime.putExtra("action", "check stat");
			   startService(intnAllTime);break;
		   case "show dialAct":
			   Intent intnDial=new Intent(getApplicationContext(),Dial.class);
			   startActivity(intnDial);
			   
		 }

        return super.onOptionsItemSelected(item);
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

