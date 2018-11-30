package com.example.lathifrdp.demoapp.fragment.job;

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
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Job;
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

public class DetailVacancyFragment extends Fragment{
    Bundle bundle;
    private String id_vacancy;
    TextView judul, alamat, gajimin,gajimax,deskripsi,profil, nama,angkatan, prodi,comp,det_alamat,kualifikasi,enddate,mail,sub,berkas,jobloc;
    CircleImageView foto;
    ApiInterface apiService;
    SessionManager sessionManager;
    public String strmin,strmax;
    public int numbermin,numbermax;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_vacancy,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Lowongan");
        sessionManager = new SessionManager(getActivity());
        judul = (TextView) getView().findViewById(R.id.judul_vacancy);
        comp = (TextView) getView().findViewById(R.id.nama_perusahaan);
        alamat = (TextView) getView().findViewById(R.id.nama_tempat);
        det_alamat = (TextView) getView().findViewById(R.id.detail_tempat);
        gajimin = (TextView) getView().findViewById(R.id.gajimin);
        gajimax = (TextView) getView().findViewById(R.id.gajimax);
        profil = (TextView) getView().findViewById(R.id.profilenya);
        kualifikasi = (TextView) getView().findViewById(R.id.syaratnya);
        deskripsi = (TextView) getView().findViewById(R.id.jobdescnya);
        mail = (TextView) getView().findViewById(R.id.emailnya);
        sub = (TextView) getView().findViewById(R.id.subjectnya);
        berkas = (TextView) getView().findViewById(R.id.berkasnya);
        enddate = (TextView) getView().findViewById(R.id.close_date);

        nama = (TextView) getView().findViewById(R.id.nama_orang);
        angkatan = (TextView) getView().findViewById(R.id.angkatan_orang);
        prodi = (TextView) getView().findViewById(R.id.studi_orang);
        foto = (CircleImageView) getView().findViewById(R.id.orang);

        bundle = this.getArguments();

        if(bundle != null){
            id_vacancy = bundle.getString("id_vacancy");
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

        Call<Job> call = apiService.getDetailVacancy("JWT "+ sessionManager.getKeyToken(),id_vacancy);
        call.enqueue(new Callback<Job>() {
            @Override
            public void onResponse(Call<Job> call, final Response<Job> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    Job job = response.body();

                    if(job.getSalaryMin().length() > 8){
                        strmin = job.getSalaryMin();
                    }else{
                        numbermin = Integer.parseInt(job.getSalaryMin());
                        strmin = NumberFormat.getNumberInstance(Locale.US).format(numbermin);
                    }
                    if(job.getSalaryMax().length() > 8){
                        strmax = job.getSalaryMax();
                    }else{
                        numbermax = Integer.parseInt(job.getSalaryMax());
                        strmax = NumberFormat.getNumberInstance(Locale.US).format(numbermax);
                    }

                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = null;
                    try {
                        date = inputFormat.parse(job.getCloseDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String formattedDate = outputFormat.format(date);

                    det_alamat.setText(job.getAddress());
                    judul.setText(job.getTitle());
                    gajimin.setText("Rp "+strmin);
                    gajimax.setText("Rp "+strmax);
                    deskripsi.setText(job.getJobDescription());
                    comp.setText(job.getCompany());
                    sub.setText("Subyek: "+job.getSubject());
                    enddate.setText("Berakhir pada tanggal "+formattedDate);
                    mail.setText("Email: "+job.getEmail());
                    profil.setText(job.getCompanyProfile());
                    kualifikasi.setText(job.getJobQualification());
                    deskripsi.setText(job.getJobDescription());
                    alamat.setText(job.getJobLocation().getName());
                    berkas.setText(job.getFile());

                    nama.setText(job.getUser().getFullName());
                    prodi.setText(job.getUser().getStudyProgram().getName());
                    angkatan.setText("Angkatan "+job.getUser().getBatch());

                    String url = new BaseModel().getProfileUrl()+job.getUser().getUserProfile().getPhoto();
                    Picasso.get()
                            .load(url)
                            .placeholder(R.drawable.placegam)
                            .error(R.drawable.logoipb)
                            .into(foto);
                }
            }

            @Override
            public void onFailure(Call<Job> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
