package com.example.varukonto.calculator_brain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by varukonto on 10.04.16.
 */
public class DayStat implements IEntity {
    private long id;
    private long dayStamp;
    private long operandId;
    private long dayCounter;

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public String operand;

    DayStat() {

    }

    DayStat(long dayStamp, long operandId, long dayCounter) {
        setDayStamp(dayStamp);
        setOperandId(operandId);
        setDayCounter(dayCounter);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getDayStamp() {
        return dayStamp;
    }

    public void setDayStamp(long dayStamp) {
        this.dayStamp = dayStamp;
    }

    public long getOperandId() {
        return operandId;
    }

    public void setOperandId(long operandId) {
        this.operandId = operandId;
    }

    public long getDayCounter() {
        return dayCounter;
    }

    public void setDayCounter(long dayCounter) {
        this.dayCounter = dayCounter;
    }

    @Override
    public String toString() {
        return "Kuupäev: \"" + dayStampToDate(getDayStamp()) + "\" \nMärk: "+getOperand()+" päringuid: "+getDayCounter();
    }

    private String dayStampToDate(long l) {
        SimpleDateFormat inFormatter = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat outFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = inFormatter.parse(String.valueOf(l));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outFormatter.format(date);
    }

}
