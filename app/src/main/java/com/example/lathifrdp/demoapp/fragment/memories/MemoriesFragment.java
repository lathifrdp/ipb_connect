package com.example.lathifrdp.demoapp.fragment.memories;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.MemoriesAdapter;
import com.example.lathifrdp.demoapp.adapter.MyRecyclerAdapter;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Memory;
import com.example.lathifrdp.demoapp.response.MemoriesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemoriesFragment extends Fragment{

    //Deceleration of list of  GalleryItems
    public List<Memory> galleryItems;
    private RecyclerView recyclerViewGallery;
    //Read storage permission request code
    //private static final int RC_READ_STORAGE = 5;
    //MemoriesAdapter mGalleryAdapter;
    MyRecyclerAdapter mGalleryAdapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    //ProgressDialog pd;
    private int page = 1;
    private int limitpage=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memories,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Memories");
        sessionManager = new SessionManager(getActivity());

        recyclerViewGallery= (RecyclerView) getView().findViewById(R.id.recc);
        recyclerViewGallery.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //Create RecyclerView Adapter
        //mGalleryAdapter = new MemoriesAdapter(getActivity());
        loadMemories();
        //set adapter to RecyclerView

    }

    private void loadMemories() {

        try {
            apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<MemoriesResponse> call = apiService.getMemories("JWT "+ sessionManager.getKeyToken(),page);
            call.enqueue(new Callback<MemoriesResponse>() {

                @Override
                public void onResponse(Call<MemoriesResponse> call, final Response<MemoriesResponse> response) {
                    if (response.isSuccessful()) {

                        galleryItems = response.body().getMemories();
                        mGalleryAdapter = new MyRecyclerAdapter(galleryItems,R.layout.row_memories);
                        //mGalleryAdapter.addGalleryItems(galleryItems);
                        recyclerViewGallery.setAdapter(mGalleryAdapter);
                        //recyclerView.setAdapter(new SharingAdapter(getActivity(), sharingArrayList));
                        recyclerViewGallery.smoothScrollToPosition(0);
                        //Toast.makeText(getActivity(), movieArrayList.toString(), Toast.LENGTH_SHORT).show();
                        //swipeContainer.setRefreshing(false);
                        //pd.hide();
                    }
                }

                @Override
                public void onFailure(Call<MemoriesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getActivity(), "Error Fetching Data!", Toast.LENGTH_SHORT).show();
//                    pd.hide();
                }

            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
