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

import com.example.mobfinalmenuplusnavbar.Category;
import com.example.mobfinalmenuplusnavbar.DBValidateDataException;
import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.pojo.Icon;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

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

        if (updateCategory !=null){
            name.getEditText().setText(updateCategory.getName());
            description.getEditText().setText(updateCategory.getDescription());
            spinnerIcons.setSelection(Icon.getPosition(updateCategory.getIcon()));
        }


        btnSubmit = view.findViewById(R.id.btn_submit);
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
        if(description.getEditText().getText().length() == 0 ){
            description.setError("Description can't be empty");
        }else{
            description.setError(null);
        }

        if (name.getEditText().getText().length()!=0 && description.getEditText().getText().length()!=0){


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
                } catch (DBValidateDataException e) {
                    e.printStackTrace();
                }
            }else{
                Category category = new Category(n,d,i);
                try {
                    category.save();
                } catch (DBValidateDataException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(context, "Record was added successfully",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
