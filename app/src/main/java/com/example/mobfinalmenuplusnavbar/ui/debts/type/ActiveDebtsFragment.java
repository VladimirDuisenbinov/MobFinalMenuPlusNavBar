package com.example.mobfinalmenuplusnavbar.ui.debts.type;

import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.db.Debt;
import com.example.mobfinalmenuplusnavbar.db.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class ActiveDebtsFragment extends Fragment {


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DebtsAdapter debtsAdapter;

    public ActiveDebtsFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_active_debts, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.actDebtsRecView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        debtsAdapter = new DebtsAdapter(getDebtsList());
        recyclerView.setAdapter(debtsAdapter);
        return view;
    }

    public void initRecyclerView(){
        recyclerView = (RecyclerView) requireView().findViewById(R.id.actDebtsRecView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        debtsAdapter = new DebtsAdapter(getDebtsList());
        recyclerView.setAdapter(debtsAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
    }

    private List<Debt> getDebtsList() {
        List<Pair<String, Double>> rawDebts = Record.getDebts();
        final List<Debt> debts = new ArrayList<>();
        rawDebts.forEach(new Consumer<Pair<String, Double>>() {
            @Override
            public void accept(Pair<String, Double> t) {
                debts.add(new Debt(t.first, t.second));
            }
        });
        /*debts.add(new Debt("Праоу", 520.0));
        debts.add(new Debt("Ерасыл", -20000.0));*/
        return debts;
    }
}
