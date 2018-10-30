package com.example.lathifrdp.demoapp.fragment.sharing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.ViewPagerAdapter;

public class SharingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sharing,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = getView().findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(PageFragment.newInstance("Satu"), "Satu");
        adapter.addFragment(PageFragment.newInstance("Dua"), "Dua");
        adapter.addFragment(PageFragment.newInstance("Tiga"), "Tiga");
        viewPager.setAdapter(adapter);
    }
}
