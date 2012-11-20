package ca.ualberta.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 */
public class SQLiteHandler extends SQLiteOpenHelper {

	public SQLiteHandler(Context context){
		super(context, "UserDB.db", null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CreateTable = "CREATE TABLE Users (username TEXT PRIMARY KEY, password TEXT)";
		db.execSQL(CreateTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS Users");
		onCreate(db);
	}
	
	public void insertRecord(String new_username, String new_password){
		ContentValues values = new ContentValues(2);
		values.put("username", new_username);
		values.put("password", new_password);
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.insertOrThrow("Users", null, values);
	}
	public void retrieveRecord(String username){
		
	}
}
