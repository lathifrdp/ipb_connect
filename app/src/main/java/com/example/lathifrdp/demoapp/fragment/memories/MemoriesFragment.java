package com.example.lathifrdp.demoapp.fragment.memories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.MemoriesList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Memory;
import com.example.lathifrdp.demoapp.response.MemoriesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class MemoriesFragment extends Fragment{

    //Deceleration of list of  GalleryItems
    public List<Memory> galleryItems;
    private RecyclerView recyclerViewGallery;
    //Read storage permission request code
    //private static final int RC_READ_STORAGE = 5;
    MemoriesList mGalleryAdapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    //ProgressDialog pd;
    private int page = 1;
    private int limitpage=0;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memories,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Memories");
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab_memories);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment createFragment = null;
                createFragment = new CreateMemoriesFragment();
                createFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, createFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        sessionManager = new SessionManager(getActivity());

        recyclerViewGallery= (RecyclerView) getView().findViewById(R.id.recc);
        recyclerViewGallery.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //Create RecyclerView Adapter

        if(bundle != null){
            page=1;
        }
        else {
            page=1;
        }
        loadMemories();

        galleryItems = new ArrayList<>();
        mGalleryAdapter= new MemoriesList(galleryItems);

        recyclerViewGallery.setAdapter(mGalleryAdapter);
        recyclerViewGallery.smoothScrollToPosition(0);
        //set adapter to RecyclerView
//        recyclerViewGallery.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int pastVisibleItems, visibleItemsCount, totalItemCount;
////                visibleItemsCount = recyclerViewGallery.getChildCount();
//
//                if (dy > 0) {
////                    if(visibleItemsCount == mGalleryAdapter.getItemCount()-1){
//                    if (page<limitpage+1) {
//                        // method where you get your images
//                        loadMemories();
//                    }
////                    }
//
//                }
//            }
//        });

        recyclerViewGallery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                GridLayoutManager layoutManager = ((GridLayoutManager)recyclerViewGallery.getLayoutManager());
//                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

                if(newState == SCROLL_STATE_IDLE){
                    if (page<limitpage+1) {
                        // method where you get your images
                        loadMemories();
                        //Toast.makeText(getActivity(), "lanjut " + page, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void loadMemories() {

        try {
            apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<MemoriesResponse> call = apiService.getMemories("JWT "+ sessionManager.getKeyToken(),page);
            call.enqueue(new Callback<MemoriesResponse>() {

                @Override
                public void onResponse(Call<MemoriesResponse> call, final Response<MemoriesResponse> response) {
                    if (response.isSuccessful()) {

//                        galleryItems = response.body().getMemories();
//                        mGalleryAdapter = new MemoriesList(galleryItems);
//                        //mGalleryAdapter.addGalleryItems(galleryItems);
//                        recyclerViewGallery.setAdapter(mGalleryAdapter);
//                        //recyclerView.setAdapter(new SharingAdapter(getActivity(), sharingArrayList));
//                        recyclerViewGallery.smoothScrollToPosition(0);
                        mGalleryAdapter.addList(response.body().getMemories());
                        mGalleryAdapter.notifyDataSetChanged();
                        //Toast.makeText(getActivity(), movieArrayList.toString(), Toast.LENGTH_SHORT).show();
                        //swipeContainer.setRefreshing(false);
                        //pd.hide();
                        int total = response.body().getTotal();
                        int limit = response.body().getLimit();
                        limitpage = (int)Math.ceil((double)total/limit);

                        //Log.e("limitpage: ",String.valueOf(limitpage));
                        page++;
                    }
                }

                @Override
                public void onFailure(Call<MemoriesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getActivity(), "Gagal Mengambil Data!", Toast.LENGTH_SHORT).show();
//                    pd.hide();
                }

            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
