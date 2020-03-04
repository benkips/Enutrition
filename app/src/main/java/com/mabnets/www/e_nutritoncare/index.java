package com.mabnets.www.e_nutritoncare;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.FragmentManager;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.core.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.caching.FileCacher;
import com.kosalgeek.android.json.JsonConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class index extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Dialog mydialog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String locotxt;
    private final int CAMERA_REQUEST = 13323;
    private ProgressDialog progressDialog;
    private FileCacher<String> locationcacher;
    private Mycommand mycommand;
    private ArrayAdapter<String> locationadapter;
    private ArrayList<String> title;
    private String phones;
    private String pass;
    private TextView profile_name;
    private TextView profile_no;
    private String name;
    private FileCacher<String> offline_cachertransfer;
    private FileCacher<String> offline_cachertransfertwo;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    final String Tag = this.getClass().getName();
    private  List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("E-nutritioncare");
        mydialog = new Dialog(this);
        mycommand = new Mycommand(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("processing");
        locationcacher = new FileCacher<>(getApplicationContext(), "location.txt");
        offline_cachertransfer = new FileCacher<>(getApplicationContext(), "offline.txt");
        offline_cachertransfertwo = new FileCacher<>(getApplicationContext(), "offlineprogressive.txt");


        preferences = getSharedPreferences("logininfo.conf", MODE_PRIVATE);
        phones = preferences.getString("phone", "");
        pass = preferences.getString("pass", "");
        locotxt = preferences.getString("location", "");
        /*name = preferences.getString("name", "");*/
        mycommand = new Mycommand(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View NavHeader = navigationView.getHeaderView(0);

        profile_name = (TextView) NavHeader.findViewById(R.id.profile_name);
        profile_no = (TextView) NavHeader.findViewById(R.id.profile_number);

        profile_name.setText("");
        profile_no .setText("");

        androidx.core.app.Fragment fragment = new home_menu();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_index, fragment).commit();
        permission();
        if (locotxt.equals("")) {
            location_finder();
        }


        /*showpopup();*/

        name = preferences.getString("name", "");
        checkForPhonePermission();
        Transferoflinework();
        Transferoflineworktwo();
        updatenavbar();

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
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            editor = preferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(index.this, Login.class));
            index.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        int id = item.getItemId();

        if (id == R.id.my_proff) {
            // Handle the camera action
            androidx.core.app.Fragment fragment = new my_profile();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_index, fragment).addToBackStack(null).commit();
        } else if (id == R.id.edit_location) {
            androidx.core.app.Fragment fragment = new change_location();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_index, fragment).addToBackStack(null).commit();
        } else if (id == R.id.info) {
            androidx.core.app.Fragment fragment = new about_app();
            getSupportFragmentManager().beginTransaction().add(new home_menu(),"Home").addToBackStack("Home").replace(R.id.framelayout_index, fragment).commit();
        } else if (id == R.id.developer) {
            androidx.core.app.Fragment fragment = new Developer();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_index, fragment).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

    public void showpopup() {
        Button selectbtn;
        final Spinner locations;
        TextView closebtn;
        mydialog.setContentView(R.layout.location_popup);

        selectbtn = (Button) mydialog.findViewById(R.id.popselectbtn);
        locations = (Spinner) mydialog.findViewById(R.id.popspinner);
        closebtn = (TextView) mydialog.findViewById(R.id.closebtn);

        locations.setAdapter(locationadapter);

        locations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object beneitem = parent.getItemAtPosition(position);
                locotxt = (String) beneitem;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("location", locotxt);
                editor.apply();
                /* Toast.makeText(index.this, locotxt, Toast.LENGTH_SHORT).show();*/
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(index.this, "please choose a location", Toast.LENGTH_SHORT).show();
            }
        });

        selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locotxt = locations.getSelectedItem().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("location", locotxt);
                editor.apply();
                Toast.makeText(index.this, locotxt + " selected", Toast.LENGTH_SHORT).show();
                mydialog.dismiss();

            }
        });

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(index.this, "select location first", Toast.LENGTH_SHORT).show();
            }
        });
        mydialog.show();
    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(index.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(index.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_REQUEST);
            }
        }
    }

    private void location_finder() {
        String url = "http://sadiq.mabnets.com/location_fetcher.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*Toast.makeText(index.this, response, Toast.LENGTH_SHORT).show();*/
                ArrayList<loco> locodetails = new JsonConverter<loco>().toArrayList(response, loco.class);
                title = new ArrayList<String>();
                for (loco value : locodetails) {
                    title.add(value.location);
                }
                locationadapter = new ArrayAdapter<String>(index.this, R.layout.spinner_layout_common, title);
                try {
                    locationcacher.writeCache(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showpopup();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    progressDialog.dismiss();
                  /*  Toast.makeText(index.this, "error time out ", Toast.LENGTH_SHORT).show();*/
                    try {
                        String t = locationcacher.readCache();
                        ArrayList<loco> locodetails = new JsonConverter<loco>().toArrayList(t, loco.class);
                        title = new ArrayList<String>();
                        for (loco value : locodetails) {
                            title.add(value.location);
                        }
                        locationadapter = new ArrayAdapter<String>(index.this, R.layout.spinner_layout_common, title);
                        showpopup();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else if (error instanceof NoConnectionError) {
                    progressDialog.dismiss();
                    /*Toast.makeText(index.this, "error no connection", Toast.LENGTH_SHORT).show();*/
                } else if (error instanceof NetworkError) {
                    progressDialog.dismiss();
                    /*Toast.makeText(index.this, "error network error", Toast.LENGTH_SHORT).show();*/
                } else if (error instanceof AuthFailureError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "errorin Authentication", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error while parsing", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error  in server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ClientError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error with Client", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error while loading", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mycommand.add(stringRequest);
        progressDialog.show();
        mycommand.execute();
        mycommand.remove(stringRequest);
    }

    private void updatenavbar() {
        String url = "http://sadiq.mabnets.com/navbar_details.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                /* Toast.makeText(index.this, response, Toast.LENGTH_SHORT).show();*/
                ArrayList<navigation_details> navdetails = new JsonConverter<navigation_details>().toArrayList(response, navigation_details.class);
                ArrayList<String> titlee = new ArrayList<>();
                for (navigation_details value : navdetails) {
                    titlee.add(value.username);
                }
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name", titlee.get(0));
                editor.apply();
                profile_name.setText(name);
                profile_no.setText(phones);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    progressDialog.dismiss();
                    profile_name.setText(name);
                    profile_no.setText(phones);
                  /*  Toast.makeText(index.this, "error time out ", Toast.LENGTH_SHORT).show();*/
                } else if (error instanceof NoConnectionError) {
                    progressDialog.dismiss();
                    profile_name.setText(name);
                    profile_no.setText(phones);
                   /* Toast.makeText(index.this, "error no connection", Toast.LENGTH_SHORT).show();*/
                } else if (error instanceof NetworkError) {
                    progressDialog.dismiss();

                    Toast.makeText(index.this, "error network error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "errorin Authentication", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error while parsing", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error  in server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ClientError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error with Client", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error while loading", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phones);
                return params;
            }
        };
        mycommand.add(stringRequest);
        progressDialog.show();
        mycommand.execute();
        mycommand.remove(stringRequest);
    }

    private void checkForPhonePermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission not yet granted. Use requestPermissions().
            Toast.makeText(this, "call permission not granted", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    private void Transferoflinework() {
        if (offline_cachertransfer.hasCache()) {
            try {
                 list =offline_cachertransfer.getAllCaches();
                for(String text : list){
                    //do something with text

                }
                Log.d(Tag, String.valueOf(list));
            } catch (IOException e) {
                e.printStackTrace();
            }
        String url="http://sadiq.mabnets.com/offline_saving.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d(Tag,response);
                if(response.contains("offline data recieved")){
                    try {
                        offline_cachertransfer.clearCache();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            //read cache
        /*    try {
                String t = offline_cachertransfer.readCache();
                Log.d(Tag, t);
            } catch (IOException e) {
                e.printStackTrace();
            }*/




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                   /* Toast.makeText(index.this, "error time out ", Toast.LENGTH_SHORT).show();*/
                } else if (error instanceof NoConnectionError) {
                    progressDialog.dismiss();
                   /* Toast.makeText(index.this, "error no connection", Toast.LENGTH_SHORT).show();*/
                } else if (error instanceof NetworkError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error network error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "errorin Authentication", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error while parsing", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error  in server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ClientError) {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error with Client", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(index.this, "error while loading", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("offline_data",String.valueOf(list));
                return params;
            }
        };
        mycommand.add(stringRequest);
        progressDialog.show();
        mycommand.execute();
        mycommand.remove(stringRequest);
    }

    }
    private void Transferoflineworktwo() {
        if (offline_cachertransfertwo.hasCache()) {
            try {
                list =offline_cachertransfertwo.getAllCaches();
                for(String text : list){
                    //do something with text

                }
                Log.d(Tag, String.valueOf(list));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String url="http://sadiq.mabnets.com/progressive_offline.php";
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Log.d(Tag,response);
                    if(response.contains("offline data recieved")){
                        try {
                            offline_cachertransfertwo.clearCache();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //read cache
        /*    try {
                String t = offline_cachertransfer.readCache();
                Log.d(Tag, t);
            } catch (IOException e) {
                e.printStackTrace();
            }*/




                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError) {
                        /* Toast.makeText(index.this, "error time out ", Toast.LENGTH_SHORT).show();*/
                    } else if (error instanceof NoConnectionError) {
                        progressDialog.dismiss();
                        /* Toast.makeText(index.this, "error no connection", Toast.LENGTH_SHORT).show();*/
                    } else if (error instanceof NetworkError) {
                        progressDialog.dismiss();
                        Toast.makeText(index.this, "error network error", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        progressDialog.dismiss();
                        Toast.makeText(index.this, "errorin Authentication", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        progressDialog.dismiss();
                        Toast.makeText(index.this, "error while parsing", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        progressDialog.dismiss();
                        Toast.makeText(index.this, "error  in server", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ClientError) {
                        progressDialog.dismiss();
                        Toast.makeText(index.this, "error with Client", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(index.this, "error while loading", Toast.LENGTH_SHORT).show();
                    }
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("offline_data",String.valueOf(list));
                    return params;
                }
            };
            mycommand.add(stringRequest);
            progressDialog.show();
            mycommand.execute();
            mycommand.remove(stringRequest);
        }

    }

}
