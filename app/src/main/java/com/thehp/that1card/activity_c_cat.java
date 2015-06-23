package com.thehp.that1card;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardViewNative;


public class activity_c_cat extends ActionBarActivity {
    List<Categories> persons;
    List<Categories> persons2;
    public ProgressDialog prgDialog;
    int dr[]={R.drawable.wc,R.drawable.gc,R.drawable.bc};
    int dr2[]={R.drawable.cwed1,R.drawable.cwed11,R.drawable.cwed13,R.drawable.cwed14,R.drawable.cwed18,R.drawable.cwed22};
    RecyclerView rv;
    RV_cat_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_cat);
        KeyUtils.initvectors();

        initializeData();

        rv = (RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        adapter = new RV_cat_adapter(persons,getApplicationContext());
        rv.setAdapter(adapter);


        if(KeyUtils.s_id.equals("null")) {
            //prgDialog = new ProgressDialog(this);
            //prgDialog.setMessage("Creating your session...");
            //prgDialog.setCancelable(false);
           new SessionAccessTask().execute();
        }


// This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    }
    class SessionAccessTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new CategoryAccessTask().execute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getSessionid();
            return null;
        }
    }









    class CategoryAccessTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
//pBar.setVisibility(View.VISIBLE);
            Log.e("onPreExecute",
                    "----------------onPreExecute-----------------");
            //prgDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            readingCategories();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            Log.e("onPostExecute",
                    "----------------onPostExecute-----------------");
            //prgDialog.hide();
            //prgDialog.dismiss();
            initializeData();
            adapter = new RV_cat_adapter(KeyUtils.cat,getApplicationContext());
            rv.setAdapter(adapter);
            for(int k=0;k<KeyUtils.cat.size();k++)
            {
                Log.e("key", KeyUtils.cat.get(k).name);
                Log.e("child",String.valueOf(KeyUtils.cat.get(k).getChildrenCount()));
                for(int m=0;m<KeyUtils.cat.get(k).getChildrenCount();m++)
                {
                    Log.e("keyccc",KeyUtils.cat.get(k).children.get(m).name);
                }
            }



            for(int k=0;k<KeyUtils.pro.size();k++)
            {
                Log.e("keyp", KeyUtils.pro.get(k).name);

                for(int m=0;m<KeyUtils.pro.get(k).cid.size();m++)
                {
                    Log.e("keypppp",KeyUtils.pro.get(k).cid.get(m).toString());
                }
            }

            Log.e("key",KeyUtils.s_id);
        }

    }
    public void getSessionid()
    {
        try{

            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            env.dotNet = false;
            env.xsd = SoapSerializationEnvelope.XSD;
            env.enc = SoapSerializationEnvelope.ENC;

            SoapObject request = new SoapObject(KeyUtils.NAMESPACE, "login");

            request.addProperty("username", "appdev");
            request.addProperty("apiKey", "23011996thehp");

            env.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(KeyUtils.URL);
            androidHttpTransport.call("", env);
            Object result = env.getResponse();
            Log.d("sessionId", result.toString());
            String sessionId = result.toString();
            KeyUtils.s_id=sessionId;

            SoapObject request2 = new SoapObject(KeyUtils.NAMESPACE, "shoppingCartCreate");

            request2.addProperty("sessionId", KeyUtils.s_id);
            request2.addProperty("is_active",1);



            env.setOutputSoapObject(request2);

            androidHttpTransport.call("", env);
            SoapObject obj3 = (SoapObject) env.bodyIn;
            KeyUtils.CartId=  (Integer)obj3.getProperty(0);

            Log.d("cartId",String.valueOf(KeyUtils.CartId));



        }
        catch (Exception r){
            r.printStackTrace();
        };
    }

    public void readingCategories() {
        try {
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            env.dotNet = false;
            env.xsd = SoapSerializationEnvelope.XSD;
            env.enc = SoapSerializationEnvelope.ENC;

            /*SoapObject request = new SoapObject(KeyUtils.NAMESPACE, "login");

            request.addProperty("username", "appdev");
            request.addProperty("apiKey", "23011996thehp");

            env.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(KeyUtils.URL);
            androidHttpTransport.call("", env);
            Object result = env.getResponse();
            Log.d("sessionId", result.toString());
            String sessionId = result.toString();
            KeyUtils.s_id=sessionId;

            */

            SoapObject requestCart = new SoapObject(KeyUtils.NAMESPACE, "catalogCategoryTree");
            requestCart.addProperty("sessionId", KeyUtils.s_id);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(KeyUtils.URL);
            env.setOutputSoapObject(requestCart);
            androidHttpTransport.call("", env);


            SoapObject obj3 = (SoapObject) env.bodyIn;
            obj3=  (SoapObject)obj3.getProperty(0);
            Log.d("dat",String.valueOf(obj3));
            obj3=  (SoapObject)obj3.getProperty("children");
            obj3=  (SoapObject)obj3.getProperty(0);
            obj3=  (SoapObject)obj3.getProperty("children");

            SoapObject obj4,obj5;
            Log.d("dat",String.valueOf(obj3));

            KeyUtils.cat.add(new Categories("Dummy", "23 years old", R.drawable.wc));
            for (int i = 0; i < obj3.getPropertyCount(); i++) {


                obj4=  (SoapObject)obj3.getProperty(i);

                //KeyUtils.c_name.add(i, obj4.getProperty("name").toString());
                //KeyUtils.c_id.add(i, obj4.getProperty("category_id").toString());
                Categories c=new Categories(obj4.getProperty("name").toString(),obj4.getProperty("category_id").toString(), dr[i%3]);
                Log.d("name",obj4.getProperty("name").toString());
                obj4=(SoapObject)obj4.getProperty("children");


                Log.e("children",obj4.toString());
                persons2=new ArrayList<Categories>();
                persons2.add(new Categories("Dummy", "23 years old", R.drawable.wc));
                for(int j=0;j<obj4.getPropertyCount();j++)
                {
                    obj5=  (SoapObject)obj4.getProperty(j);
                   // KeyUtils.child_c_name.add(j, obj5.getProperty("name").toString());
                    //KeyUtils.child_c_id.add(j, obj5.getProperty("category_id").toString());
                    Log.d("name",obj5.getProperty("name").toString());
                    Categories c2=new Categories(obj5.getProperty("name").toString(),obj5.getProperty("category_id").toString(), dr[j%3]);
                    persons2.add(c2);


                }

                c.SetChildren(persons2);
                KeyUtils.cat.add(c);
                //persons2.removeAll(persons2);
                //persons2=new ArrayList<Categories>();



                //Log.d("level",level);








            }

            SoapObject obj7;

            SoapObject requestCart2 = new SoapObject(KeyUtils.NAMESPACE, "catalogProductList");
            requestCart2.addProperty("sessionId", KeyUtils.s_id);

            env.setOutputSoapObject(requestCart2);
            androidHttpTransport.call("", env);
            SoapObject obj6 = (SoapObject) env.bodyIn;
            obj6=  (SoapObject)obj6.getProperty(0);

            Log.d("prooolist",String.valueOf(obj6));

            for(int p=0;p<obj6.getPropertyCount();p++)
            {
                SoapObject obj8;
                List<Integer>cid=new ArrayList<Integer>();


                obj7=(SoapObject)obj6.getProperty(p);
                obj8=(SoapObject)obj7.getProperty("category_ids");
                for(int l=0;l<obj8.getPropertyCount();l++)
                {
                    Log.e("ppp",obj8.getProperty(l).toString());
                    cid.add(Integer.parseInt(obj8.getProperty(l).toString()));

                }
                KeyUtils.pro.add(new Product(obj7.getProperty("name").toString(),obj7.getProperty("product_id").toString(),dr2[p%6],cid));

            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void initializeData(){
       persons = new ArrayList<>();
        persons2 = new ArrayList<>();
/*

        persons2.add(new Categories("Dummy", "23 years old", R.drawable.wc));
        for(int j=0;j<KeyUtils.child_c_name.size();j++)
        {
            Log.e("cname",KeyUtils.c_name.get(j));
            Categories c=new Categories(KeyUtils.child_c_name.get(j),KeyUtils.child_c_id.get(j), dr[j%3]);
            persons2.add(c);
        }

        persons.add(new Categories("Dummy", "23 years old", R.drawable.wc));
        for(int j=0;j<KeyUtils.c_name.size();j++)
        {
            Log.e("cname",KeyUtils.c_name.get(j));
            Categories c=new Categories(KeyUtils.c_name.get(j),KeyUtils.c_id.get(j), dr[j%3]);

            c.SetChildren(persons2);
            persons.add(c);
        }*/

        /*persons.add(new Categories("SaticData Below", "23 years old", R.drawable.wc));
        persons.add(new Categories("Birthday Cards", "25 years old", R.drawable.bc));
        persons.add(new Categories("Greeting Cards", "35 years old", R.drawable.gc));
        persons.add(new Categories("Invitation Cards", "23 years old", R.drawable.wc));
        persons.add(new Categories("Lavery Maiss", "25 years old", R.drawable.gc));
        persons.add(new Categories("Lillie Watts", "35 years old", R.drawable.bc));
        persons.add(new Categories("Emma Wilson", "23 years old", R.drawable.gc));
        persons.add(new Categories("Lavery Maiss", "25 years old", R.drawable.wc));
        persons.add(new Categories("Lillie Watts", "35 years old", R.drawable.bc));
        */
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_c_cat, menu);
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
        if (id == R.id.action_profile) {
            if(KeyUtils.logged==false) {
                Intent cat = new Intent(getApplicationContext(), activity_c_user.class);
                startActivity(cat);
            }
            else
            {
                Intent cat = new Intent(getApplicationContext(), activity_c_user2.class);
                startActivity(cat);
            }
            return true;
        }
        if (id == R.id.action_wishlist) {
            Intent cat=new Intent(getApplicationContext(),activity_c_wishlist.class);
            startActivity(cat);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
