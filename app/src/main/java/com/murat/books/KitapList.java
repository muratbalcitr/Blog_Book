package com.murat.books;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.murat.books.adapter.Book_Adapter;
import com.murat.books.model.Book;

import java.util.ArrayList;
import java.util.List;

public class KitapList extends AppCompatActivity {
    RecyclerView rvBook;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    List<Book> listBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap_list);
        rvBook = findViewById(R.id.rvBook);
        listBook = new ArrayList<>();
        getFirebaseData();

    }

    private void getFirebaseData() {

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("kitaplik");//firebase'deki tablomuza erişiyoruz.
        mDatabaseReference.addValueEventListener(new ValueEventListener() {//bu metot sayesinde verilerimizi tek tek çekiyoruz.
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book book = new Book();
                    book.setYazar_Adi(ds.child("yazar_adi").getValue().toString());/*yazar_adi child'ının value'lerini alıyoruz
                    diğer bütün satırlarda aynsını uyguluyoruz*/
                    book.setKitap_adi(ds.child("kitap_adi").getValue().toString());
                    book.setISBN(ds.child("ISBN").getValue().toString());
                    book.setBasim_tarihi(ds.child("tarih").getValue().toString());
                    book.setOzet(ds.child("ozet").getValue().toString());
                    listBook.add(book);
                }
                if (listBook.size() > 0) {
                    addList();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    Book_Adapter book_adapter;
    Context context;

    private void addList() {

        try {
            book_adapter = new Book_Adapter(listBook, getApplicationContext());
            rvBook.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            rvBook.setAdapter(book_adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
