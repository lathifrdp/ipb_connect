package com.example.lathifrdp.demoapp.adapter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.model.StudyProgram;


public class StudyProgramSpinner extends ArrayAdapter<StudyProgram>{

    LayoutInflater flater;
    private List<StudyProgram> list;

    public StudyProgramSpinner(Activity context, int resouceId, int textviewId, List<StudyProgram> list){
        super(context,resouceId,textviewId, list);
        this.list = list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    public StudyProgram getItem(int pos){
        if (list != null){
            return list.get(pos);
        }
        return null;
    }

    private View rowview(View convertView , int position){

        StudyProgram studyProgram = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.spinner_study_program, null, false);

            holder.tvTitle = (TextView) rowview.findViewById(R.id.prodi_sp);
            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }

        if (studyProgram != null && !TextUtils.isEmpty(studyProgram.getName()))
            holder.tvTitle.setText(studyProgram.getName());

        return rowview;
    }

    private class viewHolder{
        TextView tvTitle;
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        // On selecting a spinner item
//        //String item = parent.getItemAtPosition(position).toString();
//
//        // Showing selected spinner item
//        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
//        Toast.makeText(parent.getContext(), "Selected: ", Toast.LENGTH_LONG).show();
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//        Toast.makeText(getContext(), "nothing", Toast.LENGTH_LONG).show();
//    }

}
