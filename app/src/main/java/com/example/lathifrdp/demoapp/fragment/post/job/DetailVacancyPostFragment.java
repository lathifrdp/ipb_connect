package com.example.lathifrdp.demoapp.fragment.post.job;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Job;
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

public class DetailVacancyPostFragment extends Fragment{
    Bundle bundle;
    private String id_vacancy;
    TextView judul, alamat, gajimin,gajimax,deskripsi,profil, nama,angkatan, prodi,comp,det_alamat,kualifikasi,enddate,mail,sub,berkas,jobloc;
    CircleImageView foto;
    ApiInterface apiService;
    SessionManager sessionManager;
    public String strmin,strmax;
    public int numbermin,numbermax;
    FloatingActionButton fab_ubah, fab_hapus;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_vacancy_post,null);
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

        fab_ubah = (FloatingActionButton) getView().findViewById(R.id.edit_vacancy_post);
        fab_hapus = (FloatingActionButton) getView().findViewById(R.id.delete_vacancy_post);
        fab_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment createFragment = null;
                createFragment = new EditVacancyFragment();
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
                        Toast.makeText(getActivity(), "Ya di klik", Toast.LENGTH_SHORT).show();
//                        alertDialog.dismiss();
                        pd = new ProgressDialog(getActivity());
                        pd.setMessage("Menghapus data...");
                        pd.setCancelable(false);
                        pd.show();
                        deleteJob();
                    }
                });

                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "Tidak di klik", Toast.LENGTH_SHORT).show();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
                Toast.makeText(getActivity(), "hapus", Toast.LENGTH_SHORT).show();
            }
        });

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

    private void deleteJob(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<DeleteResponse> ucall = apiService.deleteJob("JWT "+ sessionManager.getKeyToken(),id_vacancy);
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
                        newFragment = new PostJobFragment();
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
