package com.mabnets.www.e_nutritoncare;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kosalgeek.android.caching.FileCacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class progressive_record extends Fragment {
    private EditText idno;
    private EditText weight;
    private EditText  height;
    private EditText maucc;
    private Button btn;
    private ProgressDialog progressDialog;
    private Mycommand mycommand;
    private FileCacher<String> offline_cacher;
    final String Tag=this.getClass().getName();
    public progressive_record() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View progressive=inflater.inflate(R.layout.fragment_progressive_record, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    idno=(EditText)progressive.findViewById(R.id.patient_idnoprog);
    weight=(EditText)progressive.findViewById(R.id.patient_weightprog);
    height=(EditText)progressive.findViewById(R.id.patient_heightprog);
    maucc=(EditText)progressive.findViewById(R.id.patient_muacprog);
    btn=(Button)progressive.findViewById(R.id.save_btnprog);

        offline_cacher=new FileCacher<>(getContext(),"offlineprogressive.txt");

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("saving...");
        mycommand=new Mycommand(getContext());

    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String idnoo=idno.getText().toString().trim();
            final String weightt=weight.getText().toString().trim();
            final String heightt=height.getText().toString().trim();
            final String mauccc=maucc.getText().toString().trim();


            if(idnoo.isEmpty()){
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .repeat(2)
                        .playOn(idno);
                idno.setError("invalid idno");
                idno.requestFocus();
                return;
            } else  if(weightt.isEmpty()){
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .repeat(2)
                        .playOn(weight);
                weight.setError("invalid weight");
                weight.requestFocus();
                return;
            } else  if(heightt.isEmpty()){
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .repeat(2)
                        .playOn(height);
                height.setError("invalid height");
                height.requestFocus();
                return;
            }else if(mauccc.isEmpty()){
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .repeat(2)
                        .playOn(maucc);
                maucc.setError("invalid muac");
                maucc.requestFocus();
                return;
            }else{
                String url = "http://sadiq.mabnets.com/progressive.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if(response.equals("Progressive record saved successfull")){
                            AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                            alert.setMessage("Progressive record saved successfull");
                            alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Fragment fragment=new home_menu();
                                    FragmentManager manager=getFragmentManager();
                                    manager.beginTransaction().replace(R.id.framelayout_index,fragment).addToBackStack(null).commit();

                                }

                            });
                            alert.show();
                        }else{
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        }




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        if (error instanceof TimeoutError) {
                            Toast.makeText(getContext(), "error time out ", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            ArrayList title = new ArrayList<String>();

                            title.add(idnoo);
                            title.add(weightt);
                            title.add(heightt);
                            title.add(mauccc);


                            /* Log.d(Tag, String.valueOf(title)+":");*/
                            try {
                                offline_cacher.appendOrWriteCache(String.valueOf(title));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(offline_cacher.hasCache()){
                                //read cache
//                                    try {
//                                        String t=offline_cacher.readCache();
//                                        Log.d(Tag, t);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
                                try {
                                    List<String> list =offline_cacher.getAllCaches();
                                    for(String text : list){
                                        //do something with text

                                    }
                                    Log.d(Tag, String.valueOf(list));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                                alert.setMessage("Data was successfull saved offline ");
                                alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Fragment fragment=new home_menu();

                                        FragmentManager manager=getFragmentManager();
                                        manager.beginTransaction().replace(R.id.framelayout_index,fragment).addToBackStack(null).commit();
                                    }

                                });
                                alert.show();
                            }
                            } else if (error instanceof NoConnectionError) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "error no connection", Toast.LENGTH_SHORT).show();
                            ArrayList title = new ArrayList<String>();

                            title.add(idnoo);
                            title.add(weightt);
                            title.add(heightt);
                            title.add(mauccc);


                            /* Log.d(Tag, String.valueOf(title)+":");*/
                            try {
                                offline_cacher.appendOrWriteCache(String.valueOf(title));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(offline_cacher.hasCache()){
                                //read cache
//                                    try {
//                                        String t=offline_cacher.readCache();
//                                        Log.d(Tag, t);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
                                try {
                                    List<String> list =offline_cacher.getAllCaches();
                                    for(String text : list){
                                        //do something with text

                                    }
                                    Log.d(Tag, String.valueOf(list));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                                alert.setMessage("Data was successfull saved offline ");
                                alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Fragment fragment=new home_menu();

                                        FragmentManager manager=getFragmentManager();
                                        manager.beginTransaction().replace(R.id.framelayout_index,fragment).addToBackStack(null).commit();
                                    }

                                });
                                alert.show();
                            }
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
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("pidno",idnoo);
                        params.put("pweight", weightt);
                        params.put("pheight", heightt);
                        params.put("pmuac", mauccc);
                        return params;
                    }
                };
                mycommand.add(stringRequest);
                progressDialog.show();
                mycommand.execute();
                mycommand.remove(stringRequest);
            }
        }
    });



     return  progressive;
    }

}
