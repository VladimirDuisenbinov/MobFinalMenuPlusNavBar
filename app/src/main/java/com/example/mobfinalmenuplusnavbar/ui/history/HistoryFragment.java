package com.example.mobfinalmenuplusnavbar.ui.history;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.add_data_fragments.AddRecordFragment;
import com.example.mobfinalmenuplusnavbar.db.Account;
import com.example.mobfinalmenuplusnavbar.db.Category;
import com.example.mobfinalmenuplusnavbar.db.DBHelper;
import com.example.mobfinalmenuplusnavbar.db.Record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "HistoryFragment";

    private ArrayList<Long> cardRecyclerIds = new ArrayList<>();
    private ArrayList<Integer> cardRecyclerLogos = new ArrayList<>();
    private ArrayList<String> cardRecyclerBankNames = new ArrayList<>();
    private ArrayList<String> cardRecyclerCashes = new ArrayList<>();
    private ArrayList<String> cardRecyclerDates = new ArrayList<>();

    private Button startDateBtn,endDateBtn, filterBtn;
    private TextView startDate, endDate;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
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
                    initCards();
                    //update RecyclerView by notifiying adapter;
                    initRecyclerView();
                }
            }
        });






        return v;
    }

    public void onStart() {
        super.onStart();

        initCards();
    }

    public void initCards() {
        Log.d(TAG, "initCards");

        String start_date = startDate.getText().toString();
        String end_date = endDate.getText().toString();
        List<Record> records = Record.filter(Record.DATE_COLUMN+" between ? and ?", new String[]{ start_date, end_date + "z" });

        cardRecyclerIds = new ArrayList<>();
        cardRecyclerLogos = new ArrayList<>();
        cardRecyclerBankNames = new ArrayList<>();
        cardRecyclerCashes = new ArrayList<>();
        cardRecyclerDates = new ArrayList<>();

        for (Record record: records){
            cardRecyclerIds.add(record.getId());
            cardRecyclerLogos.add(Category.get(record.getCategory_id()).getIcon());
            cardRecyclerCashes.add(record.getAmount() >= 0 ? "+" + record.getAmount() : "" + record.getAmount());
            cardRecyclerBankNames.add(Account.get(record.getAccount_id()).getName());
            cardRecyclerDates.add(record.getDate());
        }

        // cardRecyclerIds.add(id);
//        cardRecyclerLogos.add(R.drawable.record_base_icon);
//        cardRecyclerCashes.add("-10 000 T");
//        cardRecyclerBankNames.add("Sport");
//        cardRecyclerDates.add("01.04.2019");
//
//        cardRecyclerLogos.add(R.drawable.record_base_icon);
//        cardRecyclerCashes.add("+20 000 T");
//        cardRecyclerBankNames.add("Work");
//        cardRecyclerDates.add("03.01.2020");
//
//        cardRecyclerLogos.add(R.drawable.record_base_icon);
//        cardRecyclerCashes.add("-145 000 T");
//        cardRecyclerBankNames.add("VISA");
//        cardRecyclerDates.add("18.03.2019");
//
//        cardRecyclerLogos.add(R.drawable.record_base_icon);
//        cardRecyclerCashes.add("+10 000 T");
//        cardRecyclerBankNames.add("Sell");
//        cardRecyclerDates.add("03.01.2020");
//
//        cardRecyclerLogos.add(R.drawable.record_base_icon);
//        cardRecyclerCashes.add("-85 000 T");
//        cardRecyclerBankNames.add("Food");
//        cardRecyclerDates.add("18.03.2019");

        initRecyclerView();
    }

    public void initRecyclerView() {
        Log.d(TAG, "initRecyclerView");
        RecyclerView recyclerView = requireView().findViewById(R.id.lastRecordsRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerHistoryViewAdapter recyclerHistoryViewAdapter = new RecyclerHistoryViewAdapter(
                this.getContext(), cardRecyclerIds,
                cardRecyclerLogos, cardRecyclerBankNames,
                cardRecyclerCashes, cardRecyclerDates
        );
        recyclerView.setAdapter(recyclerHistoryViewAdapter);
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
