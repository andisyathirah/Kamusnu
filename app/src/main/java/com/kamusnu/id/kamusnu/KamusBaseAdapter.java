package com.kamusnu.id.kamusnu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andrya on 9/13/2015.
 */
public class KamusBaseAdapter extends BaseAdapter {

    ArrayList<entitaskamus> searchArrayList;
    LayoutInflater mInflater;

    public KamusBaseAdapter(Context context, ArrayList<entitaskamus> result){
        searchArrayList = result;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return searchArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder;

        if(v==null){
            v = mInflater.inflate(R.layout.item_custom_listview,null);
            holder = new ViewHolder();

            holder.bIndo = (TextView) v.findViewById(R.id.outIndo);
            holder.bSunda = (TextView) v.findViewById(R.id.outSunda);

            v.setTag(holder);

        }else{
            holder = (ViewHolder) v.getTag();

        }
        holder.bIndo.setText(searchArrayList.get(position).getIndo());
        holder.bSunda.setText(searchArrayList.get(position).getSunda());
        return v;
    }

    class ViewHolder{
        TextView bIndo,bSunda;
    }
}

</entitaskamus></entitaskamus>
