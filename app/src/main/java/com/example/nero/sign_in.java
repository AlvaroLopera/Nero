package com.example.nero;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import PaqueteRequest.sign_inRequest;
import PaqueteRequestQueueSingleton.SingletonRequestQueue;

public class sign_in extends AppCompatActivity {

    private MaterialButton btn_confirmar;
    private TextInputLayout nombre;
    private TextInputLayout nick;
    private TextInputLayout password;
    private TextInputLayout email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //R
        nombre = (TextInputLayout) findViewById(R.id.campo_reg_RNombre);
        nick = (TextInputLayout) findViewById(R.id.campo_reg_RUsuario);
        password = (TextInputLayout) findViewById(R.id.campo_reg_RPass);
        email = (TextInputLayout) findViewById(R.id.campo_reg_REmail);
        btn_confirmar = (MaterialButton) findViewById(R.id.btn_aceptar_registro);

        //Eventos
        nick.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(nick.getError()!=null)
                    nick.setError(null);
            }
        });

        password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(password.getError()!=null)
                    password.setError(null);
            }
        });

        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(email.getError()!=null)
                    email.setError(null);
            }
        });
        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inombre = nombre.getEditText().getText().toString();
                String inick = nick.getEditText().getText().toString();
                String ipassword = password.getEditText().getText().toString();
                String iemail = email.getEditText().getText().toString();

                boolean ningunError = true;

                //Comprobación del nick
                if(inick.contains(" ") || inick.isEmpty()){
                    nick.setError("No puede haber espacios");
                    ningunError = false;
                }

                //Comprobación de la contraseña
                if(ipassword.contains(" ") || ipassword.length()<8){
                    password.setError("Minimo 8 caracteres sin espacios");
                    ningunError = false;
                }

                //Comprobación de email
                if(iemail.contains(" ") || iemail.isEmpty()){
                    email.setError("No puede contener espacios");
                    ningunError = false;
                }

                if(ningunError){
                    usarVolley(inombre,inick,ipassword,iemail);
                }
            }
        });
    }

    private void usarVolley(String inombre, String inick, String ipassword, String iemail){
        iemail = iemail+"@gmail.com";
        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject j = new JSONObject(response);
                    System.out.println("---------RESPUESTA DEL REGISTRO: "+j.toString());
                    if(j.getBoolean("existeUsuario")){
                        nick.setError("El usuario ya existe, intenta otro");
                        System.out.println("El usuario existe");
                    } else if(j.getBoolean("existeEmail")){
                        email.setError("Email en uso, prueba otro");
                        System.out.println("El email existe");
                    }else if(j.getBoolean("success")){
                        Toast.makeText(sign_in.this,"Te has registrado correctamente",Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(sign_in.this, "Ha ocurrido un error en la BD", Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.getMessage();
                }
            }
        };

        sign_inRequest sr = new sign_inRequest(inick, ipassword, iemail, inombre, respuesta);
        SingletonRequestQueue.getInstance(sign_in.this).addToRequestQueue(sr);
    }
}
