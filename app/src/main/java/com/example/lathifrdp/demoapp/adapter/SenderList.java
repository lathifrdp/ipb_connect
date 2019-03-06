package com.example.lathifrdp.demoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.model.Message;
import com.example.lathifrdp.demoapp.model.TracerStudy;
import com.example.lathifrdp.demoapp.response.SenderResponse;

import java.util.ArrayList;
import java.util.List;

public class SenderList extends ArrayAdapter<SenderResponse> implements View.OnClickListener {
    private List<SenderResponse> dataSet;
    Context mContext;

    public void setList(List<SenderResponse> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet = list;
    }
    public void addList(List<SenderResponse> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet.addAll(list);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtPengirim;
        TextView txtPesan;
        TextView txtCreated;
    }

    public SenderList(List<SenderResponse> data, Context context) {
        super(context, R.layout.row_item_inbox, data);
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
        SenderResponse dataModel=(SenderResponse) object;

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
        // Get the data item for this position
        SenderResponse dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_inbox, parent, false);
            viewHolder.txtPengirim = (TextView) convertView.findViewById(R.id.sender_inbox);
            viewHolder.txtPesan = (TextView) convertView.findViewById(R.id.message_inbox);
            viewHolder.txtCreated = (TextView) convertView.findViewById(R.id.created_inbox);
            //viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        int i = dataModel.getMessages().size();

        viewHolder.txtPengirim.setText(dataModel.getSender().getFullName());
        viewHolder.txtPesan.setText(dataModel.getMessages().get(i-1).getMessage());
        viewHolder.txtCreated.setText(dataModel.getMessages().get(i-1).getCreated());
//        viewHolder.info.setOnClickListener(this);
//        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
