package com.example.databasegjs;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * The DatabaseHandler stores saved jobs(selected by user) in local machine.
 * Saved jobs can be added/removed.
 * All data is operated based on KEY_JOBID which is defined in server and passed to local machine.
 * */
public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "GJSdata";

	// Contacts table name
	private static final String TABLE_STOREDATA = "script";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_JOBID = "jobid";
	private static final String KEY_TITLE = "title";
	private static final String KEY_COMPANY = "company";
	private static final String KEY_SALARY = "salary";
	private static final String KEY_LOCATION = "location";
	private static final String KEY_DETAIL = "detail";
	private static final String KEY_SAVE = "save";

	private final ArrayList<StoreData> store_list = new ArrayList<StoreData>();

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_STOREDATA + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_JOBID + " TEXT," + KEY_TITLE + " TEXT," + KEY_COMPANY
				+ " TEXT," + KEY_SALARY + " TEXT," +KEY_LOCATION + " TEXT," + KEY_DETAIL + " TEXT," 
				+ KEY_SAVE + " TEXT"+ ")";

		db.execSQL(CREATE_DATA_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOREDATA);
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations The following
	 * TABLE_CONTACTS methods are for EachData
	 */

	public void Add_Data(StoreData data) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID, data.getId());
		values.put(KEY_JOBID, data.getJobId());
		values.put(KEY_TITLE, data.getTitle());
		values.put(KEY_COMPANY, data.getCompany());
		values.put(KEY_SALARY, data.getSalary());
		values.put(KEY_LOCATION, data.getLocation());
		values.put(KEY_DETAIL, data.getDetail());
		values.put(KEY_SAVE, data.getSave());

		db.insert(TABLE_STOREDATA, null, values);
		db.close(); // Closing database connection
	}

	// Getting single job 
	StoreData Get_Job(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_STOREDATA, new String[] { KEY_ID, KEY_JOBID,
				KEY_TITLE, KEY_COMPANY, KEY_SALARY, KEY_LOCATION, KEY_DETAIL, KEY_SAVE }, KEY_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);
		if (cursor != null)
			cursor.moveToFirst();
		StoreData data = new StoreData(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5), cursor.getString(6),
				cursor.getString(7));
		// return data
		cursor.close();
		db.close();

		return data;
	}

	// Getting All Data
	public ArrayList<StoreData> Get_Datas() {
		try {
			store_list.clear();

			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_STOREDATA;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					StoreData data = new StoreData();
					data.setId(Integer.parseInt(cursor.getString(0)));
					data.setJobId(cursor.getString(1));
					data.setTitle(cursor.getString(2));
					data.setCompany(cursor.getString(3));
					data.setSalary(cursor.getString(4));
					data.setLocation(cursor.getString(5));
					data.setDetail(cursor.getString(6));
					data.setSave(cursor.getString(7));

					// Adding data to list
					store_list.add(data);
				} while (cursor.moveToNext());
			}

			// return contact list
			cursor.close();
			db.close();
			return store_list;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("all_job", "" + e);
		}

		return store_list;
	}

	// Getting Job Count
	public int Get_Total_Datas() {
		String countQuery = "SELECT  * FROM " + TABLE_STOREDATA;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
	

	
	// Deleting single job
	public void Delete_Job(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STOREDATA, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}
}
