package com.murat.books.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.murat.books.R;
import com.murat.books.model.Book;

import java.util.List;

public class Book_Adapter extends RecyclerView.Adapter<Book_Adapter.BookHolder> {

    private List<Book> bookList;
    private Context context;

    public Book_Adapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        /*book_item.xml layoutmuzu kullanacağımızı bu metotun içerisinde belirtecez*/
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.book_item, viewGroup, false);
        /*
         * burada yapılan işlem şudur book_item.xml layoutunu inflate ediyor ve içindeki bütün viewları itemView
         * içine atıyor itemView'ımızıda Holder sınıfına yolluyoruz ve tanımlamalarımı yapıyoruz*/
        return new BookHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder bookHolder, int i) {
        /*
         * Bu metot nesnelere atamaları yaptığımız metotumuzdur.*/
        Book book = bookList.get(i);/* constructor sınıfımızı kullanarak bütün gelecek verileri sırasıyla recyler içinde gösterecez.*/
        bookHolder.tvKitapAdi.setText(book.getKitap_adi());
        bookHolder.tvYazarAdi.setText(book.getYazar_Adi());
        bookHolder.tvISBN.setText(String.valueOf(book.getISBN()));
        bookHolder.tvBasimTarihi.setText(String.valueOf(book.getBasim_tarihi()));
        bookHolder.tvOzet.setText(book.getOzet());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        TextView tvKitapAdi, tvYazarAdi, tvISBN, tvBasimTarihi, tvOzet;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            /* book_item.xml içindeki view'ların tanımlamalarını holder içinde yapıyoruz.*/
            tvKitapAdi = itemView.findViewById(R.id.tvKitapAdi_item);
            tvYazarAdi = itemView.findViewById(R.id.tvYazarAdi_item);
            tvISBN = itemView.findViewById(R.id.tvISBN_item);
            tvBasimTarihi = itemView.findViewById(R.id.tvBasimTarihi_item);
            tvOzet = itemView.findViewById(R.id.tvOzet_item);
        }
    }
}
