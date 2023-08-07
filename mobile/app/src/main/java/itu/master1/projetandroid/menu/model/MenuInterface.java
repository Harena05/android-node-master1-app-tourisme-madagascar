package itu.master1.projetandroid.menu.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MenuInterface {
    @GET("api/contents")
    Call<List<Content>> doGetContentList();
}
