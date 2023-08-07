package itu.master1.projetandroid.menu.view.preferences;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import java.util.ArrayList;
import java.util.List;

import itu.master1.projetandroid.R;
import itu.master1.projetandroid.menu.model.Content;
import itu.master1.projetandroid.menu.view.DelayedMessageService;
import itu.master1.projetandroid.menu.viewmodel.MadaViewModel;

public class PreferencesFragment extends PreferenceFragmentCompat {
    private MadaViewModel madaViewModel;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        madaViewModel = new ViewModelProvider(this.getActivity()).get(MadaViewModel.class);

        // Notification preference
        SwitchPreferenceCompat prefNofit = findPreference("notifications");

        Intent intent = new Intent(getActivity(), DelayedMessageService.class);
        madaViewModel.getContentsLive().observe(this, new Observer<List<Content>>() {
            @Override
            public void onChanged(List<Content> contents) {
                if(contents != null && contents.size() > 0) {
                    intent.putParcelableArrayListExtra(DelayedMessageService.EXTRA_CONTENT, (ArrayList<? extends Parcelable>) contents);
                }
            }
        });
        prefNofit.setOnPreferenceChangeListener((preference, newValue) -> {
            if((Boolean)newValue == true) {
                getActivity().startService(intent);
            } else {
                getActivity().stopService(intent);
            }
            return true;
        });


        // Frequency Preference
        ListPreference listPreference = findPreference("freq_list");
        String[] entries = new String[]{"5 sec", "10 sec", "30 sec", "1 mn"};
        listPreference.setEntries(entries);
        listPreference.setEntryValues(entries);
        if(listPreference.getValue() == null) {
            listPreference.setValueIndex(0);
        }

        listPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            System.out.println(newValue);
            if ("10 sec".equals(newValue)) {
                intent.putExtra(DelayedMessageService.EXTRA_FREQUENCY, 10000);
            } else if ("30 sec".equals(newValue)) {
                intent.putExtra(DelayedMessageService.EXTRA_FREQUENCY, 30000);
            } else if ("1 mn".equals(newValue)) {
                intent.putExtra(DelayedMessageService.EXTRA_FREQUENCY, 60000);
            } else {
                intent.putExtra(DelayedMessageService.EXTRA_FREQUENCY, 5000);
            }

            return true;
        });

        // Feedback Preference
        Preference myPref = findPreference("feedback");
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                sendFeedback(getActivity());
                return true;
            }
        });

    }

    public static void sendFeedback(Context context) {
        String body = null;
        try {
            body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            body = "\n\n-----------------------------\nVeuillez ne pas supprimer ces informations\n Device OS: Android \n Device OS version: " +
                    Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                    "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
        } catch (PackageManager.NameNotFoundException e) {
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"feedback@ibossy.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion d'amélioration");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, "Choisissir une application"));
    }
}
