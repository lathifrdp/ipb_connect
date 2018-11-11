package com.example.lathifrdp.demoapp.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.fragment.memories.DetailMemoriesFragment;
import com.example.lathifrdp.demoapp.model.Memory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MemoriesList extends RecyclerView.Adapter<MemoriesList.MyViewHolder>{
    private List<Memory> memoryList;

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
        this.memoryList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_memories, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Memory memory = memoryList.get(position);
        holder.caption.setText(memory.getCaption());
        holder.photo.setImageBitmap(null);
        Picasso.get().cancelRequest(holder.photo);
        String url = "http://api.ipbconnect.cs.ipb.ac.id/uploads/memory/"+memory.getPhoto();
        Picasso.get().load(url).placeholder(R.drawable.placegam).error(R.drawable.alumni2).into(holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle;
                bundle = new Bundle();
                Fragment newFragment = null;
                newFragment = new DetailMemoriesFragment();

                bundle.putString("id_memory",memory.getId());
                newFragment.setArguments(bundle);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.screen_area, newFragment);
                ft.addToBackStack(null);
                ft.commit();

                Toast.makeText(view.getContext(), memory.getId() + " " + memory.getCaption(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return memoryList.size();
    }
}
