package com.example.lathifrdp.demoapp.fragment.job;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.JobLocationSpinner;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.JobLocation;
import com.example.lathifrdp.demoapp.response.PostJobResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateVacancyFragment extends Fragment {

    EditText judul, nama, alamat, gajimin, gajimax, tglakhir, profil, kualifikasi, deskripsi, email2, subjek,keterangan;
    //private Spinner spinner;
    private JobLocationSpinner adapterLocation;
    public String jobLocationId;
    ApiInterface apiService;
    SessionManager sessionManager;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String closeDate;
    Button btn;
    public String title,subject,company,email,companyProfile,jobQualification,jobDescription,salaryMax,salaryMin,file,address;
    ProgressDialog pd;
    TextView lokasinya, pilih_lokasi;
    SpinnerDialog spinnerDialog;
    ArrayList<String> items=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_vacancy,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Buat Lowongan");
        sessionManager = new SessionManager(getActivity());

        judul = (EditText) getView().findViewById(R.id.judul);
        nama = (EditText) getView().findViewById(R.id.nama_perusahaan);
        alamat = (EditText) getView().findViewById(R.id.alamat_perusahaan);
        gajimin = (EditText) getView().findViewById(R.id.gaji_min);
        gajimax = (EditText) getView().findViewById(R.id.gaji_max);
        profil = (EditText) getView().findViewById(R.id.profil_perusahaan);
        kualifikasi = (EditText) getView().findViewById(R.id.kualifikasi);
        deskripsi = (EditText) getView().findViewById(R.id.deskripsi_pekerjaan);
        email2 = (EditText) getView().findViewById(R.id.email_vc);
        subjek = (EditText) getView().findViewById(R.id.email_subject_vc);
        keterangan = (EditText) getView().findViewById(R.id.berkas_lamaran);
        btn = (Button) getView().findViewById(R.id.submit_vc);
        lokasinya = (TextView) getView().findViewById(R.id.lokasinya);
        pilih_lokasi = (TextView) getView().findViewById(R.id.lokasiCombo);

        loadDataLocation();
        getTanggalAkhir();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Membuat Lowongan...");
                pd.setCancelable(false);
                pd.show();
                postJob();
            }
        });
        pilih_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });

    }
    private void loadDataLocation(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<JobLocation>> call = apiService.getLocation("JWT "+ sessionManager.getKeyToken());
        call.enqueue(new Callback<List<JobLocation>>() {
            @Override
            public void onResponse(Call<List<JobLocation>> call, Response<List<JobLocation>> response) {
                if (response.isSuccessful()) {
                    final List<JobLocation> alllokasi = response.body();
                    jobLocationId = "";
                    lokasinya.setText("Belum memilih lokasi");
                    items.clear();
                    for(int x=0;x<alllokasi.size();x++){
                        items.add(alllokasi.get(x).getName());
                    }

                    spinnerDialog=new SpinnerDialog(getActivity(),items,"Pilih Lokasi",R.style.DialogAnimations_SmileWindow,"Tutup");
                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {

                            for(int x=0;x<alllokasi.size();x++){
                                if(item.equals(alllokasi.get(x).getName())) {
                                    jobLocationId = alllokasi.get(x).getId();
                                }
                            }
                            lokasinya.setText(item);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<JobLocation>> call, Throwable t) {

            }
        });
    }
    public void getTanggalAkhir(){
        tglakhir = (EditText) getView().findViewById(R.id.tanggal_berakhir);

        tglakhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "-" + month + "-" + year;
                closeDate = year + "-" + month + "-" + day;
                tglakhir.setText(date);
            }
        };
    }

    public void postJob(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        final String createdBy = sessionManager.getKeyId();
//        if(TextUtils.isEmpty(judul.getText())){
//            judul.setError("Judul lowongan harus diisi");
//        }
        //else{
            title = judul.getText().toString();
        //}
        //if(TextUtils.isEmpty(nama.getText())){
            judul.setError("Nama perusahaan harus diisi");
        //}
        //else{
            company = nama.getText().toString();
        //}

        address = alamat.getText().toString();
        salaryMin = gajimin.getText().toString();
        salaryMax = gajimax.getText().toString();
        companyProfile = profil.getText().toString();
        jobQualification = kualifikasi.getText().toString();
        jobDescription = deskripsi.getText().toString();
        email = email2.getText().toString();
        subject = subjek.getText().toString();
        file = keterangan.getText().toString();

        Call<PostJobResponse> ucall = apiService.postJob("JWT "+ sessionManager.getKeyToken(), title, company, jobLocationId, address, salaryMin, salaryMax, closeDate, companyProfile,jobQualification,jobDescription,email,subject,file,createdBy);
        ucall.enqueue(new Callback<PostJobResponse>() {
            @Override
            public void onResponse(Call<PostJobResponse> call, Response<PostJobResponse> response) {

                if (response.isSuccessful()) {

                    PostJobResponse pjr = response.body();

                    if(pjr.isSuccess()==false ){
                        Snackbar.make(getView(), pjr.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        Snackbar.make(getView(), pjr.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Fragment newFragment = null;
                        newFragment = new JobFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PostJobResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
