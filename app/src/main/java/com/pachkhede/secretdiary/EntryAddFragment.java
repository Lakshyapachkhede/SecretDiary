package com.pachkhede.secretdiary;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class EntryAddFragment extends Fragment {
    private TextView entryDate;
    private LinearLayout datePickerLayout;
    EditText edtTitle;
    PageEditText edtContent;


    public EntryAddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_add, container, false);

        entryDate = view.findViewById(R.id.diary_entry_date);
        datePickerLayout = view.findViewById(R.id.date_picker_layout);
        edtTitle = view.findViewById(R.id.title_entry);
        edtContent = view.findViewById(R.id.content_entry);

        SimpleDateFormat dbformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dbDate = dbformat.format(new Date());
        entryDate.setTag(dbDate);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMMM yyyy", Locale.getDefault());
        String formattedDate = sdf.format(new Date());
        entryDate.setText(formattedDate);



        datePickerLayout.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);



            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        (view, selectedYear, selectedMonth, selectedDay) -> {

                            Calendar selectedDate = Calendar.getInstance();
                            selectedDate.set(selectedYear, selectedMonth, selectedDay);

                            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMMM yyyy", Locale.getDefault());
                            String formattedDate = sdf.format(selectedDate.getTime());
                            entryDate.setText(formattedDate);


                            SimpleDateFormat dbformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            String dbDate = dbformat.format(selectedDate.getTime());
                            entryDate.setTag(dbDate);

                            Log.e("Db Date", dbDate.toString());
                            Log.e("Se Date", formattedDate.toString());





                        }, year, month, day
                );
                datePickerDialog.show();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();

        inflater.inflate(R.menu.toolbar_entry_add_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.save_entry){
            saveEntryToDB();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveEntryToDB() {
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        String date = (String) entryDate.getTag();

        if (title.isBlank() && content.isBlank()) {
            Toast.makeText(getContext(), "Either enter title or content", Toast.LENGTH_SHORT).show();
            return;
        }

        DiaryDatabaseHelper dbHelper = new DiaryDatabaseHelper(getContext());

        dbHelper.addDiaryEntry(title, content, date);
        Toast.makeText(getContext(), "Saved to db", Toast.LENGTH_SHORT).show();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.detachFragment();
        }

    }

}