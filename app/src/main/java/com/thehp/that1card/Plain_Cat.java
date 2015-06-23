package com.thehp.that1card;

import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;


public class Plain_Cat extends ActionBarActivity {


    ListView list;
    Vector<String> c_id;
    Vector<String> c_name;
    CategoryListAdapter adapter;
    public  Plain_Cat CustomListView = null;
    public  ArrayList<Listcategories> CustomListViewValuesArr = new ArrayList<Listcategories>();
    String id="id",name="name",level="level";
    public String s_id;
    public String NAMESPACE="urn:Magento";
    public String URL="http://192.168.42.49/magento/api/v2_soap/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        c_id=new Vector<String>();
        c_name=new Vector<String>();
        new AccessTask().execute();
        Intent intent = getIntent();
        s_id = intent.getExtras().getString("s_id");
        setContentView(R.layout.activity_plain__cat);


        CustomListView = this;

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/

    }
    public void setListData()
    {

        for (int i = 0; i < c_id.size(); i++) {

            final Listcategories sched = new Listcategories();

            /******* Firstly take data in model object ******/
            sched.setName(c_name.get(i));
            sched.setImage("ic_launcher");
            sched.setid(c_id.get(i));

            /******** Take Model Object in ArrayList **********/
            CustomListViewValuesArr.add( sched );
        }

    }

    public void onItemClick(int mPosition)
    {


        Intent cat=new Intent(getApplicationContext(),Plain_Pro.class);
        cat.putExtra("s_id",s_id);
        cat.putExtra("c_id",c_id.get(mPosition));
        startActivity(cat);

    }


    class AccessTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
//pBar.setVisibility(View.VISIBLE);
            Log.e("onPreExecute",
                    "----------------onPreExecute-----------------");
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

            setListData();

            Resources res =getResources();
            list= ( ListView )findViewById( R.id.listView );  // List defined in XML ( See Below )

            /**************** Create Custom Adapter *********/
            adapter=new CategoryListAdapter( CustomListView, CustomListViewValuesArr,res );

            list.setAdapter( adapter );

        }

    }
    public void readingData() {
        try {
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            env.dotNet = false;
            env.xsd = SoapSerializationEnvelope.XSD;
            env.enc = SoapSerializationEnvelope.ENC;

            /*SoapObject request = new SoapObject(NAMESPACE, "login");

            request.addProperty("username", "appdev");
            request.addProperty("apiKey", "23011996");

            env.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call("", env);
            Object result = env.getResponse();
            Log.d("sessionId", result.toString());
            String sessionId = result.toString();

            */
            String sessionId = s_id;

            SoapObject requestCart = new SoapObject(NAMESPACE, "catalogCategoryTree");
            requestCart.addProperty("sessionId", sessionId);
            //requestCart.addProperty("categoryId", 5);
            env.setOutputSoapObject(requestCart);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call("", env);
            //result = env.getResponse();
            //env.getResponse();

            SoapObject obj3 = (SoapObject) env.bodyIn;
            obj3=  (SoapObject)obj3.getProperty(0);
            Log.d("dat",String.valueOf(obj3));
            obj3=  (SoapObject)obj3.getProperty("children");
            obj3=  (SoapObject)obj3.getProperty(0);
            obj3=  (SoapObject)obj3.getProperty("children");
            //obj3=  (SoapObject)obj3.getProperty("5");
            //SoapObject obj2 = (SoapObject) obj1.getProperty(0);
            //SoapObject obj3=  (SoapObject)obj2.getProperty(5);
            //obj3=(SoapObject)obj3.getProperty(0);
            SoapObject obj4;
            Log.d("dat",String.valueOf(obj3));

            for (int i = 0; i < obj3.getPropertyCount(); i++) {


                obj4=  (SoapObject)obj3.getProperty(i);
                //obj4=  (SoapObject)obj4.getProperty("name");
                //id +=", "+obj3.getProperty(0).toString();
              // name += ", "+obj4.getProperty("name").toString();
               //level +=", " +obj3.getProperty("level").toString();
                //Log.d("id",id);
                c_name.add(i,obj4.getProperty("name").toString());
                c_id.add(i,obj4.getProperty("category_id").toString());
                Log.d("name",obj4.getProperty("name").toString());
                //Log.d("level",level);

            }


            SoapObject requestImg = new SoapObject(NAMESPACE, "catalogProductAttributeMediaList");
            //SoapObject requestImg = new SoapObject(NAMESPACE, "catalogProductInfo");
            //SoapObject requestImg = new SoapObject(NAMESPACE, "catalogProductAttributeMediaTypes");

            requestImg.addProperty("sessionId", s_id);

            requestImg.addProperty("product","1");
            requestImg.addProperty("storeView","en");
            requestImg.addProperty("IdentifierType", "id");
            env.setOutputSoapObject(requestImg);
            androidHttpTransport.call("", env);


            //result = env.getResponse();
            //env.getResponse();

            //String str= ((SoapFault) env.bodyIn).faultstring;
            //Log.i("", str);
            SoapObject obj5 = (SoapObject) env.bodyIn;
            obj5=  (SoapObject)obj5.getProperty(0);
            obj5=  (SoapObject)obj5.getProperty(0);
            String img_url=obj5.getProperty("url").toString();

            //Log.d("image",obj5.toString());
            Log.d("image",img_url);




            //Log.d("result",result.getClass().toString());
            //Log.d("array",);
            //Log.d("List of Products", result.toString());
            //Log.d("List of Products", obj1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plain__cat, menu);
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
