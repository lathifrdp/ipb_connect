package com.example.lathifrdp.demoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.model.KnowledgeSharing;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{
    private List<KnowledgeSharing> movies;
    private Context context;
    // private Movie movie;

    public MoviesAdapter(Context applicationContext, List<KnowledgeSharing> movieArrayList) {
        this.context =applicationContext;
        this.movies=movieArrayList;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_movie, viewGroup, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(movies.get(i).getTitle());
        viewHolder.actors.setText(movies.get(i).getTotalSlide());
        viewHolder.year.setText(movies.get(i).getCreated());

        // This is how we use Picasso to load images from the internet.
//        Picasso.with(context)
//                .load(movies.get(i).getCover())
//                .placeholder(R.drawable.load)
//                .into(viewHolder.imageView);
        String url = "http://182.23.70.28:3501/uploads/knowledgesharing/"+movies.get(i).getCover();
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.logoipb)
                .error(R.drawable.alumni2)
                .into(viewHolder.imageView);

        //viewHolder.imageView.setImageURI(movies.get(i).getCover());
    }
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, year, actors;
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            year = (TextView) view.findViewById(R.id.year);
            actors = (TextView) view.findViewById(R.id.actors);
            imageView= (ImageView) view.findViewById(R.id.cover);

            // on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        KnowledgeSharing clickedDataItem = movies.get(pos);
//                        Intent intent = new Intent(context, .class);
//                        intent.putExtra("movie_title", movies.get(pos).getTitle());
//                        intent.putExtra("movie_actors", movies.get(pos).getActors());
//                        intent.putExtra("movie_cover", movies.get(pos).getCover());
//                        intent.putExtra("movie_director", movies.get(pos).getDirector());
//                        intent.putExtra("movie_year", movies.get(pos).getYear());
//                        intent.putExtra("movie_plot", movies.get(pos).getPlot());
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /* Within the RecyclerView.Adapter class */
    // Clean all elements of the recycler
    public void clear() {
        movies.clear();
        notifyDataSetChanged();
    }

    //RecyclerView mRecycler;
    // Add a list of ites
    public void addAll(int position, List<KnowledgeSharing> mov) {
        movies.addAll(0,mov);
        notifyItemInserted(0);
        //mRecycler.smoothScrollToPosition(0);
        notifyDataSetChanged();
    }
}
