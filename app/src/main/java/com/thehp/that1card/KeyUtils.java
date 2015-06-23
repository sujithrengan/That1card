package com.thehp.that1card;

import android.app.ProgressDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by HP on 05-06-2015.
 */
public class KeyUtils {

    public static String NAMESPACE="urn:Magento";
    public static String DEVELOPER_KEY="AIzaSyBoSC8DwE2uwkfDsdbJYq4UczJuCOPLbnM";
    public static String vcode="Gl5jTFB82MU";
    public static String soapusername="appdev";
    public static String soapkey="23011996thehp";
    public static int catLevel;
   // public static String URL="http://192.168.42.49/magento/api/v2_soap/";
   public static String URL="http://52.74.234.78/index.php/api/v2_soap/";
    public static int CartId;
    public static String s_id;
    public static Vector<String> c_id;
    public static User user;
    public static Vector<String> c_name;
    public static Vector<String> child_c_id;
    public static Vector<String> child_c_name;
    public static List<Product> allproducts;
    public static List<Product> wishlisted;
    public static List<Categories> cat;
    public static List<Product> pro;
    public static List<Categories> child_cat;
    public static int cplist;
    public static int plist;
public static boolean logged=false;
    public static void initvectors() {

        cat = new ArrayList<Categories>();
        pro = new ArrayList<Product>();
        s_id = "null";
        catLevel = 1;
        c_id = new Vector<String>();
        c_name = new Vector<String>();
        child_c_id = new Vector<String>();
        child_c_name = new Vector<String>();
        wishlisted = new ArrayList<>();


        user = new User();
    }

}
