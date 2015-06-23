package com.thehp.that1card;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;


public class activity_c_productlist extends ActionBarActivity {

    List<Integer> pids;
    public String catindex="";
    List<Product> persons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_prolist);
        pids=new ArrayList<Integer>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        initializeData();

        RecyclerView rv = (RecyclerView)findViewById(R.id.rvp);

       GridLayoutManager llm = new GridLayoutManager(this,2);
        rv.setLayoutManager(llm);
        RV_pro_adapter adapter = new RV_pro_adapter(persons,getApplicationContext());
        rv.setAdapter(adapter);

        new SessionAccessTask().execute();
    }

    private void initializeData(){
        persons = new ArrayList<>();
        //persons=KeyUtils.pro;
        for(int n=0;n<KeyUtils.pro.size();n++)
        {
            for(int b=0;b<KeyUtils.pro.get(n).cid.size();b++)
            {
                if(KeyUtils.cplist==KeyUtils.pro.get(n).cid.get(b)){
                    persons.add(KeyUtils.pro.get(n));
                    break;
                }
            }
        }
       /* persons.add(new Product("Product1", "Rs 500", R.drawable.wc));
        persons.add(new Product("Product2", "Rs 1100", R.drawable.bc));
        persons.add(new Product("Product3", "Rs 400", R.drawable.gc));
        persons.add(new Product("Product4", "Rs 5200", R.drawable.wc));
        persons.add(new Product("Birthday Cards", "Rs 700", R.drawable.bc));
        persons.add(new Product("Greeting Cards", "Rs 500", R.drawable.gc));
        persons.add(new Product("Wedding Cards", "Rs 1500", R.drawable.wc));
        persons.add(new Product("Birthday Cards", "Rs 550", R.drawable.bc));
        persons.add(new Product("Greeting Cards", "Rs 990", R.drawable.gc));
        */

    }

    class SessionAccessTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
//pBar.setVisibility(View.VISIBLE);
            Log.e("onPreExecute",
                    "----------------onPreExecute-----------------");
            //prgDialog.show();
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
            //prgDialog.hide();
            //prgDialog.dismiss();
            //initializeData();

        }

    }


    public void readingData() {
        try {
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            env.dotNet = false;
            env.xsd = SoapSerializationEnvelope.XSD;
            env.enc = SoapSerializationEnvelope.ENC;

            //SoapObject request = new SoapObject(KeyUtils.NAMESPACE, "catalogCategoryAssignedProducts");

            //request.addProperty("sessionId", KeyUtils.s_id);
            //request.addProperty("categoryId",KeyUtils.cplist);
            //Log.d("dat", s_id + "=====" + c_id);
            //env.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(KeyUtils.URL);
            //androidHttpTransport.call("", env);
            //String str= ((SoapFault) env.bodyIn).faultstring;
            //Log.i("", str);
            /*SoapObject obj3 = (SoapObject) env.bodyIn;
            obj3=  (SoapObject)obj3.getProperty(0);
            Log.d("dat",String.valueOf(obj3));

            for(int i=0;i<obj3.getPropertyCount();i++)
            {
                obj4=(SoapObject)obj3.getProperty(i);
                obj4.getProperty("product_id");
            }
*/

            //SoapObject requestImg = new SoapObject(NAMESPACE, "catalogProductAttributeMediaList");
            //SoapObject requestImg = new SoapObject(KeyUtils.NAMESPACE, "catalogProductInfo");
            //SoapObject requestImg = new SoapObject(NAMESPACE, "catalogProductAttributeMediaTypes");


            for(int k=0;k<persons.size();k++) {

                SoapObject obj4,obj5;
                SoapObject requestImg = new SoapObject(KeyUtils.NAMESPACE, "catalogProductInfo");
                SoapObject attstr = new SoapObject(KeyUtils.NAMESPACE, "ArrayOfStrings");
                attstr.addProperty("item","videoid");


                SoapObject att = new SoapObject(KeyUtils.NAMESPACE, "catalogProductRequestAttributes");
                att.addProperty("additional_attributes",attstr);
                requestImg.addProperty("sessionId", KeyUtils.s_id);

                requestImg.addProperty("productId", persons.get(k).id);
                requestImg.addProperty("storeView", "en");
                requestImg.addProperty("attributes", att);

                requestImg.addProperty("identifierType", "id");
                env.setOutputSoapObject(requestImg);
                androidHttpTransport.call("", env);

               // obj6 = (SoapObject) env.bodyIn;

                SoapObject obj6 = (SoapObject) env.bodyIn;
                Log.e("image",obj6.toString());
                obj5=  (SoapObject)obj6.getProperty(0);
                obj5=  (SoapObject)obj5.getProperty("additional_attributes");
                obj5=  (SoapObject)obj5.getProperty(0);
                KeyUtils.pro.get(k).video=obj5.getProperty("value").toString();

                Log.e("id",persons.get(k).id);
                Log.d("image",obj6.toString());
            }
            //result = env.getResponse();
            //env.getResponse();

            //String str= ((SoapFault) env.bodyIn).faultstring;
            //Log.i("", str);
           // obj5 = (SoapObject) env.bodyIn;

            //obj5=  (SoapObject)obj5.getProperty(0);
            /*
            pro_desc=obj5.getProperty("description").toString();
            pro_name=obj5.getProperty("name").toString();
            pro_price=obj5.getProperty("price").toString();
            pro_pricestrike=obj5.getProperty("special_price").toString();
*/
            //Log.d("image",obj5.toString());

            //String img_url=obj5.getProperty("url").toString();

            //Log.d("image",obj5.toString());
            //Log.d("image",img_url);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_c_productlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
