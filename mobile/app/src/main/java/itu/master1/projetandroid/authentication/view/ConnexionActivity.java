package itu.master1.projetandroid.authentication.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import itu.master1.projetandroid.R;
import itu.master1.projetandroid.authentication.viewmodel.ConnexionViewModel;
import itu.master1.projetandroid.databinding.ActivityAuthentificationConnexionBinding;
import itu.master1.projetandroid.global.MyApplication;
import itu.master1.projetandroid.menu.view.MenuActivity;


public class ConnexionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification_connexion);
        ConnexionViewModel connexionViewModel = new ViewModelProvider(this).get(ConnexionViewModel.class);

        ActivityAuthentificationConnexionBinding connexionBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_authentification_connexion);

        connexionBinding.setConnexionModel(connexionViewModel);
        connexionBinding.setLifecycleOwner(this);

        connexionViewModel.getEstAuthentifier().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    MyApplication app = (MyApplication)getApplication();
                    Intent menuIntent = new Intent(ConnexionActivity.this, MenuActivity.class);
                    startActivity(menuIntent);
                }
            }
        });
    }

}