package com.refresh.pos.techicalservices;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Real database connector, provides all CRUD operation.
 * database tables are created here.
 * 
 * @author Refresh Team
 *
 */
public class AndroidDatabase extends SQLiteOpenHelper implements Database {

	private static final int DATABASE_VERSION = 1;

	/**
	 * Constructs a new AndroidDatabase.
	 * @param context The current stage of the application.
	 */
	public AndroidDatabase(Context context) {
		super(context, DatabaseContents.DATABASE.toString(), null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {

		database.execSQL("CREATE TABLE " + DatabaseContents.TABLE_ORG + "("

				+ "code TEXT(20),"
				+ "name TEXT(100),"
				+ "address1 TEXT(100),"
				+ "address2 TEXT(100),"
				+ "address3 TEXT(100),"
				+ "phone TEXT(30),"
				+ "line TEXT(30),"
				+ "email TEXT(30),"
				+ "serial TEXT(20),"
				+ "token TEXT(30)"

				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_ORG + " Successfully.");


		database.execSQL("CREATE TABLE " + DatabaseContents.TABLE_PRODUCT_CATALOG + "("
				
				+ "_id INTEGER PRIMARY KEY,"
				+ "cate_id INTEGER,"
				+ "topping_group TEXT(100),"
				+ "name TEXT(100),"
				+ "image TEXT(100),"
				+ "cost DOUBLE,"
				+ "unit_price DOUBLE,"
				+ "status TEXT(10),"
				+ "code TEXT(10),"
				+ "gpk TEXT(100),"
				+ "sync INTEGER"
				
				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_PRODUCT_CATALOG + " Successfully.");


		database.execSQL("CREATE TABLE " + DatabaseContents.TABLE_Category_Product + "("

				+ "category_product_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "pk TEXT(100),"
				+ "sequence INTEGER,"
				+ "parent_id INTEGER,"
				+ "name TEXT(100),"
				+ "sync INTEGER,"
				+ "image TEXT(200),"
				+ "status TEXT(10),"
				+ "type TEXT(50)"

				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_Category_Product + " Successfully.");

		database.execSQL("CREATE TABLE "+ DatabaseContents.TABLE_STOCK + "(" 
				
				+ "_id INTEGER PRIMARY KEY,"
				+ "product_id INTEGER,"
				+ "quantity INTEGER,"
				+ "cost DOUBLE,"
				+ "date_added DATETIME"
				
				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_STOCK + " Successfully.");
		
		database.execSQL("CREATE TABLE "+ DatabaseContents.TABLE_SALE + "("
				
				+ "_id INTEGER PRIMARY KEY,"
				+ "gpk TEXT(40),"
				+ "code TEXT(40),"
				+ "dated DATETIME,"
				+ "table_id INTEGER,"
				+ "user_id INTEGER,"
				+ "member_id INTEGER,"
				+ "payment_id INTEGER,"
				+ "vat_type TEXT(40),"
				+ "vat_percent INTEGER,"
				+ "vat_value INTEGER,"
				+ "total DOUBLE,"
				+ "discount DOUBLE,"
				+ "money_input DOUBLE,"
				+ "money_change DOUBLE,"
				+ "created DATETIME,"
				+ "sync INTEGER,"
				+ "status TEXT(40)"


				
				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_SALE + " Successfully.");

		database.execSQL("CREATE TABLE "+ DatabaseContents.TABLE_SALE_MOCK + "("

				+ "_id INTEGER PRIMARY KEY,"
				+ "status TEXT(40),"
				+ "payment TEXT(50),"
				+ "table_id INTEGER,"
				+ "total DOUBLE,"
				+ "start_time DATETIME,"
				+ "end_time DATETIME,"
				+ "orders INTEGER"

				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_SALE_MOCK + " Successfully.");


		database.execSQL("CREATE TABLE "+ DatabaseContents.TABLE_SALE_LINEITEM + "("
				
				+ "_id INTEGER PRIMARY KEY,"
				+ "gpk TEXT(50),"
				+ "sale_id INTEGER,"
				+ "product_id INTEGER,"
				+ "quantity INTEGER,"
				+ "unit_price DOUBLE,"
				+ "unit_cost DOUBLE,"
				+ "total DOUBLE,"
				+ "topping TEXT(50),"
				+ "topping_name TEXT(50),"
				+ "topping_price DOUBLE,"
				+ "sync INTEGER,"
				+ "status TEXT(40)"
				
				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_SALE_LINEITEM + " Successfully.");


		database.execSQL("CREATE TABLE "+ DatabaseContents.TABLE_SALE_LINEITEM_TEMP + "("

				+ "_id INTEGER PRIMARY KEY,"
				+ "sale_id INTEGER,"
				+ "pk TEXT(50),"
				+ "member_id INTEGER,"
				+ "table_id INTEGER,"
				+ "user_id INTEGER,"
				+ "product_id INTEGER,"
				+ "qty INTEGER,"
				+ "unit_price DOUBLE,"
                + "topping_group INTEGER,"
				+ "topping TEXT(200),"
				+ "topping_name TEXT(200),"
				+ "topping_price DOUBLE,"
				+ "created DATETIME"

				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_SALE_LINEITEM_TEMP + " Successfully.");


		database.execSQL("CREATE TABLE " + DatabaseContents.TABLE_TOPPING + "("

				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "gpk TEXT(20),"
				+ "group_id INTEGER,"
				+ "name TEXT(100),"
				+ "price DOUBLE,"
				+ "image TEXT(100),"
				+ "status TEXT(20)"


				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_TOPPING + " Successfully.");


		database.execSQL("CREATE TABLE " + DatabaseContents.TABLE_TOPPING_GROUP + "("

				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "gpk TEXT(20),"
				+ "name TEXT(100),"
				+ "image TEXT(100),"
				+ "status TEXT(20)"


				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_TOPPING_GROUP + " Successfully.");


		database.execSQL("CREATE TABLE " + DatabaseContents.TABLE_BILL_RUNNING + "("

				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "type TEXT(20),"
				+ "date DATETIME,"
				+ "running_id INTEGER"


				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_BILL_RUNNING + " Successfully.");


		// this _id is product_id but for update method, it is easier to use name _id
		database.execSQL("CREATE TABLE " + DatabaseContents.TABLE_STOCK_SUM + "("
				
				+ "_id INTEGER PRIMARY KEY,"
				+ "quantity INTEGER"
				
				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_STOCK_SUM + " Successfully.");
		
		database.execSQL("CREATE TABLE " + DatabaseContents.LANGUAGE + "("
				
				+ "_id INTEGER PRIMARY KEY,"
				+ "language TEXT(5)"
				
				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.LANGUAGE + " Successfully.");


		database.execSQL("CREATE TABLE " + DatabaseContents.TABLE_TABLE_DETAIL + "("

				+ "table_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "sequence INTEGER,"
				+ "table_name TEXT(20),"
				+ "gpk TEXT(20),"
				+ "status TEXT(20),"
				+ "sync INTEGER,"
				+ "status_service TEXT(20)"


				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_TABLE_DETAIL + " Successfully.");

		database.execSQL("CREATE TABLE " + DatabaseContents.TABLE_Users + "("

				+ "user_id INTEGER PRIMARY KEY,"
				+ "pk TEXT(100),"
				+ "name TEXT(100),"
				+ "phone TEXT(20),"
				+ "code TEXT(200),"
				+ "line TEXT(50),"
				+ "email TEXT(100),"
				+ "username TEXT(50),"
				+ "password TEXT(250),"
				+ "salt TEXT(250),"
				+ "level TEXT(50),"
				+ "start_time DATETIME,"
				+ "sync INTEGER,"
				+ "status TEXT(10)"

				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_Users + " Successfully.");


		Log.d("CREATE DATABASE", "Create Database Successfully.");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	@Override
	public List<Object> select(String queryString) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			List<Object> list = new ArrayList<Object>();
			Cursor cursor = database.rawQuery(queryString, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						ContentValues content = new ContentValues();
						String[] columnNames = cursor.getColumnNames();
						for (String columnName : columnNames) {
							content.put(columnName, cursor.getString(cursor
									.getColumnIndex(columnName)));
						}
						list.add(content);
					} while (cursor.moveToNext());
				}
			}
			cursor.close();
			database.close();
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int insert(String tableName, Object content) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			int id = (int) database.insert(tableName, null,
					(ContentValues) content);
			database.close();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	@Override
	public boolean update(String tableName, Object content) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues cont = (ContentValues) content;
			// this array will always contains only one element. 
			String[] array = new String[]{cont.get("_id")+""};
			database.update(tableName, cont, " _id = ?", array);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateCategory(String tableName, Object content) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues cont = (ContentValues) content;
			// this array will always contains only one element.
			String[] array = new String[]{cont.get("category_product_id")+""};
			database.update(tableName, cont, " category_product_id = ?", array);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateTemp(String tableName, Object content) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues cont = (ContentValues) content;
			// this array will always contains only one element.
			String[] array = new String[]{cont.get("_id")+""};
			database.update(tableName, cont, " _id = ?", array);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean updateTable(String tableName, Object content) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues cont = (ContentValues) content;
			// this array will always contains only one element.
			String[] array = new String[]{cont.get("table_id")+""};
			database.update(tableName, cont, " table_id = ?", array);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

    @Override
    public boolean delete(String tableName, int id) {
            try {
                    SQLiteDatabase database = this.getWritableDatabase();
                    database.delete(tableName, " _id = ?", new String[]{id+""});
                    return true;
                    
            } catch (Exception e) {
                    e.printStackTrace();
                    return false;
            }
    }

	@Override
	public boolean deleteLine(String tableName, int id) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			database.delete(tableName, " sale_id = ?", new String[]{id+""});
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteTableId(String tableName, int id) {
		try {
			SQLiteDatabase database = this.getWritableDatabase();
			database.delete(tableName, " table_id = ?", new String[]{id+""});
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



	@Override
	public boolean execute(String query) {
		try{
			SQLiteDatabase database = this.getWritableDatabase();
			database.execSQL(query);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}


}
