package com.thehp.that1card;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.security.Key;


public class activity_c_user2 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_user2);
        TextView uid=(TextView)findViewById(R.id.uid);
        uid.setText("Hello "+KeyUtils.user.FirstName+" "+KeyUtils.user.LastName);
        new EmailAccessTask().execute();


    }

    class EmailAccessTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),"Sent",Toast.LENGTH_SHORT).show();
            //Log.d("dat",String.valueOf(obj3));

        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*try {
                Log.e("email",KeyUtils.user.Email);
                GMailSender sender = new GMailSender("that1card.com@gmail.com", "that!card");
                sender.sendMail("Sorry google",
                        "Hi da",
                        "that1card.com@gmail.com",
                        KeyUtils.user.Email);
            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
            }*/

            attachcart();
            return null;
        }
    }

void attachcart()
{



    try{

        SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        env.dotNet = false;
        //env.implicitTypes=true;
        env.xsd = SoapSerializationEnvelope.XSD;
        env.enc = SoapSerializationEnvelope.ENC;

        SoapObject request = new SoapObject(KeyUtils.NAMESPACE, "shoppingCartCustomerSet");

        request.addProperty("sessionId",KeyUtils.s_id);
        request.addProperty("quoteId",KeyUtils.CartId);
        SoapObject item = new SoapObject(KeyUtils.NAMESPACE, "shoppingCartCustomerEntity");
        item.addProperty("mode","customer");
        item.addProperty("customer_id",Integer.parseInt(KeyUtils.user.uid));
        item.addProperty("email",KeyUtils.user.Email);
        item.addProperty("firstname",KeyUtils.user.FirstName);
        item.addProperty("lastname",KeyUtils.user.LastName);
        item.addProperty("password",KeyUtils.user.Pass);
        item.addProperty("website_id",1);
        item.addProperty("store_id",1);
        item.addProperty("group_id",1);

        request.addProperty("customer",item);
        env.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(KeyUtils.URL);
        androidHttpTransport.call("", env);
        SoapObject ob =(SoapObject)env.bodyIn;
        //boolean obj3 = (boolean) env.bodyIn;

        Log.d("dat",String.valueOf(ob));

        env.dotNet = true;
        env.implicitTypes = true;
        env.encodingStyle = SoapSerializationEnvelope.XSD;
        MarshalDouble md = new MarshalDouble();
        md.register(env);
        SoapObject cartarray=new SoapObject((KeyUtils.NAMESPACE),"shoppingCartProductEntityArray");
        SoapObject cartpro=new SoapObject((KeyUtils.NAMESPACE),"shoppingCartProductEntity");
        cartpro.addProperty("product_id","5");
        cartpro.addProperty("sku","1000");
        double qty=3;
        cartpro.addProperty("qty",qty);
        cartarray.addProperty("products",cartpro);

        SoapObject request2=new SoapObject(KeyUtils.NAMESPACE,"shoppingCartProductAdd");
        request2.addProperty("sessionId",KeyUtils.s_id);
        request2.addProperty("quoteId",KeyUtils.CartId);
        request2.addProperty("products",cartarray);

        env.setOutputSoapObject(request2);
        //HttpTransportSE androidHttpTransport = new HttpTransportSE(KeyUtils.URL);
        androidHttpTransport.call("", env);
        ob =(SoapObject)env.bodyIn;

        Log.d("datcart",String.valueOf(ob));

        SoapObject request5=new SoapObject(KeyUtils.NAMESPACE,"customerAddressList");
        request5.addProperty("sessionId",KeyUtils.s_id);
        request5.addProperty("customerId",KeyUtils.user.uid);
        env.setOutputSoapObject(request5);
        androidHttpTransport.call("", env);
        //ob =(SoapObject)env.bodyIn;

        SoapObject obb=(SoapObject)env.bodyIn;
        obb=(SoapObject)obb.getProperty(0);
        obb=(SoapObject)obb.getProperty(0);

        Log.d("datadress",String.valueOf(obb));
        SoapObject billaddress=new SoapObject(KeyUtils.NAMESPACE,"shoppingCartCustomerAddressEntity");
        SoapObject shipaddress=new SoapObject(KeyUtils.NAMESPACE,"shoppingCartCustomerAddressEntity");


        billaddress.addProperty("mode","billing");
        billaddress.addProperty("address_id","4");

        shipaddress.addProperty("mode","shipping");
        shipaddress.addProperty("address_id","4");
/*
        shipaddress.addProperty("firstname","testFirstname");
        shipaddress.addProperty("lastname","testLastname");
        shipaddress.addProperty("company","testCompany");
        shipaddress.addProperty("street","testStreet");
        shipaddress.addProperty("city","Trichy");
        shipaddress.addProperty("region","Tamil1nadu");
        shipaddress.addProperty("postcode","620015");
        shipaddress.addProperty("country_id","IN");
        shipaddress.addProperty("telephone","0123456789");
        shipaddress.addProperty("fax","0123456789");
        shipaddress.addProperty("is_default_shipping",0);
        shipaddress.addProperty("is_default_billing",0);*/

        SoapObject addressarray=new SoapObject(KeyUtils.NAMESPACE,"shoppingCartCustomerAddressEntityArray");
        addressarray.addProperty("customerAddressData ",billaddress);
        addressarray.addProperty("customer",shipaddress);

        SoapObject request6=new SoapObject(KeyUtils.NAMESPACE,"shoppingCartCustomerAddresses");
        request6.addProperty("sessionId",KeyUtils.s_id);
        request6.addProperty("quoteId",KeyUtils.CartId);
        request6.addProperty("customer",addressarray);
        env.setOutputSoapObject(request6);
        androidHttpTransport.call("", env);
        //ob =(SoapObject)env.bodyIn;

        SoapObject obb3=(SoapObject)env.bodyIn;
        //obb3=(SoapObject)obb.getProperty(0);
        //obb=(SoapObject)obb.getProperty(0);

        Log.d("datadress",String.valueOf(obb3));



        SoapObject request8=new SoapObject(KeyUtils.NAMESPACE,"shoppingCartShippingMethod");
        request8.addProperty("sessionId",KeyUtils.s_id);
        request8.addProperty("quoteId",KeyUtils.CartId);
        request8.addProperty("method","flatrate_flatrate");
        env.setOutputSoapObject(request8);
        androidHttpTransport.call("", env);
        //ob =(SoapObject)env.bodyIn;
        //SoapFault obb4=(SoapFault)env.bodyIn;
        //Log.d("datship",String.valueOf(obb4));

       SoapObject obb4=(SoapObject)env.bodyIn;
        Log.d("datship",String.valueOf(obb4));





        SoapObject pmethod=new SoapObject(KeyUtils.NAMESPACE,"shoppingCartPaymentMethodEntity");
        pmethod.addProperty("po_number",null);
        pmethod.addProperty("method","checkmo");
        pmethod.addProperty("cc_cid",null);
        pmethod.addProperty("cc_owner",null);
        pmethod.addProperty("cc_number",null);
        pmethod.addProperty("cc_type",null);
        pmethod.addProperty("cc_exp_year",null);
        pmethod.addProperty("cc_exp_month",null);


        SoapObject request9=new SoapObject(KeyUtils.NAMESPACE,"shoppingCartPaymentMethod");
        request9.addProperty("sessionId",KeyUtils.s_id);
        request9.addProperty("quoteId",KeyUtils.CartId);
        request9.addProperty("method",pmethod);

        env.setOutputSoapObject(request9);
        androidHttpTransport.call("", env);
        //ob =(SoapObject)env.bodyIn;
        //SoapFault obb4=(SoapFault)env.bodyIn;
        //Log.d("datship",String.valueOf(obb4));


        SoapObject obb5=(SoapObject)env.bodyIn;
        Log.d("datpay",String.valueOf(obb5));








        SoapObject request3=new SoapObject(KeyUtils.NAMESPACE,"shoppingCartOrder");
        request3.addProperty("sessionId",KeyUtils.s_id);
        request3.addProperty("quoteId",KeyUtils.CartId);
        env.setOutputSoapObject(request3);
        androidHttpTransport.call("", env);
        //ob =(SoapObject)env.bodyIn;

        SoapObject obb2=(SoapObject)env.bodyIn;
//        SoapFault obb2=(SoapFault)env.bodyIn;
       Log.d("datorder",String.valueOf(obb2));

        SoapObject request4=new SoapObject(KeyUtils.NAMESPACE,"shoppingCartInfo");
        request4.addProperty("sessionId",KeyUtils.s_id);
        request4.addProperty("quoteId",KeyUtils.CartId);
        env.setOutputSoapObject(request4);
        androidHttpTransport.call("", env);
        //ob =(SoapObject)env.bodyIn;

        SoapObject o=(SoapObject)env.bodyIn;
        Log.d("datcinfo",String.valueOf(o));


        //obb=(SoapFault)env.bodyIn;
        //Log.d("datorder",String.valueOf(obb));




    }
    catch (Exception r){
        r.printStackTrace();
    };


}

    public class MarshalDouble implements Marshal {
        public Object readInstance(XmlPullParser parser, String namespace,
                                   String name, PropertyInfo expected) throws IOException,
                XmlPullParserException {

            return Double.parseDouble(parser.nextText());
        }

        public void register(SoapSerializationEnvelope cm) {
            cm.addMapping(cm.xsd, "double", Double.class, this);

        }

        public void writeInstance(XmlSerializer writer, Object obj)
                throws IOException {
            writer.text(obj.toString());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_c_user2, menu);
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
