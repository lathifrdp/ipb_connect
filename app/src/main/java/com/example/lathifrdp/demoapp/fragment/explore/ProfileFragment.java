package com.example.lathifrdp.demoapp.fragment.explore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;

public class ProfileFragment extends Fragment {

    Bundle bundle;
    private String x3,full,batch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bundle = this.getArguments();

        if(bundle != null){
            // handle your code here.
            //String x = bundle.getString("limit");
            //String x2 = bundle.getString("limit2");
            x3 = bundle.getString("id");
            full = bundle.getString("fullname");
            //batch = bundle.getString("batch");
            Toast.makeText(getActivity(), full, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), batch, Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), x3, Toast.LENGTH_SHORT).show();
            //loadDataUser();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }
    }
}
