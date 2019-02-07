package com.example.lathifrdp.demoapp.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.fragment.crowdfunding.mahasiswa.CrowdCreateProgressFragment;
import com.example.lathifrdp.demoapp.fragment.post.crowdfunding.CrowdEditFragment;
import com.example.lathifrdp.demoapp.fragment.post.crowdfunding.PostCrowdFragment;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.response.DeleteResponse;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrowdMahasiswaPostList extends ArrayAdapter<Crowdfunding> implements View.OnClickListener{
    private List<Crowdfunding> dataSet;
    Context mContext;
    ProgressDialog pd;
    ApiInterface apiService;
    SessionManager sessionManager;
    String id_crowd;

    public void setList(List<Crowdfunding> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet = list;
    }
    public void addList(List<Crowdfunding> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet.addAll(list);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        TextView txtCurrent;
        TextView txtTotal;
        TextView txtDescription;
        TextView txtStatus;

        ImageView gambar;
        ImageView progress;
        ImageView ubah;
        ImageView hapus;

    }

    public CrowdMahasiswaPostList(List<Crowdfunding> data, Context context) {
        super(context, R.layout.row_item_cr_mhs_post, data);
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
        Crowdfunding dataModel=(Crowdfunding) object;
        Bundle bundle;
        id_crowd = dataModel.getId();

        switch (v.getId())
        {
            case R.id.tambah_progress:
                Snackbar.make(v, "progress ", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

//                bundle = new Bundle();
//                Fragment progressFragment = null;
//                progressFragment = new CrowdCreateProgressFragment();
//
//                bundle.putString("id",dataModel.getId());
//                progressFragment.setArguments(bundle);
//
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                FragmentManager fragmentManager = activity.getSupportFragmentManager();
//                FragmentTransaction ft = fragmentManager.beginTransaction();
//                ft.replace(R.id.screen_area, progressFragment);
//                ft.addToBackStack(null);
//                ft.commit();
                break;
            case R.id.ubah_crowd:
                Snackbar.make(v, "ubah ", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                bundle = new Bundle();
                Fragment editFragment = null;
                editFragment = new CrowdEditFragment();

                bundle.putString("id",dataModel.getId());
                editFragment.setArguments(bundle);

                AppCompatActivity editactivity = (AppCompatActivity) v.getContext();
                FragmentManager editfragmentManager = editactivity.getSupportFragmentManager();
                FragmentTransaction editft = editfragmentManager.beginTransaction();
                editft.replace(R.id.screen_area, editFragment);
                editft.addToBackStack(null);
                editft.commit();
                break;
            case R.id.hapus_crowd:
                Snackbar.make(v, id_crowd, Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                final AppCompatActivity hapusactivity = (AppCompatActivity) v.getContext();

                final AlertDialog alertDialog = new AlertDialog.Builder(hapusactivity).create();
                alertDialog.setTitle("Hapus");
                alertDialog.setMessage("Apakah anda yakin untuk menghapus data "+dataModel.getTitle()+"?");

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        alertDialog.dismiss();
                        //ProgressDialog pd;
                        pd = new ProgressDialog(hapusactivity);
                        pd.setMessage("Menghapus data...");
                        pd.setCancelable(false);
                        pd.show();
                        deleteCrowd();
                    }
                });

                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                // Showing Alert Message
                alertDialog.show();
                break;
        }
    }

    private void deleteCrowd(){

        final AppCompatActivity activitynya = (AppCompatActivity) getContext();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(activitynya);

        Call<DeleteResponse> ucall = apiService.deleteCrowd("JWT "+ sessionManager.getKeyToken(),id_crowd);
        ucall.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                if (response.isSuccessful()) {

                    DeleteResponse mr = response.body();

                    if(mr.isSuccess()==false ){
                        Toast.makeText(activitynya, mr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(activitynya, mr.getMessage(), Toast.LENGTH_SHORT).show();
                        Fragment newFragment = null;
                        newFragment = new PostCrowdFragment();
                        FragmentManager hapusfragmentManager = activitynya.getSupportFragmentManager();
                        FragmentTransaction hapusft = hapusfragmentManager.beginTransaction();
                        hapusft.replace(R.id.screen_area, newFragment);
                        hapusft.addToBackStack(null);
                        hapusft.commit();
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(activitynya, "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Crowdfunding dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        CrowdMahasiswaPostList.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new CrowdMahasiswaPostList.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_cr_mhs_post, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.title_cr);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.desc_cr);
            viewHolder.txtCurrent = (TextView) convertView.findViewById(R.id.current_cr);
            viewHolder.txtTotal = (TextView) convertView.findViewById(R.id.total_cr);
            viewHolder.gambar = (ImageView) convertView.findViewById(R.id.gambar_cr);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.status_cr);
            viewHolder.progress = (ImageView) convertView.findViewById(R.id.tambah_progress);
            viewHolder.ubah = (ImageView) convertView.findViewById(R.id.ubah_crowd);
            viewHolder.hapus = (ImageView) convertView.findViewById(R.id.hapus_crowd);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CrowdMahasiswaPostList.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;


        int current = Integer.parseInt(dataModel.getCurrentCost());
        int total = Integer.parseInt(dataModel.getTotalCost());

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtDescription.setText(dataModel.getDescription());
        viewHolder.txtCurrent.setText("Telah terkumpul "+formatRupiah.format((double)current));
        viewHolder.txtTotal.setText("Dari target "+formatRupiah.format((double)total));
        if(dataModel.isVerified() == true) {
            viewHolder.txtStatus.setText("Sudah diverifikasi");
            viewHolder.txtStatus.setBackgroundColor(Color.parseColor("#2db4aa"));
            viewHolder.progress.setVisibility(View.VISIBLE);
        }
        else if(dataModel.isVerified() == false) {
            viewHolder.txtStatus.setText("Belum diverifikasi");
            viewHolder.txtStatus.setBackgroundColor(Color.parseColor("#d63030"));
            viewHolder.progress.setVisibility(View.GONE);
        }

        String url = new BaseModel().getCrowdfundingUrl()+dataModel.getProposalImages().get(0).getFile();
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.placegam)
                .error(R.drawable.placeholdergambar)
                .into(viewHolder.gambar);
        viewHolder.progress.setOnClickListener(this);
        viewHolder.progress.setTag(position);
        viewHolder.ubah.setOnClickListener(this);
        viewHolder.ubah.setTag(position);
        viewHolder.hapus.setOnClickListener(this);
        viewHolder.hapus.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
