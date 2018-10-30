package com.example.lathifrdp.demoapp.fragment.sharing;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lathifrdp.demoapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {


    private String title;
    private static final String ARG_TITLE = "title";

    public static PageFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);

        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            title = extras.getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_page, container, false);

        TextView textView = v.findViewById(R.id.title);
        textView.setText(title);

        return v;
    }

}
