package com.thehp.that1card;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
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

import java.util.HashMap;
import java.util.List;

/**
 * Created by HP on 04-06-2015.
 */



public class RV_cat_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Categories> categories;
    Context context;

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return 1;
        else
            return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        if(i==0) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categorycard, viewGroup, false);
            CategoryViewHolder pvh = new CategoryViewHolder(v);
            return pvh;
        }
        else
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categorybannercard, viewGroup, false);
            BannerViewHolder pvh = new BannerViewHolder(v,context);
            return pvh;

        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder categoryViewHolder1, final int i) {

        CategoryViewHolder categoryViewHolder;
        BannerViewHolder bannerViewHolder;
        if(i!=0) {

            categoryViewHolder=(CategoryViewHolder)categoryViewHolder1;
            categoryViewHolder.CatName.setText(categories.get(i).name);
            Typeface t;
            t = Typeface.createFromAsset(context.getAssets(),
                    "fonts/Roboto-Light.ttf");
            categoryViewHolder.CatName.setTypeface(t);

            categoryViewHolder.CatPhoto.setImageResource(categories.get(i).photoId);
            categoryViewHolder.CatPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
            categoryViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(categories.get(i).getChildrenCount()>0) {

                        ++KeyUtils.catLevel;
                        Intent intent = new Intent(context, activity_c_cat2.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("cat",i);
                        //intent.putExtra("depth",i);

                        context.startActivity(intent);

                    }

                    else
                    {
                        Intent intent = new Intent(context, activity_c_productlist.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        KeyUtils.cplist=Integer.parseInt(categories.get(i).id);
                        context.startActivity(intent);
                        //((activity_c_cat)context).finish();
                    }

                }
            });
        }
        else
        {
            bannerViewHolder=(BannerViewHolder)categoryViewHolder1;
            if(bannerViewHolder.set==false)
            {

                for(String name : bannerViewHolder.file_maps.keySet()){
                    TextSliderView textSliderView = new TextSliderView(context);
                    // initialize a SliderLayout
                    textSliderView
                            .description(name)
                            .image(bannerViewHolder.file_maps.get(name))
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
                    //textSliderViewlist.add(textSliderView);
                    bannerViewHolder.slide.addSlider(textSliderView);
                }



                bannerViewHolder.set=true;
            }




            Log.e("k","kkkkkk");
        }

    }

    @Override
    public int getItemCount() {
        return categories.size();

    }



    RV_cat_adapter(List<Categories> cat,Context context){
        this.categories = cat;
        this.context=context;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView CatName;
        TextView CatId;
        ImageView CatPhoto;

        CategoryViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            CatName = (TextView) itemView.findViewById(R.id.categoryname);
            //CatId = (TextView)itemView.findViewById(R.id.person_age);
            CatPhoto = (ImageView) itemView.findViewById(R.id.categoryphoto);

        }
    }

        public static class BannerViewHolder extends RecyclerView.ViewHolder {

            CardView cv1;
            Boolean set;
            HashMap<String,Integer> file_maps = new HashMap<String, Integer>();

            //List<TextSliderView> textSliderViewlist;
            SliderLayout slide;

            BannerViewHolder(View itemView,Context context) {


                super(itemView);
                set=false;


                file_maps.put("Elite Cards",R.drawable.slide1);
                file_maps.put("Special Invites",R.drawable.slide2);
                file_maps.put("Simple Cards",R.drawable.slide3);
                file_maps.put("Wedding Cards", R.drawable.slide4);
                cv1 = (CardView) itemView.findViewById(R.id.cvb);



                slide = (SliderLayout) itemView.findViewById(R.id.slider2);
                slide.setPresetTransformer(SliderLayout.Transformer.Default);
                slide.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                slide.setCustomAnimation(new DescriptionAnimation());
                slide.setDuration(4000);

            }


        }




}