package com.example.lathifrdp.demoapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.model.Comment;
import com.example.lathifrdp.demoapp.model.Donation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonationList extends RecyclerView.Adapter<DonationList.MyViewHolder>{
    private List<Donation> donationList;

    public void setList(List<Donation> list) {
        if(donationList==null) donationList=new ArrayList<>();
        this.donationList = list;
    }
    public void addList(List<Donation> list) {
        if(donationList==null) donationList=new ArrayList<>();
        this.donationList.addAll(list);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama;
        public CircleImageView photo;

        public MyViewHolder(View view) {
            super(view);
            nama = (TextView) view.findViewById(R.id.nama_donation);
            //photo = (CircleImageView) view.findViewById(R.id.foto_comment);
        }
    }


    public DonationList(List<Donation> donationList) {
        this.donationList = donationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_donation, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Donation donation = donationList.get(position);
        holder.nama.setText(donation.getCreatedBy().getFullName());


            //holder.photo.setImageBitmap(null);
            //Picasso.get().cancelRequest(holder.photo);
//            String url = "http://api.ipbconnect.cs.ipb.ac.id/uploads/profile/" + comment.getUser().getUserProfile().getPhoto();
//            Picasso.get().load(url).placeholder(R.drawable.logoipb).error(R.drawable.alumni2).into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }
}
