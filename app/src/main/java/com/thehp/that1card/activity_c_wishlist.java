package com.thehp.that1card;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class activity_c_wishlist extends ActionBarActivity {

    List<Product> persons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_wishlist);

        initializeData();

        RecyclerView rv = (RecyclerView)findViewById(R.id.rvwl);

        GridLayoutManager llm = new GridLayoutManager(this,2);
        rv.setLayoutManager(llm);
        RV_pro_adapter adapter = new RV_pro_adapter(persons,getApplicationContext());
        rv.setAdapter(adapter);

    }
    private void initializeData(){
        persons = new ArrayList<>();
        persons=KeyUtils.wishlisted;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_c_wishlist, menu);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
