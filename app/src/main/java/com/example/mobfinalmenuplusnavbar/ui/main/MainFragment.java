package com.example.mobfinalmenuplusnavbar.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.db.Account;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    private ArrayList<Integer> cardRecyclerLogos = new ArrayList<>();
    private ArrayList<String> cardRecyclerBankNames = new ArrayList<>();
    private ArrayList<String> cardRecyclerCashes = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();

        initCards();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void initCards() {
        Log.d(TAG, "initCards");

        List<Account> accounts = Account.filter(null, null);
        cardRecyclerLogos.clear();
        cardRecyclerBankNames.clear();
        cardRecyclerCashes.clear();

        for (Account account: accounts){
            cardRecyclerLogos.add(account.getIcon());
            cardRecyclerBankNames.add(account.getName());
            cardRecyclerCashes.add(account.getAmount() + account.getCurrency());
        }

//        cardRecyclerLogos.add(R.drawable.jusan);
//        cardRecyclerBankNames.add("Jusan Bank");
//        cardRecyclerCashes.add("50 000 T");
//
//        cardRecyclerLogos.add(R.drawable.kaspi);
//        cardRecyclerBankNames.add("Kaspi Bank");
//        cardRecyclerCashes.add("10 000 T");
//
//        cardRecyclerLogos.add(R.drawable.eurasian);
//        cardRecyclerBankNames.add("Eurasian Bank");
//        cardRecyclerCashes.add("85 000 T");
//
//        cardRecyclerLogos.add(R.drawable.sberbank);
//        cardRecyclerBankNames.add("Sberbank");
//        cardRecyclerCashes.add("20 000 T");
//
//        cardRecyclerLogos.add(R.drawable.qazkom);
//        cardRecyclerBankNames.add("Qazkommertsbank");
//        cardRecyclerCashes.add("145 000 T");

        initRecyclerView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void initRecyclerView() {
        Log.d(TAG, "initRecyclerView");
        RecyclerView recyclerView = requireView().findViewById(R.id.cardRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerMainViewAdapter recyclerMainViewAdapter = new RecyclerMainViewAdapter(this.getContext(), cardRecyclerLogos, cardRecyclerBankNames, cardRecyclerCashes);
        recyclerView.setAdapter(recyclerMainViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }

}
