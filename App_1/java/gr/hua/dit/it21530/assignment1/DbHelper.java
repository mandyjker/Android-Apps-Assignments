package gr.hua.dit.it21530.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "MY_DB";
    public static final int DB_VERSION = 1;
    public static final String COL_ID ="_ID";
    public static final String COL_USERID = "USERID";
    public static final String COL_LONGITUDE = "LONGITUDE";
    public static final String COL_LATITUDE = "LATITUDE";
    public static final String COL_DT= "DT";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Create database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
        "CREATE TABLE MY_DB ( "+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                +COL_USERID+" TEXT NOT NULL, "
                +COL_LONGITUDE+" REAL NOT NULL, "
                +COL_LATITUDE+" REAL NOT NULL, "
                +COL_DT+" TEXT NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*The following methods are not used, but have been created in the first Assignment of 2019
    and have been used in this Assignment for debugging purposes, as there are useful methods
    like viewing all items in database*/

    // Insert in database
    public long insert(DBContract dbContract) {
        // insert row into database (don't insert ID, because it increments automatically
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERID, dbContract.getUserid());
        values.put(COL_LONGITUDE, dbContract.getLongitude());
        values.put(COL_LATITUDE, dbContract.getLatitude());
        values.put(COL_DT, dbContract.getDt());
        return sqLiteDatabase.insert(DB_NAME, null, values);
    }

    // Select all timestamps saved in database
    public List<String> selectAllDt() {
        List<String> list = new ArrayList<>();
        // insert "blank" timestamp as first object in list
        list.add("-");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String columns[] = { COL_DT };
        // create query "SELECT column_dt FROM database"
        Cursor mycursor = sqLiteDatabase.query(DB_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);
        if (mycursor.moveToFirst()) {
            // add all timestamps to list
            do {
                list.add(mycursor.getString(0));
            } while (mycursor.moveToNext());
            mycursor.close();
        }
        return list;
    }

    // Select everything from database where UserID and Timestamp
    public List<DBContract> selectAllWithUserIdAndDt(String userID, String timestamp) {
        List<DBContract> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selection;
        String selectionArgs[];
        // if user selected only timestamp but not userID, then query selects WHERE timestamp = userInput
        if (userID.equals("") && !timestamp.equals("-")) {
            selection = COL_DT+"=?";
            selectionArgs = new String[]{ timestamp };
        // if user selected only userID but not timestamp, then query selects WHERE userID = UserInput
        } else if ( !userID.equals("") && timestamp.equals("-") ) {
            selection = COL_USERID+"=?";
            selectionArgs = new String[]{ userID };
        // if user selected both, then query selects WHERE userID=UserInput AND timestamp=UserInput
        } else if ( !userID.equals("") && !timestamp.equals("-") ) {
            selection = COL_USERID +"=? and "+COL_DT+"=?";
            selectionArgs = new String[]{ userID, timestamp };
        // else query selects EVERYTHING (no WHERE clause)
        } else {
            selection = null;
            selectionArgs = null;
        }
        // execute query with the WHERE clause created above
        Cursor mycursor = sqLiteDatabase.query(DB_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if (mycursor.moveToFirst()) {
            do {
                // Create DBContract object and add it to list
                String id = mycursor.getString(0);
                String userid = mycursor.getString(1);
                String longitude = mycursor.getString(2);
                String latitude = mycursor.getString(3);
                String dt = mycursor.getString(4);
                DBContract dbContract = new DBContract(Integer.valueOf(id), userid, Float.valueOf(longitude), Float.valueOf(latitude), dt);
                list.add(dbContract);
            } while (mycursor.moveToNext());
            mycursor.close();
        }
        return list;
    }

    //select all from db
    public List<String> selectAllRows() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        // create query "SELECT * FROM database"
        Cursor mycursor = sqLiteDatabase.query(DB_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        if (mycursor.moveToFirst()) {
            // add all timestamps to list
            do {
                String id = mycursor.getString(0);
                String userid = mycursor.getString(1);
                String longitude = mycursor.getString(2);
                String latitude = mycursor.getString(3);
                String dt = mycursor.getString(4);
                DBContract dbContract = new DBContract(Integer.valueOf(id), userid, Float.valueOf(longitude), Float.valueOf(latitude), dt);
                list.add(dbContract.toString());
            } while (mycursor.moveToNext());
            mycursor.close();
        }
        return list;
    }

    //delete row from db
    public int deleteFromDB(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(DB_NAME, COL_ID + "=?", new String[]{id});
    }
}
