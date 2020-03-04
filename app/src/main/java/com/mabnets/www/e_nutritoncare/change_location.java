package com.mabnets.www.e_nutritoncare;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class change_location extends Fragment {
    private Spinner locations;
    private String locotxt;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Button  selectbtn;
    private ArrayAdapter<String> locationadapter;
    private ProgressDialog progressDialog;
    private Mycommand mycommand;
    private FileCacher<String> locationcacher;
    private ArrayList<String> title;

    public change_location() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View change_location=inflater.inflate(R.layout.fragment_change_location, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("loading..");
        mycommand=new Mycommand(getContext());
        preferences=getActivity().getSharedPreferences("logininfo.conf",MODE_PRIVATE);

        locations=(Spinner)change_location.findViewById(R.id.change_location);
        selectbtn=(Button) change_location.findViewById(R.id.change_btn);
        locationcacher=new FileCacher<>(getContext(),"location.txt");

       location_finder();



      return change_location;
    }
    private void location_finder(){
        String url="http://sadiq.mabnets.com/location_fetcher.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                ArrayList<loco> locodetails = new JsonConverter<loco>().toArrayList(response, loco.class);
                title = new ArrayList<String>();
                for (loco value :  locodetails) {
                    title.add(value.location);
                }
                locationadapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_layout_common,title);
                try {
                    locationcacher.writeCache(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setupspinner();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError) {
                    progressDialog.dismiss();
                    try {
                        String t=locationcacher.readCache();
                        ArrayList<loco> locodetails = new JsonConverter<loco>().toArrayList(t, loco.class);
                        title = new ArrayList<String>();
                        for (loco value :  locodetails) {
                            title.add(value.location);
                        }
                        locationadapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_layout_common,title);
                        setupspinner();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            /*        Toast.makeText(getContext(), "error time out ", Toast.LENGTH_SHORT).show();*/
                } else if (error instanceof NoConnectionError) {
                    progressDialog.dismiss();
                   /* Toast.makeText(getContext(), "error no connection", Toast.LENGTH_SHORT).show();*/
                } else if (error instanceof NetworkError) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "error network error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "errorin Authentication", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "error while parsing", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "error  in server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ClientError) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "error with Client", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "error while loading", Toast.LENGTH_SHORT).show();
                }




            }
        });
        mycommand.add(stringRequest);
        progressDialog.show();
        mycommand.execute();
        mycommand.remove(stringRequest);
    }
    private  void setupspinner(){

        locations.setAdapter(locationadapter);


        locations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object beneitem = parent.getItemAtPosition(position);
                locotxt=(String) beneitem;
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("location",locotxt);
                editor.apply();
                /* Toast.makeText(index.this, locotxt, Toast.LENGTH_SHORT).show();*/
            }
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "please choose a location", Toast.LENGTH_SHORT).show();
            }
        });

        selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locotxt=locations.getSelectedItem().toString();
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("location",locotxt);
                editor.apply();
                Toast.makeText(getContext(), locotxt+" selected", Toast.LENGTH_SHORT).show();
                ;

            }
        });

    }

}
