package com.example.api_restful;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.api_restful.WebServices.Asynchtask;
import com.example.api_restful.WebServices.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LugaresTuristicos extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lugares_turisticos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AutoCompleteTextView autocListaLugares =
                findViewById(R.id.autocListaLugares);
        autocListaLugares
                .setOnItemClickListener ((AdapterView<?> parent, View view, int position ,long id) -> {
            Map<String, String> datos = new HashMap<String, String>();
            WebService ws= new WebService(
                    "https://turismo.quevedoenlinea.gob.ec/lugar_turistico/json_getlistadoGridLT/" + (position +1),
                    datos, LugaresTuristicos.this, LugaresTuristicos.this);
            ws.execute("GET");
        });

    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView txtRes = findViewById(R.id.txtRes);
        String lstLista="";

        JSONObject resultados = new JSONObject(result);
        JSONArray JSONlista = resultados.getJSONArray("data");
        for(int i=0; i< JSONlista.length();i++){
            JSONObject banco= JSONlista.getJSONObject(i);
            lstLista = lstLista + i + ".- " +
                    banco.getString("nombre_lugar").toString()
                    + " - " +
                    banco.getString("categoria").toString() + "\n" ;
        }
        txtRes.setText(lstLista);
    }
}