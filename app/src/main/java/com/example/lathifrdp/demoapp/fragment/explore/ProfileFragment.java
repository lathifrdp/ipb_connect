package com.example.lathifrdp.demoapp.fragment.explore;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.lathifrdp.demoapp.model.User;
import com.example.lathifrdp.demoapp.model.UserProfile;
import com.example.lathifrdp.demoapp.response.UploadProfileResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    Bundle bundle;
    private String id_user,full,mail;
    TextView nama_profil, alamat_profil, nomor_profil, email_profil, interest_profil, hobby_profil, job_profil, marital_profil;
    ApiInterface apiService;
    SessionManager sessionManager;
    CircleImageView foto_profil;
    ImageView ubah_foto;
    ImageView gambar,galeri,kamera;
    public String pathImage,msg;
    //RecyclerView gambar;
    public File poto, compoto;
    ProgressDialog pd;
    Button postnya;
    String createdBy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profil");
        sessionManager = new SessionManager(getActivity());
        foto_profil = (CircleImageView) getView().findViewById(R.id.user_profile_photo);
        nama_profil = (TextView) getView().findViewById(R.id.user_profile_name);
        alamat_profil = (TextView) getView().findViewById(R.id.address);
        nomor_profil = (TextView) getView().findViewById(R.id.nomor_hp);
        email_profil = (TextView) getView().findViewById(R.id.user_profile_short_bio);
        interest_profil = (TextView) getView().findViewById(R.id.interest);
        hobby_profil = (TextView) getView().findViewById(R.id.hobby);
        job_profil = (TextView) getView().findViewById(R.id.current_job);
        marital_profil = (TextView) getView().findViewById(R.id.marital_status);
        ubah_foto = (ImageView) getView().findViewById(R.id.ubah_profile);
        bundle = this.getArguments();

        if(bundle != null){
            id_user = bundle.getString("id");
            full = bundle.getString("nama");
            mail = bundle.getString("email");

            loadDataProfile();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }

        createdBy = sessionManager.getKeyId();
        if(id_user.equals(createdBy)){
            ubah_foto.setVisibility(View.VISIBLE);
        }
        else{
            ubah_foto.setVisibility(View.GONE);
        }

        ubah_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "ubah foto", Toast.LENGTH_SHORT).show();
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                LayoutInflater inflater = getActivity().getLayoutInflater();
                //alertDialog.setTitle("Unggah Bukti");
                View dialogview = inflater.inflate(R.layout.fragment_edit_photo, null);
                alertDialog.setView(dialogview);

                galeri = (ImageView) dialogview.findViewById(R.id.profile_gallery);
                kamera = (ImageView) dialogview.findViewById(R.id.profile_camera);
                postnya = (Button) dialogview.findViewById(R.id.submit_photo);
                gambar = (ImageView) dialogview.findViewById(R.id.profile_gambar);

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
                        pd.setMessage("Unggah foto profil...");
                        pd.setCancelable(false);
                        pd.show();
                        editFoto();
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

    private void loadDataProfile(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<UserProfile> call = apiService.getProfile("JWT "+ sessionManager.getKeyToken(),id_user);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, final Response<UserProfile> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    UserProfile userProfile = response.body();
                    nama_profil.setText(full);
                    email_profil.setText(mail);
                    alamat_profil.setText("Alamat: "+userProfile.getAddress());
                    nomor_profil.setText("Nomor Handphone: "+userProfile.getMobileNumber());
                    interest_profil.setText("Ketertarikan: "+userProfile.getInterest());
                    hobby_profil.setText("Hobi: "+userProfile.getHobby());
                    job_profil.setText("Pekerjaan saat ini: "+userProfile.getCurrentJob());
                    marital_profil.setText("Status: "+userProfile.getMaritalStatus());

                    String url = new BaseModel().getProfileUrl()+userProfile.getPhoto();
                    Picasso.get()
                            .load(url)
                            .placeholder(R.drawable.alumni2)
                            .error(R.drawable.logoipb)
                            .into(foto_profil);
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void editFoto(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        File filenya = new File(pathImage);
        try {
            compoto = new Compressor(getActivity()).compressToFile(filenya);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), compoto);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", poto.getName(), reqFile);
        //RequestBody id = RequestBody.create(MediaType.parse("text/plain"), id2);
        //final String id = id2;

        Call<UploadProfileResponse> ucall = apiService.editPhoto(id_user,photo);
        ucall.enqueue(new Callback<UploadProfileResponse>() {
            @Override
            public void onResponse(Call<UploadProfileResponse> call, Response<UploadProfileResponse> response) {

                if (response.isSuccessful()) {

                    UploadProfileResponse mr = response.body();
                    msg = mr.getMessage();

                    if(mr.isSuccess()==false ){
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        loadDataProfile();
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UploadProfileResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

}
