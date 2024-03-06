package com.example.expirymate;

public class NewMedicine {

    private String medicineid;
    private String medicinename;
    private String medicinetype;
    private String qty;
    private String description;
    private String mfrdate;
    private String expdate;
    private String userid;
    private String phnum;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NewMedicine()
    {
    }

    public NewMedicine(String medicineid, String medicinename, String medicinetype, String qty, String mfrdate,
                       String expdate, String description, String userid,String phnum)
    {
        this.setMedicineid(medicineid);
        this.setMedicinename(medicinename);
        this.setMedicinetype(medicinetype);
        this.setQty(qty);
        this.setMfrdate(mfrdate);
        this.setExpdate(expdate);
        this.setDescription(description);
        this.setUserid(userid);
        this.setPhnum(phnum);
    }


    public String getMedicineid() {
        return medicineid;
    }

    public void setMedicineid(String medicineid) {
        this.medicineid = medicineid;
    }

    public String getMedicinename() {
        return medicinename;
    }

    public void setMedicinename(String medicinename) {
        this.medicinename = medicinename;
    }

    public String getMedicinetype() {
        return medicinetype;
    }

    public void setMedicinetype(String medicinetype) {
        this.medicinetype = medicinetype;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getMfrdate() {
        return mfrdate;
    }

    public void setMfrdate(String mfrdate) {
        this.mfrdate = mfrdate;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhnum() {
        return phnum;
    }

    public void setPhnum(String phnum) {
        this.phnum = phnum;
    }

}
