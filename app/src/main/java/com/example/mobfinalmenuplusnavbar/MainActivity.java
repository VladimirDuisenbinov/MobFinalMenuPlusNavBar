package com.example.mobfinalmenuplusnavbar;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.example.mobfinalmenuplusnavbar.ui.analytics.AnalyticsFragment;
import com.example.mobfinalmenuplusnavbar.ui.debts.DebtsFragment;
import com.example.mobfinalmenuplusnavbar.ui.history.HistoryFragment;
import com.example.mobfinalmenuplusnavbar.ui.main.MainFragment;
import com.example.mobfinalmenuplusnavbar.ui.plan.PlanFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ViewPager2 viewPager2;
    private MenusPagerAdapter adapter;
    private DrawerLayout drawer;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adapter = new MenusPagerAdapter(this);
        viewPager2 = findViewById(R.id.pager);
        viewPager2.setAdapter(adapter);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this,
                        drawer, toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            viewPager2.setCurrentItem(0);
            navigationView.setCheckedItem(R.id.nav_main);
        }

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_main:
                viewPager2.setCurrentItem(0);
                break;
            case R.id.nav_history:
                viewPager2.setCurrentItem(1);
                break;
            case R.id.nav_debts:
                viewPager2.setCurrentItem(2);
                break;
            case R.id.nav_plan:
                viewPager2.setCurrentItem(3);
                break;
            case R.id.nav_analytics:
                viewPager2.setCurrentItem(4);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
