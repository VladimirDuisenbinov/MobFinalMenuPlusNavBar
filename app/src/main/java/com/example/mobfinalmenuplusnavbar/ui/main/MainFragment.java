package com.example.mobfinalmenuplusnavbar.ui.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.add_data_fragments.AddRecordFragment;
import com.example.mobfinalmenuplusnavbar.db.Account;
import com.example.mobfinalmenuplusnavbar.db.DBHelper;
import com.example.mobfinalmenuplusnavbar.db.Record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    private ArrayList<Integer> cardRecyclerLogos = new ArrayList<>();
    private ArrayList<String> cardRecyclerBankNames = new ArrayList<>();
    private ArrayList<String> cardRecyclerCashes = new ArrayList<>();

    private ArrayList<Integer> lastRecordsRecyclerLogos = new ArrayList<>();
    private ArrayList<String> lastRecordsRecyclerCategories = new ArrayList<>();
    private ArrayList<String> lastRecordsRecyclerCashes = new ArrayList<>();

    private Button addRecordBtn;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        addRecordBtn = v.findViewById(R.id.add_record_btn);

        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRecordFragment addFragment = new AddRecordFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.main_container, addFragment, "AddRecFragment");
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();

        initCards();
        initLastRecords();
        initMonthExpenditures();
    }

    public void initMonthExpenditures(){
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        DateFormat df = new SimpleDateFormat(DBHelper.DATETIME_FORMAT, Locale.US);
        List<Pair<String, Double>> mandatory_res = Record.groupByCategories(df.format(c.getTime()), "Z", 1);
        List<Pair<String, Double>> nonmandatory_res = Record.groupByCategories(df.format(c.getTime()), "Z", 0);
        Log.e(" ggg", Record.filter("mandatory = 1", null).toString());
        StringBuilder res_builder = new StringBuilder();
        for (Pair<String, Double> item: mandatory_res){
            res_builder.append(item.first).append(": ").append(item.second).append("\n");
        }
        ((TextView)getView().findViewById(R.id.expenditureMandatoryCategoryAndValue)).setText(res_builder.toString());
        res_builder = new StringBuilder();
        for (Pair<String, Double> item: nonmandatory_res){
            res_builder.append(item.first).append(": ").append(item.second).append("\n");
        }
        ((TextView)getView().findViewById(R.id.expenditureNonMandatoryCategoryAndValue)).setText(res_builder.toString());
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

    public void initLastRecords() {
        lastRecordsRecyclerLogos.add(R.drawable.jusan);
        lastRecordsRecyclerCategories.add("Jusan Bank");
        lastRecordsRecyclerCashes.add("50 000 T");

        lastRecordsRecyclerLogos.add(R.drawable.jusan);
        lastRecordsRecyclerCategories.add("Jusan Bank");
        lastRecordsRecyclerCashes.add("50 000 T");

        initLastRecordsRecyclerView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void initRecyclerView() {
        Log.d(TAG, "initRecyclerView");
        RecyclerView recyclerView = requireView().findViewById(R.id.cardRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerMainViewAdapter recyclerMainViewAdapter = new RecyclerMainViewAdapter(
                this.getContext(),
                cardRecyclerLogos,
                cardRecyclerBankNames,
                cardRecyclerCashes
        );
        recyclerView.setAdapter(recyclerMainViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    public void initLastRecordsRecyclerView() {
        Log.d(TAG, "initLastRecordsRecyclerView");
        RecyclerView recyclerView = requireView().findViewById(R.id.lastRecordsRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerLastRecordsAdapter recyclerLastRecordsAdapter = new RecyclerLastRecordsAdapter(
                this.getContext(),
                lastRecordsRecyclerLogos,
                lastRecordsRecyclerCategories,
                lastRecordsRecyclerCashes
        );
        recyclerView.setAdapter(recyclerLastRecordsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }


}
