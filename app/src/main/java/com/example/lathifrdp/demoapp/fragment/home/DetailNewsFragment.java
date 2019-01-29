package com.example.lathifrdp.demoapp.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient2;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.News;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailNewsFragment extends Fragment {

    Bundle bundle;
    private String id_news;
    TextView judul, narasumber, lokasi, isi, tanggal;
    ImageView gambar;
    ApiInterface apiService;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_news,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Berita");
        sessionManager = new SessionManager(getActivity());
        judul = (TextView) getView().findViewById(R.id.judul_news_detail);
        narasumber = (TextView) getView().findViewById(R.id.narasumber_news_detail);
        lokasi = (TextView) getView().findViewById(R.id.lokasi_news_detail);
        isi = (TextView) getView().findViewById(R.id.isi_news_detail);
        tanggal = (TextView) getView().findViewById(R.id.tanggal_news_detail);
        gambar = (ImageView) getView().findViewById(R.id.gambar_news_detail);

        bundle = this.getArguments();

        if(bundle != null){
            id_news = bundle.getString("id");
            loadDataDetail();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDataDetail(){
        apiService = ApiClient2.getClient().create(ApiInterface.class);
        final String language = "ID";

        Call<News> call = apiService.getNewsDetail("Bearer 4857d8cb-af10-3ceb-91f3-fe025244e9eb",language,id_news);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, final Response<News> response) {
                if (response.isSuccessful()) {

                    News news = response.body();
                    judul.setText(news.getJudul());
                    narasumber.setText(news.getNarasumber());
                    lokasi.setText(news.getLokasi());
                    isi.setText(Html.fromHtml(news.getIsi()));

                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

                    Date date = null;
                    try {
                        date = inputFormat.parse(news.getTanggal());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String formattedDate = outputFormat.format(date);
                    tanggal.setText(formattedDate);

                    String url = news.getImage();
                    Picasso.get()
                            .load(url)
                            .placeholder(R.drawable.placegam)
                            .error(R.drawable.placeholdergambar)
                            .into(gambar);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
