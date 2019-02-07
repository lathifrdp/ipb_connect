package com.example.lathifrdp.demoapp.fragment.bookmark;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.fragment.bookmark.sharing.BookmarkSharingFragment;
import com.example.lathifrdp.demoapp.fragment.post.sharing.PostSharingFragment;
import com.example.lathifrdp.demoapp.helper.SessionManager;

public class BookmarkFragment extends Fragment{

    RelativeLayout sharing;
    SessionManager sessionManager;
    String status;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookmark,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Bookmark");
        sharing = (RelativeLayout) getView().findViewById(R.id.bookmark_sharing);

        sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new BookmarkSharingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
