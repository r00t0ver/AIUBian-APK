package com.example.aiubity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  {

    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    WebView portal;
    NavigationView navigationView;
    static  FrameLayout frameLayout;
    public static FragmentManager fm;
    public static FragmentTransaction ft;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.toolbar=findViewById(R.id.toolBar);
        this.bottomNavigationView=findViewById(R.id.bottomNAvigationView);
        this.drawerLayout=findViewById(R.id.drawerLayout);
        this.navigationView=findViewById(R.id.navigationView);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(

                MainActivity.this,drawerLayout,toolbar,R.string.open,R.string.close
        );
        drawerLayout.addDrawerListener(toggle);



        hasInternet(MainActivity.this);
        LoadWebsite("https://www.aiub.edu");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) frameLayout.getLayoutParams();
                TypedValue typedValue = new TypedValue();

                //if (getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
                     int actionBarSize = TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());


                    if (item.getItemId() == R.id.navHome) {


                        fm=getSupportFragmentManager();
                        ft=fm.beginTransaction();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        layoutParams.bottomMargin =20;
                        drawerLayout.closeDrawer(GravityCompat.START);
                        if(!hasInternet(MainActivity.this))
                        {
                            frameLayout.removeAllViews();

                            ft.add(R.id.frameLayout,new NoInternet());
                            ft.commit();
                            return true;
                        }
                        ft.add(R.id.frameLayout,new WebLoad());
                        ft.commit();


                    } else if (item.getItemId() == R.id.navRoutine) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        bottomNavigationView.setVisibility(View.GONE);

                        // Set the new marginTop to 20 (in pixels)
                        layoutParams.bottomMargin = 0;
                        Routine();



                    } else if (item.getItemId() == R.id.navLogout) {

                        MainActivity.super.onBackPressed();
                    }
                    // Apply the modified layout params back to the frameLayout
                    frameLayout.setLayoutParams(layoutParams);
                    return true;
                }

        });



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                if(item.getItemId()==R.id.portal)
                {
                    LoadWebsite("https://portal.aiub.edu");
                }

                else if(item.getItemId()==R.id.website)
                {
                    LoadWebsite("https://www.aiub.edu");
                }
                else if(item.getItemId()==R.id.teams)
                {

                    LoadWebsite("https://www.microsoft.com/en-us/microsoft-teams/log-in");
                    Intent intent=getPackageManager().getLaunchIntentForPackage("com.microsoft.teams");
                    if(intent!=null)
                    {
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Teams not found in your device",Toast.LENGTH_LONG).show();
                        // Microsoft Teams is not installed, prompt the user to install it
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Microsoft Teams is not installed. Do you want to install it from the Play Store?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Redirect to Microsoft Teams app page on Play Store
                                        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW);
                                        playStoreIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.microsoft.teams"));
                                        startActivity(playStoreIntent);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }



                }

                else if(item.getItemId()==R.id.mail)
                {
                    LoadWebsite("https://outlook.live.com/owa/?cobrandid=ab0455a0-8d03-46b9-b18b-df2f57b9e44c&nlp=1");
                    Intent intent=getPackageManager().getLaunchIntentForPackage("com.microsoft.office.outlook");
                    if(intent!=null)
                    {
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Microsoft Outlook not found in your device",Toast.LENGTH_LONG).show();
                        // Microsoft Teams is not installed, prompt the user to install it
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Microsoft Outlook is not installed. Do you want to install it from the Play Store?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Redirect to Microsoft Teams app page on Play Store
                                        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW);
                                        playStoreIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.microsoft.office.outlook"));
                                        startActivity(playStoreIntent);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }

                item.setChecked(true);


                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        if(WebLoad.web.canGoBack())
        {
            WebLoad.web.goBack();
        }
        else {
            super.onBackPressed();
        }

    }

    private void LoadWebsite(String weblink)
    {

        frameLayout=findViewById(R.id.frameLayout);
        frameLayout.removeAllViews();
        String url=weblink;
        fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        WebLoad.link=url;
        if(hasInternet(MainActivity.this))
        {
            ft.add(R.id.frameLayout,new WebLoad());
        }
        else
        {
            ft.add(R.id.frameLayout,new NoInternet());
        }

        ft.commit();
    }
    public static boolean hasInternet(MainActivity context)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null)
        {
            NetworkInfo activeNetwork=connectivityManager.getActiveNetworkInfo();
            return activeNetwork!=null &&activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }
    public void Routine()
    {

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        frameLayout.removeAllViews();
        ft.add(R.id.frameLayout,new Routine());
        ft.commit();
    }


}


