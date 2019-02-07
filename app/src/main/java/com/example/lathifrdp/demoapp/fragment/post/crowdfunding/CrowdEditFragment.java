package com.example.lathifrdp.demoapp.fragment.post.crowdfunding;

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
import com.example.lathifrdp.demoapp.fragment.crowdfunding.mahasiswa.CrowdMahasiswaFragment;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.response.PostCrowdfundingResponse;
import com.example.lathifrdp.demoapp.response.UploadCrowdfundingResponse;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrowdEditFragment extends Fragment {

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
    Bundle bundle;
    private String id_crowd;

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

        bundle = this.getArguments();

        if(bundle != null){
            id_crowd = bundle.getString("id");
            loadDataDetail();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }

        //btn.setVisibility(View.GONE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Mengubah Proposal...");
                pd.setCancelable(false);
                pd.show();
                putCrowd();
            }
        });
    }

    private void loadDataDetail(){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Crowdfunding> call = apiService.getDetailCrowd("JWT "+ sessionManager.getKeyToken(),id_crowd);
        call.enqueue(new Callback<Crowdfunding>() {
            @Override
            public void onResponse(Call<Crowdfunding> call, final Response<Crowdfunding> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    Crowdfunding crowd = response.body();

                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("d-M-yyyy");
                    Date date = null;
                    try {
                        date = inputFormat.parse(crowd.getDeadline());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String formattedDate = outputFormat.format(date);

                    lokasi.setText(crowd.getLocation());
                    judul.setText(crowd.getTitle());
                    deskripsi.setText(crowd.getDescription());
                    kontak.setText(crowd.getContactPerson());
                    proyek.setText(crowd.getProjectType());
                    biaya.setText(crowd.getTotalCost());
                    batas_waktu.setText(formattedDate);
                    urlnya.setText(crowd.getFile());
                    urlnya_video.setText(crowd.getProposalVideo());

                    String url = new BaseModel().getCrowdfundingUrl()+crowd.getProposalImages().get(0).getFile();
                    Picasso.get()
                            .load(url)
                            .placeholder(R.drawable.placegam)
                            .error(R.drawable.placeholdergambar)
                            .into(gambar);
                }
            }

            @Override
            public void onFailure(Call<Crowdfunding> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
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
    private void putCrowd(){

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

        Call<PostCrowdfundingResponse> ucall = apiService.putCrowd("JWT "+ sessionManager.getKeyToken(),id_crowd,file,title,description,contactPerson,location,projectType,cost,deadline,createdBy);
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
                        postVideo();
                        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Fragment newFragment = null;
                        newFragment = new PostCrowdFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    pd.dismiss();
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
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UploadCrowdfundingResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
