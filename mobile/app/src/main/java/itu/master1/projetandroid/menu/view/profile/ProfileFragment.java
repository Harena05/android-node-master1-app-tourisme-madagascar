package itu.master1.projetandroid.menu.view.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import itu.master1.projetandroid.R;
import itu.master1.projetandroid.global.MyApplication;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        ConstraintLayout container = (ConstraintLayout)inflater.inflate(R.layout.fragment_profile, parent, false);
        TextView txtView = container.findViewById(R.id.txtProfileName);
        MyApplication app = (MyApplication) getActivity().getApplication();
        String name = app.getName();
        if(name != null) name = name.toUpperCase(Locale.ROOT);
        txtView.setText(name);
        return container;
    }

}
