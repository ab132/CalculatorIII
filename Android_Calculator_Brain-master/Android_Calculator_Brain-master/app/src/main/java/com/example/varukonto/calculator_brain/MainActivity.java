package com.example.varukonto.calculator_brain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private TextView textView;
    private UOW uow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.list);
        textView = (TextView) findViewById(R.id.text);
        uow = new UOW(getApplicationContext());
        displayOperatorsListView();
    }


    private void displayOperandsListView() {
        String s = "Statistika tehete järgi";
        textView.setText(s);
        OperandsAdapter adapter = new OperandsAdapter(this, uow.operandRepo.getCursorAll(), uow);
        listView.setAdapter(adapter);
    }

    private void displayDayStatsListView() {
        String s = "Statistika päevade kaupa";
        textView.setText(s);
        DayStatsAdapter adapter = new DayStatsAdapter(this, uow.dayStatRepo.getCursorAll(), uow);
        listView.setAdapter(adapter);
    }

    private void displayOperatorsListView() {
        String s = "Kõik päringud";
        textView.setText(s);
        OperationsAdapter adapter = new OperationsAdapter(this, uow.operationRepo.getCursorAll(), uow);
        listView.setAdapter(adapter);
    }

    public void refreshClicked() {
        refreshActivity();
    }

    private void refreshActivity() {
        //TODO ära muuta
        displayOperandsListView();
        displayDayStatsListView();
        displayOperatorsListView();
    }

    public void deleteClicked() {
        new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Kas soovid tõest kogu andmebaasi tühjaks kustutada?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        uow.DropCreateDatabase();
                        Toast.makeText(MainActivity.this, "Tehtud", Toast.LENGTH_SHORT).show();
                        refreshActivity();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            deleteClicked();
        } else if (id == R.id.action_refresh) {
            refreshClicked();
        } else if (id == R.id.action_show_history) {
            displayOperatorsListView();
        } else if (id == R.id.action_show_daysstats) {
            displayDayStatsListView();
        } else if (id == R.id.action_show_operandstats) {
            displayOperandsListView();
        }
        return super.onOptionsItemSelected(item);
    }
}
