package com.thehp.that1card;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.List;

/**
 * Created by HP on 04-06-2015.
 */
public class RV_pro_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Product> products;
    Context context;



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.productcard, viewGroup, false);
            ProductViewHolder pvh = new ProductViewHolder(v);
            return pvh;



    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder categoryViewHolder1, final int i) {

        final ProductViewHolder categoryViewHolder;



            categoryViewHolder=(ProductViewHolder)categoryViewHolder1;
            categoryViewHolder.ProName.setText(products.get(i).name);
            Typeface t;
            t = Typeface.createFromAsset(context.getAssets(),
                    "fonts/Roboto-Light.ttf");
            categoryViewHolder.ProName.setTypeface(t);
        categoryViewHolder.ProId.setText(products.get(i).desc);

        categoryViewHolder.ProId.setTypeface(t);

            categoryViewHolder.ProPhoto.setImageResource(products.get(i).photoId);
            categoryViewHolder.ProPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        categoryViewHolder.ProWish.setScaleType(ImageView.ScaleType.FIT_XY);
        if(categoryViewHolder.Wished==true) {
            categoryViewHolder.ProWish.setBackgroundResource(R.drawable.wishstar2);

        }
        else{

            categoryViewHolder.ProWish.setBackgroundResource(R.drawable.wishstar);

        }


            categoryViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, activity_c_productinfo.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.putExtra("s_id", "3");
                    //intent.putExtra("c_id", "5");
                    context.startActivity(intent);
                    //((activity_c_cat)context).finish();


                }
            });

        categoryViewHolder.ProWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categoryViewHolder.Wished==false) {
                    categoryViewHolder.ProWish.setBackgroundResource(R.drawable.wishstar2);
                    categoryViewHolder.Wished=true;
                }
                else{

                    categoryViewHolder.ProWish.setBackgroundResource(R.drawable.wishstar);
                    categoryViewHolder.Wished=false;
                }

                KeyUtils.wishlisted.add(products.get(i));
            }


        });
        categoryViewHolder.ProVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"video",Toast.LENGTH_SHORT).show();


                KeyUtils.vcode=products.get(i).video;
                Intent intent = new Intent(context.getApplicationContext(),YoutubeDialogActivity.class)
                     .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.getApplicationContext().startActivity(intent);



            }

        });






    }






    @Override
    public int getItemCount() {
        return products.size();

    }



    RV_pro_adapter(List<Product> pro,Context context){
        this.products = pro;
        this.context=context;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView ProName;
        TextView ProId;
        ImageView ProPhoto;
        ImageView ProWish;
        ImageView ProVideo;
        Boolean Wished;

        ProductViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvp);
            ProName = (TextView) itemView.findViewById(R.id.productname);
            ProId = (TextView)itemView.findViewById(R.id.productprice);
            ProPhoto = (ImageView) itemView.findViewById(R.id.productphoto);
            ProWish=(ImageView) itemView.findViewById(R.id.wishstar);
            ProVideo=(ImageView)itemView.findViewById(R.id.yt);
            Wished=false;

        }
    }




    }





