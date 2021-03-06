package com.example.lathifrdp.demoapp.fragment.crowdfunding.mahasiswa;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.response.PostCrowdRequestResponse;
import com.example.lathifrdp.demoapp.response.PostCrowdfundingResponse;
import com.example.lathifrdp.demoapp.response.UploadCrowdfundingResponse;
import com.obsez.android.lib.filechooser.ChooserDialog;
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

public class CrowdCreateFragment extends Fragment {

    ApiInterface apiService;
    SessionManager sessionManager;
    String pathDir, msg, pathnya, pathImage, pathnya_video, tgl;
    LinearLayout uploadFile, uploadVideo;
    TextView urlnya, urlnya_video;
    EditText judul, deskripsi, kontak, lokasi, proyek, biaya, batas_waktu;
    File compDoc, compVid, poto, compoto;
    Button btn;
    ProgressDialog pd;
    ImageView gambar,gallery,camera;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String idnya;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_crowdfunding,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Proposal");
        sessionManager = new SessionManager(getActivity());
        uploadFile = (LinearLayout) getView().findViewById(R.id.crowd_upload);
        uploadVideo = (LinearLayout) getView().findViewById(R.id.crowd_upload_video);
        urlnya = (TextView) getView().findViewById(R.id.crowd_url);
        urlnya_video = (TextView) getView().findViewById(R.id.crowd_url_video);
        judul = (EditText) getView().findViewById(R.id.crowd_judul_et);
        deskripsi = (EditText) getView().findViewById(R.id.crowd_deskripsi_et);
        kontak = (EditText) getView().findViewById(R.id.crowd_kontak_et);
        lokasi = (EditText) getView().findViewById(R.id.crowd_lokasi_et);
        proyek = (EditText) getView().findViewById(R.id.crowd_proyek_et);
        biaya = (EditText) getView().findViewById(R.id.crowd_biaya_et);
        batas_waktu = (EditText) getView().findViewById(R.id.crowd_bataswaktu_et);
        btn = (Button) getView().findViewById(R.id.submit_crowd);

        gambar = (ImageView) getView().findViewById(R.id.crowd_gambar);
        gallery = (ImageView) getView().findViewById(R.id.crowd_gallery);
        camera = (ImageView) getView().findViewById(R.id.crowd_camera);

        getBatasWaktu();
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDocument();
            }
        });

        uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVideo();
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
        pd.setMessage("Menyimpan Proposal...");
        pd.setCancelable(false);
        pd.show();
        postCrowd();
    }

    private void getBatasWaktu(){
        batas_waktu.setOnClickListener(new View.OnClickListener() {
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
                String tanggalnya = day + "-" + month + "-" + year;
                tgl = year + "-" + month + "-" + day;
                //Toast.makeText(RegisterActivity.this, dateOfBirth, Toast.LENGTH_SHORT).show();
                batas_waktu.setText(tanggalnya);
            }
        };
    }

    private void getDocument(){
        new ChooserDialog().with(getActivity())
                .withFilter(false, false, "pdf", "doc","docx", "ppt", "pptx", "xls", "xlsx")
                .withStartFile(pathnya)
                .withResources(R.string.title_choose_dict_file, R.string.title_choose, R.string.dialog_cancel)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        Toast.makeText(getActivity(), "FILE: " + path, Toast.LENGTH_SHORT).show();
                        compDoc = pathFile;
                        urlnya.setText(path);
                    }
                })
                .build()
                .show();
    }
    private void getVideo(){
        new ChooserDialog().with(getActivity())
                .withFilter(false, false, "mp4")
                .withStartFile(pathnya_video)
                .withResources(R.string.title_choose_dict_file, R.string.title_choose, R.string.dialog_cancel)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        Toast.makeText(getActivity(), "FILE: " + path, Toast.LENGTH_SHORT).show();
                        compVid = pathFile;
                        urlnya_video.setText(path);
                    }
                })
                .build()
                .show();
    }
    private void postCrowd(){

        final String title2 = judul.getText().toString();
        final String description2 = deskripsi.getText().toString();
        final String contactPerson2 = kontak.getText().toString();
        final String location2 = lokasi.getText().toString();
        final String projectType2 = proyek.getText().toString();
        final String cost2 = biaya.getText().toString();
        final String deadline2 = batas_waktu.getText().toString();
        final String createdBy2 = sessionManager.getKeyId();

        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), compDoc);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file",compDoc.getName(), reqFile);
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), title2);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), description2);
        RequestBody contactPerson = RequestBody.create(MediaType.parse("text/plain"), contactPerson2);
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"), location2);
        RequestBody projectType = RequestBody.create(MediaType.parse("text/plain"), projectType2);
        RequestBody cost = RequestBody.create(MediaType.parse("text/plain"), cost2);
        RequestBody deadline = RequestBody.create(MediaType.parse("text/plain"), tgl);
        RequestBody createdBy = RequestBody.create(MediaType.parse("text/plain"), createdBy2);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<PostCrowdfundingResponse> ucall = apiService.postCrowd("JWT "+ sessionManager.getKeyToken(),file,title,description,contactPerson,location,projectType,cost,deadline,createdBy);
        ucall.enqueue(new Callback<PostCrowdfundingResponse>() {
            @Override
            public void onResponse(Call<PostCrowdfundingResponse> call, Response<PostCrowdfundingResponse> response) {

                if (response.isSuccessful()) {

                    PostCrowdfundingResponse cr = response.body();
                    msg = cr.getMessage();

                    if(cr.isSuccess()==false ){
                        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        idnya = cr.getCrowdfunding().getId();
                        postFoto();
                        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                    //pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PostCrowdfundingResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf, gagal posting", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

    private void postVideo(){

        RequestBody reqFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), compVid);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file",compVid.getName(), reqFile3);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<UploadCrowdfundingResponse> ucall = apiService.uploadVideoCrowd("JWT "+ sessionManager.getKeyToken(),idnya,file);
        ucall.enqueue(new Callback<UploadCrowdfundingResponse>() {
            @Override
            public void onResponse(Call<UploadCrowdfundingResponse> call, Response<UploadCrowdfundingResponse> response) {

                if (response.isSuccessful()) {

                    UploadCrowdfundingResponse cr = response.body();
                    msg = cr.getMessage();

                    if(cr.isSuccess()==false ){
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        Fragment newFragment = null;
                        newFragment = new CrowdMahasiswaFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UploadCrowdfundingResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf, gagal posting video", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
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

    public void postFoto(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        File filenya = new File(pathImage);
        try {
            compoto = new Compressor(getActivity()).compressToFile(filenya);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody reqFile4 = RequestBody.create(MediaType.parse("image/*"), compoto);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file", poto.getName(), reqFile4);
        //RequestBody id = RequestBody.create(MediaType.parse("text/plain"), id2);
        //final String id = id2;

        Call<UploadCrowdfundingResponse> ucall = apiService.uploadPhotoCrowd("JWT "+ sessionManager.getKeyToken(),idnya,file);
        ucall.enqueue(new Callback<UploadCrowdfundingResponse>() {
            @Override
            public void onResponse(Call<UploadCrowdfundingResponse> call, Response<UploadCrowdfundingResponse> response) {

                if (response.isSuccessful()) {

                    UploadCrowdfundingResponse cr = response.body();
                    msg = cr.getMessage();

                    if(cr.isSuccess()==false ){
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        postVideo();
                    }
                    //pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UploadCrowdfundingResponse> call, Throwable t) {
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
        deskripsi.setError(null);
        kontak.setError(null);
        lokasi.setError(null);
        proyek.setError(null);
        deskripsi.setError(null);
        biaya.setError(null);
        batas_waktu.setError(null);

        String title2 = judul.getText().toString();
        String description2 = deskripsi.getText().toString();
        String contactPerson2 = kontak.getText().toString();
        String location2 = lokasi.getText().toString();
        String projectType2 = proyek.getText().toString();
        String cost2 = biaya.getText().toString();
        String deadline2 = batas_waktu.getText().toString();
        String urlnya2 = urlnya.getText().toString();
        String urlnya_video2 = urlnya_video.getText().toString();

        if(cekError==0) {
            if (title2.isEmpty()) {
                judul.setError("Judul tidak boleh kosong");
                focusView = judul;
                valid = true;
            } else {
                judul.setError(null);
                cekError=1;
            }
        }
        if(cekError==1) {
            if (description2.isEmpty()) {
                deskripsi.setError("Deskripsi tidak boleh kosong");
                focusView = deskripsi;
                valid = true;
            } else {
                deskripsi.setError(null);
                cekError=2;
            }
        }
        if(cekError==2) {
            if (contactPerson2.isEmpty()) {
                //Toast.makeText(getActivity(), "Biaya tidak boleh kosong", Toast.LENGTH_SHORT).show();
                kontak.setError("Info dan kontak tidak boleh kosong");
                focusView = kontak;
                valid = true;
            } else {
                kontak.setError(null);
                cekError = 3;
            }
        }
        if(cekError==3) {
            if (location2.isEmpty()) {
                //Toast.makeText(getActivity(), "Tanggal awal tidak boleh kosong", Toast.LENGTH_SHORT).show();
                lokasi.setError("Lokasi tidak boleh kosong");
                focusView = lokasi;
                valid = true;
            }
            else {
                lokasi.setError(null);
                cekError=4;
            }
        }
        if(cekError==4) {
            if (projectType2.isEmpty()) {
                //Toast.makeText(getActivity(), "Tanggal akhir tidak boleh kosong", Toast.LENGTH_SHORT).show();
                proyek.setError("Tipe proyek tidak boleh kosong");
                focusView = proyek;
                valid = true;
            }
            else {
                proyek.setError(null);
                cekError=5;
            }
        }
        if(cekError==5) {
            if (cost2.isEmpty()) {
                //Toast.makeText(getActivity(), "Waktu dimulai tidak boleh kosong", Toast.LENGTH_SHORT).show();
                biaya.setError("Biaya tidak boleh kosong");
                focusView = biaya;
                valid = true;
            } else {
                biaya.setError(null);
                cekError = 6;
            }
        }
        if(cekError==6) {
            if (deadline2.isEmpty()) {
                Toast.makeText(getActivity(), "Batas waktu tidak boleh kosong", Toast.LENGTH_SHORT).show();
                batas_waktu.setError("Batas waktu tidak boleh kosong");
                focusView = batas_waktu;
                valid = true;
            } else {
                //batas_waktu.setError(null);
                cekError=7;
            }
        }
        if(cekError==7) {
            if (urlnya2.isEmpty()) {
                //urlnya.setError("Dokumen tidak boleh kosong");
                Toast.makeText(getActivity(), "Dokumen tidak boleh kosong", Toast.LENGTH_SHORT).show();
                focusView = urlnya;
                valid = true;
            }
            else {
                urlnya.setError(null);
                cekError=8;
            }
        }
        if(cekError==8) {
            if (urlnya_video2.isEmpty()) {
                //urlnya_video.setError("Video tidak boleh kosong");
                Toast.makeText(getActivity(), "Video tidak boleh kosong", Toast.LENGTH_SHORT).show();
                focusView = urlnya_video;
                valid = true;
            } else {
                urlnya_video.setError(null);
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
