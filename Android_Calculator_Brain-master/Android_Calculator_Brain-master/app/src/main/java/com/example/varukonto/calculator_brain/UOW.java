package com.example.varukonto.calculator_brain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by varukonto on 10.04.16.
 */
public class UOW {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private final Context context;

    public OperandRepo operandRepo;
    public DayStatRepo dayStatRepo;
    public OperationRepo operationRepo;

    public UOW(Context context){

        this.context = context;

        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        operandRepo = new OperandRepo(database, dbHelper.TABLE_OPERANDS, dbHelper.ALLCOLUMNS_OPERANDS);
        dayStatRepo = new DayStatRepo(database, dbHelper.TABLE_DAYSTATS, dbHelper.ALLCOLUMNS_DAYSTATS, operandRepo);
        operationRepo = new OperationRepo(database, dbHelper.TABLE_OPERATIONS, dbHelper.ALLCOLUMNS_OPERATIONS, operandRepo);
}

    public void DropCreateDatabase(){
        dbHelper.dropCreateDatabase(database);
    }


    public void addNewRowToStats(String operand, Double num1, Double num2, Double answer) {
        Operand operandObj = operandRepo.getByOperand(operand);
        operandObj.setLifetimeCounter(operandObj.getLifetimeCounter()+1);
        operandRepo.update(operandObj);

        dayStatRepo.addOneToDayCounter(operandObj.getId());


        Operation operation = new Operation();
        operation.setOperandId(operandObj.getId());
        operation.setNum1(num1);
        operation.setNum2(num2);
        operation.setRes(answer);
        operation.setTimestamp( System.currentTimeMillis());
        operationRepo.add(operation);


    }
}
