package com.example.varukonto.calculator_brain;

/**
 * Created by varukonto on 10.04.16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by akaver on 03/04/16.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = MySQLiteHelper.class.getName();
    private final Context context;

    public static final String TABLE_OPERANDS = "operands";
    public static final String COLUMN_OPERANDS_ID = "_id";
    public static final String COLUMN_OPERANDS_OPERAND = "operand";
    public static final String COLUMN_OPERANDS_LIFEIMECOUNTER = "lifetimeCounter";
    public static final String[] ALLCOLUMNS_OPERANDS = {COLUMN_OPERANDS_ID, COLUMN_OPERANDS_OPERAND,
            COLUMN_OPERANDS_LIFEIMECOUNTER};

    public static final String TABLE_DAYSTATS = "dayStats";
    public static final String COLUMN_DAYSTATS_ID = "_id";
    public static final String COLUMN_DAYSTATS_DAYSTAMP = "dayStamp";
    public static final String COLUMN_DAYSTATS_OPERANDID = "operandId";
    public static final String COLUMN_DAYSTATS_DAYCOUNTER = "dayCounter";
    public static final String[] ALLCOLUMNS_DAYSTATS = {COLUMN_DAYSTATS_ID, COLUMN_DAYSTATS_DAYSTAMP,
            COLUMN_DAYSTATS_OPERANDID,COLUMN_DAYSTATS_DAYCOUNTER };

    public static final String TABLE_OPERATIONS = "operations";
    public static final String COLUMN_OPERATIONS_ID = "_id";
    public static final String COLUMN_OPERATIONS_OPERANDID = "operandId";
    public static final String COLUMN_OPERATIONS_NUM1 = "num1";
    public static final String COLUMN_OPERATIONS_NUM2 = "num2";
    public static final String COLUMN_OPERATIONS_RES = "res";
    public static final String COLUMN_OPERATIONS_TIMESTAMP = "timestamp";
    public static final String[] ALLCOLUMNS_OPERATIONS = {COLUMN_OPERATIONS_ID, COLUMN_OPERATIONS_OPERANDID,
            COLUMN_OPERATIONS_NUM1,COLUMN_OPERATIONS_NUM2, COLUMN_OPERATIONS_RES, COLUMN_OPERATIONS_TIMESTAMP };

    private static final String DATABASE_NAME = "calc_brain.db";
    private static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    private static final String DATABASE_CREATE_OPERANDS = "create table "
            + TABLE_OPERANDS + "("
            + COLUMN_OPERANDS_ID + " integer primary key autoincrement, "
            + COLUMN_OPERANDS_OPERAND + " text not null, "
            + COLUMN_OPERANDS_LIFEIMECOUNTER + " integer not null);";

    private static final String DATABASE_CREATE_DAYSTATS = "create table "
            + TABLE_DAYSTATS + "("
            + COLUMN_DAYSTATS_ID + " integer primary key autoincrement, "
            + COLUMN_DAYSTATS_DAYSTAMP + " integer not null, "
            + COLUMN_DAYSTATS_OPERANDID + " integer not null, "
            + COLUMN_DAYSTATS_DAYCOUNTER + " integer not null); ";

    private static final String DATABASE_CREATE_OPERATIONS = "create table "
            + TABLE_OPERATIONS + "("
            + COLUMN_OPERATIONS_ID + " integer primary key autoincrement, "
            + COLUMN_OPERATIONS_OPERANDID + " text not null, "
            + COLUMN_OPERATIONS_NUM1 + " real null, "
            + COLUMN_OPERATIONS_NUM2 + " real null, "
            + COLUMN_OPERATIONS_RES + " real null, "
            + COLUMN_OPERATIONS_TIMESTAMP + " integer not null DEFAULT CURRENT_TIMESTAMP);";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void dropCreateDatabase(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERANDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAYSTATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATIONS);

        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_OPERANDS);
        db.execSQL(DATABASE_CREATE_DAYSTATS);
        db.execSQL(DATABASE_CREATE_OPERATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERANDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAYSTATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATIONS);
        onCreate(db);
    }
}
