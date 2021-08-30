package com.privexec.DBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;


public class AllAppDB extends DBHandler {

    public static final String KEY_ID = "_id";
    public static final String KEY_appName = "appName";
    public static final String KEY_packageName = "packageName";
     String TAG = "AllAppDB ";

    public AllAppDB(Context context) {
        super(context);
    }
     public void addItemInDB(String appName, String packageName ) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_appName,appName);
            values.put(KEY_packageName,packageName);

            db.insert(AllApps_Table, null, values);
            db.close();
            Log.e(TAG + "item_add ", "sucess  appName=" + appName + " packageName=" + packageName);

        } catch (Exception e) {
            Log.e(TAG + "cart item add", "exception");
        }
    }

 /*   public void deleteCartItem(String productId, String proUnit) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + FruitVeg_TABLE_NAME + " WHERE " + KEY_unit + " = '" + proUnit + "' AND " + KEY_productId + " = " + productId); //delete all rows in a table
            db.close();
            Log.e(TAG + "cart item deleted", "sucess");

        } catch (Exception e) {
            Log.e(TAG + "cart item deleted", "exceptiong" + e.toString());
        }
    }
*/

    public int getAllCartProductCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String QUERY = "SELECT * FROM " + AllApps_Table;
            Cursor cursor = db.rawQuery(QUERY, null);
            count = cursor.getCount();
        } catch (Exception e) {
            Log.e(TAG + "excep getAllApps", e + "");
        }
        db.close();
        return count;

    }

    public ArrayList<String> getAllCartProductList() {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> arrayLiAllPro = new ArrayList<String>();
        try {
            String QUERY = "SELECT * FROM " + AllApps_Table;

            HashMap<String, String> hashMap = null;

            Cursor cursor = db.rawQuery(QUERY, null);
            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    arrayLiAllPro.add(cursor.getString(1));
                }
            }
        } catch (Exception e) {
            Log.e("excep getAllCartProduLi", e + "");

        }
        db.close();
        return arrayLiAllPro;


    }

    public void deleteAllRecord() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + AllApps_Table); //delete all rows in a table
        db.close();
    }



}
