package com.example.lathifrdp.demoapp.fragment.crowdfunding.alumni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.fragment.home.HomeFragment;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.response.PostCrowdRequestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrowdRequestFragment extends Fragment{

    Button req;
    SessionManager sessionManager;
    ApiInterface apiService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crowd_request_alumni,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Crowdfunding");
        sessionManager = new SessionManager(getActivity());
        req = (Button) getView().findViewById(R.id.crowd_req);

        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memintaAkses();
            }
        });

    }

    private void memintaAkses(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        final String createdBy = sessionManager.getKeyId();

        Call<PostCrowdRequestResponse> ucall = apiService.postCrowdRequest("JWT "+ sessionManager.getKeyToken(),createdBy);
        ucall.enqueue(new Callback<PostCrowdRequestResponse>() {
            @Override
            public void onResponse(Call<PostCrowdRequestResponse> call, Response<PostCrowdRequestResponse> response) {

                if (response.isSuccessful()) {
                    PostCrowdRequestResponse cr = response.body();

                    if(cr.isSuccess()==false ){
                        Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();
                        sessionManager.updateCrowdfunding(cr.getIsCrowdfunding());
                        Toast.makeText(getActivity(), sessionManager.getKeyCrowdfunding(), Toast.LENGTH_SHORT).show();
                        Fragment newFragment = null;
                        newFragment = new CrowdNotifFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostCrowdRequestResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
