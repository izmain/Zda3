package net.den.zda3;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.*;
import android.view.View.*;
import android.content.*;

/*-------------------

TODO:
ознакомится с гуидлайном названия переменных
переименовать мэйнактив
врубить редактор
запуск редактора при создании нью таска
парсинг временной строки
подправить тему
использовать прикосновения вместо клика

--------------------------*/


public class ListOperation   extends FragmentActivity implements LoaderCallbacks<Cursor>
	
{
	private static final int CM_DELETE_ID = 1;
	private static final int RESUL_TASK = 2;

	private ListView lvData;
	private Button btAdd;
	private EditText et;
	
	private Intent intntEnter;
    private Intent intnEditor;
	DB db;
	SimpleCursorAdapter scAdapter;

	/** onCreate **/
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listop);
		et=(EditText) findViewById(R.id.etOfList);

		btAdd=(Button) findViewById(R.id.bt_add);
		btAdd.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v)
				{onButtonClick(v);}});  //TODO : its is creasy
		lvData = (ListView) findViewById(R.id.lvData);
		lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			    @Override
			     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				    intnEditor= new Intent(getApplicationContext(),BaseEdit.class);
                    intnEditor.putExtra("item", position);
					intnEditor.putExtra("id", id);
					intnEditor.putExtra("target", "edit");
					 
					 startActivityForResult(intnEditor, RESUL_TASK);
			     }
		       });
		initz();

	}

	private void initz() {
		intntEnter =getIntent();
		et.setText(intntEnter.getStringExtra("time"));
		db = new DB(this);
		db.open();

		// формируем столбцы сопоставления
		String[] from = new String[] { DB.COLUMN_IMG, DB.COLUMN_TXT };
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
		switch (requestCode) {
			case RESUL_TASK:
				int numTask=data.getIntExtra("number task",-1);
				String nameTask=(data.getStringExtra("name task"));
				String terminTask=(data.getStringExtra("determinate"));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	//-------------------------------
	//         НАЖАТИЯ
	//--------------------------------

	// нажатия кнопки
	public void onButtonClick(View v) {
		switch (v.getId() ){
			case R.id.bt_add:{
				intnEditor= new Intent(getApplicationContext(),BaseEdit.class);
				intnEditor.putExtra("target", "add");//ToDO add constantS
		      //todo fix addrec
				db.addRec(et.getText().toString(), R.drawable.ic_launcher);
		      // получаем новый курсор с данными

		      //todo ---- getSupportLoaderManager().getLoader(0).forceLoad();
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
			AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item
				.getMenuInfo();
			// извлекаем id записи и удаляем соответствующую запись в БД
			db.delRec(acmi.id);
			// получаем новый курсор с данными
			getSupportLoaderManager().getLoader(0).forceLoad();
			return true;
		}
		return super.onContextItemSelected(item);
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

