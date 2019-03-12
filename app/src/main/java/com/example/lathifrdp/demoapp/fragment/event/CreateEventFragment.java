package com.example.lathifrdp.demoapp.fragment.event;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.response.PostEventResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventFragment extends Fragment {

    EditText judul, alamat, biaya, tglawal,tglakhir, wktawal,wktakhir, deskripsi, info;
    CheckBox cekbox_selesai,cekbox_biaya;
    ApiInterface apiService;
    SessionManager sessionManager;
    private DatePickerDialog.OnDateSetListener mDateSetListenerstart,mDateSetListenerend;
    //private String endDate2, startDate2;
    Button btn;
    ImageView gambar,gallery,camera;
    public String pathImage;
    //RecyclerView gambar;
    public File poto, compoto;
    public String title2,place2,startDate2,endDate2,startTime2,endTime2,description2,contact2,price2,createdBy2;
    public String title3,place3,startDate3,endDate3,startTime3,endTime3,description3,contact3,price3,createdBy3;
    ProgressDialog pd;
    public Boolean biayaState, selesaiState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_event,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Buat Acara");
        sessionManager = new SessionManager(getActivity());

        gambar = (ImageView) getView().findViewById(R.id.gambar_event);
        gallery = (ImageView) getView().findViewById(R.id.gallery_event);
        camera = (ImageView) getView().findViewById(R.id.camera_event);
        btn = (Button) getView().findViewById(R.id.submit_event);

        judul = (EditText) getView().findViewById(R.id.judul_event_et);
        alamat = (EditText) getView().findViewById(R.id.lokasi_event_et);
        biaya = (EditText) getView().findViewById(R.id.biaya_event_et);
        wktawal = (EditText) getView().findViewById(R.id.waktu_awal_et);
        wktakhir = (EditText) getView().findViewById(R.id.waktu_akhir_et);
        deskripsi = (EditText) getView().findViewById(R.id.deskripsi_acara_et);
        info = (EditText) getView().findViewById(R.id.infokontak_et);
        tglawal = (EditText) getView().findViewById(R.id.tanggal_awal_et);
        tglakhir = (EditText) getView().findViewById(R.id.tanggal_akhir_et);

        cekbox_biaya = (CheckBox) getView().findViewById(R.id.gratis);
        cekbox_selesai = (CheckBox) getView().findViewById(R.id.selesai);

        cekbox_biaya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true) {
                    biaya.setText("0");
                    biaya.setEnabled(false);
                }
                else{
                    biaya.getText().clear();
                    biaya.setEnabled(true);
                }
            }
        });

        cekbox_selesai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true) {
                    wktakhir.setText("");
                    wktakhir.setEnabled(false);
                }
                else{
                    wktakhir.getText().clear();
                    wktakhir.setEnabled(true);
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageGallery();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageCamera();
            }
        });

        getTanggalAwal();
        getTanggalAkhir();
        getWaktuAwal();
        getWaktuAkhir();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cek();
            }
        });
    }
    public void cek() {
        if (validate() == true) {
            return;
        }
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Membuat Acara...");
        pd.setCancelable(false);
        pd.show();
        postEvent();
    }

    private void getWaktuAwal(){

        wktawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                wktawal.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });


    }

    private void getWaktuAkhir(){
        // Get Current time

        wktakhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar ca = Calendar.getInstance();
                int hour = ca.get(Calendar.HOUR_OF_DAY);
                int minute = ca.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                wktakhir.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

    }

    private void getTanggalAwal(){
        tglawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerstart,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerstart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String startdate = day + "-" + month + "-" + year;
                startDate2 = year + "-" + month + "-" + day;
                //Toast.makeText(RegisterActivity.this, dateOfBirth, Toast.LENGTH_SHORT).show();
                tglawal.setText(startdate);
            }
        };
    }

    private void getTanggalAkhir(){
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
                        mDateSetListenerend,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerend = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String enddate = day + "-" + month + "-" + year;
                endDate2 = year + "-" + month + "-" + day;
                //Toast.makeText(RegisterActivity.this, dateOfBirth, Toast.LENGTH_SHORT).show();
                tglakhir.setText(enddate);
            }
        };
    }

    private void getImageGallery(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Membuka Galeri...");
        pd.setCancelable(false);
        pd.show();
        EasyImage.openGallery(this, 0);
        //EasyImage.openChooserWithGallery(this, "Pick source", 0);
    }

    private void getImageCamera(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Membuka Kamera...");
        pd.setCancelable(false);
        pd.show();
        EasyImage.openCamera(this, 0);
        //EasyImage.openChooserWithGallery(this, "Pick source", 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onImagePicked(File imageFiles, EasyImage.ImageSource source, int type) {
                try {
                    poto = new Compressor(getActivity()).compressToFile(imageFiles);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
                pathImage = imageFiles.getAbsolutePath();
                onPhotosReturned(poto);
                Toast.makeText(getActivity(), "picked", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), pathImage, Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                Toast.makeText(getActivity(), "canceled", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

    private void onPhotosReturned(File returnedPhotos) {
        Picasso.get()
                .load(returnedPhotos)
                .placeholder(R.drawable.placegam)
                .error(R.drawable.logoipb)
                .into(gambar);
    }

    private void postEvent(){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        createdBy2 = sessionManager.getKeyId();
        title2 = judul.getText().toString();
        place2 = alamat.getText().toString();
        startTime2 = wktawal.getText().toString();
        endTime2 = wktakhir.getText().toString();
        description2 = deskripsi.getText().toString();
        contact2 = info.getText().toString();
        price2 = biaya.getText().toString();

        File filenya = new File(pathImage);
        try {
            compoto = new Compressor(getActivity()).compressToFile(filenya);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), compoto);
        MultipartBody.Part picture = MultipartBody.Part.createFormData("picture", poto.getName(), reqFile);
        RequestBody createdBy = RequestBody.create(MediaType.parse("text/plain"), createdBy2);
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), title2);
        RequestBody place = RequestBody.create(MediaType.parse("text/plain"), place2);
        RequestBody startDate = RequestBody.create(MediaType.parse("text/plain"), startDate2);
        RequestBody endDate = RequestBody.create(MediaType.parse("text/plain"), endDate2);
        RequestBody startTime = RequestBody.create(MediaType.parse("text/plain"), startTime2);
        RequestBody endTime = RequestBody.create(MediaType.parse("text/plain"), endTime2);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), description2);
        RequestBody contact = RequestBody.create(MediaType.parse("text/plain"), contact2);
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), price2);

        Call<PostEventResponse> ucall = apiService.postEvent("JWT "+ sessionManager.getKeyToken(),title,place,startDate,endDate,startTime,endTime,description,contact,price,picture,createdBy);
        ucall.enqueue(new Callback<PostEventResponse>() {
            @Override
            public void onResponse(Call<PostEventResponse> call, Response<PostEventResponse> response) {

                if (response.isSuccessful()) {

                    PostEventResponse er = response.body();

                    if(er.isSuccess()==false ){
                        Snackbar.make(getView(), er.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        Snackbar.make(getView(), er.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Fragment newFragment = null;
                        newFragment = new EventFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PostEventResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
    public boolean validate() {
        boolean valid = false;
        View focusView = null;

        int cekError = 0;

        judul.setError(null);
        alamat.setError(null);
        biaya.setError(null);
        wktawal.setError(null);
        wktakhir.setError(null);
        deskripsi.setError(null);
        info.setError(null);
        tglawal.setError(null);
        tglakhir.setError(null);

        title3 = judul.getText().toString();
        place3 = alamat.getText().toString();
        startTime3 = wktawal.getText().toString();
        endTime3 = wktakhir.getText().toString();
        description3 = deskripsi.getText().toString();
        contact3 = info.getText().toString();
        price3 = biaya.getText().toString();
        startDate3 = tglawal.getText().toString();
        endDate3 = tglakhir.getText().toString();

        if(cekError==0) {
            if (title3.isEmpty()) {
                judul.setError("Judul tidak boleh kosong");
                focusView = judul;
                valid = true;
            } else {
                judul.setError(null);
                cekError=1;
            }
        }
        if(cekError==1) {
            if (place3.isEmpty()) {
                alamat.setError("Alamat tidak boleh kosong");
                focusView = alamat;
                valid = true;
            } else {
                alamat.setError(null);
                cekError=2;
            }
        }
        if(cekError==2) {
            if (price3.isEmpty()) {
                //Toast.makeText(getActivity(), "Biaya tidak boleh kosong", Toast.LENGTH_SHORT).show();
                biaya.setError("Biaya tidak boleh kosong");
                focusView = biaya;
                valid = true;
            } else {
                biaya.setError(null);
                cekError = 3;
            }
        }
        if(cekError==3) {
            if (startDate3.isEmpty()) {
                Toast.makeText(getActivity(), "Tanggal awal tidak boleh kosong", Toast.LENGTH_SHORT).show();
                tglawal.setError("Tanggal awal tidak boleh kosong");
                focusView = tglawal;
                valid = true;
            }
            else {
                tglawal.setError(null);
                cekError=4;
            }
        }
        if(cekError==4) {
            if (endDate3.isEmpty()) {
                Toast.makeText(getActivity(), "Tanggal akhir tidak boleh kosong", Toast.LENGTH_SHORT).show();
                tglakhir.setError("Tanggal Akhir tidak boleh kosong");
                focusView = tglakhir;
                valid = true;
            }
            else {
                tglakhir.setError(null);
                cekError=5;
            }
        }
        if(cekError==5) {
            if (startTime3.isEmpty()) {
                Toast.makeText(getActivity(), "Waktu dimulai tidak boleh kosong", Toast.LENGTH_SHORT).show();
                wktawal.setError("Waktu dimulai tidak boleh kosong");
                focusView = wktawal;
                valid = true;
            } else {
                wktawal.setError(null);
                cekError = 6;
            }
        }
        if(cekError==6) {
            if (endTime3.isEmpty() && cekbox_selesai.isChecked() == false) {
                Toast.makeText(getActivity(), "Waktu berakhir tidak boleh kosong", Toast.LENGTH_SHORT).show();
                wktakhir.setError("Waktu berakhir tidak boleh kosong");
                focusView = wktakhir;
                valid = true;
            } else if(endTime3.isEmpty() && cekbox_selesai.isChecked()){
                wktakhir.setError(null);
                cekError=7;
            } else {
                wktakhir.setError(null);
                cekError=7;
            }
        }
        if(cekError==7) {
            if (description3.isEmpty()) {
                deskripsi.setError("Deskripsi tidak boleh kosong");
                focusView = deskripsi;
                valid = true;
            }
            else {
                deskripsi.setError(null);
                cekError=8;
            }
        }
        if(cekError==8) {
            if (contact3.isEmpty()) {
                info.setError("Info dan kontak tidak boleh kosong");
                focusView = info;
                valid = true;
            } else {
                info.setError(null);
                cekError=9;
            }
        }
        if(cekError==9) {
            if (gambar.getDrawable() == null) {
                Toast.makeText(getActivity(), "Gambar tidak boleh kosong", Toast.LENGTH_SHORT).show();
                focusView = gambar;
                valid = true;
            } else {
                cekError=10;
            }
        }
        if (valid) {
            focusView.requestFocus();
        }
        return valid;
    }
}
