package com.example.varukonto.calculator_brain;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
/**
 * Created by varukonto on 11.04.16.
 */
public class OperandsAdapter  extends CursorAdapter{
    private final LayoutInflater layoutInflater;
    private UOW uow;
    private ViewGroup parentViewGroup;

    public OperandsAdapter(Context context, Cursor c, UOW uow) {
        super(context, c, 0);
        layoutInflater = LayoutInflater.from(context);
        this.uow = uow;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view=layoutInflater.inflate(R.layout.day_stats, parent, false);
        parentViewGroup = parent;
        return view;
    }


    // this can be called several times by the system!!!
    // first pass - initial draw, get measurements
    // second pass - final draw
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewName =(TextView) view.findViewById(R.id.name);

        Operand op = uow.operandRepo.cursorToEntity(cursor);
        textViewName.setText(op.toString());
        //displayDayStats(view, context, op);
    }


    private void displayDayStats(View view, Context context, Operand operand) {
        // get the contactsListView
        //LinearLayout operandsListView = (LinearLayout) view.findViewById(R.id.dayStatsListView);

        // if this gets called multiple times, first clean all up
        // otherwise you will add same childs several times
        //operandsListView.removeAllViews();

        ListView listView = (ListView) view.findViewById(R.id.dayStatsListView);
        DayStatsAdapter adapter = new DayStatsAdapter(context, uow.dayStatRepo.getForOperand(operand.getId()), uow);
        listView.setAdapter(adapter);



    }
}
