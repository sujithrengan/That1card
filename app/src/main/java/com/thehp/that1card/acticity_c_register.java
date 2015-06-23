package com.thehp.that1card;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class acticity_c_register extends ActionBarActivity {

    private static String APP_ID = "308180782571605"; // Replace your App ID here
    SoapFault reg=null;
    // Instance of Facebook Class

LoginButton loginButton;
    CallbackManager callbackManager;
    Profile profile;
    ProfileTracker mProfileTracker;
    AccessToken accesstk;


    public EditText fn=null,ln = null,em=null,p=null,cp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.c_register);
        em=(EditText)findViewById(R.id.txt_username);
        fn=(EditText)findViewById(R.id.txt_firstname);
        ln=(EditText)findViewById(R.id.txt_lastname);
        em=(EditText)findViewById(R.id.txt_emailid);
        p=(EditText)findViewById(R.id.txt_password);
        cp=(EditText)findViewById(R.id.txt_cpassword);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("email");


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.e("Access Token", loginResult.getAccessToken().getToken().toString());
                Log.e("Access Token", loginResult.getAccessToken().getUserId().toString());
                Log.e("Access Token", loginResult.getAccessToken().getUserId().toString());

                accesstk = loginResult.getAccessToken();
                mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                        Log.v("facebook - profile", profile2.getFirstName());


                        mProfileTracker.stopTracking();
                    }
                };
                mProfileTracker.startTracking();


                fbparse();

            }

            @Override
            public void onCancel() {

                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });







        Button btn_login=(Button)findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //KeyUtils.user.setloginparam(un.getText().toString(),pw.getText().toString());

                final String email = em.getText().toString();
                final String pass = p.getText().toString();
                if(fn.getText().toString().trim().equals(""))
                {
                    fn.setError("Inavlid Firstname");
                    //Toast.makeText(getApplicationContext(),"Empty Credentials",Toast.LENGTH_SHORT).show();
                }
                else if(ln.getText().toString().trim().equals(""))
                {
                    fn.setError("Inavlid Lastname");
                }



                else if (!isValidEmail(email)) {
                    em.setError("Invalid Email");
                }


                else if (!isValidPassword(pass)) {
                    p.setError("Invalid Password");
                }
                else if(!(p.getText().toString().equals(cp.getText().toString())))
                {
                    cp.setError("Password mismatch");
                    cp.setText("");
                }



                else
                {

                    new UserRegisterTask().execute();

                }


                /*

                Login
                else {
                    checkloginparam(KeyUtils.user);
                }
                */



            }
        });

    }


    public void fbparse()
    {
        GraphRequest request = GraphRequest.newMeRequest(
                accesstk,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code

                        Log.e("graphresponse",response.toString());

                        try {
                            em.setText(object.get("email").toString());
                            fn.setText(object.get("first_name").toString());
                            ln.setText(object.get("last_name").toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loginButton.setVisibility(View.INVISIBLE);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }



    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }


    class UserRegisterTask extends AsyncTask<Void, Void, Void>
    {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(reg==null)
            Toast.makeText(getApplicationContext(), "Registered,", Toast.LENGTH_SHORT).show();
            else{
                Toast.makeText(getApplicationContext(), "Email Already Registered", Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        protected Void doInBackground(Void... voids) {
            RegUser();
            return null;
        }
    }


    void RegUser()
    {
        try{

            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            env.dotNet = false;
            env.xsd = SoapSerializationEnvelope.XSD;
            env.enc = SoapSerializationEnvelope.ENC;

            SoapObject request = new SoapObject(KeyUtils.NAMESPACE, "customerCustomerCreate");


            SoapObject cust = new SoapObject(KeyUtils.NAMESPACE, "customerCustomerEntityToCreate");

            cust.addProperty("email", em.getText().toString());
            cust.addProperty("firstname", fn.getText().toString());
            cust.addProperty("lastname", ln.getText().toString());

            cust.addProperty("password",p.getText().toString());
            cust.addProperty("website_id",1);
            cust.addProperty("store_id",1);
            cust.addProperty("group_id",1);

            request.addProperty("sessionId",KeyUtils.s_id);
            request.addProperty("customerData",cust);
            reg=null;
            env.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(KeyUtils.URL);
            androidHttpTransport.call("", env);
            reg= (SoapFault) env.bodyIn;
            if(reg==null) {
                SoapObject obj3 = (SoapObject) env.bodyIn;

                //obj3=  (SoapObject)obj3.getProperty(0);
                Log.d("dat", String.valueOf(obj3));
                //KeyUtils.user.FirstName=obj3.getProperty("firstname").toString();
                //KeyUtils.user.LastName=obj3.getProperty("lastname").toString();
            }
        }
        catch (Exception r){
            r.printStackTrace();
        };
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acticity_register, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        LoginManager.getInstance().logOut();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_wishlist) {
            Intent cat=new Intent(getApplicationContext(),activity_c_wishlist.class);
            startActivity(cat);
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
