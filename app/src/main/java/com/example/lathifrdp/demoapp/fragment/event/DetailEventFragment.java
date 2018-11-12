package com.example.lathifrdp.demoapp.fragment.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Event;
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

public class DetailEventFragment extends Fragment {

    Bundle bundle;
    private String id_event;
    TextView judul, alamat, harga, deskripsi, kontak, nama, angkatan, prodi,startdate,enddate,starttime,endtime;
    ImageView putu;
    CircleImageView foto;
    ApiInterface apiService;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_event,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Event");
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
                    if(event.getEndTime()==""){
                        endtime.setText("selesai");
                    }
                    else{
                        endtime.setText(event.getEndTime());
                    }

                    String url2 = "http://api.ipbconnect.cs.ipb.ac.id/uploads/event/"+event.getPicture();
                    Picasso.get()
                            .load(url2)
                            .placeholder(R.drawable.placegam)
                            .error(R.drawable.placeholdergambar)
                            .into(putu);

                    String url = "http://api.ipbconnect.cs.ipb.ac.id/uploads/profile/"+event.getUser().getUserProfile().getPhoto();
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
}
