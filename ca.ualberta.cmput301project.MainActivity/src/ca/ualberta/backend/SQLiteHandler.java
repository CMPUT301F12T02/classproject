package ca.ualberta.backend;

import android.content.ContentValues;
import android.content.Context;
<<<<<<< HEAD
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
		
=======
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ca.ualberta.backend.User;

/*
 * References used:
 * http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 * http://huuah.com/using-sqlite-from-your-android-app/
 * */
public class SQLiteHandler extends SQLiteOpenHelper {
	private SQLiteHandler dbHelper;
	private SQLiteDatabase db;
	private final Context dbcontext;
	private static final int VERSION = 1;
	/* File path = /data/data/ca.ualberta/frontend/databases/UserDB.db
	 * Access:
	 * % adb devices
	 * % adb -s emulator-5554 shell
	 * OR
	 * Eclipse: Window (tab) > Show View > Other... > "Android/File Explorer"
	 */
	private static final String DB_NAME = "UserDB.db";
	
	private static final String TABLE_NAME = "Users";
	private static final String USER_COL = "username";
	private static final String PASS_COL = "password";

	public SQLiteHandler(Context context){
		super(context, DB_NAME, null, VERSION);
		this.dbcontext = context;
	}
	@Override
	public void onCreate(SQLiteDatabase new_db) {
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
	public SQLiteHandler open() throws SQLException {
		dbHelper = new SQLiteHandler(dbcontext);
		db = dbHelper.getReadableDatabase();
		return this;
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

		db = this.getReadableDatabase();
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
>>>>>>> 22314e0068d7bd1447ce6b69d96ce9944ec7c647
	}
}
