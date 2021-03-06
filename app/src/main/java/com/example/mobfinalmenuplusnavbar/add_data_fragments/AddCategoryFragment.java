package com.example.mobfinalmenuplusnavbar.add_data_fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.db.Category;
import com.example.mobfinalmenuplusnavbar.db.DBValidateDataException;
import com.example.mobfinalmenuplusnavbar.db.Icon;
import com.example.mobfinalmenuplusnavbar.ui.main.MainFragment;
import com.example.mobfinalmenuplusnavbar.ui.settings.SettingsFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Set;

public class AddCategoryFragment extends Fragment implements View.OnClickListener {

    TextInputLayout name;
    TextInputLayout description;
    Spinner spinnerIcons;
    Button btnSubmit;
    Context context;
    ArrayList<Icon> icons;
    Category updateCategory;

    public AddCategoryFragment(Category updateCategory){
        this.updateCategory = updateCategory;
    }

    public AddCategoryFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.add_category_fragment, container, false);
        name = view.findViewById(R.id.title_field);
        description = view.findViewById(R.id.description_field);
        spinnerIcons = view.findViewById(R.id.icon_dropdown);
        icons = Icon.getCategoryIcons();

        IconsAdapter adapter = new IconsAdapter(context, icons);
        spinnerIcons.setAdapter(adapter);
        btnSubmit = view.findViewById(R.id.btn_submit);

        if (updateCategory !=null){
            name.getEditText().setText(updateCategory.getName());
            description.getEditText().setText(updateCategory.getDescription());
            spinnerIcons.setSelection(Icon.getCategoryPosition(updateCategory.getIcon()));
            btnSubmit.setText("Update Category");
        }


        btnSubmit.setOnClickListener(this);

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

        if (name.getEditText().getText().length()!=0 ){


            String n = name.getEditText().getText().toString();
            String d = description.getEditText().getText().toString();
            Icon icon = (Icon)spinnerIcons.getSelectedItem();
            int i = icon.getId();

            if(updateCategory!=null){
                updateCategory.setName(n);
                updateCategory.setDescription(d);
                updateCategory.setIcon(i);
                try {
                    updateCategory.save();
                    Toast.makeText(context, "Category was updated successfully",
                            Toast.LENGTH_SHORT).show();
                } catch (DBValidateDataException e) {
                    e.printStackTrace();
                }
            }else{
                Category category = new Category(n,d,i);
                try {
                    category.save();
                    Toast.makeText(context, "Category was added successfully",
                            Toast.LENGTH_SHORT).show();
                    SettingsFragment settingsFragment = new SettingsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_container, settingsFragment, "SettingsFragment");
                    transaction.commit();
                } catch (DBValidateDataException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
