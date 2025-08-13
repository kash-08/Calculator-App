package com.company.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.company.calculator.databinding.ActivityChangeThemeBinding;

public class ChangeThemeActivity extends AppCompatActivity {

    ActivityChangeThemeBinding switchBinding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        switchBinding = ActivityChangeThemeBinding.inflate(getLayoutInflater());
        setContentView(switchBinding.getRoot());

        switchBinding.toolbar2.setNavigationOnClickListener(view -> {
            finish();

        });
        switchBinding.MySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            sharedPreferences=this.getSharedPreferences("com.company.calculator", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();


            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("switch", true);

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("switch", false);


            }
            editor.apply();

        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences=this.getSharedPreferences("com.company.calculator", Context.MODE_PRIVATE);
        switchBinding.MySwitch.setChecked(sharedPreferences.getBoolean("switch", false));
    }
}