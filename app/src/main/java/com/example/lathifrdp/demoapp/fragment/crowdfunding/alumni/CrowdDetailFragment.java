package com.example.lathifrdp.demoapp.fragment.crowdfunding.alumni;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.model.Event;
import com.example.lathifrdp.demoapp.response.PostDonasiResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrowdDetailFragment extends Fragment {

    Bundle bundle;
    private String id_crowd;
    TextView judul, lokasi, curr, tot, deskripsi, kontak, nama, angkatan, prodi,proyek;
    CircleImageView foto;
    ApiInterface apiService;
    SessionManager sessionManager;
    Button btn;
    ProgressDialog pd;
    public String pathImage,msg;
    public File poto, compoto;
    public ImageView galeri,kamera, gambar;
    Button postnya;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_crowd_alumni,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Proposal");
        sessionManager = new SessionManager(getActivity());
        judul = (TextView) getView().findViewById(R.id.judul_crowd_alumni);
        lokasi = (TextView) getView().findViewById(R.id.lokasi_crowd_alumni);
        curr = (TextView) getView().findViewById(R.id.donasi_crowd_alumni);
        deskripsi = (TextView) getView().findViewById(R.id.deskripsi_crowd_alumni);
        kontak = (TextView) getView().findViewById(R.id.kontak_crowd_alumni);
        nama = (TextView) getView().findViewById(R.id.namanya);
        angkatan = (TextView) getView().findViewById(R.id.angkatannya);
        prodi = (TextView) getView().findViewById(R.id.studinya);
        foto = (CircleImageView) getView().findViewById(R.id.fotonya);
        proyek = (TextView) getView().findViewById(R.id.project_crowd_alumni);
        btn = (Button) getView().findViewById(R.id.crowd_donasi);

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

                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                LayoutInflater inflater = getActivity().getLayoutInflater();
                alertDialog.setTitle("Unggah Bukti");
                //alertDialog.setView(inflater.inflate(R.layout.fragment_crowd_donasi, null));
                View dialogview = inflater.inflate(R.layout.fragment_crowd_donasi, null);
                alertDialog.setView(dialogview);

                galeri = (ImageView) dialogview.findViewById(R.id.crowd_gallery);
                kamera = (ImageView) dialogview.findViewById(R.id.crowd_camera);
                postnya = (Button) dialogview.findViewById(R.id.submit_donasi);
                gambar = (ImageView) dialogview.findViewById(R.id.crowd_gambar);

                galeri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getImageGallery();
                    }
                });
                kamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getImageCamera();
                    }
                });

                postnya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        pd = new ProgressDialog(getActivity());
                        pd.setMessage("Unggah bukti...");
                        pd.setCancelable(false);
                        pd.show();
                        postDonasi();
                    }
                });

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
                pd.dismiss();
                //Toast.makeText(getActivity(), pathImage, Toast.LENGTH_SHORT).show();
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

    private void postDonasi(){

        final String createdBy2 = sessionManager.getKeyId();

        File filenya = new File(pathImage);
        try {
            compoto = new Compressor(getActivity()).compressToFile(filenya);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), compoto);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file", poto.getName(), reqFile);
        RequestBody createdBy = RequestBody.create(MediaType.parse("text/plain"), createdBy2);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<PostDonasiResponse> ucall = apiService.postDonasi("JWT "+ sessionManager.getKeyToken(),file,id_crowd,createdBy);
        ucall.enqueue(new Callback<PostDonasiResponse>() {
            @Override
            public void onResponse(Call<PostDonasiResponse> call, Response<PostDonasiResponse> response) {

                if (response.isSuccessful()) {

                    PostDonasiResponse mr = response.body();
                    msg = mr.getMessage();

                    if(mr.isSuccess()==false ){
                        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Fragment newFragment = null;
                        newFragment = new CrowdAlumniFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        Toast.makeText(getActivity(), "Bukti yang anda unggah segera diverifikasi oleh admin. Terima kasih.", Toast.LENGTH_LONG).show();
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PostDonasiResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
