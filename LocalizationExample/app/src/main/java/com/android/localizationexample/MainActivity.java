package com.android.localizationexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.loadLocale();
        setContentView(R.layout.activity_main);
    }

    /************************************************************************
     * Purpose:         Load Locale (Language) Preference
     * Precondition:    .
     * Postcondition:   .
     ************************************************************************/
    private void loadLocale()
    {
        String langPref = getString(R.string.pref_language_key);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_common_key), Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, Locale.getDefault().getLanguage().trim());

        this.changeLocale(language);
    }

    private void changeLocale(String language)
    {
        Locale locale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();

        Locale.setDefault(locale);
        config.setLocale(locale);
        res.updateConfiguration(config, metrics);
    }

    /************************************************************************
     * Purpose:         Toolbar & Toolbar Item Selection
     * Precondition:    .
     * Postcondition:   .
     ************************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.activity_toolbar_settingsButton)
        {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}