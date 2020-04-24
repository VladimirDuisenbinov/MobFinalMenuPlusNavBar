package com.example.mobfinalmenuplusnavbar.add_data_fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.pojo.Account;
import com.example.mobfinalmenuplusnavbar.pojo.Icon;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddAccountFragment extends Fragment implements View.OnClickListener {
    TextInputLayout name;
    TextInputLayout amount;
    Spinner spinnerCurrency;
    Spinner spinnerIcon;
    Button btnSubmit;
    Context context;
    ArrayList<Icon> icons;

    public AddAccountFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.add_account_fragment, container, false);
        name = view.findViewById(R.id.title_field);
        amount = view.findViewById(R.id.amount_field);
        spinnerCurrency = view.findViewById(R.id.currency_dropdown);
        spinnerIcon = view.findViewById(R.id.icon_dropdown);


        btnSubmit = view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        String[] currencies = new String[]{"$", "€", "₸", "₽"};

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1,
                currencies);

        spinnerCurrency.setAdapter(adapter);
        ArrayList<Icon> icons = Icon.getAccountIcons();
        IconsAdapter iconsAdapter = new IconsAdapter(context,icons);
        spinnerIcon.setAdapter(iconsAdapter);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(name.getEditText().getText().length() == 0 ){
            name.setError("Title can't be empty");
            name.setErrorEnabled(true);
        }else{
            name.setError(null);
        }
        if(amount.getEditText().getText().length()  == 0 ){
            amount.setError("Amount can't be empty");
        }else{
            amount.setError(null);
        }

        if (name.getEditText().getText().length()!=0 && amount.getEditText().getText().length()!=0){


            String n = name.getEditText().getText().toString();
            double a = Double.parseDouble(amount.getEditText().getText().toString());
            String c = (String)spinnerCurrency.getSelectedItem();
            Icon icon = (Icon)spinnerIcon.getSelectedItem();
            int i = icon.getId();

            Account account = new Account(n,a,c, i);
            //            addCategoryToDb(category);
            Toast.makeText(context, "Account was added successfully",
                    Toast.LENGTH_SHORT).show();
        }
    }
}


