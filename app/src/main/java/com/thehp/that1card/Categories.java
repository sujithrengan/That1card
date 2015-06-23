package com.thehp.that1card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 04-06-2015.
 */
class Categories {
    String name;
    String id;
    String desc;
    List<Categories> children;


    int photoId;

    public int getChildrenCount()
    {

       return this.children.size();
    }
    public void SetChildren(List<Categories> children)
    {
        this.children=children;

    }
    Categories(String name, String age, int photoId) {
        this.name = name;
        this.id = age;
        this.photoId = photoId;
        this.children=new ArrayList<Categories>();



    }
}