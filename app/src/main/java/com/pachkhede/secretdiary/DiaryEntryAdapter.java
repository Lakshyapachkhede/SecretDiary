package com.pachkhede.secretdiary;



import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;


public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.ViewHolder> {
    private ArrayList<DiaryEntry> diaryEntries;
    private static Context context;


    public DiaryEntryAdapter(ArrayList<DiaryEntry> diaryEntries, Context context) {
        this.diaryEntries = diaryEntries;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_diary_entry_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaryEntry diaryEntry = diaryEntries.get(position);
        holder.txtYear.setText(diaryEntry.getYear());
        holder.txtMonth.setText(diaryEntry.getMonthAbbreviation());
        holder.txtDate.setText(diaryEntry.getDay());
        holder.txtTitle.setText(diaryEntry.getTitle());
        holder.txtContent.setText(diaryEntry.getContent());

        setMonthBackground(holder.sideBar, holder.txtMonth, Integer.parseInt(diaryEntry.getMonth()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int entryId = diaryEntry.getId();

                Bundle bundle = new Bundle();
                bundle.putInt("entry_id", entryId);

                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

                EntryUpdateReadFragment fragment = new EntryUpdateReadFragment();
                fragment.setArguments(bundle);

                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).addToBackStack(null).commit();

            }
        });





    }

    @Override
    public int getItemCount() {
        return diaryEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMonth, txtDate, txtYear, txtTitle, txtContent;
        private View sideBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sideBar = itemView.findViewById(R.id.entry_side_bar);
            txtMonth = itemView.findViewById(R.id.txt_month);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtYear= itemView.findViewById(R.id.txt_year);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtContent = itemView.findViewById(R.id.txt_content);




        }
    }


    private static void setMonthBackground(View sidebar, TextView monthView, int month) {
        int colorId;

        switch (month) {
            case 1:
                colorId = R.color.month01;
                break;
            case 2:
                colorId = R.color.month02;
                break;
            case 3:
                colorId = R.color.month03;
                break;
            case 4:
                colorId = R.color.month04;
                break;
            case 5:
                colorId = R.color.month05;
                break;
            case 6:
                colorId = R.color.month06;
                break;
            case 7:
                colorId = R.color.month07;
                break;
            case 8:
                colorId = R.color.month08;
                break;
            case 9:
                colorId = R.color.month09;
                break;
            case 10:
                colorId = R.color.month10;
                break;
            case 11:
                colorId = R.color.month11;
                break;
            case 12:
                colorId = R.color.month12;
                break;
            default:
                colorId = R.color.white; // Default color in case something goes wrong
        }

        // Set the background color
        sidebar.setBackgroundColor(ContextCompat.getColor(context, colorId));
        monthView.setBackgroundColor(ContextCompat.getColor(context, colorId));
    }





}
