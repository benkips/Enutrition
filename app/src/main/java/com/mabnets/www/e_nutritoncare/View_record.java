package com.mabnets.www.e_nutritoncare;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class View_record extends Fragment {
    private WebView wv;
    private ProgressBar PD;
    final String myerrorpage = "file:///android_asset/myfiles/errorpage.html";
    private SharedPreferences preferences;
    private String specialist_no;
    private String specialistlocation;
    public View_record() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view_record=inflater.inflate(R.layout.fragment_view_record, container, false);

        preferences=getActivity().getSharedPreferences("logininfo.conf",MODE_PRIVATE);
        specialist_no=preferences.getString("phone","");
        specialistlocation=preferences.getString("location","");

        wv= view_record.findViewById(R.id.wvone);
        PD= view_record.findViewById(R.id.progress);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        wv.clearHistory();
        wv.clearCache(true);
        wv.requestFocus(View.FOCUS_DOWN);
        wv.setFocusable(true);
        wv.setFocusableInTouchMode(true);
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setDatabaseEnabled(true);
        wv.getSettings().setAppCacheEnabled(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv.getSettings().setAppCacheEnabled(true);
        wv.loadUrl("http://sadiq.mabnets.com/myrecord.php?uid="+specialist_no+"&loc="+specialistlocation);

        WebSettings webSettings = wv.getSettings(); webSettings.setJavaScriptEnabled(true);


        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                PD.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                PD.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                wv.loadUrl(myerrorpage);
                AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                alert.setMessage("make sure you are connected to the internet to view records");
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
        });
      return view_record;
    }

}
