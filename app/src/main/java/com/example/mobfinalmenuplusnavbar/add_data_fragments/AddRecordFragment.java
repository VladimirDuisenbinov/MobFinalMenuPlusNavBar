package com.example.mobfinalmenuplusnavbar.add_data_fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.mobfinalmenuplusnavbar.R;

import com.example.mobfinalmenuplusnavbar.db.Account;
import com.example.mobfinalmenuplusnavbar.db.Category;
import com.example.mobfinalmenuplusnavbar.db.DBHelper;
import com.example.mobfinalmenuplusnavbar.db.DBValidateDataException;
import com.example.mobfinalmenuplusnavbar.db.Record;
import com.example.mobfinalmenuplusnavbar.db.Icon;
import com.example.mobfinalmenuplusnavbar.ui.history.HistoryFragment;
import com.example.mobfinalmenuplusnavbar.ui.main.MainFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddRecordFragment extends Fragment implements View.OnClickListener {

    Button btnDatePicker, btnTimePicker, btnSubmit;
    EditText txtDate, txtTime;
    TextInputLayout title, description, amount, subject;
    SwitchMaterial mandatory;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Spinner spinnerAccounts;
    Spinner spinnerCategories;
    Context context;
    private String date;
    private String time;
    private List<Category> categories;
    private List<Account> accounts;
    private Record updateRecord;



    public AddRecordFragment(){};

    public AddRecordFragment(Record updateRecord){
        this.updateRecord = updateRecord;
        Log.d("AddRecord:", "UpdateRecordFragment: ");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();

        View view = inflater.inflate(R.layout.add_records_fragment, container, false);

        spinnerAccounts = view.findViewById(R.id.account_dropdown);
        spinnerCategories = view.findViewById(R.id.category_dropdown);

        categories = Category.filter(null, null);
        accounts = Account.filter(null, null);

        ArrayList<Icon> accountIcons =
                Icon.convertToAccountIcons(accounts);

        ArrayList<Icon> categoryIcons =
                Icon.convertToCategoryIcons(categories);


        title = view.findViewById(R.id.title_field);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setError(null);
            }
        });

        subject = view.findViewById(R.id.subject_field);
        subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject.setError(null);
            }
        });
        description = view.findViewById(R.id.description_field);
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setError(null);
            }
        });
        amount = view.findViewById(R.id.amount_field);
        amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setError(null);
            }
        });

        mandatory = view.findViewById(R.id.switch_mandatory);

        IconsAdapter adapterAccounts = new IconsAdapter(context, accountIcons);
        IconsAdapter adapterCategories = new IconsAdapter(context, categoryIcons);

        spinnerAccounts.setAdapter(adapterAccounts);
        spinnerCategories.setAdapter(adapterCategories);

        btnDatePicker=(Button)view.findViewById(R.id.btn_date);
        btnTimePicker=(Button)view.findViewById(R.id.btn_time);
        btnSubmit = view.findViewById(R.id.btn_submit);
        txtDate=(EditText)view.findViewById(R.id.in_date);
        txtDate.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (txtDate.getText().toString().length() <= 0) {
                    txtDate.setError("Date can't be empty");
                } else {
                    txtDate.setError(null);
                }
            }
        });
        txtTime=(EditText)view.findViewById(R.id.in_time);
        txtTime.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s)  {
                if (txtTime.getText().toString().length() <= 0) {
                    txtTime.setError("Time can't be empty");
                } else {
                    txtTime.setError(null);
                }
            }
        });

        if (updateRecord!=null){
            title.getEditText().setText(updateRecord.getTitle());
            subject.getEditText().setText(updateRecord.getSubject());
            description.getEditText().setText(updateRecord.getDescription());
            amount.getEditText().setText("" +updateRecord.getAmount());
            mandatory.setEnabled(updateRecord.getMandatory() == 1);
            btnSubmit.setText("update record");
            int accIndex = 0;

            for (Account account: accounts){
                if(updateRecord.getAccount_id()== account.getId()){
                    break;
                }
                accIndex++;
            }

            int catIndex = 0;

            for (Category category: categories){
                if(updateRecord.getCategory_id()== category.getId()){
                    break;
                }
                catIndex++;
            }

            spinnerCategories.setSelection(catIndex);
            spinnerAccounts.setSelection(accIndex);
            String dt = updateRecord.getDate();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//            LocalDateTime dateTime = LocalDateTime.parse(dt, formatter);
            date = dt.substring(0,10);
            txtDate.setText(date);
            time = dt.substring(11);
            txtTime.setText(time);
        }


        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);


        return view;
    }



    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
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
                            date = df.format(newDate.getTime());
                            txtDate.setText(date);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
//                            String h = hourOfDay < 10 ? "0" + hourOfDay: "" + hourOfDay;
//                            String m = minute < 10 ? "0" + minute: "" + minute;
//                            time = h + ":" + m;
                            Calendar newDate = Calendar.getInstance();
                            newDate.set (0, 0, 0, hourOfDay, minute);
                            DateFormat df = new SimpleDateFormat(DBHelper.TIME_FORMAT, Locale.US);
                            time = df.format(newDate.getTime());
                            txtTime.setText(time);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }

        if (v == btnSubmit){
            if (date == null){
                txtDate.setError("Choose Date");
            }
            if (time == null){
                txtTime.setError("Choose Time");
            }

            if(amount.getEditText().getText().length()  == 0 ){
                amount.setError("Amount can't be empty");
            }else{
                amount.setError(null);
            }



            if (amount.getEditText().getText().length()!=0 && date!=null && time!=null){
                String dateTime = date + " " + time;
                String t = title.getEditText().getText().toString();
                String s = subject.getEditText().getText().toString();
                String d = description.getEditText().getText().toString();
                double a = Double.parseDouble(amount.getEditText().getText().toString());
                int m = mandatory.isChecked() == true ? 1 : 0;

                FragmentTransaction transaction = getFragmentManager().beginTransaction();




                if (updateRecord!=null){
                    updateRecord.setDate(dateTime);
                    updateRecord.setTitle(t);
                    updateRecord.setSubject(s);
                    updateRecord.setDescription(d);
                    updateRecord.setAmount(a);
                    updateRecord.setMandatory(m);
                    try {
                        updateRecord.save();

                        int index = getFragmentManager().getBackStackEntryCount() - 1;
                        FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
                        String tag = backEntry.getName();
                        Log.d("tag", "fragment tag: " + tag);
                        if (tag.equals("HistRec")){
                            HistoryFragment historyFragment = new HistoryFragment();
                            transaction.replace(R.id.history_container,historyFragment, "HistoryFragment");
                        }else if (tag.equals("LastRec")){
                            MainFragment mainFragment = new MainFragment();
                            transaction.replace(R.id.main_container,mainFragment, "MainFragment");
                        }


                        transaction.commit();
                        Toast.makeText(context, "Record was updated successfully",
                                Toast.LENGTH_SHORT).show();
                    } catch (DBValidateDataException e) {
                        e.printStackTrace();
                    }
                }else{
                    long acc_id = accounts.get(spinnerAccounts.getSelectedItemPosition()).getId();
                    long cat_id = categories.get(spinnerCategories.getSelectedItemPosition()).getId();
                    Record record = new Record(t,a,d,cat_id,acc_id,m,s,dateTime);
                    try {
                        record.save();
                        MainFragment mainFragment = new MainFragment();
                        transaction.replace(R.id.main_container,mainFragment, "MainFragment");
                        transaction.commit();
                        Toast.makeText(context, "Record was added successfully",
                                Toast.LENGTH_SHORT).show();
                    } catch (DBValidateDataException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }
}
