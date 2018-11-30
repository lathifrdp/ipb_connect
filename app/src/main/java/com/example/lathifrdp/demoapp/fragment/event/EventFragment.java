package com.example.lathifrdp.demoapp.fragment.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.EventList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Event;
import com.example.lathifrdp.demoapp.response.EventResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventFragment extends Fragment{

    private String title;
    TextView tv;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    EventList adapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    private int page = 1;
    private boolean isRefresh = false;
    private List<Event> listEvent;
    private int limitpage=0;
    Bundle bundle;
    String stat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Daftar Event");
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Create", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Fragment createFragment = null;
                createFragment = new CreateEventFragment();
                createFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, createFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        listView=(ListView)getView().findViewById(R.id.listEvent);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        sessionManager = new SessionManager(getActivity());
        //bundle = this.getArguments();

        if(bundle != null){
            page=1;
        }
        else {
            page=1;
        }
        //Toast.makeText(getActivity(), "stat " + stat, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), "page " + page, Toast.LENGTH_SHORT).show();
        loadDataEvent();

        bundle = new Bundle();
        listEvent = new ArrayList<>();
        adapter= new EventList(listEvent,getActivity());
        listView.setAdapter(adapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page=1;
                loadDataEvent();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //final String str= listEvent.get(i).getFullName();

                Fragment newFragment = null;
                newFragment = new DetailEventFragment();

                //bundle.putString("nama",listUser.get(i).getFullName()); // Put anything what you want
                bundle.putString("id",listEvent.get(i).getId()); // Put anything what you want
                //bundle.putString("email",listUser.get(i).getEmail()); // Put anything what you want

                newFragment.setArguments(bundle);
                //Toast.makeText(getActivity(), listEvent.get(i).getId(), Toast.LENGTH_SHORT).show();
//
                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.screen_area, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE && listView.getLastVisiblePosition() ==
                        adapter.getSize() - 1){
                    if(page<limitpage+1) {
                        loadDataEvent();
                        //Toast.makeText(getActivity(), "lanjut " + page, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //we don't need this method, so we leave it empty
            }
        });

    }

    private void loadDataEvent(){

        //spinner = (Spinner) getView().findViewById(R.id.prodiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<EventResponse> call = apiService.getEvent("JWT "+ sessionManager.getKeyToken(),page);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    if(isRefresh) adapter.setList(response.body().getEvent());
                    else adapter.addList(response.body().getEvent());
                    isRefresh = false;
                    adapter.notifyDataSetChanged();

                    int total = response.body().getTotal();
                    int limit = response.body().getLimit();
                    limitpage = (int)Math.ceil((double)total/limit);
                    page++;

                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
