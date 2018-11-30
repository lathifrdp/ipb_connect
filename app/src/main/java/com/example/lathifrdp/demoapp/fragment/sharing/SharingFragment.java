package com.example.lathifrdp.demoapp.fragment.sharing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

//        Toolbar toolbar = getView().findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Daftar Pengetahuan");
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab_sharing);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Create", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Fragment createFragment = null;
                createFragment = new CreateSharingFragment();
                //createFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, createFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ViewPager viewPager = getView().findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(TerbaruFragment.newInstance(), "Terbaru");
        adapter.addFragment(PopularFragment.newInstance(), "Popular");
        adapter.addFragment(TeknologiFragment.newInstance(), "Teknologi");
        adapter.addFragment(DesainFragment.newInstance(), "Desain");
        adapter.addFragment(BisnisFragment.newInstance(), "Bisnis");
        adapter.addFragment(KesehatanFragment.newInstance(), "Kesehatan");
        adapter.addFragment(UmumFragment.newInstance(), "Umum");
        viewPager.setAdapter(adapter);
        //viewPager.setOffscreenPageLimit(2);
    }
}
