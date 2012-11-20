package ca.ualberta.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ca.ualberta.backend.User;

/*
 * References used: http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 * */
public class SQLiteAuthentication extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	private static final String DB_NAME = "UserDB.db";
	private static final String TABLE_NAME = "Users";
	private static final String USER_COL = "username";
	private static final String PASS_COL = "password";

	public SQLiteAuthentication(Context context){
		super(context, DB_NAME, null, VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String NewTableStatement = "CREATE TABLE " + TABLE_NAME + " " +
				                   "(" + USER_COL + " TEXT PRIMARY KEY, " +
				                         PASS_COL + " TEXT)";
		db.execSQL(NewTableStatement);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	/* Adds user to database.
	 * If successful, return 0.
	 * If failure, return 1.
	 */
	public int AddRecord(User new_user){
		ContentValues new_values = new ContentValues(2);
		new_values.put(USER_COL, new_user.getUsername());
		new_values.put(PASS_COL, new_user.getPassword());
		
		SQLiteDatabase db = this.getWritableDatabase();
		long status = db.insertOrThrow(TABLE_NAME, null, new_values);
		db.close();
		
		return ( (status == -1) ? 1 : 0 );
	}
	
	public int FindRecord(User q_user){
		String[] columns = new String[]{USER_COL};
		String[] selectionArgs = new String[]{q_user.getUsername()};

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, columns, USER_COL + "=?", selectionArgs, null, null, null);
		db.close();
		if (!cursor.isNull(0)){
			cursor.close();
			//User exists
			return 0;
		} else {
			cursor.close();
			//User does not exist
			return 1;
		}
	}
}
