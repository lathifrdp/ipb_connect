package com.example.lathifrdp.demoapp.fragment.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Event;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventFragment extends Fragment {

    Bundle bundle;
    private String id_event;
    TextView judul, alamat, harga, deskripsi, kontak;
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
                    harga.setText("Rp "+str);
                    deskripsi.setText(event.getDescription());
                    kontak.setText(event.getContact());
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
