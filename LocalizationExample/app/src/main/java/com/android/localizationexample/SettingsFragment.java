package com.android.localizationexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat
{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        this.setLanguage();
    }

    /************************************************************************
     * Purpose:         Set Language in Settings
     * Precondition:    .
     * Postcondition:   .
     ************************************************************************/
    private void setLanguage()
    {
        ListPreference languagePref = findPreference(getString(R.string.pref_language_key));

        if (languagePref != null)
        {
            Resources res = getResources();
            Configuration config = res.getConfiguration();

            switch (config.locale.getLanguage().trim())
            {
                case "de":
                    languagePref.setValueIndex(1);
                    break;
                case "es":
                    languagePref.setValueIndex(2);
                    break;
                case "fr":
                    languagePref.setValueIndex(3);
                    break;
                case "zh":
                    languagePref.setValueIndex(4);
                    break;
                case "ja":
                    languagePref.setValueIndex(5);
                    break;
                case "ko":
                    languagePref.setValueIndex(6);
                    break;
                default:
                    languagePref.setValueIndex(0);
            }

            languagePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue)
                {
                    saveLocale(newValue.toString());
                    changeLocale(newValue.toString());

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    return true;
                }
            });
        }
    }

    /************************************************************************
     * Purpose:         Save Locale (Language) Preference
     * Precondition:    .
     * Postcondition:   .
     ************************************************************************/
    private void saveLocale(String language)
    {
        if (getActivity() != null)
        {
            String langPref = getString(R.string.pref_language_key);
            SharedPreferences prefs = getActivity()
                    .getSharedPreferences(getString(R.string.pref_common_key), Activity.MODE_PRIVATE);
            Editor editor = prefs.edit();
            editor.putString(langPref, language);
            editor.apply();
        }
    }

    /************************************************************************
     * Purpose:         Change Locale (Language) Preference
     * Precondition:    .
     * Postcondition:   .
     ************************************************************************/
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
}
