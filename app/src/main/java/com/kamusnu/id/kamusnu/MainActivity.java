package com.kamusnu.id.kamusnu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.infinityware.org.kamusku.AmbilData.JSONObjectResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {

    EntitasKamusku kamus = new EntitasKamusku();
    ArrayList<entitaskamus> listKamus = new ArrayList<entitaskamus>();

    EditText indo;
    TextView sunda;
    Button btTambah;
    ImageButton btTrans;
    //String url = "http://10.0.2.2/KamusAndroid/kamus.php"; //INI UNTUK Emulator
    String url = "http://192.168.42.48/KamusAndroid/kamus.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indo = (EditText) findViewById(R.id.input);
        sunda = (TextView) findViewById(R.id.output);
        btTambah = (Button) findViewById(R.id.bTambah);
        btTrans = (ImageButton) findViewById(R.id.bSearch);

        btTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, TambahActivity.class);
                Bundle b = new Bundle();
                b.putString("var","kosong");
                in.putExtras(b);
                startActivity(in);

            }
        });

        btTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilData ambilData = new AmbilData();
                ambilData.init(indo.getText().toString(),url,MainActivity.this,jres);
                indo.setText("");
            }
        });

    }

    JSONObjectResult jres = new JSONObjectResult() {
        @Override
        public void gotJSONObject(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("kamus");
                for(int i=0; i< <jsonArray.length(); i++){
                    sunda.setText(jsonArray.getJSONObject(i).getString("sunda"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}

</entitaskamus></entitaskamus>
