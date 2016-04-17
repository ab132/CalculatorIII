package com.example.varukonto.calculator_brain;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by varukonto on 10.04.16.
 */
public class DayStatRepo extends Repo<DayStat> {
    OperandRepo operandRepo;
    public DayStatRepo(SQLiteDatabase database, String tableName, String[] allColumns, OperandRepo operandRepo) {
        super(database, tableName, allColumns);
        this.operandRepo = operandRepo;
    }

    @Override
    public DayStat cursorToEntity(Cursor cursor) {
        DayStat stat = new DayStat();
        stat.setId(cursor.getLong(0));
        stat.setDayStamp(cursor.getLong(1));
        stat.setOperandId(cursor.getLong(2));
        stat.setDayCounter(cursor.getLong(3));
        Operand operand = operandRepo.getById(stat.getOperandId());
        stat.setOperand(operand.getOperand());
        return stat;
    }

    @Override
    public ContentValues entityToContentValues(DayStat stat) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.COLUMN_DAYSTATS_DAYSTAMP, stat.getDayStamp());
        contentValues.put(MySQLiteHelper.COLUMN_DAYSTATS_OPERANDID, stat.getOperandId());
        contentValues.put(MySQLiteHelper.COLUMN_DAYSTATS_DAYCOUNTER, stat.getDayCounter());
        return contentValues;
    }

    private long getDateStamp() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return Long.parseLong(sdf.format(date));
    }

    public Cursor getForOperand(long operandId){
        Cursor cursor = getDatabase().query(getTableName(),
                getAllColumns(), "operandId = " + operandId, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public DayStat addOneToDayCounter(long id) {
        DayStat newEntity;
        long dayStamp=getDateStamp();
        Cursor cursor = database.query(tableName,
                allColumns, allColumns[2] + " = "+ id +" and " + allColumns[1] +" = "+dayStamp ,
                null, null, null, null);

        if (cursor == null || cursor.getCount()<1) {
            //lisame
            DayStat stat = new DayStat();
            stat.setDayCounter(1);
            stat.setOperandId(id);
            stat.setDayStamp(dayStamp);
            newEntity = add(stat);
        } else {
            cursor.moveToFirst();
            newEntity = cursorToEntity(cursor);
            newEntity.setDayCounter(newEntity.getDayCounter() + 1);
            update(newEntity);
        }

        return newEntity;
    }
}
