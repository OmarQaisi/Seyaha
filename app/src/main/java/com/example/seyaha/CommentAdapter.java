package com.example.seyaha;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends ArrayAdapter<Comment> {


    int tourPosition;

    public CommentAdapter(@NonNull Context context, List<Comment> comments,int tourPosition) {
        super(context,0,comments);
        this.tourPosition=tourPosition;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View listItem=convertView;
        if(listItem==null)
        {
            listItem= LayoutInflater.from(getContext()).inflate(R.layout.comment_item,parent,false);
        }
         Comment comment=getItem(position);


        CircleImageView profilePic=listItem.findViewById(R.id.comment_profile_pic);
        Picasso.get().load(comment.user.imageURL).fit().into(profilePic);

        TextView personName=listItem.findViewById(R.id.comment_person_name);
        personName.setText(comment.user.displayName);

        TextView commentText=listItem.findViewById(R.id.comment_main_text);
        commentText.setText(comment.comment);

        RatingBar ratingBar=listItem.findViewById(R.id.comment_rating_bar);
        ratingBar.setRating(comment.rating);

        final TextView noOfReviews=listItem.findViewById(R.id.number_of_reviews);
        FirestoreQueries.getUser(new FirestoreQueries.FirestoreUserCallback() {
            @Override
            public void onCallback(User user) {
               noOfReviews.setText(user.toursCommentedOn.size()+" Reviews");
            }
        });

        TextView time=listItem.findViewById(R.id.comment_time);
        time.setText(comment.time);

        return listItem;
    }
}
