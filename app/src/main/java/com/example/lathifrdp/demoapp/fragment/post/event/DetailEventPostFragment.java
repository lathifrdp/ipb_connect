package com.example.lathifrdp.demoapp.fragment.post.event;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Event;
import com.example.lathifrdp.demoapp.response.DeleteResponse;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventPostFragment extends Fragment {

    Bundle bundle;
    private String id_event;
    TextView judul, alamat, harga, deskripsi, kontak, nama, angkatan, prodi,startdate,enddate,starttime,endtime;
    ImageView putu;
    CircleImageView foto;
    ApiInterface apiService;
    SessionManager sessionManager;
    FloatingActionButton fab_ubah, fab_hapus;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_event_post,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Acara");
        sessionManager = new SessionManager(getActivity());
        judul = (TextView) getView().findViewById(R.id.judul_event);
        alamat = (TextView) getView().findViewById(R.id.alamat);
        harga = (TextView) getView().findViewById(R.id.harga);
        deskripsi = (TextView) getView().findViewById(R.id.deskripsinya);
        kontak = (TextView) getView().findViewById(R.id.kontaknya);
        nama = (TextView) getView().findViewById(R.id.namanya);
        angkatan = (TextView) getView().findViewById(R.id.angkatannya);
        prodi = (TextView) getView().findViewById(R.id.studinya);
        foto = (CircleImageView) getView().findViewById(R.id.fotonya);
        putu = (ImageView) getView().findViewById(R.id.foto_poster);
        startdate = (TextView) getView().findViewById(R.id.startdate_et);
        enddate = (TextView) getView().findViewById(R.id.enddate_et);
        starttime = (TextView) getView().findViewById(R.id.starttime_et);
        endtime = (TextView) getView().findViewById(R.id.endtime_et);

        bundle = this.getArguments();

        if(bundle != null){
            id_event = bundle.getString("id");
            loadDataDetail();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }

        fab_ubah = (FloatingActionButton) getView().findViewById(R.id.edit_event_post);
        fab_hapus = (FloatingActionButton) getView().findViewById(R.id.delete_event_post);
        fab_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment createFragment = null;
                createFragment = new EditEventFragment();
                createFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, createFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                //Toast.makeText(getActivity(), "ubah", Toast.LENGTH_SHORT).show();
            }
        });

        fab_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment createFragment = null;
//                createFragment = new CreateVacancyFragment();
//                createFragment.setArguments(bundle);
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.screen_area, createFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Hapus");
                alertDialog.setMessage("Apakah anda yakin untuk menghapus data ini?");

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        alertDialog.dismiss();
                        pd = new ProgressDialog(getActivity());
                        pd.setMessage("Menghapus data...");
                        pd.setCancelable(false);
                        pd.show();
                        deleteEvent();
                    }
                });

                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });

    }

    private void loadDataDetail(){

        //spinner = (Spinner) getView().findViewById(R.id.prodiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);

        //final String fullName = full;
        //final String isVerified = x3;

        Call<Event> call = apiService.getDetailEvent("JWT "+ sessionManager.getKeyToken(),id_event);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, final Response<Event> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    Event event = response.body();
                    int number = Integer.parseInt(event.getPrice());
                    String str = NumberFormat.getNumberInstance(Locale.US).format(number);
                    alamat.setText(event.getPlace());
                    judul.setText(event.getTitle());
                    deskripsi.setText(event.getDescription());
                    kontak.setText(event.getContact());
                    nama.setText(event.getUser().getFullName());
                    prodi.setText(event.getUser().getStudyProgram().getName());
                    angkatan.setText("Angkatan "+event.getUser().getBatch());

                    if(number == 0){
                        harga.setText("Gratis");
                    }
                    else{
                        harga.setText("Rp "+str);
                    }

                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

                    Date awaldate = null;
                    try {
                        awaldate = inputFormat.parse(event.getStartDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String formattedstartDate = outputFormat.format(awaldate);
                    startdate.setText(formattedstartDate);

                    Date akhirdate = null;
                    try {
                        akhirdate = inputFormat.parse(event.getEndDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String formattedendDate = outputFormat.format(akhirdate);
                    enddate.setText(formattedendDate);

                    starttime.setText("Pukul "+event.getStartTime());
                    if(event.getEndTime().equals("")){
                        endtime.setText("selesai");
                    }
                    else{
                        endtime.setText(event.getEndTime());
                    }

                    String url2 = new BaseModel().getEventUrl()+event.getPicture();
                    Picasso.get()
                            .load(url2)
                            .placeholder(R.drawable.placegam)
                            .error(R.drawable.placeholdergambar)
                            .into(putu);

                    String url = new BaseModel().getProfileUrl()+event.getUser().getUserProfile().getPhoto();
                    Picasso.get()
                            .load(url)
                            .placeholder(R.drawable.placegam)
                            .error(R.drawable.placeholdergambar)
                            .into(foto);
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void deleteEvent(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<DeleteResponse> ucall = apiService.deleteEvent("JWT "+ sessionManager.getKeyToken(),id_event);
        ucall.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                if (response.isSuccessful()) {

                    DeleteResponse mr = response.body();

                    if(mr.isSuccess()==false ){
                        Snackbar.make(getView(), mr.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        Snackbar.make(getView(), mr.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Fragment newFragment = null;
                        newFragment = new PostEventFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
