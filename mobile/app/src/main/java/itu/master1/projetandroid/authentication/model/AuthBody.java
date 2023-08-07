package itu.master1.projetandroid.authentication.model;

import com.google.gson.annotations.SerializedName;

public class AuthBody {
    @SerializedName("role")
    private int role;
    @SerializedName("name")
    private String name;
    @SerializedName("token")
    private String token;
    @SerializedName("email")
    private String email;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
