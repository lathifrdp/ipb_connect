package com.example.lathifrdp.demoapp.fragment.job;

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

public class JobFragment extends Fragment {

    Bundle bundle;
    private EditText title2;
    Button btnSearch;
    public String title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_job,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.jobFragment).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"Job Fragment",Toast.LENGTH_SHORT).show();
//            }
//        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Job Vacancy");
        bundle = new Bundle();
        title2 = (EditText) getView().findViewById(R.id.titleFragment);
        btnSearch = (Button) getView().findViewById(R.id.searchBTN);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = title2.getText().toString();
                Fragment newFragment = null;
                newFragment = new VacancyFragment();
                bundle.putString("title",title);
                newFragment.setArguments(bundle);

                Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
