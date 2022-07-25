package com.example.nero;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;

import PaqueteClasesPrincipales.Usuario;
import PaqueteRequest.LoginRequest;
import PaqueteRequestQueueSingleton.SingletonRequestQueue;

public class MainActivity extends AppCompatActivity {
    //Constantes
    private static final int MY_SOCKET_TIMEOUT_MS = 6000;

    //Campos
    private Button boton_IC;
    private EditText camp_usuario;
    private EditText camp_contra;
    private ProgressBar circulo_Carga;

    //Datos
    private String usu;
    private String cont;

    private Button boton_sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //R
        boton_IC = (Button) findViewById(R.id.boton_is);
        camp_usuario = (EditText) findViewById(R.id.campoUsuario);
        camp_contra = (EditText) findViewById(R.id.campoContra);
        circulo_Carga = (ProgressBar) findViewById(R.id.barraDeCarga);
        circulo_Carga.setVisibility(View.INVISIBLE);
        boton_sign_in = (Button) findViewById(R.id.btn_sign_in);

        //Eventos
        boton_IC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circulo_Carga.setVisibility(View.VISIBLE);
                usarVolley();
            }

        });

        boton_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), sign_in.class);
                startActivity(intent);
            }
        });
    }


    private void usarVolley(){
        usu = camp_usuario.getText().toString();
        cont = camp_contra.getText().toString();

        Response.Listener<String> respuesta = new Response.Listener<String>(){
            @Override
            public void onResponse(String respuesta){
                circulo_Carga.setVisibility(View.INVISIBLE);
                try{
                    JSONObject jsonRespuesta = new JSONObject(respuesta);
                    boolean ok = jsonRespuesta.getBoolean("success");
                    if(ok){
                        boolean baneado = jsonRespuesta.getBoolean("baneado");
                        if(baneado){
                            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                            alerta.setMessage("Estas baneado").setNegativeButton("Volver", null).create().show();
                        } else {
                            //AQUI DEBERIAMOS OBTENER TODOS LOS DATOS DEL USUARIO
                            Usuario thisUser = new Usuario(usu, cont);
                            thisUser.setNombre(jsonRespuesta.getString("nombre"));
                            Intent intent = new Intent(getApplicationContext(), activity_maps.class);
                            intent.putExtra("PasarEsteUsuario", thisUser);
                            startActivity(intent);
                        }
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                        alerta.setMessage("Datos incorrectos").setNegativeButton("Volver", null).create().show();
                    }
                } catch (JSONException e){
                    e.getMessage();
                }
            }
        };


        LoginRequest lr = new LoginRequest(usu, cont, respuesta);
        lr.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonRequestQueue.getInstance(MainActivity.this).addToRequestQueue(lr);
    }
}
