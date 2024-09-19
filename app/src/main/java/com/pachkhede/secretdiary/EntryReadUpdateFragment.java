package com.pachkhede.secretdiary;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class EntryReadUpdateFragment extends Fragment {
    private TextView entryDate;
    private LinearLayout datePickerLayout;


    public EntryReadUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_read_update, container, false);

        entryDate = view.findViewById(R.id.diary_entry_date);
        datePickerLayout = view.findViewById(R.id.date_picker_layout);
        datePickerLayout.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);



            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        (view, selectedYear, selectedMonth, selectedDay) -> {

                            Calendar selectedDate = Calendar.getInstance();
                            selectedDate.set(selectedYear, selectedMonth, selectedDay);

                            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMMM yyyy", Locale.getDefault());
                            String formattedDate = sdf.format(selectedDate.getTime());

                            entryDate.setText(formattedDate);


                        }, year, month, day
                );
                datePickerDialog.show();
            }
        });

        return view;
    }





}