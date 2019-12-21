package com.example.seyaha;

import android.content.Intent;
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
import java.util.Iterator;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ProfileFragment";
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    static InterestsAdapter interest_adapter;
    TextView name;
    CircleImageView img;
    ColorDrawable colorDrawable;
    FirebaseAuth mFirebaseAuth;
    Button save_but, clear_but;
    CustomToggleButton notification_but;
    FirebaseFirestore db;
    public User mUser;

    private void filledView(View pView)
    {
        db = FirebaseFirestore.getInstance();
        save_but = pView.findViewById(R.id.save_interests);
        save_but.setOnClickListener(this);
        clear_but = pView.findViewById(R.id.clear_interests);
        clear_but.setOnClickListener(this);
        notification_but = pView.findViewById(R.id.notification_button);
        notification_but.setOnClickListener(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        colorDrawable = new ColorDrawable(Color.GRAY);
        name = pView.findViewById(R.id.prof_name);
        name.setText(mFirebaseAuth.getCurrentUser().getDisplayName());
        img = pView.findViewById(R.id.prof_img);
        Picasso.get().load(mFirebaseAuth.getCurrentUser().getPhotoUrl().toString() + "?height=500").fit().placeholder(colorDrawable).into(img);
        recyclerView = pView.findViewById(R.id.rv_recommended);
        gridLayoutManager = new GridLayoutManager(pView.getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        interest_adapter = new InterestsAdapter(getContext());
        recyclerView.setAdapter(interest_adapter);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pView = inflater.inflate(R.layout.fragment_profile, container, false);
        FirestoreQueries.getUser(new FirestoreQueries.FirestoreUserCallback() {
            @Override
            public void onCallback(User user) {
                mUser = user;
            }
        });
        filledView(pView);
        return pView;
    }

    private void publish_interests() {

        Map<String, Object> updatedData = new HashMap<>();
        InterestsAdapter.interests_chosen.addAll(InterestsAdapter.intrests_hashSet);
        updatedData.put("intrests", InterestsAdapter.interests_chosen);
        DocumentReference userRefernce = db.collection("users").document(mUser.userId);
        userRefernce.update(updatedData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: user info updated");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e);
            }
        });
    }

    public boolean checkInterestsIdentical() {
        int counter = 0;
        for (int i = 0; i < mUser.intrests.size(); i++) {
            if (InterestsAdapter.intrests_hashSet.size() != 0) {
                Iterator<String> x = InterestsAdapter.intrests_hashSet.iterator();
                while (x.hasNext()) {
                    if (mUser.intrests.get(i).equals(x.next()))
                        counter++;
                }
            }
        }

        if (counter == mUser.intrests.size() && counter == InterestsAdapter.intrests_hashSet.size())
            return true;
        else
            return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case  R.id.save_interests :
                if (InterestsAdapter.intrests_hashSet.size() != 0 && !checkInterestsIdentical()) {
                    publish_interests();
                    InterestsAdapter.interests_chosen.clear();
                    Toast.makeText(getContext(), getResources().getString(R.string.save), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("FRAGMENT_ID", 1);
                    startActivity(intent);
                } else if (mUser.intrests.size() != 0 && checkInterestsIdentical())
                    Toast.makeText(getContext(), getResources().getString(R.string.save_size_check2), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), getResources().getString(R.string.save_size_check), Toast.LENGTH_SHORT).show();
                break;

            case R.id.clear_interests :
                InterestsAdapter.intrests_hashSet.clear();
                publish_interests();
                interest_adapter.clearInterests();
                break;
            case R.id.notification_button :

                if (notification_but.isChecked()) {
                    Toast.makeText(getContext(), getResources().getString(R.string.notification_on), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.notification_off), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}


