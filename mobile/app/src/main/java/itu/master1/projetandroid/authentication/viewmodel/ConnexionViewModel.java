package itu.master1.projetandroid.authentication.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import itu.master1.projetandroid.authentication.model.AuthBody;
import itu.master1.projetandroid.authentication.model.AuthInterface;
import itu.master1.projetandroid.authentication.model.Utilisateur;
import itu.master1.projetandroid.global.MyApplication;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ConnexionViewModel extends AndroidViewModel {
    private MutableLiveData<Utilisateur> utilisateur = new MutableLiveData<>();
    private MutableLiveData<Boolean> estAuthentifier = new MutableLiveData<>();

    public ConnexionViewModel(@NonNull Application application) {
        super(application);
        utilisateur.setValue(new Utilisateur());
    }

    public LiveData<Utilisateur> getUtilisateur() {
        return this.utilisateur;
    }
    public MutableLiveData<Boolean> getEstAuthentifier() {
        return this.estAuthentifier;
    }

    /**
     * check the user, if there is in the database
     * @return boolean
     */
    public void seConnecter() {
        Utilisateur _utilisateur = getUtilisateur().getValue();
        // WEB SERVICE CALL

        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MyApplication.URL)
                .client(client)
                .build();
        AuthInterface service = retrofit.create(AuthInterface.class);
        Call<AuthBody> infoUtilisateur = service.seConnecter(_utilisateur);

        infoUtilisateur.enqueue(new Callback<AuthBody>() {
            @Override
            public void onResponse(Call<AuthBody> call, Response<AuthBody> response) {
                AuthBody body = response.body();


                if(body == null) {
                    Toast.makeText(getApplication(), "Mot de passe ou Nom d'utilisateur incorrect", Toast.LENGTH_SHORT).show();
                } else {
                    MyApplication app = getApplication();
                    app.setToken(body.getToken());
                    app.setName(body.getName());
                    app.setRole(body.getRole());
                    app.setEmail(body.getEmail());
                    estAuthentifier.setValue(true);
                    Toast.makeText(getApplication(), "Vous êtes connecter avec succès." + _utilisateur.getNom(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AuthBody> call, Throwable t) {
                Toast.makeText(getApplication(), "Une erreur inconnue s'est produite", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
