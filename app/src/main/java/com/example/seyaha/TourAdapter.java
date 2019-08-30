package com.example.seyaha;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ImageViewHolder> {
    private static final String TAG = "TourAdapter";
    public static List<Tour> mTours;
    ColorDrawable colorDrawable;
    Context context;
    public User mUser;
    FirebaseFirestore db;
    int id = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tour_item, viewGroup, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);

        FirestoreQueries.getUser(new FirestoreQueries.FirestoreUserCallback() {
            @Override
            public void onCallback(User user) {
                mUser = user;
                if (user.isAdmin)
                    id = R.menu.tour_options_admin;
                else
                    id = R.menu.tour_options;
            }
        });

        return imageViewHolder;
    }

    public TourAdapter(List<Tour> tours) {
        mTours = tours;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {

        final Tour tour = mTours.get(position);

        if (SplashScreenActivity.lan.equalsIgnoreCase("ar")) {
            holder.mTitle.setText(tour.titleAR);
            holder.mDescription.setText(tour.makeArabicDescription(tour.categoriesAR));
        } else {
            holder.mTitle.setText(tour.titleEN);
            holder.mDescription.setText(tour.makeEnglishDescription(tour.categoriesEN));
        }
        holder.mRating.setText(tour.ratingsNum + "");

        holder.mComments.setText(tour.commentsNum + "");
        colorDrawable = new ColorDrawable(Color.GRAY);
        Picasso.get().load(tour.imageURLs.get(0)).placeholder(colorDrawable).fit().into(holder.img1);
        holder.img1.setClipToOutline(true);

        Picasso.get().load(tour.imageURLs.get(1)).placeholder(colorDrawable).fit().into(holder.img2);
        holder.img2.setClipToOutline(true);

        Picasso.get().load(tour.imageURLs.get(2)).placeholder(colorDrawable).fit().into(holder.img3);
        holder.img3.setClipToOutline(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tour.addClickEffect(view);
                Intent i = new Intent(context, DetailedActivity.class);
                i.putExtra("places", (Serializable) mTours.get(position).places);
                context.startActivity(i);
            }
        });

        holder.alertDialog = holder.builder.create();
        holder.mCancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.alertDialog.dismiss();
            }
        });
        holder.mRate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.alertDialog.show();
            }
        });
        holder.post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    db = FirebaseFirestore.getInstance();
                    final DocumentReference tourReference = db.collection("tours").document(mTours.get(position).tourId);
                    tourReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Tour tour = documentSnapshot.toObject(Tour.class);
                            List<Comment> comments = new ArrayList<>();
                            comments = tour.comments;
                            addComment(comments, tour, holder.editText, holder.ratingBar, holder.mRating, holder.mComments, holder.mRate_btn, position);
                            holder.alertDialog.dismiss();
                        }
                    });


                } catch (Exception e) {
                    Log.e(TAG, "onClick: ", e);
                }
            }

        });


        holder.alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        FirestoreQueries.getUser(new FirestoreQueries.FirestoreUserCallback() {
            @Override
            public void onCallback(User user) {
                holder.ratingName.setText(user.displayName);
                Picasso.get().load(user.imageURL).fit().into(holder.ratingPic);
                for (String n : user.toursCommentedOn) {
                    if (n.equals(mTours.get(position).tourId)) {
                        holder.mRate_btn.setImageResource(R.drawable.ic_star_filled);
                        holder.mRate_btn.setClickable(false);
                        break;
                    }
                }
            }
        });

        holder.mArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopupMenu(context, v, tour);
            }
        });

        holder.mComment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Tour tour = mTours.get(position);
                    List<Comment> comments = tour.comments;
                    CommentAdapter adapter = new CommentAdapter(context, comments);
                    Dialog commentsDialog = new Dialog(context);
                    commentsDialog.setContentView(R.layout.comments_dialog);
                    holder.commentsListView.setAdapter(adapter);

                    commentsDialog.show();
                } catch (Exception e) {
                    Log.e(TAG, "onClick: ", e);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTours.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView img1, img2, img3;
        CircleImageView ratingPic;
        TextView mTitle, mDescription, mRating, mComments, ratingName;
        ImageButton mShare_btn, mComment_btn, mRate_btn, mArrowDown;
        Button post_btn, mCancel_btn;
        LayoutInflater vi;
        View mView;
        View commentsDialogView;
        EditText editText;
        AlertDialog.Builder builder;
        AlertDialog alertDialog;
        RatingBar ratingBar;
        ListView commentsListView;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ImageViewHolder(View itemView) {
            super(itemView);
            vi = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(R.layout.custom_rating_dialog, null, false);
            commentsDialogView = vi.inflate(R.layout.comments_dialog, null, false);
            commentsListView = commentsDialogView.findViewById(R.id.comments_listview);
            post_btn = mView.findViewById(R.id.post_btn);
            mCancel_btn = mView.findViewById(R.id.cancel_btn);
            ratingPic = mView.findViewById(R.id.rating_pic);
            ratingName = mView.findViewById(R.id.rating_name);
            editText = mView.findViewById(R.id.comment_post);
            ratingBar = mView.findViewById(R.id.rating_bar);
            builder = new AlertDialog.Builder(itemView.getContext());
            builder.setView(mView);
            mTitle = itemView.findViewById(R.id.title_txt);
            mDescription = itemView.findViewById(R.id.desc);
            mRating = itemView.findViewById(R.id.rate_num);
            mArrowDown = itemView.findViewById(R.id.arrowDown);
            mComments = itemView.findViewById(R.id.comment_num);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            mShare_btn = itemView.findViewById(R.id.share_btn);
            mComment_btn = itemView.findViewById(R.id.comment_btn);
            mRate_btn = itemView.findViewById(R.id.star);
        }

    }

    public void removeTour(Tour tour) {
        mTours.remove(tour);
        notifyDataSetChanged();
    }

    public void createPopupMenu(final Context context, View view, final Tour tour) {
        db = FirebaseFirestore.getInstance();
        PopupMenu mPopupMenu = new PopupMenu(context, view);
        mPopupMenu.getMenuInflater().inflate(id, mPopupMenu.getMenu());
        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.overview_tour:
                        // code the overview here
                        break;
                    case R.id.delete_tour:
                        db.collection("tours").document(tour.tourId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Deleted Tour.");
                                    removeTour(tour);
                                } else
                                    Log.d(TAG, "Failed!!");

                            }
                        });
                        break;
                }
                return true;
            }
        });

        mPopupMenu.show();
    }


    private void addComment(List<Comment> comments, Tour tour, EditText editText, RatingBar ratingBar, TextView mRating, TextView mComments, ImageButton mRate_btn, int position) {

        final DocumentReference tourReference = db.collection("tours").document(mTours.get(position).tourId);

        Comment mComment = new Comment(mUser, editText.getText().toString(), ratingBar.getRating());
        comments.add(mComment);
        double ratingsNum = tour.ratingsNum;
        int commentsNum = tour.commentsNum;
        int numOfPeopleWhoRated = tour.numOfPeopleWhoRated;
        ratingsNum = ((ratingsNum * numOfPeopleWhoRated) + ratingBar.getRating()) / ++numOfPeopleWhoRated;

        if (!(editText.getText().toString().matches(""))) {
            commentsNum++;
        }

        mRating.setText(ratingsNum + "");
        mComments.setText(commentsNum + "");
        mRate_btn.setImageResource(R.drawable.ic_star_filled);
        mRate_btn.setClickable(false);


        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("comments", comments);
        updatedData.put("commentsNum", commentsNum);
        updatedData.put("ratingsNum", ratingsNum);
        updatedData.put("numOfPeopleWhoRated", numOfPeopleWhoRated);
        tourReference.update(updatedData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: comments updated successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Failed to update comments");
            }
        });

        Map<String, Object> userUpdate = new HashMap<>();
        List<String> toursCommentedOn = mUser.toursCommentedOn;
        toursCommentedOn.add(mTours.get(position).tourId);
        userUpdate.put("toursCommentedOn", toursCommentedOn);

        DocumentReference userRefernce = db.collection("users").document(mUser.userId);
        userRefernce.update(userUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {

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

        editText.setText("");
        ratingBar.setRating(0);

    }

}
