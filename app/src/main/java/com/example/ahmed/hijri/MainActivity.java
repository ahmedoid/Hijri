package com.example.ahmed.hijri;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Weeks;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "MainActivity";
    FloatingActionButton button;
    DatePickerDialog datePickerDialog;
    private android.widget.TextView geydate;
    private android.widget.TextView hijridate;
    private android.widget.TextView ramadandate;
    private android.support.v7.widget.GridLayout choicegrid;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.result = (TextView) findViewById(R.id.result);
        this.choicegrid = (GridLayout) findViewById(R.id.choice_grid);
        this.ramadandate = (TextView) findViewById(R.id.ramadan_date);
        this.hijridate = (TextView) findViewById(R.id.hijri_date);
        this.geydate = (TextView) findViewById(R.id.gey_date);
        button = (FloatingActionButton) findViewById(R.id.action);
        datePickerDialog = new DatePickerDialog(
                this, this, 2016, 6, 22);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        DateTime dtIslamic = DateTime.now().withChronology(IslamicChronology.getInstance());
        geydate.setText(DateTime.now().toString("E dd MMMM YYYY G"));
        hijridate.setText(dtIslamic.toString("E dd MMMM YYYY G"));
        int days = Days.daysBetween(new LocalDate(DateTime.now()), new LocalDate(new DateTime(2016, 8, 9, 0, 0))).getDays();
        ramadandate.setText(nextRamadan());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        DateTime date = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0, 0);
        DateTime dtIslamic = date.withChronology(IslamicChronology.getInstance());
        result.setText(dtIslamic.toString("E dd MMMM yyyy G"));
    }

    public String nextRamadan() {
        int days;
        Chronology hijri = IslamicChronology.getInstanceUTC();
        DateTime dtIslamic = DateTime.now().withChronology(IslamicChronology.getInstance());
        DateTime dtHijri;
        if (dtIslamic.getMonthOfYear() < 9) {
            dtHijri = new DateTime(dtIslamic.getYear(), 9, 1, 0, 0, hijri);
        } else {
            dtHijri = new DateTime(dtIslamic.getYear() + 1, 9, 1, 0, 0, hijri);
        }
        DateTime dtIso = new DateTime(dtHijri);
        days = Days.daysBetween(new LocalDate(DateTime.now()), new LocalDate(dtIso)).getDays();
        Weeks.weeksBetween(new LocalDate(DateTime.now()), new LocalDate(dtIso)).getWeeks();
        return String.valueOf(days);
    }
}
