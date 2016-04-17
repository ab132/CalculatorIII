package com.aelken.android.calculator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Screen screen;
    private String _previousOp = "=";
    private Double _previousNum = .0;

    private static final String STATE_SCREEN = "screenValue";
    private static final String STATE_SCREEN_STATE = "screenState";
    private static final String STATE_MAIN_ACTIVITY_PREVIOUS_OP = "mainActivityPreviousOp";
    private static final String STATE_MAIN_ACTIVITY_PREVIOUS_NUM = "mainActivityPreviousNum";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = new Screen("0", (TextView) findViewById(R.id.textViewScreen));
        loadSavedInstanceState(savedInstanceState);
    }

    private void loadSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            screen.setValue(savedInstanceState.getString(STATE_SCREEN));
            screen.setAddNextSymbolAsNew(savedInstanceState.getBoolean(STATE_SCREEN_STATE));
            _previousOp = savedInstanceState.getString(STATE_MAIN_ACTIVITY_PREVIOUS_OP);
            _previousNum = savedInstanceState.getDouble(STATE_MAIN_ACTIVITY_PREVIOUS_NUM);
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(STATE_SCREEN, screen.toString());
        savedInstanceState.putBoolean(STATE_SCREEN_STATE, screen.getAddNextSymbolAsNew());
        savedInstanceState.putString(STATE_MAIN_ACTIVITY_PREVIOUS_OP, _previousOp);
        savedInstanceState.putDouble(STATE_MAIN_ACTIVITY_PREVIOUS_NUM, _previousNum);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void numberClicked(View v) {
	    String num = v.getTag().toString();
        screen.addSymbol(num);
    }
    public void operatorClicked(View v) {
        String op = v.getTag().toString();
        if (_previousOp.equals("=")) {
            _previousNum = screen.toDouble();
            screen.setValue(_previousNum);
        } else {
            if (!screen.getAddNextSymbolAsNew()) {
                Intent intent = new Intent();
                intent.setAction("com.example.varukonto.CALCULATE");
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                intent.putExtra("val1", _previousNum);
                intent.putExtra("val2", screen.toDouble());
                intent.putExtra("op", _previousOp);
                sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        _previousNum = Double.parseDouble(getResultData());
                        screen.setValue(_previousNum);
                    }
                }, null, Activity.RESULT_OK, null, null);
            }
        }
        _previousOp = op;
        screen.setAddNextSymbolAsNew(true);
    }

    public void delClicked(View v) {
        screen.delLastSymbol();
    }

    public void exitButtonClicked(View v) {
        finish();
    }

    public void ceButtonClicked(View v) {
        _previousOp = "=";
        _previousNum = .0;
        screen.clear();
    }

    public void signButtonClicked(View v) {
        screen.changeSign();
    }


}
