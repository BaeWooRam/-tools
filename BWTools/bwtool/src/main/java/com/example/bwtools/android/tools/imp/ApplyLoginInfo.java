package com.example.bwtools.android.tools.imp;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bwtools.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;


public abstract class ApplyLoginInfo {
    private static final String TAG = "GoogleLoginInfoBuilder";
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


    public boolean GoogleApplayInfo(GoogleSignInAccount account) {
        if(account == null)
            return false;

        String imageUrl = account.getPhotoUrl().toString();
        ApplyInfo(imageUrl, account.getEmail(), account.getDisplayName());
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

        ApplyInfo(image,email,name);

        return true;
    }

    public void ApplyInfo(String imageUrl, String email, String name) {
        if (Image != null) {
            ApplyPicture(Image,imageUrl);
        }

        if (Name != null)
            Name.setText(name);

        if (Name != null)
            Email.setText(email);
    }


    /**
     * Picasso or Gilde 처리 해주는 곳
     * @param image
     */
    public abstract void ApplyPicture(ImageView image, String str_imageUrl);
}



