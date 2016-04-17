package com.example.varukonto.calculator_brain;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by varukonto on 10.04.16.
 */
public class Operation implements IEntity {
    private long id;
    private long operandId;

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    private String operand;
    private double num1;
    private double num2;
    private double res;
    private long timestamp;

    Operation() {

    }


    Operation(long operandId, double num1, double num2, double res, long timestamp) {
        setOperandId(operandId);
        setNum1(num1);
        setNum2(num2);
        setRes(res);
        setTimestamp(timestamp);
    }
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getOperandId() {
        return operandId;
    }

    public void setOperandId(long operandId) {
        this.operandId = operandId;
    }

    public double getNum1() {
        return num1;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public double getNum2() {
        return num2;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public double getRes() {
        return res;
    }

    public void setRes(double res) {
        this.res = res;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return timeStampToDate(getTimestamp())+"\nTehe: "+getNum1()+getOperand()+getNum2()+"="+getRes();
    }

    private String timeStampToDate(long l) {
        Date date = new Date(l);
        SimpleDateFormat outFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return outFormatter.format(date);
    }
}
