package com.app.habr;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DbAdapter {

	private static final String DATABASE_NAME = "pressure.db";
	private static final String DATABASE_TABLE = "pTable";
	private static final String TABLE_CREATE =
	"create table " + DATABASE_TABLE + " ( _id integer primary key	autoincrement," +
	"_time NUMERIC not null, pressure text not null);";
	private static final String TABLE_DROP =
			"drop table IF EXISTS " + DATABASE_TABLE+";";
	SQLiteDatabase myDatabase;
	
	public void createDatabase(Context ctx) {
	Log.i(DbAdapter.class.getName(), "Creating database ***********************");
	myDatabase = ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
	try{
	myDatabase.execSQL(TABLE_DROP);
	}catch (SQLiteException ex){}
	myDatabase.execSQL(TABLE_CREATE);
	Log.i(DbAdapter.class.getName(), "Created database ***********************");
	
	}
	
	public void insValue(Context ctx){
		
		
	// Создайте новую строку со значениями для вставки.
	ContentValues newValues = new ContentValues();
	// Задайте значения для каждой строки.
	newValues.put("_time", System.currentTimeMillis());
	newValues.put("pressure", "221");
		// Вставьте строку в вашу базу данных.
	myDatabase.insert(DATABASE_TABLE, null, newValues);
	newValues.put("_time", System.currentTimeMillis());
	newValues.put("pressure", "2ssssssss21");
		myDatabase.insert(DATABASE_TABLE, null, newValues);
	
	
	}

	public void getValue(Context applicationContext) {
		// Возвращает все строки для первого и третьего столбца, без повторений
		String[] result_columns = new String[] {"_id", "column_one"};
		Cursor allRows = myDatabase.query(true, DATABASE_TABLE, result_columns,	null, null, null, null, null, null);
		if (allRows.moveToFirst()) {
			// Пройдитесь по каждой строке.
			do {
				Long s1 = allRows.getLong(1);
				String s2 = allRows.getString(2);
				Log.i(DbAdapter.class.getName(), "readed "+s1+" "+s2);
			} while(allRows.moveToNext());
			}
		
	}
	
}
