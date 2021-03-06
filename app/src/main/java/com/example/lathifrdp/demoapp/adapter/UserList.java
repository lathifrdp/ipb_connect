package com.example.lathifrdp.demoapp.adapter;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserList extends ArrayAdapter<User> implements View.OnClickListener{

    private List<User> dataSet;
    Context mContext;

    public void setList(List<User> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet = list;
    }
    public void addList(List<User> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet.addAll(list);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtProdi;
        TextView txtAngkatan;
        //ImageView info;
        //ListView tes;
    }

    public UserList(List<User> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }
    public int getSize(){
        if(dataSet==null) dataSet=new ArrayList<>();
        return dataSet.size();

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        User dataModel=(User)object;

//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Email : " +dataModel.getEmail(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
////            case R.id.idnya:
////                Snackbar.make(v, "Nama : " +dataModel.getFullName(), Snackbar.LENGTH_LONG)
////                        .setAction("No action", null).show();
////                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtProdi = (TextView) convertView.findViewById(R.id.prodi);
            viewHolder.txtAngkatan = (TextView) convertView.findViewById(R.id.angkatan_angka);
            //viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);
            //viewHolder.tes = (ListView) convertView.findViewById(R.id.idnya);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getFullName());
        viewHolder.txtProdi.setText(dataModel.getStudyProgram().getName());
        viewHolder.txtAngkatan.setText(dataModel.getBatch().toString());
//        viewHolder.info.setOnClickListener(this);
//        viewHolder.info.setTag(position);
//        viewHolder.tes.setOnClickListener(this);
//        viewHolder.tes.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}

