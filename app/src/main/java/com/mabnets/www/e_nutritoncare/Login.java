package com.mabnets.www.e_nutritoncare;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class Login extends AppCompatActivity {
/*    DEFINING The views we had seeen eairly like the edittext check box...etc
  * we declare them  private because  we only need them to be accessed here only  */
    private EditText etphone;
    private EditText etpass;
    private CheckBox cb;
    private Button btnlogin;
    private Handler handler;
    private Mycommand mycommand;
    private ProgressDialog progressDialog;
    private Boolean checked;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handler=new Handler();
        mycommand=new Mycommand(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("processing");

//        bind them to the id we saw on the  login_Activity design sheet

        etphone=(EditText)findViewById(R.id.etphoneL);
        etpass=(EditText)findViewById(R.id.etpasswordL);
        cb=(CheckBox)findViewById(R.id.cbL);
        btnlogin=(Button)findViewById(R.id.btnL);


        checked=cb.isChecked();

        preferences=getSharedPreferences("logininfo.conf",MODE_PRIVATE);
        String phones=preferences.getString("phone","");
        String pass=preferences.getString("pass","");

        if(!phones.equals("") && !pass.equals("")){
            /*startActivity here*/
             startActivity(new Intent(Login.this,index.class));
                Login.this.finish();
        }




        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone=etphone.getText().toString().trim();
                String pass=etpass.getText().toString().trim();
                validate(phone,pass);

            }
        });
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                checked=b;

            }
        });

    }
    private void validate(final String phoneno, final String password){
       Thread validate=new Thread(){
           @Override
           public void run() {
               handler.post(new Runnable() {
                   @Override
                   public void run() {

                       if(phoneno.isEmpty()){
                           YoYo.with(Techniques.Shake)
                                   .duration(700)
                                   .repeat(2)
                                   .playOn(etphone);
                           etphone.setError("invalid number");
                           etphone.requestFocus();
                           return;
                       }else if(password.isEmpty()){
                           YoYo.with(Techniques.Shake)
                                   .duration(700)
                                   .repeat(2)
                                   .playOn(etpass);
                           etpass.setError("inavalid password");
                           etpass.requestFocus();
                           return;
                       }else{
                           if(!isphone(etphone.getText().toString()) || (phoneno.length()!=10 || !phoneno.startsWith("07"))) {
                               YoYo.with(Techniques.Shake)
                                       .duration(700)
                                       .repeat(2)
                                       .playOn(etphone);
                               etphone.setError("invalid number");
                               etphone.requestFocus();
                               return;
                           }else if(password.length()!=8){
                               etpass.setError("password should be 8 characters");
                               etpass.requestFocus();
                               return;
                           }else{
                               String url="http://sadiq.mabnets.com/loginuser.php";
                               StringRequest rqst=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                   @Override
                                   public void onResponse(String response) {
                                       progressDialog.dismiss();
                                       Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                                       if(response.equals("success")){
                                           if(checked){
                                               preferences=getSharedPreferences("logininfo.conf",MODE_PRIVATE);
                                               SharedPreferences.Editor editor=preferences.edit();
                                               editor.putString("phone",phoneno);
                                               editor.putString("pass",password);
                                               editor.apply();
                                           }
                                         /*  put an Activity here*/
                                           startActivity(new Intent(Login.this,index.class));
                                           Login.this.finish();

                                       }else{
                                           AlertDialog.Builder alert=new AlertDialog.Builder(Login.this);
                                           alert.setMessage("Wrong details please try again");
                                           alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialogInterface, int i) {
                                                   Toast.makeText(Login.this, "try again", Toast.LENGTH_SHORT).show();
                                               }

                                           });
                                           alert.show();
                                       }

                                   }
                               }, new Response.ErrorListener() {
                                   @Override
                                   public void onErrorResponse(VolleyError error) {
                                       if (error instanceof TimeoutError) {
                                           progressDialog.dismiss();
                                           Toast.makeText(Login.this, "error time out ", Toast.LENGTH_SHORT).show();
                                       } else if (error instanceof NoConnectionError) {
                                           progressDialog.dismiss();
                                           Toast.makeText(Login.this, "error no connection", Toast.LENGTH_SHORT).show();
                                       } else if (error instanceof NetworkError) {
                                           progressDialog.dismiss();
                                           Toast.makeText(Login.this, "error network error", Toast.LENGTH_SHORT).show();
                                       } else if (error instanceof AuthFailureError) {
                                           progressDialog.dismiss();
                                           Toast.makeText(Login.this, "errorin Authentication", Toast.LENGTH_SHORT).show();
                                       } else if (error instanceof ParseError) {
                                           progressDialog.dismiss();
                                           Toast.makeText(Login.this, "error while parsing", Toast.LENGTH_SHORT).show();
                                       } else if (error instanceof ServerError) {
                                           progressDialog.dismiss();
                                           Toast.makeText(Login.this, "error  in server", Toast.LENGTH_SHORT).show();
                                       } else if (error instanceof ClientError) {
                                           progressDialog.dismiss();
                                           Toast.makeText(Login.this, "error with Client", Toast.LENGTH_SHORT).show();
                                       } else {
                                           progressDialog.dismiss();
                                           Toast.makeText(Login.this, "error while loading", Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               }){
                                   @Override
                                   protected Map<String, String> getParams() throws AuthFailureError {
                                       HashMap<String,String> params=new HashMap<>();
                                       params.put("phone",phoneno);
                                       params.put("pass",password);
                                       return params;
                                   }
                               };
                               mycommand.add(rqst);
                               progressDialog.show();
                               mycommand.execute();
                               mycommand.remove(rqst);

                           }
                       }
                   }
               });
           }
       };
       validate.start();
    }
    public static boolean isphone(CharSequence target){
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }
    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this,"right-to-left");
    }
}
