package com.example.manik.recybook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecAdap extends RecyclerView.Adapter<RecAdap.ViewHolder>  {

    private List<BookObject> list;
    private Context context;
    private int mExpandedPosition = -1;
    private int previousExpandedPosition = -1;

    public RecAdap(List<BookObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.one_book,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        BookObject bookObject =list.get(position);
        holder.tvBookName.setText(bookObject.getmBookName());
        holder.tvAuthor.setText(bookObject.getmAuthorName());
        holder.tvDescription.setText(bookObject.getmDescription());
        holder.tvCategories.setText(bookObject.getmCategories());
        holder.rb.setRating((float) bookObject.getmRating());
        holder.tvPages.setText(String.valueOf(bookObject.getmPages()));


        Picasso.get()
                .load(bookObject.getmImgUrl())
                .resize(225,325)
                .into(holder.imageView);

        final boolean isExpanded = position==mExpandedPosition;
        holder.tvDescription.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.imageView.setVisibility(isExpanded?View.GONE:View.VISIBLE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = !isExpanded ? position : -1;
                notifyItemChanged(position);
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBookName;
        TextView tvAuthor;
        TextView tvDescription;
        TextView tvCategories;
        RatingBar rb;
        TextView tvPages;
        ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            tvBookName=v.findViewById(R.id.tvBookName);
            tvAuthor = v.findViewById(R.id.tvAuthor);
            tvDescription = v.findViewById(R.id.tvDescription);
            tvCategories = v.findViewById(R.id.tvCategories);
            rb = v.findViewById(R.id.ratingBar);
            tvPages = v.findViewById(R.id.tvPages);
            imageView=v.findViewById(R.id.imageView);
        }
    }
}
