package com.example.mobfinalmenuplusnavbar.ui.settings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.mobfinalmenuplusnavbar.R;
import com.example.mobfinalmenuplusnavbar.add_data_fragments.AddRecordFragment;

public class RecordActivity extends AppCompatActivity {

    FragmentTransaction fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data_activity);
        fm = getSupportFragmentManager().beginTransaction();
        fm.add(R.id.container, new AddRecordFragment());
        fm.commit();
    }

}
