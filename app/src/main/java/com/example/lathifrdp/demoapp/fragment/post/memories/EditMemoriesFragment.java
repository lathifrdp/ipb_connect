package com.example.lathifrdp.demoapp.fragment.post.memories;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.fragment.memories.MemoriesFragment;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Memory;
import com.example.lathifrdp.demoapp.response.PostMemoriesResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMemoriesFragment extends Fragment{
    EditText capt;
    Button btn;
    ImageView gambar,gallery,camera;
    public String pathImage,msg;
    //RecyclerView gambar;
    public File poto, compoto;
    SessionManager sessionManager;
    ApiInterface apiService;
    ProgressDialog pd;
    String memo;
    Bundle bundle;
    public String urlnya;
    //public Uri tesnya;
    //private ImageAdapter imagesAdapter;
    //private ArrayList<File> photos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_memories,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Ubah Memories");
        sessionManager = new SessionManager(getActivity());

        gambar = (ImageView) getView().findViewById(R.id.gambar);
        gallery = (ImageView) getView().findViewById(R.id.gallery);
        camera = (ImageView) getView().findViewById(R.id.camera);
        capt = (EditText) getView().findViewById(R.id.caption_et);
        btn = (Button) getView().findViewById(R.id.submit_memories);
        //imagesAdapter = new ImageAdapter(getActivity(), photos);
        //gambar = (RecyclerView) getView().findViewById(R.id.gambar);
//        gambar.setLayoutManager(new LinearLayoutManager(getActivity()));
//        gambar.setHasFixedSize(true);
//        gambar.setAdapter(imagesAdapter);

        bundle = this.getArguments();

        if(bundle != null){
            // handle your code here.
            memo = bundle.getString("id_memory");
            loadDataMemories();
        }
        else {
            Toast.makeText(getActivity(), "gagal boss", Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(getActivity(), urlnya, Toast.LENGTH_SHORT).show();

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
        pd.setMessage("Mengubah Memories...");
        pd.setCancelable(false);
        pd.show();
        putMemories();
    }

    private void loadDataMemories(){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Memory> call = apiService.getDetailMemory("JWT "+ sessionManager.getKeyToken(),memo);
        call.enqueue(new Callback<Memory>() {
            @Override
            public void onResponse(Call<Memory> call, final Response<Memory> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    Memory memory = response.body();
                    capt.setText(memory.getCaption());
                    urlnya = new BaseModel().getMemoryUrl()+memory.getPhoto();
                    //Toast.makeText(getActivity(), "load: "+urlnya, Toast.LENGTH_SHORT).show();
                    Picasso.get()
                            .load(urlnya)
                            .placeholder(R.drawable.placegam)
                            .error(R.drawable.logoipb)
                            .into(gambar);
//                    tesnya = Uri.parse(urlnya);
//                    File tes = new File(urlnya);
//                    Toast.makeText(getActivity(), "tes: "+tes.getAbsolutePath(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Memory> call, Throwable t) {
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

    private void putMemories(){

        final String caption2 = capt.getText().toString();
        final String createdBy2 = sessionManager.getKeyId();

        if(pathImage==null){
            compoto = new File(urlnya);
            //File y = new File(urlnya.replace("http://",""));
            //Log.e("stringnya: ",urlnya.replace("http://","")+" 0");
            //Log.e("stringnya: ",compoto.toString().replace("http:/","http://")+" 00");
            //Log.e("stringnya: ",compoto.toString()+" 1");
            //Log.e("stringnya: ",compoto.toString().split("/",1)+" 2");
            //File x = new File(compoto.toString().replace("http:/","http://"));
            //Log.e("stringnya: ",y+" 1");
        }
        else {
            File filenya = new File(pathImage);
            try {
                compoto = new Compressor(getActivity()).compressToFile(filenya);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), compoto);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", compoto.getName(), reqFile);
        RequestBody caption = RequestBody.create(MediaType.parse("text/plain"), caption2);
        RequestBody createdBy = RequestBody.create(MediaType.parse("text/plain"), createdBy2);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<PostMemoriesResponse> ucall = apiService.putMemories("JWT "+ sessionManager.getKeyToken(),memo,photo,caption,createdBy);
        ucall.enqueue(new Callback<PostMemoriesResponse>() {
            @Override
            public void onResponse(Call<PostMemoriesResponse> call, Response<PostMemoriesResponse> response) {

                if (response.isSuccessful()) {

                    PostMemoriesResponse mr = response.body();
                    msg = mr.getMessage();

                    if(mr.isSuccess()==false ){
                        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        //Toast.makeText(getActivity(), compoto.getPath(), Toast.LENGTH_SHORT).show();
                        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Fragment newFragment = null;
                        newFragment = new DetailMemoriesPostFragment();
                        bundle.putString("id_memory",memo);
                        newFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PostMemoriesResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf, untuk saat ini gambar harus diubah", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "atau unggah ulang gambar yang sama", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }
    public boolean validate() {
        boolean valid = false;
        View focusView = null;

        int cekError = 0;

        capt.setError(null);
        final String caption2 = capt.getText().toString();

        if(cekError==0) {
            if (gambar.getDrawable() == null) {
                Toast.makeText(getActivity(), "Gambar tidak boleh kosong", Toast.LENGTH_SHORT).show();
                focusView = gambar;
                valid = true;
            } else {
                cekError=1;
            }
        }
        if(cekError==1) {
            if (caption2.isEmpty()) {
                capt.setError("Caption tidak boleh kosong");
                focusView = capt;
                valid = true;
            } else {
                capt.setError(null);
                cekError=2;
            }
        }
        if (valid) {
            focusView.requestFocus();
        }
        return valid;
    }
}
