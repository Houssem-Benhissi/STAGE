package com.example.stage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConnect extends SQLiteOpenHelper {

    private static  String dbname = "DB_STAGE";
    private static String dbtable = "user";
    private static  int version = 3;

    private static String ID = "id";
    private static String USERNAME = "username";
    private static String PASSWORD = "password";

    private static final String TECHNICIAN_TABLE = "technician";
    private static final String TECHNICIAN_ID = "id";
    private static final String TECHNICIAN_NAME = "name";
    private static final String TECHNICIAN_SPECIALTY = "specialty";


    private static final String MACHINE_TABLE = "machine";
    private static final String MACHINE_ID = "id";
    private static final String MACHINE_NAME = "name";

    public DBConnect(@Nullable Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table " + dbtable + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " TEXT, " + PASSWORD + " TEXT)";
        sqLiteDatabase.execSQL(query);


        String createTechnicianTableQuery = "CREATE TABLE " + TECHNICIAN_TABLE + "(" +
                TECHNICIAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TECHNICIAN_NAME + " TEXT, " +
                TECHNICIAN_SPECIALTY + " TEXT)";
        sqLiteDatabase.execSQL(createTechnicianTableQuery);


        String createMachineTableQuery = "CREATE TABLE " + MACHINE_TABLE + "(" +
                MACHINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MACHINE_NAME + " TEXT)";
        sqLiteDatabase.execSQL(createMachineTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + dbtable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TECHNICIAN_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MACHINE_TABLE);
        onCreate(sqLiteDatabase);

    }
    public void adduser(SQLiteDatabase db, USER user){
        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());
        db.insert(dbtable, null, values);
    }
    public boolean isValidUser(String username , String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"username"};
        String selection = "username = ? AND password = ?";

        String[] selectionArgs = {username, password};
        Cursor cursor = null;
        try {
            cursor = db.query("user", columns, selection, selectionArgs, null, null, null);

            boolean isValid = cursor.getCount() > 0;
            return  isValid;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }
    public long addTechnician(Technician technician) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TECHNICIAN_NAME, technician.getName());
        values.put(TECHNICIAN_SPECIALTY, technician.getSpecialty());
        long id = db.insert(TECHNICIAN_TABLE, null, values);
        db.close();
        return id; // Returns the ID of the newly inserted technician, or -1 on error.
    }

    // Get all technicians
    public Cursor getAllTechnicians() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TECHNICIAN_TABLE, null, null, null, null, null, null);
    }

    // Update a technician
    public int updateTechnician(Technician technician) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TECHNICIAN_NAME, technician.getName());
        values.put(TECHNICIAN_SPECIALTY, technician.getSpecialty());
        int rowsAffected = db.update(TECHNICIAN_TABLE, values, TECHNICIAN_ID + " = ?",
                new String[]{String.valueOf(technician.getId())});
        db.close();
        return rowsAffected; // Returns the number of rows updated (1 if successful, 0 if not found)
    }

    // Delete a technician
    public int deleteTechnician(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TECHNICIAN_TABLE, TECHNICIAN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected; // Returns the number of rows deleted (1 if successful, 0 if not found)
    }
    public long addMachine(Machine machine) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MACHINE_NAME, machine.getName());
        long id = db.insert(MACHINE_TABLE, null, values);
        db.close();
        return id; // Returns the ID of the newly inserted machine, or -1 on error.
    }
    public Cursor getAllMachines() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(MACHINE_TABLE, null, null, null, null, null, null);
    }
    public int deleteMachine(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(MACHINE_TABLE, MACHINE_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }
}
