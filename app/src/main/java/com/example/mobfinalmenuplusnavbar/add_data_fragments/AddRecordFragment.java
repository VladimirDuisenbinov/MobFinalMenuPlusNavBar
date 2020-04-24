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

import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.pojo.Record;
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

    public AddRecordFragment(ArrayList<String> categories, ArrayList<String> accounts) {
        super();
        this.categories = categories;
        this.accounts = accounts;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();

        View view = inflater.inflate(R.layout.add_records_fragment, container, false);

        spinnerAccounts = view.findViewById(R.id.account_dropdown);
        spinnerCategories = view.findViewById(R.id.category_dropdown);

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

        ArrayAdapter adapterAccounts = new ArrayAdapter(container.getContext(), android.R.layout.simple_list_item_1,
                accounts);
        ArrayAdapter adapterCategories = new ArrayAdapter(context, android.R.layout.simple_list_item_1,
                categories);
        adapterAccounts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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


        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        btnSubmit = view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Record record = createRecord()

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
                    String str = date + " " + time;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
                    String t = title.getEditText().getText().toString();
                    String d = description.getEditText().toString();
                    int a = Integer.parseInt(amount.getEditText().getText().toString());
                    boolean m = mandatory.isChecked();

                    Record record = new Record(t,d,a,m,dateTime);
//                    addRecordToDatabase();
                    Toast.makeText(context, "Record was added successfully",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

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

                            date = d + "-" + m + "-" + year;
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
    }
}
