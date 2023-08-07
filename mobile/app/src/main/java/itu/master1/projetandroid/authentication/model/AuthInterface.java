package itu.master1.projetandroid.authentication.model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthInterface {
    @POST("api/users/login")
    Call<AuthBody> seConnecter(@Body Utilisateur utilisareur);
}
