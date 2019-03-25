package com.example.bwtools.android.builder.GoogleLogin;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


public class GoogleLoginInfoBuilder {
    private static final String TAG = "GoogleLoginInfoBuilder";
    private TextView Email;
    private TextView Name;
    private ImageView Image;
    private GoogleSignInAccount account;

    public GoogleLoginInfoBuilder setAccount(GoogleSignInAccount account) {
        this.account = account;
        return this;
    }

    public GoogleLoginInfoBuilder setEmail(TextView email) {
        Email = email;
        return this;
    }


    public GoogleLoginInfoBuilder setName(TextView name) {
        Name = name;
        return this;
    }

    public GoogleLoginInfoBuilder setImage(ImageView image) {
        Image = image;
        return this;
    }

    public GoogleLoginInfoBuilder bulid(){
        new Info();
        return this;
    }

    class Info{
        public Info() {
            if(Image != null){
                Picasso.get()
                        .load(account.getPhotoUrl())
                        .error(R.drawable.ic_menu_gallery)
                        .into(Image);
            }

            if(Name != null)
                Name.setText(account.getDisplayName());

            if(Name != null)
                Email.setText(account.getEmail());
        }
    }
}
