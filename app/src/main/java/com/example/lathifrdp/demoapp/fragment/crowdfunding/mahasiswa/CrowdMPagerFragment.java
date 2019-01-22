package com.example.lathifrdp.demoapp.fragment.crowdfunding.mahasiswa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.ViewPagerAdapter;
import com.example.lathifrdp.demoapp.fragment.crowdfunding.alumni.CrowdDetailFragment;
import com.example.lathifrdp.demoapp.fragment.crowdfunding.alumni.CrowdMediaFragment;
import com.example.lathifrdp.demoapp.fragment.crowdfunding.alumni.CrowdProgressFragment;

public class CrowdMPagerFragment extends Fragment{

    Bundle bundle;
    private String id_crowd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crowd_pager_mahasiswa,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Crowdfunding");

        bundle = this.getArguments();

        if(bundle != null){
            id_crowd = bundle.getString("id");
            //loadDataMedia();
        }
        else {
            //Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }
        bundle.putString("id",id_crowd);
        Fragment newFragment = null;
        newFragment = new CrowdMDetailFragment();
        newFragment.setArguments(bundle);

        ViewPager viewPager = getView().findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(CrowdMDetailFragment.newInstance(id_crowd), "Detail");
        adapter.addFragment(CrowdMMediaFragment.newInstance(id_crowd), "Proposal");
        adapter.addFragment(CrowdMProgressFragment.newInstance(id_crowd), "Progress");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        //viewPager.setOffscreenPageLimit(2);
    }
}
