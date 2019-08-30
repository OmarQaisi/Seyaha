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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends ArrayAdapter {

    Context mContext;
    List<Comment> mComments=new ArrayList<>();
    public CommentAdapter(@NonNull Context context, List<Comment> comments) {
        super(context,0);
        mContext=context;
        mComments=comments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        if(convertView==null)
        {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.comment_item,parent,false);
        }
         Comment comment=mComments.get(position);


        CircleImageView profilePic=convertView.findViewById(R.id.comment_profile_pic);
        Picasso.get().load(comment.user.imageURL).fit().into(profilePic);

        TextView personName=convertView.findViewById(R.id.comment_person_name);
        personName.setText(comment.user.displayName);

        TextView commentText=convertView.findViewById(R.id.comment_main_text);
        commentText.setText(comment.comment);

        RatingBar ratingBar=convertView.findViewById(R.id.comment_rating_bar);
        ratingBar.setNumStars((int)comment.rating);

        return convertView;
    }
}
