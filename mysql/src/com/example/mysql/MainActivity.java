package com.example.mysql;



import net.sqlcipher.database.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class MainActivity extends Activity {
	EventDataSQLHelper eventsData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SQLiteDatabase.loadLibs(this);
		String password = "foo123";

	    eventsData = new EventDataSQLHelper(this);
	    
	    //then you can open the database using a password
	    SQLiteDatabase db = eventsData.getWritableDatabase(password);
	    
	    for (int i = 1; i < 100; i++)
	    	addEvent("Hello Android Event: " + i, db);
	    
	   db.close();
	   
	   db = eventsData.getReadableDatabase(password);
	   
	    Cursor cursor = getEvents(db);
	    showEvents(cursor);
	    
	    db.close();

	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	private void addEvent(String title, SQLiteDatabase db) {
	    
	    ContentValues values = new ContentValues();
	    values.put(EventDataSQLHelper.TIME, System.currentTimeMillis());
	    values.put(EventDataSQLHelper.TITLE, title);
	    db.insert(EventDataSQLHelper.TABLE, null, values);
	  }

	  private Cursor getEvents(SQLiteDatabase db) {
	    
	    Cursor cursor = db.query(EventDataSQLHelper.TABLE, null, null, null, null,
	        null, null);
	    
	    startManagingCursor(cursor);
	    return cursor;
	  }

	  private void showEvents(Cursor cursor) {
	    StringBuilder ret = new StringBuilder("Saved Events:\n\n");
	    while (cursor.moveToNext()) {
	      long id = cursor.getLong(0);
	      long time = cursor.getLong(1);
	      String title = cursor.getString(2);
	      ret.append(id + ": " + time + ": " + title + "\n");
	    }
	    
	    Log.i("sqldemo",ret.toString());
	  }
}
