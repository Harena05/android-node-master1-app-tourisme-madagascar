package itu.master1.projetandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import itu.master1.projetandroid.authentication.view.ConnexionActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        startActivity(new Intent(SplashScreenActivity.this, ConnexionActivity.class));
        finish();
    }
}
