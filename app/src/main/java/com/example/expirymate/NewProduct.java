package com.example.expirymate;

public class NewProduct {

    private String productid;
    private String productname;
    private String producttype;
    private String qty;
    private String expdate;
    private String mfgdate;
    private String userid;
    private String phnum;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public NewProduct()
    {
    }

    public NewProduct(String productid, String productname, String producttype, String qty, String mfgdate,
                      String expdate, String description, String userid, String phnum)
    {
        this.setProductid(productid);
        this.setProductname(productname);
        this.setProducttype(producttype);
        this.setQty(qty);
        this.setMfgdate(mfgdate);
        this.setExpdate(expdate);
        this.setDescription(description);
        this.setUserid(userid);
        this.setPhnum(phnum);
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getMfgdate() {
        return mfgdate;
    }

    public void setMfgdate(String mfgdate) {
        this.mfgdate = mfgdate;
    }

    public String getPhnum() {
        return phnum;
    }

    public void setPhnum(String phnum) {
        this.phnum = phnum;
    }

}
