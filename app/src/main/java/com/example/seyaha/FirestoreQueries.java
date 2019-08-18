package com.example.seyaha;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreQueries {

    private static final String TAG = "FirestoreQueries";

    //Firebase
    private static FirebaseAuth mFirebaseAuth;
    private static FirebaseFirestore mFirebaseFirestore;
    private static CollectionReference users;

    public static void getUser(final FirestoreCallback firestoreCallback){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        users = mFirebaseFirestore.collection("users");
        users.whereEqualTo("email",  mFirebaseAuth.getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                User mUser = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                firestoreCallback.onCallback(mUser);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"Error Fetching config", e);
            }
        });
    }


    protected interface FirestoreCallback {
        void onCallback(User user);
    }

}
