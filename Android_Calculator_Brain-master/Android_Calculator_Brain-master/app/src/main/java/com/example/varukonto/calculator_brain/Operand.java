package com.example.varukonto.calculator_brain;

/**
 * Created by varukonto on 10.04.16.
 */
public class Operand implements IEntity {
    private long id;
    private String operand;
    private long lifetimeCounter;

    Operand() {

    }

    Operand(String op, long lifetimeCounter) {
        setOperand(op);
        setLifetimeCounter(lifetimeCounter);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public long getLifetimeCounter() {
        return lifetimeCounter;
    }

    public void setLifetimeCounter(long lifetimeCounter) {
        this.lifetimeCounter = lifetimeCounter;
    }

    @Override
    public String toString() {
        return "Tehet \"" + getOperand() + "\" päritud "+getLifetimeCounter()+ " korda";
    }
}
