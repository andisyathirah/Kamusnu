package com.kamusnu.id.kamusnu;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.infinityware.org.kamusku.AmbilData.JSONObjectResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Andrya on 9/13/2015.
 */
public class TambahActivity extends Activity {

    ArrayList<entitaskamus> listKamus = new ArrayList<entitaskamus>();
    EntitasKamus kamus;
    int count = 0;
    ListView lv;
    Button btSimpan;
    EditText bIndo,bSunda;
    //String url = "http://10.0.2.2/KamusAndroid/kamus.php"; //INI UNTUK Emulator
    String url = "http://192.168.42.48/KamusAndroid/kamus.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        lv = (ListView) findViewById(R.id.listView1);
        bIndo = (EditText) findViewById(R.id.addIndo);
        bSunda = (EditText) findViewById(R.id.addSunda);
        btSimpan = (Button) findViewById(R.id.bSimpan);

        loadKamus();

        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InUpDelData inUpDelData = new InUpDelData();
                inUpDelData.init(TambahActivity.this,"",bIndo.getText().toString(),
                        bSunda.getText().toString(),"insert");
                loadKamus();
                bIndo.setText("");
                bSunda.setText("");
            }
        });

    }

    //MEMUAT KATA DALAM DATABASE
    public void loadKamus(){
        Bundle b = this.getIntent().getExtras();
        String var = b.getString("var");
        final AmbilData ambilData = new AmbilData();
        ambilData.init(var,url,TambahActivity.this,jres);
    }

    //DEKLARASI UNTUK ABSTRACT CLASS
    public JSONObjectResult jres = new JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("kamus");
                for(int i=0; i < <jsonArray.length();i++){
                    kamus = new EntitasKamus();
                    kamus.setId(jsonArray.getJSONObject(i).getString("id"));
                    kamus.setIndo(jsonArray.getJSONObject(i).getString("indonesia"));
                    kamus.setSunda(jsonArray.getJSONObject(i).getString("sunda"));

                    listKamus.add(kamus);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(count>0){
                listKamus.clear();
                KamusBaseAdapter kamusBaseAdapter = new KamusBaseAdapter(TambahActivity.this,listKamus);
                lv.setAdapter(kamusBaseAdapter);
                loadKamus();
                count = -1;
            }else{
                KamusBaseAdapter kamusBaseAdapter = new KamusBaseAdapter(TambahActivity.this,listKamus);
                lv.setAdapter(kamusBaseAdapter);
            }

            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                    String ids,indo,sunda;
                    ids = listKamus.get(position).getId();
                    indo = listKamus.get(position).getIndo();
                    sunda = listKamus.get(position).getSunda();
                    dialogOption(ids,indo,sunda);
                    return false;
                }
            });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {

                }
            });

            count +=1;
        }
    };


    //DIALOG PILIH AKSI
    public void dialogOption(final String id, final String indo, final String sunda){
        final Dialog dlg = new Dialog(this);
        dlg.setContentView(R.layout.dialog_option);
        dlg.setTitle("Pilih Aksi");
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dlg.getWindow().getAttributes());
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dlg.getWindow().setAttributes(lp);

        Button update,delete;
        update = (Button) dlg.findViewById(R.id.opUpdate);
        delete = (Button) dlg.findViewById(R.id.opDelete);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdate(id, indo, sunda);
                dlg.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDelete(id);
                dlg.dismiss();
            }
        });

        dlg.show();
    }

    //DIALOG UPDATE
    public void dialogUpdate(final String id, String indo, String sunda){
        final Dialog dialog = new Dialog(TambahActivity.this);
        dialog.setContentView(R.layout.dialog_edit);
        dialog.setTitle("Perbaharaui Kata");
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);

        final EditText edIndo,edSunda;

        edIndo = (EditText) dialog.findViewById(R.id.edIndo);
        edSunda = (EditText) dialog.findViewById(R.id.edSunda);
        Button btUpdate, btBack;

        edIndo.setText(indo);
        edSunda.setText(sunda);

        btUpdate = (Button) dialog.findViewById(R.id.btUpdate);


        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InUpDelData inUpDelData = new InUpDelData();
                inUpDelData.init(TambahActivity.this, id, edIndo.getText().toString()
                        , edSunda.getText().toString(), "update");
                dialog.dismiss();
                loadKamus();
            }
        });


        dialog.show();
    }

    //DIALOG DELETE
    public void dialogDelete(final String id){
        final Dialog dlg = new Dialog(this);
        dlg.setContentView(R.layout.dialog_delete);
        dlg.setTitle("Apakah anda yakin?");
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dlg.getWindow().getAttributes());
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dlg.getWindow().setAttributes(lp);

        Button btYa, btTidak;

        btYa = (Button) dlg.findViewById(R.id.btYa);
        btTidak = (Button) dlg.findViewById(R.id.btTidak);

        btYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                InUpDelData inUpDelData = new InUpDelData();
                inUpDelData.init(TambahActivity.this,id,"","","delete");

                loadKamus();
            }
        });

        btTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        dlg.show();
    }

}





</entitaskamus></entitaskamus>
