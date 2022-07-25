package Ajustes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nero.R;

import PaqueteClasesPrincipales.Usuario;

public class EditarPerfil extends AppCompatActivity {

    private Usuario usuarioModificar;

    private EditText etUsuario, etCorreo, etNombre;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        obtenerDatosUsuario();

        etUsuario = (EditText) findViewById(R.id.etNombreUsuario);
        etCorreo = (EditText) findViewById(R.id.etEmail);
        etNombre = (EditText) findViewById(R.id.etNombreCompleto);
        etPassword = (EditText) findViewById(R.id.etContrasenaNueva);

        etUsuario.setText(usuarioModificar.getNombre_usuario());
        etPassword.setText(usuarioModificar.getPassword());
        etCorreo.setText(usuarioModificar.getEmail());
        etNombre.setText(usuarioModificar.getNombre());


    }

    //Obtenemos los datos del usuario de la activity anterior
    private void obtenerDatosUsuario(){
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        usuarioModificar = (Usuario) extras.getSerializable("Usuario");

        /*En este método se deberia recoger los datos del usuario desde la Base de Datos,
         * en lugar de pasar los datos entre actividades
         */
    }

    public void guardarCambios(View view){

        boolean cambio = false;


        //Comprobamos que los campos sean diferentes
        if(!(usuarioModificar.getNombre_usuario().contentEquals(etUsuario.getText().toString()))){

            //Funcion de modificar nombre_usuario

            usuarioModificar.setNombre_usuario(etUsuario.getText().toString());

            //usuarioModificar.update -> nombre del usuario

            cambio = true;
        }

        if(!(usuarioModificar.getPassword().contentEquals(etPassword.getText().toString()))){

            //Funcion de modificar contraseña

            usuarioModificar.setPassword(etPassword.getText().toString());

            //usuarioModificar.update -> contraseña

            cambio = true;
        }



        if(!(usuarioModificar.getEmail().contentEquals(etCorreo.getText().toString()))){

            //Funcion de modificar email

            usuarioModificar.setEmail(etCorreo.getText().toString());

            //usuarioModificar.update ->  email

            cambio = true;
        }

        if(!(usuarioModificar.getNombre().contentEquals(etNombre.getText().toString()))){

            //Funcion de modificar nombre

            usuarioModificar.setNombre(etCorreo.getText().toString());

            //usuarioModificar.update -> nombre completo

            cambio = true;
        }


        //Si se ha producido algún cambio
        if(cambio){
            Toast.makeText(this.getApplication(),"Se ha realizado la modificación correctamente"
                    ,Toast.LENGTH_LONG).show();
        }

        finish();

    }

    @Override
    public void finish() {
        Intent data = new Intent();

        // Devolver dos pares de valores.
        data.putExtra("UsuarioModificado",  usuarioModificar);
        setResult(RESULT_OK, data);
        super.finish();
    }

}
