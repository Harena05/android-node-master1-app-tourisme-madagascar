package itu.master1.projetandroid.menu.view.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;


import itu.master1.projetandroid.R;


public class MadaDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_course_detail, parent, false);
    }

    @Override
    public void onStart() {
        super.onStart();


    }

}
