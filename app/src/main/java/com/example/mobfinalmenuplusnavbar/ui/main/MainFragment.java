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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobfinalmenuplusnavbar.R;
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

public class MainFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "MainFragment";

    private ArrayList<Integer> cardRecyclerLogos = new ArrayList<>();
    private ArrayList<String> cardRecyclerBankNames = new ArrayList<>();
    private ArrayList<String> cardRecyclerCashes = new ArrayList<>();

    private ArrayList<Integer> lastRecordsRecyclerLogos = new ArrayList<>();
    private ArrayList<String> lastRecordsRecyclerCategories = new ArrayList<>();
    private ArrayList<String> lastRecordsRecyclerCashes = new ArrayList<>();

    private Button startDateBtn,endDateBtn, filterBtn;
    private TextView startDate, endDate;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        startDateBtn = v.findViewById(R.id.start_date_btn);
        startDateBtn.setOnClickListener(this);
        startDate = v.findViewById(R.id.start_text);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        startDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.US ).format(date));
        endDateBtn = v.findViewById(R.id.end_date_btn);
        endDateBtn.setOnClickListener(this);
        endDate = v.findViewById(R.id.end_text);
        endDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date()));
        filterBtn = v.findViewById(R.id.filter_btn);

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startDate.getText().length()!= 0
                        && endDate.getText().length()!= 0 ){
                    String start_date = startDate.getText().toString();
                    String end_date = endDate.getText().toString();
                    List<Record> records = Record.filter(Record.DATE_COLUMN+" between ? and ?", new String[]{ start_date, end_date + "z" });
                    //update RecyclerView by notifiying adapter;
//                    TODO: show records in recycler view
                }
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

    @Override
    public void onClick(final View v) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
//                            String d = dayOfMonth < 10 ? "0" + dayOfMonth : ""+dayOfMonth;
//                            String m = monthOfYear < 10 ? "0" + monthOfYear : ""+monthOfYear;

//                            date = d + "/" + m + "/" + year;
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        DateFormat df = new SimpleDateFormat(DBHelper.DATE_FORMAT, Locale.US);
                        if (v == startDateBtn){
                            startDate.setText(df.format(newDate.getTime()));
                        }else if (v == endDateBtn){
                            endDate.setText(df.format(newDate.getTime()));
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
