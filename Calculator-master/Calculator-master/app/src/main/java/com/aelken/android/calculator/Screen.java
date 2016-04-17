package com.aelken.android.calculator;

import android.widget.TextView;

/**
 * Created by aelken on 16.03.16.
 */
public class Screen {
    private String value = "";
    private boolean addNextSymbolAsNew = false;
    private TextView _screen;

    public Screen(String v, TextView s) {
        value = v;
        _screen = s;
        refreshScreen();
    }


    public void setAddNextSymbolAsNew (boolean b) { addNextSymbolAsNew = b; }
    public boolean getAddNextSymbolAsNew () { return addNextSymbolAsNew; }
    public void setValue(double d) {
        setValue(String.valueOf(d));

    }
    public void setValue(String s) { value = s;
        if (value.endsWith(".0")) {
            value = value.substring(0, value.length()-2);
        }
        refreshScreen();
    }

    public void addSymbol(String s) {
        if (addNextSymbolAsNew) {
            value = "0";
            addNextSymbolAsNew = false;
        }
        if (value.equals("0") && !s.equals(".")) {  //remove leading zero
            value = s;
        } else if (!s.equals(".") || !value.contains(".")) { //cant be two decimal point
            value+=s;
        }
        refreshScreen();
    }


    public void changeSign() {
        if(value.equals("0")) return;
        if (value.charAt(0) == '-') {
            value = value.substring(1);
        } else {
            value = "-"+value;
        }
        refreshScreen();
    }

    public void delLastSymbol() {
        if (value.length() < 2 || value.equals("error")) {
            value = "0";
        } else {
            value = value.substring(0,value.length()-1);
        }
        refreshScreen();
    }

    public void clear() {
        value = "0";
        refreshScreen();
    }

    @Override
    public String toString() {
        return value;
    }

    public double toDouble() {
        return Double.parseDouble(value);
    }

    private void refreshScreen() {
        _screen.setText(value);
    }

}
