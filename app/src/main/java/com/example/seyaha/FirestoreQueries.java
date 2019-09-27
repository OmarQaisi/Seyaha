package com.example.seyaha;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirestoreQueries {

    private static final String TAG = "FirestoreQueries";
   static List<Tour> mTours = new ArrayList<Tour>();
    //Firebase
    private static FirebaseAuth mFirebaseAuth;
    private static FirebaseFirestore mFirebaseFirestore;
    private static CollectionReference users, places, tours;

    public static void getUser(final FirestoreUserCallback firestoreCallback){
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

    public static void getPlaces(final FirestorePlaceCallback firestorePlaceCallback){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        places = mFirebaseFirestore.collection("places");
        places.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Place> mPlaces = new ArrayList<Place>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        mPlaces.add(document.toObject(Place.class));
                    }
                    firestorePlaceCallback.onCallback(mPlaces);
                }else
                    Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    public static void getTours(final FirestoreTourCallback firestoreTourCallback){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        tours = mFirebaseFirestore.collection("tours");
        tours.orderBy("ratingsNum", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Tour> mTours = new ArrayList<Tour>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        mTours.add(document.toObject(Tour.class));
                    }
                    firestoreTourCallback.onCallback(mTours);
                }else
                    Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    protected interface FirestoreUserCallback {
        void onCallback(User user);
    }

    protected interface FirestorePlaceCallback {
        void onCallback(List<Place> places);
    }

    protected interface FirestoreTourCallback {
        void onCallback(List<Tour> tours);
    }


}
