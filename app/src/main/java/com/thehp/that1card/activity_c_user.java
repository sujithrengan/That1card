package com.thehp.that1card;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class activity_c_user extends ActionBarActivity {

    public EditText un=null,pw = null;
    public int stat=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_user);

        un=(EditText)findViewById(R.id.txt_username);
        pw=(EditText)findViewById(R.id.txt_password);


        LinearLayout lyt_skip= (LinearLayout) findViewById(R.id.lyt_skip);
        lyt_skip.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent cat=new Intent(getApplicationContext(),acticity_c_register.class);

                        startActivity(cat);
                        //activity_c_user.this.finish();

                    }
                }
        );



        Button btn_login=(Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KeyUtils.user.setloginparam(un.getText().toString(),pw.getText().toString());

                if(un.getText().equals("")||pw.getText().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Empty Credentials",Toast.LENGTH_SHORT).show();
                }

               /*

                Register

                else
                {
                    new UserRegisterTask().execute();

                }*/


                /*

                Login*/
                else {
                    checkloginparam(KeyUtils.user);
                }



            }
        });
    }


    int checkloginparam(User user)
    {

        AsyncHttpClient client = new AsyncHttpClient();
        // Http Request Params Object
        RequestParams param = new RequestParams();
        param.add("username",user.Email);
        param.add("password",user.Pass);
               /* List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("username", "testemailid@ymail.com"));
                nameValuePairs.add(new BasicNameValuePair("password", "23011996"));
*/
        client.post("http://52.74.234.78/appaccess.php", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {



                Log.e("hooooooo", response);
                try {
                    // Extract JSON array from the response
                    JSONObject obj = new JSONObject(response);

                    //System.out.println(arr.length());
                    // If no of array elements is not zero

                        // Loop through each array element, get JSON object which has userid and username

                            // Get JSON object

                            System.out.println(obj.get("status"));
                            if(obj.get("status").toString().equals("1"))
                            {
                                stat=1;
                                KeyUtils.user.uid=obj.get("userID").toString();
                                KeyUtils.logged=true;
                                new UserAccessTask().execute();

                            }
                            else
                            {
                                stat=0;
                                un.setText("");
                                pw.setText("");
                                Toast.makeText(getApplicationContext(),"Wrong Credentials",Toast.LENGTH_SHORT).show();

                            }
                           // System.out.println(obj.get("userID"));


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }




            }
            // When error occured
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                // TODO Auto-generated method stub
                // Hide ProgressBar
                //prgDialog.hide();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return stat;
    }


    class UserRegisterTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),"Registered,",Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            RegUser();
            return null;
        }
    }

    class UserAccessTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i = new Intent(getApplicationContext(), activity_c_user2.class);
            startActivity(i);
            activity_c_user.this.finish();


        }

        @Override
        protected Void doInBackground(Void... voids) {
            getUserinfo();
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

            cust.addProperty("email",un.getText().toString());
            cust.addProperty("firstname","Test");
            cust.addProperty("lastname","Name");

            cust.addProperty("password",pw.getText().toString());
            cust.addProperty("website_id",1);
            cust.addProperty("store_id",1);
            cust.addProperty("group_id",1);

            request.addProperty("sessionId",KeyUtils.s_id);
            request.addProperty("customerData",cust);

            env.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(KeyUtils.URL);
            androidHttpTransport.call("", env);
            SoapObject obj3 = (SoapObject) env.bodyIn;
            //obj3=  (SoapObject)obj3.getProperty(0);
            Log.d("dat",String.valueOf(obj3));
            //KeyUtils.user.FirstName=obj3.getProperty("firstname").toString();
            //KeyUtils.user.LastName=obj3.getProperty("lastname").toString();
        }
        catch (Exception r){
            r.printStackTrace();
        };
    }

    void getUserinfo()
    {
        try{

            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            env.dotNet = false;
            env.xsd = SoapSerializationEnvelope.XSD;
            env.enc = SoapSerializationEnvelope.ENC;

            SoapObject request = new SoapObject(KeyUtils.NAMESPACE, "customerCustomerInfo");

            request.addProperty("sessionId",KeyUtils.s_id);
            request.addProperty("customerId",Integer.parseInt(KeyUtils.user.uid));

            env.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(KeyUtils.URL);
            androidHttpTransport.call("", env);
            SoapObject obj3 = (SoapObject) env.bodyIn;
            obj3=  (SoapObject)obj3.getProperty(0);
            Log.d("dat",String.valueOf(obj3));
            KeyUtils.user.FirstName=obj3.getProperty("firstname").toString();
            KeyUtils.user.LastName=obj3.getProperty("lastname").toString();
        }
        catch (Exception r){
            r.printStackTrace();
        };

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_c_user, menu);
        return true;
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
