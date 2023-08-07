package itu.master1.projetandroid.menu.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import itu.master1.projetandroid.R;
import itu.master1.projetandroid.authentication.view.ConnexionActivity;
import itu.master1.projetandroid.global.MyApplication;
import itu.master1.projetandroid.menu.view.list.MadaFragment;
import itu.master1.projetandroid.menu.view.preferences.PreferencesFragment;
import itu.master1.projetandroid.menu.view.profile.ProfileFragment;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_menu);

        FragmentManager ft = getSupportFragmentManager();
        ft.beginTransaction().replace(R.id.id_frag_menu_container, MadaFragment.class, null).commit();


        configureBottomView();
    }

    private void configureBottomView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (id) {
                case R.id.action_menu:
                    ft.replace(R.id.id_frag_menu_container, MadaFragment.class, null);
                    break;
                case R.id.action_settings:
                    ft.replace(R.id.id_frag_menu_container, PreferencesFragment.class, null);
                    break;
                case R.id.action_profile:
                    ft.replace(R.id.id_frag_menu_container, ProfileFragment.class, null);
                    break;
                default:
                    break;
            }

            ft.commit();
            return true;
        });
    }

    public void logout(View view) {
        MyApplication app = (MyApplication) getApplication();
        app.setToken(null);
        Intent intentLogin = new Intent(MenuActivity.this, ConnexionActivity.class);
        startActivity(intentLogin);
    }

}
