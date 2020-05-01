package com.example.mobfinalmenuplusnavbar.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.add_data_fragments.AddAccountFragment;
import com.example.mobfinalmenuplusnavbar.add_data_fragments.AddCategoryFragment;
import com.example.mobfinalmenuplusnavbar.add_data_fragments.AddRecordFragment;
import com.example.mobfinalmenuplusnavbar.db.Category;

public class SettingsFragment extends Fragment {

    private Button addAccountBtn;
    private Button addCategoryBtn;


    public SettingsFragment(){};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container,false);
        final Context context = view.getContext();
        addAccountBtn = view.findViewById(R.id.add_acc_btn);
        addAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), AccountActivity.class);
//                startActivity(intent);
                AddAccountFragment addFragment = new AddAccountFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.main_container, addFragment, "AddAccount");
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });
        addCategoryBtn = view.findViewById(R.id.add_cat_btn);
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCategoryFragment addFragment = new AddCategoryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.main_container, addFragment, "AddAccount");
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        return view;
    }
}
