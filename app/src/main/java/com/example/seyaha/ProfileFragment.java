package com.example.seyaha;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

  RecyclerView recyclerView;
  GridLayoutManager gridLayoutManager;
  fav_adapter fav_adapter_v;
  TextView name,email;
  CircleImageView img;
    ColorDrawable colorDrawable;
    FirebaseAuth mFirebaseAuth;
    Button confirm_but;
    FirebaseFirestore db;
    public User mUser;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_profile,container,false);
        FirestoreQueries.getUser(new FirestoreQueries.FirestoreUserCallback() {
            @Override
            public void onCallback(User user) {
                mUser = user;

            }
        });
        confirm_but=mView.findViewById(R.id.confirm_interests);
        mFirebaseAuth=FirebaseAuth.getInstance();
        colorDrawable=new ColorDrawable(Color.GRAY);
        name=mView.findViewById(R.id.prof_name);
        name.setText(mFirebaseAuth.getCurrentUser().getDisplayName());
        email=mView.findViewById(R.id.prof_email);
        email.setText(mFirebaseAuth.getCurrentUser().getEmail());
        img=mView.findViewById(R.id.prof_img);
        Picasso.get().load(mFirebaseAuth.getCurrentUser().getPhotoUrl()).placeholder(colorDrawable).into(img);
            recyclerView=mView.findViewById(R.id.rv);
        gridLayoutManager = new GridLayoutManager(mView.getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        fav_adapter_v=new fav_adapter(getContext());
        recyclerView.setAdapter(fav_adapter_v);
        db = FirebaseFirestore.getInstance();
       confirm_but.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {
               publish_interests();
               Toast.makeText(getContext(),getResources().getString(R.string.save),Toast.LENGTH_LONG).show();
           }
       });

        return mView;
    }
    private void publish_interests()
    {
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("intrests",fav_adapter.interests_chosen);
        DocumentReference userRefernce = db.collection("users").document(mUser.userId);
        userRefernce.update(updatedData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("anastest", "onSuccess: user info updated");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("anastest", "onFailure: ", e);
            }
        });
    }




    }


