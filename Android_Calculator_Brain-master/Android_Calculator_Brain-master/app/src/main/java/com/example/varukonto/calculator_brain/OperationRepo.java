package com.example.varukonto.calculator_brain;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by varukonto on 10.04.16.
 */
public class OperationRepo extends Repo<Operation> {
    private OperandRepo operandRepo;
    public OperationRepo(SQLiteDatabase database, String tableName, String[] allColumns, OperandRepo op) {
        super(database, tableName, allColumns);
        operandRepo = op;
    }

    @Override
    public Operation cursorToEntity(Cursor cursor) {
        Operation op = new Operation();
        op.setId(cursor.getLong(0));
        op.setOperandId(cursor.getLong(1));
        op.setNum1(testNull(cursor.getDouble(2)));
        op.setNum2(testNull(cursor.getDouble(3)));
        op.setRes(testNull(cursor.getDouble(4)));
        op.setTimestamp(cursor.getLong(5));
        Operand operand = operandRepo.getById(op.getOperandId());
        op.setOperand(operand.getOperand());
        return op;
    }

    @Override
    public ContentValues entityToContentValues(Operation op) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_OPERANDID, op.getOperandId());
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_NUM1, testNaN(op.getNum1()));
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_NUM2, testNaN(op.getNum2()));
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_RES,  testNaN(op.getRes()));
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_TIMESTAMP, op.getTimestamp());
        return contentValues;
    }
    private Double testNull(Double d) {
        if(d == null) {
            return Double.NaN;
        }
        return d;
    }

    private Double testNaN(double d) {
        if(Double.isNaN(d)) {
            return null;
        }
        return d;
    }

    public Cursor getForOperarandAndDatestamp(long operandId){
        List<DayStat> listOfEntity = new ArrayList<DayStat>();

        Cursor cursor = getDatabase().query(getTableName(),
                getAllColumns(), "operandId = " + operandId, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
}