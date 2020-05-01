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
import androidx.fragment.app.FragmentTransaction;

import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.db.Account;
import com.example.mobfinalmenuplusnavbar.db.DBValidateDataException;
import com.example.mobfinalmenuplusnavbar.db.Icon;
import com.example.mobfinalmenuplusnavbar.ui.main.MainFragment;
import com.example.mobfinalmenuplusnavbar.ui.settings.SettingsFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class AddAccountFragment extends Fragment implements View.OnClickListener {
    TextInputLayout name;
    TextInputLayout amount;
    Spinner spinnerCurrency;
    Spinner spinnerIcon;
    Button btnSubmit;
    Context context;
    ArrayList<Icon> icons;
    Account updateAccount;

    public AddAccountFragment(){

    }

    public AddAccountFragment(Account updateAccount){
        this.updateAccount = updateAccount;
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

        ArrayList<String > currencies = new ArrayList<>(Arrays.asList(new String[]{"$", "€", "₸", "₽"}));

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1,
                currencies);

        spinnerCurrency.setAdapter(adapter);
        ArrayList<Icon> icons = Icon.getAccountIcons();
        IconsAdapter iconsAdapter = new IconsAdapter(context,icons);
        spinnerIcon.setAdapter(iconsAdapter);

        if (updateAccount!=null){
            btnSubmit.setText("update account");
            name.getEditText().setText(updateAccount.getName());
            amount.getEditText().setText(""+updateAccount.getAmount());
            spinnerCurrency.setSelection(currencies.indexOf(updateAccount.getCurrency()));
            spinnerIcon.setSelection(Icon.getAccountPosition(updateAccount.getIcon()));
        }

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
            FragmentTransaction transaction = getFragmentManager().beginTransaction();


            if (updateAccount!=null){
                updateAccount.setName(n);
                updateAccount.setAmount(a);
                updateAccount.setCurrency(c);
                updateAccount.setIcon(i);
                try {
                    updateAccount.save();
                    Toast.makeText(context, "Account was updated successfully",
                            Toast.LENGTH_SHORT).show();
                    MainFragment mainFragment = new MainFragment();
                    transaction.replace(R.id.main_container,mainFragment, "SettingsFragment");
                    transaction.commit();

                } catch (DBValidateDataException e) {
                    e.printStackTrace();
                }
            }else{
                Account account = new Account(n,a,c, i);
                try {
                    account.save();
                    Toast.makeText(context, "Account was added successfully",
                            Toast.LENGTH_SHORT).show();
                    SettingsFragment settingsFragment = new SettingsFragment();
                    transaction.replace(R.id.main_container, settingsFragment, "SettingsFragment");
                    transaction.commit();
                } catch (DBValidateDataException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}


