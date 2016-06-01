package com.example.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CheckTableExist {
	public static boolean exec(SQLiteDatabase db, String tableName, String columnName) {
		boolean result = false;
		Cursor cursor = null;
		try {
			// ≤È—Ø“ª––
			cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
			result = cursor != null && cursor.getColumnIndex(columnName) != -1;
		} catch (Exception e) {
			// Log.e(TAG,"checkColumnExists1..." + e.getMessage()) ;
		} finally {
			if (null != cursor && !cursor.isClosed()) {
				cursor.close();
			}
		}

		return result;

	}

}
