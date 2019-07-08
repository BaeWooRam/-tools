package com.example.bwtools.android.tools.imp;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public abstract class ApplyLoginInfo {
    private final String TAG = "GoogleLoginInfoBuilder";
    private TextView Email;
    private TextView Name;
    private ImageView Image;
    private GoogleSignInAccount GoogleAccount;
    private String NaverAccount;

    public ApplyLoginInfo setupNaverAccount(String naverAccount) {
        NaverAccount = naverAccount;
        return this;
    }

    public ApplyLoginInfo setupGoogleAccount(GoogleSignInAccount account) {
        this.GoogleAccount = account;
        return this;
    }

    public ApplyLoginInfo setupEmail(TextView email) {
        Email = email;
        return this;
    }


    public ApplyLoginInfo setupName(TextView name) {
        Name = name;
        return this;
    }

    public ApplyLoginInfo setupImage(ImageView image) {
        Image = image;
        return this;
    }

    public void Apply(){
        if (GoogleApplayInfo(GoogleAccount))
            new Error(TAG+" Error! Please input Google Account");
        else if(NaverApplayInfo(NaverAccount))
            new Error(TAG + " Error! Please input Naver Account.");
        else
            new Error(TAG + " Error! Please input Naver Account and Google Account.");
    }


    private boolean GoogleApplayInfo(GoogleSignInAccount account) {
        if(account == null)
            return false;

        String imageUrl = account.getPhotoUrl().toString();

        if(imageUrl != null)
            ApplyInfo(imageUrl, account.getEmail(), account.getDisplayName());
        return true;
    }

    private boolean NaverApplayInfo(String account){
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

        ApplyInfo(image,email,name);

        return true;
    }

    private void ApplyInfo(String imageUrl, String email, String name) {
        if (Image != null) {
            ApplyPicture(Image,imageUrl);
        }

        if (Name != null)
            Name.setText(name);

        if (Name != null)
            Email.setText(email);
    }


    public abstract void ApplyPicture(ImageView image, String str_imageUrl);
}



