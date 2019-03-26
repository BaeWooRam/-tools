package com.example.akginakwon.util.builder;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akginakwon.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nhn.android.naverlogin.OAuthLogin;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class LoginInfoBuilder {
    private static final String TAG = "GoogleLoginInfoBuilder";
    private TextView Email;
    private TextView Name;
    private ImageView Image;
    private GoogleSignInAccount GoogleAccount;
    private String NaverAccount;

    public LoginInfoBuilder setNaverAccount(String naverAccount) {
        NaverAccount = naverAccount;
        return this;
    }

    public LoginInfoBuilder setAccount(GoogleSignInAccount account) {
        this.GoogleAccount = account;
        return this;
    }

    public LoginInfoBuilder setEmail(TextView email) {
        Email = email;
        return this;
    }


    public LoginInfoBuilder setName(TextView name) {
        Name = name;
        return this;
    }

    public LoginInfoBuilder setImage(ImageView image) {
        Image = image;
        return this;
    }

    public LoginInfoBuilder bulid() {
        new Info();
        return this;
    }

    class Info {
        public Info() {
            if (!GoogleApplayInfo(GoogleAccount))
                new Error(TAG+" Error! Please input Google Account");

            if(!NaverApplayInfo(NaverAccount))
                new Error(TAG + " Error! Please input Naver Account.");
        }
    }

    public boolean GoogleApplayInfo(GoogleSignInAccount account) {
        if(account == null)
            return false;

        ApplayInfo(account.getPhotoUrl(), account.getEmail(), account.getDisplayName());
        return true;
    }

    public boolean NaverApplayInfo(String account){
        if(account == null)
            return false;

//        Log.e(TAG,"acount = "+account);
        JsonParser jsonParser = new JsonParser();
        JsonObject object = (JsonObject) jsonParser.parse(account);

        JsonObject response = object.get("response").getAsJsonObject();
//        Log.e(TAG,"response = "+response);
        String email = response.get("email").getAsString();
        String name = response.get("name").getAsString();
        String image = response.get("profile_image").getAsString();

        ApplayInfo(image,email,name);

        return true;
    }

    public void ApplayInfo(String imageUrl, String email, String name) {
        if (Image != null) {
            Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.ic_menu_gallery)
                    .into(Image);
        }

        if (Name != null)
            Name.setText(name);

        if (Name != null)
            Email.setText(email);
    }

    public void ApplayInfo(Uri imageUrl, String email, String name) {
        if (Image != null) {
            Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.ic_menu_gallery)
                    .into(Image);
        }

        if (Name != null)
            Name.setText(name);

        if (Name != null)
            Email.setText(email);
    }
}



