package com.example.lathifrdp.demoapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.example.lathifrdp.demoapp.fragment.bookmark.BookmarkFragment;
import com.example.lathifrdp.demoapp.fragment.event.EventFragment;
import com.example.lathifrdp.demoapp.fragment.explore.ExploreFragment;
import com.example.lathifrdp.demoapp.fragment.group.GroupFragment;
import com.example.lathifrdp.demoapp.fragment.home.HomeFragment;
import com.example.lathifrdp.demoapp.fragment.job.JobFragment;
import com.example.lathifrdp.demoapp.fragment.memories.MemoriesFragment;
import com.example.lathifrdp.demoapp.fragment.post.PostFragment;
import com.example.lathifrdp.demoapp.fragment.sharing.SharingFragment;
import com.example.lathifrdp.demoapp.helper.SessionManager;

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
        //Toast.makeText(MainActivity.this,sessionManager.getKeyProdi(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this,sessionManager.getKeyUsertype(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this,sessionManager.getKeyToken(),Toast.LENGTH_SHORT).show();

        NavigationView naview = (NavigationView) findViewById(R.id.nav_view);
        View hView =  naview.getHeaderView(0);
        TextView nav_email = (TextView)hView.findViewById(R.id.textView);
        TextView nav_fullname = (TextView)hView.findViewById(R.id.fullName);
        nav_photo = (CircleImageView) hView.findViewById(R.id.imageView);

        nav_email.setText(sessionManager.getKeyEmail());
        nav_fullname.setText(sessionManager.getKeyFullname());


        // Loader image - will be shown before loading image
        int loader = R.drawable.ic_menu_share;

        // Imageview to show
        //ImageView image = (ImageView) findViewById(R.id.image);

        // Image url
        //String URL = "http://182.23.70.28:3501/uploads/profile/"+sessionManager.getKeyPhoto();
        String a = sessionManager.getKeyPhoto();
        //a = null;
        if(a == null) {
            URL = "http://172.17.66.42:3501/uploads/profile/person.png";
        }
        else{
            URL = "http://172.17.66.42:3501/uploads/profile/"+sessionManager.getKeyPhoto();
        }
        GetXMLTask task = new GetXMLTask();
        // Execute the task
        task.execute(new String[] { URL });

        // ImageLoader class instance
        //ImageLoader imgLoader = new ImageLoader(getApplicationContext());

        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView
        //imgLoader.DisplayImage(image_url, loader, nav_photo);

        //encode image to base64 string
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),sessionManager.getKeyPhoto());
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes = baos.toByteArray();
//        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //decode base64 string to image
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] imageBytes = baos.toByteArray();
//        imageBytes = Base64.decode(sessionManager.getKeyPhoto(), Base64.DEFAULT);
//        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        //tes
//        byte[] photo = getIntent().getByteArrayExtra("photoS");
//        ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
        //byte[] decodedString = Base64.decode(sessionManager.getKeyPhoto(), Base64.DEFAULT);
        //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        Bitmap theImage= BitmapFactory.decodeStream(imageStream);
//        nav_photo.setImageBitmap(theImage);
        //tes

        //Resources resources = getResources();
        //RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(resources, decodedByte);
        //dr.setCircular(true);
//        byte[] decodedString = Base64.decode(sessionManager.getKeyPhoto(), Base64.DEFAULT);
//        //Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        ByteArrayInputStream imageStream = new ByteArrayInputStream(decodedString);
//        Bitmap theImage= BitmapFactory.decodeStream(imageStream);
        //nav_photo.setImageDrawable(dr);


        //Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0 ,decodedString.length);


        //Toast.makeText(MainActivity.this,theImage.toString(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this,sessionManager.getKeyPhoto(),Toast.LENGTH_SHORT).show();
        //Log.d("OnFailure", decodedString.toString());

        //String imgString = Base64.encodeToString(sessionManager.getKeyPhoto().getBytes(),Base64.NO_WRAP);
        //byte[] decodedString = Base64.decode(sessionManager.getKeyPhoto(), Base64.DEFAULT);
        //ByteArrayInputStream imageStream = new ByteArrayInputStream(decodedString);
        //Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
       // Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        //nav_photo.setImageBitmap(imgBitMap);
//        Toast.makeText(MainActivity.this,theImage.toString(),Toast.LENGTH_SHORT).show();
//        Log.d("OnFailure", theImage.toString());
        //Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);

//        mEmail = (TextView) findViewById(R.id.textView);
//        mFullname = (TextView) findViewById(R.id.fullname);
//        mPhoto = (ImageView) findViewById(R.id.imageView);
//
//        TextView email = mEmail;
//        email.setText(sessionManager.getKeyEmail());
        //mEmail.setText(sessionManager.getKeyEmail());
        //mFullname.setText(sessionManager.getKeyFullname());
        //mPhoto.setImageBitmap(sessionManager.getKeyPhoto());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        Fragment fragmentx = null;
//        fragmentx = new HomeFragment();

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.replace(R.id.screen_area, fragmentx);
//        ft.commit();
        //HomeFragment hf = (HomeFragment)
        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager().findFragmentById(R.id.homeFragment);
        setFragment(new HomeFragment());
//        HomeFragment fragmenthome = (HomeFragment)
//                getSupportFragmentManager().findFragmentById(R.id.homeFragment);
    }

    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            nav_photo.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
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
        } else {
            super.onBackPressed();
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

        int id = item.getItemId();

        if (id == R.id.home) {
            fragment = new HomeFragment();
        }
        else if (id == R.id.job) {
            fragment = new JobFragment();
        }
        else if (id == R.id.event) {
            fragment = new EventFragment();
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
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
