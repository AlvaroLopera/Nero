package com.example.nero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import PaqueteClasesPrincipales.Fuente;
import PaqueteClasesPrincipales.Usuario;
import PaqueteRequest.AnyadirComentarioRequest;
import PaqueteRequestQueueSingleton.SingletonRequestQueue;

public class AnyadirComentario extends AppCompatActivity {
    //Elementos
    private TextInputLayout comentario;
    private FloatingActionButton btn_aceptar;

    //Datos
    private Usuario thisUser;
    private Fuente thisFuente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_comentario);

        //Obtención de datos
        obtenerDatos();

        //R
        comentario = (TextInputLayout) findViewById(R.id.campo_anyadircomentario_texto);
        btn_aceptar = (FloatingActionButton) findViewById(R.id.btn_anyadircomentario_aceptar);

        //Eventos
        comentario.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(comentario.getError()!= null){
                    comentario.setError(null);
                }
            }
        });

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = comentario.getEditText().getText().toString();
                if(txt.isEmpty()){
                    comentario.setError("Coloca algún comentario.");
                } else {
                    //USAR VOLLEY
                    crearNuevoComentario(txt);
                }
            }
        });
    }

    private void crearNuevoComentario(String txt){

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject j = new JSONObject(response);
                    if(j.getBoolean("success")){
                        Toast.makeText(AnyadirComentario.this, "Se ha realizado el comentario correctamente", Toast.LENGTH_SHORT)
                                .show();
                        finish();
                    } else {
                        Toast.makeText(AnyadirComentario.this, "Ha ocurrido un error. Prueba otra vez", Toast.LENGTH_SHORT)
                                .show();
                    }
                } catch (JSONException ex){
                    System.out.println("Error: "+ex.getMessage());
                }

            }
        };

        AnyadirComentarioRequest ac = new AnyadirComentarioRequest(thisUser.getNombre_usuario(), thisUser.getPassword(), txt, thisFuente.getId()+"", respuesta);
        SingletonRequestQueue.getInstance(AnyadirComentario.this).addToRequestQueue(ac);

    }

    private void obtenerDatos(){
        Bundle extras = getIntent().getExtras();
        thisUser = (Usuario) extras.getSerializable("PasarEsteUsuario");
        thisFuente = (Fuente) extras.getSerializable("PasarEstaFuente");
    }
}
