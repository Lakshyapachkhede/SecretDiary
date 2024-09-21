package com.pachkhede.secretdiary;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class EntryUpdateReadFragment extends Fragment {
    private DiaryEntry diaryEntry;
    private TextView entryDate;
    private LinearLayout datePickerLayout;
    EditText edtTitle;
    PageEditText edtContent;


    public EntryUpdateReadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            int entryId = getArguments().getInt("entry_id");
            DiaryDatabaseHelper db = new DiaryDatabaseHelper(getContext());
            diaryEntry = db.getDiaryEntry(entryId);

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_add, container, false);

        entryDate = view.findViewById(R.id.diary_entry_date);
        datePickerLayout = view.findViewById(R.id.date_picker_layout);
        edtTitle = view.findViewById(R.id.title_entry);
        edtContent = view.findViewById(R.id.content_entry);

        edtTitle.setText(diaryEntry.getTitle());
        edtContent.setText(diaryEntry.getContent());

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMMM yyyy", Locale.getDefault());
        String formattedDate = sdf.format(diaryEntry.getDate());
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


                            diaryEntry.setDate(selectedDate.getTime());


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

        inflater.inflate(R.menu.toolbar_entry_update_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.save_entry){
            updateEntry();
            return true;
        }

        if (itemId == R.id.delete_entry){
            deleteEntry();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateEntry() {
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();


        if (title.isBlank() && content.isBlank()) {
            Toast.makeText(getContext(), "Either enter title or content", Toast.LENGTH_SHORT).show();
            return;
        }

        DiaryDatabaseHelper dbHelper = new DiaryDatabaseHelper(getContext());

        diaryEntry.setTitle(title);
        diaryEntry.setContent(content);


        dbHelper.updateDiaryEntry(diaryEntry);
        Toast.makeText(getContext(), "Updated entry", Toast.LENGTH_SHORT).show();
    }

    private void deleteEntry() {

        new AlertDialog.Builder(getContext())
                .setTitle("Delete Entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    DiaryDatabaseHelper dbHelper = new DiaryDatabaseHelper(getContext());
                    dbHelper.deleteDiaryEntry(diaryEntry.getId());
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null) {
                        mainActivity.detachFragment();
                    }
                }

                ).setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();



    }



}