package com.example.lathifrdp.demoapp.fragment.crowdfunding.alumni;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.lathifrdp.demoapp.fragment.crowdfunding.mahasiswa.CrowdMahasiswaFragment;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.response.PinResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrowdPinFragment extends Fragment {
    Button pinbtn;
    EditText pin;
    SessionManager sessionManager;
    ApiInterface apiService;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crowd_pin_alumni,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Crowdfunding");
        sessionManager = new SessionManager(getActivity());
        pin = (EditText) getView().findViewById(R.id.crowd_pin);
        pinbtn = (Button) getView().findViewById(R.id.crowd_btn_pin);
        pinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Menunggu ...");
                pd.setCancelable(false);
                pd.show();
                postPin();
            }
        });
    }
    private void postPin(){
        final String key = pin.getText().toString();
        final String createdBy = sessionManager.getKeyId();

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<PinResponse> ucall = apiService.postPin("JWT "+ sessionManager.getKeyToken(),createdBy,key);
        ucall.enqueue(new Callback<PinResponse>() {
            @Override
            public void onResponse(Call<PinResponse> call, Response<PinResponse> response) {

                if (response.isSuccessful()) {

                    PinResponse pr = response.body();

                    if(pr.isSuccess()==false ){
                        Toast.makeText(getActivity(), pr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), pr.getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<PinResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
