package com.example.nero;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import PaqueteClasesPrincipales.Comentario;
import PaqueteClasesPrincipales.Usuario;
import PaqueteRequest.AnyadirReporteComentarioRequest;
import PaqueteRequestQueueSingleton.SingletonRequestQueue;

public class AnyadirReporteComentario extends AppCompatActivity {
    //Constantes
    private static final int MY_SOCKET_TIMEOUT_MS = 6000;
    private String[] items;
    //Elementos
    private TextInputLayout tipo;
    private TextInputLayout descripcion;
    private AutoCompleteTextView autocomplete;
    private MaterialButton btn_anyadirReporte;

    //Datos
    private Usuario thisUser;
    private Comentario thisComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_reporte_comentario);

        //R
        tipo = (TextInputLayout) findViewById(R.id.campo_seleccionar_tipo);
        descripcion = (TextInputLayout) findViewById(R.id.campo_anyadir_reporte_comentario);
        autocomplete = (AutoCompleteTextView) findViewById(R.id.autocomplete_reportar_comentario);
        btn_anyadirReporte = (MaterialButton) findViewById(R.id.btn_realizar_reporte);

        //Datos
        obtenerDatos();

        //Configuracion del Dropdown
        items = new String[] {
                "Spam o publicidad",
                "Incita al odio",
                "Otros"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.dropdown_anyadir_reporte_item,
                items
        );
        autocomplete.setAdapter(adapter);

        //Eventos
        tipo.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(tipo.getError()!=null)
                    tipo.setError(null);
            }
        });


        btn_anyadirReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean encontrado = false;
                int i = 0;
                while (!encontrado && i<items.length){
                    if(tipo.getEditText().getText().toString().equalsIgnoreCase(items[i]))
                        encontrado = true;
                    i++;
                }

                if(encontrado){
                    String t = "Tipo:"+tipo.getEditText().getText().toString()+", descripcion: "+descripcion.getEditText().getText().toString();
                    usarVolley(t);
                } else {
                    tipo.setError("Seleccione una opción");
                }
            }
        });
    }

    private void usarVolley(String idescripcion){

        Response.Listener<String> respuesta = new Response.Listener<String>(){
            @Override
            public void onResponse(String respuesta){
                try{
                    JSONObject jsonRespuesta = new JSONObject(respuesta);
                    if(!jsonRespuesta.getBoolean("malLogeado")){
                        if(!jsonRespuesta.getBoolean("yaExisteReporte")){
                            if(jsonRespuesta.getBoolean("success")){
                                Toast.makeText(
                                        AnyadirReporteComentario.this,
                                        "Reporte realizado correctamente",
                                        Toast.LENGTH_LONG)
                                        .show();
                                finish();
                            } else {
                                Toast.makeText(
                                        AnyadirReporteComentario.this,
                                        "Error con la conexion",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            Toast.makeText(
                                    AnyadirReporteComentario.this,
                                    "Ya habia reportado anteriormente este comentario",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } else {
                        Toast.makeText(
                                AnyadirReporteComentario.this,
                                "Ha ocurrido un error. Inicie sesion nuevamente.",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                } catch (JSONException e){
                    System.out.println("------------ERROR JSON:"+e.getMessage());
                }
            }
        };

        AnyadirReporteComentarioRequest arcr = new AnyadirReporteComentarioRequest(
                thisUser.getNombre_usuario(),
                thisUser.getPassword(),
                thisComentario.getId()+"",
                idescripcion,
                respuesta
        );
        arcr.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonRequestQueue.getInstance(this).addToRequestQueue(arcr);
    }

    //Obtencion de datos de la activity anterior
    private void obtenerDatos(){
        Bundle extras = getIntent().getExtras();
        thisUser = (Usuario) extras.getSerializable("PasarEsteUsuario");
        thisComentario = (Comentario) extras.getSerializable("PasarEsteComentario");
    }
}


