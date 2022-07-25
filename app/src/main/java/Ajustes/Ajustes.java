package Ajustes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nero.R;

import PaqueteClasesPrincipales.Usuario;

public class Ajustes extends AppCompatActivity {
    private Button btnPermisos, btnEditarPerfil, btnModificarFuente;
    private Usuario usuario;
    //Modificar ajustes
    private final int RESQUEST_CODE_MODIFICAR_PERFIL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        /*
        btnPermisos = (Button) findViewById(R.id.btnModificarPermisos);
        btnEditarPerfil = (Button) findViewById(R.id.btnEditarPerfil);
        btnModificarFuente = (Button) findViewById(R.id.btnModificarFuente);*/

        obtenerDatosUsuario();


    }

    /*Método que nos envia a la pantalla de Ajustes de nuestra aplicación. Para el cambio de los
     * permisos */
    public void modificarPermisos(View view){

        Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
        i.setData(uri);
        startActivity(i);

    }

    public void editarPerfil(View view){

        Intent i = new Intent(getApplicationContext(), EditarPerfil.class);
        i.putExtra("Usuario", usuario);
        startActivityForResult(i, RESQUEST_CODE_MODIFICAR_PERFIL);


    }

    public void modificarFuente(View view){

        Intent i = new Intent(getApplicationContext(), ModificarFuente.class);
        startActivity(i);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RESQUEST_CODE_MODIFICAR_PERFIL) {
            if (data.hasExtra("UsuarioModificado")) {
                usuario = (Usuario) data.getExtras().getSerializable("UsuarioModificado");
            }
        }
    }


    //Obtenemos los datos del usuario de la activity anterior
    private void obtenerDatosUsuario(){
        Bundle extras = getIntent().getExtras();

        usuario = (Usuario) extras.getSerializable("usuario");
    }
}
