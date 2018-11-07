package com.example.lathifrdp.demoapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.model.Memory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MemoriesList extends RecyclerView.Adapter<MemoriesList.MyViewHolder>{
    private List<Memory> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView caption;
        public ImageView photo;

        public MyViewHolder(View view) {
            super(view);
            caption = (TextView) view.findViewById(R.id.textViewImageName);
            photo = (ImageView) view.findViewById(R.id.imageViewThumbnail);
        }
    }


    public MemoriesList(List<Memory> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_memories, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Memory movie = moviesList.get(position);
        holder.caption.setText(movie.getCaption());
        holder.photo.setImageBitmap(null);
        Picasso.get().cancelRequest(holder.photo);
        String url = "http://api.ipbconnect.cs.ipb.ac.id/uploads/memory/"+movie.getPhoto();
        Picasso.get().load(url).placeholder(R.drawable.logoipb).error(R.drawable.alumni2).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
