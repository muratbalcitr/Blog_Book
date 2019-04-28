package com.murat.books.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.murat.books.R;
import com.murat.books.activity.KitapActivity_Drawer;

import java.util.Arrays;

public class FirebaseUIwithLogin extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final int SIGN_IN = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_uiwith_login);
        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                        Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build()
                        )).build(), SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode==RESULT_OK){
                //BAŞARILI GİRİŞ
                startActivity(new Intent(FirebaseUIwithLogin.this,KitapActivity_Drawer.class));
            }else {
                if (response==null){
                    Toast.makeText(this, "giriş başarısız", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

}
