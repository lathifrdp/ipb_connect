package com.example.lathifrdp.demoapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.fragment.sharing.DetailSharingFragment;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.model.KnowledgeSharing;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SharingAdapter extends RecyclerView.Adapter<SharingAdapter.ViewHolder>{
    private List<KnowledgeSharing> knowledgeSharings;
    private Context context;
    // private Movie movie;

    public SharingAdapter(Context applicationContext, List<KnowledgeSharing> sharingArrayList) {
        this.context =applicationContext;
        this.knowledgeSharings=sharingArrayList;
    }

    @Override
    public SharingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sharing, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SharingAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(knowledgeSharings.get(i).getTitle());
        viewHolder.slide.setText(knowledgeSharings.get(i).getTotalSlide()+" Slide");
        viewHolder.created.setText(knowledgeSharings.get(i).getCreated());
        viewHolder.filesize.setText(knowledgeSharings.get(i).getFileSize());

        // This is how we use Picasso to load images from the internet.
//        Picasso.with(context)
//                .load(movies.get(i).getCover())
//                .placeholder(R.drawable.load)
//                .into(viewHolder.imageView);
        String url = new BaseModel().getKnowledgeUrl()+knowledgeSharings.get(i).getCover();
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.placegam)
                .error(R.drawable.placeholdergambar)
                .into(viewHolder.imageView);

        //viewHolder.imageView.setImageURI(movies.get(i).getCover());
    }
    @Override
    public int getItemCount() {
        if(knowledgeSharings == null) knowledgeSharings = new ArrayList<>();
        return knowledgeSharings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, slide, created, filesize;
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            slide = (TextView) view.findViewById(R.id.slide);
            created = (TextView) view.findViewById(R.id.created);
            filesize = (TextView) view.findViewById(R.id.filesize);
            imageView= (ImageView) view.findViewById(R.id.cover);

            // on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        KnowledgeSharing clickedDataItem = knowledgeSharings.get(pos);
//                        Intent intent = new Intent(context, .class);
//                        intent.putExtra("movie_title", movies.get(pos).getTitle());
//                        intent.putExtra("movie_actors", movies.get(pos).getActors());
//                        intent.putExtra("movie_cover", movies.get(pos).getCover());
//                        intent.putExtra("movie_director", movies.get(pos).getDirector());
//                        intent.putExtra("movie_year", movies.get(pos).getYear());
//                        intent.putExtra("movie_plot", movies.get(pos).getPlot());
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);

                        Bundle bundle;
                        bundle = new Bundle();
                        Fragment newFragment = null;
                        newFragment = new DetailSharingFragment();

                        bundle.putString("id_sharing",clickedDataItem.getId());
                        newFragment.setArguments(bundle);

                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.screen_area, newFragment);
                        ft.addToBackStack(null);
                        ft.commit();

                        //Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getId(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /* Within the RecyclerView.Adapter class */
    // Clean all elements of the recycler
    public void clear() {
        knowledgeSharings.clear();
        notifyDataSetChanged();
    }

    //RecyclerView mRecycler;
    // Add a list of ites
    public void addAll(int position, List<KnowledgeSharing> mov) {
        knowledgeSharings.addAll(0,mov);
        notifyItemInserted(0);
        //mRecycler.smoothScrollToPosition(0);
        notifyDataSetChanged();
    }
}
