package com.thehp.that1card;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;


public class activity_c_blog extends ActionBarActivity {

    String html="",mime="",encoding="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_blog);

        new BlogAccessTask().execute();






/*

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                webview.loadUrl("javascript:(function() { " +
                        "document.getElementsByTagName('header')[0].style.display=\"none\"; " + "})()");
                webview.loadUrl("javascript:(function() { " +
                        "document.getElementsByTagName('footer')[0].style.display=\"none\"; " + "})()");

            }
        });
        webview.loadUrl(url);


        */
    }



    class BlogAccessTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
//pBar.setVisibility(View.VISIBLE);
            Log.e("onPreExecute",
                    "----------------onPreExecute-----------------");
            //prgDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Blogparse();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            Log.e("onPostExecute",
                    "----------------onPostExecute-----------------");
            //prgDialog.hide();
            //prgDialog.dismiss();
            //initializeData();
            final WebView webview = (WebView)findViewById(R.id.browser);
            webview.loadData(html, mime, encoding);
            webview.setInitialScale(60);
            webview.getSettings().setBuiltInZoomControls(true);
            webview.getSettings().setSupportZoom(true);

        }

    }


    void Blogparse()
    {

        String url="http://52.74.234.78/index.php/marriage-blog";



        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements ele = doc.select("div#content-wrapper");


        html = ele.toString();
       mime = "text/html";
       encoding = "utf-8";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_c_blog, menu);
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
