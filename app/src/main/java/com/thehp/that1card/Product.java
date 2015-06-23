package com.thehp.that1card;

import java.util.List;

/**
 * Created by HP on 04-06-2015.
 */
public class Product {

    public String name;
    public String id;
    public String desc;
    public List<Integer> cid;
    public int price;
    public int discprice;
    public String video;
    public String spl_price;






    int photoId;

    Product(String name,String id, int photoId,List<Integer> cid) {
        this.name = name;
        this.id = id;
        this.photoId = photoId;
        this.cid=cid;
        this.video="";

    }
}
