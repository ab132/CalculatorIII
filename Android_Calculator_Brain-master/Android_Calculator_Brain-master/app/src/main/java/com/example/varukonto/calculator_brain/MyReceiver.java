package com.example.varukonto.calculator_brain;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by varukonto on 10.04.16.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isOrderedBroadcast()) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                UOW uow = new UOW(context);

                Double  num1 = extras.getDouble("val1");
                Double  num2 = extras.getDouble("val2");
                String op = extras.getString("op");
                Double answer = calculate(num1, num2, op);
                setResultCode(Activity.RESULT_OK);
                setResultData(answer.toString());
                uow.addNewRowToStats(op, num1, num2, answer);
            }

        }

    }

    public static Double calculate(Double d1, Double d2, String op) {

        BigDecimal answer = BigDecimal.ZERO;
        try {
            BigDecimal val1 = BigDecimal.valueOf(d1);
            BigDecimal val2 = BigDecimal.valueOf(d2);
            if (op.equals("+")) {
                answer = val1.add(val2);
            } else if (op.equals("-")) {
                answer = val1.subtract(val2);
            } else if (op.equals("*")) {
                answer = val1.multiply(val2);
            } else if (op.equals("/")) {
                answer = val1.divide(val2, 10, RoundingMode.HALF_UP);
            }
        } catch (ArithmeticException a) {
            return Double.NaN;
        } catch (NumberFormatException n) {
            return Double.NaN;
        }
        return answer.doubleValue();
    }
}
