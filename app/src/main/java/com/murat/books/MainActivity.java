package com.murat.books;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText etKitapAdi, etYazarAdi, etOzet, etISBN, etTarih;
    Button btnEkle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        etKitapAdi = findViewById(R.id.etKitapAdi);
        etYazarAdi = findViewById(R.id.etYazarAdi);
        etOzet = findViewById(R.id.etOzet);
        etISBN = findViewById(R.id.etISBN);
        etTarih = findViewById(R.id.etBasimTarihi);
        btnEkle = findViewById(R.id.btnSave);
        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFirebase();
            }
        });

    }

    private void addFirebase() {
        String kitap_Adi = etKitapAdi.getText().toString();
        String Yazar_Adi = etYazarAdi.getText().toString();
        String ISBN = etISBN.getText().toString();
        String Tarih = etTarih.getText().toString();
        String ozet = etOzet.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("kitaplik").child(kitap_Adi);
        myRef.child("kitap_adi").setValue(kitap_Adi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    etKitapAdi.setText("");
                }else {
                    etKitapAdi.setError("hata! veritabanına eklenemedi");
                }
            }
        });
        myRef.child("yazar_adi").setValue(Yazar_Adi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    etYazarAdi.setText("");
                }else {
                    etYazarAdi.setError("hata! veritabanına eklenemedi");
                }
            }
        });

        /*addOnComplete eklemekteki amacımız değerler veri tabanına başarılı bir şekilde kayıt edilip edilmediğini
        * kontrol etmek amacıyla eğer eklendiyse edittext içeriğini boşalt*/
        myRef.child("ISBN").setValue(ISBN).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    etISBN.setText("");
                }else {
                    etISBN.setError("hata! veritabanına eklenemedi");
                }
            }
        });
        myRef.child("tarih").setValue(Tarih).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    etTarih.setText("");
                }else {
                    etTarih.setError("hata! veritabanına eklenemedi");
                }
            }
        });
        myRef.child("ozet").setValue(ozet).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    etOzet.setText("");
                }else {
                    etOzet.setError("hata! veritabanına eklenemedi");
                }
            }
        });
    }

    public void kitapList(View view) {
        startActivity(new Intent(this,KitapList.class));
    }
}
