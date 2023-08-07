package itu.master1.projetandroid.authentication.model;

import com.google.gson.annotations.SerializedName;

import itu.master1.projetandroid.global.MyApplication;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * which allows users to be controlled
 *
 */
public final class Utilisateur{
    @SerializedName("name")
    private String nom;
    @SerializedName("password")
    private String motDePasse;

    /**
     * Constructor set name and passowrd deflaut
     *
     */
    public Utilisateur() {
        setNom("etu1");
        setMotDePasse("1");
    }

    /**
     * Constructor set name and passowrd with
     * @param _name username
     * @param _password password
     */
    public Utilisateur(String _name, String _password) {
        this.setNom(_name);
        this.setMotDePasse(_password);
    }


    /**
     * get the name of user
     * @return String
     */
    public String getNom() {
        return nom;
    }

    /**
     * set the name of user
     * @param nom username
     */
    public void setNom(String nom) {
        this.nom = nom;
    }


    /**
     * get the password of user
     * @return String
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * set the password of user
     * @param motDePasse password of user
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
