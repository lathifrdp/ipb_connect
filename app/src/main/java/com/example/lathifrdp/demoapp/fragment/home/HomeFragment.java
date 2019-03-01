package com.example.lathifrdp.demoapp.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.MainActivity;
import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.EventHomeList;
import com.example.lathifrdp.demoapp.adapter.EventList;
import com.example.lathifrdp.demoapp.adapter.NewsList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiClient2;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.fragment.event.DetailEventFragment;
import com.example.lathifrdp.demoapp.helper.MyValueFormatter;
import com.example.lathifrdp.demoapp.helper.NonScrollListView;
import com.example.lathifrdp.demoapp.model.Event;
import com.example.lathifrdp.demoapp.model.News;
import com.example.lathifrdp.demoapp.response.CountResponse;
import com.example.lathifrdp.demoapp.helper.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.lathifrdp.demoapp.response.EventResponse;
import com.example.lathifrdp.demoapp.response.NewsResponse;
import com.example.lathifrdp.demoapp.response.StatusResponse;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ApiInterface apiService;
    SessionManager sessionManager;
    PieChart pieChart;
    PieChart pieChart2;
    public Integer laki;
    public Integer perempuan;
    public Integer alumni;
    public Integer mahasiswa;
    public Integer total;
    TextView HomeProdi;
    TextView HomeKet;
    TextView HomeTotal;
    TextView HomeTotalKet;
    SwipeRefreshLayout mSwipeRefreshLayout;
    //private TextView HomeProdi, HomeKet;
    NonScrollListView listView;
    NewsList adapter;
    private int page = 1;
    private boolean isRefresh = false;
    private List<News> listNews;
    private int limitpage=0, limitpageEvent=0;
    Bundle bundle;
    TextView load_berita, load_acara;
    NonScrollListView listViewEvent;
    EventHomeList adapterEvent;
    private int pageEvent = 1;
    private List<Event> listEvent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Beranda");
        sessionManager = new SessionManager(getActivity());

        HomeProdi = (TextView) getView().findViewById(R.id.homeProdi);
        HomeKet = (TextView) getView().findViewById(R.id.homeKet);
        HomeTotal = (TextView) getView().findViewById(R.id.homeTotal);
        HomeTotalKet = (TextView) getView().findViewById(R.id.homeTotalKet);
        listView = (NonScrollListView) getView().findViewById(R.id.listNews);
        load_berita = (TextView) getView().findViewById(R.id.memuat_lebih_berita);
        listViewEvent = (NonScrollListView) getView().findViewById(R.id.listEventHome);
        load_acara = (TextView) getView().findViewById(R.id.memuat_lebih_acara);

        if(bundle != null){
            page=1;
            pageEvent = 1;
        }
        else {
            page=1;
            pageEvent = 1;
        }

        //pieChart = (PieChart) getView().findViewById(R.id.piechart_gender);
        //pieChart2 = (PieChart) getView().findViewById(R.id.piechart_account);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                loadCount();
//                loadStatus();
//                isRefresh = true;
//                page=1;
//                //Toast.makeText(getActivity(), "page refresh: "+page, Toast.LENGTH_SHORT).show();
//                loadDataNews();
//                //adapter.notifyDataSetChanged();
                Fragment newFragment = null;
                newFragment = new HomeFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        loadCount();
        loadStatus();
        loadDataNews();
        loadDataEvent();

        bundle = new Bundle();
        listNews = new ArrayList<>();
        adapter= new NewsList(listNews,getActivity());
        listView.setAdapter(adapter);

        listEvent = new ArrayList<>();
        adapterEvent = new EventHomeList(listEvent,getActivity());
        listViewEvent.setAdapter(adapterEvent);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment newFragment = null;
                newFragment = new DetailNewsFragment();
                bundle.putString("id",listNews.get(i).getId());
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        listViewEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment newFragment = null;
                newFragment = new DetailEventFragment();
                bundle.putString("id",listEvent.get(i).getId());
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        load_berita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page < limitpage + 1) {
                    loadDataNews();
                }
                if(page>=limitpage){
                    load_berita.setVisibility(View.GONE);
                }
            }
        });

        load_acara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pageEvent < limitpageEvent + 1) {
                    loadDataEvent();
                }
                if(pageEvent>=limitpageEvent){
                    load_acara.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadDataEvent(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        final int limit = 3;

        Call<EventResponse> call = apiService.getEventHome("JWT "+ sessionManager.getKeyToken(),limit,pageEvent);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    if(isRefresh) adapterEvent.setList(response.body().getEvent());
                    else adapterEvent.addList(response.body().getEvent());
                    isRefresh = false;
                    adapterEvent.notifyDataSetChanged();

                    int total = response.body().getTotal();
                    int batas = response.body().getLimit();
                    limitpageEvent = (int)Math.ceil((double)total/batas);
                    pageEvent++;

                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadDataNews(){

        apiService = ApiClient2.getClient().create(ApiInterface.class);
        final String language = "ID";
        final String order = "Latest";
        final Integer perPage = 3;

        Call<NewsResponse> call = apiService.getNews("Bearer 4857d8cb-af10-3ceb-91f3-fe025244e9eb",language,page,perPage,order);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    NewsResponse newsResponse = response.body();
                    if(isRefresh) {
                        adapter.setList(newsResponse.getNews());
                    }
                    else adapter.addList(newsResponse.getNews());
                    isRefresh = false;
                    adapter.notifyDataSetChanged();
                    limitpage = newsResponse.getTotalpages();
                    page++;
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadStatus(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<StatusResponse> call = apiService.getStatus("JWT "+ sessionManager.getKeyToken(), sessionManager.getKeyId());
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    StatusResponse sr = response.body();
                    sessionManager.updateCrowdfunding(sr.getIsCrowdfunding());
                    //Toast.makeText(getActivity(), "status: "+sr.getIsCrowdfunding(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadCount(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CountResponse> call = apiService.getCount("JWT "+ sessionManager.getKeyToken());
        call.enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    CountResponse cr = response.body();
                    laki = cr.getCount().getGender().getLaki();
                    perempuan = cr.getCount().getGender().getPerempuan();
                    alumni = cr.getCount().getUserType().getAlumni();
                    mahasiswa = cr.getCount().getUserType().getMahasiswa();
                    total = cr.getTotal();

                    HomeProdi.setText(sessionManager.getKeyProdi());
                    HomeKet.setText("Anda adalah "+sessionManager.getKeyUsertype()+" dari Program Studi "+sessionManager.getKeyProdi());
                    HomeTotal.setText(total+" Pengguna");
                    HomeTotalKet.setText("Sebanyak "+total+" Alumni dan Mahasiswa yang telah terdaftar");
                }
            }

            @Override
            public void onFailure(Call<CountResponse> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
//    public void setPieChartGender() {
//
//        pieChart.setUsePercentValues(true);
//        pieChart.getDescription().setEnabled(false);
//        pieChart.setExtraOffsets(0,0,0,0);
//        pieChart.setDragDecelerationFrictionCoef(0.9f);
//        pieChart.setTransparentCircleRadius(61f);
//        pieChart.setHoleColor(Color.WHITE);
//        pieChart.setDrawEntryLabels(false);
//        pieChart.setCenterText("Jenis Kelamin");
//        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
//        //pieChart.animateX(2000, Easing.EasingOption.EaseInBack);
//        ArrayList<PieEntry> yValues = new ArrayList<>();
//        yValues.add(new PieEntry(perempuan,"Perempuan"));
//        yValues.add(new PieEntry(laki,"Laki-laki"));
//
//        PieDataSet dataSet = new PieDataSet(yValues,"");
//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);
//        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        PieData pieData = new PieData((dataSet));
//        pieData.setValueTextSize(10f);
//        pieData.setValueFormatter(new MyValueFormatter());
//        pieData.setValueTextColor(Color.WHITE);
//        pieChart.setData(pieData);
//        //PieChart Ends Here
//    }
//
//    public void setPieChartAccount() {
//
//        pieChart2.setUsePercentValues(true);
//        pieChart2.getDescription().setEnabled(false);
//        pieChart2.setExtraOffsets(0,0,0,0);
//        pieChart2.setDragDecelerationFrictionCoef(0.9f);
//        pieChart2.setTransparentCircleRadius(61f);
//        pieChart2.setHoleColor(Color.WHITE);
//        pieChart2.setDrawEntryLabels(false);
//        pieChart2.setCenterText("Pengguna");
//        pieChart2.animateY(2000, Easing.EasingOption.EaseInOutCubic);
//        //pieChart2.animateX(2000, Easing.EasingOption.EaseInBack);
//        ArrayList<PieEntry> yValues2 = new ArrayList<>();
//        yValues2.add(new PieEntry(alumni,"Alumni"));
//        yValues2.add(new PieEntry(mahasiswa,"Mahasiswa"));
//
//        PieDataSet dataSet2 = new PieDataSet(yValues2,"");
//        dataSet2.setSliceSpace(3f);
//        dataSet2.setSelectionShift(5f);
//        dataSet2.setColors(ColorTemplate.PASTEL_COLORS);
//        PieData pieData2 = new PieData((dataSet2));
//        pieData2.setValueTextSize(10f);
//        pieData2.setValueFormatter(new MyValueFormatter());
//        pieData2.setValueTextColor(Color.WHITE);
//        pieChart2.setData(pieData2);
//        //PieChart Ends Here
//    }
}
