package com.notes.iit.simplenotesmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;

/**
 * Created by amardeep on 10/26/2017.
 */

public class SqliteHelper extends SQLiteOpenHelper {

    //DATABASE NAME
    public static final String DATABASE_NAME = "notes";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";

    //COLUMN email
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_CONTACTNO = "contactNo";


    //COLUMN password
    public static final String KEY_PASSWORD = "password";

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_FIRST_NAME + " TEXT, "
            + KEY_LAST_NAME + " TEXT, "
            + KEY_GENDER + " TEXT, "
            + KEY_CONTACTNO + " INTEGER, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";
    public static final String TABLE_NOTES = "note";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_MODIFIEDDATE = "date";
    public static final String SQL_TABLE_NOTES = " CREATE TABLE " + TABLE_NOTES
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_MODIFIEDDATE + " STRING"
            + " ) ";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_NOTES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    public void addNote(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DESCRIPTION, note.description);
        values.put(KEY_MODIFIEDDATE, note.date);
        db.insert(TABLE_NOTES, null, values);
    }

    public Cursor retriveAllNotesCursor() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select rowid as _id," + KEY_DESCRIPTION + "," + KEY_MODIFIEDDATE + " from " + TABLE_NOTES, null);
        return cur;
    }

    public void addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_EMAIL, user.email);

        values.put(KEY_PASSWORD, user.password);

        values.put(KEY_FIRST_NAME, user.firstName);

        values.put(KEY_LAST_NAME, user.lastName);

        values.put(KEY_GENDER, user.gender);

        values.put(KEY_CONTACTNO, user.contactNo);

        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public User retreiveUserByEmail(String email) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_USERS, null, KEY_EMAIL + "=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String mail = cursor.getString(cursor.getColumnIndex(KEY_EMAIL));
            String password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
            String firstName = cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME));
            String gender = cursor.getString(cursor.getColumnIndex(KEY_GENDER));
            String contactNo = cursor.getString(cursor.getColumnIndex(KEY_CONTACTNO));

            User retreivedUser = new User(mail, password, firstName, lastName, gender, contactNo);
            return retreivedUser;
        }
        return null;
    }


    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                null,
                KEY_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public boolean deleteNoteList() {

        String table = TABLE_NOTES;
        SQLiteDatabase database = this.getReadableDatabase();
        int result = database.delete(table, null, null);
        if (result > 0)
            return true;
        else
            return false;
    }

    public boolean deleteNoteList(String rowId) {

        String table = TABLE_NOTES;
        SQLiteDatabase database = this.getReadableDatabase();
        int result = database.delete(table, KEY_ID + "=?", new String[]{rowId});
        if (result > 0)
            return true;
        else
            return false;
    }

    public void updateUser(User user) {

        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_FIRST_NAME, user.firstName);

        values.put(KEY_LAST_NAME, user.lastName);

        values.put(KEY_GENDER, user.gender);

        values.put(KEY_CONTACTNO, user.contactNo);

        values.put(KEY_PASSWORD, user.password);

        sqldb.update(TABLE_USERS, values, KEY_EMAIL + "=?" , new String[]{user.email});
        sqldb.close();
    }


}
