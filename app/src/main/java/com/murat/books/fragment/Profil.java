package com.murat.books.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.murat.books.R;
import com.murat.books.login.FirebaseUIwithLogin;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profil extends Fragment implements View.OnClickListener {

    ImageView imgProfilPhoto;
    TextView tvName, tvNote;
    EditText etAbout;
    Button btnAbout, btnUpdateView;

    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    FirebaseUser mFirebaseUser;

    String name, email, about, uid;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;


    FirebaseStorage storage;
    StorageReference storageReference;

    public Profil() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if (mFirebaseUser == null) {
            startActivity(new Intent(getActivity(), FirebaseUIwithLogin.class));
        } else {
            name = mFirebaseUser.getDisplayName();
            email = mFirebaseUser.getEmail();
            uid = mFirebaseUser.getUid();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        imgProfilPhoto = view.findViewById(R.id.imgProfilPhoto);
        tvName = view.findViewById(R.id.tvNameProfil);
        tvNote = view.findViewById(R.id.tvAbout);
        etAbout = view.findViewById(R.id.etAbout);
        btnAbout = view.findViewById(R.id.btnAddAbout);
        btnUpdateView = view.findViewById(R.id.btnUpdateView);
        btnUpdateView.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        imgProfilPhoto.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName.setText(name + "\n" + email);
        viewUpdateProfile();

    }

    private void addAbout() {

        about = etAbout.getText().toString();
        mDatabaseReference.child("hakkinda").child(name).setValue(about);
        etAbout.setText("");
    }


    private void viewUpdateProfile() {
        mDatabaseReference.child("hakkinda").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue().toString().matches(""))
                    tvNote.setText(dataSnapshot.getValue().toString());
                else
                    Toast.makeText(getContext(), "hakkımda kısmını doldurunuz", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addPhoto() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference strRef = storageReference.child("images/" + UUID.randomUUID().toString());
            strRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnAddAbout:
                addAbout();
                break;
            case R.id.imgProfilPhoto:
                addPhoto();
                break;
            case R.id.btnUpdateView:
                viewUpdateProfile();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imgProfilPhoto.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
