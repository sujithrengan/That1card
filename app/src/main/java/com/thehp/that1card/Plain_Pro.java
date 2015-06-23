package com.thehp.that1card;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class Plain_Pro extends ActionBarActivity {

    CategoryListAdapter adapter;
    public  Plain_Cat CustomListView = null;
    public ArrayList<Listcategories> CustomListViewValuesArr = new ArrayList<Listcategories>();
    SliderLayout mDemoSlider;
    public String NAMESPACE="urn:Magento";
    public String s_id,c_id,pro_name="ProductName",pro_price="pricehere",pro_desc="Product description here.",pro_pricestrike="discount";
    public String URL2="http://192.168.42.49/magento/api/v2_soap/";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain__pro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.drawable.hannibal);
        file_maps.put("Big Bang Theory",R.drawable.bigbang);
        file_maps.put("House of Cards",R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView baseSliderView) {

                        }
                    });

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        Intent intent = getIntent();
        s_id = intent.getExtras().getString("s_id");
        c_id = intent.getExtras().getString("c_id");

        Log.d("cc",c_id);
        new ProAccessTask().execute();
    }


    class ProAccessTask extends AsyncTask<Void, Void, Void> {
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
            ImageView iv= (ImageView) findViewById(R.id.iv_pr);


            int loader = R.drawable.ic_launcher;

            // Imageview to show

            // Image url
            String image_url = "http://192.168.42.49/magento/media/catalog/product/t/1/t1c_ic.png";

            // ImageLoader class instance
            ImageLoader imgLoader = new ImageLoader(getApplicationContext());

            // whenever you want to load an image from url
            // call DisplayImage function
            // url - image url to load
            // loader - loader image, will be displayed before getting image
            // image - ImageView
            imgLoader.DisplayImage(image_url, loader,iv);

            TextView name=(TextView)findViewById(R.id.pro_name);
            name.setText(pro_name);
            TextView desc=(TextView)findViewById(R.id.pro_desc);
            desc.setText(pro_desc);
            TextView price=(TextView)findViewById(R.id.pro_price);
            price.setText("Rs " + pro_price);
            TextView pricestrike=(TextView)findViewById(R.id.pro_pricestrike);
            pricestrike.setText("Rs " + pro_pricestrike);
            pricestrike.setPaintFlags(pricestrike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            pricestrike.setTextColor(Color.RED);
           //iv.setImageBitmap(loadBitmap("http://192.168.42.49/magento/media/catalog/product/t/1/t1c_ic.png"));

        }

    }

    public static Bitmap LoadImageFromWebOperations(String url) {
        try {

            //InputStream is = (InputStream) new URL(url).getContent();
            //Drawable d = Drawable.createFromStream(is, "src name");
            //Log.d("loading..","try done");
            //return d;



            URL url2 = new URL("http://192.168.42.49/magento/media/catalog/product/t/1/t1c_ic.png");
            InputStream is = new BufferedInputStream(url2.openStream());
            Bitmap b = BitmapFactory.decodeStream(is);
            return b;


        } catch (Exception e) {
            Log.d("loading..","catch done");
            return null;
        }
    }
    private Bitmap loadBitmap(String url) throws MalformedURLException, IOException {
        return BitmapFactory.decodeStream(new FlushedInputStream((InputStream) new URL(url).getContent()));
    }

    public void readingData() {
        try {
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            env.dotNet = false;
            env.xsd = SoapSerializationEnvelope.XSD;
            env.enc = SoapSerializationEnvelope.ENC;

            SoapObject request = new SoapObject(NAMESPACE, "catalogCategoryAssignedProducts");

            request.addProperty("sessionId", s_id);
            request.addProperty("categoryId",Integer.parseInt(c_id));
            Log.d("dat",s_id+"====="+c_id);
            env.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL2);
            androidHttpTransport.call("", env);
            //String str= ((SoapFault) env.bodyIn).faultstring;
            //Log.i("", str);
           SoapObject obj3 = (SoapObject) env.bodyIn;
            obj3=  (SoapObject)obj3.getProperty(0);
            Log.d("dat",String.valueOf(obj3));


            //SoapObject requestImg = new SoapObject(NAMESPACE, "catalogProductAttributeMediaList");
            SoapObject requestImg = new SoapObject(NAMESPACE, "catalogProductInfo");
            //SoapObject requestImg = new SoapObject(NAMESPACE, "catalogProductAttributeMediaTypes");

            requestImg.addProperty("sessionId", s_id);

            requestImg.addProperty("productId","1");
            requestImg.addProperty("storeView","en");
            requestImg.addProperty("identifierType", "id");
            env.setOutputSoapObject(requestImg);
            androidHttpTransport.call("", env);


            //result = env.getResponse();
            //env.getResponse();

            //String str= ((SoapFault) env.bodyIn).faultstring;
            //Log.i("", str);
            SoapObject obj5 = (SoapObject) env.bodyIn;

            obj5=  (SoapObject)obj5.getProperty(0);
            pro_desc=obj5.getProperty("description").toString();
            pro_name=obj5.getProperty("name").toString();
            pro_price=obj5.getProperty("price").toString();
            pro_pricestrike=obj5.getProperty("special_price").toString();

            Log.d("image",obj5.toString());

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
        getMenuInflater().inflate(R.menu.menu_plain_, menu);
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
