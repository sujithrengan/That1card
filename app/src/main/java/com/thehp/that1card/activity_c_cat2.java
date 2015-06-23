package com.thehp.that1card;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class activity_c_cat2 extends ActionBarActivity {

    public int catindex;
    public List<Categories> cat2;
    int dr[]={R.drawable.wc,R.drawable.gc,R.drawable.bc};
    RecyclerView rv;
    RV_cat_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_cat2);

        Intent intent = getIntent();
        catindex = intent.getExtras().getInt("cat");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cat2=new ArrayList<Categories>();
        cat2=KeyUtils.cat.get(catindex).children;
        for(int i=0;i<cat2.size();i++)
        {
            Log.e("cat2",cat2.get(i).name);
        }



        rv = (RecyclerView)findViewById(R.id.rv2);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        adapter = new RV_cat_adapter(cat2,getApplicationContext());
        rv.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_c_cat2, menu);
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
