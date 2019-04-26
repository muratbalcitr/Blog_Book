package com.murat.books.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.murat.books.R;
import com.murat.books.adapter.Book_Adapter;
import com.murat.books.model.Book;

import java.util.ArrayList;
import java.util.List;

public class KitapList extends Fragment {
    RecyclerView rvBook;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    List<Book> listBook;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listBook = new ArrayList<>();
        getFirebaseData();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_kitap_list, container, false);
        rvBook = view.findViewById(R.id.rvBook);
        return view;

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
            book_adapter = new Book_Adapter(listBook, getContext());
            rvBook.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            rvBook.setAdapter(book_adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
