package gr.hua.dit.it21530.assignment1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;

import java.sql.Timestamp;

public class LocationContentProvider extends ContentProvider {

    private DbHelper dbHelper;
    static public UriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        //create URIs for the following 2 cases: 1) listing all locations in database and 2) inserting a location
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("gr.hua.dit.it21530.assignment1", "locations", 1 );
        uriMatcher.addURI("gr.hua.dit.it21530.assignment1", "locations/*", 3);
        uriMatcher.addURI("gr.hua.dit.it21530.assignment1", "save", 2);

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        //a switch is used instead of an if clause, as we might need to add more cases if we need to
        switch (uriMatcher.match(uri)) {
            case 1:
                cursor = dbHelper.getReadableDatabase().query(
                        DbHelper.DB_NAME,
                        null,
                        DbHelper.COL_USERID + "=?",
                        selectionArgs,
                        null,
                        null,
                        null);
                break;
            case 3:
                String paths[] = uri.toString().split("/");
                String userid = paths[paths.length-1];
                cursor = dbHelper.getReadableDatabase().query(
                        DbHelper.DB_NAME,
                        null,
                        DbHelper.COL_USERID + "=?",
                        new String[]{userid},
                        null,
                        null,
                        null);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) == 2) {
            //add timestamp to contentValues and insert it into database
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues newvalues = new ContentValues(values);
            newvalues.put(DbHelper.COL_DT, (new Timestamp(System.currentTimeMillis())).toString());
            long i = sqLiteDatabase.insert(DbHelper.DB_NAME, null, newvalues);
            if ( i != -1 ) {
                return null;
            }
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
