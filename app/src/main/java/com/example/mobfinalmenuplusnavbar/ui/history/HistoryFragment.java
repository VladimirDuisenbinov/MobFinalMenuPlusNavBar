package com.example.mobfinalmenuplusnavbar.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobfinalmenuplusnavbar.R;
import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private static final String TAG = "HistoryFragment";

    private ArrayList<Integer> cardRecyclerLogos = new ArrayList<>();
    private ArrayList<String> cardRecyclerBankNames = new ArrayList<>();
    private ArrayList<String> cardRecyclerCashes = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    public void onStart() {
        super.onStart();

        initCards();
    }

    public void initCards() {
        Log.d(TAG, "initCards");

        cardRecyclerLogos.add(R.drawable.record_base_icon);
        cardRecyclerCashes.add("+80 000 T");
        cardRecyclerBankNames.add("Salary");

        cardRecyclerLogos.add(R.drawable.record_base_icon);
        cardRecyclerCashes.add("-10 000 T");
        cardRecyclerBankNames.add("Sport");

        cardRecyclerLogos.add(R.drawable.record_base_icon);
        cardRecyclerCashes.add("+20 000 T");
        cardRecyclerBankNames.add("Work");

        cardRecyclerLogos.add(R.drawable.record_base_icon);
        cardRecyclerCashes.add("-145 000 T");
        cardRecyclerBankNames.add("VISA");

        cardRecyclerLogos.add(R.drawable.record_base_icon);
        cardRecyclerCashes.add("+10 000 T");
        cardRecyclerBankNames.add("Sell");

        cardRecyclerLogos.add(R.drawable.record_base_icon);
        cardRecyclerCashes.add("-85 000 T");
        cardRecyclerBankNames.add("Food");

        initRecyclerView();
    }

    public void initRecyclerView() {
        Log.d(TAG, "initRecyclerView");
        RecyclerView recyclerView = requireView().findViewById(R.id.lastRecordsRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerHistoryViewAdapter recyclerHistoryViewAdapter = new RecyclerHistoryViewAdapter(this.getContext(), cardRecyclerLogos, cardRecyclerBankNames, cardRecyclerCashes);
        recyclerView.setAdapter(recyclerHistoryViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }
}
