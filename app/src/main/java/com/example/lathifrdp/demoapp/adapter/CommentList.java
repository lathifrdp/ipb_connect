package com.example.lathifrdp.demoapp.adapter;

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
import com.example.lathifrdp.demoapp.fragment.memories.DetailMemoriesFragment;
import com.example.lathifrdp.demoapp.model.Comment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class CommentList extends RecyclerView.Adapter<CommentList.MyViewHolder>{
    private List<Comment> commentList;

    public void setList(List<Comment> list) {
        if(commentList==null) commentList=new ArrayList<>();
        this.commentList = list;
    }
    public void addList(List<Comment> list) {
        if(commentList==null) commentList=new ArrayList<>();
        this.commentList.addAll(list);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView value,tanggal,nama;
        public CircleImageView photo;

        public MyViewHolder(View view) {
            super(view);
            value = (TextView) view.findViewById(R.id.value_comment);
            tanggal = (TextView) view.findViewById(R.id.tanggal_comment);
            nama = (TextView) view.findViewById(R.id.nama_comment);
            //photo = (CircleImageView) view.findViewById(R.id.foto_comment);
        }
    }


    public CommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_comment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Comment comment = commentList.get(position);
        holder.nama.setText(comment.getUser().getFullName());
            holder.value.setText(comment.getValue());

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {
                date = inputFormat.parse(comment.getCreated());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDate = outputFormat.format(date);

            holder.tanggal.setText(formattedDate);

            //holder.photo.setImageBitmap(null);
            //Picasso.get().cancelRequest(holder.photo);
//            String url = "http://api.ipbconnect.cs.ipb.ac.id/uploads/profile/" + comment.getUser().getUserProfile().getPhoto();
//            Picasso.get().load(url).placeholder(R.drawable.logoipb).error(R.drawable.alumni2).into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
