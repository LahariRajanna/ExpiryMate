package com.example.expirymate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] pname;
    private final String[] ptype;
    private final String[] mfgdate;
    private final String[] expdate;
    public MyListAdapter(Activity  context, String[] pname, String[] ptype, String[] mfgdate, String[] expdate) {
        super(context, R.layout.productlist, pname);
        // TODO Auto-generated constructor stub
        this.context=  context;
        this.pname=pname;
        this.ptype=ptype;
        this.expdate=expdate;
        this.mfgdate=mfgdate;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.productlist, null,true);

        TextView pnameText = (TextView) rowView.findViewById(R.id.pnametxt);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView ptypeText = (TextView) rowView.findViewById(R.id.ptypetxt);
        TextView mfgText = (TextView) rowView.findViewById(R.id.mfgtxt);
        TextView expText = (TextView) rowView.findViewById(R.id.exptxt);

        pnameText.setText(pname[position]);
        ptypeText.setText(ptype[position]);
        expText.setText(expdate[position]);
        mfgText.setText(mfgdate[position]);

        return rowView;
    };

}
