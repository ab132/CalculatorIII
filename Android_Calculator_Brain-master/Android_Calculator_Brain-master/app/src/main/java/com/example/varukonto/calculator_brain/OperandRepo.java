package com.example.varukonto.calculator_brain;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by varukonto on 10.04.16.
 */
public class OperandRepo extends Repo<Operand> {
    public OperandRepo(SQLiteDatabase database, String tableName, String[] allColumns) {
        super(database, tableName, allColumns);
    }

    @Override
    public Operand cursorToEntity(Cursor cursor) {
        Operand op = new Operand();
        op.setId(cursor.getLong(0));
        op.setOperand(cursor.getString(1));
        op.setLifetimeCounter(cursor.getLong(2));
        return op;
    }

    @Override
    public ContentValues entityToContentValues(Operand op) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.COLUMN_OPERANDS_OPERAND, op.getOperand());
        contentValues.put(MySQLiteHelper.COLUMN_OPERANDS_LIFEIMECOUNTER, op.getLifetimeCounter());
        return contentValues;
    }

    public Operand getByOperand(String op) {
        Operand newEntity;
        Cursor cursor = database.query(tableName,
                allColumns, allColumns[1] + " = '" + op +"'",
                null, null, null, null);

        if (cursor == null || cursor.getCount()<1) {
            //lisame
            Operand opObj = new Operand();
            opObj.setOperand(op);
            opObj.setLifetimeCounter(0);
            newEntity = add(opObj);
        } else {
            cursor.moveToFirst();
            newEntity = cursorToEntity(cursor);
        }

        return newEntity;
    }


}
