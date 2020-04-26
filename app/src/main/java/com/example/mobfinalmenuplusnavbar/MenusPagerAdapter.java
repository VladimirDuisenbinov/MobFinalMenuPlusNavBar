package com.example.mobfinalmenuplusnavbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.mobfinalmenuplusnavbar.ui.analytics.AnalyticsFragment;
import com.example.mobfinalmenuplusnavbar.ui.debts.DebtsFragment;
import com.example.mobfinalmenuplusnavbar.ui.history.HistoryFragment;
import com.example.mobfinalmenuplusnavbar.ui.main.MainFragment;
import com.example.mobfinalmenuplusnavbar.ui.plan.PlanFragment;
import com.example.mobfinalmenuplusnavbar.ui.settings.SettingsFragment;

public class MenusPagerAdapter extends FragmentStateAdapter {

    public MenusPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MainFragment();
            case 1:
                return new HistoryFragment();
            case 2:
                return new DebtsFragment();
            case 3:
                return new PlanFragment();
            case 4:
                return new AnalyticsFragment();
            case 5:
                return new SettingsFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
