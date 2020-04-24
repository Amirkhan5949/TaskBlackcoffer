package com.example.taskblackcoffer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.taskblackcoffer.Fregments.ExploreFragment;
import com.example.taskblackcoffer.Fregments.SavedFragment;
import com.example.taskblackcoffer.Fregments.TagsFragment;
import com.example.taskblackcoffer.Fregments.TrendingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.preference.PowerPreference;

public class DashBoardActivity extends AppCompatActivity {


    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        frameLayout=findViewById(R.id.frame);
        bottomNavigationView=findViewById(R.id.bottom);
        replace(new TagsFragment());


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.tags:
                        replace(new TagsFragment());
                        return true;
                    case R.id.trending:
                        replace(new TrendingFragment());
                        return true;

                    case R.id.explore:
                        replace(new ExploreFragment());
                        return true;

                    case R.id.saved:
                        replace(new SavedFragment());
                        return true;

                }
                return false;
            }
        });
    }
    void replace(Fragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }

    }
