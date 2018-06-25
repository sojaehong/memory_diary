package com.jaehong.kcci.memorydiary.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import com.jaehong.kcci.memorydiary.DBImportAndExport;
import com.jaehong.kcci.memorydiary.PasswordActivity;
import com.jaehong.kcci.memorydiary.R;

/**
 * Created by jeahong on 2018-05-07.
 */

public class SettingPreferenceFragment extends PreferenceFragment {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    PreferenceScreen password, backup, recovery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();

        password = (PreferenceScreen) findPreference("password");
        backup = (PreferenceScreen) findPreference("backup");
        recovery = (PreferenceScreen) findPreference("recovery");

        if (prefs.getString("password",null) == null){
            editor.putString("password","0000");
            editor.commit();
            password.setSummary(prefs.getString("password",null));
        }

        password.setSummary(prefs.getString("password",null));

        password.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity() , PasswordActivity.class);
                intent.putExtra("type","비밀번호 저장");
                startActivity(intent);
                return true;
            }
        });

        backup.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                backup.setSummary(new DBImportAndExport(getActivity().getApplicationContext()).exportDB());
                return true;
            }
        });

        recovery.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new DBImportAndExport(getActivity().getApplicationContext()).importDB();
                getActivity().finish();
                restart();
                return true;
            }
        });

    }



    public void restart(){

        Intent intent = getActivity().getBaseContext().getPackageManager().
                getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
