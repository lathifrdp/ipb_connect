package com.example.lathifrdp.demoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.fragment.bookmark.BookmarkFragment;
import com.example.lathifrdp.demoapp.fragment.event.EventFragment;
import com.example.lathifrdp.demoapp.fragment.explore.ExploreFragment;
import com.example.lathifrdp.demoapp.fragment.explore.ProfileFragment;
import com.example.lathifrdp.demoapp.fragment.explore.UserFragment;
import com.example.lathifrdp.demoapp.fragment.group.GroupFragment;
import com.example.lathifrdp.demoapp.fragment.home.HomeFragment;
import com.example.lathifrdp.demoapp.fragment.job.JobFragment;
import com.example.lathifrdp.demoapp.fragment.memories.MemoriesFragment;
import com.example.lathifrdp.demoapp.fragment.post.PostFragment;
import com.example.lathifrdp.demoapp.fragment.sharing.SharingFragment;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mFullname,mEmail;
    private ImageView mPhoto;
    SessionManager sessionManager;
    //ImageView nav_photo;
    CircleImageView nav_photo;
    public String URL;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Kirim email", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        sessionManager = new SessionManager(MainActivity.this);

        NavigationView naview = (NavigationView) findViewById(R.id.nav_view);
        View hView =  naview.getHeaderView(0);
        TextView nav_email = (TextView)hView.findViewById(R.id.textView);
        TextView nav_fullname = (TextView)hView.findViewById(R.id.fullName);
        nav_photo = (CircleImageView) hView.findViewById(R.id.imageView);

        nav_email.setText(sessionManager.getKeyEmail());
        nav_fullname.setText(sessionManager.getKeyFullname());

        String url = "http://182.23.70.28:3501/uploads/profile/"+sessionManager.getKeyPhoto();
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.alumni2)
                .error(R.drawable.logoipb)
                .into(nav_photo);

        nav_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "profil", Toast.LENGTH_SHORT).show();
                bundle = new Bundle();
                bundle.putString("nama",sessionManager.getKeyFullname());
                bundle.putString("id",sessionManager.getKeyId());
                bundle.putString("email",sessionManager.getKeyEmail());
                Fragment fragment = null;
                fragment = new ProfileFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.screen_area, fragment);
                ft.addToBackStack(null);
                ft.commit();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager().findFragmentById(R.id.homeFragment);
        setFragment(new HomeFragment());
    }

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.screen_area, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
            //finish();
//            Intent b = new Intent(Intent.ACTION_MAIN);
//            b.addCategory(Intent.CATEGORY_HOME);
//            b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(b);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        SessionManager sessionManager;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "Settings Coming Soon", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_logout) {
            sessionManager = new SessionManager(MainActivity.this);
            sessionManager.logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        bundle = new Bundle();

        int id = item.getItemId();

        if (id == R.id.home) {
            fragment = new HomeFragment();
        }
        else if (id == R.id.job) {
            fragment = new JobFragment();
        }
        else if (id == R.id.event) {
            fragment = new EventFragment();
            //bundle.putString("event_stat","1");
            //fragment.setArguments(bundle);
        }
        else if (id == R.id.explore) {
            fragment = new ExploreFragment();
        }
        else if (id == R.id.memories) {
            fragment = new MemoriesFragment();
        }
        else if (id == R.id.sharing) {
            fragment = new SharingFragment();
        }
        else if (id == R.id.group) {
            fragment = new GroupFragment();
        }
        else if (id == R.id.bookmark) {
            fragment = new BookmarkFragment();
        }
        else if (id == R.id.post) {
            fragment = new PostFragment();
        }

        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
