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

public class DiscussionList extends RecyclerView.Adapter<DiscussionList.MyViewHolder>{
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

        public MyViewHolder(View view) {
            super(view);
            value = (TextView) view.findViewById(R.id.value_discuss);
            tanggal = (TextView) view.findViewById(R.id.tanggal_discuss);
            nama = (TextView) view.findViewById(R.id.nama_discuss);
            //photo = (CircleImageView) view.findViewById(R.id.foto_comment);
        }
    }
    public DiscussionList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public DiscussionList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_discussion, parent, false);

        return new DiscussionList.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiscussionList.MyViewHolder holder, int position) {
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
