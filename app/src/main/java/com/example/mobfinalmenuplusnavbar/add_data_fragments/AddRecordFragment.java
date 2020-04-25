package com.example.mobfinalmenuplusnavbar.add_data_fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobfinalmenuplusnavbar.Account;
import com.example.mobfinalmenuplusnavbar.Category;
import com.example.mobfinalmenuplusnavbar.DBValidateDataException;
import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.Record;
import com.example.mobfinalmenuplusnavbar.pojo.Icon;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class AddRecordFragment extends Fragment implements View.OnClickListener {

    Button btnDatePicker, btnTimePicker, btnSubmit;
    EditText txtDate, txtTime;
    TextInputLayout title, description, amount;
    SwitchMaterial mandatory;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Spinner spinnerAccounts;
    Spinner spinnerCategories;
    Context context;
    private String date;
    private String time;
    private ArrayList<String> categories;
    private ArrayList<String> accounts;
    private Record updateRecord;

    public AddRecordFragment(ArrayList<String> categories, ArrayList<String> accounts) {
        super();
        this.categories = categories;
        this.accounts = accounts;
    }

    public AddRecordFragment(){};

    public AddRecordFragment(Record updateRecord){
        this.updateRecord = updateRecord;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();

        View view = inflater.inflate(R.layout.add_records_fragment, container, false);

        spinnerAccounts = view.findViewById(R.id.account_dropdown);
        ArrayList<Icon> accountIcons = Icon.getAccountIcons();
        spinnerCategories = view.findViewById(R.id.category_dropdown);
        ArrayList<Icon> categoryIcons = Icon.getCategoryIcons();


        title = view.findViewById(R.id.title_field);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setError(null);
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
            description.getEditText().setText(updateRecord.getDescription());
            amount.getEditText().setText((int) updateRecord.getAmount());
            mandatory.setEnabled(updateRecord.getMandatory() == 1);
            spinnerCategories.setSelection(
                    Icon.getCategoryPosition(Category.get(updateRecord.getCategory_id()).getIcon()));
            spinnerAccounts.setSelection(
                    Icon.getAccountPosition(Account.get(updateRecord.getAccount_id()).getIcon()));
            String date = updateRecord.getDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            txtDate.setText(dateTime.getDayOfMonth() + "/" + dateTime.getMonth() + "/" +
                    dateTime.getYear());
            txtTime.setText(dateTime.getHour()+":" + dateTime.getMinute());
        }


        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        btnSubmit = view.findViewById(R.id.btn_submit);
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
                            String d = dayOfMonth < 10 ? "0" + dayOfMonth : ""+dayOfMonth;
                            String m = monthOfYear < 10 ? "0" + monthOfYear : ""+monthOfYear;

                            date = d + "/" + m + "/" + year;
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
                            String h = hourOfDay < 10 ? "0" + hourOfDay: "" + hourOfDay;
                            String m = minute < 10 ? "0" + minute: "" + minute;
                            time = h + ":" + m;
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
            if(title.getEditText().getText().length() == 0 ){
                title.setError("Title can't be empty");
                title.setErrorEnabled(true);
            }else{
                title.setError(null);
            }
            if(description.getEditText().getText().length() == 0 ){
                description.setError("Description can't be empty");
            }else{
                description.setError(null);
            }
            if(amount.getEditText().getText().length()  == 0 ){
                amount.setError("Amount can't be empty");
            }else{
                amount.setError(null);
            }



            if (title.getEditText().getText().length()!=0 && description.getEditText().getText().length()!=0
                    && amount.getEditText().getText().length()!=0 && date!=null && time!=null){
                String dateTime = date + " " + time;
                String t = title.getEditText().getText().toString();
                String d = description.getEditText().toString();
                int a = Integer.parseInt(amount.getEditText().getText().toString());
                int m = mandatory.isChecked() == true ? 1 : 0;

                if (updateRecord!=null){
                    updateRecord.setDate(dateTime);
                    updateRecord.setTitle(t);
                    updateRecord.setDescription(d);
                    updateRecord.setAmount(a);
                    updateRecord.setMandatory(m);
                    try {
                        updateRecord.save();
                    } catch (DBValidateDataException e) {
                        e.printStackTrace();
                    }
                }else{
                    Record record = new Record(t,a,d,m,);
                }




//                    addRecordToDatabase();
                Toast.makeText(context, "Record was added successfully",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
