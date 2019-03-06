package com.example.lathifrdp.demoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.model.Message;
import com.example.lathifrdp.demoapp.response.SenderResponse;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InboxList extends ArrayAdapter<Message> implements View.OnClickListener {
    private List<Message> dataSet;
    Context mContext;

    public void setList(List<Message> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet = list;
    }
    public void addList(List<Message> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet.addAll(list);
    }

    private static class ViewHolder {
        TextView txtPesan;
        TextView txtCreated;
        ImageView gambar;
    }

    public InboxList(List<Message> data, Context context) {
        super(context, R.layout.row_item_detail_inbox, data);
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
        Message dataModel=(Message) object;

//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Email " +dataModel.getEmail(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_detail_inbox, parent, false);
            viewHolder.txtPesan = (TextView) convertView.findViewById(R.id.message_inbox);
            viewHolder.txtCreated = (TextView) convertView.findViewById(R.id.created_inbox);
            viewHolder.gambar = (ImageView) convertView.findViewById(R.id.gambar_inbox);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(dataModel.getCreated());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(date);

        viewHolder.txtPesan.setText(dataModel.getMessage());
        viewHolder.txtCreated.setText(formattedDate);

        if(dataModel.getPhotos().size() != 0){
            String url = new BaseModel().getMessageUrl()+dataModel.getPhotos().get(0);
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.placegam)
                    .error(R.drawable.placeholdergambar)
                    .into(viewHolder.gambar);
        }
//        if(dataModel.getPhotos().size() == 0){
//            viewHolder.gambar.setVisibility(View.GONE);
//        }

        return convertView;
    }
}
