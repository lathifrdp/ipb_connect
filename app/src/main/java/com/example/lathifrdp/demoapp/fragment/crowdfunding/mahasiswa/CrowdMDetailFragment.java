package com.example.lathifrdp.demoapp.fragment.crowdfunding.mahasiswa;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.DonationList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.model.Donation;
import com.example.lathifrdp.demoapp.response.DonationResponse;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrowdMDetailFragment extends Fragment {

    Bundle bundle;
    private String id_crowd;
    TextView judul, lokasi, curr, tot, deskripsi, kontak, nama, angkatan, prodi,proyek, kosong;
    CircleImageView foto;
    ApiInterface apiService;
    SessionManager sessionManager;
    Button btn;
    public List<Donation> donationList;
    public RecyclerView recyclerViewDon;
    DonationList donationAdapter;

    public static CrowdMDetailFragment newInstance(String id) {

        Bundle args = new Bundle();
        CrowdMDetailFragment fragment = new CrowdMDetailFragment();
        args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_crowd_mahasiswa,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Proposal");
        sessionManager = new SessionManager(getActivity());
        judul = (TextView) getView().findViewById(R.id.judul_crowd_mahasiswa);
        lokasi = (TextView) getView().findViewById(R.id.lokasi_crowd_mahasiswa);
        curr = (TextView) getView().findViewById(R.id.donasi_crowd_mahasiswa);
        deskripsi = (TextView) getView().findViewById(R.id.deskripsi_crowd_mahasiswa);
        kontak = (TextView) getView().findViewById(R.id.kontak_crowd_mahasiswa);
        nama = (TextView) getView().findViewById(R.id.namanya);
        angkatan = (TextView) getView().findViewById(R.id.angkatannya);
        prodi = (TextView) getView().findViewById(R.id.studinya);
        foto = (CircleImageView) getView().findViewById(R.id.fotonya);
        proyek = (TextView) getView().findViewById(R.id.project_crowd_mahasiswa);
        btn = (Button) getView().findViewById(R.id.crowd_donatur);
        tot = (TextView) getView().findViewById(R.id.donasi_total_crowd_mahasiswa);

        bundle = this.getArguments();

        if(bundle != null){
            id_crowd = bundle.getString("id");
            loadDataDetail();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                LayoutInflater inflater = getActivity().getLayoutInflater();
                alertDialog.setTitle("Donatur");
                //alertDialog.setView(inflater.inflate(R.layout.fragment_crowd_donasi, null));
                View dialogview = inflater.inflate(R.layout.fragment_crowd_donatur, null);
                alertDialog.setView(dialogview);

                kosong = (TextView) dialogview.findViewById(R.id.donatur_kosong);
                recyclerViewDon= (RecyclerView) dialogview.findViewById(R.id.recy_donatur);
                recyclerViewDon.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewDon.setHasFixedSize(true);
                loadDonation();

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "OK di klik", Toast.LENGTH_SHORT).show();
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

        Call<Crowdfunding> call = apiService.getDetailCrowd("JWT "+ sessionManager.getKeyToken(),id_crowd);
        call.enqueue(new Callback<Crowdfunding>() {
            @Override
            public void onResponse(Call<Crowdfunding> call, final Response<Crowdfunding> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    Crowdfunding crowd = response.body();

                    int sekarang = Integer.parseInt(crowd.getCurrentCost());
                    int total = Integer.parseInt(crowd.getTotalCost());
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                    lokasi.setText(crowd.getLocation());
                    judul.setText(crowd.getTitle());
                    deskripsi.setText(crowd.getDescription());
                    kontak.setText(crowd.getContactPerson());
                    nama.setText(crowd.getUser().getFullName());
                    prodi.setText(crowd.getUser().getStudyProgram().getName());
                    angkatan.setText("Angkatan "+crowd.getUser().getBatch());
                    curr.setText(formatRupiah.format((double)sekarang));
                    proyek.setText(crowd.getProjectType());
                    tot.setText(formatRupiah.format((double)total));

                    String url = new BaseModel().getProfileUrl()+crowd.getUser().getUserProfile().getPhoto();
                    Picasso.get()
                            .load(url)
                            .placeholder(R.drawable.placegam)
                            .error(R.drawable.placeholdergambar)
                            .into(foto);
                }
            }

            @Override
            public void onFailure(Call<Crowdfunding> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadDonation(){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<DonationResponse> call = apiService.getDonation("JWT "+ sessionManager.getKeyToken(),id_crowd);
        call.enqueue(new Callback<DonationResponse>() {
            @Override
            public void onResponse(Call<DonationResponse> call, final Response<DonationResponse> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    DonationResponse dr = response.body();
                    donationList = dr.getDonations();
                    donationAdapter = new DonationList(donationList);
                    recyclerViewDon.setAdapter(donationAdapter);
                    //recyclerViewCom.smoothScrollToPosition(0);
                    int total = dr.getTotal();
                    if(total == 0){
                        kosong.setText("Belum ada donatur");
                    }
                }
            }

            @Override
            public void onFailure(Call<DonationResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}
