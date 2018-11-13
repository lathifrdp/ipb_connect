package com.example.lathifrdp.demoapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.model.Comment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentKnowledgeList extends RecyclerView.Adapter<CommentKnowledgeList.MyViewHolder>{
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


    public CommentKnowledgeList(List<Comment> commentList) {
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
            holder.tanggal.setText(comment.getCreated());

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
