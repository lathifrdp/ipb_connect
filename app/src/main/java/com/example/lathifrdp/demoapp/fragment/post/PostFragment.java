package com.example.lathifrdp.demoapp.fragment.post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;

public class PostFragment extends Fragment{

    RelativeLayout job, event, memories, sharing, group, crowd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Post Activity");
        job = (RelativeLayout) getView().findViewById(R.id.post_job);
        event = (RelativeLayout) getView().findViewById(R.id.post_event);
        memories = (RelativeLayout) getView().findViewById(R.id.post_memories);
        sharing = (RelativeLayout) getView().findViewById(R.id.post_sharing);
        group = (RelativeLayout) getView().findViewById(R.id.post_group);
        crowd = (RelativeLayout) getView().findViewById(R.id.post_crowd);

        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = null;
                fragment = new PostJobFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new PostEventFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        memories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new PostMemoriesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new PostSharingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new PostGroupFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        crowd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new PostCrowdFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }
}
