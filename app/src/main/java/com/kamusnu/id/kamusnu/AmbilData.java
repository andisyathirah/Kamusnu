package com.kamusnu.id.kamusnu;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrya on 9/13/2015.
 */
public class InUpDelData extends AsyncTask<string tring=""> {

        ProgressDialog pg;
        String url = "http://192.168.42.48/KamusAndroid/tambah.php";
        Context context;
        JSONParser jsonParser;
        JSONObject jsonObject;
        String act;

public void init(Context c,String ids, String indo, String sunda, String action){
        context = c;
        act = action;
        InUpDelData inUpDelData = this;
        inUpDelData.execute(indo,sunda,action,ids);
        }

@Override
protected void onPreExecute() {
        super.onPreExecute();
        pg = new ProgressDialog(context);
        pg.setTitle("Menyimpan Data");
        pg.setMessage("Mohon Tunggu");
        pg.show();
        }

@Override
protected String doInBackground(String... params) {
        jsonParser = new JSONParser();
        jsonObject = null;

        String indo,sunda,aksi,sukses,id;
        sukses = "";
        indo = (String) params[0];
        sunda = (String) params[1];
        aksi = (String) params[2];
        id = (String) params[3];

        List<namevaluepair> param = new ArrayList<namevaluepair>();
        param.add(new BasicNameValuePair("bIndo",indo));
        param.add(new BasicNameValuePair("bSunda",sunda));
        param.add(new BasicNameValuePair("action",aksi));
        param.add(new BasicNameValuePair("id",id));

        try {
        jsonObject = jsonParser.getJsonObject("POST",url,param);
        } catch (IOException e) {
        e.printStackTrace();
        }

        try {
        int stat = jsonObject.getInt("status");
        if(stat == 1){
        sukses = "sukses";
        }else{
        sukses = "gagal";
        }
        } catch (JSONException e) {
        e.printStackTrace();
        }



        return sukses;
        }

@Override
protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s=="sukses"){
        if(act=="delete"){
        Toast.makeText(context,"Kata berhasil dihapus!", Toast.LENGTH_LONG).show();
        }else {
        Toast.makeText(context, "Kata berhasil disimpan!", Toast.LENGTH_LONG).show();
        }
        }else{
        if(act=="delete"){
        Toast.makeText(context,"Kata gagal dihapus!", Toast.LENGTH_LONG).show();
        }else {
        Toast.makeText(context, "Kata gagal disimpan!", Toast.LENGTH_LONG).show();
        }
        }
        pg.dismiss();
        }
        }

</namevaluepair></namevaluepair></string>