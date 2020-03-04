package com.mabnets.www.e_nutritoncare;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class home_menu extends Fragment {
    private ListView index_menulv;
    private SharedPreferences preferences;
    private String phones;
    private ProgressDialog progressDialog;
    private Mycommand mycommand;
    public home_menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View home_menu= inflater.inflate(R.layout.fragment_home_menu, container, false);
        index_menulv=(ListView)home_menu.findViewById(R.id.lvindex);
        preferences=getActivity().getSharedPreferences("logininfo.conf",MODE_PRIVATE);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("loading..");
        mycommand=new Mycommand(getContext());
        call_no();
        String [] titlee=getResources().getStringArray(R.array.indexmenuone);
        String [] Description=getResources().getStringArray(R.array.indexmenutwo);
        SimpleAdapter adapter=new SimpleAdapter(getContext(),titlee,Description);
        index_menulv.setAdapter(adapter);

        index_menulv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Fragment selectedfragment=null;*/
                Fragment selectedfragment=null;
                switch(position){
                    case 0:{
                        selectedfragment=new Bio_data();
                        break;
                    }
                    case  1:{
                        selectedfragment=new View_record();
                        break;
                    }
                    case 2:{
                        selectedfragment=new Locate_specialist();
                        break;
                    }
                    case 3:{
                        selectedfragment=new progressive_record();
                        break;
                    }
                }
                getFragmentManager().beginTransaction().replace(R.id.framelayout_index,selectedfragment).addToBackStack(null).commit();
            }
        });

       return home_menu;
    }
    public class SimpleAdapter extends BaseAdapter {
        private Context mcontext;
        private LayoutInflater layoutInflater;
        private TextView title,description;
        private String [] titleArray;
        private String [] Description;
        private ImageView imv;

        public SimpleAdapter(Context context,String [] title ,String [] description){
            mcontext=context;
            titleArray=title;
            Description=description;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return titleArray.length;

        }

        @Override
        public Object getItem(int i) {
            return titleArray[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view==null){
                view=layoutInflater.inflate(R.layout.index_menu,null);

            }
            title=(TextView)view.findViewById(R.id.tvtitleindex_menu);
            description=(TextView)view.findViewById(R.id.tvdescindex_menu);
            imv=(ImageView)view.findViewById(R.id.ivindex_menu);
            title.setText(titleArray[i]);
            description.setText(Description[i]);
            if(titleArray[i].equalsIgnoreCase("Create new patient record")){
                imv.setImageResource(R.drawable.add_record);
            }else if(titleArray[i].equalsIgnoreCase("View my record")){
                imv.setImageResource(R.drawable.vrecords);
            }else if(titleArray[i].equalsIgnoreCase("Locate other specialist")){
                imv.setImageResource(R.drawable.map);
            }else{
                imv.setImageResource(R.drawable.update_record);
            }
            return view;

        }
    }
    private  void call_no() {
        String url="http://sadiq.mabnets.com/Admin.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                ArrayList<call> admindetails = new JsonConverter<call>().toArrayList(response,call.class);
                ArrayList<String> titlee=new ArrayList<>();
                for (call value :  admindetails) {
                    titlee.add(value.phone);
                }
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("admin_phone","0"+titlee.get(0));
                editor.apply();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof TimeoutError) {
                    progressDialog.dismiss();
                 /*   Toast.makeText(getContext(), "error time out ", Toast.LENGTH_SHORT).show();*/
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
}
