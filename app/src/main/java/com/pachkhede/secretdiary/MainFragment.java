package com.pachkhede.secretdiary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private OnAddButtonClickListener listener;




    public interface OnAddButtonClickListener{
        void onAddButtonClicked();
    }




    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.diaryEntryRecylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DiaryDatabaseHelper db = new DiaryDatabaseHelper(getContext());

        DiaryEntryAdapter diaryEntryAdapter = new DiaryEntryAdapter(db.getAllDiaryEntries(), getContext());
        recyclerView.setAdapter(diaryEntryAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation()
        );

        Drawable dividerDrawable = getResources().getDrawable(R.drawable.diary_entry_divider);
        dividerItemDecoration.setDrawable(dividerDrawable);

        recyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton addButton = view.findViewById(R.id.add_entry_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAddButtonClicked();
                }
            }
        });



        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnAddButtonClickListener){
            listener = (OnAddButtonClickListener) context;

        } else {
            throw new RuntimeException(context.toString() + " must implement OnAddButtonClickListener and OnEntryClickListener");

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;

    }

}