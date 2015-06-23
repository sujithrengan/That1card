package com.thehp.that1card;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends ActionBarActivity {

    public String NAMESPACE="urn:Magento";
    public String URL="http://192.168.42.49/magento/api/v2_soap/";
    public String s_id;
    public ProgressDialog prgDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_login=(Button)findViewById(R.id.btn_login);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Creating your session...");
        prgDialog.setCancelable(false);
        new SessionAccessTask().execute();
        LinearLayout lyt_skip= (LinearLayout) findViewById(R.id.lyt_skip);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Login();
            }
        });
        lyt_skip.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent cat=new Intent(getApplicationContext(),Plain_Cat.class);
                        cat.putExtra("s_id",s_id);
                        startActivity(cat);
                        MainActivity.this.finish();

                    }
                }
        );
    }

    class SessionAccessTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
//pBar.setVisibility(View.VISIBLE);
            Log.e("onPreExecute",
                    "----------------onPreExecute-----------------");
            prgDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            readingData();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            Log.e("onPostExecute",
                    "----------------onPostExecute-----------------");
            prgDialog.hide();
            prgDialog.dismiss();



        }

    }



    public void readingData() {
        try {
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            env.dotNet = false;
            env.xsd = SoapSerializationEnvelope.XSD;
            env.enc = SoapSerializationEnvelope.ENC;

            SoapObject request = new SoapObject(NAMESPACE, "login");

            request.addProperty("username", "appdev");
            request.addProperty("apiKey", "23011996");

            env.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call("", env);
            Object result = env.getResponse();
            Log.d("sessionId", result.toString());
            String sessionId = result.toString();
            s_id=sessionId;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
