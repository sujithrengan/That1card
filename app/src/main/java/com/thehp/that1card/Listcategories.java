package com.thehp.that1card;

/**
 * Created by HP on 27-05-2015.
 */
public class Listcategories {

    private  String Name="";
    private  String Image="";
    private  String id="";

    /*********** Set Methods ******************/

    public void setName(String Name)
    {
        this.Name = Name;
    }

    public void setImage(String Image)
    {
        this.Image = Image;
    }

    public void setid(String id)
    {
        this.id = "c_id: "+id;
    }

    /*********** Get Methods ****************/

    public String getName()
    {
        return this.Name;
    }

    public String getImage()
    {
        return this.Image;
    }

    public String getid()
    {
        return this.id;
    }
}