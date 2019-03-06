package com.example.lathifrdp.demoapp.fragment.home;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.fragment.group.GroupFragment;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.UserProfile;
import com.example.lathifrdp.demoapp.response.PostGroupResponse;
import com.example.lathifrdp.demoapp.response.UploadProfileResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PutProfileFragment extends Fragment{

    EditText ketertarikan,hobi,pekerjaan,alamat,status,nomor;
    Button btn;
    SessionManager sessionManager;
    ApiInterface apiService;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profil");
        sessionManager = new SessionManager(getActivity());
        ketertarikan = (EditText) getView().findViewById(R.id.profil_ketertarikan);
        hobi = (EditText) getView().findViewById(R.id.profil_hobi);
        pekerjaan = (EditText) getView().findViewById(R.id.profil_pekerjaan);
        alamat = (EditText) getView().findViewById(R.id.profil_alamat);
        status = (EditText) getView().findViewById(R.id.profil_status);
        nomor = (EditText) getView().findViewById(R.id.profil_handphone);
        btn = (Button) getView().findViewById(R.id.submit_profil);

        loadDataProfile();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Ubah Profil ...");
                pd.setCancelable(false);
                pd.show();
                putProfile();
            }
        });

    }
    private void loadDataProfile(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        final String id_user = sessionManager.getKeyId();

        Call<UserProfile> call = apiService.getProfile("JWT "+ sessionManager.getKeyToken(),id_user);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, final Response<UserProfile> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    UserProfile userProfile = response.body();
                    alamat.setText(userProfile.getAddress());
                    nomor.setText(userProfile.getMobileNumber());
                    ketertarikan.setText(userProfile.getInterest());
                    hobi.setText(userProfile.getHobby());
                    pekerjaan.setText(userProfile.getCurrentJob());
                    status.setText(userProfile.getMaritalStatus());
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void putProfile(){
        final String address = alamat.getText().toString();
        final String mobileNumber = nomor.getText().toString();
        final String currentJob = pekerjaan.getText().toString();
        final String interest = ketertarikan.getText().toString();
        final String hobby = hobi.getText().toString();
        final String maritalStatus = status.getText().toString();
        final String id = sessionManager.getKeyId();

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<UploadProfileResponse> ucall = apiService.putUser("JWT "+ sessionManager.getKeyToken(),address,mobileNumber,currentJob,interest,hobby,maritalStatus,id);
        ucall.enqueue(new Callback<UploadProfileResponse>() {
            @Override
            public void onResponse(Call<UploadProfileResponse> call, Response<UploadProfileResponse> response) {

                if (response.isSuccessful()) {

                    UploadProfileResponse gr = response.body();

                    if(gr.isSuccess()==false ){
                        Snackbar.make(getView(), gr.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        Snackbar.make(getView(), gr.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Fragment newFragment = null;
                        newFragment = new HomeFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
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
