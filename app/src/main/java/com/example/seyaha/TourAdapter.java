package com.example.seyaha;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ImageViewHolder> {
    public static List<Tour> mTours;
    ColorDrawable colorDrawable;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        context=viewGroup.getContext();
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tour_item,viewGroup,false);
        ImageViewHolder imageViewHolder=new ImageViewHolder(view);
        return imageViewHolder;
    }

    public TourAdapter(List<Tour> tours)
    {
        mTours = tours;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ImageViewHolder holder , int position)
    {

        Tour tour = mTours.get(position);

        holder.mTitle.setText(tour.titleEN);

        holder.mDescription.setText(tour.categoriesEN);

        holder.mRating.setText(tour.ratingsNum+"");

        holder.mComments.setText(tour.commentsNum+"");
        colorDrawable =new ColorDrawable(Color.GRAY);
        Picasso.get().load(tour.imageURLs[0]).placeholder(colorDrawable).fit().into(holder.img1);
        holder.img1.setClipToOutline(true);

        Picasso.get().load(tour.imageURLs[1]).placeholder(colorDrawable).fit().into(holder.img2);
        holder.img2.setClipToOutline(true);

        Picasso.get().load(tour.imageURLs[2]).placeholder(colorDrawable).fit().into(holder.img3);
        holder.img3.setClipToOutline(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Tour.addClickEffect(v);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Tour.addClickEffect(view);
                Intent i=new Intent(context,DetailedActivity.class);
                context.startActivity(i);
            }
        });
        holder.alertDialog=holder.builder.create();
        holder.mClost_btn.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(context,holder.editText.getText().toString()+"",Toast.LENGTH_LONG).show();
                holder.alertDialog.dismiss();
            }
        });
    holder.alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public int getItemCount()
    {
        return mTours.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img1, img2, img3;
        TextView mTitle, mDescription, mRating, mComments;
        ImageButton mShare_btn,mComment_btn,mRate_btn,mClost_btn;
        Button post_btn;
        LayoutInflater vi;
        View mView;
        EditText editText;
        AlertDialog.Builder builder;
        AlertDialog alertDialog;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ImageViewHolder(View itemView)
        {
            super(itemView);
             vi = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             mView=vi.inflate(R.layout.custom_rating_dialog,null,false);
             post_btn=mView.findViewById(R.id.post_btn);
             mClost_btn=mView.findViewById(R.id.close_btn);
             editText=mView.findViewById(R.id.comment_post);
            builder=new AlertDialog.Builder(itemView.getContext());
            builder.setView(mView);
            mTitle = itemView.findViewById(R.id.title_txt);
            mDescription = itemView.findViewById(R.id.desc);
            mRating = itemView.findViewById(R.id.rate_num);
            mComments = itemView.findViewById(R.id.comment_num);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            mShare_btn=itemView.findViewById(R.id.share_btn);
            mComment_btn=itemView.findViewById(R.id.comment_btn);
            mRate_btn=itemView.findViewById(R.id.star);
        }

    }
}
