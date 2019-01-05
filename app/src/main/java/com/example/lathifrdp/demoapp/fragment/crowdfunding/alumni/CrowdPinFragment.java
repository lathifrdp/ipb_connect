package com.example.lathifrdp.demoapp.fragment.crowdfunding.alumni;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.response.PinResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrowdPinFragment extends Fragment {
    Button pinbtn;
    EditText pin,pin1,pin2,pin3,pin4,pin5,pin6;
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
        //pin = (EditText) getView().findViewById(R.id.crowd_pin);
        pin1 = (EditText) getView().findViewById(R.id.pin1);
        pin2 = (EditText) getView().findViewById(R.id.pin2);
        pin3 = (EditText) getView().findViewById(R.id.pin3);
        pin4 = (EditText) getView().findViewById(R.id.pin4);
        pin5 = (EditText) getView().findViewById(R.id.pin5);
        pin6 = (EditText) getView().findViewById(R.id.pin6);

        tombolNext();
        pinGeser();

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
        //final String key = pin.getText().toString();
        final String createdBy = sessionManager.getKeyId();
        final String gabungpin = pin1.getText().toString()+pin2.getText().toString()+
                                 pin3.getText().toString()+pin4.getText().toString()+
                                 pin5.getText().toString()+pin6.getText().toString();

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<PinResponse> ucall = apiService.postPin("JWT "+ sessionManager.getKeyToken(),createdBy,gabungpin);
        ucall.enqueue(new Callback<PinResponse>() {
            @Override
            public void onResponse(Call<PinResponse> call, Response<PinResponse> response) {

                if (response.isSuccessful()) {

                    PinResponse pr = response.body();

                    if(pr.isSuccess()==false ){
                        Toast.makeText(getActivity(), pr.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), gabungpin, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), pr.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), gabungpin, Toast.LENGTH_SHORT).show();
                        //sessionManager.updateCrowdfunding("3");
                        Fragment newFragment = null;
                        newFragment = new CrowdAlumniFragment();
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

    private void tombolNext(){
        pin1.setNextFocusDownId(pin2.getId());
        pin2.setNextFocusDownId(pin3.getId());
        pin3.setNextFocusDownId(pin4.getId());
        pin4.setNextFocusDownId(pin5.getId());
        pin5.setNextFocusDownId(pin6.getId());
    }

    private void pinGeser(){
        pin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(pin1.getText().toString().length() == 1){
                    pin2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pin2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(pin2.getText().toString().length() == 1){
                    pin3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pin3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(pin3.getText().toString().length() == 1){
                    pin4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pin4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(pin4.getText().toString().length() == 1){
                    pin5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pin5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(pin5.getText().toString().length() == 1){
                    pin6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
